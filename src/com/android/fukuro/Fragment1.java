package com.android.fukuro;

import com.android.fukuro.R.id;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Fragment1 extends Fragment {
	
	@Override
	  public View onCreateView(
	    LayoutInflater inflater,
	    ViewGroup container,
	    Bundle savedInstanceState) {
	   // return inflater.inflate(R.layout.fragment1, container, false);
		 View view = inflater.inflate(R.layout.fragment1, container, false);
	        TextView textView = (TextView) view.findViewById(id.textView1);
	        textView.setText("メッセージ1"); // (6)
	        return view;
	  }

}
