package ru.savostikov.advent.solvers.years.twentyfive.day10;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.*;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.TEN, skip = true)
public class Factory {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            return Arrays.stream(input.split("\r\n"))
                    .map(this::prepareMachineInfo)
                    .mapToInt(this::calculateMinimumPush)
                    .sum();
        }

        private int calculateMinimumPush(Machine machine) {
            Map<Integer, Integer> initialState = prepareInitialMachineState(machine.startMachineIndicator.size());
            Set<Map<Integer, Integer>> alreadySeenState = new HashSet<>();
            Queue<MachineState> stateOnCheck = new PriorityQueue<>(Comparator.comparing(MachineState::buttonClicks));

            alreadySeenState.add(initialState);
            stateOnCheck.add(new MachineState(initialState, 0));
            while (!stateOnCheck.isEmpty()) {
                MachineState currentState = stateOnCheck.poll();
                alreadySeenState.add(currentState.state);
                if (currentState.state.equals(machine.startMachineIndicator)) {
                    return currentState.buttonClicks;
                }
                stateOnCheck.addAll(tryClickAllButtons(currentState.state, machine.buttons, currentState.buttonClicks, alreadySeenState));
            }

            return -1;
        }

        private List<MachineState> tryClickAllButtons(Map<Integer, Integer> currentState,
                                                      List<List<Integer>> buttons,
                                                      int currentClickCount,
                                                      Set<Map<Integer, Integer>> alreadySeenState) {
            List<MachineState> newStates = new ArrayList<>();
            for (List<Integer> button : buttons) {
                Map<Integer, Integer> state = new HashMap<>(currentState);
                for (int buttonIndicator : button) {
                    state.put(buttonIndicator, state.get(buttonIndicator) == 0 ? 1 : 0);
                }
                if (!alreadySeenState.contains(state)) {
                    newStates.add(new MachineState(state, currentClickCount + 1));
                }
            }
            return newStates;
        }

        private Machine prepareMachineInfo(String line) {
            Map<Integer, Integer> machineIndicators = new HashMap<>();
            List<List<Integer>> buttons = new ArrayList<>();
            for (String inputParam : line.split(" ")) {
                if (inputParam.charAt(0) == '[') {
                    machineIndicators.putAll(parseStartMachineIndicators(inputParam));
                } else if (inputParam.charAt(0) == '(') {
                    buttons.add(parseButton(inputParam));
                }
            }
            return new Machine(machineIndicators, buttons);
        }

        private Map<Integer, Integer> parseStartMachineIndicators(String param) {
            Map<Integer, Integer> indicators = new HashMap<>();
            int index = 0;
            for (Character indicator : param.toCharArray()) {
                if (indicator == '.') {
                    indicators.put(index, 0);
                    index++;
                } else if (indicator == '#') {
                    indicators.put(index, 1);
                    index++;
                }
            }
            return indicators;
        }

        private List<Integer> parseButton(String param) {
            return Arrays.stream(param.replaceAll("[()]", "").split(","))
                    .map(Integer::parseInt)
                    .toList();
        }

        private Map<Integer, Integer> prepareInitialMachineState(int indicatorLightsCount) {
            Map<Integer, Integer> state = new HashMap<>();
            for (int i = 0; i < indicatorLightsCount; i++) {
                state.put(i, 0);
            }
            return state;
        }
    }


    private record Machine(Map<Integer, Integer> startMachineIndicator, List<List<Integer>> buttons) {}

    private record MachineState(Map<Integer, Integer> state, int buttonClicks) {
    }
}
