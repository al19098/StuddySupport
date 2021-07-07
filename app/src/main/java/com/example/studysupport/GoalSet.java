/********************************************************************
 ***  ModuleName  :GoalSet.java
 ***  Version     :V1.1
 ***  Designer    :桑原　赳
 ***  Date        :2021.06.30
 ***  Purpose     :目標設定画面での処理
 ***
 ********************************************************************/
/*
 ***Revision :
 *** V1.0 : 桑原赳 2021.06.19
 *** V1.1 : 桑原赳 2021.06.30 改訂内容
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
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

        // キャンセルボタンを登録、ボタンが押されたときの処理
        Button ngbutton = (Button) findViewById(R.id.ngbutton);
        ngbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish(); //メイン画面に戻る
            }
        });
        // 確認ボタンを登録、ボタンが押されたときの処理
        Button okbutton = (Button) findViewById(R.id.okbutton);
        okbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText goaltime = (EditText) findViewById(R.id.inputtime); // 入力された値
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
                    startActivity(intent);
                }
            }
        });
    }
}