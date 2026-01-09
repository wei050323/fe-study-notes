package com.example.drink2.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DrinkRepository {

    private static final int DAILY_LIMIT_MG = 400;
    private static DrinkRepository instance;

    private final List<DrinkRecord> records = new ArrayList<>();

    private DrinkRepository() {
        initMockData();
    }

    public static DrinkRepository getInstance() {
        if (instance == null) {
            instance = new DrinkRepository();
        }
        return instance;
    }

    private void initMockData() {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.YEAR, -2);

        Calendar today = Calendar.getInstance();

        String[] brands = {"Starbucks", "瑞幸", "喜茶", "奈雪"};
        String[] coffeeNames = {"美式咖啡", "拿铁", "卡布奇诺", "摩卡", "冷萃咖啡", "澳白"};
        String[] milkTeaNames = {"珍珠奶茶", "芝士奶盖茶", "乌龙奶茶", "多肉葡萄", "四季春茶"};

        while (start.before(today)) {

            // 控制频率：约 60% 的日子有记录
            if (Math.random() < 0.6) {

                int dailyCount = 1;
                if (Math.random() < 0.15) dailyCount = 2;   // 偶尔两杯
                if (Math.random() < 0.03) dailyCount = 3;   // 极少三杯（超标日）

                for (int i = 0; i < dailyCount; i++) {
                    boolean isCoffee = Math.random() < 0.55;

                    String brand = brands[(int)(Math.random() * brands.length)];
                    String name = isCoffee
                            ? coffeeNames[(int)(Math.random() * coffeeNames.length)]
                            : milkTeaNames[(int)(Math.random() * milkTeaNames.length)];

                    DrinkRecord.DrinkType type = isCoffee
                            ? DrinkRecord.DrinkType.COFFEE
                            : DrinkRecord.DrinkType.MILK_TEA;

                    int caffeine = isCoffee
                            ? 80 + (int)(Math.random() * 70)
                            : 20 + (int)(Math.random() * 50);

                    int calorie = isCoffee
                            ? 50 + (int)(Math.random() * 120)
                            : 180 + (int)(Math.random() * 150);

                    int sugar = isCoffee
                            ? (Math.random() < 0.4 ? 0 : 5 + (int)(Math.random() * 10))
                            : 15 + (int)(Math.random() * 20);

                    DrinkRecord.SugarLevel sugarLevel =
                            sugar == 0 ? DrinkRecord.SugarLevel.NONE :
                                    sugar < 15 ? DrinkRecord.SugarLevel.HALF :
                                            DrinkRecord.SugarLevel.FULL;

                    String size = Math.random() < 0.4 ? "M" : "L";
                    String temp = Math.random() < 0.6 ? "冰" : "热";

                    List<String> toppings = new ArrayList<>();
                    if (!isCoffee && Math.random() < 0.4) toppings.add("珍珠");
                    if (!isCoffee && Math.random() < 0.2) toppings.add("椰果");

                    Calendar time = (Calendar) start.clone();
                    time.set(Calendar.HOUR_OF_DAY, 9 + i * 4);
                    time.set(Calendar.MINUTE, (int)(Math.random() * 60));

                    records.add(new DrinkRecord(
                            name,
                            brand,
                            type,
                            size,
                            sugarLevel,
                            caffeine,
                            calorie,
                            sugar,
                            isCoffee ? 22f + (float)Math.random() * 8 : 24f + (float)Math.random() * 10,
                            temp,
                            toppings,
                            "",
                            time.getTimeInMillis()
                    ));
                }
            }

            start.add(Calendar.DAY_OF_YEAR, 1);
        }
    }


    private List<String> createToppings(String... toppings) {
        List<String> list = new ArrayList<>();
        for (String topping : toppings) {
            list.add(topping);
        }
        return list;
    }

    public int getDailyLimitMg() {
        return DAILY_LIMIT_MG;
    }

    public List<DrinkRecord> getTodayRecords() {
        List<DrinkRecord> today = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        for (DrinkRecord record : records) {
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == y
                    && calendar.get(Calendar.MONTH) == m
                    && calendar.get(Calendar.DAY_OF_MONTH) == d) {
                today.add(record);
            }
        }
        return today;
    }

    public int getTodayCaffeineTotal() {
        int sum = 0;
        for (DrinkRecord record : getTodayRecords()) {
            sum += record.getCaffeineMg();
        }
        return sum;
    }

    public void addRecord(DrinkRecord record) {
        records.add(record);
    }

    public boolean removeRecord(DrinkRecord record) {
        return records.remove(record);
    }

    public int[] getLast7DaysTotals() {
        int[] totals = new int[7];
        Calendar now = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            Calendar day = (Calendar) now.clone();
            day.add(Calendar.DAY_OF_YEAR, -(6 - i));
            int y = day.get(Calendar.YEAR);
            int m = day.get(Calendar.MONTH);
            int d = day.get(Calendar.DAY_OF_MONTH);
            int sum = 0;
            for (DrinkRecord record : records) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(record.getTimestampMillis());
                if (c.get(Calendar.YEAR) == y
                        && c.get(Calendar.MONTH) == m
                        && c.get(Calendar.DAY_OF_MONTH) == d) {
                    sum += record.getCaffeineMg();
                }
            }
            totals[i] = sum;
        }
        return totals;
    }

    public int[] getMonthDailyTotals(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int[] totals = new int[daysInMonth];

        for (DrinkRecord record : records) {
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                if (day >= 1 && day <= daysInMonth) {
                    totals[day - 1] += record.getCaffeineMg();
                }
            }
        }
        return totals;
    }

    public List<DrinkRecord> getDayRecords(int year, int month, int day) {
        List<DrinkRecord> dayRecords = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        for (DrinkRecord record : records) {
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                dayRecords.add(record);
            }
        }
        return dayRecords;
    }

    public List<DrinkRecord> getMonthRecords(int year, int month) {
        List<DrinkRecord> monthRecords = new ArrayList<>();
        for (DrinkRecord record : records) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month) {
                monthRecords.add(record);
            }
        }
        return monthRecords;
    }

    public List<DrinkRecord> getYearRecords(int year) {
        List<DrinkRecord> yearRecords = new ArrayList<>();
        for (DrinkRecord record : records) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == year) {
                yearRecords.add(record);
            }
        }
        return yearRecords;
    }

    public int[] getMonthDailyCounts(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int[] counts = new int[daysInMonth];

        for (DrinkRecord record : records) {
            calendar.setTimeInMillis(record.getTimestampMillis());
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                if (day >= 1 && day <= daysInMonth) {
                    counts[day - 1]++;
                }
            }
        }
        return counts;
    }

    public int[] getYearMonthlyCounts(int year) {
        int[] counts = new int[12];
        for (int month = 0; month < 12; month++) {
            List<DrinkRecord> monthRecords = getMonthRecords(year, month);
            counts[month] = monthRecords.size();
        }
        return counts;
    }
}
