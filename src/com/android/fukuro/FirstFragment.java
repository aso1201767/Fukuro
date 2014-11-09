package com.android.fukuro;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

public class FirstFragment extends Fragment{
	 private TabLayout parent;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.first_fragment, container, false);
	        Button btnMove = (Button) view.findViewById(R.id.next);
	        btnMove.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                parent.move();
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
