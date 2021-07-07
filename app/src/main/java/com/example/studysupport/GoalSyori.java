/********************************************************************
 ***  ModuleName  :GoalSyori.java
 ***  Version     :V1.1
 ***  Designer    :桑原　赳
 ***  Date        :2021.07.05
 ***  Purpose     :週間学習目標処理部
 ***
 ********************************************************************/
/*
 ***Revision :
 *** V1.0 : 桑原赳 2021.06.23
 *** V1.1 : 桑原赳 2021.07.05 改訂内容 resetGoal
 */
package com.example.studysupport;

public class GoalSyori {
    // ファイルから目標を読み込むメソッド
    String getGoal() {
        String goaltime;
        Managegoal read = new Managegoal();
        goaltime = read.readGoal();
        return goaltime;
    }
    // ファイルから目標を入力した時間を読み込むメソッド
    String getTime() {
        String time;
        Managegoal read = new Managegoal();
        time = read.readTime();
        return time;
    }
    //ファイルに目標を保存する
    void setGoal(String text){
        Managegoal save = new Managegoal();
        save.saveTime(text);
    }
    // ファイルの目標をリセット(0を書き込む)するメソッド
    void resetGoal(String text){
        Managegoal reset = new Managegoal();
        reset.resetGoal(text);
    }
}
