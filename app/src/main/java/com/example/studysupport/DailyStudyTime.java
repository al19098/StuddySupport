/****************************************************
 *** Module Name    :   DailyStudyTime.java
 *** Version        :   V1.0
 *** Designer       :   大谷 真由
 *** Date           :   2021.06.14
 *** Purpose        :   学習時間の書き込み・読み出し
 ***
 ***************************************************/
/*
 *** Revision    :
 *** V1.0        :   大谷 真由, 2021.06.14, 作成
 *** V1.1        :   大谷 真由, 2021.06.28, メソッド名変更
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

// 日付と学習時間を格納する
class StudyTime{
    Calendar date;  // 日付
    int time;   // 学習時間
}

public class DailyStudyTime {
    static List<StudyTime> studylist = new ArrayList<StudyTime>();  // 日付と学習時間のリスト
    static String filename = "dailystudyfile.csv";  // 日別学習時間ファイル名
    TimeZone timezone = TimeZone.getTimeZone("Asia/Tokyo"); //タイムゾーン設定(東京)

    // ファイル読み込み
    private void readFile(){
        String line;    // 読みだした行を格納する
        new AppContext();   // AppContextインスタンス
        Context context = AppContext.getAppContext();   // コンテクストを取得
        File file = new File(context.getFilesDir(), filename);  // 内部ストレージ内のファイルを取得

        // ファイル読み込み
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((line = br.readLine()) != null) {
                StudyTime study = new StudyTime();
                Calendar cal = Calendar.getInstance(timezone);
                String[] split = line.split(",");
                cal.clear();
                cal.set(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1, Integer.parseInt(split[2]),
                        0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                study.date = cal;
                study.time = Integer.parseInt(split[3]);
                studylist.add(study);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
            // ファイルが存在しなかった場合はその日の日付+0分の記録を追加する
            try{
                FileWriter writer = new FileWriter(file);
                PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
                Calendar today = Calendar.getInstance(timezone);
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                StudyTime study = new StudyTime();
                study.date = today;
                study.time = 0;
                studylist.add(study);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
                df.setTimeZone(timezone);
                pw.println(df.format(study.date.getTime())+ "," + study.time);
                Log.i("write", df.format(study.date.getTime())+ "," + study.time);
                pw.close();
            }catch (Exception e2){
                e.printStackTrace();
            }
        }
    }

    // ファイル書き込み(成功:true，失敗:falseを返す)
    boolean write(int studytime){   //学習時間
        Calendar today = Calendar.getInstance(timezone);    // 今日の日付を取得
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        boolean issucceeded = false;    // 保存できたかどうか

        // 学習時間が負の値の場合は保存しない
        if (studytime < 0) {
            return issucceeded;
        }
        if (studylist.size() == 0){
            readFile();
        }
        if (studylist.size() == 0) {
            StudyTime study = new StudyTime();
            study.date = today;
            study.time = 0;
            studylist.add(study);
        }

        // 現在のデータと比較して日付が一致した場合は学習時間を加算，一致しなければ日付と時間を登録
        int num = studylist.size() - 1; // リストのインデックスの最大値
        if (studylist.get(num).date.compareTo(today) == 0) {
            studylist.get(num).time += studytime;
        }else{
            StudyTime todaystudy = new StudyTime();
            todaystudy.date = today;
            todaystudy.time = studytime;
            studylist.add(todaystudy);
        }

        // ファイルに書き出す
        try {

            OutputStream os = AppContext.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

            for (int i = 0; i < studylist.size(); i++){
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
                df.setTimeZone(timezone);
                pw.println(df.format(studylist.get(i).date.getTime()) + "," + studylist.get(i).time);
                //pw.println(df.format(studylist.get(i).date.getTime()) + "," + 120);
                Log.i("write", df.format(studylist.get(i).date.getTime()) + "," + studylist.get(i).time);
            }
            pw.close();
            issucceeded = true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return issucceeded;
    }

    //  ファイル読み出し
    int read(Calendar day){ // 参照する日付
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        int time = -1; // 学習時間
        if (studylist.size() == 0){
            readFile();
        }
        for (int i = 0; i < studylist.size(); i++){
            if(studylist.get(i).date.compareTo(day) == 0){
                time = studylist.get(i).time;
            }
        }

        // 読み出した時間を返す(該当する日付がなければ-1)
        return time;
    }
}
