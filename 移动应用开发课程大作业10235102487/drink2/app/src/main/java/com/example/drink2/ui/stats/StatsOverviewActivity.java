package com.example.drink2.ui.stats;

import android.os.Bundle;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRecord;
import com.example.drink2.model.DrinkRepository;
import com.example.drink2.model.UserSettings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class StatsOverviewActivity extends AppCompatActivity {

    private DrinkRepository repository;

    private MaterialButtonToggleGroup toggleGroup;
    private View monthContainer;
    private View yearContainer;

    private TextView textMonthTitle;
    private TextView textMonthTotalCups;
    private TextView textMonthAvgInterval;
    private TextView textMonthTotalPrice;
    private TextView textMonthAvgDailyPrice;
    private TextView textMonthCalorie;
    private TextView textMonthSugar;
    private TextView textMonthCaffeine;
    private android.widget.ProgressBar progressMonthCalorie;
    private android.widget.ProgressBar progressMonthSugar;
    private android.widget.ProgressBar progressMonthCaffeine;
    private LinearLayout layoutMonthBars;

    private TextView textYearTitle;
    private TextView textYearTotalCups;
    private TextView textYearAvgInterval;
    private TextView textYearTotalPrice;
    private TextView textYearAvgDailyPrice;
    private TextView textYearCalorie;
    private TextView textYearSugar;
    private TextView textYearCaffeine;
    private android.widget.ProgressBar progressYearCalorie;
    private android.widget.ProgressBar progressYearSugar;
    private android.widget.ProgressBar progressYearCaffeine;
    private LinearLayout layoutYearBars;

    private Calendar currentMonth;
    private int currentYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_overview);

        repository = DrinkRepository.getInstance();

        toggleGroup = findViewById(R.id.toggle_mode);
        monthContainer = findViewById(R.id.container_month);
        yearContainer = findViewById(R.id.container_year);

        textMonthTitle = findViewById(R.id.text_month_title);
        textMonthTotalCups = findViewById(R.id.text_month_total_cups);
        textMonthAvgInterval = findViewById(R.id.text_month_avg_interval);
        textMonthTotalPrice = findViewById(R.id.text_month_total_price);
        textMonthAvgDailyPrice = findViewById(R.id.text_month_avg_daily_price);
        textMonthCalorie = findViewById(R.id.text_month_calorie);
        textMonthSugar = findViewById(R.id.text_month_sugar);
        textMonthCaffeine = findViewById(R.id.text_month_caffeine);
        progressMonthCalorie = findViewById(R.id.progress_month_calorie);
        progressMonthSugar = findViewById(R.id.progress_month_sugar);
        progressMonthCaffeine = findViewById(R.id.progress_month_caffeine);
        layoutMonthBars = findViewById(R.id.layout_month_bars);
        MaterialButton buttonPrevMonth = findViewById(R.id.button_prev_month_overview);
        MaterialButton buttonNextMonth = findViewById(R.id.button_next_month_overview);

        textYearTitle = findViewById(R.id.text_year_title);
        textYearTotalCups = findViewById(R.id.text_year_total_cups);
        textYearAvgInterval = findViewById(R.id.text_year_avg_interval);
        textYearTotalPrice = findViewById(R.id.text_year_total_price);
        textYearAvgDailyPrice = findViewById(R.id.text_year_avg_daily_price);
        textYearCalorie = findViewById(R.id.text_year_calorie);
        textYearSugar = findViewById(R.id.text_year_sugar);
        textYearCaffeine = findViewById(R.id.text_year_caffeine);
        progressYearCalorie = findViewById(R.id.progress_year_calorie);
        progressYearSugar = findViewById(R.id.progress_year_sugar);
        progressYearCaffeine = findViewById(R.id.progress_year_caffeine);
        layoutYearBars = findViewById(R.id.layout_year_bars);
        MaterialButton buttonPrevYear = findViewById(R.id.button_prev_year);
        MaterialButton buttonNextYear = findViewById(R.id.button_next_year);

        currentMonth = Calendar.getInstance();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
        currentYear = currentMonth.get(Calendar.YEAR);

        toggleGroup.check(R.id.button_mode_month);
        showMonth();

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (!isChecked) {
                    return;
                }
                if (checkedId == R.id.button_mode_month) {
                    showMonth();
                } else if (checkedId == R.id.button_mode_year) {
                    showYear();
                }
            }
        });

        buttonPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, -1);
                updateMonthUi();
            }
        });

        buttonNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, 1);
                updateMonthUi();
            }
        });

        buttonPrevYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear -= 1;
                updateYearUi();
            }
        });

        buttonNextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear += 1;
                updateYearUi();
            }
        });

        updateMonthUi();
        updateYearUi();
    }

    private void showMonth() {
        monthContainer.setVisibility(View.VISIBLE);
        yearContainer.setVisibility(View.GONE);
    }

    private void showYear() {
        monthContainer.setVisibility(View.GONE);
        yearContainer.setVisibility(View.VISIBLE);
    }

    private void updateMonthUi() {
        int year = currentMonth.get(Calendar.YEAR);
        int month = currentMonth.get(Calendar.MONTH);

        String title = year + "年" + (month + 1) + "月";
        textMonthTitle.setText(title);

        List<DrinkRecord> monthRecords = repository.getMonthRecords(year, month);
        Collections.sort(monthRecords, (o1, o2) -> Long.compare(o1.getTimestampMillis(), o2.getTimestampMillis()));

        int totalCups = monthRecords.size();
        textMonthTotalCups.setText(String.valueOf(totalCups));

        float totalPrice = 0f;
        int totalCalorie = 0;
        int totalSugar = 0;
        int totalCaffeine = 0;

        for (DrinkRecord record : monthRecords) {
            totalPrice += record.getPrice();
            totalCalorie += record.getCalorieKcal();
            totalSugar += record.getSugarGram();
            totalCaffeine += record.getCaffeineMg();
        }

        textMonthTotalPrice.setText(String.format("%.1f 元", totalPrice));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        float avgDailyPrice = daysInMonth > 0 ? totalPrice / daysInMonth : 0f;
        textMonthAvgDailyPrice.setText(String.format("%.1f 元", avgDailyPrice));

        float avgInterval = 0f;
        if (monthRecords.size() > 1) {
            long firstTime = monthRecords.get(0).getTimestampMillis();
            long lastTime = monthRecords.get(monthRecords.size() - 1).getTimestampMillis();
            long diffDays = (lastTime - firstTime) / (1000 * 60 * 60 * 24);
            avgInterval = diffDays > 0 ? (float) diffDays / (monthRecords.size() - 1) : 0f;
        }
        textMonthAvgInterval.setText(String.format("%.1f 天", avgInterval));

        int dailyCalorieLimit = UserSettings.getStatsDailyCalorieLimit(this);
        int dailySugarLimit = UserSettings.getStatsDailySugarLimit(this);
        int dailyCaffeineLimit = UserSettings.getStatsDailyCaffeineLimit(this);
        int monthCalorieLimit = dailyCalorieLimit * daysInMonth;
        int monthSugarLimit = dailySugarLimit * daysInMonth;
        int monthCaffeineLimit = dailyCaffeineLimit * daysInMonth;
        textMonthCalorie.setText(totalCalorie + " / " + monthCalorieLimit + " kcal");
        textMonthSugar.setText(totalSugar + " / " + monthSugarLimit + " g");
        textMonthCaffeine.setText(totalCaffeine + " / " + monthCaffeineLimit + " mg");

        // 进度条：按“当前值/限值”比例填充；达到限值时拉满并变红
        int bgColor = ContextCompat.getColor(this, R.color.divider_light);
        int okColor = ContextCompat.getColor(this, R.color.coffee_primary);
        int warnColor = ContextCompat.getColor(this, R.color.warning_red);

        // 统一按百分比（0-100）展示；达到/超过限值时填满并变红
        progressMonthCalorie.setMax(100);
        progressMonthSugar.setMax(100);
        progressMonthCaffeine.setMax(100);

        int calorieProgress = monthCalorieLimit > 0
                ? Math.min(100, Math.round((totalCalorie * 100f) / monthCalorieLimit))
                : 0;
        int sugarProgress = monthSugarLimit > 0
                ? Math.min(100, Math.round((totalSugar * 100f) / monthSugarLimit))
                : 0;
        int caffeineProgress = monthCaffeineLimit > 0
                ? Math.min(100, Math.round((totalCaffeine * 100f) / monthCaffeineLimit))
                : 0;

        progressMonthCalorie.setProgress(calorieProgress);
        progressMonthSugar.setProgress(sugarProgress);
        progressMonthCaffeine.setProgress(caffeineProgress);

        boolean calorieOver = monthCalorieLimit > 0 && totalCalorie >= monthCalorieLimit;
        boolean sugarOver = monthSugarLimit > 0 && totalSugar >= monthSugarLimit;
        boolean caffeineOver = monthCaffeineLimit > 0 && totalCaffeine >= monthCaffeineLimit;

        progressMonthCalorie.setProgressTintList(ColorStateList.valueOf(calorieOver ? warnColor : okColor));
        progressMonthSugar.setProgressTintList(ColorStateList.valueOf(sugarOver ? warnColor : okColor));
        progressMonthCaffeine.setProgressTintList(ColorStateList.valueOf(caffeineOver ? warnColor : okColor));

        progressMonthCalorie.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));
        progressMonthSugar.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));
        progressMonthCaffeine.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));

        int[] dailyCounts = repository.getMonthDailyCounts(year, month);
        layoutMonthBars.removeAllViews();

        int maxCount = 0;
        for (int count : dailyCounts) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        if (maxCount == 0) {
            maxCount = 1;
        }

        int barHeight = (int) getResources().getDimension(R.dimen.bar_max_height);
        int spacing = (int) getResources().getDimension(R.dimen.spacing_4);
        int labelSpace = (int) getResources().getDimension(R.dimen.spacing_24);
        int barWidth = (int) getResources().getDimension(R.dimen.bar_width);

        for (int i = 0; i < dailyCounts.length; i++) {
            int count = dailyCounts[i];
            float ratio = (float) count / (float) maxCount;

            // 预留一部分高度给顶部数字，避免被裁剪
            int usableHeight = barHeight - labelSpace;
            if (usableHeight < 8) {
                usableHeight = barHeight;
            }
            int height = (int) (usableHeight * ratio);
            if (height < 4) {
                height = 4;
            }

            // 容器：垂直方向排列，底部对齐（数字在上，柱子在下）
            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            container.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));

            // 顶部杯数数字（仅在 count > 0 时显示）
            if (count > 0) {
                TextView textCount = new TextView(this);
                textCount.setText(String.valueOf(count));
                textCount.setTextSize(10f);
                textCount.setTextColor(getResources().getColor(R.color.text_secondary));
                LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                countParams.bottomMargin = spacing / 2;
                textCount.setLayoutParams(countParams);
                container.addView(textCount);
            }

            // 柱子本体
            View bar = new View(this);
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(barWidth, height);
            bar.setLayoutParams(barParams);
            bar.setBackgroundColor(getResources().getColor(R.color.coffee_primary));
            container.addView(bar);

            // 容器左右留白，便于区分每天的柱子
            LinearLayout.LayoutParams containerParams = (LinearLayout.LayoutParams) container.getLayoutParams();
            containerParams.leftMargin = spacing;
            containerParams.rightMargin = spacing;

            layoutMonthBars.addView(container);
        }
    }

    private void updateYearUi() {
        textYearTitle.setText(currentYear + "年");

        List<DrinkRecord> yearRecords = repository.getYearRecords(currentYear);
        Collections.sort(yearRecords, (o1, o2) -> Long.compare(o1.getTimestampMillis(), o2.getTimestampMillis()));

        int totalCups = yearRecords.size();
        textYearTotalCups.setText(String.valueOf(totalCups));

        float totalPrice = 0f;
        int totalCalorie = 0;
        int totalSugar = 0;
        int totalCaffeine = 0;

        for (DrinkRecord record : yearRecords) {
            totalPrice += record.getPrice();
            totalCalorie += record.getCalorieKcal();
            totalSugar += record.getSugarGram();
            totalCaffeine += record.getCaffeineMg();
        }

        textYearTotalPrice.setText(String.format("%.1f 元", totalPrice));

        Calendar yearCal = Calendar.getInstance();
        yearCal.set(Calendar.YEAR, currentYear);
        yearCal.set(Calendar.DAY_OF_YEAR, 1);
        int daysInYear = yearCal.getActualMaximum(Calendar.DAY_OF_YEAR);
        float avgDailyPrice = daysInYear > 0 ? totalPrice / daysInYear : 0f;
        textYearAvgDailyPrice.setText(String.format("%.1f 元", avgDailyPrice));

        float avgInterval = 0f;
        if (yearRecords.size() > 1) {
            long firstTime = yearRecords.get(0).getTimestampMillis();
            long lastTime = yearRecords.get(yearRecords.size() - 1).getTimestampMillis();
            long diffDays = (lastTime - firstTime) / (1000 * 60 * 60 * 24);
            avgInterval = diffDays > 0 ? (float) diffDays / (yearRecords.size() - 1) : 0f;
        }
        textYearAvgInterval.setText(String.format("%.1f 天", avgInterval));

        int dailyCalorieLimit = UserSettings.getStatsDailyCalorieLimit(this);
        int dailySugarLimit = UserSettings.getStatsDailySugarLimit(this);
        int dailyCaffeineLimit = UserSettings.getStatsDailyCaffeineLimit(this);
        int yearCalorieLimit = dailyCalorieLimit * daysInYear;
        int yearSugarLimit = dailySugarLimit * daysInYear;
        int yearCaffeineLimit = dailyCaffeineLimit * daysInYear;
        textYearCalorie.setText(totalCalorie + " / " + yearCalorieLimit + " kcal");
        textYearSugar.setText(totalSugar + " / " + yearSugarLimit + " g");
        textYearCaffeine.setText(totalCaffeine + " / " + yearCaffeineLimit + " mg");

        progressYearCalorie.setMax(100);
        progressYearSugar.setMax(100);
        progressYearCaffeine.setMax(100);

        int calorieProgress = yearCalorieLimit > 0
                ? Math.min(100, Math.round((totalCalorie * 100f) / yearCalorieLimit))
                : 0;
        int sugarProgress = yearSugarLimit > 0
                ? Math.min(100, Math.round((totalSugar * 100f) / yearSugarLimit))
                : 0;
        int caffeineProgress = yearCaffeineLimit > 0
                ? Math.min(100, Math.round((totalCaffeine * 100f) / yearCaffeineLimit))
                : 0;

        progressYearCalorie.setProgress(calorieProgress);
        progressYearSugar.setProgress(sugarProgress);
        progressYearCaffeine.setProgress(caffeineProgress);

        int bgColor = ContextCompat.getColor(this, R.color.divider_light);
        int okColor = ContextCompat.getColor(this, R.color.coffee_primary);
        int warnColor = ContextCompat.getColor(this, R.color.warning_red);

        boolean calorieOverY = yearCalorieLimit > 0 && totalCalorie >= yearCalorieLimit;
        boolean sugarOverY = yearSugarLimit > 0 && totalSugar >= yearSugarLimit;
        boolean caffeineOverY = yearCaffeineLimit > 0 && totalCaffeine >= yearCaffeineLimit;

        progressYearCalorie.setProgressTintList(ColorStateList.valueOf(calorieOverY ? warnColor : okColor));
        progressYearSugar.setProgressTintList(ColorStateList.valueOf(sugarOverY ? warnColor : okColor));
        progressYearCaffeine.setProgressTintList(ColorStateList.valueOf(caffeineOverY ? warnColor : okColor));

        progressYearCalorie.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));
        progressYearSugar.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));
        progressYearCaffeine.setProgressBackgroundTintList(ColorStateList.valueOf(bgColor));

        int[] monthlyCounts = repository.getYearMonthlyCounts(currentYear);
        layoutYearBars.removeAllViews();

        int maxCount = 0;
        for (int count : monthlyCounts) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        if (maxCount == 0) {
            maxCount = 1;
        }

        int barHeight = (int) getResources().getDimension(R.dimen.bar_max_height);
        int spacing = (int) getResources().getDimension(R.dimen.spacing_8);
        int labelSpace = (int) getResources().getDimension(R.dimen.spacing_24);

        for (int month = 0; month < 12; month++) {
            int count = monthlyCounts[month];
            float ratio = (float) count / (float) maxCount;

            int usableHeight = barHeight - labelSpace;
            if (usableHeight < 8) {
                usableHeight = barHeight;
            }
            int height = (int) (usableHeight * ratio);
            if (height < 4) {
                height = 4;
            }

            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            container.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            ));

            // 顶部杯数字（仅在 count > 0 时显示）
            if (count > 0) {
                TextView textCount = new TextView(this);
                textCount.setText(String.valueOf(count));
                textCount.setTextSize(10f);
                textCount.setTextColor(getResources().getColor(R.color.text_secondary));
                LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                countParams.bottomMargin = spacing / 2;
                textCount.setLayoutParams(countParams);
                container.addView(textCount);
            }

            View bar = new View(this);
            int barWidth = (int) getResources().getDimension(R.dimen.bar_width);
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(barWidth, height);
            barParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            bar.setLayoutParams(barParams);
            bar.setBackgroundColor(getResources().getColor(R.color.coffee_primary));

            TextView label = new TextView(this);
            label.setTextSize(10f);
            label.setTextColor(getResources().getColor(R.color.text_secondary));
            label.setText((month + 1) + "月");
            label.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            labelParams.topMargin = (int) getResources().getDimension(R.dimen.spacing_4);
            label.setLayoutParams(labelParams);

            container.addView(bar);
            container.addView(label);

            LinearLayout.LayoutParams containerParams = (LinearLayout.LayoutParams) container.getLayoutParams();
            containerParams.leftMargin = spacing / 2;
            containerParams.rightMargin = spacing / 2;

            layoutYearBars.addView(container);
        }
    }
}
