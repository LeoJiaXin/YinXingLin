package com.example.cstest;

import java.util.ArrayList;
import java.util.List;

import com.viewpagerindicator.CirclePageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Styleb extends Activity implements OnPageChangeListener{

	private ViewPager vp = null;
	private ViewPagerAdapter vpAdapter; 
	private List<View> views;
	private int currentIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
		WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_landing_face);
		initViews();
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.what_new_two, null)); 
		views.add(inflater.inflate(R.layout.what_new_three, null)); 
		views.add(inflater.inflate(R.layout.what_new_five, null)); 
		views.add(inflater.inflate(R.layout.what_new_six, null)); 
		vp = (ViewPager)findViewById(R.id.pager);
		vpAdapter = new ViewPagerAdapter(views, this);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
		CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.circle);
	     indicator.setViewPager(vp);
	     indicator.setSnap(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_face, menu);
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
			
		
	}

}
