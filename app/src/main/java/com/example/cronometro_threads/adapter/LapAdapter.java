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

    // Lista de voltas a exibir
    private List<Lap> lapList;

    // Construtor recebe lista inicial de voltas
    public LapAdapter(List<Lap> lapList) {
        this.lapList = lapList;
    }

    // Atualiza a lista de voltas e notifica o RecyclerView
    public void updateLaps(List<Lap> newLaps) {
        this.lapList = newLaps;
        notifyDataSetChanged(); // redesenha todos os itens
    }

    // Infla o layout de cada item de volta
    @NonNull
    @Override
    public LapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lap, parent, false);
        return new LapViewHolder(view);
    }

    // Associa os dados da volta à ViewHolder
    @Override
    public void onBindViewHolder(@NonNull LapViewHolder holder, int position) {
        Lap lap = lapList.get(position);
        holder.tvLap.setText(
                "Volta " + lap.getNumber() + ": " + lap.getFormattedTime()
        );
    }

    // Número total de voltas na lista
    @Override
    public int getItemCount() {
        return lapList == null ? 0 : lapList.size();
    }

    // ViewHolder: mantém a referência ao TextView de cada volta
    static class LapViewHolder extends RecyclerView.ViewHolder {
        TextView tvLap;
        LapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLap = itemView.findViewById(R.id.tvLap); // encontra o TextView
        }
    }
}
