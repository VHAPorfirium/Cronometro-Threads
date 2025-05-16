package com.example.cronometro_threads.model;

public class Lap {
    private final int number;
    private final String formattedTime;

    public Lap(int number, String formattedTime) {
        this.number = number;
        this.formattedTime = formattedTime;
    }

    public int getNumber() {
        return number;
    }

    public String getFormattedTime() {
        return formattedTime;
    }
}
