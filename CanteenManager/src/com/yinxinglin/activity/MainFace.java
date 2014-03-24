package com.yinxinglin.activity;

import java.io.File;
import java.util.ArrayList;

import com.viewpagerindicator.TabPageIndicator;
import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.activity.PopupPicture.PicSolver;
import com.yinxinglin.object.Dishes;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;
import com.yinxinglin.utils.HttpUtils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class MainFace extends FragmentActivity {
    
	private static final String[] CONTENT = new String[] { "推荐", "分类", "发现" };   
	private ArrayList<Dishes> menu;
    private RecFragment rec;
    private NewFragment new_one;
    private MoreFragment found;
    private boolean quit_state = false;
    private PopupWindow setting_menu = null;
    private View layout = null;
    private Button setting = null;
    private PopupPicture pop = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_face);
        rec = new RecFragment();
        new_one = new NewFragment();
        found = new MoreFragment();
        FragmentPagerAdapter adapter = new MyAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator_tab);
        indicator.setViewPager(pager);
        menu = new ArrayList<Dishes>();
        RecFragment.menu = menu;
        HttpUtils.init(new Handler());
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				DishesUtils.getDishes(0, menu, "uestc",new DoSomeThing() {
					
					@Override
					public void doit(boolean flag,String s,Object obj) {
						if(flag) {
							rec.reflesh();
						}
					}
				});	
			}
		}).start();
        
        //主界面的右上方按钮事件
        layout = getLayoutInflater().inflate(R.layout.popup_options, null);
        layout.findViewById(R.id.personal_message).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), PersonMegFace.class);
				startActivity(intent);
			}
		});
        setting = (Button)findViewById(R.id.setting_in_menu);
        setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(setting_menu == null) {
					setting_menu = new PopupWindow(layout,
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					setting_menu.setOutsideTouchable(true);
					setting_menu.setFocusable(true);
					setting_menu.setBackgroundDrawable(new BitmapDrawable());  
					setting_menu.showAsDropDown(setting);
				}else{
					setting_menu.showAsDropDown(setting);
				}
			}
		});
        findViewById(R.id.add_in_menu).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(pop == null) {
					pop = new PopupPicture(MainFace.this);
					pop.showAsDropDown(setting);
				}else{
					pop.showAsDropDown(setting);
				}
			}
		});
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == 1)
            return;
    	pop.OnActionResult(requestCode, resultCode, data, new PicSolver() {
			
			public void afterCrop(String file) {
				Intent intent = new Intent();
				intent.setClass(MainFace.this, AddDishesFace.class);
				intent.putExtra("filename", file);
				startActivity(intent);
			}
		});
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(!quit_state) {
	    		Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
	    		quit_state = true;
	    		new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						quit_state = false;
					}
				}).start();
	    		return true;
	    	}
			break;
		default:break;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    @Override
	protected void onDestroy() {
		for(int i=0;i<RecFragment.menu.size();i++)
			RecFragment.menu.get(i).getPhoto().recycle();
		super.onDestroy();	
	}

	class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	switch(position%CONTENT.length) {
        	case 0:
        		return rec;
        	case 1:
        		return new_one;
        	case 2:
        		return found;
    		default:
    			return null;
        	}
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
          return CONTENT.length;
        }

    }
}
