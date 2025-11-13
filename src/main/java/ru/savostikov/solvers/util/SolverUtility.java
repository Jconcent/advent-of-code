package ru.savostikov.solvers.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class SolverUtility {

    private static final String PATH_TO_INPUT_TEMPLATE = "puzzle-input/%d/day%d/part%d/input.txt";
    private static final String PRINTED_BOUNDARIES = "|----------------|----------------|";

    public static String readInputAsString(int year, int day, int part) {
        try (InputStream is = SolverUtility.class.getClassLoader().getResourceAsStream(PATH_TO_INPUT_TEMPLATE.formatted(year, day, part))) {
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

    public static void printResult(int year, int day, int part, int result) {
        System.out.println(PRINTED_BOUNDARIES);
        System.out.printf("|%16s|%16s|\n", "%d-DAY%d-PART%d".formatted(year, day, part), result);
        System.out.println(PRINTED_BOUNDARIES);
    }
}
