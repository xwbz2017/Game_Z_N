package com.example.game_z_n;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//强制竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Intent intent = getIntent();
		setContentView(new NView(this, intent.getIntExtra("n", 3),
				dm.widthPixels, dm.heightPixels));
	}

	private void tishi() {
		final AlertDialog builder = new Builder(this).create();
		builder.setCancelable(false);
		builder.setTitle("游戏还在继续，是否退出？");
		builder.setButton("等等", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				builder.cancel();
			}
		});
		builder.setButton2("退出", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				System.exit(0);
			}
		});
		builder.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			tishi();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
