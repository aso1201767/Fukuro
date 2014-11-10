package com.android.fukuro;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import com.android.fukuro.MyPage.MyHandler;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class TabLayout extends Activity{
	private static final String TAG = "myTag";
	private int selectedIndex = 0;
	private Timer timer;
	private MyHandler handler;
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
    private ArrayList<String> coordename = new ArrayList<String>();
    /** Called when the activity is first created. */
    private boolean firstflg = false;
    private String topimage; 
    private File dir = new File("/data/data/com.android.fukuro/Item");
	
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
        createTab(actionBar, new MainTabListener<MyPage>(this, MyPage.class),R.layout.tab_mypage, R.id.tab_mypage_title, "MyPage");
        createTab(actionBar, new MainTabListener<Fragment2>(this, Fragment2.class),R.layout.tab_wear, R.id.tab_wear_title,"WEAR");
        createTab(actionBar, new MainTabListener<SecondFragment>(this, SecondFragment.class),R.layout.tab_style, R.id.tab_style_title,"STYLE");
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
	public String mypage_topimage(){
		db = dbHelper.getReadableDatabase();
        
        String sql01 = "select item from Item where item_id = (select MAX(item_id) from Item)";
        Cursor c1 = db.rawQuery(sql01, null);
       // Log.d("test","sql=" + sql); //検査用
       // Log.d("test","cursor" + c.getCount()); //検査用
        c1.moveToFirst();
       // Log.d("test","cursor" + c.getCount()); //検査用

        for (int i = 0; i < c1.getCount() ; i++) {
            topimage = c1.getString(0);
            Log.d("test","name=" + coordename);
            c1.moveToNext();
        }
		return topimage;
	}
	
	
	
	public ArrayList<String> mypage_db(){
		
		db = dbHelper.getReadableDatabase();
        
        String sql02 = "select thambnail from Mylist order by maked DESC";
        Cursor c2 = db.rawQuery(sql02, null);
       // Log.d("test","sql=" + sql); //検査用
       // Log.d("test","cursor" + c.getCount()); //検査用
        c2.moveToFirst();
       // Log.d("test","cursor" + c.getCount()); //検査用

        for (int i = 0; i < c2.getCount(); i++) {
            coordename.add(c2.getString(0));
            Log.d("test","name=" + coordename);
            c2.moveToNext();
        }
        return coordename;
	}
	
	public void setimage(int imageView,Bitmap bm){
		((ImageView)findViewById(imageView)).setImageBitmap(bm); 
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
	    		_fragment.onStop();
	    		ft.detach(_fragment);
	    	}
	    }
	}
}
