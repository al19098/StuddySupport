/****************************************************
 *** Module Name    :   ToDo_UI
 *** Version        :   V1.0
 *** Designer       :   馬場　章
 *** Date           :   2021.07.3
 *** Purpose        :   ToDoリスト表示画面処理部
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   馬場　章　2021.07.3
 */
//ToDO_UI　メイン画面リストの表示消去

package com.example.studysupport;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;

import com.example.studysupport.helper.ToDoOpenHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ToDo_UI extends
        android.app.ListActivity {

    Cursor cursor;      //データベースへの要求結果

    private long a = 0;     //listの仮ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        BottomNavigationView menu = findViewById(R.id.bnv);
        menu.getMenu().findItem(R.id.ToDo).setChecked(true);



        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.Main:
                        intent = new Intent(ToDo_UI.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Timer:
                        intent = new Intent(ToDo_UI.this, MainTimerFrame.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Study:
                        intent = new Intent(ToDo_UI.this, StudyRecord_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Calendar:
                        intent = new Intent(ToDo_UI.this, calendarUI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.ToDo:
                        return true;
                }
                return false;
            }
        });

    }
   //データベースと接続，リストの表示
    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase database = null;
        try {
            SQLiteOpenHelper helper = new ToDoOpenHelper(ToDo_UI.this);
            database = helper.getReadableDatabase();

            cursor = database.query("ToDoList", null, null, null, null, null, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(ToDo_UI.this, R.layout.list_row, cursor, new String[]{"title"}, new int[]{R.id.list_row1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            database.close();
        }
    }
//タスクがクリックされたら編集画面に移動する
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(ToDo_UI.this, ToDo_Setting.class);
        intent.putExtra("id", id);
        startActivity(intent);
        a = id;
    }


    //    TextView userIdTextView = ((View) view.getParent()).findViewById(R.id.list_row1);
    //  String userIdStr = userIdTextView.getText().toString();
    //Log.d("select_id", "選択したidは" + userIdStr);

//タスクの完了ボタンが押された場合そのタスクを消す，未完成
    public void deleteTask(View view) {

        View parent = (View) view.getParent();

        View i = (View) view;
        //View parent2=(View) parent.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.list_row1);

        String task = String.valueOf(taskTextView.getText());

        SQLiteOpenHelper helperDelete = null;
        SQLiteDatabase databaseDelete = null;


        try {
            //データベース読み込み
            helperDelete = new ToDoOpenHelper(ToDo_UI.this);
            databaseDelete = helperDelete.getWritableDatabase();

            int deleteCount = databaseDelete.delete("ToDoList", "_id=?", new String[]{String.valueOf(a)});
            //消去成功時に「消去しました」と表示，失敗は「登録できませんでした」
            if (deleteCount == 1) {
                Toast.makeText(ToDo_UI.this, R.string.toast_delete, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ToDo_UI.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
            }
            //画面のリロード
            onResume();
            //例外処理
        } catch (Exception e) {

            Log.e("エラー", e.getMessage(), e);
        } finally {
            //dbクローズ
            databaseDelete.close();
        }
    }
 //作成ボタンが押されたとき入力画面に移動する
    public void sakusei_onClick(View v) {
        Intent intent = new Intent(this, ToDo_Input.class);
        startActivityForResult(intent, 1);
    }

//移動時にdbから切断
    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null || !cursor.isClosed()) {
            cursor.close();
        }
    }


    /*public void onTapEvent(View view) { //(View)ビュー

        DialogFragment dialogFragment = new popup();
        //　ダイアログをだす
        dialogFragment.show(getFragmentManager(),  );
    }

*/}



    //画面遷移


