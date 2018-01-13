package com.example.game_z_n;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static int n = 3;
	private MyAdapter ma;
	private AlertDialog builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) this.findViewById(R.id.listView1);
		ma = new MyAdapter(this);
		lv.setAdapter(ma);
		Button btnxj = (Button) findViewById(R.id.btnxj);
		Button btnsz = (Button) findViewById(R.id.btnsz);
		Button btntc = (Button) findViewById(R.id.btntc);
		btnxj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,
						GameActivity.class);
				intent.putExtra("n", n);
				startActivity(intent);
			}
		});
		btnsz.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				openOptionsMenu();
			}
		});
		btntc.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				tishi();
			}
		});
//		AlertDialog bb = new Builder(this).create();
//		LinearLayout ll = new LinearLayout(this);
//		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,200);
//		NShowView nsv = new NShowView(this);
//		ll.addView(nsv , params);
//		bb.setView(ll);
//		bb.show();
	}

	private void tishi() {
		final AlertDialog builder = new Builder(MainActivity.this).create();
		builder.setCancelable(false);
		builder.setTitle("确定要退出吗？");
		builder.setButton("等等", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				builder.cancel();
			}
		});
		builder.setButton2("对", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				finish();
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

	@Override
	protected void onResume() {
		super.onResume();
		ma.notifyDataSetChanged();
	}

	@Override
	public void openOptionsMenu() {
		super.openOptionsMenu();
		builder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.activity_setting, null);
		builder = new AlertDialog.Builder(this).create();
		builder.setView(view);
		final RatingBar rb = (RatingBar) view.findViewById(R.id.ndratingBar);
		Button btn1 = (Button) view.findViewById(R.id.ndbtn1);
		Button btn2 = (Button) view.findViewById(R.id.ndbtn2);
		rb.setRating(n);
		rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				Toast.makeText(MainActivity.this, "当前：" + (int) arg1, 0).show();
			}
		});
		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this,
						"修改为：" + (n = (int) rb.getRating()), 0).show();
				builder.dismiss();
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				builder.dismiss();
			}
		});
		return true;
	}
}
