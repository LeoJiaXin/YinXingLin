package com.example.cstest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ImageActivity extends Activity {
	
	private Animation anim = null;
	private ImageView iv = null;
	private Runnable getImage = null,setImage = null;
	private Handler handler;
	private Bitmap bmp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		handler = new Handler();
		getImage = new Runnable() {
			
			@Override
			public void run() {
				try {
					URL url = new URL("http://10.0.2.2:8080/mywebsite/img");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				    conn.setConnectTimeout(5000);
				    conn.setRequestMethod("GET");
				    bmp = BitmapFactory.decodeStream(conn.getInputStream());
				    handler.post(setImage);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		setImage = new Runnable() {
			
			@Override
			public void run() {
				iv.setImageBitmap(bmp);
				
			}
		};
		anim = AnimationUtils.loadAnimation(this, R.anim.loading);
		iv = (ImageView)findViewById(R.id.image);
		iv.setAnimation(anim);
		new Thread(getImage).start();
	}

	@Override
	protected void onDestroy() {
		if(bmp!=null)
			bmp.recycle();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
