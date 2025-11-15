package ru.savostikov;

import ru.savostikov.solvers.years.fifteen.day1.Part1;
import ru.savostikov.solvers.years.fifteen.day1.Part2;

public class AdventOfCodeApp {

    public static void main(String[] args) {
        var day1Part1 = new Part1();
        var day1Part2 = new Part2();
        var day2Part1 = new ru.savostikov.solvers.years.fifteen.day2.Part1();

        day1Part1.solvePuzzle();
        day1Part2.solvePuzzle();
        day2Part1.solvePuzzle();
    }
}
