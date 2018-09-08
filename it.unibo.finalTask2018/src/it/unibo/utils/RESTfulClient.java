package it.unibo.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class RESTfulClient {

	private static HttpClient client = HttpClientBuilder.create().build();

	// POST
	public static HttpResponse execPOST(String url, String entity) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		if (entity != null) {
			StringEntity input = new StringEntity(entity);
			input.setContentType("application/JSON");
			post.setEntity(input);
		}
		HttpResponse response = client.execute(post);
		return response;
	}

	public static HttpResponse execPOST(String url) throws ClientProtocolException, IOException {
		return execPOST(url, null);
	}

	// GET
	public static HttpResponse execGET(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		return response;
	}

	// PUT
	public static HttpResponse execPUT(String url, String entity) throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(url);
		if (entity != null) {
			StringEntity input = new StringEntity(entity);
			input.setContentType("application/JSON");
			put.setEntity(input);
		}
		HttpResponse response = client.execute(put);
		return response;
	}

	public static HttpResponse execPUT(String url) throws ClientProtocolException, IOException {
		return execPUT(url, null);
	}

	// DELETE
	public static HttpResponse execDELETE(String url) throws ClientProtocolException, IOException {
		HttpDelete delete = new HttpDelete(url);
		HttpResponse response = client.execute(delete);
		return response;
	}

}
