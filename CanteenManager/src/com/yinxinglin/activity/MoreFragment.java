package com.yinxinglin.activity;

import com.yingxinlin.canteenmanager.R;
import com.yingxinlin.more.CallTogetherFace;
import com.yingxinlin.more.PriceComFace;
import com.yingxinlin.more.SquareFace;
import com.yingxinlin.more.TakeAwayFace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MoreFragment extends Fragment{

	private MyAboutDialog aboutDlg = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = inflater.inflate(R.layout.menu_more, container,false);
    	v.findViewById(R.id.more_to_square).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SquareFace.class);
				startActivity(intent);
			}
		});
    	v.findViewById(R.id.more_price_compare).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), PriceComFace.class);
				startActivity(intent);
			}
		});
    	v.findViewById(R.id.more_AA_part).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), CallTogetherFace.class);
				startActivity(intent);				
			}
		});
    	v.findViewById(R.id.more_take_away).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), TakeAwayFace.class);
				startActivity(intent);				
			}
		});
    	v.findViewById(R.id.more_about).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(aboutDlg == null) {
					aboutDlg = new MyAboutDialog();
					aboutDlg.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
					aboutDlg.show(getFragmentManager(), "about");
				}else{
					aboutDlg.show(getChildFragmentManager(), "about");
				}
			}
		});
    	return v;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

class MyAboutDialog extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_about, container);
		v.findViewById(R.id.about_ok).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				
			}
		});
		return v;
	}
}