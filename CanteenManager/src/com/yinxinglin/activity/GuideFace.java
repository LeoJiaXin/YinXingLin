package com.yinxinglin.activity;

import com.yingxinlin.canteenmanager.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class GuideFace extends FragmentActivity {

	public static int width=0,height=0;
	
	private final int GuideID[] = { 
		R.drawable.guide1,R.drawable.guide2,R.drawable.guide3
	};
	private ViewPager guider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		SharedPreferences sp = getSharedPreferences("canteen", Context.MODE_PRIVATE);
		if(!sp.getBoolean("landBefore", false))
		{
			Editor editor = sp.edit();
			editor.putBoolean("landBefore", true);
			editor.commit();
			setContentView(R.layout.activity_guide_face);
			guider = (ViewPager)findViewById(R.id.Guide_viewPager);
			guider.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
				
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return GuideID.length;
				}
				
				@Override
				public Fragment getItem(int pos) {
					return new GuideFragment(GuideID[pos]);
				}
				
			});
		}else{
			Intent intent = new Intent();
			intent.setClass(this, LoginFace.class);
			startActivity(intent);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

class GuideFragment extends Fragment {

	private int pos = 0;
	private Bitmap bmp = null;
	private Button enter = null;
	
	public GuideFragment(int BitmapID) {
		this.pos = BitmapID;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bitmap temp = BitmapFactory.decodeResource(getResources(), pos);
		bmp = Bitmap.createScaledBitmap(temp, GuideFace.width, GuideFace.height, false);
		temp.recycle();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginFace.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.guide_face_cell, container, false);
		ImageView face = (ImageView)v.findViewById(R.id.guide_cell);
		face.setImageBitmap(bmp);
		enter = (Button)v.findViewById(R.id.enter_app);
		if(pos != R.drawable.guide3) {
			enter.setVisibility(View.INVISIBLE);
		}
		return v;
	}

	@Override
	public void onDestroy() {
		if(bmp != null) bmp.recycle();
		super.onDestroy();
	}
	
	
}