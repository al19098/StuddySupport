/********************************************************************
 ***  ModuleName  :Percentcal.java
 ***  Version     :V1.1
 ***  Designer    :桑原　赳
 ***  Date        :2021.07.03
 ***  Purpose     :週間学習記録処理
 ***
 ********************************************************************/
/*
 ***Revision :
 *** V1.0 : 桑原赳 2021.06.30
 *** V1.1 : 桑原赳,大谷真由,川田紗英花 2021.07.03 改訂内容 switch文の中、最後の計算
 */

package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
import java.util.Calendar;
import java.util.TimeZone;

public class Percentcal {
    int cal() {
        GoalSyori goalsyori = new GoalSyori();
        DailyStudyTime dailystudytime = new DailyStudyTime();
        String goal = goalsyori.getGoal(); // 週間目標
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));

        int weektime = 0;
        int time = 0;
        double percent = 0.0;
        int intgoal = Integer.parseInt(goal);
        //曜日ごとにswitch文で日別学習時間の取得回数分岐
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
                    if (time < 0) {
                        time = 0;
                    }
                    weektime += time;
                    cal.add(Calendar.DATE, -1);
                }
                break;
        }
        if (intgoal == 0) {
            percent = 0;
        }else{
            percent = 100 * weektime / (intgoal * 60);
        }
        int per = (int) percent;
        return per;
    }
}
