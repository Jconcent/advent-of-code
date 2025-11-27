package ru.savostikov.advent.solvers.years.fifteen.day5;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Set;

@AdventPuzzle(year = Year.TWO_K_FIFTEEN, day = Day.FIVE)
public class DoesntHeHaveInternElvesForThis {

    private static final Set<String> DISALLOWED_STR = Set.of("ab", "cd", "pq", "xy");
    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputLines = input.split("\r\n");

            int niceStringCount = 0;

            for (String targetLine : inputLines) {
                if (isStringNice(targetLine.toCharArray())) {
                    niceStringCount++;
                } else {
                    System.out.println(targetLine);
                }
            }
            return niceStringCount;
        }

        private boolean isStringNice(char[] target) {
            if (target.length < 2) {
                return false;
            }

            int start = 0;
            int end = target.length - 1;
            int vowelsCount = 0;
            boolean hasTwiceLetter = false;

            while (start < end) {
                if (isDisallowed(String.valueOf(target, start, 2)) ||
                        (end != target.length - 1 && isDisallowed(String.valueOf(target, end, 2)))) {
                    return false;
                }
                if (isVowels(target[start])) {
                    vowelsCount++;
                }
                if (isVowels(target[end])) {
                    vowelsCount++;
                }
                if (target[start] == target[start + 1] || (end != target.length - 1 && target[end] == target[end + 1])) {
                    hasTwiceLetter = true;
                }
                start++;
                end--;
            }

            return vowelsCount >= 3 && hasTwiceLetter;
        }

        private boolean isVowels(char target) {
            return VOWELS.contains(target);
        }

        private boolean isDisallowed(String target) {
            return DISALLOWED_STR.contains(target);
        }
    }
}
