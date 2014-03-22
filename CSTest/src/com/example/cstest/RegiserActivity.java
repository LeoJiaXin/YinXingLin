package com.example.cstest;

import com.example.Utils.DoSomeThing;
import com.example.Utils.UserUtils;
import com.example.object.User;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegiserActivity extends Activity {

	private EditText username, password, confirmp, shool, address, info;
	private Button register,alter;
	private UserUtils users;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		users = new UserUtils();
		users.handler = new Handler();
		setContentView(R.layout.activity_regiser);
		username = (EditText)findViewById(R.id.name_e);
		password = (EditText)findViewById(R.id.pass_e);
		confirmp = (EditText)findViewById(R.id.cpass_e);
		shool = (EditText)findViewById(R.id.shool_e);
		address = (EditText)findViewById(R.id.address_e);
		info = (EditText)findViewById(R.id.info_e);
		register = (Button)findViewById(R.id.register);
		alter = (Button)findViewById(R.id.alter);
		alter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(password.getText().toString().equals(confirmp.getText().toString()))
				{
					new Thread(new Runnable() {
						@Override
						public void run() {
							User user = new User();
							user.setName(username.getText().toString());
							user.setPassword(password.getText().toString());
							user.setSchool(shool.getText().toString());
							user.setAddress(address.getText().toString());
							user.setInfo(info.getText().toString());
							try {
								users.update(user,false, new DoSomeThing() {
									
									@Override
									public void doit(boolean flag) {
										if(flag)
										{
											Toast.makeText(RegiserActivity.this, "修改成功 ", Toast.LENGTH_SHORT).show();		
										}else{
											Toast.makeText(RegiserActivity.this, "未知参数", Toast.LENGTH_SHORT).show();		
										}
									}
								});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				}
				
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(password.getText().toString().equals(confirmp.getText().toString()))
				{
					new Thread(new Runnable() {
						@Override
						public void run() {
							User user = new User();
							user.setName(username.getText().toString());
							user.setPassword(password.getText().toString());
							user.setSchool(shool.getText().toString());
							user.setAddress(address.getText().toString());
							user.setInfo(info.getText().toString());
							try {
								users.update(user,true, new DoSomeThing() {
									
									@Override
									public void doit(boolean flag) {
										if(flag)
										{
											Toast.makeText(RegiserActivity.this, "注册成功 ", Toast.LENGTH_SHORT).show();		
										}else{
											Toast.makeText(RegiserActivity.this, "未知错误", Toast.LENGTH_SHORT).show();		
										}
									}
								});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				}else{
					Toast.makeText(RegiserActivity.this, "两次输入的密码不一致 ", Toast.LENGTH_SHORT).show();		
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regiser, menu);
		return true;
	}

}
