package com.example.drinktracker.ui.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drink2.R;
import com.example.drinktracker.model.DrinkRecord;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddDrinkActivity extends AppCompatActivity {

    private MaterialButtonToggleGroup toggleDrinkType, toggleSize;
    private ChipGroup chipGroupSugar;
    private TextView tvCaffeineResult, tvCaloriesResult;
    private Button btnSave;

    private String drinkType = "coffee";
    private String size = "M";
    private String sugarLevel = "半糖";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        toggleDrinkType = findViewById(R.id.toggle_drink_type);
        toggleSize = findViewById(R.id.toggle_size);
        chipGroupSugar = findViewById(R.id.chip_group_sugar);
        tvCaffeineResult = findViewById(R.id.tv_caffeine_result);
        tvCaloriesResult = findViewById(R.id.tv_calories_result);
        btnSave = findViewById(R.id.btn_save);

        setupListeners();
        toggleDrinkType.check(R.id.btn_coffee);
        toggleSize.check(R.id.btn_size_m);
        chipGroupSugar.check(R.id.chip_sugar_half);
        updateResults();

        btnSave.setOnClickListener(v -> saveRecord());
    }

    private void setupListeners() {
        toggleDrinkType.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btn_coffee) {
                    drinkType = "coffee";
                } else if (checkedId == R.id.btn_milk_tea) {
                    drinkType = "milk_tea";
                }
                updateResults();
            }
        });

        toggleSize.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btn_size_s) {
                    size = "S";
                } else if (checkedId == R.id.btn_size_m) {
                    size = "M";
                } else if (checkedId == R.id.btn_size_l) {
                    size = "L";
                }
                updateResults();
            }
        });

        chipGroupSugar.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                sugarLevel = chip.getText().toString();
                updateResults();
            }
        });
    }

    private void updateResults() {
        int caffeine = calculateCaffeine();
        int calories = calculateCalories();
        tvCaffeineResult.setText(String.format(Locale.getDefault(), "%d mg", caffeine));
        tvCaloriesResult.setText(String.format(Locale.getDefault(), "%d kcal", calories));
    }

    private int calculateCaffeine() {
        int baseCaffeine = drinkType.equals("coffee") ? 100 : 50;
        switch (size) {
            case "S":
                return (int) (baseCaffeine * 0.8);
            case "L":
                return (int) (baseCaffeine * 1.5);
            default: // M
                return baseCaffeine;
        }
    }

    private int calculateCalories() {
        int baseCalories = drinkType.equals("coffee") ? 15 : 200;
        int sizeMultiplier;
        switch (size) {
            case "S":
                sizeMultiplier = 8;
                break;
            case "L":
                sizeMultiplier = 15;
                break;
            default: // M
                sizeMultiplier = 10;
                break;
        }

        int sugarCalories;
        switch (sugarLevel) {
            case "全糖":
                sugarCalories = 100;
                break;
            case "半糖":
                sugarCalories = 50;
                break;
            default: // 无糖
                sugarCalories = 0;
                break;
        }
        return baseCalories + sizeMultiplier * 10 + sugarCalories;
    }

    private void saveRecord() {
        String name = (drinkType.equals("coffee") ? "咖啡" : "奶茶") + " (" + size + ", " + sugarLevel + ")";
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        int caffeine = calculateCaffeine();

        DrinkRecord newRecord = new DrinkRecord(name, time, caffeine, sugarLevel, drinkType);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_drink", newRecord);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
