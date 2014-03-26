package com.yinxinglin.activity;

import java.io.File;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.object.Dish;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;
import com.yinxinglin.utils.HttpUtils;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class AddDishesFace extends Activity {

	private EditText name,price,canteen,type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dishes_face);
		Intent intent = getIntent();
		ImageView iv = (ImageView)findViewById(R.id.before_add_pic);
		iv.setImageURI(Uri.fromFile(new File(intent.getStringExtra("filename"))));
		name = (EditText)findViewById(R.id.before_add_name);
		price = (EditText)findViewById(R.id.before_add_price);
		canteen = (EditText)findViewById(R.id.before_add_canteen);
		type = (EditText)findViewById(R.id.before_add_type);
		
		findViewById(R.id.before_add_submit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {	
						Dish dish = new Dish();
						dish.setName(name.getText().toString());
						try{
							dish.setPrice(Float.parseFloat(price.getText().toString()));
						}catch(NumberFormatException mfe) {
							HttpUtils.handler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(AddDishesFace.this, "�۸����벻��", Toast.LENGTH_SHORT).show();
								}
							});
							return;
						}
						dish.setCanteen(canteen.getText().toString());
						dish.setType(type.getText().toString());
						dish.setUserName("������");
						dish.setSchool("���ӿƼ���ѧ");
						DishesUtils.signIn(dish, new DoSomeThing() {
							
							@Override
							public void doit(boolean result, String info, Object obj) {
								if(result) {
									Toast.makeText(AddDishesFace.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
									AddDishesFace.this.finish();
								}else
									Toast.makeText(AddDishesFace.this, "���ʧ��", Toast.LENGTH_SHORT).show();
							}
						});
					}
				}).start();
				
			}
		});
		
	}


}
