package com.example.cronometro_threads.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cronometro_threads.R;
import com.example.cronometro_threads.adapter.LapAdapter;
import com.example.cronometro_threads.model.Lap;
import com.example.cronometro_threads.util.ChronometerManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChronometerManager.ChronometerListener {

    // Elementos da interface
    private TextView tvChrono;
    private Button btnStartPause, btnLap, btnReset;
    private RecyclerView rvLaps;

    // Adapter para lista de voltas
    private LapAdapter lapAdapter;
    // Gerenciador do cronômetro
    private ChronometerManager chronoMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // carrega o layout

        // Ajuste de margens para barras do sistema (opcional)
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                }
        );

        // Inicializa views
        tvChrono = findViewById(R.id.tvChrono);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnLap = findViewById(R.id.btnLap);
        btnReset = findViewById(R.id.btnReset);
        rvLaps = findViewById(R.id.rvLaps);

        // Configura RecyclerView para exibir as voltas
        lapAdapter = new LapAdapter(new ArrayList<>());
        rvLaps.setLayoutManager(new LinearLayoutManager(this));
        rvLaps.setAdapter(lapAdapter);

        // Cria o gerenciador de cronômetro e exibe tempo inicial
        chronoMgr = new ChronometerManager(this);
        tvChrono.setText("00:00:00");

        // Botão de iniciar/pausar
        btnStartPause.setOnClickListener(v -> {
            if (btnStartPause.getText().equals("Iniciar")) {
                chronoMgr.start();           // inicia contagem
                btnStartPause.setText("Pausar");
            } else {
                chronoMgr.pause();           // pausa contagem
                btnStartPause.setText("Iniciar");
            }
        });

        // Botão de registrar volta
        btnLap.setOnClickListener(v -> chronoMgr.lap());
        // Botão de resetar cronômetro e voltas
        btnReset.setOnClickListener(v -> {
            chronoMgr.reset();              // zera tudo
            btnStartPause.setText("Iniciar");
        });
    }

    // Atualiza o TextView a cada tick do cronômetro
    @Override
    public void onTick(String formattedTime) {
        tvChrono.setText(formattedTime);
    }

    // Atualiza a lista de voltas quando houver mudança
    @Override
    public void onLapListUpdated(List<Lap> laps) {
        lapAdapter.updateLaps(laps);
    }
}
