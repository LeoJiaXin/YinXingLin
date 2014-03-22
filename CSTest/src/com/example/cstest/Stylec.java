package com.example.cstest;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.example.Utils.DishesUtils;
import com.example.Utils.DoSomeThing;
import com.example.object.Dishes;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class Stylec extends FragmentActivity {
    private static final String[] CONTENT = new String[] { "Recent", "Artists", "Albums", "Songs" };
    private static final int[] ICONS = new int[] {
	        R.drawable.perm_group_calendar,
	        R.drawable.perm_group_camera,
	        R.drawable.perm_group_device_alarms,
	        R.drawable.perm_group_location
	};
    ArrayList<Dishes> menu;
    TFragment t = new TFragment();
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stylec);

        FragmentPagerAdapter adapter = new MyAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_stylec);
        UnderlinePageIndicatorEx uindicator = (UnderlinePageIndicatorEx)findViewById(R.id.indicator_underline);
        indicator.setViewPager(pager);
        uindicator.setViewPager(pager);
        uindicator.setFades(false);
        handler = new Handler();
        indicator.setOnPageChangeListener(uindicator);
        menu = new ArrayList<Dishes>();
        TFragment.menu = menu;
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				DishesUtils du = new DishesUtils(handler);
				du.getDisher(0, "uestc", menu, new DoSomeThing() {
					
					@Override
					public void doit(boolean flag) {
						t.reflesh();
					}
				});
				
			}
		}).start();
        
    }

    @Override
	protected void onDestroy() {
		for(int i=0;i<TFragment.menu.size();i++)
			TFragment.menu.get(i).getPhoto().recycle();
		super.onDestroy();
	}

	class MyAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	if(position == 0)
        	{
        		return t;
        	}
            return TFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";//CONTENT[position % CONTENT.length];//.toUpperCase();
        }

        @Override
        public int getCount() {
          return ICONS.length;
        }

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return ICONS[index];
		}
    }
}

