package com.lifeistech.formation;

import android.content.Intent;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;
import android.content.res.AssetFileDescriptor;
import android.widget.Toast;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //---変数宣言---
    ImageView mainimage;
    ImageView startButton;
    ImageView stopButton;
    TextView textView;
    int[] images = {
            R.drawable.volume,
            R.drawable.play,
            R.drawable.person,
            R.drawable.object,
            R.drawable.music
    };
    int page = 0;
    int seconds = 0;
    int chapter = 0;
    int maxCount = 4;
    int minCount = 0;
    Timer mTimer;
    Timer mTimerm;
    int mTime = 0;
    int mTimem = 0;
    int currentPosition = 0;
    SeekBar seekBar;
    Handler mHandler;
    private MediaPlayer mediaPlayer;
    private MyView myView;
    private int positionX = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainimage = (ImageView) findViewById(R.id.imageView);//一旦imageView
        mainimage.setImageResource(images[page]);
        startButton = (ImageView) findViewById(R.id.start);
        stopButton = (ImageView) findViewById(R.id.stop);
        textView = (TextView) findViewById(R.id.textView);
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        myView = findViewById(R.id.myView);


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(4);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        //tv0.setText("設定値:"+seekBar.getProgress());
                        page = seekBar.getProgress();
                        mainimage.setImageResource(images[page]);
                        textView.setText(String.valueOf(page + 1));
                    }// ツマミをドラッグしたときに呼ばれる

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                });
        mHandler = new Handler();


    }//ここまでが起動時一度だけ実行される---変数の初期化---


    public void next(View v) {
        if (page < maxCount) {
            page = page + 1;
            mainimage.setImageResource(images[page]);
            seekBar.setProgress(page);
            textView.setText(String.valueOf(page + 1));
//            page = page + 1;
            currentPosition = currentPosition + 1000;
        } else {
            mainimage.setImageResource(images[page]);
            seekBar.setProgress(page + 1);
            textView.setText(String.valueOf(page + 1));
        }
        //seconds = seconds + 1;
        //seconds = seconds + page;
    }//ここまで右ボタン押した時に実行

    public void prev(View v) {
        if (page > minCount) {
            page = page - 1;
            mainimage.setImageResource(images[page]);
            seekBar.setProgress(page);
            textView.setText(String.valueOf(page + 1));
//            page = page - 1;
            currentPosition = currentPosition - 1000;
        }
        //seconds = seconds - 1;
        //seconds = seconds + page;
    }//ここまで左ボタン押した時に実行

    public void start(View v) {
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        if (mTimer == null) {
            mTime = page;
            mTimer = new Timer(false);
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mTime);
                            mainimage.setImageResource(images[page]);
                            textView.setText(String.valueOf(page + 1));
                            mTime++;
                            if (mTime == 6) {
                                mTime = 0;
                                seekBar.setProgress(mTime);
                                mTimer.cancel();
                                page = 0;
                                textView.setText(String.valueOf(page + 1));
                                mTimer = null;
                                startButton.setVisibility(View.VISIBLE);
                                stopButton.setVisibility(View.GONE);
                                audioStop();
                            }
                        }
                    });
                }
            }, 0, 1000);
        }
        //mediaPlayer.seekTo(seconds);
        audioPlay();

        mTimem = currentPosition;
        mTimerm = new Timer(false);
        mTimerm.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //seekBar.setProgress(mTimem);
                        //mainimage.setImageResource(images[page]);
                        //textView.setText(String.valueOf(page + 1));
                        mTimem++;
                        if(mTimem == currentPosition + 6000){
                            mTimerm.cancel();
                            mTimerm = null;
                        }
                        //if (mTime == 6) {
                            //mTime = 0;
                            //seekBar.setProgress(mTime);
                            //mTimer.cancel();
                            //page = 0;
                            //textView.setText(String.valueOf(page + 1));
                            //mTimer = null;
                            //startButton.setVisibility(View.VISIBLE);
                            //stopButton.setVisibility(View.GONE);
                            //audioStop();
                        //}
                    }
                });
            }
        }, 0, 100);
        //seconds = mediaPlayer.getDuration();
    }//ここまで再生ボタンを押した時に実行

    public void stop(View v) {
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        if (mTimer != null) {
            mTime = page;
            mTimer.cancel();
            mTimer = null;
        }
        if (mediaPlayer != null) {
            audioStop();
        }
    }//ここまでストップボタンを押した時に実行

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTime = page;
            mTimer.cancel();
            mTimer = null;
        }
    }//MainActivity終了時に実行

    public void menu(View v) {

    }//ここまでメニューボタンを押した時に実行

    public void volume(View v) {
        myView.positionUpdate(positionX);
    }//ここまで音量ボタンを押した時に実行

    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());
        return true;
    }//ここまで画面をタッチした時に実行


    private boolean audioSetup() {
        boolean fileCheck = false;

        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        String filePath = "music.mp3";

        // assetsから mp3 ファイルを読み込み
        try (AssetFileDescriptor afdescripter = getAssets().openFd(filePath);) {
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }//ここまでオーディオステップアップ


    private void audioPlay() {
        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (audioSetup()) {
                Toast.makeText(getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            // 繰り返し再生する場合
            mediaPlayer.stop();
            currentPosition = mediaPlayer.getCurrentPosition();
            currentPosition = currentPosition - currentPosition % 1000;
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();
        }

        // 再生する
        mediaPlayer.seekTo(currentPosition);
        //mediaPlayer.seekTo(seconds);
        mediaPlayer.start();

        // 終了を検知するリスナー
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("debug", "end of audio");
                audioStop();
            }
        });
    }//ここまでオーディオプレイ


    private void audioStop() {
        // 再生終了
        currentPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.stop();

        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();

        mediaPlayer = null;
    }//ここまでオーディオストップ

    public void musicSelect(View v){
        Intent intent4 = new Intent(this,MusicSelectActivity.class);
        startActivity(intent4);
    }//音楽選択画面へ


}
