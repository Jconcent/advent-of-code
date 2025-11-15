package ru.savostikov.solvers.years.fifteen.day2;

import ru.savostikov.solvers.PuzzleSolver;
import ru.savostikov.solvers.enumerations.Day;
import ru.savostikov.solvers.enumerations.PuzzlePart;
import ru.savostikov.solvers.enumerations.Year;
import ru.savostikov.solvers.util.SolverUtility;

public class Part1 implements PuzzleSolver {

    @Override
    public void solvePuzzle() {
        var input = SolverUtility.readInputAsString(Year.TWO_K_FIFTEEN, Day.TWO, PuzzlePart.PART_1).split("\r\n");

        int startPtr = 0;
        int endPtr = input.length - 1;
        int result = 0;

        while (startPtr <= endPtr) {
            result += calculateSquareFeetOfWrappingPaper(input[startPtr]) + (startPtr == endPtr ? 0 : calculateSquareFeetOfWrappingPaper(input[endPtr]));

            startPtr++;
            endPtr--;
        }

        SolverUtility.printResult(Year.TWO_K_FIFTEEN, Day.TWO, PuzzlePart.PART_1, result);
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
