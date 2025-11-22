package ru.savostikov.solvers.years.fifteen.day3;

import ru.savostikov.solvers.PuzzleSolver;
import ru.savostikov.solvers.enumerations.Day;
import ru.savostikov.solvers.enumerations.PuzzlePart;
import ru.savostikov.solvers.enumerations.Year;
import ru.savostikov.solvers.util.SolverUtility;

import java.util.HashSet;

public class Part2 implements PuzzleSolver {

    @Override
    public void solvePuzzle() {
        var input = SolverUtility.readInputAsString(Year.TWO_K_FIFTEEN, Day.THREE, PuzzlePart.PART_2).toCharArray();

        var santaDirection = new SantaDirection(0, 0);
        var roboSantaDirection = new SantaDirection(0, 0);

        var deliveredHouse = new HashSet<SantaDirection>();
        deliveredHouse.add(santaDirection);

        for (int i = 0; i < input.length - 1; i += 2) {
            santaDirection = checkNextDirection(santaDirection, input[i]);
            if (i + 1 < input.length) {
                roboSantaDirection = checkNextDirection(roboSantaDirection, input[i + 1]);
                deliveredHouse.add(roboSantaDirection);
            }

            deliveredHouse.add(santaDirection);
        }

        SolverUtility.printResult(Year.TWO_K_FIFTEEN, Day.THREE, PuzzlePart.PART_2, deliveredHouse.size());
    }

    private SantaDirection checkNextDirection(SantaDirection current, char directionMove) {
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
