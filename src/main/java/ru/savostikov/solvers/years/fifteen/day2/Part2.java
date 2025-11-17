package ru.savostikov.solvers.years.fifteen.day2;

import ru.savostikov.solvers.PuzzleSolver;
import ru.savostikov.solvers.enumerations.Day;
import ru.savostikov.solvers.enumerations.PuzzlePart;
import ru.savostikov.solvers.enumerations.Year;
import ru.savostikov.solvers.util.SolverUtility;

public class Part2 implements PuzzleSolver {

    @Override
    public void solvePuzzle() {
        var input = SolverUtility.readInputAsString(Year.TWO_K_FIFTEEN, Day.TWO, PuzzlePart.PART_2).split("\r\n");

        int startPtr = 0;
        int endPtr = input.length - 1;
        int result = 0;

        while (startPtr <= endPtr) {
            result += calculateSquareFeetOfWrappingPaper(input[startPtr]) + (startPtr == endPtr ? 0 : calculateSquareFeetOfWrappingPaper(input[endPtr]));

            startPtr++;
            endPtr--;
        }

        SolverUtility.printResult(Year.TWO_K_FIFTEEN, Day.TWO, PuzzlePart.PART_2, result);
    }

    private int calculateSquareFeetOfWrappingPaper(String dimensions) {
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
