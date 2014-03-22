package com.yinxinglin.activity;

import java.util.ArrayList;

import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.object.Dishes;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;
import com.yinxinglin.utils.HttpUtils;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class MainFace extends FragmentActivity {
    private static final String[] CONTENT = new String[] { "Recent", "Artists", "Albums", "Songs" };
    private static final int[] ICONS = new int[] {
	        R.drawable.perm_group_calendar,
	        R.drawable.perm_group_camera,
	        R.drawable.perm_group_device_alarms,
	        R.drawable.perm_group_location
	};
    ArrayList<Dishes> menu;
    TFragment t = new TFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_face);
        FragmentPagerAdapter adapter = new MyAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_stylec);
        UnderlinePageIndicatorEx uindicator = (UnderlinePageIndicatorEx)findViewById(R.id.indicator_underline);
        indicator.setViewPager(pager);
        uindicator.setViewPager(pager);
        uindicator.setFades(false);
        indicator.setOnPageChangeListener(uindicator);
        menu = new ArrayList<Dishes>();
        TFragment.menu = menu;
        HttpUtils.init(new Handler());
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				DishesUtils.getDishes(0, menu, "uestc",new DoSomeThing() {
					
					@Override
					public void doit(boolean flag,String s,Object obj) {
						if(flag) {
							t.reflesh();
						}
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
