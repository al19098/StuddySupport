/****************************************************
 *** Module Name    :   CharacterOut.java
 *** Version        :   V1.0
 *** Designer       :   川田　紗英花
 *** Date           :   2021.06.28
 *** Purpose        :   キャラクターファイル管理部
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
import android.content.res.AssetManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

public class CharacterOut {

    //　コメントを読み込むメソッド
    String readComment() {

        Context context = AppContext.getAppContext();    //　コンテキスト
        String fileName = "Comment.txt";                 //　ファイル名
        LinkedList<String> comment = new LinkedList<>(); //　コメント格納リスト
        String line;                                     //　ファイル内の行数

        //　コメントファイルから文字列をリストに追加
        InputStream is = null;
        BufferedReader br = null; 
        try {
            try {
                //　assetsフォルダを開く
                AssetManager assets = context.getResources().getAssets();
                is = assets.open(fileName);
                br = new BufferedReader(new InputStreamReader(is));

                //　文字列をリストに格納
                while ((line = br.readLine()) != null) {
                    comment.add(line);
                }
            } finally {
                //　ファイルクローズ
                if (is != null) is.close();
                if (br != null) br.close();
            }
        } catch (Exception e) {
            //　例外処理
            e.printStackTrace();
        }

        //　コメントリストからランダムにひとつ抽出
        int index = new Random().nextInt(comment.size()); 
        String result = comment.get(index);
        //　抽出されたコメントを出力
        return result;
    }
}
