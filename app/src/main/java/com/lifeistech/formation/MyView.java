package com.lifeistech.formation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class MyView extends View {
    private Paint paint;

    // 描画するラインの太さ
    private float StrokeWidth = 20.0f;

    private int circleX = 0;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ペイントする色の設定
        paint.setColor(Color.argb(255, 255, 0, 255));
        // ペイントストロークの太さを設定
        paint.setStrokeWidth(StrokeWidth);
        // Styleのストロークを設定する
        paint.setStyle(Paint.Style.STROKE);

        // drawRectを使って矩形を描画する、引数に座標を設定
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(300, 300,600, 600, paint);
        canvas.drawCircle(circleX,100,200,paint);
        //paint.setColor(Color.argb(255,255,255,255));
    }

    void positionUpdate(int positionX){
        circleX = positionX;
        invalidate();
    }
}
