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
//        List<Position> positionList = new ArrayList();
//        List<Position> positionList2 = new ArrayList();
//
//
//        for (int i = 0; i < 3000; i++) {
//            positionList.add(new Position(i/10, i/10));
//        }
//        for (int i = 0; i < 2000; i++) {
//            positionList.add(new Position(300-i/10, 300-i/10));
//        }
//        for (int i = 0; i < 4000; i++) {
//            positionList.add(new Position(100+i/10, 100));
//        }
//        for (int i = 0; i < 2000; i++) {
//            positionList.add(new Position(500, 100+i/10));
//        }
//        for (int i = 0; i < 2000; i++) {
//            positionList.add(new Position(500-i/10, 300));
//        }
//        for (int i = 0; i < 200000; i++) {
//            positionList.add(new Position(300,300));
//        }// 仮で小円位置情報を生成
//
////
//        Dancer dancer1 = new Dancer(positionList, "dancer1");
//        dancers.add(dancer1);
//        Dancer dancer2 = new Dancer(positionList2, "dancer2");
//        dancers.add(dancer2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float xc = canvas.getWidth() / 2;
        float yc = canvas.getHeight() / 2;
        paint.setColor(Color.argb(255, 255, 0, 255));// ペイントする色の設定
        paint.setStrokeWidth(StrokeWidth);// ペイントストロークの太さを設定
        paint.setStyle(Paint.Style.STROKE);// Styleのストロークを設定する

        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
//        canvas.drawRect(300, 300, 600, 600, paint);// drawRectを使って矩形を描画する、引数に座標を設定
//        canvas.drawCircle(circleX, circleY, 200, paint);
        //paint.setColor(Color.argb(255,255,255,255));
//        canvas.drawCircle(xc, yc, 300, paint);
//        canvas.drawCircle(xc + 100, yc - 200, 70, paint);
        //Position posistion= new Position(5,5);

        for(Dancer dancer : dancers){
            canvas.drawCircle(dancer.movePositions.get(currentTime).x,dancer.movePositions.get(currentTime).y,20,paint);
        }

    }


    void update(int mTime) {
        currentTime = mTime;
        invalidate(); //onDraw()がもう一度呼ばれる
    }

    public void setDancers(List<Dancer> newDancers){
        dancers = newDancers;
    }


}
