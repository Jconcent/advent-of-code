package ru.savostikov.solvers.years.fifteen.day1;

import ru.savostikov.solvers.PuzzleSolver;
import ru.savostikov.solvers.enumerations.Day;
import ru.savostikov.solvers.enumerations.PuzzlePart;
import ru.savostikov.solvers.enumerations.Year;
import ru.savostikov.solvers.util.SolverUtility;

import java.util.Map;

public class Part2 implements PuzzleSolver {

    private static final Map<Character, Integer> NUMBER_BY_PARENTHESIS = Map.of(
            '(', 1,
            ')', -1
    );

    @Override
    public void solvePuzzle() {
        var input = SolverUtility.readInputAsString(Year.TWO_K_FIFTEEN, Day.ONE, PuzzlePart.PART_2).toCharArray();

        int startPtr = 0;
        int result = 0;

        while (startPtr < input.length) {
            result += NUMBER_BY_PARENTHESIS.get(input[startPtr]);
            if (result == -1) {
                break;
            }
            startPtr++;
        }

        SolverUtility.printResult(Year.TWO_K_FIFTEEN, Day.ONE, PuzzlePart.PART_2, startPtr + 1);
    }
}
