/*********************************************
 ***  Module Name		: calendarUI.java
 ***  Version		: V1.0
 ***  Designer		: 山中 祐人
 ***  Date			: 2021.07.03
 ***  Purpose       	: カレンダー画面を表示する
 ********************************************/
package com.example.studysupport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.studysupport.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class calendarUI extends AppCompatActivity {
    Calendar today = Calendar.getInstance();
    int globalyear = today.get(Calendar.YEAR);
    int globalmonth = today.get(Calendar.MONTH)+1;
    int globaldate = today.get(Calendar.DATE);//カレンダーで選択されている年月日

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView cal = findViewById(R.id.Calendar);
        BottomNavigationView menu = findViewById(R.id.bnv);
        menu.getMenu().findItem(R.id.Calendar).setChecked(true);

        //今日のデータの表示
        String txt; //出力内容
        calendarMain data = new calendarMain();//データの取得および追加・削除に用いるクラス
        data.get(globalyear, globalmonth, globaldate);
        //日付の表示
        TextView dateview = (TextView) calendarUI.this.findViewById(R.id.dayView);
        txt = globalyear + "年" + globalmonth + "月" + globaldate + "日";
        dateview.setText(txt);
        //学習時間の表示
        TextView study = (TextView) calendarUI.this.findViewById(R.id.studyTime);
        int hour = 0, minute = 0;//学習時間(hour時間minute分)
        hour = data.studyTime / 60;
        minute = data.studyTime % 60;
        txt = hour + "時間" + minute + "分\n";
        study.setText(txt);
        //todoの表示
        /*
        TextView todo = (TextView) calendarUI.this.findViewById(R.id.todoList);
        txt = "・todo\n";
        if (data.task == null) { //タスクが登録されていない場合
            txt = txt + "タスクはありません。\n";
        } else { //タスクがある場合
            for (int i = 0; i < data.task.size(); i++) {
                txt = txt + data.task.get(i) + "\n";
            }
        }
        todo.setText(txt);
         */
        //イベント情報の表示
        TextView event = (TextView) calendarUI.this.findViewById(R.id.eventView);
        txt = "";
        if (data.event.size() == 0) { //イベントが登録されていない場合
            txt = txt + "イベントはありません。\n";
        } else { //イベントがある場合
            for (int i = 0; i < data.event.size(); i++) {
                txt = txt + data.event.get(i) + "\n";
            }
        }
        event.setText(txt);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int date) {
                String txt;//画面表示するテキスト内容
                globalyear = year;
                globalmonth = month + 1;
                globaldate = date;
                //情報の取得（学習時間・タスク・イベント）
                calendarMain data = new calendarMain();
                //boolean check = data.add(2021, 6, 15, "test");
                data.get(globalyear, globalmonth, globaldate);

                //日付の表示
                //TextView dateview = (TextView) calendarUI.this.findViewById(R.id.dayView);
                txt = globalyear + "年" + globalmonth + "月" + globaldate + "日";
                dateview.setText(txt);

                //学習時間の表示
                //TextView study = (TextView) calendarUI.this.findViewById(R.id.studyTime);
                int hour = 0, minute = 0;//学習時間(hour時間minute分)
                hour = data.studyTime / 60;
                minute = data.studyTime % 60;
                txt = hour + "時間" + minute + "分\n";
                study.setText(txt);

                //todoの表示
                //TextView todo = (TextView) calendarUI.this.findViewById(R.id.todoList);
                /*
                txt = "・todo\n";
                if (data.task == null) { //タスクが登録されていない場合
                    txt = txt + "タスクはありません。\n";
                } else { //タスクがある場合
                    for (int i = 0; i < data.task.size(); i++) {
                        txt = txt + data.task.get(i) + "\n";
                    }
                }
                todo.setText(txt);
                 */

                //イベントの表示
                TextView event = (TextView) calendarUI.this.findViewById(R.id.eventView);
                txt = "";
                if (data.event.size() == 0) { //イベントが登録されていない場合
                    txt = txt + "イベントはありません。\n";
                } else { //イベントがある場合
                    for (int i = 0; i < data.event.size(); i++) {
                        txt = txt + data.event.get(i) + "\n";
                    }
                }
                event.setText(txt);
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.Main:
                        intent = new Intent(calendarUI.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Timer:
                        intent = new Intent(calendarUI.this, MainTimerFrame.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Study:
                        intent = new Intent(calendarUI.this, StudyRecord_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Calendar:
                        return true;
                    case R.id.ToDo:
                        intent = new Intent(calendarUI.this, ToDo_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                }
                return false;
            }
        });
    }
    //イベント情報の追加(addButtonクリック時の動作)
    public void addOnClick(View view) {
        calendarMain cal = new calendarMain();
        cal.get(globalyear,globalmonth,globaldate);
        final EditText editText = new EditText(this); //入力受付用テキストボックス
        TextView event = (TextView) calendarUI.this.findViewById(R.id.eventView); //イベント情報を出力するテキストボックス
        editText.setHint("イベント名");
        AlertDialog.Builder alertadd = new AlertDialog.Builder(this); //アラートダイアログ呼び出し
        alertadd.setTitle("イベントの追加");
        alertadd.setMessage(globalyear+"-"+globalmonth+"-"+globaldate);
        alertadd.setView(editText);
        //完了ボタンクリック時の操作
        alertadd.setPositiveButton("完了",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                if(editText.getText().toString().length() > 20){ //文字数が20文字より多い場合
                    Toast toast = Toast.makeText(calendarUI.this,"イベント名を20文字以下にしてください",Toast.LENGTH_LONG);
                    toast.show();
                }else if(editText.getText().toString().contains("\n")) {//改行文字が含まれている場合
                    Toast toast = Toast.makeText(calendarUI.this, "改行文字を含めないでください", Toast.LENGTH_LONG);
                    toast.show();
                }else if(editText.getText().toString().contains(" ")) {
                    Toast toast = Toast.makeText(calendarUI.this, "スペースを含めないでください", Toast.LENGTH_LONG);
                    toast.show();
                }else if(editText.getText().toString().contains(",")) {
                    Toast toast = Toast.makeText(calendarUI.this, "「,」を含めないでください", Toast.LENGTH_LONG);
                    toast.show();
                }else if(globalyear >= 2100 || globalyear < 2020) {
                    Toast toast = Toast.makeText(calendarUI.this, "範囲外の日付です", Toast.LENGTH_LONG);
                    toast.show();
                } else if(cal.event.size() >= 10) {//登録されているイベントが10個以上の場合
                    Toast toast = Toast.makeText(calendarUI.this, "登録できるイベントは10個までです", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{//正常に入力を受け付けた場合
                    boolean check = cal.add(globalyear, globalmonth, globaldate, editText.getText().toString());
                    cal.get(globalyear, globalmonth, globaldate);
                    String txt = "";
                    for (int i = 0; i < cal.event.size(); i++) {
                        txt = txt + cal.event.get(i) + "\n";
                    }
                    if (!check) {
                        txt = "書き込み失敗";
                    }
                    event.setText(txt);
                }
            }
        });
        alertadd.setNegativeButton("キャンセル", null);
        alertadd.show();
    }

    //イベント情報の削除
    public void deleteOnClick(View view) {
        //イベント情報の取得
        calendarMain cal = new calendarMain();
        cal.get(globalyear,globalmonth,globaldate);

        //リストの生成
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final CheckBox[] checkbox;
        checkbox = new CheckBox[cal.event.size()];
        for(int i = 0; i<cal.event.size(); i++){
            checkbox[i] = new CheckBox(this);
            checkbox[i].setText(cal.event.get(i));
            layout.addView(checkbox[i]);
        }

        //アラートダイアログの設定
        AlertDialog.Builder alertdelete = new AlertDialog.Builder(this);
        alertdelete.setTitle("イベントの削除");
        alertdelete.setMessage(globalyear+"-"+globalmonth+"-"+globaldate);
        alertdelete.setView(layout);
        alertdelete.setPositiveButton("完了", (dialog, which) -> {
            for(int i = 0; i < cal.event.size(); i++){
                //削除処理（チェックがつけられている場合）
                if(checkbox[i].isChecked() == true) {
                    cal.delete(globalyear,globalmonth,globaldate,cal.event.get(i));
                }
            }
            //削除実行後のデータを画面に反映する
            cal.get(globalyear,globalmonth,globaldate);
            String txt = "";
            for (int i = 0; i < cal.event.size(); i++) {
                txt = txt + cal.event.get(i) + "\n";
            }
            TextView event = (TextView) calendarUI.this.findViewById(R.id.eventView); //イベント情報を出力するテキストボックス
            event.setText(txt);
        });
        alertdelete.setNegativeButton("キャンセル", (dialog, which) -> {
        });
        alertdelete.show();
    }

    public void onClickHint(View view){
        final TextView textview = new TextView(this);
        textview.setText("カレンダーで予定を確認したい日付をクリックすると、その日のイベントおよび学習時間、その日が締め切りのTodoが表示されます");
        AlertDialog.Builder alerthint = new AlertDialog.Builder(this);
        alerthint.setTitle("ヒント");
        alerthint.setView(textview);
        alerthint.setNegativeButton("閉じる",null);
        alerthint.show();
    }
}