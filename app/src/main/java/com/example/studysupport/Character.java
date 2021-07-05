/****************************************************
 *** Module Name    :   Character.java
 *** Version        :   V1.0
 *** Designer       :   川田　紗英花
 *** Date           :   2021.06.28
 *** Purpose        : 　キャラクター処理部
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
import android.widget.ImageView;
import android.widget.TextView;

public class Character {

    //　キャラクターコメント更新メソッド
    public void getComment(TextView comment) { //　(TextView)コメント
        CharacterOut out = new CharacterOut();
        //　得たコメントをテキストボックスに表示
        comment.setText(out.readComment());
    }
}
