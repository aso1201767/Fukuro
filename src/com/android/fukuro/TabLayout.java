package com.android.fukuro;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class TabLayout extends Activity{
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
    private ArrayList<String> coordename = new ArrayList<String>();
    /** Called when the activity is first created. */
    private String topimage; 
    private Intent intent = null;
    private Intent i = null;
    private File picFile;
	private String picname = null;
	static final int REQUEST_CAPTURE_IMAGE = 100;
	MainTabListener<?> second;
	private List<String> imgList = new ArrayList<String>();
	private List<Integer> filename = new ArrayList<Integer>();
	private List<String> favoList = new ArrayList<String>();
	private List<String> itemid = new ArrayList<String>();
	private List<String> memoList = new ArrayList<String>();
	private List<String> nameList = new ArrayList<String>();
	private List<String> categoryList = new ArrayList<String>();
	private static final int ITEM1 = Menu.FIRST;
	private static final String CUTflg = "CUTflg1";
	private static final String PITflg = "PITflg1";
	Fragment MylistFragment;
	Fragment ItemMylistFragment;
	boolean mylist_flg =false;
	boolean itemmylist_flg =false;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_main);
	    
	  
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    int color = R.color.actionbar_background;
	    Drawable backgroundDrawable = getApplicationContext().getResources().getDrawable(color);
	    actionBar.setBackgroundDrawable(backgroundDrawable);
	    
	    second = new MainTabListener<MenuFragment>(this, MenuFragment.class);
	  
	 // タブにリスナーを追加する
        createTab(actionBar, new MainTabListener<MyPage>(this, MyPage.class),R.layout.tab_mypage, R.id.tab_mypage_title, "MyPage");
        createTab(actionBar, second,R.layout.tab_wear, R.id.tab_wear_title,"WEAR");
        createTab(actionBar, new MainTabListener<RankingMenu>(this, RankingMenu.class),R.layout.tab_style, R.id.tab_style_title,"STYLE");
        createTab(actionBar, new MainTabListener<Camera>(this, Camera.class),R.layout.tab_photo, R.id.tab_photo_title,"PHOTO");
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

	void move(Class<?> move,String id,Integer value) {
		Log.d("move","move="+move);
        intent = new Intent(this, move);
        if(id!=null){
        	intent.putExtra(id, value);
        }
        Log.d("move","move2");
        startActivity(intent);
        Log.d("move","move3");
    }
	void move1(Class<?> move,String id,String value) {
        intent = new Intent(this, move);
        intent.putExtra(id, value);
        startActivity(intent);
    }
//	void move2(){
//		intent = new Intent(getApplicationContext(),Uplist.class);
//		startActivity(intent);
//	}
	void camera(){
//		//インテントに、この画面と、遷移する別の画面を指定する
//		intent = new Intent(this, CameraActivity.class);
//
//		//インテントで指定した別の画面に遷移する
//		startActivity(intent);
		

		picname = getPicFileName();
		picFile = new File(
			Environment.getExternalStorageDirectory() + "/Item",
			picname);
		
		SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(defaultSharedPreferences.getBoolean(PITflg,true)){
        	defaultSharedPreferences.edit().putBoolean(PITflg,false).apply();
                Log.d("テスト10","一回目");
                i=new Intent(this,petutorial.class);
        }else{
            //二回目以降の処理
        	Log.d("テスト10","２回目");
        	i = new Intent(getApplicationContext(),picture_edit.class);
        }
		
         i.putExtra("Fpath", picFile.toString());
    	 i.putExtra("Fname", picname);
    	 i.putExtra("previousview", "camera");
		 startActivity(i);

		 intent = new Intent(
			MediaStore.ACTION_IMAGE_CAPTURE);


		intent.putExtra(
			MediaStore.EXTRA_OUTPUT,
			Uri.fromFile(picFile));

		startActivityForResult(
			intent,
			REQUEST_CAPTURE_IMAGE);
        Log.e("test",picFile.toString());
		Log.e("test",getPicFileName());
		Log.e("test",picname);
	}
	
	void mylist(){
//		intent = new Intent(this, Mylist.class);
//		startActivity(intent);
		Fragment f=second.fragment();
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(f);        
		MylistFragment = new Mylist();
		fragmentTransaction.add(android.R.id.content, MylistFragment);
		fragmentTransaction.commit();
		mylist_flg=true;
	}
	void itemMylist(){
//		intent = new Intent(this, Mylist.class);
//		startActivity(intent);
		Fragment f=second.fragment();
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(f);        
		ItemMylistFragment = new ItemMylist();
		fragmentTransaction.add(android.R.id.content, ItemMylistFragment);
		fragmentTransaction.commit();
		itemmylist_flg=true;
	}
	void unselected_mylist(){
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(MylistFragment);   
		fragmentTransaction.commit();
		mylist_flg=false;
	}
	
	void unselected_itemmylist(){
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(ItemMylistFragment);   
		fragmentTransaction.commit();
		itemmylist_flg=false;
	}
	
	protected String getPicFileName(){
		Calendar c = Calendar.getInstance();
		String s = c.get(Calendar.YEAR)
			+ "_" + (c.get(Calendar.MONTH)+1)
			+ "_" + c.get(Calendar.DAY_OF_MONTH)
			+ "_" + c.get(Calendar.HOUR_OF_DAY)
			+ "_" + c.get(Calendar.MINUTE)
			+ "_" + c.get(Calendar.SECOND)
			+ ".png";
		return s;
	}
	
	class ReturnValue {
		public List<String> rImgList = new ArrayList<String>();
		public List<Integer> rFilename = new ArrayList<Integer>();
		public List<String> rFavoList = new ArrayList<String>();
	}
	
	public ReturnValue mylist_db() {
		imgList=new ArrayList<String>();
		filename=new ArrayList<Integer>();
		favoList=new ArrayList<String>();
		db = dbHelper.getReadableDatabase();
		
		String destPath = null;

		Cursor cr = db.rawQuery("SELECT mylist_id,mylist,maked,favorite FROM Mylist ORDER BY favorite DESC, maked DESC", null);
		cr.moveToFirst();

		//プラスボタン
		imgList.add("plus"); //リソースから画像をとるのでダミーのパスを指定
		filename.add(0,0);
		favoList.add("plus");
		//プラスボタン画像をfileListに挿入

		for(int cnt = 1; cnt <= cr.getCount(); cnt++){
//			destPath = "/data/data/"+this.getPackageName()+"/Item/" + cr.getString(1);
			destPath = Environment.getExternalStorageDirectory() +"/Item/" + cr.getString(1);
			System.out.println(cr.getString(1));

			// List<String> imgList にはファイルのパスを入れる
			imgList.add(destPath);
			filename.add(cnt,cr.getInt(0));
			favoList.add(cr.getString(3));
			cr.moveToNext();
		}
		
		
		ReturnValue value = new ReturnValue();
		value.rImgList = imgList;
		value.rFilename = filename;
		value.rFavoList=favoList;
		return value;
	}
	
	public void first_camera(){
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(defaultSharedPreferences.getBoolean(CUTflg,true)){
        	defaultSharedPreferences.edit().putBoolean(CUTflg,false).apply();
                Log.d("テスト10","一回目");
                intent=new Intent(this,CUtutorial.class);
                startActivity(intent);
        }else{
                //二回目以降の処理
        	Log.d("テスト10","２回目");
        	camera();
        }
	}
	
	class ReturnValue_item {
		public List<String> rImgList = new ArrayList<String>();
		public List<String> ritemid = new ArrayList<String>();
		public List<String> rmemoList = new ArrayList<String>();
		public List<String> nameList = new ArrayList<String>();
		public List<String> rcategoryList = new ArrayList<String>();
	}
	
	public ReturnValue_item itemmylist_db() {
		imgList=new ArrayList<String>();
		itemid=new ArrayList<String>();
		memoList=new ArrayList<String>();
		nameList=new ArrayList<String>();
		categoryList = new ArrayList<String>();
		db = dbHelper.getReadableDatabase();
		 String destPath = null;

		 Cursor cr = db.rawQuery("SELECT item, item_id, memo,category_id FROM Item WHERE NOT category_id = \"7\" ORDER BY category_id DESC", null);
		 cr.moveToFirst();
			
		 for(int cnt = 0; cnt < cr.getCount(); cnt++){
//		 	destPath = "/data/data/"+this.getPackageName()+"/Item/" + cr.getString(0);
		 	destPath = Environment.getExternalStorageDirectory() +"/Item/" + cr.getString(0);

		 	// List<String> imgList にはファイルのパスを入れる
		 	imgList.add(destPath);
		 	itemid.add(cr.getString(1));
		 	Log.d("","2"+cr.getInt(1));
		 	memoList.add(cr.getString(2));
		 	nameList.add(cr.getString(0));
		 	categoryList.add(cr.getString(3));
		 	cr.moveToNext();
		 }
		
		ReturnValue_item value = new ReturnValue_item();
		value.rImgList = imgList;
		value.ritemid=itemid;
		value.rmemoList=memoList;
		value.rcategoryList=categoryList;
		value.nameList=nameList;
		Log.d("tab","ritemid"+itemid);
		return value;
	}
	
	public GridView setgrid(){
		return  (GridView) findViewById(R.id.gridview);
	}
	public ImageButton setImageButton(int imageButton){
		return (ImageButton) findViewById(imageButton);
	}
	
	public Context getC(){
		return this.getApplicationContext();
	}
	
	public String mypage_topimage(){
		db = dbHelper.getReadableDatabase();
        
        String sql01 = "select item from Item where item_id = (select MAX(item_id) from Item where category_id = 7)";
        Cursor c1 = db.rawQuery(sql01, null);
        c1.moveToFirst();
        for (int i = 0; i < c1.getCount() ; i++) {
            topimage = c1.getString(0);
            Log.d("test","name=" + coordename);
            c1.moveToNext();
        }
		return topimage;
	}
	
	public ArrayList<String> mypage_db(){
		
		db = dbHelper.getReadableDatabase();
		coordename = new ArrayList<String>();
        String sql02 = "select mylist from Mylist order by maked DESC";
        Cursor c2 = db.rawQuery(sql02, null);
        c2.moveToFirst();
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
	    
	public class MainTabListener<T extends Fragment> implements ActionBar.TabListener {
	    
	    private Fragment _fragment;
	    private final Activity _activity;
	    private final Class<T> _cls;
	    
	    public MainTabListener(
		    Activity activity, Class<T> cls){
		    this._activity = activity;
		    this._cls = cls;
	    }
	    Fragment fragment(){
	    	return _fragment;
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
	    	if(mylist_flg){
	    		unselected_mylist();
	    	}
	    	if(itemmylist_flg){
	    		unselected_itemmylist();
	    	}
	    }
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_BACK:
	            // ダイアログ表示など特定の処理を行いたい場合はここに記述
	            // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
	            return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Disable Back key
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.btn_up){
			intent=new Intent(this,Uplist.class);
			startActivity(intent);
            return true;  
		}
		return super.onOptionsItemSelected(item);
	}
}
