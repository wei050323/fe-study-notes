package com.example.drink2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.drink2.R;

public class CaffeineProgressView extends View {

    private int maxCaffeine = 400;
    private int currentCaffeine = 0;

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint centerTextPaint;
    private Paint subTextPaint;
    private final RectF arcRect = new RectF();

    public CaffeineProgressView(Context context) {
        super(context);
        init(null);
    }

    public CaffeineProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CaffeineProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int bgColor = 0xFFE5E7EB;
        int progressColor = ContextCompat.getColor(getContext(), R.color.coffee_primary);
        int textColor = ContextCompat.getColor(getContext(), R.color.text_primary);
        int subTextColor = ContextCompat.getColor(getContext(), R.color.text_secondary);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, new int[]{});
            a.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(18f);
        backgroundPaint.setColor(bgColor);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(18f);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setColor(textColor);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);
        centerTextPaint.setTextSize(48f);

        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setColor(subTextColor);
        subTextPaint.setTextAlign(Paint.Align.CENTER);
        subTextPaint.setTextSize(28f);
    }

    public void setCaffeine(int current, int max) {
        this.currentCaffeine = Math.max(0, current);
        this.maxCaffeine = Math.max(1, max);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float padding = 36f;
        float left = padding;
        float top = padding;
        float right = getWidth() - padding;
        float bottom = getHeight() - padding;
        arcRect.set(left, top, right, bottom);

        float startAngle = -210f;
        float sweepAngle = 240f;

        canvas.drawArc(arcRect, startAngle, sweepAngle, false, backgroundPaint);

        float ratio = (float) currentCaffeine / (float) maxCaffeine;
        ratio = Math.max(0f, Math.min(1f, ratio));
        float progressSweep = sweepAngle * ratio;

        int color = ContextCompat.getColor(getContext(), R.color.coffee_primary);
        if (currentCaffeine > maxCaffeine) {
            color = ContextCompat.getColor(getContext(), R.color.warning_red);
        }
        progressPaint.setColor(color);

        canvas.drawArc(arcRect, startAngle, progressSweep, false, progressPaint);

        String mainText = currentCaffeine + "mg";
        String subText = "目标 " + maxCaffeine + "mg";

        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        Paint.FontMetrics fm = centerTextPaint.getFontMetrics();
        float textY = cy - (fm.ascent + fm.descent) / 2;
        canvas.drawText(mainText, cx, textY - 20, centerTextPaint);

        Paint.FontMetrics fm2 = subTextPaint.getFontMetrics();
        float subY = textY + (-fm2.ascent);
        canvas.drawText(subText, cx, subY, subTextPaint);
    }
}

