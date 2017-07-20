package com.sunasteffen.mytimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimerAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToStart = new Intent(context, TimerActivity.class);
        intentToStart.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentToStart.putExtra(TimerActivity.TIME_IN_MILLIS_KEY, intent.getLongExtra(TimerActivity.TIME_IN_MILLIS_KEY, 0L));
        intentToStart.putExtra(TimerActivity.START_TIMER_KEY, false);
        context.startActivity(intentToStart);
    }
}
