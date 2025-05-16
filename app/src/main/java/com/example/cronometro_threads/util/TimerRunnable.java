package com.example.cronometro_threads.util;

public class TimerRunnable implements Runnable {
    private final ChronometerManager manager;

    public TimerRunnable(ChronometerManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.update();
    }
}
