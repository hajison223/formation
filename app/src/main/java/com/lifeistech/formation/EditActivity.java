package com.lifeistech.formation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.widget.SeekBar;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        VerticalSeekBar verticalSeekBar
                = (VerticalSeekBar)findViewById(R.id.verticalSeekBar);
        //垂直シークバーをレイアウトから取得
        verticalSeekBar.setMax(100);
        verticalSeekBar.setOnSeekBarChangeListener(
                new AppCompatSeekBar.OnSeekBarChangeListener()
                {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Log.i("MainActivity : ", "値 = " + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
    }


}
