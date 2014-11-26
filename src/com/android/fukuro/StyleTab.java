package com.android.fukuro;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StyleTab extends Fragment {
	private TabLayout tablayout;

	@Override
	  public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
				Button btnMove = (Button) view.findViewById(R.id.btn_picture_edit);
		        btnMove.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	tablayout.move(Uplist.class,null,null);
		            }
		        });
	    return view;
	  }
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tablayout = (TabLayout) activity;
    }

}
