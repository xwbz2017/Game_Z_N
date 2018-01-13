package com.example.game_z_n;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class NShowView extends View {
	private Paint p;
	private Bitmap bm;
	private int n;
	private int m;
	private float x;
	private float y;
	private Matrix mt;

	public NShowView(Context context) {
		super(context);
		bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		n = 0;
		mt = new Matrix();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (m > 30) {
			n++;
			m = 0;
		} else
			m++;
		canvas.drawColor(NView.mcolor[n % NView.mcolor.length]);
		// mt.setRotate(m);//��ת
//		 mt.setTranslate(x++%100, y++%30);//λ��
		// mt.setSkew(x++%3, y++%5);//��ת
		if(x<5)
			x++;
		if(y<5)
			y++;
		 mt.setScale(x, y);//�Ŵ�
		// mt.setSinCos(x, y++);//����
		canvas.drawBitmap(bm, mt, p);
		invalidate();
	}
}
