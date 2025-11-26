package ru.savostikov.advent.enumerations;

public enum Year {
    TWO_K_FIFTEEN(2015);

    private final int yearValue;

    Year(int value) {
        this.yearValue = value;
    }

    public int getYearValue() {
        return yearValue;
    }
}
