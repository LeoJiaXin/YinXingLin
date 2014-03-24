package com.yinxinglin.activity;

import java.io.File;

import com.yingxinlin.canteenmanager.R;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class AddDishesFace extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dishes_face);
		Intent intent = getIntent();
		ImageView iv = (ImageView)findViewById(R.id.before_add_pic);
		iv.setImageURI(Uri.fromFile(new File(intent.getStringExtra("filename"))));
	}


}
