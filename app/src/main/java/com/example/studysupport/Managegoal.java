/********************************************************************
 ***  ModuleName  :Managegoal.java
 ***  Version     :V1.1
 ***  Designer    :桑原　赳
 ***  Date        :2021.07.05
 ***  Purpose     :週間学習目標管理
 ***
 ********************************************************************/
/*
 ***Revision :
 *** V1.0 : 桑原赳 2021.06.23
 *** V1.1 : 桑原赳,大谷真由 2021.07.05 改訂内容 全メソッド
 */
package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.TimeZone;

public class Managegoal {
    TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
    Context context = AppContext.getAppContext(); // コンテキスト
    String filename = "goaltime.txt";             // ファイル名
    // ファイル作成
    File file = new File(context.getFilesDir(), filename);

    //　週間目標読み込みメソッド
    public String readGoal() {
        String goaltime = null;
        String goal = null;
        try {
            //　内部ストレージのファイルから読み込み
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            goaltime = br.readLine();
            String[] array = goaltime.split(",");
            goal = array[1]; // 日別学習時間
            br.close();
        } catch (IOException e1) { //ファイルがない場合
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("0．0．0,0");
                goal = "0";
            }catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return goal;
    }
    //　週間目標入力読み込みメソッド
    public String readTime() {
        String goaltime = null;
        String time = null;
        try {
            //　内部ストレージのファイルから読み込み
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            goaltime = br.readLine();
            String[] array = goaltime.split(",");
            time = array[0]; //目標入力時間
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }
    //　週間目標書き込みメソッド
    public void saveTime(String str) {//入力された値をファイルに保存する
        String timegoal; //書き込む内容
        try (FileWriter writer = new FileWriter(file)) {
            Calendar cal =  Calendar.getInstance(tz);
            int[] ymd = new int[3];
            ymd[0]=cal.get(Calendar.YEAR);
            ymd[1]=cal.get(Calendar.MONTH) + 1;
            ymd[2]=cal.get(Calendar.DATE);
            timegoal = ymd[0] + "．" +ymd[1] +  "．" + ymd[2] + "," + str;
            writer.write(timegoal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //　週間目標リセット(0書き込み)メソッド
    public void resetGoal(String str) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}