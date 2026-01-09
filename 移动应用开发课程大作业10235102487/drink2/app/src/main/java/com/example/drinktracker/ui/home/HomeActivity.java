package com.example.drinktracker.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink2.R;
import com.example.drinktracker.model.DrinkRecord;
import com.example.drinktracker.ui.add.AddDrinkActivity;
import com.example.drinktracker.view.CaffeineProgressView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private TextView tvDate;
    private CaffeineProgressView progressCaffeine;
    private TextView tvCaffeineAmount;
    private RecyclerView rvDrinks;
    private FloatingActionButton fabAdd;

    private DrinkAdapter adapter;
    private List<DrinkRecord> drinkRecords = new ArrayList<>();
    private int totalCaffeine = 0;
    private final int MAX_CAFFEINE = 400;

    private final ActivityResultLauncher<Intent> addDrinkLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    DrinkRecord newRecord = (DrinkRecord) result.getData().getSerializableExtra("new_drink");
                    if (newRecord != null) {
                        drinkRecords.add(0, newRecord);
                        adapter.notifyItemInserted(0);
                        updateCaffeineIntake();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvDate = findViewById(R.id.tv_date);
        progressCaffeine = findViewById(R.id.progress_caffeine);
        tvCaffeineAmount = findViewById(R.id.tv_caffeine_amount);
        rvDrinks = findViewById(R.id.rv_drinks);
        fabAdd = findViewById(R.id.fab_add);

        setupViews();
        loadMockData();
        updateCaffeineIntake();
    }

    private void setupViews() {
        tvDate.setText(new SimpleDateFormat("yyyy年M月", Locale.getDefault()).format(new Date()));

        rvDrinks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DrinkAdapter(drinkRecords);
        rvDrinks.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddDrinkActivity.class);
            addDrinkLauncher.launch(intent);
        });

        progressCaffeine.setMax(MAX_CAFFEINE);
    }

    private void loadMockData() {
        drinkRecords.add(new DrinkRecord("拿铁", "09:30", 120, "半糖", "coffee"));
        drinkRecords.add(new DrinkRecord("珍珠奶茶", "15:00", 100, "全糖", "milk_tea"));
        adapter.notifyDataSetChanged();
    }

    private void updateCaffeineIntake() {
        totalCaffeine = 0;
        for (DrinkRecord record : drinkRecords) {
            totalCaffeine += record.getCaffeine();
        }
        progressCaffeine.setProgress(totalCaffeine);
        tvCaffeineAmount.setText(String.format(Locale.getDefault(), "%dmg / %dmg", totalCaffeine, MAX_CAFFEINE));
    }
}
