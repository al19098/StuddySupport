package com.example.studysupport;

public class GoalSyori {
    //　ファイルから目標を読み込み出力する
    String getGoal() {
        String text;
        Managegoal read = new Managegoal();
        text = read.readGoal();
        return text;
    }
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
    void resetGoal(String text){
        Managegoal reset = new Managegoal();
        reset.resetTime(text);
    }
}
