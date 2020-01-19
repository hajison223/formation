package com.lifeistech.formation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void moveActivity(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }//メイン画面へ

    public void projectManagerActivity(View v){
        Intent intent2 = new Intent(this,ProjectManagerActivity.class);
        startActivity(intent2);
    }//プロジェクト管理画面へ

    public void helpActivity(View v){
        Intent intent3 = new Intent(this,HelpActivity.class);
        startActivity(intent3);
    }//ヘルプ画面へ

}
