package com.example.studysupport;

import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class Percentcal {
    int cal() {
        GoalSyori goalsyori = new GoalSyori();
        DailyStudyTime dailystudytime = new DailyStudyTime();
        String goal = goalsyori.getGoal();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));

        int weektime = 0;
        int time = 0;
        double percent = 0.0;
        int intgoal = Integer.parseInt(goal);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                for (int i = 7; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
            case Calendar.MONDAY:
                time = dailystudytime.read(cal);
                if (time < 0) {
                    time = 0;
                }
                weektime = time;
                break;
            case Calendar.TUESDAY:
                for (int i = 2; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
            case Calendar.WEDNESDAY:
                for (int i = 3; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
            case Calendar.THURSDAY:
                for (int i = 4; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
            case Calendar.FRIDAY:
                for (int i = 5; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
            case Calendar.SATURDAY:
                for (int i = 6; i > 0; i--) {
                    time = dailystudytime.read(cal);
                    Log.d("time", "" + time);
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
        }
        //weektime += 1;
        if (intgoal == 0) {
            percent = 0;
        }else{
            percent = 100 * weektime / (intgoal * 60);
        }
        Log.d("percent", weektime + "," + intgoal + "," + percent);
        int per = (int) percent;
        return per;
    }
}
