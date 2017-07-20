package com.sunasteffen.mytimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.add_timer_fab)
    FloatingActionButton addTimerFab;
    @BindView(R.id.time_edit_text)
    EditText timeEt;
    @BindView(R.id.delete_time_button)
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        timeEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        timeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateAddTimerFabVisibility();
            }
        });
        timeEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                startTimerActivity();
                return true;
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEt.setText(null);
                updateAddTimerFabVisibility();
            }
        });

        addTimerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerActivity();
            }
        });
    }

    private void startTimerActivity() {
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        intent.putExtra(TimerActivity.TIME_IN_MILLIS_KEY, Long.valueOf(timeEt.getText().toString()) * 1000L);
        startActivity(intent);
    }

    private void updateAddTimerFabVisibility() {
        addTimerFab.setVisibility(timeEt.getText().toString().isEmpty() ? View.INVISIBLE : View.VISIBLE);
    }
}
