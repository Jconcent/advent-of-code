package ru.savostikov.advent.solvers.years.twentyfive.day9;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.Arrays;
import java.util.Comparator;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.NINE)
public class MovieTheater {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var coordinates = Arrays.stream(input.split("\r\n"))
                    .map(line -> {
                        var coordinate = line.split(",");
                        return new Coordinate(Long.parseLong(coordinate[0]), Long.parseLong(coordinate[1]));
                    })
//                    .sorted(Comparator.comparing(Coordinate::x, Comparator.reverseOrder()).thenComparing(Coordinate::y, Comparator.naturalOrder()))
                    .toList();

            long result = 0;

            for (int i = 0; i < coordinates.size(); i++) {
                for (int j = i + 1; j < coordinates.size(); j++) {
                    long tmp = calculateSquare(coordinates.get(i), coordinates.get(j));
                    if (result < tmp) {
                        result = tmp;
                    }
                }
            }

//            var first = coordinates.getFirst();
//            var second = coordinates.getLast();
//
//            System.out.println(first);
//            System.out.println(second);

            return result;
        }

        private long calculateSquare(Coordinate first, Coordinate second) {
            return (Math.abs(first.x - second.x) + 1) * (Math.abs(second.y - first.y) + 1);
        }
    }

    private record Coordinate(long x, long y) {}
}
