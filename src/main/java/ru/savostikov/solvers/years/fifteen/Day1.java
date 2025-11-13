package ru.savostikov.solvers.years.fifteen;

import ru.savostikov.solvers.PuzzleSolver;
import ru.savostikov.solvers.util.SolverUtility;

public class Day1 implements PuzzleSolver {

    private static final int YEAR = 2015;
    private static final int DAY = 1;
    private static final int PART = 1;

    @Override
    public void solvePuzzle() {
        var input = SolverUtility.readInputAsString(YEAR, DAY, PART).toCharArray();

        int startPtr = 0;
        int endPtr = input.length - 1;
        int result = 0;

        while (startPtr <= endPtr) {
            result += getNumberByParenthesis(input[startPtr]) + (startPtr == endPtr ? 0 : getNumberByParenthesis(input[endPtr]));
            startPtr++;
            endPtr--;
        }

        SolverUtility.printResult(YEAR, DAY, PART, result);
    }

    private int getNumberByParenthesis(char parenthesis) {
        if (parenthesis == '(') {
            return 1;
        } else {
            return -1;
        }
    }
}
