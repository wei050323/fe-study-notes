package com.example.drinktracker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.drink2.R;

public class CaffeineProgressView extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF rectF;

    private float progress = 0;
    private int max = 400;

    public CaffeineProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.milk_tea_primary));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(30);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.coffee_primary));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(30);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - 15;

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawOval(rectF, backgroundPaint);

        float angle = 360 * progress / max;
        canvas.drawArc(rectF, -90, angle, false, progressPaint);
    }

    public void setProgress(int current) {
        this.progress = current;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }
}
