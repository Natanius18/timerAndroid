package com.example.training.aditional;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.TextView;

public class ShowTime {
    private static int i = 0;
    private static int vibro = 0;
    private static int sToReady = 3;
    private static boolean metronom = false;

    public static void setI(int i) {
        ShowTime.i = i;
    }

    public static void setVibro(int vibro) {
        ShowTime.vibro = vibro;
    }

    public static void setMetronom(boolean metronom) {
        ShowTime.metronom = metronom;
    }

    public static int getsToReady() {
        return sToReady;
    }

    public static void setMsToReady(int sToReady) {
        ShowTime.sToReady = sToReady;
    }

    @SuppressLint("SetTextI18n")
    public static void onTick(long millisUntilFinished, TextView timeLeft, TextView bigDigits,
                              TextView setFromAll, long milSecondsInOneSet, int circles,
                              Vibrator vibrator, MediaPlayer whistle, MediaPlayer tick) {

        if (sToReady > 0) {
            if (sToReady <= 3) {
                vibrator.vibrate(250L * vibro);
                tick.start();
            }
            bigDigits.setText(String.valueOf(sToReady--));
            setFromAll.setText(i + "/" + circles);
            if (sToReady == 0) {
                i++;
            }
        } else {
            int left = (int) (millisUntilFinished / 1000) + 1;
            int seconds = (int) ((millisUntilFinished / 1000 + 1) % 60);
            int minutes = (int) ((millisUntilFinished / 1000 + 1) / 60);
            timeLeft.setText((minutes < 10 ? "0" + minutes : minutes)
                    + ":"
                    + (seconds < 10 ? "0" + seconds : seconds));
            int leftInSet = (int) (left % milSecondsInOneSet);
            if (leftInSet == 0) {
                bigDigits.setTextColor(Color.RED);
                bigDigits.setPadding(0, 180, 0, 0);
                bigDigits.setText("Ыыщ");
                setFromAll.setText(i++ + "/" + circles);
                vibrator.vibrate(500L * vibro);
                whistle.start();
            } else {
                if (metronom) {
                    vibrator.vibrate(250L * vibro);
                    tick.start();
                }
                bigDigits.setPadding(0, 0, 0, 0);
                bigDigits.setTextColor(Color.parseColor("#00F10C"));
                bigDigits.setText(String.valueOf(leftInSet));
                if (leftInSet <= 3) {
                    vibrator.vibrate(250L * vibro);
                    tick.start();
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public static void onFinish(TextView timeLeft, TextView bigDigits, Vibrator vibrator, MediaPlayer finishKong) {
        timeLeft.setText("00:00");
        bigDigits.setPadding(0, 220, 0, 0);
        bigDigits.setText("Конец!");
        vibrator.vibrate(700L * vibro);
        finishKong.start();
    }
}
