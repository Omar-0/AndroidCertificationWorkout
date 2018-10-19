package com.axiasoft.mycertificationworkout.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/* Example from https://www.raywenderlich.com/142-android-custom-view-tutorial */

/* custom view draws a happy face*/

public class SadFaceView extends View {

    // Paint object for coloring and styling
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Some colors for the face background, eyes and mouth.
    private int faceColor = Color.YELLOW;
    private int eyesColor = Color.BLACK;
    private int mouthColor = Color.BLACK;
    private int borderColor = Color.BLACK;
    // Face border width in pixels
    private float borderWidth = 4.0f;
    // View size in pixels
    private int size = 320;

    private Path mouthPath = new Path();

    public SadFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFaceBackground(canvas);
        drawEyes(canvas);
        drawMouth(canvas);
    }


    private void drawFaceBackground(Canvas canvas) {

        // 1
        paint.setColor(faceColor);
        paint.setStyle(Paint.Style.FILL);

        // 2
        float radius = size / 2f;

        // 3
        canvas.drawCircle(size / 2f, size / 2f, radius, paint);

        // 4
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        // 5
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);
    }

    private void drawEyes(Canvas canvas) {


        // 1
        paint.setColor(eyesColor);
        paint.setStyle(Paint.Style.FILL);

// 2
        RectF leftEyeRect = new RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f);

        canvas.drawOval(leftEyeRect, paint);

// 3
        RectF rightEyeRect = new RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f);

        canvas.drawOval(rightEyeRect, paint);
    }

    private void drawMouth(Canvas canvas) {

        // 1
        mouthPath.moveTo(size * 0.22f, size * 0.7f);
// 2
        mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f);
// 3
        mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f);
// 4
        paint.setColor(mouthColor);
        paint.setStyle(Paint.Style.FILL);
// 5
        canvas.drawPath(mouthPath, paint);
    }
}
