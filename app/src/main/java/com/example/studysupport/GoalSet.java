package com.example.studysupport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.File;


public class GoalSet extends AppCompatActivity {

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goalset);

        Context context = getApplicationContext();
        String filename = "goaltime.txt";
        file = new File(context.getFilesDir(), filename);

        Button ngbutton = (Button) findViewById(R.id.ngbutton);//リスナーをボタンに登録
        ngbutton.setOnClickListener(new View.OnClickListener() { //キャンセルボタンが押されたときの処理
            public void onClick(View view){
                finish(); //メイン画面に戻る
            }
        });
        Button okbutton = (Button) findViewById(R.id.okbutton);//リスナーをボタンに登録
        okbutton.setOnClickListener(new View.OnClickListener(){ //確認ボタンが押されたときの処理
            @Override
            public void onClick(View view){
                EditText goaltime = (EditText) findViewById(R.id.inputtime); //EditTextで入力された値を受け取る
                String str = goaltime.getText().toString();
                int inttime = Integer.parseInt(str);
                //入力された時間が168時間より大きかった場合
                if(inttime > 168){
                    DialogFragment dialogFragment = new TimeAlertDialog();
                    dialogFragment.show(getSupportFragmentManager(),"Timeerrordialog");
                } else { //入力された時間が168時間以下だった場合
                    GoalSyori goalsyori = new GoalSyori();
                    goalsyori.setGoal(str);//入力された値をファイルに入力
                    Intent intent = new Intent(GoalSet.this,StudyRecord_UI.class);
                    intent.putExtra("goaltime",str);
                    //StudyRecord＿UIに戻る
                    startActivity(intent);
                }
            }


        });
    }
}