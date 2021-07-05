/****************************************************
 *** Module Name    :   MainActivity.java
 *** Version        :   V1.1
 *** Designer       :   川田　紗英花
 *** Date           :   2021.07.03
 *** Purpose        :   メイン画面のUI処理
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   川田　紗英花, 2021.06.28, 作成
 *** V1.1        :   川田　紗英花, 2021.07.03, 円グラフの作成
 */


package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView textView; //　大きな目標
    private TextView textNext; //　次の目標
    private TextView comment;  //　キャラのコメント
    private TextView pie;      //　達成率表示
    Mokuhyo mokuhyo = new Mokuhyo();
    Character chara = new Character();
    Percentcal percentCal = new Percentcal();
    int percent = percentCal.cal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //　(初期処理)大きな目標表示
        textView = findViewById(R.id.bigMokuhyo);
        textView.setText(mokuhyo.getMokuhyo());

        //　(初期処理)次の目標パーセント表示
        textNext = findViewById(R.id.textNext);
        int next; // 残り%
        if(percent < 100) {
            if (percent < 26) {
                next = 25 - percent;
            } else if (percent < 51) {
                next = 50 - percent;
            } else if (percent < 76) {
                next = 75 - percent;
            } else {
                next = 100 - percent;
            }
            textNext.setText("次の目標まで　" + next + "%");
        }else{
            textNext.setText("目標達成！");
        }

        //　(初期処理)達成率表示
        pie = findViewById(R.id.pieChart);
        pie.setText(percent + "%");

        // (初期処理)キャラの画像設定
        ImageButton imgbutton; // キャラ画像ボタン
        imgbutton = findViewById(R.id.imageButton);

        if (percent < 25) {
            imgbutton.setImageResource(R.drawable.chara0);
        } else if (percent < 50) {
            imgbutton.setImageResource(R.drawable.chara1);
        } else if (percent < 75) {
            imgbutton.setImageResource(R.drawable.chara2);
        } else if (percent < 100){
            imgbutton.setImageResource(R.drawable.chara3);
        } else {
            imgbutton.setImageResource(R.drawable.chara4);
        }

        //　(初期処理)コメント表示
        comment = findViewById(R.id.Comment);
        chara.getComment(comment);

        //　大きい目標更新処理
        ((Button) findViewById(R.id.mokuhyo)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    //　ボックスをタッチ
                    public void onClick(View v) {
                        final EditText bigMokuhyo = new EditText(MainActivity.this);    //　入力文字
                        //　ダイアログ表示(画像、タイトル、入力文字表示)
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setIcon(android.R.drawable.ic_dialog_info);
                        builder.setTitle("目標を入力してください");
                        builder.setView(bigMokuhyo);
                        //　確定ボタン押下
                        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //　入力された文字列を取得しtextに代入
                                String text = bigMokuhyo.getText().toString();
                                //　21文字以上の場合トーストを表示
                                if (text.length() > 20) {
                                    Toast myToast = Toast.makeText(
                                            getApplicationContext(),
                                            "20文字以内で入力してください",
                                            Toast.LENGTH_SHORT
                                    );
                                    myToast.show();
                                } else {
                                    //　20文字以下なら文字列を保存、ボックスに表示
                                    mokuhyo.setMokuhyo(text);
                                    textView.setText(text);
                                }
                            }
                        })
                                //　キャンセルボタン押下　何もせず閉じる
                                .setNegativeButton("キャンセル", null)
                                .show();
                    }
                });
        //宣言
        BottomNavigationView menu = findViewById(R.id.bnv);
        menu.getMenu().findItem(R.id.Main).setChecked(true);


        //画面遷移
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.Main:
                        return true;
                    case R.id.Timer:
                        intent = new Intent(MainActivity.this, MainTimerFrame.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Study:
                        intent = new Intent(MainActivity.this, StudyRecord_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Calendar:
                        intent = new Intent(MainActivity.this, calendarUI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.ToDo:
                        intent = new Intent(MainActivity.this, ToDo_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                }
                return false;
            }
        });
    }

    //　キャラタッチ処理のメソッド
    public void onTapChara(View view) { //(View)ビュー
        //　コメントを更新
        chara.getComment(comment);
    }

    //　ヘルプボタン処理のメソッド
    public void onTapEvent(View view) { //(View)ビュー
        popup pop = new popup(
                "画面上部のボックスには最終的な目標を20文字以内で入力してください。\n" +
                        "\n" +
                        "\n" +
                        "週間の達成率に応じてキャラの服装や見た目が変化します。\n" +
                        "次の変化までの達成率はキャラの右下の欄に表示されています。\n" +
                        "また、キャラ上をタッチするとコメントが変化します"
        );
        //　ダイアログをだす
        pop.show(getSupportFragmentManager(), "popup");
    }
}