package com.example.studysupport;

import android.util.Log;

public class Mokuhyou {
    //　ファイルから目標を読み込み出力する
    String getMokuhyo() {
        String text;
        MokuhyoOut out = new MokuhyoOut();
       text = out.readFile();
        return text;
    }

    // ファイルに目標を保存する
    void setMokuhyo(String text){
        MokuhyoOut out = new MokuhyoOut();
        out.saveFile(text);
    }
}