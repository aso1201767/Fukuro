package com.android.fukuro;

import com.android.fukuro.ViewFlipperSample.MyView;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView;

public class SecondFragment extends Fragment{
	private TabLayout parent;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.second_fragment, container, false);
	        view=parent.se();
	        return view;
	    }
}
