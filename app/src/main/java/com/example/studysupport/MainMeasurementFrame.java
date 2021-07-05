/****************************************************
 *** Module Name    :   MainMeasurementFrame.java
 *** Version        :   V1.0
 *** Designer       :   大谷 真由
 *** Date           :   2021.06.22
 *** Purpose        :   計測画面を表示する.
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   大谷 真由, 2021.06.22, 作成
 *** V1.1        :   大谷 真由, 2021.07.06, SnackBarをToastに変更
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;

public class MainMeasurementFrame extends AppCompatActivity {
    SoundPool soundpool; // アラーム再生用サウンドプール
    int soundid; // サウンド識別子
    int studytime; // 学習時間
    String mode; // 計測モード(タイマー:COUNTDOWN, ストップウォッチ:STOPWATCH
    boolean isstarted = false; // 開始状態
    StopWatch stopwatch = new StopWatch(); // ストップウォッチオブジェクト
    CountDown countdown = new CountDown(); // カウントダウンオブジェクト

    // ヒントダイアログ
    popup pop = new popup("・ストップボタンを押すとカウントが一時停止します\n" +
            "・カウントが止まっている状態でスタートボタンを押すとカウントが再スタートします\n" +
            "・カウントが止まっている状態でリセットボタンを押すと学習時間を保存して元の画面に戻ります\n" +
            "・タイマーの場合アラームが鳴っている状態でストップボタンを押すと学習時間を保存して元の画面に戻ります");

    // アクティビティ開始時に呼び出されるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_measurement_frame);

        // 画面遷移時にインテントとモード変数を取得
        Intent intent = getIntent();
        mode = intent.getStringExtra("MODE");

        // ボタン，テキストビュー．タイマーの設定
        Button stop = findViewById(R.id.button3);
        Button reset = findViewById(R.id.button4);
        TextView modetext = findViewById(R.id.textView6);
        TextView time = findViewById(R.id.textView5);
        Timer timer = new Timer();

        // アラーム音の設定
        AudioAttributes audioattributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundpool = new SoundPool.Builder().setAudioAttributes(audioattributes).setMaxStreams(1).build();
        soundid = soundpool.load(this, R.raw.alerm, 1);

        // 指定されたモードに従ってタイマー，又はストップウォッチを起動
        if (mode.equals("STOPWATCH")){
            modetext.setText(R.string.stopwatch);
            stopwatch.start(time);
            timer.scheduleAtFixedRate(stopwatch, 0, 100);
            isstarted =true;
        }
        else if (mode.equals("COUNTDOWN")){
            modetext.setText(R.string.timer);
            int min = intent.getIntExtra("MINUTES", 0); // 分を取得
            int sec = intent.getIntExtra("SECONDS", 0); // 秒を取得
            countdown.start(time, soundpool, soundid, min, sec);
            timer.scheduleAtFixedRate(countdown, 0, 100);
            isstarted = true;
        }

        // ストップボタンが押された時の処理
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("COUNTDOWN") && countdown.isplayed){
                    studytime = countdown.reset();
                    // 保存に失敗した場合はエラーメッセージを表示
                    if (studytime < 0){
                        Toast errmsg = Toast.makeText(getApplicationContext(), "保存に失敗しました",
                                Toast.LENGTH_SHORT);
                        errmsg.show();
                    }
                    finish();
                }
                if (isstarted){
                    if (mode.equals("STOPWATCH")){
                        stopwatch.interruption();
                    }
                    else if (mode.equals("COUNTDOWN")){
                        countdown.interruption();
                    }
                    isstarted = false;
                    stop.setText(R.string.start);
                    reset.setVisibility(View.VISIBLE);
                }else{
                    if (mode.equals("STOPWATCH")){
                        stopwatch.interruption();
                    }
                    else if (mode.equals("COUNTDOWN")){
                        countdown.interruption();
                    }
                    isstarted = true;
                    stop.setText(R.string.stop);
                    reset.setVisibility(View.GONE);
                }
            }
        });

        // リセットボタンが押された時の処理
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("STOPWATCH")){
                    studytime = stopwatch.reset();
                }
                else if (mode.equals("COUNTDOWN")){
                    studytime = countdown.reset();
                }
                if (studytime < 0){
                    Toast errmsg = Toast.makeText(getApplicationContext(), "保存に失敗しました",
                            Toast.LENGTH_SHORT);
                    errmsg.show();
                }
                finish();
            }
        });
    }

    // ヒントボタンを押したときの処理
    public void onTapEvent(View view) {
        //ダイアログをだす
        pop.show(getSupportFragmentManager(),"popup");
    }

    // アクティビティ終了時にサウンドプールを解放する
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundpool.release();
    }
}