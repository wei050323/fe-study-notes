package com.example.drinktracker.ui.stats;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.drink2.R;

import java.util.Random;

public class StatsActivity extends AppCompatActivity {

    private LinearLayout layoutChart;
    private TextView tvTodayCaffeine, tvTodayStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        layoutChart = findViewById(R.id.layout_chart);
        tvTodayCaffeine = findViewById(R.id.tv_today_caffeine);
        tvTodayStatus = findViewById(R.id.tv_today_status);

        setupTodaySummary();
        setupWeeklyChart();
    }

    private void setupTodaySummary() {
        int todayCaffeine = 220; // Mock data
        tvTodayCaffeine.setText(String.format("咖啡因: %d mg", todayCaffeine));
        if (todayCaffeine > 400) {
            tvTodayStatus.setText("状态: 超标");
            tvTodayStatus.setTextColor(ContextCompat.getColor(this, R.color.warning));
        } else {
            tvTodayStatus.setText("状态: 适量");
            tvTodayStatus.setTextColor(ContextCompat.getColor(this, R.color.success));
        }
    }

    private void setupWeeklyChart() {
        layoutChart.removeAllViews();
        Random random = new Random();
        int maxIntake = 500; // For chart scaling

        for (int i = 0; i < 7; i++) {
            int dailyIntake = 150 + random.nextInt(300);

            View bar = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    (int) (200 * (dailyIntake / (float) maxIntake) * getResources().getDisplayMetrics().density),
                    1
            );
            params.setMargins(8, 0, 8, 0);
            bar.setLayoutParams(params);

            if (dailyIntake > 400) {
                bar.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            } else {
                bar.setBackgroundColor(ContextCompat.getColor(this, R.color.coffee_primary));
            }

            layoutChart.addView(bar);
        }
    }
}
