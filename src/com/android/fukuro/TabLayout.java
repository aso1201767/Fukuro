package com.android.fukuro;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class TabLayout extends Activity{
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_main);
	    
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    int color = R.color.action_bar_background;
	    Drawable backgroundDrawable = getApplicationContext().getResources().getDrawable(color);
	    actionBar.setBackgroundDrawable(backgroundDrawable);
	 

	 // タブにリスナーを追加する
        createTab(actionBar, new MainTabListener<Fragment1>(this, Fragment1.class),R.layout.tab_mypage, R.id.tab_mypage_title, "MyPage");
        createTab(actionBar, new MainTabListener<Fragment2>(this, Fragment2.class),R.layout.tab_wear, R.id.tab_wear_title,"WEAR");
        createTab(actionBar, new MainTabListener<FirstFragment>(this, FirstFragment.class),R.layout.tab_style, R.id.tab_style_title,"STYLE");
        createTab(actionBar, new MainTabListener<FirstFragment>(this, FirstFragment.class),R.layout.tab_photo, R.id.tab_photo_title,"PHOTO");
        // デフォルトの状態選択を変更する
        actionBar.setSelectedNavigationItem(0);
        
        
	    }
	
	private void createTab(ActionBar actionBar,
			MainTabListener<?> listener,int tabId, int tabTitleId, String title) {
		// TODO 自動生成されたメソッド・スタブ
		ActionBar.Tab tab = actionBar.newTab();
		tab.setCustomView(tabId);
        tab.setTabListener(listener);

        actionBar.addTab(tab); // (3)
        TextView textView = (TextView) findViewById(tabTitleId);
        textView.setText(title);
	}

	void move() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
	    
	public static class MainTabListener<T extends Fragment> implements ActionBar.TabListener {
	    
	    private Fragment _fragment;
	    private final Activity _activity;
	    private final Class<T> _cls;
	    
	    public MainTabListener(
		    Activity activity, Class<T> cls){
		    this._activity = activity;
		    this._cls = cls;
	    }
	      
	    @Override
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    	_fragment = Fragment.instantiate(_activity, _cls.getName());
            ft.replace(android.R.id.content, _fragment); // (4)
	    }

	    @Override
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
		    if(_fragment == null){
		    	_fragment = Fragment.instantiate(_activity, _cls.getName());
		    	ft.add(android.R.id.content, _fragment);
		    }else{
		    	ft.attach(_fragment);
		    }    
	    }

	    @Override
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	    	if(_fragment != null){
	    		ft.detach(_fragment);
	    	}
	    }
	}
}
