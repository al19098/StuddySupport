/****************************************************
 *** Module Name    :   MokuhyoOut.java
 *** Version        :   V1.0
 *** Designer       :   川田　紗英花
 *** Date           :   2021.06.28
 *** Purpose        :   大きな目標ファイル管理部
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

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MokuhyoOut {
    Context context = AppContext.getAppContext();   //　コンテキスト
    String fileName = "Bigmokuhyo.txt";             //　ファイル名
    //　ファイル作成
    File file = new File(context.getFilesDir(), fileName);

    //　目標読み込みメソッド
    String readFile() {
        String text = null; //　大きな目標
        try {
            //　内部ストレージのファイルから読み込み
            BufferedReader br = new BufferedReader((
                                    new InputStreamReader(
                                         new FileInputStream(file),"UTF-8")));
            text = br.readLine();
            br.close();

        } catch (IOException e) {
            //　ファイルがなかった場合の処理(初期状態の場合)
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("大きな目標を入力してください");
            }
            catch (IOException ex) {
             // 例外処理
                ex.printStackTrace();
            }
        }
        return text;
    }

    //　目標保存メソッド
    void saveFile(String text) { // (String)入力文字列
        //　過去のファイルの削除
        file.delete();
        //　ファイル作成
        File file2 = new File(context.getFilesDir(),fileName);

        //　ファイル更新
        try{
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(file2), "UTF-8")));
            pw.print(text);
            pw.close();
        } catch (IOException e) {
        // 例外処理
            e.printStackTrace();
        }
    }
}