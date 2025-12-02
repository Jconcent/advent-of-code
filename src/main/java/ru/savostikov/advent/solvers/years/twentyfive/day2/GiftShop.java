package ru.savostikov.advent.solvers.years.twentyfive.day2;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Arrays;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.TWO)
public class GiftShop {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var idRanges = input.split(",");

            return Arrays.stream(idRanges)
                    .mapToLong(this::findInvalidIdInRange)
                    .sum();
        }

        private long findInvalidIdInRange(String range) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);

            long result = 0;

            while (start <= end) {
                String id = String.valueOf(start);

                if (id.length() % 2 != 0) {
                    start++;
                    continue;
                }
                if (!checkId(id)) {
                    result += start;
                }
                start++;
            }
            return result;
        }

        private boolean checkId(String id) {
            int firstPtr = 0;
            int secondPtr = id.length() / 2;

            while (firstPtr < id.length() / 2) {
                if (id.charAt(firstPtr) != id.charAt(secondPtr)) {
                    return true;
                }
                firstPtr++;
                secondPtr++;
            }
            return false;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var idRanges = input.split(",");

            return Arrays.stream(idRanges)
                    .mapToLong(this::findInvalidIdInRange)
                    .sum();
        }

        private long findInvalidIdInRange(String range) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);

            long result = 0;

            while (start <= end) {
                String id = String.valueOf(start);

                if (checkInvalidId(id)) {
                    result += start;
                }
                start++;
            }
            return result;
        }

        private boolean checkInvalidId(String id) {
            String checkStr = id + id;

            return checkStr.indexOf(id, 1) != id.length();
        }
    }
}
