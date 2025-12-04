package ru.savostikov.advent.solvers.years.twentyfive.day4;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Arrays;
import java.util.List;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.FOUR)
public class PrintingDepartment {

    private static final List<Integer> X_SHIFT = List.of(1, 0, -1, 0);
    private static final List<Integer> Y_SHIFT = List.of(0, 1, 0, -1);

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var map = prepareInputMap(input);

            int availableCount = 0;

            for (int i = 0; i < map.length; i++) {
                availableCount += findAvailableRollOfPaper(i, map);
            }

            return availableCount;
        }

        private int findAvailableRollOfPaper(int line, char[][] map) {
            int availableCount = 0;

            for (int i = 0; i < map[line].length; i++) {
                if (map[line][i] == '@' && checkAdjacentPositions(i, line, map)) {
                    availableCount++;
                }
            }

            return availableCount;
        }
    }

    public static class Part2 implements PuzzleSolver {

        private char[][] mapCopy;

        @Override
        public long solve(String input) {
            var map = prepareInputMap(input);

            mapCopy = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);

            int result = -1;
            int accumulate = 0;

            while (result != 0) {
                map = Arrays.stream(mapCopy).map(char[]::clone).toArray(char[][]::new);
                result = findMore(map);
                accumulate += result;
            }

            return accumulate;
        }

        private int findMore(char[][] map) {
            int availableCount = 0;

            for (int i = 0; i < map.length; i++) {
                availableCount += findAvailableRollOfPaper(i, map);
            }

            return availableCount;
        }

        private int findAvailableRollOfPaper(int line, char[][] map) {
            int availableCount = 0;

            for (int i = 0; i < map[line].length; i++) {
                if (map[line][i] == '@' && checkAdjacentPositions(i, line, map)) {
                    mapCopy[line][i] = '.';
                    availableCount++;
                }
            }

            return availableCount;
        }
    }

    private static boolean checkAdjacentPositions(int x, int y, char[][] map) {
        int index = -1;
        int xMove = 0;
        int yMove = 0;

        x = x - 1;
        y = y - 1;

        int paperRollCount = 0;

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                index++;
                xMove = X_SHIFT.get(index);
                yMove = Y_SHIFT.get(index);
            }
            if (x < 0 || y < 0 || x >= map[0].length || y >= map.length) {
                x += xMove;
                y += yMove;
                continue;
            }
            if (map[y][x] == '@') {
                paperRollCount++;
                if (paperRollCount > 3) {
                    return false;
                }
            }
            x += xMove;
            y += yMove;
        }

        return true;
    }

    private static char[][] prepareInputMap(String input) {
        var lines = input.split("\r\n");

        char[][] map = new char[lines.length][lines[0].length()];

        for (int i = 0; i < lines.length; i++) {
            map[i] = lines[i].toCharArray();
        }

        return map;
    }
}
