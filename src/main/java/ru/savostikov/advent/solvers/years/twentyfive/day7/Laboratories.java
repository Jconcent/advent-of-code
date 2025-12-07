package ru.savostikov.advent.solvers.years.twentyfive.day7;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.SEVEN)
public class Laboratories {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var lines = input.split("\r\n");

            char[][] map = new char[lines.length][lines[0].length()];

            for (int i = 0; i < lines.length; i++) {
                map[i] = lines[i].toCharArray();
            }

            int startX = 0;
            for (int i = 0; i < map[0].length; i++) {
                if (map[0][i] == 'S') {
                    startX = i;
                }
            }

            return countTachyonBeams(map, startX, 0);
        }

        private int countTachyonBeams(char[][] schema, int startX, int startY) {
            if (startX < 0 || startX == schema[0].length) {
                return 0;
            }
            for (int y = startY; y < schema.length; y++) {
                if (schema[y][startX] == '|') {
                    return 0;
                }
                if (schema[y][startX] == '^') {
                    return 1 + countTachyonBeams(schema, startX - 1, y) + countTachyonBeams(schema, startX + 1, y);
                }
                schema[y][startX] = '|';
            }
            return 0;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var lines = input.split("\r\n");

            char[][] map = new char[lines.length][lines[0].length()];
            long[][] grid = new long[lines.length][lines[0].length()];

            for (int i = 0; i < lines.length; i++) {
                map[i] = lines[i].toCharArray();
            }

            for (int i = 0; i < map[0].length; i++) {
                if (map[0][i] == 'S') {
                    grid[1][i] = 1;
                    break;
                }
            }

            return countTachyonBeams(map, grid);
        }

        private long countTachyonBeams(char[][] schema, long[][] grid) {
            for (int y = 1; y < schema.length; y++) {
                for (int x = 0; x < schema[y].length; x++) {
                    if (schema[y][x] == '.') {
                        grid[y][x] += grid[y - 1][x];
                    }
                    if (schema[y][x] == '^') {
                        grid[y + 1][x - 1] += grid[y - 1][x];
                        grid[y + 1][x + 1] += grid[y - 1][x];
                    }
                }
            }

            long result = 0;

            for (int i = 0; i< grid[grid.length - 1].length; i++) {
                result += grid[grid.length - 1][i];
            }

            return result;
        }
    }
}
