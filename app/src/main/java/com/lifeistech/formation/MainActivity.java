package com.lifeistech.formation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.res.AssetFileDescriptor;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    int chapter = 0;
    int maxCount;
    int minCount = 0;
    Timer mTimerm;
    int mTimem = 0;
    int audioCurrentPosition = 0; //
    SeekBar seekBar;
    Handler mHandler;
    private MediaPlayer mediaPlayer;
    private MyView myView;
    private int positionX = 0;
    private int positionY = 0;

    private int maxTime;
    List<Integer> chapterTime = new ArrayList<>(Arrays.asList(0,30000,50000,70000));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainimage = (ImageView) findViewById(R.id.imageView);//一旦imageView
//        mainimage.setImageResource(images[chapter]);
        startButton = (ImageView) findViewById(R.id.start);
        stopButton = (ImageView) findViewById(R.id.stop);
        textView = (TextView) findViewById(R.id.textView);
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        myView = findViewById(R.id.myView);
        seekBar = findViewById(R.id.seekBar2);

        mHandler = new Handler();

        maxCount = chapterTime.size();

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                        mTimem = seekBar.getProgress();
                        audioCurrentPosition = seekBar.getProgress();
                        mediaPlayer.seekTo(audioCurrentPosition);
//                        mediaPlayer.start();
                    }
                }
        );

    }//ここまでが起動時一度だけ実行される---変数の初期化---

    public void next(View v) {
        if(mTimerm == null) {
            if (chapter < maxCount) {
                chapter = chapter + 1;
                int time = chapterTime.get(chapter);
                seekBar.setProgress(time);
                mTimem = time;
                textView.setText(String.valueOf(mTimem));
                audioCurrentPosition = mTimem;
            }
        }
    }//ここまで右ボタン押した時に実行

    public void prev(View v) {
        if(mTimerm == null) {
            if (chapter >= minCount) {
                if (mTimem == chapterTime.get(chapter) && chapter != minCount) {
                    chapter = chapter - 1;
                }
                int time = chapterTime.get(chapter);
                seekBar.setProgress(time);
                mTimem = time;
                textView.setText(String.valueOf(mTimem));
                audioCurrentPosition = mTimem;
//            chapter = chapter - 1;
                //audioCurrentPosition = audioCurrentPosition - 1000;
            }
        }
    }//ここまで左ボタン押した時に実行


    public void start(View v) {
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
//        if (mTimer == null) {
//            mTime = chapter;
//            mTimer = new Timer(false);
//            mTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            seekBar.setProgress(mTime);
//                            //mainimage.setImageResource(images[chapter]);
//                            //textView.setText(String.valueOf(chapter + 1));
//                            mTime++;
//                            if(mTime > audioCurrentPosition + maxTime){
//                             stopTimer();
//                            }
//                            myView.update(mTime);//myViewに描画する
//                            if (mTime == maxTime) {
//                                mTime = 0;
//                                seekBar.setProgress(mTime);
//                                mTimer.cancel();
//                                chapter = 0;
//                                textView.setText(String.valueOf(chapter + 1));
//                                mTimer = null;
//                                startButton.setVisibility(View.VISIBLE);
//                                stopButton.setVisibility(View.GONE);
//                                audioStop();
//                            }
//                        }
//                    });
//                }
//            }, 0, 1000);
//        }
        audioPlay();

//        mTimem = audioCurrentPosition;
        mTimerm = new Timer(false);
        mTimerm.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(mTimem);
                        mTimem++;
                        if(mTimem >= chapterTime.get(chapter)){
                            chapter = chapter + 1;
                        }else {
                            chapter = chapter - 1;
                        }
                        textView.setText(String.valueOf(mTimem));
//                        if(mTimem > audioCurrentPosition + maxTime){
//                            stopTimer();
//                        }
                            //positionX=positionX+10;
                            //positionY=positionY+5;
                            //myView.update(positionX,positionY);//myViewに描画する
                        if (mTimem == maxTime) {
                            mTimem = 0;
                            seekBar.setProgress(mTimem);
                            mTimerm.cancel();
                            chapter = 0;
                            //textView.setText(String.valueOf(chapter + 1));
                            mTimerm = null;
                            startButton.setVisibility(View.VISIBLE);
                            stopButton.setVisibility(View.GONE);
                            audioStop();
                        }
                    }
                });
            }
        }, 0, 1);
    }//ここまで再生ボタンを押した時に実行

    public void stop(View v) {
        stopTimer();
    }//ここまでストップボタンを押した時に実行

//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
//        mTimem = seekBar.getProgress();
//        audioCurrentPosition = seekBar.getProgress();
//    }//ユーザー　つまみをドラッグ時



    public void stopTimer() {
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        if (mTimerm != null) {
            //mTimem = chapter;
            mTimerm.cancel();
            mTimerm = null;
        }
        if (mediaPlayer != null) {
            audioStop();
        }
    }//ここまでタイマーストップ時に実行

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimerm != null) {
            mTimem = chapter;
            mTimerm.cancel();
            mTimerm = null;
        }
    }//MainActivity終了時に実行

    public void menu(View v) {

    }//ここまでメニューボタンを押した時に実行

    public void volume(View v) {

    }//ここまで音量ボタンを押した時に実行

    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "x:" + event.getX() + ",y:" + event.getY());
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
            maxTime = mediaPlayer.getDuration();
            seekBar.setMax(maxTime);
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
            audioCurrentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();
        }

        // 再生する
        mediaPlayer.seekTo(audioCurrentPosition);
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
        audioCurrentPosition = mediaPlayer.getCurrentPosition();
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
