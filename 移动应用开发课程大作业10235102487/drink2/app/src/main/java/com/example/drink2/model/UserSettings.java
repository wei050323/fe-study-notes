package com.example.drink2.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 统一管理用户可配置的阈值（摄入上限等）。
 */
public class UserSettings {

    private static final String PREF_NAME = "user_settings";

    private static final String KEY_DAILY_CAFFEINE_LIMIT = "daily_caffeine_limit_mg";
    private static final String KEY_STATS_DAILY_CALORIE_LIMIT = "stats_daily_calorie_limit_kcal";
    private static final String KEY_STATS_DAILY_SUGAR_LIMIT = "stats_daily_sugar_limit_g";
    private static final String KEY_STATS_DAILY_CAFFEINE_LIMIT = "stats_daily_caffeine_limit_mg";

    // 默认值：与目前代码中的写死值保持一致
    private static final int DEFAULT_DAILY_CAFFEINE_LIMIT = 400;
    private static final int DEFAULT_STATS_DAILY_CALORIE_LIMIT = 2000;
    private static final int DEFAULT_STATS_DAILY_SUGAR_LIMIT = 50;
    private static final int DEFAULT_STATS_DAILY_CAFFEINE_LIMIT = 400;

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ========= 今日咖啡因上限 =========

    public static int getDailyCaffeineLimit(Context context) {
        return prefs(context).getInt(KEY_DAILY_CAFFEINE_LIMIT, DEFAULT_DAILY_CAFFEINE_LIMIT);
    }

    public static void setDailyCaffeineLimit(Context context, int limitMg) {
        prefs(context).edit().putInt(KEY_DAILY_CAFFEINE_LIMIT, limitMg).apply();
    }

    // ========= 统计页：以“每天”为单位的上限 =========

    public static int getStatsDailyCalorieLimit(Context context) {
        return prefs(context).getInt(KEY_STATS_DAILY_CALORIE_LIMIT, DEFAULT_STATS_DAILY_CALORIE_LIMIT);
    }

    public static void setStatsDailyCalorieLimit(Context context, int limitKcal) {
        prefs(context).edit().putInt(KEY_STATS_DAILY_CALORIE_LIMIT, limitKcal).apply();
    }

    public static int getStatsDailySugarLimit(Context context) {
        return prefs(context).getInt(KEY_STATS_DAILY_SUGAR_LIMIT, DEFAULT_STATS_DAILY_SUGAR_LIMIT);
    }

    public static void setStatsDailySugarLimit(Context context, int limitGram) {
        prefs(context).edit().putInt(KEY_STATS_DAILY_SUGAR_LIMIT, limitGram).apply();
    }

    public static int getStatsDailyCaffeineLimit(Context context) {
        return prefs(context).getInt(KEY_STATS_DAILY_CAFFEINE_LIMIT, DEFAULT_STATS_DAILY_CAFFEINE_LIMIT);
    }

    public static void setStatsDailyCaffeineLimit(Context context, int limitMg) {
        prefs(context).edit().putInt(KEY_STATS_DAILY_CAFFEINE_LIMIT, limitMg).apply();
    }
}


