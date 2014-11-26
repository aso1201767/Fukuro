package com.android.fukuro;

public interface DownloadListTaskCallback {
	  /**
	   * 画像のリストのダウンロードが成功した時に呼ばれるメソッド
	   */
	  void onSuccessDownloadList(String result);

	  /**
	   * 画像のリストのダウンロードが失敗した時に呼ばれるメソッド
	   */
	  void onFailedDownloadList();
	  
	  /**
	   * エラーが発生した時に呼ばれるメソッド
	   */
	  void onFailedFound(String result);
}