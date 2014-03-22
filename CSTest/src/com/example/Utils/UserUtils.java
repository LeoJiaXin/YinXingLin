package com.example.Utils;

import httpconnect.HttpConnect;

import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.object.User;

import android.os.Handler;

public class UserUtils {
	private final String URL = "http://192.168.1.127:8080/mywebsite/";
	public Handler handler;
	public static DoSomeThing justdoit;
	private boolean result = false;
	HttpConnect httpConnect = new HttpConnect();

	public void getUser(User user, String name, DoSomeThing justdoit)
			throws Exception {

		// User user = new User();
		UserUtils.justdoit = justdoit;
		String URL = this.URL + "getuser? " + "name=" + name;

		JSONObject json = httpConnect.getInfo(URL);

		try {
			user.setName(json.getString("name"));
			user.setSchool(json.getString("school"));
			user.setAddress(json.getString("address"));
			user.setInfo(json.getString("info"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		handler.post(new Runnable() {

			@Override
			public void run() {
				UserUtils.justdoit.doit(false);
			}
		});
		// return user;
	}

	public boolean signIn(String name, String password) throws JSONException,
			ClientProtocolException, IOException {
		Boolean result = false;
		String URL = this.URL + "confirm";

		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("password", password);

		JSONObject obj = httpConnect.getInfo(URL,json);
		result = Boolean.getBoolean(obj.getString("info"));

		return result;
	}

	public void update(User user, Boolean isCreat, DoSomeThing justdoit)
			throws ClientProtocolException, IOException, ParseException,
			JSONException {
		UserUtils.justdoit = justdoit;
		String URL = this.URL + "update";
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
		JSONObject obj = httpConnect.getInfo(URL,json);
			result = obj.getBoolean("info");
			System.out.println("aaa");
		handler.post(new Runnable() {
			@Override
			public void run() {
				UserUtils.justdoit.doit(result);
				result = false;
			}
		});
	}
}
