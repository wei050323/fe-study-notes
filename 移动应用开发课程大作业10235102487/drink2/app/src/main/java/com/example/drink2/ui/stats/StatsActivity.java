package com.example.drink2.ui.stats;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRepository;
import com.example.drink2.model.UserSettings;
import com.google.android.material.appbar.MaterialToolbar;

public class StatsActivity extends AppCompatActivity {

    private DrinkRepository repository;
    private LinearLayout layoutBarChart;
    private TextView textTodayCaffeine;
    private TextView textTodayStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        repository = DrinkRepository.getInstance();

        MaterialToolbar toolbar = findViewById(R.id.toolbar_stats);
        toolbar.setNavigationOnClickListener(view -> finish());

        layoutBarChart = findViewById(R.id.layout_bar_chart);
        textTodayCaffeine = findViewById(R.id.text_today_caffeine);
        textTodayStatus = findViewById(R.id.text_today_status);

        updateTodaySummary();
        buildBarChart();
    }

    private void updateTodaySummary() {
        int total = repository.getTodayCaffeineTotal();
        int limit = UserSettings.getDailyCaffeineLimit(this);
        String text = "咖啡因 " + total + "mg / " + limit + "mg";
        textTodayCaffeine.setText(text);

        if (total > limit) {
            textTodayStatus.setText("已超过建议摄入量");
            textTodayStatus.setTextColor(getResources().getColor(R.color.warning_red));
        } else {
            textTodayStatus.setText("在安全范围内");
            textTodayStatus.setTextColor(getResources().getColor(R.color.success_green));
        }
    }

    private void buildBarChart() {
        layoutBarChart.removeAllViews();
        int[] totals = repository.getLast7DaysTotals();

        int max = 0;
        for (int v : totals) {
            if (v > max) {
                max = v;
            }
        }
        int dailyLimit = UserSettings.getDailyCaffeineLimit(this);
        if (max < dailyLimit) {
            max = dailyLimit;
        }
        if (max == 0) {
            max = 1;
        }

        int barWidth = (int) getResources().getDimension(R.dimen.bar_width);
        int barMaxHeight = (int) getResources().getDimension(R.dimen.bar_max_height);
        int spacing = (int) getResources().getDimension(R.dimen.spacing_8);

        for (int value : totals) {
            float ratio = (float) value / (float) max;
            int height = (int) (barMaxHeight * ratio);
            if (height < spacing) {
                height = spacing;
            }

            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

            View bar = new View(this);
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(barWidth, height);
            bar.setLayoutParams(barParams);

            if (value > dailyLimit) {
                bar.setBackgroundColor(getResources().getColor(R.color.warning_red));
            } else {
                bar.setBackgroundColor(getResources().getColor(R.color.coffee_primary));
            }

            TextView label = new TextView(this);
            label.setTextSize(10f);
            label.setTextColor(getResources().getColor(R.color.text_secondary));
            label.setText(String.valueOf(value));
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            labelParams.topMargin = spacing / 2;
            label.setLayoutParams(labelParams);

            container.addView(bar);
            container.addView(label);

            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            );
            containerParams.leftMargin = spacing / 2;
            containerParams.rightMargin = spacing / 2;

            layoutBarChart.addView(container, containerParams);
        }
    }
}

