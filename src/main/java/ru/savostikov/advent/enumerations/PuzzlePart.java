package ru.savostikov.advent.enumerations;

import java.util.Arrays;
import java.util.Objects;

public enum PuzzlePart {

    PART_1(1, "Part1"),
    PART_2(2, "Part2");

    private final int partValue;
    private final String partName;

    PuzzlePart(int value, String partName) {
        this.partValue = value;
        this.partName = partName;
    }

    public int getPartValue() {
        return partValue;
    }

    public String getPartName() {
        return partName;
    }

    public static PuzzlePart valueOfByPartName(String partVariant) {
        return Arrays.stream(PuzzlePart.values())
                .filter(part -> Objects.equals(part.getPartName(), partVariant))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не допустимое имя раздела пазла"));
    }
}
