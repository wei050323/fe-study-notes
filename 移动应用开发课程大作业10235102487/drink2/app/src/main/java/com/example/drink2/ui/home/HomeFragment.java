package com.example.drink2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRecord;
import com.example.drink2.model.DrinkRepository;
import com.example.drink2.model.UserSettings;
import com.example.drink2.ui.add.AddDrinkActivity;
import com.example.drink2.view.CaffeineProgressView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView textDate;
    private TextView textYear;
    private TextView textTodayCaffeineBig;
    private TextView textTodayStatus;
    private RecyclerView calendarRecycler;
    private RecyclerView recyclerView;
    private CaffeineProgressView caffeineProgressView;
    private DrinkAdapter adapter;
    private DrinkRepository repository;

    private Calendar currentMonthCalendar;
    private CalendarDayAdapter calendarDayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    //初始化与事件绑定
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = DrinkRepository.getInstance();

        textDate = view.findViewById(R.id.text_date);
        textYear = view.findViewById(R.id.text_year);
        textTodayCaffeineBig = view.findViewById(R.id.text_today_caffeine_big);
        textTodayStatus = view.findViewById(R.id.text_today_status);
        recyclerView = view.findViewById(R.id.recycler_drinks);
        caffeineProgressView = view.findViewById(R.id.caffeine_progress);
        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        MaterialButton buttonStats = view.findViewById(R.id.button_stats);
        calendarRecycler = view.findViewById(R.id.recycler_calendar);
        TextView buttonPrevMonth = view.findViewById(R.id.text_prev_month);
        TextView buttonNextMonth = view.findViewById(R.id.text_next_month);
        TextView buttonMonthLabel = view.findViewById(R.id.text_month_label);


        if (buttonStats != null) {
            buttonStats.setVisibility(View.GONE);
        }

        // 年份点击选择年份
        if (textYear != null) {
            textYear.setOnClickListener(v -> showYearPicker());
        }

        adapter = new DrinkAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(null);
        adapter.setOnItemDeleteListener(record -> {
            repository.removeRecord(record);
            refreshData();
        });

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    adapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(@NonNull android.graphics.Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    android.graphics.drawable.ColorDrawable background = new android.graphics.drawable.ColorDrawable(getResources().getColor(R.color.warning_red));
                    background.setBounds((int) (itemView.getRight() + dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    background.draw(c);

                    android.graphics.drawable.Drawable deleteIcon = getResources().getDrawable(android.R.drawable.ic_menu_delete);
                    int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + iconMargin;
                    int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                    int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    deleteIcon.setTint(getResources().getColor(android.R.color.white));
                    deleteIcon.draw(c);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        currentMonthCalendar = Calendar.getInstance();
        currentMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        calendarDayAdapter = new CalendarDayAdapter();
        calendarRecycler.setLayoutManager(new GridLayoutManager(getContext(), 7));
        calendarRecycler.setAdapter(calendarDayAdapter);

        View.OnClickListener prevClick = v -> {
            currentMonthCalendar.add(Calendar.MONTH, -1);
            updateMonthAndCalendar();
        };
        View.OnClickListener nextClick = v -> {
            currentMonthCalendar.add(Calendar.MONTH, 1);
            updateMonthAndCalendar();
        };

        buttonPrevMonth.setOnClickListener(prevClick);
        buttonNextMonth.setOnClickListener(nextClick);
        buttonMonthLabel.setOnClickListener(v -> {
            // 中间月份按钮默认跳转到下个月，形成轻量快捷切换
            currentMonthCalendar.add(Calendar.MONTH, 1);
            updateMonthAndCalendar();
        });

        fabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddDrinkActivity.class);
            startActivity(intent);
        });

        updateMonthAndCalendar();
        refreshData();
    }

    //生成当月天格与占位
    private void updateMonthAndCalendar() {
        Date monthDate = currentMonthCalendar.getTime();
        CharSequence yearText = DateFormat.format("yyyy年", monthDate);
        CharSequence monthText = DateFormat.format("M月", monthDate);
        if (textYear != null) {
            textYear.setText(yearText);
        }
        // 为了兼容旧布局，如果 textDate 存在，让它显示“yyyy年M月”
        if (textDate != null) {
            textDate.setText(yearText + "" + monthText);
        }
        // 同时更新中间的月份按钮文本
        View root = getView();
        if (root != null) {
            TextView monthTextView = root.findViewById(R.id.text_month_label);
            if (monthTextView != null) {
                monthTextView.setText(monthText);
            }
        }

        List<CalendarDayItem> days = new ArrayList<>();

        Calendar firstDay = (Calendar) currentMonthCalendar.clone();
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);

        int offset = firstDayOfWeek - Calendar.MONDAY;
        if (offset < 0) {
            offset = 6;
        }

        for (int i = 0; i < offset; i++) {
            days.add(new CalendarDayItem());
        }

        int maxDay = firstDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int d = 1; d <= maxDay; d++) {
            firstDay.set(Calendar.DAY_OF_MONTH, d);
            long time = firstDay.getTimeInMillis();
            days.add(new CalendarDayItem(d, time));
        }

        calendarDayAdapter.submitList(days);
    }

    //弹出年份选择器，修改当前月份所在的年份，并刷新月历
    private void showYearPicker() {
        if (getContext() == null) return;

        final Calendar cal = (Calendar) currentMonthCalendar.clone();
        final int currentYearValue = cal.get(Calendar.YEAR);

        NumberPicker picker = new NumberPicker(requireContext());
        // 合理的年份范围，可按需调整
        int minYear = 2000;
        int maxYear = currentYearValue + 10;
        picker.setMinValue(minYear);
        picker.setMaxValue(maxYear);
        picker.setValue(currentYearValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("选择年份");
        builder.setView(picker);
        builder.setPositiveButton("确定", (dialog, which) -> {
            int selectedYear = picker.getValue();
            currentMonthCalendar.set(Calendar.YEAR, selectedYear);
            updateMonthAndCalendar();
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void refreshData() {
        List<DrinkRecord> todayRecords = repository.getTodayRecords();
        Collections.sort(todayRecords, new Comparator<DrinkRecord>() {
            @Override
            public int compare(DrinkRecord o1, DrinkRecord o2) {
                return Long.compare(o2.getTimestampMillis(), o1.getTimestampMillis());
            }
        });

        adapter.submitList(todayRecords);

        int total = repository.getTodayCaffeineTotal();
        int limit = UserSettings.getDailyCaffeineLimit(requireContext());
        textTodayCaffeineBig.setText(String.valueOf(total));
        if (total > limit) {
            textTodayStatus.setText("已超过建议摄入量");
            textTodayStatus.setTextColor(getResources().getColor(R.color.warning_red));
        } else {
            textTodayStatus.setText("在安全范围内");
            textTodayStatus.setTextColor(getResources().getColor(R.color.text_secondary));
        }
        caffeineProgressView.setCaffeine(total, limit);

        calendarDayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private static class CalendarDayItem {
        final int dayOfMonth;
        final long timeMillis;
        final boolean isEmpty;

        CalendarDayItem(int dayOfMonth, long timeMillis) {
            this.dayOfMonth = dayOfMonth;
            this.timeMillis = timeMillis;
            this.isEmpty = false;
        }

        CalendarDayItem() {
            this.dayOfMonth = 0;
            this.timeMillis = 0;
            this.isEmpty = true;
        }
    }

    private class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder> {

        private final List<CalendarDayItem> items = new ArrayList<>();

        @Override
        public DayViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_calendar_day, parent, false);
            return new DayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DayViewHolder holder, int position) {
            CalendarDayItem item = items.get(position);
            holder.bind(item);
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
            private final TextView textDay;
            private final ImageView imageBrandLogo;
            private final View overDot;
            private final android.view.View layoutCell;

            DayViewHolder(android.view.View itemView) {
                super(itemView);
                textDay = itemView.findViewById(R.id.text_day);
                imageBrandLogo = itemView.findViewById(R.id.image_brand_logo);
                overDot = itemView.findViewById(R.id.view_over_dot);
                layoutCell = itemView.findViewById(R.id.layout_cell);
            }

            void bind(CalendarDayItem item) {
                // 保证单元格为正方形：根据 RecyclerView 的总宽度平均分配为 7 列
                itemView.post(() -> {
                    android.view.View parent = (android.view.View) itemView.getParent();
                    if (parent instanceof RecyclerView) {
                        RecyclerView rv = (RecyclerView) parent;
                        int totalWidth = rv.getWidth();
                        if (totalWidth > 0) {
                            int horizontalPadding = rv.getPaddingLeft() + rv.getPaddingRight();
                            int cellWidth = (totalWidth - horizontalPadding) / 7;
                            if (cellWidth > 0) {
                                ViewGroup.LayoutParams lp = layoutCell.getLayoutParams();
                                if (lp != null && lp.height != cellWidth) {
                                    lp.height = cellWidth;
                                    layoutCell.setLayoutParams(lp);
                                }
                            }
                        }
                    }
                });
                if (item.isEmpty) {
                    textDay.setVisibility(View.GONE);
                    imageBrandLogo.setVisibility(View.GONE);
                    overDot.setVisibility(View.GONE);
                    layoutCell.setBackgroundColor(getResources().getColor(R.color.transparent));
                    itemView.setClickable(false);
                    return;
                }

                itemView.setClickable(true);
                textDay.setText(String.valueOf(item.dayOfMonth));
                imageBrandLogo.setVisibility(View.GONE);
                overDot.setVisibility(View.GONE);

                Calendar cellCal = Calendar.getInstance();
                cellCal.setTimeInMillis(item.timeMillis);
                int year = cellCal.get(Calendar.YEAR);
                int month = cellCal.get(Calendar.MONTH);
                int day = cellCal.get(Calendar.DAY_OF_MONTH);

                List<DrinkRecord> dayRecords = repository.getDayRecords(year, month, day);

                if (dayRecords.isEmpty()) {
                    layoutCell.setBackgroundColor(getResources().getColor(R.color.chip_bg));
                    textDay.setTextColor(getResources().getColor(R.color.text_secondary));
                    textDay.setVisibility(View.VISIBLE);
                } else {
                    DrinkRecord firstRecord = dayRecords.get(0);
                    int logoRes = resolveBrandLogo(firstRecord);
                    imageBrandLogo.setImageResource(logoRes);
                    imageBrandLogo.setVisibility(View.VISIBLE);
                    imageBrandLogo.clearColorFilter();
                    layoutCell.setBackgroundColor(getResources().getColor(R.color.card_background));
                    textDay.setTextColor(getResources().getColor(R.color.text_primary));
                    textDay.setVisibility(View.VISIBLE);

                    int totalCaffeine = 0;
                    for (DrinkRecord record : dayRecords) {
                        totalCaffeine += record.getCaffeineMg();
                    }
                    if (totalCaffeine > UserSettings.getDailyCaffeineLimit(requireContext())) {
                        overDot.setVisibility(View.VISIBLE);
                    }
                }

                // 点击行为：有记录则弹出当日记录列表，无记录则跳转到添加页
                itemView.setOnClickListener(v -> {
                    if (dayRecords.isEmpty()) {
                        Intent intent = new Intent(v.getContext(), AddDrinkActivity.class);
                        intent.putExtra(AddDrinkActivity.EXTRA_TARGET_DATE_MILLIS, item.timeMillis);
                        v.getContext().startActivity(intent);
                    } else {
                        // 显示当天记录列表
                        List<String> lines = new ArrayList<>();
                        for (DrinkRecord record : dayRecords) {
                            CharSequence time = DateFormat.format("HH:mm", record.getTimestampMillis());
                            String line = time + "  " + record.getDrinkName()
                                    + "（" + record.getBrandName() + "）"
                                    + "  " + record.getCaffeineMg() + "mg";
                            lines.add(line);
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle((month + 1) + "月" + day + "日记录");
                        builder.setItems(lines.toArray(new String[0]), null);
                        builder.setPositiveButton("添加记录", (dialog, which) -> {
                            Intent intent = new Intent(v.getContext(), AddDrinkActivity.class);
                            intent.putExtra(AddDrinkActivity.EXTRA_TARGET_DATE_MILLIS, item.timeMillis);
                            v.getContext().startActivity(intent);
                        });
                        builder.setNegativeButton("关闭", null);
                        builder.show();
                    }
                });
            }
        }

        //与列表一致：根据品牌名称选择对应 logo。
        private int resolveBrandLogo(DrinkRecord record) {
            String brand = record.getBrandName();
            if (brand != null) {
                if (brand.contains("喜茶")) {
                    return R.drawable.ic_brand_heicha;
                } else if (brand.contains("奈雪")) {
                    return R.drawable.ic_brand_nayuki;
                } else if (brand.toLowerCase().contains("starbucks") || brand.contains("星巴克")) {
                    return R.drawable.ic_brand_starbucks;
                } else if (brand.toLowerCase().contains("luckin") || brand.contains("瑞幸")) {
                    return R.drawable.ic_brand_luckin;
                }
            }
            return record.getType() == DrinkRecord.DrinkType.COFFEE
                    ? R.drawable.ic_drink_coffee
                    : R.drawable.ic_drink_milktea;
        }
    }
}
