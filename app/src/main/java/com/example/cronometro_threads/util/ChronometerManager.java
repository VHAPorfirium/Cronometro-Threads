package com.example.cronometro_threads.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.example.cronometro_threads.model.Lap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Classe que gerencia o cronômetro usando Handler e Runnable
public class ChronometerManager {

    // Interface para callbacks de tempo e atualização de voltas
    public interface ChronometerListener {
        void onTick(String formattedTime);          // chamado a cada tick para atualizar UI
        void onLapListUpdated(List<Lap> laps);      // chamado quando lista de voltas muda
    }

    private final Handler handler = new Handler(Looper.getMainLooper());               // handler da UI thread
    private final TimerRunnable timerRunnable = new TimerRunnable(this);               // runnable para ticks periódicos
    private final ChronometerListener listener;                                       // listener para notificar a UI

    private long startTime = 0L;      // instante em ms de início da contagem
    private long timeBuff = 0L;       // tempo acumulado quando pausado
    private boolean isRunning = false;// flag de controle de estado

    private final List<Lap> laps = new ArrayList<>(); // lista de voltas registradas

    // Construtor recebe listener para callbacks
    public ChronometerManager(ChronometerListener listener) {
        this.listener = listener;
    }

    // Inicia o cronômetro se não estiver rodando
    public void start() {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis();   // marca instante inicial
            handler.post(timerRunnable);              // começa o loop de atualização
            isRunning = true;
        }
    }

    // Pausa o cronômetro salvando o tempo decorrido
    public void pause() {
        if (isRunning) {
            timeBuff += SystemClock.uptimeMillis() - startTime; // acumula tempo
            handler.removeCallbacks(timerRunnable);            // interrompe atualizações
            isRunning = false;
        }
    }

    // Reseta cronômetro e limpa lista de voltas
    public void reset() {
        handler.removeCallbacks(timerRunnable);               // para atualizações
        isRunning = false;
        startTime = 0L;
        timeBuff = 0L;
        laps.clear();
        listener.onTick(formatTime(0));           // zera display
        listener.onLapListUpdated(laps);          // limpa lista de voltas
    }

    // Registra uma volta no estado atual
    public void lap() {
        long elapsed = getElapsed();                // tempo até agora
        int lapNumber = laps.size() + 1;
        laps.add(new Lap(lapNumber, formatTime(elapsed)));
        listener.onLapListUpdated(laps);            // notifica adapter
    }

    // Retorna o tempo total decorrido em ms
    long getElapsed() {
        return isRunning
                ? (SystemClock.uptimeMillis() - startTime + timeBuff)
                : timeBuff;
    }

    // Chamado pelo runnable a cada 10ms para atualizar a UI
    void update() {
        long elapsed = getElapsed();
        listener.onTick(formatTime(elapsed));     // atualiza TextView
        handler.postDelayed(timerRunnable, 10);   // agenda próximo tick
    }

    // Formata milissegundos em MM:SS:CS
    private String formatTime(long ms) {
        int minutes = (int) (ms / 60000);
        int seconds = (int) ((ms % 60000) / 1000);
        int centis = (int) ((ms % 1000) / 10);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, centis);
    }
}