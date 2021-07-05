/**************************************************************************
 ***  Module Name		: manageCalendar.java
 ***  Version		: V1.0
 ***  Designer		: 山中 祐人
 ***  Date			: 2021.07.03
 ***  Purpose       	: イベント情報ファイルの管理を行う（データの取得・追加・削除）
 *************************************************************************/
package com.example.studysupport;


import android.content.Context;
import android.util.Log;

import  java.io.*;
import java.nio.charset.StandardCharsets;
import  java.util.*;

public class manageCalendar {
  static String filename = "eventfile.csv";

  //イベント情報の取得
  List<String> get(int year, int month, int day) {
    List<String> event = new ArrayList<String>();
    String line;
    new AppContext();
    Context context = AppContext.getAppContext();
    File file = new File(context.getFilesDir(), filename);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        if (Integer.parseInt(data[0]) == year && Integer.parseInt(data[1]) == month && Integer.parseInt(data[2]) == day) {
          event.add(data[3]);
        }
      }
      br.close();
      Log.d("calendar","get manage");
    } catch (IOException e) {
      event.add("ファイル読み込み失敗");
      Log.d("calendar","get failed");
    }finally {
      return event;
    }
  }

  boolean add(int year,int month,int day, String event_name){
    String txt = year + "," + month + "," + day + "," + event_name;
    new AppContext();
    Context context = AppContext.getAppContext();
    File file = new File(context.getFilesDir(), filename);
    try{
      OutputStream os = AppContext.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE|Context.MODE_APPEND);
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
      pw.println(txt);
      Log.d("calendar",txt);
      pw.close();
      return true;
    }catch(IOException e){
      Log.d("calendar","add failed");
        return false;
    }
  }

  boolean delete(int year,int month,int day,String event_name){
    boolean check = false;
    List<String> save = new ArrayList<>();
    String line;
    new AppContext();
    Context context = AppContext.getAppContext();

    File file = new File(context.getFilesDir(), filename);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        if (Integer.parseInt(data[0]) != year || Integer.parseInt(data[1]) != month || Integer.parseInt(data[2]) != day || !(event_name.matches(data[3]))){
          save.add(line);
        }
      }
      OutputStream os = AppContext.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
      for(int i = 0; i < save.size(); i++){
        pw.println(save.get(i));
      }
      pw.close();
      br.close();
      check = true;
    } catch (IOException e) {
      check = false;
    }finally{
      return check;
    }
  }
}
