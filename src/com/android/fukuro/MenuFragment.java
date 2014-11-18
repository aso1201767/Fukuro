package com.android.fukuro;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MenuFragment extends Fragment{
	private TabLayout parent;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.menu_fragment, container, false);
	        Button btnMove = (Button) view.findViewById(R.id.menu_btn2);
	        btnMove.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	parent.mylist();
	            }
	        });
	        return view;
	    }
	 
	 @Override
	    public void onAttach(Activity activity) {
	        parent = (TabLayout) activity;
	        super.onAttach(activity);
	    }
}
