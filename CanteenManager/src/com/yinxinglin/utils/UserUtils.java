package com.yinxinglin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinxinglin.object.User;
import android.os.Handler;

public class UserUtils {
	public static DoSomeThing justdoit;
	private static boolean result = false;
	private static User user = null;
	
	public static void loginConfirm(String name, String password, DoSomeThing justdoit) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uname",name));
		params.add(new BasicNameValuePair("password", password));	
		JSONObject json = HttpUtils.getInfo(HttpUtils.URL+
				"confirm?"+ URLEncodedUtils.format(params, HTTP.UTF_8));
		try {
			if(json != null) {
				result = json.getBoolean("result");
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserUtils.justdoit = justdoit;
		HttpUtils.handler.post(new Runnable() {

			@Override
			public void run() {
				UserUtils.justdoit.doit(result, null, null);
			}
		});
	}

	public static void getUserInfo(String name, DoSomeThing justdoit) {
		user = null;
		StringBuffer URL = new StringBuffer();
		URL.append(HttpUtils.URL);
		URL.append("confirm?uname=");
		URL.append(name);
		JSONObject json = HttpUtils.getInfo(URL.toString());
		try {
			result = json.getBoolean("result");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(result) {
			try {
				user = new User();
				user.setName(json.getString("name"));
				user.setSchool(json.getString("school"));
				user.setAddress(json.getString("address"));
				user.setInfo(json.getString("info"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		UserUtils.justdoit = justdoit;
		HttpUtils.handler.post(new Runnable() {

			@Override
			public void run() {
				UserUtils.justdoit.doit(result, null, user);
			}
		});
	}
	public static void update(User user, Boolean isCreat, DoSomeThing justdoit)
			throws ClientProtocolException, IOException, ParseException,
			JSONException {
		UserUtils.justdoit = justdoit;
		String URL = HttpUtils.URL + "update";
		JSONObject json = new JSONObject();
		try {
			json.put("isCreat", isCreat);
			json.put("name", user.getName());
			json.put("password", user.getPassword());
			json.put("school", user.getSchool());
			json.put("address", user.getAddress());
			json.put("info", user.getInfo());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = HttpUtils.getInfo(URL,json);
			result = obj.getBoolean("info");
			System.out.println("aaa");
		HttpUtils.handler.post(new Runnable() {
			@Override
			public void run() {
				UserUtils.justdoit.doit(result, "Î´Öª´íÎó",null);
				result = false;
			}
		});
	}
}
