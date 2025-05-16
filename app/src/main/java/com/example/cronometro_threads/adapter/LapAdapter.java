package com.example.cronometro_threads.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cronometro_threads.R;
import com.example.cronometro_threads.model.Lap;

import java.util.List;

public class LapAdapter extends RecyclerView.Adapter<LapAdapter.LapViewHolder> {

    private List<Lap> lapList;

    public LapAdapter(List<Lap> lapList) {
        this.lapList = lapList;
    }

    public void updateLaps(List<Lap> newLaps) {
        this.lapList = newLaps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lap, parent, false);
        return new LapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapViewHolder holder, int position) {
        Lap lap = lapList.get(position);
        holder.tvLap.setText(
                "Volta " + lap.getNumber() + ": " + lap.getFormattedTime()
        );
    }

    @Override
    public int getItemCount() {
        return lapList == null ? 0 : lapList.size();
    }

    static class LapViewHolder extends RecyclerView.ViewHolder {
        TextView tvLap;
        LapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLap = itemView.findViewById(R.id.tvLap);
        }
    }
}
