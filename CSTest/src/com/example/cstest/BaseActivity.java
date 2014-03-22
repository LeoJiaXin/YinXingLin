package com.example.cstest;

import java.util.Random;

import com.viewpagerindicator.PageIndicator;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class BaseActivity extends FragmentActivity {
	 private static final Random RANDOM = new Random();

    FragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    
}
