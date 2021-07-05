/****************************************************
 *** Module Name    :   Mokuhyo.java
 *** Version        :   V1.0
 *** Designer       :   川田　紗英花
 *** Date           :   2021.06.28
 *** Purpose        :   大きな目標処理部
 ***
 ***************************************************/
/*
*** Revision    :
*** V1.0        :   川田　紗英花, 2021.06.28, 作成
*/

package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import android.util.Log;

public class Mokuhyo {
    //　ファイルから目標を読み込み出力するメソッド
    String getMokuhyo()
    {
        String text;
        MokuhyoOut out = new MokuhyoOut();
        text = out.readFile();
        return text;
    }

    //　ファイルに目標を保存するメソッド
    void setMokuhyo(String text) //(String) 入力文字列
    {
        MokuhyoOut out = new MokuhyoOut();
        out.saveFile(text);
    }
}






