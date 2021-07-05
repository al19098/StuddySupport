/*******************************************************************
 ***  Module Name		: popup.java
 ***  Version		: V1.0
 ***  Designer		: 平井賢誠
 ***  Date			: 2021.6.16
 ***  Purpose       	: ダイアログの生成
 ***
 *******************************************************************/
/*
 *** Revision :
 *** V1.1   :   大谷 真由, 2021.06.29, コンストラクタ，setTextメソッド追加
 */
package com.example.studysupport;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class popup extends DialogFragment {
    String txt = ""; // ダイアログの文章

    // コンストラクタ
    popup () {

    }
    popup(String str) {
        txt = str;
    }

    @Override
    //ダイアログを表示させるためのメソッド
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログビルダーを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //ダイアログのタイトルを設定
        builder.setTitle("ヒント");
        //ダイアログのメッセージを設定
        builder.setMessage(txt);
        //okボタン(押したら元の画面に戻る用のボタン）の生成
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            // OKが押下された際の処理
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//okボタンを押すと消える
            }
        });
        //ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();
        return dialog;
    }

    // ダイアログの文章を設定
    public void setText(String str) {
        txt = str;
    }


}