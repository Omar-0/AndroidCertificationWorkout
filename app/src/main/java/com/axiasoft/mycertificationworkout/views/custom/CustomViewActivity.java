package com.axiasoft.mycertificationworkout.views.custom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.axiasoft.mycertificationworkout.R;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SadFaceView sadFaceView = findViewById(R.id.cv_sad_face);
        SadFaceView redButton = findViewById(R.id.happyButton);
        SadFaceView grayButton = findViewById(R.id.sadButton);

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadFaceView.setFaceColor(Color.GRAY);
            }
        });

        grayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadFaceView.setFaceColor(Color.RED);
            }
        });
    }
}
