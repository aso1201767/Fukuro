package com.android.fukuro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FirstActivity extends FragmentActivity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {

	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.first);

	    }

	    void move() {
	        Intent intent = new Intent(this, SecondActivity.class);
	        startActivity(intent);
	    }

}
