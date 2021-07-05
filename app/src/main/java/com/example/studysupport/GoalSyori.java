package com.example.studysupport;

public class GoalSyori {
    //　ファイルから目標を読み込み出力する
    String getGoal() {
        String text;
        Managegoal read = new Managegoal();
        text = read.readFile();
        return text;
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
