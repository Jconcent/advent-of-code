package ru.savostikov.advent.solvers.years.fifteen.day2;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

@AdventPuzzle(year = Year.TWO_K_FIFTEEN, day = Day.TWO)
public class IWasToldThereWouldBeNoMath {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputLines = input.split("\r\n");

            int startPtr = 0;
            int endPtr = inputLines.length - 1;
            int result = 0;

            while (startPtr <= endPtr) {
                result += calculateSquareFeetOfWrappingPaper(inputLines[startPtr]) + (startPtr == endPtr ? 0 : calculateSquareFeetOfWrappingPaper(inputLines[endPtr]));

                startPtr++;
                endPtr--;
            }

            return result;
        }

        private int calculateSquareFeetOfWrappingPaper(String dimensions) {
            var dimensionsValue = dimensions.split("x");

            int l = Integer.parseInt(dimensionsValue[0]);
            int w = Integer.parseInt(dimensionsValue[1]);
            int h = Integer.parseInt(dimensionsValue[2]);

            int firstSideSquare = l * w;
            int secondSideSquare = l * h;
            int thirdSideSquare = h * w;
            return calculateSquareForGift(firstSideSquare, secondSideSquare, thirdSideSquare) +
                    calculateSquareOfSmallestSide(firstSideSquare, secondSideSquare, thirdSideSquare);
        }

        private int calculateSquareForGift(int firstSideSquare, int secondSideSquare, int thirdSideSquare) {
            return 2 * firstSideSquare + 2 * secondSideSquare + 2 * thirdSideSquare;
        }

        private int calculateSquareOfSmallestSide(int firstSideSquare, int secondSideSquare, int thirdSideSquare) {
            return Math.min(Math.min(firstSideSquare, secondSideSquare), thirdSideSquare);
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputLines = input.split("\r\n");

            int startPtr = 0;
            int endPtr = inputLines.length - 1;
            int result = 0;

            while (startPtr <= endPtr) {
                result += calculateSquareFeetOfRibbon(inputLines[startPtr]) + (startPtr == endPtr ? 0 : calculateSquareFeetOfRibbon(inputLines[endPtr]));

                startPtr++;
                endPtr--;
            }

            return result;
        }

        private int calculateSquareFeetOfRibbon(String dimensions) {
            var dimensionsValue = dimensions.split("x");

            int l = Integer.parseInt(dimensionsValue[0]);
            int w = Integer.parseInt(dimensionsValue[1]);
            int h = Integer.parseInt(dimensionsValue[2]);

            return calculatePerimeterOfSmallestSide(l, w, h) + calculateFeetOfRibbon(l, w, h);
        }

        private int calculatePerimeterOfSmallestSide(int l, int w, int h) {
            return Math.min(Math.min(2 * l + 2 * w, 2 * l + 2 * h), 2 * w + 2 * h);
        }

        private int calculateFeetOfRibbon(int l, int w, int h) {
            return l * w * h;
        }
    }
}
