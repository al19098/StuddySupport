package com.example.studysupport;
//ToDo_Input　入力画面から入力を受け取り渡す　処理部へ?管理部へ?タスク入力

/****************************************************
 *** Module Name    :   ToDo_Setting
 *** Version        :   V1.0
 *** Designer       :   馬場　章
 *** Date           :   2021.07.3
 *** Purpose        :   入力画面処理部
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   馬場　章　2021.07.3
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studysupport.helper.ToDoOpenHelper;

public class ToDo_Input extends AppCompatActivity {

    private View view;
    EditText simekiri; //日付ダイアログのEditText
    InputMethodManager inputMethodManager; // キーボートの有無や背景タッチを可能にするため
    private LinearLayout mainLayout;       //のもの
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (LinearLayout) findViewById(R.id.setting_layout);
        simekiri = (EditText) findViewById(R.id.edit_text2);//日付ダイアログのEditText
    }

    //「一覧画面へ」がクリックされたときリスト画面に移動
    public void tolist1(View view) {

        finish();
    }

    //保存が押されたときデータベースに入力された値を保存
    public void register(View view) {
        EditText task = findViewById(R.id.edit_text1); //タスクの内容
        EditText simekiri = findViewById(R.id.edit_text2);//タスクの締め切り日
        String text = task.getText().toString();       //タスクの内容の長さ
        //入力されたタスクの内容が１文字未満のとき「1文字以上で入力してください」と表示
        if (text.length() < 1) {

            Toast.makeText(ToDo_Input.this, R.string.toast_failed1, Toast.LENGTH_LONG).show();

        } else {
            //データベースに保存
            SQLiteDatabase database = null;

            try {
                ToDoOpenHelper helper = new ToDoOpenHelper(ToDo_Input.this);
                database = helper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("title", task.getText().toString());
                contentValues.put("content", simekiri.getText().toString());

                database.insert("ToDoList", null, contentValues);
                //成功したら「登録しました」，失敗したら「失敗しました」
            } catch (Exception e) {
                Toast.makeText(ToDo_Input.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
            } finally {
                database.close();
            }
            Toast.makeText(ToDo_Input.this, R.string.toast_register, Toast.LENGTH_LONG).show();
        }

    }

    //締め切り日がクリックされたとき日付ダイアログを表示
    public void edit_text2_onClick(View view) {
        final Calendar date = Calendar.getInstance();

        //DatePickerDialogインスタンスを取得
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ToDo_Input.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //setした日付を取得して表示

                        simekiri.setText(String.format("%d,%02d,%02d", year, month + 1, dayOfMonth));

                    }
                },

                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE)
        );


        //dialogを表示
        datePickerDialog.show();

    }


    public boolean onTouchEvent(MotionEvent event) {

// キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
// 背景にフォーカスを移す
        mainLayout.requestFocus();

        return true;
    }


    public void onTapEvent(View view) { //(View)ビュー
        DialogFragment dialogFragment = new popup();
        //　ダイアログをだす
        dialogFragment.show(getSupportFragmentManager(), "popup");
    }
}