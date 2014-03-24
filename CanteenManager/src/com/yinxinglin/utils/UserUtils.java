package com.yinxinglin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinxinglin.object.User;

public class UserUtils {
	public static DoSomeThing justdoit;
	private static boolean result = false;
	private static User user = null;
	
	public static void loginConfirm(String name, String password, DoSomeThing justdoit) {
		
		StringBuffer sb = new StringBuffer(HttpUtils.URL);
		sb.append("confirm?uname=");
		sb.append(name);
		sb.append("&password=");
		sb.append(password);
		
		JSONObject json = HttpUtils.getInfo(sb.toString());
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
		try {
			URL.append(URLEncoder.encode(name, "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
			json.put("isCreate", isCreat);
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
			result = obj.getBoolean("isSuccess");
		HttpUtils.handler.post(new Runnable() {
			@Override
			public void run() {
				UserUtils.justdoit.doit(result, "Î´Öª´íÎó",null);
				result = false;
			}
		});
	}
}
