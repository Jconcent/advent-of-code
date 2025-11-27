package ru.savostikov.advent;

import ru.savostikov.advent.enumerations.PuzzlePart;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;
import ru.savostikov.advent.solvers.util.SolverUtility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class AdventContext {

    private static final boolean CLASS = true;
    private static final boolean PACKAGE = false;

    public static void run() {
        var solvers = findAllSolvers();
        solvers.forEach(AdventContext::invokeSolver);
    }

    private static List<Class<PuzzleSolver>> findAllSolvers() {
        return findOnlyPuzzleSolvers(findClassesInPackage(AdventContext.class.getPackageName()));
    }

    private static void invokeSolver(Class<PuzzleSolver> puzzleSolverClass) {
        var solver = createNewPuzzleSolverInstance(puzzleSolverClass);

        var puzzleMetadata = puzzleSolverClass.isMemberClass() ?
                puzzleSolverClass.getEnclosingClass().getAnnotation(AdventPuzzle.class) :
                puzzleSolverClass.getAnnotation(AdventPuzzle.class);

        if (Objects.nonNull(puzzleMetadata)) {
            if (puzzleMetadata.skip()) {
                System.out.println("Пропускаем класс " + puzzleSolverClass.getCanonicalName());
            } else {
                PuzzlePart part = PuzzlePart.valueOfByPartName(puzzleSolverClass.getSimpleName());
                SolverUtility.printResult(
                        puzzleMetadata.year(),
                        puzzleMetadata.day(),
                        part,
                        solver.solve(SolverUtility.readInputAsString(puzzleMetadata.year(), puzzleMetadata.day(), part))
                );
            }
        } else {
            System.out.printf("Для класса %s нет мета-информации. Игнорируем", puzzleSolverClass.getCanonicalName());
        }
    }

    private static PuzzleSolver createNewPuzzleSolverInstance(Class<PuzzleSolver> puzzleSolverClass) {
        Constructor<?> constructor = Arrays.stream(puzzleSolverClass.getDeclaredConstructors())
                .findFirst()
                .orElseThrow();
        try {
            return (PuzzleSolver) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.printf("При попытке создать экземпляр класса %s произошла ошибка %s", puzzleSolverClass.getCanonicalName(), e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    private static List<Class<PuzzleSolver>> findOnlyPuzzleSolvers(List<Class<?>> classes) {
        return classes.stream()
                .filter(AdventContext::isClassImplementPuzzleSolver)
                .map(targetClass -> (Class<PuzzleSolver>)targetClass)
                .toList();
    }

    private static boolean isClassImplementPuzzleSolver(Class<?> targetClass) {
        return Arrays.stream(targetClass.getInterfaces())
                .anyMatch(implementedInterface -> implementedInterface.isAssignableFrom(PuzzleSolver.class));
    }

    private static List<Class<?>> findClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try (
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            var classAndPackage = bufferedReader.lines()
                    .collect(Collectors.groupingBy(line -> line.contains(".class")));
            classAndPackage.getOrDefault(CLASS, Collections.emptyList()).stream()
                    .map(currentClass -> currentClass.replaceAll(".class", ""))
                    .map(currentClass -> packageName + "." + currentClass)
                    .map(currentClass -> {
                        try {
                            return Class.forName(currentClass);
                        } catch (ClassNotFoundException e) {
                            System.err.println(currentClass + " not found");
                            System.exit(-1);
                            return null;
                        }
                    })
                    .forEach(classes::add);
            classAndPackage.getOrDefault(PACKAGE, Collections.emptyList()).stream()
                    .map(nextPackage -> packageName + "." + nextPackage)
                    .map(AdventContext::findClassesInPackage)
                    .flatMap(Collection::stream)
                    .forEach(classes::add);
        } catch (Exception e) {
            System.err.printf("При поиске классов-решений была получена ошибка: %s", e.getMessage());
            System.exit(-1);
        }
        return classes;
    }
}
