package com.android.fukuro;

public interface RegisterUserTaskCallback {
	  /**
	   * ユーザー登録が成功した時に成功した時に呼ばれるメソッド
	   */
	  void onSuccessRegisterUser(String result);

	  /**
	   * ユーザー登録が失敗した時に呼ばれるメソッド
	   */
	  void onFailedRegisterUser();
	  
	  /**
	   * ユーザー登録が失敗した時に呼ばれるメソッド
	   */
	  void onFailedFound(String result);
}
