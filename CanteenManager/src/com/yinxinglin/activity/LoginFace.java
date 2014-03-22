package com.yinxinglin.activity;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.utils.DoSomeThing;
import com.yinxinglin.utils.HttpUtils;
import com.yinxinglin.utils.UserUtils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFace extends Activity {
	
	private EditText username,password; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HttpUtils.init(new Handler());
		setContentView(R.layout.activity_login_face);
		Button login = (Button)findViewById(R.id.login_bn);
		Button register = (Button)findViewById(R.id.register_bn);
		username = (EditText)findViewById(R.id.username_inputer);
		password = (EditText)findViewById(R.id.password_inputer);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						UserUtils.loginConfirm(username.getText().toString(),password.getText().toString(),
								new DoSomeThing() {
									
									@Override
									public void doit(boolean result, String info, Object obj) {
										if(result) {
											//信息要保存
											Intent intent = new Intent();
											intent.setClass(LoginFace.this, MainFace.class);
											startActivity(intent);
										}else{
											Toast.makeText(LoginFace.this, "密码不正确或其他原因", Toast.LENGTH_SHORT).show();
										}
									}
						});
					}
				}).start();
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginFace.this, RegisterFace.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_face, menu);
		return true;
	}

}
