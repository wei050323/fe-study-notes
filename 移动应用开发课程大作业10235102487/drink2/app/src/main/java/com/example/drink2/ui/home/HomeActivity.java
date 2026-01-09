package com.example.drink2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRecord;
import com.example.drink2.model.DrinkRepository;
import com.example.drink2.ui.add.AddDrinkActivity;
import com.example.drink2.ui.stats.StatsOverviewActivity;
import com.example.drink2.view.CaffeineProgressView;
import com.example.drink2.model.UserSettings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView textDate;
    private TextView textTodayCaffeineBig;
    private TextView textTodayStatus;
    private RecyclerView recyclerView;
    private RecyclerView calendarRecycler;
    private CaffeineProgressView caffeineProgressView;

    private DrinkAdapter adapter;
    private DrinkRepository repository;

    private Calendar currentMonthCalendar;
    private CalendarDayAdapter calendarDayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        repository = DrinkRepository.getInstance();

        // ===== View Binding =====
        textDate = findViewById(R.id.text_date);
        textTodayCaffeineBig = findViewById(R.id.text_today_caffeine_big);
        textTodayStatus = findViewById(R.id.text_today_status);
        recyclerView = findViewById(R.id.recycler_drinks);
        calendarRecycler = findViewById(R.id.recycler_calendar);
        caffeineProgressView = findViewById(R.id.caffeine_progress);

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        MaterialButton buttonStats = findViewById(R.id.button_stats);

       
        // ===== Drink List =====
        adapter = new DrinkAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemDeleteListener(record -> {
            repository.removeRecord(record);
            refreshData();
        });

        new ItemTouchHelper(new SwipeToDeleteCallback()).attachToRecyclerView(recyclerView);

        // ===== Calendar =====
        currentMonthCalendar = Calendar.getInstance();
        currentMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        calendarDayAdapter = new CalendarDayAdapter();
        calendarRecycler.setLayoutManager(new GridLayoutManager(this, 7));
        calendarRecycler.setAdapter(calendarDayAdapter);

        
        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddDrinkActivity.class))
        );

        buttonStats.setOnClickListener(v ->
                startActivity(new Intent(this, StatsOverviewActivity.class))
        );

        updateMonthAndCalendar();
        refreshData();
    }

    // ================== 数据刷新 ==================

    private void refreshData() {
        List<DrinkRecord> todayRecords = repository.getTodayRecords();
        Collections.sort(todayRecords, (o1, o2) ->
                Long.compare(o2.getTimestampMillis(), o1.getTimestampMillis())
        );

        adapter.submitList(todayRecords);

        int total = repository.getTodayCaffeineTotal();
        int limit = UserSettings.getDailyCaffeineLimit(this);

        textTodayCaffeineBig.setText(String.valueOf(total));

        if (total > limit) {
            textTodayStatus.setText("已超过建议摄入量");
            textTodayStatus.setTextColor(getColor(R.color.warning_red));
        } else {
            textTodayStatus.setText("在安全范围内");
            textTodayStatus.setTextColor(getColor(R.color.text_secondary));
        }

        caffeineProgressView.setCaffeine(total, limit);
        calendarDayAdapter.notifyDataSetChanged();
    }

    // ================== 月历 ==================

    private void updateMonthAndCalendar() {
        Date date = currentMonthCalendar.getTime();
        textDate.setText(DateFormat.format("yyyy年M月", date));

        List<CalendarDayItem> days = new ArrayList<>();

        Calendar cal = (Calendar) currentMonthCalendar.clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int offset = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        for (int i = 0; i < offset; i++) days.add(new CalendarDayItem());

        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int d = 1; d <= max; d++) {
            cal.set(Calendar.DAY_OF_MONTH, d);
            days.add(new CalendarDayItem(d, cal.getTimeInMillis()));
        }

        calendarDayAdapter.submitList(days);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    // ================== 内部类 ==================

    private static class CalendarDayItem {
        final int day;
        final long time;
        final boolean empty;

        CalendarDayItem() {
            this.day = 0;
            this.time = 0;
            this.empty = true;
        }

        CalendarDayItem(int day, long time) {
            this.day = day;
            this.time = time;
            this.empty = false;
        }
    }

    // Swipe 删除
    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView rv,
                              @NonNull RecyclerView.ViewHolder vh,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int dir) {
            int position = vh.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                adapter.removeItem(position);
            }
        }
    }
    private class CalendarDayAdapter
            extends RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder> {

        private final List<CalendarDayItem> items = new ArrayList<>();

        @NonNull
        @Override
        public DayViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {

            View view = getLayoutInflater()
                    .inflate(R.layout.item_calendar_day, parent, false);
            return new DayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
                @NonNull DayViewHolder holder, int position) {

            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void submitList(List<CalendarDayItem> list) {
            items.clear();
            if (list != null) {
                items.addAll(list);
            }
            notifyDataSetChanged();
        }

        class DayViewHolder extends RecyclerView.ViewHolder {

            TextView textDay;
            ImageView imageBrandLogo;
            View overDot;
            View layoutCell;

            DayViewHolder(@NonNull View itemView) {
                super(itemView);
                textDay = itemView.findViewById(R.id.text_day);
                imageBrandLogo = itemView.findViewById(R.id.image_brand_logo);
                overDot = itemView.findViewById(R.id.view_over_dot);
                layoutCell = itemView.findViewById(R.id.layout_cell);
            }

            void bind(CalendarDayItem item) {
                if (item.empty) {
                    textDay.setVisibility(View.GONE);
                    imageBrandLogo.setVisibility(View.GONE);
                    overDot.setVisibility(View.GONE);
                    layoutCell.setBackgroundColor(
                            getColor(R.color.transparent));
                    return;
                }

                textDay.setVisibility(View.VISIBLE);
                textDay.setText(String.valueOf(item.day));
                imageBrandLogo.setVisibility(View.GONE);
                overDot.setVisibility(View.GONE);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(item.time);

                List<DrinkRecord> records =
                        repository.getDayRecords(
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH));

                if (!records.isEmpty()) {
                    DrinkRecord first = records.get(0);
                    imageBrandLogo.setImageResource(
                            first.getType() == DrinkRecord.DrinkType.COFFEE
                                    ? R.drawable.ic_drink_coffee
                                    : R.drawable.ic_drink_milktea
                    );
                    imageBrandLogo.setVisibility(View.VISIBLE);

                    int total = 0;
                    for (DrinkRecord r : records) {
                        total += r.getCaffeineMg();
                    }
                    if (total > UserSettings.getDailyCaffeineLimit(HomeActivity.this)) {
                        overDot.setVisibility(View.VISIBLE);
                    }
                }

                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(
                            HomeActivity.this,
                            AddDrinkActivity.class);
                    intent.putExtra(
                            AddDrinkActivity.EXTRA_TARGET_DATE_MILLIS,
                            item.time);
                    startActivity(intent);
                });
            }
        }
    }

}
