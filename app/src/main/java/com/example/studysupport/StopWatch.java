/****************************************************
 *** Module Name    :   StopWatch.java
 *** Version        :   V1.0
 *** Designer       :   大谷 真由
 *** Date           :   2021.06.22
 *** Purpose        :   カウントアップで時間を測定する.
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
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.util.TimerTask;

public class StopWatch extends TimerTask {
    long elapsed = 0;   // 経過時間
    boolean isstarted = false;  // 計測状態(true = 計測中)
    TextView time;  // 時間表示
    final Handler handler = new Handler(Looper.getMainLooper());    // 非同期処理用ハンドラ

    // 計測開始
    public void start(TextView text){ // 時間表示
        isstarted = true;
        time = text;
    }

    // 計測中断・再開
    public void interruption(){
        isstarted = !isstarted;
    }

    // 計測終了(結果保存に失敗した場合は-1を返す)
    public int reset(){
        int written = -1;   // 計測時間
        isstarted = false;
        time.setText(R.string.zeros);
        DailyStudyTime dst = new DailyStudyTime();
        if(dst.write((int)(elapsed / 10 / 60))){
            written = (int)(elapsed / 10 / 60);
        }
        return written;
    }

    // カウントアップ(非同期処理)
    @Override
    public void run() {
        handler.post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                if (isstarted) {
                    time.setText(String.format("%02d:%02d", elapsed/10/60, elapsed/10%60));
                    elapsed++;
                }
            }
        });
    }
}
