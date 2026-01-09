package com.example.drinktracker.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink2.R;
import com.example.drinktracker.model.DrinkRecord;
import com.google.android.material.chip.Chip;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkRecord> drinkRecords;

    public DrinkAdapter(List<DrinkRecord> drinkRecords) {
        this.drinkRecords = drinkRecords;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkRecord record = drinkRecords.get(position);
        holder.drinkName.setText(record.getName());
        holder.drinkTime.setText(record.getTime());
        holder.caffeine.setText(String.format("%dmg", record.getCaffeine()));
        holder.sugar.setText(record.getSugarLevel());
    }

    @Override
    public int getItemCount() {
        return drinkRecords.size();
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView drinkName;
        TextView drinkTime;
        TextView caffeine;
        Chip sugar;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.tv_drink_name);
            drinkTime = itemView.findViewById(R.id.tv_drink_time);
            caffeine = itemView.findViewById(R.id.tv_caffeine);
            sugar = itemView.findViewById(R.id.chip_sugar);
        }
    }
}
