package ru.savostikov.advent.solvers.years.twentyfive.day6;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.SIX)
public class TrashCompactor {

    public static class Part1 implements PuzzleSolver {

        private final List<MathProblem> problems = new ArrayList<>();

        @Override
        public long solve(String input) {
            String[] lines = input.split("\r\n");

            initProblems(lines);

            for (int i = 0; i < lines.length - 1; i++) {
                solveProblems(lines[i]);
            }

            return problems.stream()
                    .mapToLong(MathProblem::getResult)
                    .sum();
        }

        private void initProblems(String[] lines) {
            String[] operations = Arrays.stream(lines[lines.length - 1].split("\\s+"))
                    .filter(not(String::isBlank))
                    .map(String::trim)
                    .toArray(String[]::new);

            for (int i = 0; i < operations.length; i++) {
                problems.add(i, new MathProblem(operations[i].charAt(0)));
            }
        }

        private void solveProblems(String problemLine) {
            long[] numbers = Arrays.stream(problemLine.split("\\s+"))
                    .filter(not(String::isBlank))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .toArray();

            for (int i = 0; i < numbers.length; i++) {
                problems.get(i).makeOperation(numbers[i]);
            }
        }

        private static class MathProblem {
            private long result;
            private final char operation;

            public MathProblem(char operation) {
                this.operation = operation;
                if (operation == '*') {
                    this.result = 1;
                } else {
                    this.result = 0;
                }
            }

            public void makeOperation(long number) {
                if (this.operation == '*') {
                    this.result *= number;
                } else {
                    this.result += number;
                }
            }

            public long getResult() {
                return result;
            }
        }
    }

    public static class Part2 implements PuzzleSolver {

        private final List<MathProblem> problems = new ArrayList<>();
        private final List<Integer> separatingIndexes = new ArrayList<>();

        @Override
        public long solve(String input) {
            String[] lines = input.split("\r\n");

            initProblems(lines);
            findAllSeparatingIndexes(input);

            for (int i = 0; i < lines.length - 1; i++) {
                initNumbers(lines[i]);
            }

            return problems.stream()
                    .mapToLong(MathProblem::calculateResult)
                    .sum();
        }

        private void initProblems(String[] lines) {
            String[] operations = Arrays.stream(lines[lines.length - 1].split("\\s+"))
                    .filter(not(String::isBlank))
                    .map(String::trim)
                    .toArray(String[]::new);

            for (int i = 0; i < operations.length; i++) {
                problems.add(i, new MathProblem(operations[i].charAt(0)));
            }
        }

        private void initNumbers(String problemLine) {
            char[] lineSymbols = problemLine.toCharArray();

            int problemIndex = 0;
            int digitIndex = 0;

            for (int i = 0; i < lineSymbols.length; i++) {
                if (i == separatingIndexes.get(problemIndex)) {
                    problemIndex++;
                    digitIndex = 0;
                } else {
                    if (lineSymbols[i] == ' ') {
                        digitIndex++;
                        continue;
                    }
                    problems.get(problemIndex).initNumbers(digitIndex, lineSymbols[i] - '0');
                    digitIndex++;
                }
            }

        }

        private void findAllSeparatingIndexes(String input) {
            var lines = input.split("\r\n");

            char[][] map = new char[lines.length][lines[0].length()];

            for (int i = 0; i < lines.length; i++) {
                map[i] = lines[i].toCharArray();
            }

            boolean allWhiteSpace = true;

            for (int i = 0; i < map[0].length; i++) {
                if (map[0][i] == ' ') {
                    allWhiteSpace = true;
                    for (char[] chars : map) {
                        if (chars[i] != ' ') {
                            allWhiteSpace = false;
                            break;
                        }
                    }
                    if (allWhiteSpace) {
                        separatingIndexes.add(i);
                    }
                }
            }

            separatingIndexes.add(map[0].length + 2);
        }

        private static class MathProblem {
            private final List<Long> numbers = new ArrayList<>();
            private char operation;

            public MathProblem(char operation) {
                this.operation = operation;
                for (int i = 0; i < 10; i++) {
                    numbers.add(0L);
                }
            }

            public void initNumbers(int index, int digit) {
                long number = numbers.get(index);
                number = number * 10 + digit;
                numbers.set(index, number);
            }

            public long calculateResult() {
                return numbers.stream()
                        .filter(number -> number != 0)
                        .reduce((one, two) -> operation == '*' ? one * two : one + two)
                        .orElse(0L);
            }
        }
    }
}
