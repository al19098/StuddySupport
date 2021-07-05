/****************************************************
 *** Module Name    :   CountDown.java
 *** Version        :   V1.0
 *** Designer       :   大谷 真由
 *** Date           :   2021.06.22
 *** Purpose        :   カウントダウンで時間を測定する.
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   大谷 真由, 2021.06.22, 作成
 *** V1.1        :   大谷 真由, 2021.06.28, DailyStudyTimeのメソッド名変更に合わせた変更
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import android.annotation.SuppressLint;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.TimerTask;

public class CountDown extends TimerTask {
    long elapsed = 0;   // 経過時間
    boolean isstarted = false; // 計測状態(true = 計測中)
    boolean isplayed = false;   // アラーム再生状態(true = 再生中)
    long rest;  // 計測時間
    TextView time;  // 時間表示
    SoundPool soundpool;    // アラーム再生用SountPool
    int soundid;    // サウンド識別子
    int stream; // ストリームID
    final Handler handler = new Handler(Looper.getMainLooper());    //非同期処理用ハンドラ

    // 計測開始
    public void start(TextView text,    // 時間表示
                      SoundPool pool,   // アラーム再生用SoundPool
                      int id,   // サウンド識別子
                      int minute,   // 計測時間(分)
                      int second){  // 計測時間(秒)
        rest = minute * 60 * 10 + second * 10;
        if (rest < 0){
            rest = 0;
        }
        isstarted = true;
        time = text;
        soundpool = pool;
        soundid = id;
    }

    // 計測中断・再開
    public void interruption(){
        isstarted = !isstarted;
    }

    // 計測終了，アラーム再生中の場合はアラームを止める(結果保存に失敗した場合は-1を返す)
    public int reset(){
        isstarted = false;
        time.setText(R.string.zeros);
        if (isplayed) {
            soundpool.stop(stream);
            isplayed = false;
        }
        DailyStudyTime dst = new DailyStudyTime();
        int written = -1; // 保存した学習時間
        if (dst.write((int)(elapsed/ 10 / 60))){
            written = (int)elapsed/ 10 / 60;
        }
        return written;
    }

    // カウントダウン(カウント0になったらアラームを鳴らす)
    @Override
    public void run() {
        handler.post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                double min = Math.floor((double)(rest - elapsed) / 10.0 / 60.0); // 表示時間(分)
                double sec = Math.ceil((double)(rest - elapsed) / 10.0 % 60.0); // 表示時間(秒)
                if (sec == 60){
                    sec = 0;
                    min ++;
                }
                if (isstarted && rest > elapsed){
                    time.setText(String.format("%02d:%02d", (int)min, (int)sec));
                    elapsed++;
                }else if (rest <= elapsed && !isplayed){
                    time.setText(String.format("%02d:%02d", (int)min, (int)sec));
                    stream = soundpool.play(soundid, 1.0f, 1.0f, 0, -1, 1);
                    isplayed = true;
                }
            }
        });
    }
}
