/****************************************************
 *** Module Name    :   ToDo_Setting
 *** Version        :   V1.0
 *** Designer       :   馬場　章
 *** Date           :   2021.07.3
 *** Purpose        :   編集画面処理部
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   馬場　章　2021.07.3
 */

package com.example.studysupport;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
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



public class ToDo_Setting extends AppCompatActivity {

    InputMethodManager inputMethodManager; //キーボートの有無や背景タッチを可能にするため
    private LinearLayout mainLayout;       //の変数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mainLayout = (LinearLayout) findViewById(R.id.content_layout);


    }

    //締め切りのEditTextがクリックされたとき日付選択のダイアログを表示する
    public void simekiri_Click(View v) {

        //
        final Calendar date = Calendar.getInstance();
        EditText simekiri = (EditText) findViewById(R.id.text_content);
        //DatePickerDialogインスタンスを取得
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ToDo_Setting.this,
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

    //編集された値をデータベースに保存
    @Override
    protected void onResume() {
        super.onResume();
        Long id = getIntent().getLongExtra("id", 0L);//リスト画面でタッチされたタスクのid
        //データベースの設定
        SQLiteOpenHelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        //データベースに保存
        try {
            helper = new ToDoOpenHelper(ToDo_Setting.this);
            database = helper.getReadableDatabase();

            cursor = database.query("ToDoList", null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
            //カーソルの移動
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("title");//タスクの内容
                String strTitle = cursor.getString(columnIndex); //セルの列
                columnIndex = cursor.getColumnIndex("content");//タスクの締め切り日
                String strContent = cursor.getString(columnIndex);

                EditText titleView = findViewById(R.id.text_title);
                EditText contentView = findViewById(R.id.text_content);
                titleView.setText(strTitle);


                contentView.setText(strContent);
                contentView.setText(strContent);
            }
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            //データベースの切断
            cursor.close();
            database.close();
        }
    }

//更新ボタンが押されたとき入力された情報をデータベースに更新する
    public void update(View view) {

        SQLiteOpenHelper helperUpdate = null;
        SQLiteDatabase databaseUpdate = null;

        String task = ((EditText) findViewById(R.id.text_title)).getText().toString();//タスクの長さ
        String simekiri = ((EditText) findViewById(R.id.text_content)).getText().toString();//締め切り日の長さ
        //タスクに入力された文字数が1未満のとき「一文字以上にしてくださいと表示」
      /*  if (task.length() < 1) {


            Toast.makeText(ToDo_Setting.this, R.string.toast_failed1, Toast.LENGTH_LONG).show();
        }else{*/
        try {
          //データベースの更新
            helperUpdate = new ToDoOpenHelper(ToDo_Setting.this);
            databaseUpdate = helperUpdate.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("title", task);
            contentValues.put("content", simekiri);

            int updateCount = databaseUpdate.update("ToDoList", contentValues, "_id=?", new String[]{String.valueOf(getIntent().getLongExtra("id", 0L))});
            //更新に成功したら「更新しました」，失敗したら「登録できませんでした」
            if (updateCount == 1) {
                Toast.makeText(ToDo_Setting.this, R.string.toast_update, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ToDo_Setting.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
            }
            finish(); //リスト表示画面にもどる
            //データベースのエラー処理，切断
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            databaseUpdate.close();
        }
    }


//タスクの削除
    public void delete(View view) {

        SQLiteOpenHelper helperDelete = null;
        SQLiteDatabase databaseDelete = null;
        //データべースに接続
        try {
            helperDelete = new ToDoOpenHelper(ToDo_Setting.this);
            databaseDelete = helperDelete.getWritableDatabase();

            int deleteCount = databaseDelete.delete("ToDoList", "_id=?", new String[]{String.valueOf(getIntent().getLongExtra("id", 0L))});
          //消去に成功したら「削除しました」失敗したら「登録できませんでした」
            if (deleteCount == 1) {
                Toast.makeText(ToDo_Setting.this, R.string.toast_delete, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ToDo_Setting.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
            }
            finish();//リスト表示画面にもどる
            //データベースのエラー処理,切断
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            databaseDelete.close();
        }

    }
//ダイアログ入力字のエラー対応
    public boolean onTouchEvent(MotionEvent event) {

// キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
// 背景にフォーカスを移す
        //mainLayout.requestFocus();

        return true;
    }
}