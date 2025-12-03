package ru.savostikov.advent.solvers.years.twentyfive.day3;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Arrays;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.THREE)
public class Lobby {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var blocks = input.split("\r\n");

            return Arrays.stream(blocks)
                    .mapToInt(this::calculateVoltageInBlock)
                    .sum();
        }

        private int calculateVoltageInBlock(String block) {
            var batteries = block.toCharArray();

            int first = 0;
            int second = 0;

            for (int i = 0; i < batteries.length; i++) {
                if ((batteries[i] - '0') > first && i + 1 != batteries.length) {
                    first = batteries[i] - '0';
                    second = 0;
                } else if ((batteries[i] - '0') > second) {
                    second = batteries[i] - '0';
                }
            }

            return first * 10 + second;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var blocks = input.split("\r\n");

            return Arrays.stream(blocks)
                    .mapToLong(this::calculateVoltageInBlock)
                    .sum();
        }

        private long calculateVoltageInBlock(String block) {
            var batteries = block.toCharArray();

            int skipCount = batteries.length - 12;
            int foundedBatteryIdx = 0;
            int currentBattery = 0;

            long result = 0;

            for (int i = 0; i < 12; i++) {
                int lastFoundedBatteryIdx = foundedBatteryIdx;
                for (int j = foundedBatteryIdx; j < batteries.length; j++) {
                    if ((batteries[j] - '0') > currentBattery) {
                        if (j - lastFoundedBatteryIdx <= skipCount) {
                            foundedBatteryIdx = j;
                            currentBattery = batteries[j] - '0';
                        }
                    }
                }
                skipCount -= foundedBatteryIdx - lastFoundedBatteryIdx;
                result = result * 10 + currentBattery;
                currentBattery = 0;
                foundedBatteryIdx++;
            }

            return result;
        }
    }
}
