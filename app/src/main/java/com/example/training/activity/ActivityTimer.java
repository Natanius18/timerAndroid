package com.example.training.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.training.R;
import com.example.training.aditional.ShowTime;
import com.example.training.aditional.Timer;

public class ActivityTimer extends Activity {
    @SuppressLint("StaticFieldLeak")
    private static Timer timer;

    public static Timer getTimer() {
        return timer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        TextView bigDigits = findViewById(R.id.bigDigits);
        TextView timeLeft = findViewById(R.id.timeLeft);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        TextView setFromAll = findViewById(R.id.setsLeft);
        MediaPlayer finishKong = MediaPlayer.create(this, R.raw.finish_kong);
        MediaPlayer whistle = MediaPlayer.create(this, R.raw.whistle);
        MediaPlayer tick = MediaPlayer.create(this, R.raw.tick);

        Bundle arguments = getIntent().getExtras();
        long millisInFuture = arguments.getLong("mills") + ShowTime.getsToReady() * 1000L;
        int circles = arguments.getInt("circles");


        if (circles == 0) {
            circles = 1;
        }


        timer = new Timer(millisInFuture, 1000, timeLeft, bigDigits,
                setFromAll, millisInFuture / circles, circles, vibrator,
                finishKong, whistle, tick);

        timer.start();
    }


    public void weak(View view) {
        Toast toast = Toast.makeText(this, "Ты шо слабый?\nДавай делай!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -450);
        toast.show();
    }
}