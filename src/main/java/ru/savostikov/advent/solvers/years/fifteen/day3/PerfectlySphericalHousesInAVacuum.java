package ru.savostikov.advent.solvers.years.fifteen.day3;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.HashSet;

@AdventPuzzle(year = Year.TWO_K_FIFTEEN, day = Day.THREE)
public class PerfectlySphericalHousesInAVacuum {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputArray = input.toCharArray();

            var santaDirection = new SantaDirection(0, 0);
            var deliveredHouse = new HashSet<SantaDirection>();
            deliveredHouse.add(santaDirection);

            for (int i = 0; i < inputArray.length; i++) {
                santaDirection = checkNextDirection(santaDirection, inputArray[i]);
                deliveredHouse.add(santaDirection);
            }

            return deliveredHouse.size();
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            var inputArray = input.toCharArray();

            var santaDirection = new SantaDirection(0, 0);
            var roboSantaDirection = new SantaDirection(0, 0);

            var deliveredHouse = new HashSet<SantaDirection>();
            deliveredHouse.add(santaDirection);

            for (int i = 0; i < inputArray.length - 1; i += 2) {
                santaDirection = checkNextDirection(santaDirection, inputArray[i]);
                if (i + 1 < inputArray.length) {
                    roboSantaDirection = checkNextDirection(roboSantaDirection, inputArray[i + 1]);
                    deliveredHouse.add(roboSantaDirection);
                }

                deliveredHouse.add(santaDirection);
            }

            return deliveredHouse.size();
        }
    }

    private static SantaDirection checkNextDirection(SantaDirection current, char directionMove) {
        if (directionMove == '^') {
            return current.up();
        } else if (directionMove == 'v') {
            return current.down();
        } else if (directionMove == '>') {
            return current.right();
        } else {
            return current.left();
        }
    }

    private record SantaDirection(int x, int y) {
        public SantaDirection up() {
            return new SantaDirection(x, y + 1);
        }

        public SantaDirection down() {
            return new SantaDirection(x, y - 1);
        }

        public SantaDirection left() {
            return new SantaDirection(x - 1, y);
        }

        public SantaDirection right() {
            return new SantaDirection(x + 1, y);
        }
    }
}
