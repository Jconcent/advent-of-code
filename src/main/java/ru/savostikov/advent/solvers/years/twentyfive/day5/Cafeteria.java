package ru.savostikov.advent.solvers.years.twentyfive.day5;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.FIVE)
public class Cafeteria {

    public static class Part1 implements PuzzleSolver {

        private final List<Range> AVAILABLE_IDS = new ArrayList<>();

        @Override
        public long solve(String input) {
            Arrays.stream(input.split("\r\n"))
                    .takeWhile(str -> str.contains("-"))
                    .forEach(this::collectIdsInRange);

            return Arrays.stream(input.split("\r\n"))
                    .filter(str -> !str.isBlank() && !str.contains("-"))
                    .map(Long::parseLong)
                    .filter(id -> AVAILABLE_IDS.stream().anyMatch(range -> range.isInRange(id)))
                    .count();
        }

        private void collectIdsInRange(String range) {
            var rangeIds = range.split("-");
            long start = Long.parseLong(rangeIds[0]);
            long end = Long.parseLong(rangeIds[1]);

            AVAILABLE_IDS.add(new Range(start, end));
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var availableIdRanges = Arrays.stream(input.split("\r\n"))
                    .takeWhile(str -> str.contains("-"))
                    .map(this::collectIdsInRange)
                    .sorted(Comparator.comparing(Range::start))
                    .collect(Collectors.toList());

            List<Range> overlapped = resolveOverlap(availableIdRanges);

            while (overlapped.size() != availableIdRanges.size()) {
                availableIdRanges = overlapped;
                overlapped = resolveOverlap(availableIdRanges);
            }

            return overlapped.stream().mapToLong(Range::countIdsInRange).sum();
        }

        private Range collectIdsInRange(String range) {
            var rangeIds = range.split("-");
            long start = Long.parseLong(rangeIds[0]);
            long end = Long.parseLong(rangeIds[1]);

            return new Range(start, end);
        }

        private List<Range> resolveOverlap(List<Range> ranges) {
            List<Range> overlapped = new ArrayList<>();

            for (int i = 0; i < ranges.size(); i++) {
                if (i + 1 < ranges.size() && ranges.get(i).isOverlap(ranges.get(i + 1))) {
                    overlapped.add(ranges.get(i).createOverlapped(ranges.get(i + 1)));
                    i++;
                } else {
                    overlapped.add(ranges.get(i));
                }
            }
            return overlapped;
        }
    }

    private record Range(long start, long end) {
        public boolean isInRange(long value) {
            return value >= start && value <= end;
        }

        public boolean isOverlap(Range range) {
            return range.start() >= this.start && range.start() <= this.end;
        }

        public Range createOverlapped(Range range) {
            return new Range(this.start, Math.max(range.end, this.end));
        }

        public long countIdsInRange() {
            return (end - start) + 1;
        }
    }
}
