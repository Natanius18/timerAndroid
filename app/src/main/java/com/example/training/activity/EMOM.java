package com.example.training.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.example.training.R;
import com.example.training.aditional.RepeatListener;
import com.example.training.aditional.ShowTime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EMOM extends Activity {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "ClickableViewAccessibility"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emom);

        final TimePicker timePicker = findViewById(R.id.timePicker);
        final TextView timeLeft = findViewById(R.id.timeLeft);
        final EditText setInput = findViewById(R.id.editTextNumber);
        final Button startTimer = findViewById(R.id.start);

        final FloatingActionButton plus = findViewById(R.id.ButtonPLUS);
        plus.setOnTouchListener(new RepeatListener(300, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setInput.getText().toString().isEmpty()) {
                    setInput.setText(String.valueOf(1));
                } else {
                    setInput.setText(String.valueOf(Integer.parseInt(setInput.getText().toString()) + 1));
                }
                showTotalTime(timePicker, setInput, timeLeft);
            }

        }));

        final FloatingActionButton minus = findViewById(R.id.ButtonMinus);
        minus.setOnTouchListener(new RepeatListener(300, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!setInput.getText().toString().isEmpty()
                        && Integer.parseInt(setInput.getText().toString()) > 1) {
                    setInput.setText(String.valueOf(Integer.parseInt(setInput.getText().toString()) - 1));
                    showTotalTime(timePicker, setInput, timeLeft);
                }
            }
        }));

        timePicker.setHour(1);
        timePicker.setMinute(0);
        timePicker.setIs24HourView(true);

        setInput.setText(String.valueOf(10));
        showTotalTime(timePicker, setInput, timeLeft);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeChanged(TimePicker view, int min, int sec) {
                showTotalTime(timePicker, setInput, timeLeft);
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int circles = 1;
                if (!setInput.getText().toString().isEmpty()) {
                    circles = Integer.parseInt(setInput.getText().toString());
                }
                long secondsInOneSet = timePicker.getHour() * 60000L + timePicker.getMinute() * 1000L;
                Intent intent = new Intent(EMOM.this, ActivityTimer.class);
                intent.putExtra("mills", circles * secondsInOneSet);
                intent.putExtra("circles", circles);
                startActivity(intent);
            }
        });


        final Switch vibroSwitch = findViewById(R.id.switch1);
        vibroSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (checked) {
                    ShowTime.setVibro(1);
                    vibroSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#0BAA18")));
                } else {
                    ShowTime.setVibro(0);
                    vibroSwitch.setThumbTintList(ColorStateList.valueOf(Color.WHITE));

                }
            }
        });

        final Switch metronomSwitch = findViewById(R.id.switch2);
        metronomSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (checked) {
                    ShowTime.setMetronom(true);
                    metronomSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#0BAA18")));
                } else {
                    ShowTime.setMetronom(false);
                    metronomSwitch.setThumbTintList(ColorStateList.valueOf(Color.WHITE));

                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityTimer.getTimer() != null) {
            ActivityTimer.getTimer().cancel();
            ShowTime.setI(0);
            ((RadioButton) findViewById(R.id.radioButton3)).setChecked(true);
            onRadioButtonClicked(findViewById(R.id.radioButton3));
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showTotalTime(TimePicker timePicker, EditText setInput, TextView setsAmount) {
        if (!setInput.getText().toString().isEmpty()) {
            int secondsTotal = Integer.parseInt(setInput.getText().toString()) *
                    (timePicker.getHour() * 60 + timePicker.getMinute());
            int seconds = secondsTotal % 60;
            int minutes = secondsTotal / 60;
            setsAmount.setText("Общее время тренировки\n" +
                    (minutes < 10 ? "0" + minutes : minutes)
                    + ":"
                    + (seconds < 10 ? "0" + seconds : seconds));
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton3:
                if (checked) {
                    ShowTime.setMsToReady(3);
                }
                break;
            case R.id.radioButton5:
                if (checked) {
                    ShowTime.setMsToReady(5);
                }
                break;
            case R.id.radioButton10:
                if (checked) {
                    ShowTime.setMsToReady(10);
                }
                break;
        }
    }
}