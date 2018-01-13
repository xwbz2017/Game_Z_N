package com.example.game_z_n;

import java.util.List;

import android.R.drawable;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.game_z_n.dao.GameDAO;
import com.example.game_z_n.entity.IMax;

public class MyAdapter extends BaseAdapter {
	private GameDAO dao;
	private List<IMax> list;
	private Context context;

	public MyAdapter(Context context) {
		dao = new GameDAO(context);
		list = dao.getAll();
		this.context = context;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (arg0 < 0 || arg0 >= list.size())
			return null;
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.activity_game, null);
		TextView tv = (TextView) view.findViewById(R.id.showtv);
		int dx = list.size() - 1 - arg0;
		int color;
		switch (arg0) {
		case 0:
			color = Color.rgb(255, 215, 0);
			break;
		case 1:
			color = Color.rgb(245, 245, 245);
			break;
		case 2:
			color = Color.rgb(244, 164, 96);
			break;
		default:
			color = Color.rgb(176, 224, 230);
		}
		tv.setBackgroundColor(color);
		IMax mmm = list.get(dx);
		tv.setText("编号：" + mmm.get_id() + "  级别：" + mmm.getN() + "  完成步数："
				+ mmm.getSum() + "  完成时间：" + mmm.getTm() + "秒");
		return view;
	}

}
