package com.yinxinglin.activity;

import java.util.ArrayList;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.object.Dishes;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public final class TFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    private Animation anim;
    public static ArrayList<Dishes> menu;
    private GridView gv = null;
    private MyAdapter gridadapter = null;
    private int ItemNum = 6;
    private boolean loadOnce = true,loadPermission = false;
    private boolean begin = false;
    public static TFragment newInstance(String content) {
        TFragment fragment = new TFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    public void reflesh() {
    	begin=true;
    	gridadapter.notifyDataSetChanged();
    	gv.setAdapter(gridadapter);
    }
    
    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.loading);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = inflater.inflate(R.layout.menu_face, container,false);
    	gv = (GridView)v.findViewById(R.id.menu_face);
    	gridadapter = new MyAdapter(inflater);
    	gv.setAdapter(gridadapter);
    	gv.setOnScrollListener(new OnLoadMoreListener());
    	return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
    private class OnLoadMoreListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if(firstVisibleItem+visibleItemCount == totalItemCount)
			{
				if(loadOnce) {
					loadPermission=true;
					loadOnce=false;
				}
			}else{
				loadPermission=false;
			}
			
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL && loadPermission)
			{
//				ItemNum+=6;
//				gridadapter.notifyDataSetChanged();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						DishesUtils.getDishes(menu.size(), menu, "uestc", new DoSomeThing() {
							
							@Override
							public void doit(boolean result, String info, Object obj) {
								ItemNum=menu.size();
								gridadapter.notifyDataSetChanged();
							}
						});
						
					}
				}).start();
				
				loadPermission=false;
			}else if(scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				loadOnce=true;
			}
		}
    	
    }
    
    public static void loadDishses(int position,ArrayList<Dishes> menu,View v) {
    	TextView tv;
    	if(position<menu.size()) {
	    	ImageView iv = (ImageView)v.findViewById(R.id.menu_cell_pic);
	    	iv.setImageBitmap(menu.get(position).getPhoto());
    		tv = (TextView)v.findViewById(R.id.menu_cell_name);
    		tv.setText(menu.get(position).getName());
    		tv = (TextView)v.findViewById(R.id.menu_cell_zan);
    		tv.setText(""+menu.get(position).getGood());
    		tv = (TextView)v.findViewById(R.id.menu_cell_price);
    		tv.setText(""+menu.get(position).getPrice());
    	}
    }
    
    private class MyAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	public MyAdapter(LayoutInflater inflater) {
    		this.inflater = inflater;
    	}
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ItemNum;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.food_menu_cell, null);	
				if(begin) {
					loadDishses(position,menu,convertView);
				}
			}
			return convertView;
		} 
    	
    }
}

