package com.example.cronometro_threads.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.example.cronometro_threads.model.Lap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChronometerManager {

    public interface ChronometerListener {
        void onTick(String formattedTime);
        void onLapListUpdated(List<Lap> laps);
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final TimerRunnable timerRunnable = new TimerRunnable(this);
    private final ChronometerListener listener;

    private long startTime = 0L;
    private long timeBuff = 0L;
    private boolean isRunning = false;

    private final List<Lap> laps = new ArrayList<>();

    public ChronometerManager(ChronometerListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis();
            handler.post(timerRunnable);
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            timeBuff += SystemClock.uptimeMillis() - startTime;
            handler.removeCallbacks(timerRunnable);
            isRunning = false;
        }
    }

    public void reset() {
        handler.removeCallbacks(timerRunnable);
        isRunning = false;
        startTime = 0L;
        timeBuff = 0L;
        laps.clear();
        listener.onTick(formatTime(0));
        listener.onLapListUpdated(laps);
    }

    public void lap() {
        long elapsed = getElapsed();
        int lapNumber = laps.size() + 1;
        laps.add(new Lap(lapNumber, formatTime(elapsed)));
        listener.onLapListUpdated(laps);
    }

    long getElapsed() {
        return isRunning
                ? (SystemClock.uptimeMillis() - startTime + timeBuff)
                : timeBuff;
    }

    void update() {
        long elapsed = getElapsed();
        listener.onTick(formatTime(elapsed));
        handler.postDelayed(timerRunnable, 10);
    }

    private String formatTime(long ms) {
        int minutes = (int) (ms / 60000);
        int seconds = (int) ((ms % 60000) / 1000);
        int centis = (int) ((ms % 1000) / 10);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, centis);
    }
}
