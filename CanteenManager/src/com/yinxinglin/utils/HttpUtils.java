package com.yinxinglin.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

public class HttpUtils {

	public static final String URL = "http://10.0.2.2:8080/mywebsite/";
	public static Handler handler = null;
	
	public static void init(Handler handler) {
		HttpUtils.handler = handler;
	}
	public static JSONObject getInfo(String URL) {
		JSONObject json = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		httpGet.addHeader("Content-Type", "text/html");
		httpGet.addHeader("charset", HTTP.UTF_8);  			
		try {
			HttpResponse hr = httpClient.execute(httpGet);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(hr.getEntity()));
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return json;
	}

	public static JSONObject getInfo(String URL, JSONObject json2)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("result", "Unknown error");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		try {
			httpPost.setEntity(new StringEntity(json2.toString()));
			HttpResponse hr = httpClient.execute(httpPost);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(hr.getEntity(),HTTP.UTF_8));
			}
			return json;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return json;
		}
	}
}
