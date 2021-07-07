package com.example.studysupport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static java.lang.Integer.parseInt;


public class StudyRecord_UI extends AppCompatActivity {
    BarChart chart;
    GoalSyori goalsyori = new GoalSyori();
    Percentcal percentcal = new Percentcal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        TextView goaltext = (TextView) findViewById(R.id.readtime);
        goaltext.setText(goalsyori.getGoal());

        TextView percenttext = (TextView) findViewById(R.id.percenttime);
        percenttext.setText(String.valueOf(percentcal.cal()));

        Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        int[] beforetime3 =new int[3];
        String beforetime1 = goalsyori.getTime();
        Log.d("beforetime1",beforetime1);
        String[] beforetime2 = beforetime1.split("．");
        Log.d("beforetime2" ,beforetime2[0] + beforetime2[1] + beforetime2[2]);
        beforetime3[0] = Integer.parseInt(beforetime2[0]);
        if(beforetime3[0] >= 2021){
            cal2.set(Calendar.YEAR,parseInt(beforetime2[0]));
            cal2.set(Calendar.MONTH,parseInt(beforetime2[1]));
            cal2.set(Calendar.DATE,parseInt(beforetime2[2]));
            if(cal1.get(Calendar.DAY_OF_WEEK) ==Calendar.MONDAY && cal1.after(cal2)) {
                goalsyori.resetGoal("0");
            }
        }

        Button hintbutton = (Button) findViewById(R.id.hintbutton);//リスナーをボタンに登録
        hintbutton.setOnClickListener(new View.OnClickListener() { //確認ボタンが押されたときの処理
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new popup();
                //ダイアログをだす
                dialogFragment.show(getSupportFragmentManager(), "popup");
            }
        });

        Button setgoalbutton = (Button) findViewById(R.id.setgoalbutton);//リスナーをボタンに登録
        setgoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //ボタンが押された時の処理
                Intent intent = new Intent(getApplication(), GoalSet.class); //インテンントの作成
                startActivity(intent);  //画面遷移
            }
        });

        //宣言
        BottomNavigationView menu = findViewById(R.id.bnv);
        menu.getMenu().findItem(R.id.Study).setChecked(true);


//画面遷移
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.Main :
                        intent = new Intent(StudyRecord_UI.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Timer :
                        intent = new Intent(StudyRecord_UI.this, MainTimerFrame.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Study :
                        return true;
                    case R.id.Calendar :
                        intent = new Intent(StudyRecord_UI.this, calendarUI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.ToDo :
                        intent = new Intent(StudyRecord_UI.this, ToDo_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                }
                return false;
            }
        });

        Calendar cl = Calendar.getInstance();

        int month = cl.get(cl.MONTH) + 1;
        Integer m = Integer.valueOf(month);
        String mon = m.toString();

        int today = cl.get(cl.DATE);
        Integer td = Integer.valueOf(today);
        String t = td.toString();

        cl.add(Calendar.DATE, -1);
        int day1 = cl.get(cl.DATE);
        Integer da1 = Integer.valueOf(day1);
        String d1 = da1.toString();

        cl.add(Calendar.DATE, -1);
        int day2 = cl.get(cl.DATE);
        Integer da2 = Integer.valueOf(day2);
        String d2 = da2.toString();

        cl.add(Calendar.DATE, -1);
        int day3 = cl.get(cl.DATE);
        Integer da3 = Integer.valueOf(day3);
        String d3 = da3.toString();

        cl.add(Calendar.DATE, -1);
        int day4 = cl.get(cl.DATE);
        Integer da4 = Integer.valueOf(day4);
        String d4 = da4.toString();

        cl.add(Calendar.DATE, -1);
        int day5 = cl.get(cl.DATE);
        Integer da5 = Integer.valueOf(day5);
        String d5 = da5.toString();

        cl.add(Calendar.DATE, -1);
        int day6 = cl.get(cl.DATE);
        Integer da6 = Integer.valueOf(day6);
        String d6 = da6.toString();

        chart = (BarChart) findViewById(R.id.barchart);

        //表示データ取得
        BarData data = new BarData(getBarData());
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0);
        left.setLabelCount(5);
        left.setDrawTopYLabelEntry(true);

        //Y軸(右)
        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawGridLines(false);
        right.setDrawZeroLine(true);
        right.setDrawTopYLabelEntry(true);

        //X軸
        XAxis xAxis = chart.getXAxis();
        //X軸に表示するLabelのリスト(最初の""は原点の位置)
        int mon1;
        /*if (cl.get(cl.MONTH) == 0) {
            mon1 = 12;
        } else {
            mon1 = cl.get(cl.MONTH);
        }*/
        mon1 = cl.get(cl.MONTH) + 1;
        Integer mon1i = Integer.valueOf(mon1);
        String mon1s = mon1i.toString();

        if (today == 1) {
            final String[] labels = {"", mon1s + "/" + d6, mon1s + "/" + d5, mon1s + "/" + d4, mon1s + "/" + d3, mon1s + "/" + d2, mon1s + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else if (today == 2) {
            final String[] labels = {"", mon1s + "/" + d6, mon1s + "/" + d5, mon1s + "/" + d4, mon1s + "/" + d3, mon1s + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else if (today == 3) {
            final String[] labels = {"", mon1s + "/" + d6, mon1s + "/" + d5, mon1s + "/" + d4, mon1s + "/" + d3, mon + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else if (today == 4) {
            final String[] labels = {"", mon1s + "/" + d6, mon1s + "/" + d5, mon1s + "/" + d4, mon + "/" + d3, mon + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else if (today == 5) {
            final String[] labels = {"", mon1s + "/" + d6, mon1s + "/" + d5, mon + "/" + d4, mon + "/" + d3, mon + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else if (today == 6) {
            final String[] labels = {"", mon1s + "/" + d6, mon + "/" + d5, mon + "/" + d4, mon + "/" + d3, mon + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        } else {
            final String[] labels = {"", mon + "/" + d6, mon + "/" + d5, mon + "/" + d4, mon + "/" + d3, mon + "/" + d2, mon + "/" + d1, mon + "/" + t};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        }
        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setDrawLabels(true);
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setDrawAxisLine(false);

        //グラフ上の表示
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setClickable(false);
    }
    //棒グラフのデータを取得
    private List<IBarDataSet> getBarData() {//表示させるデータ
        Calendar cl=Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        ArrayList<BarEntry> entries = new ArrayList<>();
        DailyStudyTime dailystudytime=new DailyStudyTime();
        for(int i=7;i>0;i--){
            int time=dailystudytime.read(cl);
            //Log.d("studytime", "" + cl + time);
            if(time==-1)
                time=0;
            entries.add(new BarEntry(i,time));
            cl.add(Calendar.DATE,-1);
        }

        List<IBarDataSet> bars = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "学習時間");

        //Barの色をセット
        bars.add(dataSet);

        return bars;
    }
}
