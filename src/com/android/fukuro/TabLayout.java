package com.android.fukuro;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import com.android.fukuro.MyPage.MyHandler;
import com.android.fukuro.ViewFlipperSample.MyView;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.FrameLayout;
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
    
 // タッチの状態管理
 		private static final int TOUCH_NONE   = 0;
 		private static final int TOUCH_SINGLE = 1;
 		private static final int TOUCH_MULTI  = 2;
 		private int touchMode = TOUCH_NONE;
 		// 画像処理
 		private Matrix baseMatrix = new Matrix(); // タッチダウン時の画像保存用
 		private Matrix imgMatrix = new Matrix(); //　画像変換用
 		private PointF po0 = new PointF();   // 移動の開始点
	
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
	ImageView se(){
		MyView view = new MyView(this);
		return view;
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
	class MyView extends ImageView {
		private ScaleGestureDetector gesDetect = null;
	  
		public MyView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			setImageResource(R.drawable.cutting_on);
			setScaleType(ImageView.ScaleType.MATRIX);
			gesDetect = new ScaleGestureDetector(context, onScaleGestureListener);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int count = event.getPointerCount();
	  
			// 移動
			switch(action) {
			case MotionEvent.ACTION_DOWN:
				if(touchMode == TOUCH_NONE && count == 1) {
					Log.v("touch", "DOWN");
					po0.set(event.getX(), event.getY());
					baseMatrix.set(imgMatrix);
					touchMode = TOUCH_SINGLE;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(touchMode == TOUCH_SINGLE) {
					Log.v("touch", "MOVE");
					// 移動処理
					imgMatrix.set(baseMatrix);
					imgMatrix.postTranslate(event.getX() - po0.x, event.getY() - po0.y);
				}
				break;
			case MotionEvent.ACTION_UP:
				if(touchMode == TOUCH_SINGLE) {
					Log.v("touch", "UP");
					touchMode = TOUCH_NONE;
				}
				break;
			}
			if(count >= 2) {
				gesDetect.onTouchEvent(event);
			}
			setImageMatrix(imgMatrix);
			return true;
		}
	  
		private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				imgMatrix.set(baseMatrix);
				imgMatrix.postScale(detector.getScaleFactor(), detector.getScaleFactor(),
			      detector.getFocusX(), detector.getFocusY());
				return super.onScale(detector);
			}
		
			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				Log.v("touch", "onScaleBegin");
				baseMatrix.set(imgMatrix);
				touchMode = TOUCH_MULTI;
				return super.onScaleBegin(detector);
			}
		
			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				Log.v("touch", "onScaleEnd");
				touchMode = TOUCH_NONE;
				super.onScaleEnd(detector);
			}
			
		};
	}

}
