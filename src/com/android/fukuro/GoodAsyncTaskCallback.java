package com.android.fukuro;

public interface GoodAsyncTaskCallback {
	  /**
	   * いいねが成功した時に呼ばれるメソッド
	   */
	  void onSuccessGood(String filename);

	  /**
	   * いいねが失敗した時に呼ばれるメソッド
	   */
	  void onFailedGood();
}
