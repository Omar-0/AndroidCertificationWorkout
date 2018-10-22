package com.axiasoft.mycertificationworkout.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.axiasoft.mycertificationworkout.R;

import static com.axiasoft.mycertificationworkout.views.custom.SadFaceView.Mood.HAPPY;
import static com.axiasoft.mycertificationworkout.views.custom.SadFaceView.Mood.SAD;

/* Example from https://www.raywenderlich.com/142-android-custom-view-tutorial */

/* custom view draws a happy face*/

public class SadFaceView extends View {
    /*
    companion object {
  private const val DEFAULT_FACE_COLOR = Color.YELLOW
  private const val DEFAULT_EYES_COLOR = Color.BLACK
  private const val DEFAULT_MOUTH_COLOR = Color.BLACK
  private const val DEFAULT_BORDER_COLOR = Color.BLACK
  private const val DEFAULT_BORDER_WIDTH = 4.0f

  const val HAPPY = 0L
  const val SAD = 1L
} */

    private static final int DEFAULT_FACE_COLOR = Color.YELLOW;
    private static final int DEFAULT_EYES_COLOR = Color.BLACK;
    private static final int DEFAULT_MOUTH_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final float DEFAULT_BORDER_WIDTH = 4.0f;

    enum Mood{
        HAPPY,
        SAD
    }
    private Mood mood;
    public void setMood(long mood) {
        if(mood != 0) {
            invalidate();
            this.mood = SAD;
        }
    }



    // Paint object for coloring and styling
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Some colors for the face background, eyes and mouth.
    private int faceColor = DEFAULT_FACE_COLOR;
    private int eyesColor = DEFAULT_EYES_COLOR;
    private int mouthColor = DEFAULT_MOUTH_COLOR;
    private int borderColor = DEFAULT_BORDER_COLOR;
    // Face border width in pixels
    private float borderWidth = DEFAULT_BORDER_WIDTH;
    // View size in pixels a square view
    private int size = 320;

    private Path mouthPath = new Path();


    public SadFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        setupAttributes(context, attrs);
    }


    private void setupAttributes(Context context, @Nullable AttributeSet attrs) {
        // 6
        // Obtain a typed array of attributes
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SadFaceView,
                0, 0);

        // 7
        // Extract custom attributes into member variables
        int m = typedArray.getInt(R.styleable.SadFaceView_state, 0 );
        setMood(m);
        faceColor = typedArray.getColor(R.styleable.SadFaceView_faceColor, DEFAULT_FACE_COLOR);
        eyesColor = typedArray.getColor(R.styleable.SadFaceView_eyesColor, DEFAULT_EYES_COLOR);
        mouthColor = typedArray.getColor(R.styleable.SadFaceView_mouthColor, DEFAULT_MOUTH_COLOR);
        borderColor = typedArray.getColor(R.styleable.SadFaceView_borderColor,
                DEFAULT_BORDER_COLOR);
        borderWidth = typedArray.getDimension(R.styleable.SadFaceView_borderWidth,
                DEFAULT_BORDER_WIDTH);

        size = (int) typedArray.getDimension(R.styleable.SadFaceView_faceSize, size);

        // 8
        // TypedArray objects are shared and must be recycled.
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFaceBackground(canvas);
        drawEyes(canvas);
        drawMouth(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d("FACE", MeasureSpec.getSize(widthMeasureSpec) + " width");
        Log.d("FACE", MeasureSpec.getSize(heightMeasureSpec) + " heihtg");

        Log.v("[View name] onMeasure w", MeasureSpec.toString(widthMeasureSpec));
        Log.v("[View name] onMeasure h", MeasureSpec.toString(heightMeasureSpec));

        //int desiredWidth = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        //int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureDimension(size, MeasureSpec.getSize(widthMeasureSpec)),
                measureDimension(size, MeasureSpec.getSize(heightMeasureSpec)));
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize){
            Log.e("ChartView", "The view is too small, the content might get cut");
        }
        return result;
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
