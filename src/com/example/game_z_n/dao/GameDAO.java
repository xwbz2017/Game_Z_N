package com.example.game_z_n.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.game_z_n.entity.IMax;

public class GameDAO {
	private MyDAO dao;

	public GameDAO(Context context) {
		dao = new MyDAO(context);
	}

	private int getMaxID() {
		SQLiteDatabase db = dao.getReadableDatabase();
		Cursor cursor = db.query("imax", new String[] { "max(_id)" }, null,
				null, null, null, null);
		if (cursor.moveToNext()) {
			return cursor.getInt(0);
		}
		return 0;
	}

	public boolean add(int n, int sum, long tm) {
		SQLiteDatabase db = dao.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", getMaxID()+1);
		values.put("n", n);
		values.put("sum", sum);
		values.put("tm", tm);
		long rowid = db.insert("imax", null, values);// 返回新添加的行号，与主键无关
		return rowid >= 0;
	}

	public List<IMax> getAll() {
		List<IMax> list = null;
		SQLiteDatabase db = dao.getReadableDatabase();
		Cursor cursor = db.query("imax", null, null, null, null, null, "sum desc");
		while (cursor.moveToNext()) {
			if (list == null)
				list = new ArrayList<IMax>();
			list.add(new IMax(cursor.getInt(0), cursor.getInt(1), cursor
					.getInt(2), cursor.getLong(3)));
		}
		return list;
	}
}
