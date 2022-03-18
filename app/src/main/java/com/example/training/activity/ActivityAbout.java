package com.example.training.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.training.R;

public class ActivityAbout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void clickEMOM(View view) {
        Intent intent = new Intent(ActivityAbout.this, EMOM.class);
        startActivity(intent);
    }



}