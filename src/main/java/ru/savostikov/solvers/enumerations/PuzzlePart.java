package ru.savostikov.solvers.enumerations;

public enum PuzzlePart {

    PART_1(1),
    PART_2(2);

    private final int partValue;

    PuzzlePart(int value) {
        this.partValue = value;
    }

    public int getPartValue() {
        return partValue;
    }
}
