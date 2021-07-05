/****************************************************
 *** Module Name    :   AppContext.java
 *** Version        :   V1.0
 *** Designer       :   大谷 真由
 *** Date           :   2021.06.14
 *** Purpose        :   アクティビティ以外からコンテクストを取得する
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   大谷 真由, 2021.06.14, 作成
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class AppContext extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context; // コンテクスト格納用

    // コンテクストを取得する
    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.context = getApplicationContext();
    }

    // コンテクストを返す
    public static Context getAppContext() {
        return AppContext.context;
    }
}
