package com.android.fukuro;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageTask 
  extends AsyncTask<String, Integer, String> {
  private DownloadImageTaskCallback callback;
  ProgressDialog dialog;
  Context context;
  String result = null;
  Bitmap oBmp = null;
  List<Bitmap> bmList = new ArrayList<Bitmap>();
  List<String> fnList = new ArrayList<String>();
  
  public DownloadImageTask(Context context,DownloadImageTaskCallback callback){
	  this.context = context;
	  this.callback = callback;
  }
  
  @Override
  protected String doInBackground(String... params) {
	  try {
	      HttpClient httpClient = new DefaultHttpClient();
	      HttpPost request = new HttpPost("http://koyoshi.php.xdomain.jp/php/"+params[0]+".php");

	      //Response
	      result = httpClient.execute(request, new ResponseHandler<String>(){
	          @Override
			public String handleResponse(HttpResponse response) throws IOException{
	              switch(response.getStatusLine().getStatusCode()){
	              case HttpStatus.SC_OK:
	                  System.out.println(HttpStatus.SC_OK);
	                  return EntityUtils.toString(response.getEntity(), "UTF-8");
	              case HttpStatus.SC_NOT_FOUND:
	                  System.out.println(HttpStatus.SC_NOT_FOUND);
	                  return "404";
	              default:
	                  System.out.println("unknown");
	                  return "unknown";
	              }
	          }
	      });
	      System.out.println(result);
	      httpClient.getConnectionManager().shutdown();
	      
	      if (result == null) {
	    	  
	      }
	      else{
	    	  JSONObject rootObject = new JSONObject(result);
	  	      JSONArray itemArray = rootObject.getJSONArray("item");
	  	      for (int i = 0; i < itemArray.length(); i++) {
	  	    	  JSONObject jsonObject = itemArray.getJSONObject(i);
	  	    	  // アイテムを追加します
	  	    	  fnList.add(i,jsonObject.getString("ranking_item"));
	  	    	  Log.d("json",jsonObject.getString("ranking_item"));
	  	      }
	  	      
	  	      URL url;
	  	      InputStream istream;
	  	      for(int i = 0; i < fnList.size(); i++){
	  	    	  //画像のURLを直うち
	  	    	  url = new URL("http://koyoshi.php.xdomain.jp/item/"+ fnList.get(i));
	  	    	  //インプットストリームで画像を読み込む
	  	    	  istream = url.openStream();
	  	    	  //読み込んだファイルをビットマップに変換
	  	    	  oBmp = BitmapFactory.decodeStream(istream);
	  	    	  //インプットストリームを閉じる
	  	    	  istream.close();
	  	    	  bmList.add(i,oBmp);
	  	      }
	      }
	  } catch (ClientProtocolException e) {
	      e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } catch (JSONException e) {
		  e.printStackTrace();
	  }
	return result;
  }

  protected void onPostExecute(String result) {
//    if(dialog != null){
//      dialog.dismiss();
//    }
    if (result == null) {
        // エラーをコールバックで返す
        callback.onFailedDownloadImage();
      }else{
        // ダウンロードした画像をコールバックで返す
        callback.onSuccessDownloadImage(bmList,fnList);
    }
  }

  @Override
  protected void onPreExecute() {
//    dialog = new ProgressDialog(this.context);
//    dialog.setTitle("Please wait");
//    dialog.setMessage("Downloading...");
//    dialog.show();
  }  
}
