package com.example.drink2.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drink2.R;
import com.example.drink2.model.UserSettings;
import com.google.android.material.button.MaterialButton;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = view.findViewById(R.id.text_settings_title);
        if (textTitle != null) {
            textTitle.setText("设置");
        }

        // ===== 上限相关控件 =====
        EditText editDailyCaffeine = view.findViewById(R.id.edit_daily_caffeine_limit);
        EditText editStatsCalorie = view.findViewById(R.id.edit_stats_calorie_limit);
        EditText editStatsSugar = view.findViewById(R.id.edit_stats_sugar_limit);
        EditText editStatsCaffeine = view.findViewById(R.id.edit_stats_caffeine_limit);
        MaterialButton buttonSave = view.findViewById(R.id.button_save_limits);

        if (editDailyCaffeine != null) {
            int v = UserSettings.getDailyCaffeineLimit(requireContext());
            editDailyCaffeine.setText(String.valueOf(v));
        }
        if (editStatsCalorie != null) {
            int v = UserSettings.getStatsDailyCalorieLimit(requireContext());
            editStatsCalorie.setText(String.valueOf(v));
        }
        if (editStatsSugar != null) {
            int v = UserSettings.getStatsDailySugarLimit(requireContext());
            editStatsSugar.setText(String.valueOf(v));
        }
        if (editStatsCaffeine != null) {
            int v = UserSettings.getStatsDailyCaffeineLimit(requireContext());
            editStatsCaffeine.setText(String.valueOf(v));
        }

        if (buttonSave != null) {
            buttonSave.setOnClickListener(view1 -> {
                try {
                    String dailyCafStr = editDailyCaffeine != null ? editDailyCaffeine.getText().toString().trim() : "";
                    String statsCalStr = editStatsCalorie != null ? editStatsCalorie.getText().toString().trim() : "";
                    String statsSugarStr = editStatsSugar != null ? editStatsSugar.getText().toString().trim() : "";
                    String statsCafStr = editStatsCaffeine != null ? editStatsCaffeine.getText().toString().trim() : "";

                    if (!TextUtils.isEmpty(dailyCafStr)) {
                        int dailyLimit = Integer.parseInt(dailyCafStr);
                        if (dailyLimit > 0) {
                            UserSettings.setDailyCaffeineLimit(requireContext(), dailyLimit);
                        }
                    }
                    if (!TextUtils.isEmpty(statsCalStr)) {
                        int calorieLimit = Integer.parseInt(statsCalStr);
                        if (calorieLimit > 0) {
                            UserSettings.setStatsDailyCalorieLimit(requireContext(), calorieLimit);
                        }
                    }
                    if (!TextUtils.isEmpty(statsSugarStr)) {
                        int sugarLimit = Integer.parseInt(statsSugarStr);
                        if (sugarLimit > 0) {
                            UserSettings.setStatsDailySugarLimit(requireContext(), sugarLimit);
                        }
                    }
                    if (!TextUtils.isEmpty(statsCafStr)) {
                        int caffeineLimit = Integer.parseInt(statsCafStr);
                        if (caffeineLimit > 0) {
                            UserSettings.setStatsDailyCaffeineLimit(requireContext(), caffeineLimit);
                        }
                    }

                    Toast.makeText(requireContext(), "已保存上限设置", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
