package com.example.cronometro_threads.util;

// Runnable que aciona a atualização do cronômetro
public class TimerRunnable implements Runnable {
    private final ChronometerManager manager; // referência ao gerenciador

    // Recebe gerenciador para chamar o metodo update()
    public TimerRunnable(ChronometerManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.update(); // chama update a cada execução
    }
}