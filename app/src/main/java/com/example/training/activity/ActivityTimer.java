package com.example.training.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.training.R;
import com.example.training.aditional.ShowTime;
import com.example.training.aditional.Timer;

public class ActivityTimer extends Activity {
    @SuppressLint("StaticFieldLeak")
    private static Timer timer;
    private static int volume = 100;
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
        final MediaPlayer finishKong = MediaPlayer.create(this, R.raw.finish_kong);
        final MediaPlayer whistle = MediaPlayer.create(this, R.raw.whistle);
        final MediaPlayer tick = MediaPlayer.create(this, R.raw.tick);

        Bundle arguments = getIntent().getExtras();
        long millisInFuture = arguments.getLong("mills");
        int circles = arguments.getInt("circles");


        if (circles == 0) {
            circles = 1;
        }

        long milSecondsInOneSet = millisInFuture / circles / 1000;
        timer = new Timer(millisInFuture + (ShowTime.getsToReady() * 1000L), 1000, timeLeft, bigDigits,
                setFromAll, milSecondsInOneSet, circles, vibrator,
                finishKong, whistle, tick);

        timer.start();

        final SeekBar appVolume = findViewById(R.id.seekBar);
        appVolume.setProgress(volume);
        changeVolume((float) (1 - (Math.log(appVolume.getMax() - volume) / Math.log(appVolume.getMax()))),
                whistle, tick, finishKong);
        appVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                volume = i;
                changeVolume((float) (1 - (Math.log(appVolume.getMax() - volume) / Math.log(appVolume.getMax()))),
                        whistle, tick, finishKong);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //    public void weak(View view) {
//        Toast toast = Toast.makeText(this, "Ты шо слабый?\nДавай делай!", Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, -450);
//        toast.show();
//    }

    public void changeVolume(float vol, MediaPlayer whistle, MediaPlayer tick, MediaPlayer finishKong){
        whistle.setVolume(vol, vol);
        tick.setVolume(vol, vol);
        finishKong.setVolume(vol, vol);
    }
}