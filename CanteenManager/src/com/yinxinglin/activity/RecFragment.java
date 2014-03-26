package com.yinxinglin.activity;

import java.util.ArrayList;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.object.Dish;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public final class RecFragment extends Fragment {

    private ListView dishes_list = null;
    private ArrayList<Bitmap> bitmaps = null;
    private ArrayList<View> dishes = null;
    private View loadMore = null;
    private MyAdapter dashesListAdapter = null;
    private LayoutInflater inflater = null;
    private boolean isInit = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmaps = new ArrayList<Bitmap>();
        dishes = new ArrayList<View>();
    }

    @Override
	public void onDestroy() {
    	for(Bitmap temp:bitmaps) {
    		temp.recycle();
    	}
		super.onDestroy();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		this.inflater = inflater;	
		View v = inflater.inflate(R.layout.menu_rec, container,false);
		dishes_list = (ListView)v.findViewById(R.id.menu_face);
		dashesListAdapter = new MyAdapter();
		dishes_list.setAdapter(dashesListAdapter);
		dishes_list.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
				if(position == dishes.size()-1) {
					new Thread(new DishGetter()).start(); 
				}else{
					Intent intent = new Intent();
					intent.setClass(getActivity(), DetailDescriptionFace.class);
					startActivity(intent);
				}
			}
		});
		
    	if(isInit) {
    		loadMore = inflater.inflate(R.layout.menu_rec_loadmore, null);
    		new Thread(new DishGetter()).start();
    		isInit = false;
    	}
    	return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private class MyAdapter extends BaseAdapter {
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dishes.size();
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
			return position==dishes.size()-1?loadMore:dishes.get(position);
		} 
    }
    
    private class DishGetter implements Runnable {

		@Override
		public void run() {
			DishesUtils.getDishes(dishes.size(), "uestc", new DoSomeThing() {
				
				@Override
				public void doit(boolean result, String info, Object obj) {
					TextView tv;
					Dish dish = (Dish)obj;
					bitmaps.add(dish.getPhoto());
					View v = inflater.inflate(R.layout.rec_cell, null);
				    ImageView iv = (ImageView)v.findViewById(R.id.rec_dish_pic);
				    iv.setImageBitmap(dish.getPhoto());
			    	tv = (TextView)v.findViewById(R.id.rec_dish_name);
			    	tv.setText(dish.getName());
			    	tv = (TextView)v.findViewById(R.id.rec_dish_zan);
			    	tv.setText(""+dish.getGood());
			    	tv = (TextView)v.findViewById(R.id.rec_dish_price);
			    	tv.setText(""+dish.getPrice());						    	
					dishes.add(v);
					dashesListAdapter.notifyDataSetChanged();	
				}
			});
			
		}
    	
    }
}

