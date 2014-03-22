package com.yinxinglin.activity;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.object.User;
import com.yinxinglin.utils.DoSomeThing;
import com.yinxinglin.utils.HttpUtils;
import com.yinxinglin.utils.UserUtils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFace extends Activity {

	private EditText username, password, confirmp, shool, address, info;
	private Button register,alter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HttpUtils.init(new Handler());
		setContentView(R.layout.activity_register_face);
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
								UserUtils.update(user,false, new DoSomeThing() {

									@Override
									public void doit(boolean flag, String info,Object obj) {
										if(flag)
										{
											Toast.makeText(RegisterFace.this, "修改成功 ", Toast.LENGTH_SHORT).show();		
										}else{
											Toast.makeText(RegisterFace.this, "未知参数", Toast.LENGTH_SHORT).show();		
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
								UserUtils.update(user,true, new DoSomeThing() {
									
									@Override
									public void doit(boolean flag, String info,Object obj) {
										if(flag)
										{
											Toast.makeText(RegisterFace.this, "注册成功 ", Toast.LENGTH_SHORT).show();		
										}else{
											Toast.makeText(RegisterFace.this, "未知错误", Toast.LENGTH_SHORT).show();		
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
					Toast.makeText(RegisterFace.this, "两次输入的密码不一致 ", Toast.LENGTH_SHORT).show();		
				}
			}
		});
	}
}
