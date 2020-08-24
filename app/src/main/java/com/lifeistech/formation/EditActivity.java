package com.lifeistech.formation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.res.AssetFileDescriptor;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EditActivity extends AppCompatActivity {

    //    ImageView mainimage;
    ImageView startButton;
    ImageView stopButton;
    Button addObject;
    Button previewButton;
    TextView textView;
    int minuteCounter=0;
    int secondCounter=0;
    int milliSecondCounter=0;
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
    SeekBar musicSeekBar;
    SeekBar horizontalSeekBar;
    LinearLayout notObjectMode;
    LinearLayout objectMode;
    LinearLayout addDancerOption;
    Button addDancer;

    TextView editDancerName;
    TextView editDancerColor;
    TextView editDancerPosition;
    EditText editName;
    EditText editColor;
    TextView textViewX;
    TextView textViewY;
    EditText editX;
    EditText editY;
    Button putDancer;

    String dancerName;
    int newX = 0;
    int newY = 0;

    Handler mHandler;
    private MediaPlayer mediaPlayer;
    private MyView myView;
    private int positionX = 0;
    private int positionY = 0;

    private int maxTime;
    List<Integer> chapterTime = new ArrayList<>(Arrays.asList(0, 30000, 50000, 226325, maxTime));
    private List<Dancer> dancers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        VerticalSeekBar verticalSeekBar
                = (VerticalSeekBar) findViewById(R.id.verticalSeekBar);
        //垂直シークバーをレイアウトから取得
        verticalSeekBar.setMax(100);
        verticalSeekBar.setOnSeekBarChangeListener(
                new AppCompatSeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Log.i("EditActivity : ", "値 = " + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


        startButton = (ImageView) findViewById(R.id.start);
        stopButton = (ImageView) findViewById(R.id.stop);
        addObject = (Button) findViewById(R.id.addObject);
        previewButton = (Button) findViewById(R.id.previewButton);
        addDancer = (Button) findViewById(R.id.addDancer);
        notObjectMode = (LinearLayout) this.findViewById(R.id.notObjectMode);
        objectMode = (LinearLayout) this.findViewById(R.id.objectMode);
        addDancerOption = (LinearLayout) this.findViewById(R.id.addDancerOption);
        notObjectMode.setVisibility(View.VISIBLE);
        objectMode.setVisibility(View.GONE);
        addDancerOption.setVisibility(View.GONE);
//        previewButton.setVisibility(View.GONE);

        editDancerName = (TextView) findViewById(R.id.editDancerName);
        editDancerColor = (TextView) findViewById(R.id.editDancerColor);
        editDancerPosition = (TextView) findViewById(R.id.editDancerPosition);
        editName = (EditText) findViewById(R.id.editName);
        editColor = (EditText) findViewById(R.id.editColor);
        textViewX = (TextView) findViewById(R.id.textViewX);
        textViewY = (TextView) findViewById(R.id.textViewY);
        editX = (EditText) findViewById(R.id.editX);
        editY = (EditText) findViewById(R.id.editY);
        putDancer = (Button) findViewById(R.id.putDancer);

        textView = (TextView) findViewById(R.id.textView);
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        myView = findViewById(R.id.myView);

        horizontalSeekBar = findViewById(R.id.horizontalSeekBar);
        musicSeekBar = findViewById(R.id.musicSeekBar);


        mHandler = new Handler();

        maxCount = chapterTime.size();

        musicSeekBar.setOnSeekBarChangeListener(
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
                        mTimem = seekBar.getProgress() - 1;
                        audioCurrentPosition = seekBar.getProgress() - 1;
                        mediaPlayer=new MediaPlayer();
                        mediaPlayer.seekTo(audioCurrentPosition);
//                        mediaPlayer.start();
                    }
                }
        );

        List<Dancer> tmpDancers = Arrays.asList(ObjectStrage.get("project_name", Dancer[].class));

        dancers.clear();
        for (Dancer dancer : tmpDancers) {
            dancers.add(dancer);
        }
        myView.setDancers(dancers);
    }

    public void next(View v) {
        if (mTimerm == null) {
            if (chapter < maxCount) {
                chapter = chapter + 1;
                int time = chapterTime.get(chapter) - 1;
                musicSeekBar.setProgress(time);
                mTimem = time;
                textView.setText(String.valueOf(mTimem));
                audioCurrentPosition = mTimem;
            }
        }
    }//ここまで右ボタン押した時に実行

    public void prev(View v) {
        if (mTimerm == null) {
            if (chapter >= minCount) {
                if (mTimem == chapterTime.get(chapter) && chapter != minCount) {
                    chapter = chapter - 1;
                }
                int time = chapterTime.get(chapter);
                musicSeekBar.setProgress(time);
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
//                            musicSeekBar.setProgress(mTime);
//                            //mainimage.setImageResource(images[chapter]);
//                            //textView.setText(String.valueOf(chapter + 1));
//                            mTime++;
//                            if(mTime > audioCurrentPosition + maxTime){
//                             stopTimer();
//                            }
//                            myView.update(mTime);//myViewに描画する
//                            if (mTime == maxTime) {
//                                mTime = 0;
//                                musicSeekBar.setProgress(mTime);
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
                        musicSeekBar.setProgress(mTimem);
                        mTimem++;
                        if (mTimem > chapterTime.get(chapter)) {
                            chapter = chapter + 1;
                        } else {
//                            chapter = chapter - 1;
                        }

                        minuteCounter = (mTimem/1000)/60;
                        secondCounter = (mTimem/1000)%60;
                        milliSecondCounter = (mTimem%1000)/10;
                        String jikoku = String.format(Locale.getDefault(), "%02d:%02d:%02d", minuteCounter, secondCounter,milliSecondCounter);
                        textView.setText(jikoku);//タイマー表示
//                        textView.setText(String.valueOf(mTimem));
//                        if(mTimem > audioCurrentPosition + maxTime){
//                            stopTimer();
//                        }
                        //positionX=positionX+10;
                        //positionY=positionY+5;
                        myView.update(mTimem);//myViewに描画する
                        if (mTimem == maxTime) {
                            mTimem = 0;
                            musicSeekBar.setProgress(mTimem);
                            mTimerm.cancel();
                            chapter = 0;
                            //textView.setText(String.valueOf(chapter + 1));
                            mTimerm = null;
                            startButton.setVisibility(View.VISIBLE);
                            stopButton.setVisibility(View.GONE);
                            audioStop();
                        }//このif文で再生終了後自動で元の状態に戻る
                    }
                });
            }
        }, 0, 1);
    }//ここまで再生ボタンを押した時に実行

    public void stop(View v) {
        stopTimer();
    }//ここまでストップボタンを押した時に実行

//    public void onProgressChanged(SeekBar musicSeekBar, int progress, boolean fromUser){
//        mTimem = musicSeekBar.getProgress();
//        audioCurrentPosition = musicSeekBar.getProgress();
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
            Log.d("TAG", maxTime + "");
            musicSeekBar.setMax(maxTime);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }//ここまでオーディオセットアップ


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

    public void addObjectMode(View v) {
        notObjectMode.setVisibility(LinearLayout.GONE);
        objectMode.setVisibility(LinearLayout.VISIBLE);
        addDancerOption.setVisibility(LinearLayout.GONE);
//        addObject.setVisibility(Button.GONE);
//        previewButton.setVisibility(Button.VISIBLE);
    }//addObjectを押して画面下半分を詳細編集画面にする

    public void previewMode(View v) {
        notObjectMode.setVisibility(View.VISIBLE);
        objectMode.setVisibility(View.GONE);
        addDancerOption.setVisibility(View.GONE);
//        addObject.setVisibility(Button.VISIBLE);
//        previewButton.setVisibility(Button.GONE);
    }//previewModeを押して画面の下半分をプレビュー画面にする

    public void addDancer(View v) {
        addDancerOption.setVisibility(LinearLayout.VISIBLE);
        objectMode.setVisibility(LinearLayout.GONE);
    }//ADD DANCERボタンを押してダンサーのオプションを開く

    public void putDancer(View v) {
        dancerName = editName.getText().toString();
        String xText = editX.getText().toString();
        int newX = Integer.parseInt(xText);
        String yText = editY.getText().toString();
        int newY = Integer.parseInt(yText);

        List<Position> positionList = new ArrayList();
        for (int i = 0; i < 3000; i++) {
            positionList.add(new Position(newX - i / 10, newY+i / 10));
        }
        Dancer dancer1 = new Dancer(positionList, dancerName);
        dancers.add(dancer1);
        myView.setDancers(dancers);

        addDancerOption.setVisibility(LinearLayout.GONE);
        notObjectMode.setVisibility(LinearLayout.VISIBLE);
    }//ADDボタンを押してダンサーを追加する

    public void musicSelect(View v) {
        Intent intent4 = new Intent(this, MusicSelectActivity.class);
        startActivity(intent4);
    }//音楽選択画面へ

    public void saveProject(View v) {
        ObjectStrage.save(dancers, "project_name");
        finish();
        Intent intent0 = new Intent(this,StartActivity.class);
        startActivity(intent0);
    }//プロジェクトのセーブをする


}
