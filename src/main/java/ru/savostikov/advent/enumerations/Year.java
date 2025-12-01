package ru.savostikov.advent.enumerations;

public enum Year {
    TWO_K_FIFTEEN(2015),
    TWO_K_TWENTY_FIVE(2025);

    private final int yearValue;

    Year(int value) {
        this.yearValue = value;
    }

    public int getYearValue() {
        return yearValue;
    }
}
