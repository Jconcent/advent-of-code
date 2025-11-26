package ru.savostikov.advent.solvers.util;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.PuzzlePart;
import ru.savostikov.advent.enumerations.Year;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class SolverUtility {

    private static final String PATH_TO_INPUT_TEMPLATE = "puzzle-input/%d/day%d/part%d/input.txt";
    private static final String PRINTED_BOUNDARIES = "|----------------|----------------|";

    public static String readInputAsString(Year year, Day day, PuzzlePart part) {
        try (InputStream is = SolverUtility.class.getClassLoader().getResourceAsStream(PATH_TO_INPUT_TEMPLATE.formatted(year.getYearValue(), day.getDayValue(), part.getPartValue()))) {
            if (Objects.isNull(is)) {
                return "";
            }
            return new String(is.readAllBytes());
        } catch (IOException e) {
            System.err.printf("Ошибка при попытке чтения входящих данных для %s %s %s %s", year, day, part, e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public static void printResult(Year year, Day day, PuzzlePart part, long result) {
        System.out.println(PRINTED_BOUNDARIES);
        System.out.printf("|%16s|%16s|\n", "%d-DAY%d-PART%d".formatted(year.getYearValue(), day.getDayValue(), part.getPartValue()), result);
        System.out.println(PRINTED_BOUNDARIES);
    }
}
