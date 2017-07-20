package com.sunasteffen.mytimer;

import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {
    public static final String TIME_IN_MILLIS_KEY = "TIME_IN_MILLIS_KEY";
    public static final String START_TIMER_KEY = "START_TIMER_KEY";
    @BindView(R.id.timer_progress_bar)
    ProgressBar timerProgressBar;
    @BindView(R.id.delete_timer_button)
    TextView deleteButton;
    @BindView(R.id.play_pause_fab)
    FloatingActionButton playPauseFab;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);

        final long duration = getIntent().getLongExtra(TIME_IN_MILLIS_KEY, 0L);

        if (duration != 0L && getIntent().getBooleanExtra(START_TIMER_KEY, true)) {
            startTimer(duration);
        } else {
            timerProgressBar.setProgress(500);
            playPauseFab.setVisibility(View.VISIBLE);
        }

        playPauseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(duration);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTimer();
            }
        });
    }

    private void deleteTimer() {
        alarmMgr.cancel(alarmIntent);
        finish();
        startActivity(new Intent(TimerActivity.this, MainActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        playPauseFab.setVisibility(intent.getBooleanExtra(START_TIMER_KEY, true) ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteTimer();
    }

    private void startTimer(long duration) {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimerAlarmReceiver.class);
        intent.putExtra(TIME_IN_MILLIS_KEY, duration);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + duration, alarmIntent);

        ObjectAnimator animation = ObjectAnimator.ofInt(timerProgressBar, "progress", 0, 500);
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
        playPauseFab.setVisibility(View.INVISIBLE);
    }
}
