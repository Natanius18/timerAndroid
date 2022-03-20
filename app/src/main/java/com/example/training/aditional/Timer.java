package com.example.training.aditional;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.TextView;

public class Timer extends CountDownTimer {
    private final TextView timeLeft;
    private final TextView bigDigits;
    private final TextView setFromAll;
    private final long milSecondsInOneSet;
    private final int circles;
    private final Vibrator vibrator;
    private final MediaPlayer finishKong;
    private final MediaPlayer whistle;
    private final MediaPlayer tick;

    public Timer(long millisInFuture, long countDownInterval, TextView timeLeft, TextView bigDigits,
                 TextView setFromAll, long milSecondsInOneSet, int circles, Vibrator vibrator,
                 MediaPlayer finishKong, MediaPlayer whistle, MediaPlayer tick) {
        super(millisInFuture, countDownInterval);
        this.timeLeft = timeLeft;
        this.bigDigits = bigDigits;
        this.setFromAll = setFromAll;
        this.milSecondsInOneSet = milSecondsInOneSet;
        this.circles = circles;
        this.vibrator = vibrator;
        this.finishKong = finishKong;
        this.whistle = whistle;
        this.tick = tick;
    }

    @SuppressLint("SetTextI18n")
    public void onTick(long millisUntilFinished) {
        ShowTime.onTick(millisUntilFinished, timeLeft, bigDigits, setFromAll, milSecondsInOneSet, circles, vibrator, whistle, tick);
    }

    @SuppressLint("SetTextI18n")
    public void onFinish() {
        ShowTime.onFinish(timeLeft, bigDigits, vibrator, finishKong);
    }


}
