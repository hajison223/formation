package com.lifeistech.formation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MyView extends View {
    private Paint paint;

    private float StrokeWidth = 20.0f;// 描画するラインの太さ
    private int circleX = 0;
    private int circleY = 100;
    private List<Dancer> dancers = new ArrayList<>();
    private int currentTime = 0;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        List<Position> positionList = new ArrayList();
//
//        // 仮で位置情報を生成
        for (int i = 0; i < 180; i++) {
            positionList.add(new Position(i*100, i*100));
        }
//
        Dancer dancer1 = new Dancer(positionList, "dancer1");
        dancers.add(dancer1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float xc = canvas.getWidth() / 2;
        float yc = canvas.getHeight() / 2;
        paint.setColor(Color.argb(255, 255, 0, 255));// ペイントする色の設定
        paint.setStrokeWidth(StrokeWidth);// ペイントストロークの太さを設定
        paint.setStyle(Paint.Style.STROKE);// Styleのストロークを設定する

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(300, 300, 600, 600, paint);// drawRectを使って矩形を描画する、引数に座標を設定
        canvas.drawCircle(circleX, circleY, 200, paint);
        //paint.setColor(Color.argb(255,255,255,255));
        canvas.drawCircle(xc, yc, 300, paint);
        canvas.drawCircle(xc + 100, yc - 200, 70, paint);

        for(Dancer dancer : dancers){
            canvas.drawCircle(dancer.movePositions.get(currentTime).x,dancer.movePositions.get(currentTime).y,20,paint);
        }

    }


    void update(int mTime) {
        currentTime = mTime;
        invalidate(); //onDraw()がもう一度呼ばれる
    }

}
