@echo off
if NOT "%1" == "" cd %1

cd server
cd src
node main 8999