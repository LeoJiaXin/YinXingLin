package com.yinxinglin.more;

import java.util.ArrayList;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.utils.HttpUtils;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.app.Activity;

public class SquareFace extends Activity {

	private ListView square_list = null;
	private SquareListAdapter com_adapter = null;
	private View loadMore = null;
	private ArrayList<View> commets = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HttpUtils.init(new Handler());
		setContentView(R.layout.activity_square_face);
		loadMore = getLayoutInflater().inflate(R.layout.menu_rec_loadmore, null);
		commets = new ArrayList<View>();
		square_list = (ListView)findViewById(R.id.square_commets);
		com_adapter = new SquareListAdapter();
		square_list.setAdapter(com_adapter);
		
	}
	
	private class SquareListAdapter extends BaseAdapter {

		
		public SquareListAdapter() {
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return commets.size();
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
			// TODO Auto-generated method stub
			return position==commets.size()-1?loadMore:commets.get(position);
		}
		
	}
	
	private class SquareCommetGetter implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
