package ru.savostikov.solvers.enumerations;

public enum Day {
    ONE(1),
    TWO(2),
    THREE(3);

    private final int dayValue;

    Day(int value) {
        this.dayValue = value;
    }

    public int getDayValue() {
        return dayValue;
    }
}
