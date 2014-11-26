package com.android.fukuro;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DragViewListener1 implements OnTouchListener {
	// ドラッグ対象のView
	private ImageView dragView;
	// ドラッグ中に移動量を取得するための変数
	private int oldx1;
	private int oldy1;

	public DragViewListener1(ImageView dragView1) {
		this.dragView = dragView1;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// タッチしている位置取得
		int x1 = (int) event.getRawX();
		int y1 = (int) event.getRawY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// 今回イベントでのView移動先の位置
			int left = dragView.getLeft() + (x1 - oldx1);
			int top = dragView.getTop() + (y1 - oldy1);
			// Viewを移動する
			dragView.layout(left, top, left + dragView.getWidth(), top
					+ dragView.getHeight());
			break;
		}

		// 今回のタッチ位置を保持
		oldx1 = x1;
		oldy1 = y1;
		// イベント処理完了
		return true;
	}


}