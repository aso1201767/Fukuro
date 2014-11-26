package com.android.fukuro;

import java.util.List;

import android.graphics.Bitmap;

public interface DownloadImageTaskCallback {
	  /**
	   * 画像のダウンロードが成功した時に呼ばれるメソッド
	   */
	  void onSuccessDownloadImage(List<Bitmap> bitmaplist ,List<String> filenames);

	  /**
	   * 画像のダウンロードが失敗した時に呼ばれるメソッド
	   */
	  void onFailedDownloadImage();
}
