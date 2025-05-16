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

    private TextView tvChrono;
    private Button   btnStartPause, btnLap, btnReset;
    private RecyclerView rvLaps;
    private LapAdapter lapAdapter;
    private ChronometerManager chronoMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        tvChrono      = findViewById(R.id.tvChrono);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnLap        = findViewById(R.id.btnLap);
        btnReset      = findViewById(R.id.btnReset);
        rvLaps        = findViewById(R.id.rvLaps);

        // RecyclerView
        lapAdapter = new LapAdapter(new ArrayList<>());
        rvLaps.setLayoutManager(new LinearLayoutManager(this));
        rvLaps.setAdapter(lapAdapter);

        // ChronometerManager
        chronoMgr = new ChronometerManager(this);
        tvChrono.setText("00:00:00");

        // Listeners
        btnStartPause.setOnClickListener(v -> {
            if (btnStartPause.getText().equals("Iniciar")) {
                chronoMgr.start();
                btnStartPause.setText("Pausar");
            } else {
                chronoMgr.pause();
                btnStartPause.setText("Iniciar");
            }
        });

        btnLap.setOnClickListener(v -> chronoMgr.lap());
        btnReset.setOnClickListener(v -> {
            chronoMgr.reset();
            btnStartPause.setText("Iniciar");
        });
    }

    @Override
    public void onTick(String formattedTime) {
        tvChrono.setText(formattedTime);
    }

    @Override
    public void onLapListUpdated(List<Lap> laps) {
        lapAdapter.updateLaps(laps);
    }
}
