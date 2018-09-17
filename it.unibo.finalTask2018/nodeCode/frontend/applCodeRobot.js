/*
 * it.unibo.frontend/nodeCode/frontend/applCodeRobot.js
 */
var express     	= require('express');
var path         	= require('path');
var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
var serverWithSocket= require('./robotFrontendServer');
var cors            = require('cors');
var robotModel      = require('./appServer/models/robot');
var User            = require("./appServer/models/user");
var mqttUtils       ; 	//to be set later fort EXTERNALACTUATORMQTT
var session         ; 	//to be set later for AUTH;
var passport        ; 	//to be set later for AUTH;
var setUpPassport   ; 	//to be set later for AUTH;
var mongoose        ; 	//to be set later for AUTH;
var flash           ; 	//to be set later for AUTH;

var net 			;	//to be set later fort EXTERNALACTUATORSOCKET
var host			;	//to be set later fort EXTERNALACTUATORSOCKET
var port			;	//to be set later fort EXTERNALACTUATORSOCKET
var conn			;   //to be set later fort EXTERNALACTUATORSOCKET

var settings = require('./settings.json');

var app              = express();

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'viewRobot'));	 
app.set("view engine", "ejs");

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use( cors() );  //npm install cors --save ;
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'jsCode')))

var externalActuatorMqtt = settings.externalActuatorMqtt; //when true, the application logic is external to the server (Mqtt)
var externalActuatorSocket = settings.externalActuatorSocket; //when true, the application logic is external to the server (Socket)
var withAuth         = settings.withAuth;

if( externalActuatorMqtt ) mqttUtils  = require('./uniboSupports/mqttUtils');
if( externalActuatorSocket ){
	net = require('net');
	host = settings.applHost;
	port = settings.applPort;
	
	setUpConn();
}
if( withAuth ){
	 session          = require("express-session");	 
	 passport         = require("passport");			 
	 setUpPassport    = require("./setuppassport");   
	 mongoose         = require("mongoose");			 
	 flash            = require("connect-flash"); 
	
	 setUpAuth();
}

/*
 * ====================== AUTH ================
 */	
 	app.get('/', function(req, res) {
 		if( withAuth ) res.render("login");
 		else 
			//res.send("WAIT A MOMENT")
 			res.render("access");
 	});	

	if(passport){
	app.get("/login", function(req, res) {
		 res.render("login");
	});

	app.post("/login", passport.authenticate("login", {
		  successRedirect: "/access",			 
		  failureRedirect: "/login",
		  failureFlash: true
	}));

	app.get("/access", ensureAuthenticated, function(req, res, next) {	 
		res.render("access");		 
	});
	app.get("/logout", function(req, res) {
	  req.logout();	//a new function added by Passport;
	  res.redirect("/");
	});
	app.get("/signup", function(req, res) {
	  res.render("signup",
			   {flash: {errors: req.flash('error')}});

	});

	app.post("/signup", function(req, res, next) {  
	  var username = req.body.username;
	  var password = req.body.password;
	  User.findOne({ username: username }, function(err, user) {
	    if (err) { return next(err); }
	    if (user) {
	      req.flash("error", "User already exists");
	      return res.redirect("/signup");
	    }
	    var newUser = new User({
	      username: username,
	      password: password
	    });
	    newUser.save(next);
	  });
	}, passport.authenticate("login", {
	  successRedirect: "/",
	  failureRedirect: "/signup",
	  failureFlash: true
	}));

	app.get("/users/:username", function(req, res, next) {
	  User.findOne({ username: req.params.username }, function(err, user) {
	    if (err) { return next(err); }
	    if (!user) { return next(404); }
	    res.render("profile", { user: user });
	  });
	});
	app.get("/edit", ensureAuthenticated, function(req, res) {
	  res.render("edit",
			  {flash: {infos: req.flash('info')}});
	});
	app.post("/edit", ensureAuthenticated, function(req, res, next) {console.log("edit " + req.body.displayName);
	  req.user.displayName = req.body.displayname;
	  req.user.bio = req.body.bio;
	  req.user.save(function(err) {
	    if (err) {
	      next(err);
	      return;
	    }
	    req.flash("info", "Profile updated!");
	    res.redirect("/edit");
	  });
	});
	}

/*
 * ====================== COMMANDS ================
 */
	app.post("/robot/actions/commands/appl", function(req, res) {
		console.info("START THE APPLICATION "   );
		delegate( "alarm","usercmd(clean)", "application", req, res);
 	});	
	app.post("/robot/actions/commands/stop", function(req, res) {
		console.info("STOP THE APPLICATION ");
		delegate( "alarm","usercmd(halt)","stop application", req, res);
 	});	

	app.post("/robot/actions/commands/w", function(req, res) {
		if(externalActuatorMqtt || externalActuatorSocket)
			delegate("usercmd","usercmd(robotgui( w(low) ))","moving forward",req,res);
		else actuate( `{ "type": "moveForward",  "arg": -1 }`, "server moving forward", req, res);
		
	});	
	app.post("/robot/actions/commands/s", function(req, res) {
		if(externalActuatorMqtt || externalActuatorSocket)
			delegate("usercmd","usercmd(robotgui( s(low) ))","moving backward",req,res);
		else actuate( `{ "type": "moveBackward",  "arg": -1 }`, "server moving backward", req, res);
	});	
	app.post("/robot/actions/commands/a", function(req, res) {
		if(externalActuatorMqtt || externalActuatorSocket)
			delegate("usercmd","usercmd(robotgui( a(low) ))","moving left",req,res);
		else actuate( `{ "type": "turnLeft",  "arg": 1000 }`, "server moving left", req, res);
	});	
	app.post("/robot/actions/commands/d", function(req, res) {
		if(externalActuatorMqtt || externalActuatorSocket)
			delegate("usercmd","usercmd(robotgui( d(low) ))","moving right",req,res);
		else actuate( `{ "type": "turnRight",  "arg": 1000 }`, "server moving right", req, res);
	});	
	app.post("/robot/actions/commands/h", function(req, res) {
		if(externalActuatorMqtt || externalActuatorSocket)
			delegate("usercmd","usercmd(robotgui( h(low) ))","stopped",req,res);
		else actuate( `{  "type": "alarm",  "arg": 1000 }`, "server stopped", req, res);
	});		
	
//=================== UTILITIES =========================

var msgNum=1; //parte da 1 e aumentera' via via
function delegate(eventName,payload,newState,req,res){
	var msg= "msg("+eventName+",event,js,none,"+payload+","+msgNum++ +")";
	robotModel.robot.state = newState;
	console.log("emits -> "+ msg);
	try{
		if(externalActuatorMqtt)
	 		mqttUtils.publish( msg );	//topic  = "unibo/qasys"
		else if(externalActuatorSocket)
			conn.write(msg+"\n");
	}
	catch(e){
		console.log("ERROR "+e);
	}
	res.render("access");
}
	
function setUpAuth(){ //AUTH
	try{	
		console.log("\tWORKING WITH AUTH ... "  ) ;
//		mongoose.connect("mongodb://192.168.99.100:27017/test"); //per il Mongo di docker
//		mongoose.connect("mongodb://localhost:27017/test");
		mongoose.connect(settings.mongoAddr);
		setUpPassport();	
		app.use(session({	 
			  secret: "LUp$Dg?,I#i&owP3=9su+OB%`JgL4muLF5YJ~{;t",
			  resave: true,
			  saveUninitialized: true
		}));
		app.use(flash());
		app.use(passport.initialize());
		app.use(passport.session());
		app.use(function(req, res, next) {
			  res.locals.currentUser = req.user;
			  res.locals.errors      = req.flash("error");
			  res.locals.infos       = req.flash("info");
			  next();
			});
	}catch( e ){
		console.log("SORRY, ERROR ... " + e) ;
	}	
}

function setUpConn(){ // EXTERNAL ACTUATOR SOCKET
	console.log('Connecting to '+ host + ":" + port);
	conn = net.connect({port:port,host:host});
	conn.setEncoding('utf-8');
	
	conn.on('data',function(data){
		console.log(data);
	})
	conn.on('close',function(){
		console.log('connection with '+host+":"+port+" is closed");
	});
	conn.on('end',function(){
		console.log('connection with '+host+":"+port+" is ended");
	})
}

function ensureAuthenticated(req, res, next) {
	  if (req.isAuthenticated()) {
		  next();
	  } else {
		    req.flash("info", "You must be logged into see this page.");
		    res.redirect("/login");
	  }
}

function actuate(cmd, newState, req, res ){ 
	var toRobot=require("./jsCode/clientRobotVirtual");
	toRobot.send( cmd );
	robotModel.robot.state = newState;
	res.render("access");
}

module.exports = app;

/*
 * ====================== REPRESENTATION ================
 */
	app.use( function(req,res){
	console.info("SENDING THE ANSWER " + req.result  );
	try{
		if( req.result != undefined)
			serverWithSocket.updateClient( JSON.stringify(req.result ) );
		res.send(req.result);
	}catch(e){console.info("SORRY ...");}
	} 
);
// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page;
  res.status(err.status || 500); 
  res.send("SORRY, ERROR=" + err.status );
});
