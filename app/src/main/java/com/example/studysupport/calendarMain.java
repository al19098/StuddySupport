/****************************************************************
 ***  Module Name		: calendarMain.java
 ***  Version		: V1.0
 ***  Designer		: 山中 祐人
 ***  Date			: 2021.07.03
 ***  Purpose       	: カレンダー画面に必要なデータのやりとりを仲介する
 *****************************************************************/
package com.example.studysupport;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import  java.io.*;
import  java.util.*;

public class calendarMain {
    List<String> event = new ArrayList<String>();//イベント情報格納場所
    int studyTime = 0;//学習時間格納場所
    List<String> task = new ArrayList<String>();//todoリスト情報格納場所
    boolean check = false;//データの追加,削除の成否結果

    //学習時間,todoリスト,イベント情報の取得
    void get(int year,int month, int day){
        manageCalendar cal = new manageCalendar();
        event = cal.get(year,month,day);
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        date.set(year, month - 1, day, 0, 0);
        DailyStudyTime s = new DailyStudyTime();
        studyTime = s.read(date);
        if (studyTime < 0) {
            studyTime = 0;
        }
        //Manage_ToDo t = new Manage_ToDo();
        //task = Manage_ToDo.getTask(date);

    }
    //イベント情報の追加
    boolean add(int year, int month, int day, String event_name){
        boolean check = false;
        manageCalendar cal = new manageCalendar();
        check = cal.add(year,month,day,event_name);
        return check;
    }
    //イベント情報の削除
    boolean delete(int year,int month,int day,String event_name){
        manageCalendar cal = new manageCalendar();
        return cal.delete(year,month,day,event_name);
    }
}
