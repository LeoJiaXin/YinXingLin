package com.yinxinglin.activity;

import java.util.ArrayList;

import com.yingxinlin.canteenmanager.R;
import com.yinxinglin.utils.DishesUtils;
import com.yinxinglin.utils.DoSomeThing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class NewFragment extends Fragment{

	private static final String[] MAIN_TAB = {"食堂" ,"种类" ,"其它1","其它2"};
	private static final String[] MAIN_TYPE = {"肉类","水产","蛋","蔬菜","菌菇","水果","豆制品","其他"};
	
	private ExpandableListView totallist = null;
	private MyExListAdapter myExAdapter = null;
	private boolean canteenOpened = true;
	private ArrayList<String> canteenName = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canteenOpened = true;
		canteenName = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = inflater.inflate(R.layout.menu_new, container,false);
    	totallist = (ExpandableListView)v.findViewById(R.id.new_total_list);
    	totallist.setGroupIndicator(null);
    	myExAdapter = new MyExListAdapter(inflater);
    	totallist.setAdapter(myExAdapter);
    	totallist.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				return false;
			}
		});
    	totallist.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
						return false;
			}
		});
    	totallist.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				if(groupPosition == 0 && canteenOpened) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							DishesUtils.getCanteenName("uestc", new DoSomeThing() {
								
								@Override
								public void doit(boolean result, String info, Object obj) {
									canteenName.add(info);
									myExAdapter.notifyDataSetChanged();
								}
							});
							
						}
					}).start();
					canteenOpened = false;
				}
			}
		});
    	totallist.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				
			}
		});
    	return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
   
    class MyExListAdapter extends BaseExpandableListAdapter {
    	
    	private LayoutInflater inflater = null;
    	
    	public MyExListAdapter(LayoutInflater inflater) {
    		this.inflater = inflater;
    	}
    	
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.new_list, null);
				switch(groupPosition) {
				case 0:
					((TextView)convertView.findViewById(R.id.new_list_content)).setText(canteenName.get(childPosition));
					break;
					default:break;
				}
			}else{
				switch(groupPosition) {
				case 0:
					((TextView)convertView.findViewById(R.id.new_list_content)).setText(canteenName.get(childPosition));
					break;
					default:break;
				}
			}
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			if(groupPosition == 0) {
				return canteenName.size();
			}
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return MAIN_TAB.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.new_tab, null);
				((TextView)convertView.findViewById(R.id.new_tab_cell)).setText(MAIN_TAB[groupPosition]);
			}else{
				
			}
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}
