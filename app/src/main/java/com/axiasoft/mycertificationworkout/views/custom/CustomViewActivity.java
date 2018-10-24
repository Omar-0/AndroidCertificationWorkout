package com.axiasoft.mycertificationworkout.views.custom;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.axiasoft.mycertificationworkout.R;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SadFaceView sadFaceView = findViewById(R.id.cv_sad_face);
        SadFaceView grayButton = findViewById(R.id.happyButton);
        SadFaceView redButton = findViewById(R.id.sadButton);

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadFaceView.setFaceColor(Color.RED);
                sadFaceView.setContentDescription("Face is red");
            }
        });

        grayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadFaceView.setFaceColor(Color.GRAY);
                //as live region will announce every time it changes
                sadFaceView.setContentDescription("Face is gray");
            }
        });


        //region Add accessibility hooks to a custom view

        // Use of AccessibilityDelegate to Add accessibility hooks to a custom view
        //https://codelabs.developers.google.com/codelabs/basic-android-accessibility/index.html?index=..%2F..%2Findex#7
        ViewCompat.setAccessibilityLiveRegion(sadFaceView, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);

        if (Build.VERSION.SDK_INT >= 14) {
            // If the API version is equal of higher than the version in
            // which onInitializeAccessibilityNodeInfo was introduced we
            // register a delegate with a customized implementation.

            redButton.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                public void onInitializeAccessibilityNodeInfo(View host,
                                                              AccessibilityNodeInfo info) {
                    // Let the default implementation populate the info.
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    // Set some other information.
                    info.setEnabled(host.isEnabled());
                    info.setContentDescription("red button");
                    Log.d("FACE" , "initialized");
                }
            });


            sadFaceView.setAccessibilityDelegate(new View.AccessibilityDelegate(){

            });
        }

        //endregion

    }


}
