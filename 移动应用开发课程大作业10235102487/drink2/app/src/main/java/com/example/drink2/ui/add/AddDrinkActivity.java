package com.example.drink2.ui.add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRecord;
import com.example.drink2.model.DrinkRepository;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddDrinkActivity extends AppCompatActivity {

    public static final String EXTRA_TARGET_DATE_MILLIS = "extra_target_date_millis";

    // 品牌下拉选项：奈雪的茶、喜茶、星巴克、瑞幸
    private static final String[] BRANDS = {"奈雪的茶", "喜茶", "星巴克", "瑞幸"};
    private static final String[] PRESET_TOPPINGS = {"珍珠", "椰果", "布丁", "奶盖"};

    private TextInputLayout inputLayoutDrinkName;
    private TextInputEditText inputDrinkName;
    private AutoCompleteTextView autoCompleteBrand;
    private TextView textDate;
    private MaterialButtonToggleGroup groupType;
    private MaterialButtonToggleGroup groupSize;
    private ChipGroup groupSugar;
    private ChipGroup groupTemperature;
    private ChipGroup groupToppings;
    private TextInputEditText inputSugarGram;
    private TextInputEditText inputCalorie;
    private TextInputEditText inputPrice;
    private TextInputEditText inputNote;
    private TextView textValueCaffeine;

    private Calendar selectedDate;
    private List<String> customToppings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_add);
        toolbar.setNavigationOnClickListener(view -> finish());

        initViews();
        setupBrands();
        setupDate();
        setupListeners();
        updateSummary();
    }

    private void initViews() {
        inputLayoutDrinkName = findViewById(R.id.input_layout_drink_name);
        inputDrinkName = findViewById(R.id.input_drink_name);
        autoCompleteBrand = findViewById(R.id.auto_complete_brand);
        textDate = findViewById(R.id.text_date);
        groupType = findViewById(R.id.group_type);
        groupSize = findViewById(R.id.group_size);
        groupSugar = findViewById(R.id.group_sugar);
        groupTemperature = findViewById(R.id.group_temperature);
        groupToppings = findViewById(R.id.group_toppings);
        inputSugarGram = findViewById(R.id.input_sugar_gram);
        inputCalorie = findViewById(R.id.input_calorie);
        inputPrice = findViewById(R.id.input_price);
        inputNote = findViewById(R.id.input_note);
        textValueCaffeine = findViewById(R.id.text_value_caffeine);

        groupType.check(R.id.button_type_milk_tea);
        groupSize.check(R.id.button_size_m);
        Chip sugarHalf = findViewById(R.id.chip_sugar_half);
        sugarHalf.setChecked(true);
        Chip tempNormal = findViewById(R.id.chip_temp_normal);
        tempNormal.setChecked(true);
    }

    private void setupBrands() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, BRANDS);
        autoCompleteBrand.setAdapter(adapter);
        autoCompleteBrand.setText(BRANDS[0], false);

        // 点击或获得焦点时展开下拉菜单，清晰展示 4 个品牌
        autoCompleteBrand.setOnClickListener(v -> autoCompleteBrand.showDropDown());
        autoCompleteBrand.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteBrand.showDropDown();
            }
        });
    }

    private void setupDate() {
        selectedDate = Calendar.getInstance();
        long target = getIntent().getLongExtra(EXTRA_TARGET_DATE_MILLIS, -1L);
        if (target > 0) {
            selectedDate.setTimeInMillis(target);
        }
        updateDateText();

        textDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    updateDateText();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.getDefault());
        textDate.setText(sdf.format(selectedDate.getTime()));
    }

    private void setupListeners() {
        MaterialButtonToggleGroup.OnButtonCheckedListener checkedListener =
                (group, checkedId, isChecked) -> {
                    if (isChecked) {
                        updateSummary();
                    }
                };

        groupType.addOnButtonCheckedListener(checkedListener);
        groupSize.addOnButtonCheckedListener(checkedListener);

        groupSugar.setOnCheckedStateChangeListener((group, checkedIds) -> updateSummary());
        groupTemperature.setOnCheckedStateChangeListener((group, checkedIds) -> updateSummary());
        groupToppings.setOnCheckedStateChangeListener((group, checkedIds) -> updateSummary());

        inputSugarGram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateSummary();
            }
        });

        MaterialButton buttonAddTopping = findViewById(R.id.button_add_topping);
        buttonAddTopping.setOnClickListener(v -> showAddToppingDialog());

        MaterialButton buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> saveRecord());
    }

    private void showAddToppingDialog() {
        TextInputEditText input = new TextInputEditText(this);
        input.setHint("输入小料名称");
        input.setSingleLine(true);

        new AlertDialog.Builder(this)
                .setTitle("添加自定义小料")
                .setView(input)
                .setPositiveButton("确认", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty() && !customToppings.contains(name)) {
                        customToppings.add(name);
                        addToppingChip(name, true);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void addToppingChip(String name, boolean checked) {
        Chip chip = new Chip(this);
        chip.setText(name);
        chip.setCheckable(true);
        chip.setChecked(checked);
        chip.setChipBackgroundColorResource(R.color.chip_bg);
        groupToppings.addView(chip);
    }

    private void updateSummary() {
        DrinkRecord.DrinkType type = getSelectedType();
        String size = getSelectedSizeLabel();
        DrinkRecord.SugarLevel sugar = getSelectedSugar();
        String temp = getSelectedTemperature();
        List<String> toppings = getSelectedToppings();

        int baseCaffeine;
        int baseCalorie;
        if (type == DrinkRecord.DrinkType.COFFEE) {
            baseCaffeine = 100;
            baseCalorie = 30;
        } else {
            baseCaffeine = 60;
            baseCalorie = 220;
        }

        float sizeFactor;
        if ("S".equals(size)) {
            sizeFactor = 0.8f;
        } else if ("L".equals(size)) {
            sizeFactor = 1.3f;
        } else {
            sizeFactor = 1.0f;
        }

        float sugarFactor;
        switch (sugar) {
            case FULL:
                sugarFactor = 1.0f;
                break;
            case HALF:
                sugarFactor = 0.7f;
                break;
            case NONE:
                sugarFactor = 0.4f;
                break;
            default:
                sugarFactor = 0.7f;
                break;
        }

        int toppingCalorie = toppings.size() * 30;

        int caffeine = Math.round(baseCaffeine * sizeFactor);
        int calorie = Math.round(baseCalorie * sizeFactor * sugarFactor) + toppingCalorie;

        textValueCaffeine.setText(String.valueOf(caffeine));

        String calorieText = inputCalorie.getText().toString().trim();
        if (calorieText.isEmpty()) {
            inputCalorie.setText(String.valueOf(calorie));
        }
    }

    private DrinkRecord.DrinkType getSelectedType() {
        int checked = groupType.getCheckedButtonId();
        if (checked == R.id.button_type_coffee) {
            return DrinkRecord.DrinkType.COFFEE;
        } else {
            return DrinkRecord.DrinkType.MILK_TEA;
        }
    }

    private String getSelectedSizeLabel() {
        int checked = groupSize.getCheckedButtonId();
        if (checked == R.id.button_size_s) {
            return "S";
        } else if (checked == R.id.button_size_l) {
            return "L";
        } else if (checked == R.id.button_size_custom) {
            return "自定义";
        } else {
            return "M";
        }
    }

    private DrinkRecord.SugarLevel getSelectedSugar() {
        int checkedId = groupSugar.getCheckedChipId();
        if (checkedId == R.id.chip_sugar_full) {
            return DrinkRecord.SugarLevel.FULL;
        } else if (checkedId == R.id.chip_sugar_half) {
            return DrinkRecord.SugarLevel.HALF;
        } else if (checkedId == R.id.chip_sugar_none) {
            return DrinkRecord.SugarLevel.NONE;
        } else {
            return DrinkRecord.SugarLevel.CUSTOM;
        }
    }

    private String getSelectedTemperature() {
        int checkedId = groupTemperature.getCheckedChipId();
        if (checkedId == R.id.chip_temp_hot) {
            return "热";
        } else if (checkedId == R.id.chip_temp_ice) {
            return "冰";
        } else {
            return "常温";
        }
    }

    private List<String> getSelectedToppings() {
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < groupToppings.getChildCount(); i++) {
            Chip chip = (Chip) groupToppings.getChildAt(i);
            if (chip.isChecked()) {
                selected.add(chip.getText().toString());
            }
        }
        return selected;
    }

    private void saveRecord() {
        String drinkName = inputDrinkName.getText().toString().trim();
        if (drinkName.isEmpty()) {
            inputLayoutDrinkName.setError("请输入饮品名称");
            return;
        }
        inputLayoutDrinkName.setError(null);

        String brandName = autoCompleteBrand.getText().toString().trim();
        if (brandName.isEmpty()) {
            Toast.makeText(this, "请选择品牌", Toast.LENGTH_SHORT).show();
            return;
        }

        DrinkRecord.DrinkType type = getSelectedType();
        String size = getSelectedSizeLabel();
        DrinkRecord.SugarLevel sugar = getSelectedSugar();

        int caffeine = Integer.parseInt(textValueCaffeine.getText().toString());

        String calorieText = inputCalorie.getText().toString().trim();
        int calorie = calorieText.isEmpty() ? 0 : Integer.parseInt(calorieText);

        String sugarText = inputSugarGram.getText().toString().trim();
        int sugarGram = sugarText.isEmpty() ? 0 : Integer.parseInt(sugarText);

        String priceText = inputPrice.getText().toString().trim();
        float price = priceText.isEmpty() ? 0f : Float.parseFloat(priceText);

        String temperature = getSelectedTemperature();
        List<String> toppings = getSelectedToppings();

        String note = inputNote.getText().toString().trim();

        long timeMillis = selectedDate.getTimeInMillis();

        DrinkRecord record = new DrinkRecord(
                drinkName,
                brandName,
                type,
                size,
                sugar,
                caffeine,
                calorie,
                sugarGram,
                price,
                temperature,
                toppings,
                note,
                timeMillis
        );

        DrinkRepository.getInstance().addRecord(record);
        finish();
    }
}
