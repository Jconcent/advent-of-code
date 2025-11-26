package ru.savostikov.advent.solvers.years.fifteen.day1;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Map;

@AdventPuzzle(year = Year.TWO_K_FIFTEEN, day = Day.ONE)
public class NotQuietList {

    private static final Map<Character, Integer> NUMBER_BY_PARENTHESIS = Map.of(
            '(', 1,
            ')', -1
    );

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputArray = input.toCharArray();

            int startPtr = 0;
            int endPtr = inputArray.length - 1;
            int result = 0;

            while (startPtr <= endPtr) {
                result += NUMBER_BY_PARENTHESIS.get(inputArray[startPtr]) + (startPtr == endPtr ? 0 : NUMBER_BY_PARENTHESIS.get(inputArray[endPtr]));
                startPtr++;
                endPtr--;
            }

            return result;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputArray = input.toCharArray();

            int startPtr = 0;
            int result = 0;

            while (startPtr < inputArray.length) {
                result += NUMBER_BY_PARENTHESIS.get(inputArray[startPtr]);
                if (result == -1) {
                    break;
                }
                startPtr++;
            }

            return startPtr + 1;
        }
    }
}
