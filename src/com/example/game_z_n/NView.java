package com.example.game_z_n;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.game_z_n.dao.GameDAO;

public class NView extends View {
	public static int[] mcolor;
	static {
		mcolor = new int[] { Color.rgb(227, 23, 13), Color.rgb(255, 128, 0),
				Color.rgb(255, 215, 0), Color.rgb(0, 255, 127),
				Color.rgb(0, 201, 87), Color.rgb(106, 90, 205),
				Color.rgb(51, 161, 201), Color.rgb(3, 168, 158),
				Color.rgb(221, 160, 221), Color.rgb(156, 102, 31),
				Color.rgb(218, 165, 105), Color.rgb(135, 38, 87) };
	}
	private int n;
	private int width;
	private int height;
	private long time;
	private Context context;
	private Paint p;
	private int v;
	private K map[];
	private int step;
	private GameDAO dao;

	/*
	 * 内部类 用于保存颜色和数字信息
	 */
	private class K {
		int id;
		int color;

		private K(int id, int color) {
			this.id = id;
			this.color = color;
		}
	}

	public NView(Context context, int n, int width, int height) {
		super(context);
		this.context = context;
		if (n < 3 || n > 7)
			n = 3;
		this.n = n;
		this.width = width;
		this.height = height;
		{
			p = new Paint();
			int w = width / n;
			int h = height / n;
			v = w > h ? h : w;
		}
		dao = new GameDAO(context);
		begin();
	}

	/*
	 * 初始化
	 */
	private void begin() {
		time = System.currentTimeMillis();
		step = 0;
		map = new K[n * n];
		for (int i = 0; i < map.length; i++) {
			if (i == map.length - 1)
				map[i] = new K(i + 1, Color.rgb(245, 245, 245));
			else
				map[i] = new K(i + 1, mcolor[i % mcolor.length]);
		}
		randomK();
	}

	/*
	 * 随机乱序数字（算法不行）
	 */
	private void randomK() {
		List<K> list = new ArrayList<K>();
		for (int i = 0; i < map.length - 1; i++) {
			int r = 0;
			do {
				r = (int) (Math.random() * (map.length - 1));
			} while (map[r].id == -1);
			K kk = new K(map[r].id, map[r].color);
			list.add(kk);
			map[r].id = -1;
		}
		list.add(new K(map[map.length - 1].id, map[map.length - 1].color));
		for (int i = 0; i < list.size(); i++) {
			map[i] = list.get(i);
		}
		/* 如果生成的map随机排列的逆序数为奇数(因为目标排列的逆序数为0，当map和目标逆序数相同时，才能完成游戏)，则无法完成游戏 */
		int isum = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = i + 1; j < map.length; j++) {
				if (map[i].id > map[j].id)
					isum++;
			}
		}
		// Toast.makeText(context, "sum=" + isum, 0).show();
		if (isum % 2 != 0)
			begin();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.rgb(128, 170, 171));
		int mLen = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int x = j * v;
				int y = i * v;
				if (mLen < map.length) {
					p.setColor(map[mLen].color);
					canvas.drawRect(new Rect(x, y, x + v, y + v), p);
					// Toast.makeText(context, map[mLen].color + ".", 0).show();
					p.setColor(Color.rgb(245, 245, 245));
					p.setTextSize((13 - n) * (10 - n));
					p.setTextAlign(Align.CENTER);
					// 获取字体的信息，主要是为了获取高度
					FontMetrics fm = p.getFontMetrics();
					/*
					 * 复制http://blog.csdn.net/sirnuo/article/details/21165665 。
					 * canvas.drawText("www.jcodecraeer.com", x, y, paint);
					 * x和y参数是指定字符串中心的坐标吗？还是左上角的坐标？这个问题的直观印象应该是左上角的坐标
					 * ，但是安卓的处理有点另类，我都怀疑安卓的设计者是不是脑壳有问题了。
					 * x默认是‘www.jcodecraeer.com
					 * ’这个字符串的左边在屏幕的位置，如果设置了paint.setTextAlign
					 * (Paint.Align.CENTER);那就是字符的中心，y是指定这个字符baseline在屏幕上的位置。
					 */
					// canvas.drawText(map[mLen++].id + "", x + v / 2f, y + v /
					// 5f * 4f-Math.abs((13-n)*(10-n)), p);
					// double ceil(double a) 返回最小的（最接近负无穷大）double
					// 值，该值大于等于参数，并等于某个整数。
					// 计算文字baseline
					// float textBaseY = height - (height - fontHeight)/2 -
					// fontMetrics.bottom;
					// 计算文字高度
					float textheight = fm.bottom - fm.top;
					canvas.drawText(map[mLen++].id + "", x + v / 2f, y
							+ (v - (v - textheight) / 2 - fm.bottom), p);
				}
			}
		}
		p.setTextSize(40);
		FontMetrics fm2 = p.getFontMetrics();
		float fm2height = height - v * n;
		float text2height = fm2.bottom - fm2.top;
		canvas.drawText(step + "", width / 2, height
				- (fm2height - (fm2height - text2height) / 2 - fm2.bottom), p);
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					int x = (int) (arg1.getY() / v);
					int y = (int) (arg1.getX() / v);
					if (x >= 0 && x < n && y >= 0 && y < n) {
						move(x, y);
					}
				}
				return true;
			}
		});
		// Toast.makeText(context, time+".", 0).show();
	}

	/*
	 * 移动方法
	 * 
	 * 往空的位置移动
	 */
	private void move(int x, int y) {
		if (x >= 0 && x < n && y >= 0 && y < n) {
			boolean ismove = false;
			if (y + 1 < n && map[x * n + y + 1].id == n * n) {
				K temp = map[x * n + y];
				map[x * n + y] = map[x * n + y + 1];
				map[x * n + y + 1] = temp;
				ismove = true;
			} else if (x + 1 < n && map[(x + 1) * n + y].id == n * n) {
				K temp = map[x * n + y];
				map[x * n + y] = map[(x + 1) * n + y];
				map[(x + 1) * n + y] = temp;
				ismove = true;
			} else if (y - 1 >= 0 && map[x * n + y - 1].id == n * n) {
				K temp = map[x * n + y];
				map[x * n + y] = map[x * n + y - 1];
				map[x * n + y - 1] = temp;
				ismove = true;
			} else if (x - 1 >= 0 && map[(x - 1) * n + y].id == n * n) {
				K temp = map[x * n + y];
				map[x * n + y] = map[(x - 1) * n + y];
				map[(x - 1) * n + y] = temp;
				ismove = true;
			}
			if (ismove) {
				step++;
				boolean gameover = true;
				for (int i = 0; i < map.length; i++) {
					if (map[i].id != i + 1) {
						gameover = false;
						break;
					}
				}
				if (gameover) {
					long now = System.currentTimeMillis();
					time = (now - time) / 1000;
					dao.add(n, step, time);
					StringBuilder show = new StringBuilder();
					if (time / (60 * 60) > 0) {
						show.append(time / 60 * 60 + "时");
						time %= 60 * 60;
					}
					if (time / 60 > 0) {
						show.append(time / 60 + "分");
						time %= 60;
					}
					show.append(time + "秒");
					AlertDialog.Builder tishi = new Builder(context);
					tishi.setTitle("完成！");
					tishi.setMessage("用了" + step + "步，" + show);
					tishi.setCancelable(false);
					tishi.setPositiveButton("再来一次",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									begin();
									invalidate(new Rect(0, 0, width, height));
								}
							});
					tishi.setNegativeButton("不玩了",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									System.exit(0);
								}
							});
					tishi.create().show();
				}
			}
			invalidate(new Rect(0, 0, width, height));
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	@Override
	public void setWillNotDraw(boolean willNotDraw) {
		// TODO Auto-generated method stub
		super.setWillNotDraw(willNotDraw);
	}

}
