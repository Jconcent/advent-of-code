package ru.savostikov.advent.solvers.years.twentyfive.day1;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.ONE)
public class SecretEntrance {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var rotationInstructions = input.split("\r\n");

            int currentPos = 50;
            int zeroPosCount = 0;

            for (String instruction : rotationInstructions) {
                currentPos += getNextRotation(instruction);
                if (currentPos % 100 == 0) {
                    zeroPosCount++;
                }
            }
            return zeroPosCount;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var rotationInstructions = input.split("\r\n");

            int currentPos = 50;
            int zeroPosCount = 0;

            for (String instruction : rotationInstructions) {
                int before = currentPos;
                currentPos += getNextRotation(instruction);
                int after = currentPos;

                if (after < before) {
                    before = -before;
                    after = -after;
                }

                zeroPosCount += Math.abs(Math.floorDiv(after, 100) - Math.floorDiv(before, 100));
            }
            return zeroPosCount;
        }
    }

    private static int getNextRotation(String instruction) {
        var count = Integer.parseInt(instruction.substring(1));
        return instruction.charAt(0) == 'R' ? count : (count * -1);
    }
}
