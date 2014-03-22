package com.example.cstest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.apache.http.HttpConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.entity.EntitySerializer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	//static final String url = "http://10.0.2.2:8080/mywebsite/confirm";
	public Button b,register;
	public EditText user,password;
	public Runnable task,jump;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sp = getSharedPreferences("canteen", Context.MODE_PRIVATE);
		if(!sp.getBoolean("landBefore", false))
		{
			Editor editor = sp.edit();
			editor.putBoolean("landBefore", true);
			editor.commit();
			
		}
		
		b = (Button)findViewById(R.id.submit);
		register = (Button)findViewById(R.id.register);
		user = (EditText)findViewById(R.id.user);
		password = (EditText)findViewById(R.id.password);
		handler = new Handler();
		
		jump = new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ImageActivity.class);
				startActivity(intent);
			}
		};
		task = new Runnable() {
			@Override
			public void run() {
				
				JSONObject js = new JSONObject();
				try {
					js.put("User_name", user.getText().toString());
					js.put("User_password", password.getText().toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String url = "http://10.0.2.2:8080/mywebsite/confirm";
				//HttpGet hg = new HttpGet(url);
				HttpPost hpost = new HttpPost(url);
				try {
					hpost.setEntity(new StringEntity(js.toString()));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HttpClient httpclient = new DefaultHttpClient();
				
				try {
					HttpResponse hp = httpclient.execute(hpost);
					if(hp.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
						JSONObject rejs = new JSONObject(EntityUtils.toString(hp.getEntity()));
						if(rejs.getBoolean("info"))
						{
							handler.post(jump);
						}
						
//						System.out.println(js.get("username"));
//						System.out.println(js.get("password"));
						//System.out.println(EntityUtils.toString(hp.getEntity()));
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(task).start();
			}
		});
		register.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RegiserActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
