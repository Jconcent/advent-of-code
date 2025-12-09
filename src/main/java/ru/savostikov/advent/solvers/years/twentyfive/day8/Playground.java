package ru.savostikov.advent.solvers.years.twentyfive.day8;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.EIGHT)
public class Playground {

    public static class Part1 implements PuzzleSolver {

        private final List<JunctionBox> boxes = new ArrayList<>();

        @Override
        public long solve(String input) {
            List<Set<JunctionBox>> circuits = new ArrayList<>();

            Arrays.stream(input.split("\r\n"))
                    .map(dimensionLine -> {
                        var dimensions = dimensionLine.split(",");
                        JunctionBox box = new JunctionBox(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), Integer.parseInt(dimensions[2]));
                        box.initCircuit(new HashSet<>(Set.of(box)));
                        return box;
                    })
                    .peek(b -> circuits.add(b.circuit))
                    .forEach(boxes::add);

//            for (int i = 0; i < boxes.size(); i++) {
//                for (int j = 0; j < boxes.size(); j++) {
//                    System.out.print(calculateDistance(boxes.get(i), boxes.get(j)));
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }

            List<BoxDistance> distances = new ArrayList<>();

            for (int i = 0; i < boxes.size(); i++) {
                for (int j = i + 1; j < boxes.size(); j++) {
                    distances.add(new BoxDistance(boxes.get(i), boxes.get(j), calculateDistance(boxes.get(i), boxes.get(j))));
                }
            }

            // 0 - 19 - 7 -14
            // 2 - 13 - 8 - 18 - 17
            // 9 - 12
            // 11 - 16
            //

            distances.stream()
                    .sorted(Comparator.comparing(BoxDistance::distance))
                    .limit(1000)
                    .forEach(distance -> {
                        System.out.println(distance.first.id + " " + distance.second.id);
//                        System.out.println("!");
//                        System.out.println(distance.first.x + " " + distance.first.y + " " + distance.first.z);
//                        System.out.println(distance.second.x + " " + distance.second.y + " " + distance.second.z);
//                        System.out.println("!");
//                        if (distance.first.hasCircuit() && distance.second.hasCircuit()) {
                        if (distance.first.circuit != distance.second.circuit) {
                        circuits.remove(distance.second.circuit);
                        }
                            distance.first.getCircuit().addAll(distance.second.getCircuit());
                            distance.second.mergeCircuit(distance.first.getCircuit());
//                        }
                    });

            circuits.stream().sorted(Comparator.comparing(Set::size, Comparator.reverseOrder())).limit(3).forEach(c -> System.out.println(c.size()));
            return 0;
        }

        private double calculateDistance(JunctionBox first, JunctionBox second) {
            return Math.sqrt(Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2) + Math.pow(first.z - second.z, 2));
        }
    }

    public static class Part2 implements PuzzleSolver {

        private final List<JunctionBox> boxes = new ArrayList<>();

        @Override
        public long solve(String input) {
            List<Set<JunctionBox>> circuits = new ArrayList<>();

            Arrays.stream(input.split("\r\n"))
                    .map(dimensionLine -> {
                        var dimensions = dimensionLine.split(",");
                        JunctionBox box = new JunctionBox(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), Integer.parseInt(dimensions[2]));
                        box.initCircuit(new HashSet<>(Set.of(box)));
                        return box;
                    })
                    .peek(b -> circuits.add(b.circuit))
                    .forEach(boxes::add);

//            for (int i = 0; i < boxes.size(); i++) {
//                for (int j = 0; j < boxes.size(); j++) {
//                    System.out.print(calculateDistance(boxes.get(i), boxes.get(j)));
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }

            List<BoxDistance> distances = new ArrayList<>();

            for (int i = 0; i < boxes.size(); i++) {
                for (int j = i + 1; j < boxes.size(); j++) {
                    distances.add(new BoxDistance(boxes.get(i), boxes.get(j), calculateDistance(boxes.get(i), boxes.get(j))));
                }
            }

            // 0 - 19 - 7 -14
            // 2 - 13 - 8 - 18 - 17
            // 9 - 12
            // 11 - 16
            //

            var sortedDistance = distances.stream()
                    .sorted(Comparator.comparing(BoxDistance::distance))
                    .toList();

            for (BoxDistance distance : sortedDistance) {
                if (distance.first.circuit != distance.second.circuit) {
                    circuits.remove(distance.second.circuit);
                }
                if (circuits.size() == 1) {
                    long res = distance.first.x * distance.second.x;
                    System.out.println("RESULT!");
                    System.out.println(res);
                    break;
                }
                distance.first.getCircuit().addAll(distance.second.getCircuit());
                distance.second.mergeCircuit(distance.first.getCircuit());
            }


            return 0;
        }

        private double calculateDistance(JunctionBox first, JunctionBox second) {
            return Math.sqrt(Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2) + Math.pow(first.z - second.z, 2));
        }
    }

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    private static class JunctionBox {
        private final int id = ATOMIC_INTEGER.getAndIncrement();
        private final long x;
        private final long y;
        private final long z;
        private Set<JunctionBox> circuit;

        public JunctionBox(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void initCircuit(Set<JunctionBox> circuit) {
            this.circuit = circuit;
        }

        public void mergeCircuit(Set<JunctionBox> newCircuit) {
            Set<JunctionBox> tmp = this.getCircuit();
            tmp.forEach(box -> box.initCircuit(newCircuit));
//            initCircuit(newCircuit);
        }

        public void addBoxToCircuit(JunctionBox other) {
            this.circuit.add(other);
        }

        public boolean hasCircuit() {
            return this.circuit != null;
        }

        public Set<JunctionBox> getCircuit() {
            return this.circuit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JunctionBox that)) return false;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private record BoxDistance(JunctionBox first, JunctionBox second, double distance) {

    }
}
