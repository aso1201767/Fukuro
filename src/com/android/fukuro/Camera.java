package com.android.fukuro;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Camera extends Fragment{
	 private TabLayout parent;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.camera, container, false);
	        Button btnMove = (Button) view.findViewById(R.id.next);
	        btnMove.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	parent.first_camera();
//	            	parent.camera();
	            }
	        });
	      
	        return view;
	    }
	    
	  //プリファレンス  
	    private SharedPreferences preference;  
	    private Editor editor;  
	   
//	    @Override  
//	    public void onCreate(Bundle savedInstanceState) {  
//	        super.onCreate(savedInstanceState);  
//	        setContentView(R.layout.main);  
//	   
//	        //プリファレンスの準備  
//	        preference = getSharedPreferences("Preference Name", MODE_PRIVATE);  
//	        editor = preference.edit();  
//	   
//	        if (preference.getBoolean("Launched", false)==false) {  
//	            //初回起動時の処理  
//	   
//	            //プリファレンスの書き変え  
//	            editor.putBoolean("Launched", true);  
//	            editor.commit();  
//	        } else {  
//	            //二回目以降の処理  
//	   
//	        }  
//	                   
//	    } 
	    

	    @Override
	    public void onAttach(Activity activity) {
	        parent = (TabLayout) activity;
	        super.onAttach(activity);
	    }

}
