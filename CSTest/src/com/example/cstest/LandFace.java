package com.example.cstest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LandFace extends Activity {

	private Button b1,b2,b3,b4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_land_face);
		b1 = (Button)findViewById(R.id.syle_a);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LandFace.this, Stylea.class);
				startActivity(intent);
			}
		});
		b2 = (Button)findViewById(R.id.syle_b);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LandFace.this, Styleb.class);
				startActivity(intent);
			}
		});
		b3 = (Button)findViewById(R.id.syle_c);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LandFace.this, Stylec.class);
				startActivity(intent);
			}
		});
		b4 = (Button)findViewById(R.id.syle_d);
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LandFace.this, Styled.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.land_face, menu);
		return true;
	}

}
