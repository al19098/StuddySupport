/****************************************************
 *** Module Name    :   ToDo_Setting
 *** Version        :   V1.0
 *** Designer       :   馬場　章
 *** Date           :   2021.07.3
 *** Purpose        :   データの管理をするdb
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   馬場　章　2021.07.3
 */

package com.example.studysupport.helper;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATABASE";
    public static final int VERSION = 1;
    public static final String CREATE_TABLE = "CREATE TABLE 'ToDoList' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT , 'title' TEXT , 'content' TEXT);";//titleがタスクの内容,contentが締め切り日

    public ToDoOpenHelper(Context context1) {
        super(context1, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}