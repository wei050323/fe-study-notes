package com.example.drink2.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink2.R;
import com.example.drink2.model.DrinkRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//当日列表
public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private final List<DrinkRecord> items = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日", Locale.getDefault());
    private OnItemClickListener onItemClickListener;
    private OnItemDeleteListener onItemDeleteListener;

    public interface OnItemClickListener {
        void onItemClick(DrinkRecord record, Context context);
    }

    public interface OnItemDeleteListener {
        void onItemDelete(DrinkRecord record);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.onItemDeleteListener = listener;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(v);
    }

    @Override
    //格式化日期、名称、备注并设置品牌图标
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkRecord record = items.get(position);
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(record.getTimestampMillis());
        holder.textDate.setText(dateFormat.format(cal.getTime()));

        holder.textName.setText(record.getDrinkName() != null ? record.getDrinkName() : record.getName());

        String note = buildNote(record);
        holder.textNote.setText(note);

        int logoRes = resolveBrandLogo(record);
        holder.imageBrandCircle.setImageResource(logoRes);
        // 品牌 logo 使用原色，不再加灰色滤镜
        holder.imageBrandCircle.clearColorFilter();

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(record, v.getContext());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String message = record.getDrinkName()
                        + "\n品牌：" + (record.getBrandName() != null ? record.getBrandName() : "未知")
                        + "\n时间：" + holder.textDate.getText()
                        + "\n咖啡因：" + record.getCaffeineMg() + "mg"
                        + "\n热量：" + record.getCalorieKcal() + "kcal";
                builder.setTitle("饮品详情")
                        .setMessage(message)
                        .setPositiveButton("知道了", null)
                        .show();
            }
        });
    }

    private String buildNote(DrinkRecord record) {
        List<String> parts = new ArrayList<>();
        
        if (record.getSizeLabel() != null && !record.getSizeLabel().isEmpty()) {
            String sizeText = record.getSizeLabel();
            if ("S".equals(sizeText)) {
                parts.add("小杯");
            } else if ("M".equals(sizeText)) {
                parts.add("中杯");
            } else if ("L".equals(sizeText)) {
                parts.add("大杯");
            } else {
                parts.add(sizeText);
            }
        }

        if (record.getSugarLevel() != null) {
            switch (record.getSugarLevel()) {
                case FULL:
                    parts.add("全糖");
                    break;
                case HALF:
                    parts.add("半糖");
                    break;
                case NONE:
                    parts.add("无糖");
                    break;
                case CUSTOM:
                    if (record.getSugarGram() > 0) {
                        parts.add(record.getSugarGram() + "g糖");
                    }
                    break;
                default:
                    break;
            }
        }

        if (record.getTemperature() != null && !record.getTemperature().isEmpty()) {
            parts.add(record.getTemperature());
        }

        if (parts.isEmpty()) {
            return "无备注";
        }

        return String.join(" ", parts);
    }

    /**
     * 根据品牌名称选择对应 logo 资源；未知品牌按饮品类型选择通用图标。
     * 需要在 res/drawable 中提供:
     *  - ic_brand_heicha    (喜茶)
     *  - ic_brand_nayuki    (奈雪的茶)
     *  - ic_brand_starbucks (星巴克)
     *  - ic_brand_luckin    (瑞幸)
     */
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void submitList(List<DrinkRecord> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    public DrinkRecord getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            DrinkRecord record = items.remove(position);
            notifyItemRemoved(position);
            if (onItemDeleteListener != null) {
                onItemDeleteListener.onItemDelete(record);
            }
        }
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {

        final TextView textDate;
        final ImageView imageBrandCircle;
        final TextView textName;
        final TextView textNote;

        DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.text_date);
            imageBrandCircle = itemView.findViewById(R.id.image_brand_circle);
            textName = itemView.findViewById(R.id.text_drink_name);
            textNote = itemView.findViewById(R.id.text_note);
        }
    }
}

