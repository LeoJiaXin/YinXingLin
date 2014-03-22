package com.example.cstest;

import com.viewpagerindicator.LinePageIndicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class Stylea extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stylea);

        mAdapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        LinePageIndicator mIndicator = (LinePageIndicator) findViewById(R.id.indicator_line);
        mIndicator.setViewPager(mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stylea, menu);
		return true;
	}

}
