package ru.savostikov.advent.enumerations;

public enum Day {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int dayValue;

    Day(int value) {
        this.dayValue = value;
    }

    public int getDayValue() {
        return dayValue;
    }
}
