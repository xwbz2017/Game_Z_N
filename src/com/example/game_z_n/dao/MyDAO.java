package com.example.game_z_n.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDAO extends SQLiteOpenHelper {

	public MyDAO(Context context) {
		super(context, "xwbz.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("create table imax(_id primary key,n,sum,tm)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
