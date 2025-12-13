package ru.savostikov.advent.solvers.years.twentyfive.day11;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.util.*;

@AdventPuzzle(year = Year.TWO_K_TWENTY_FIVE, day = Day.ELEVEN)
public class Reactor {

    public static class Part1 implements PuzzleSolver {

        private final Map<String, NodeConnections> connectionsMap = new HashMap<>();

        @Override
        public long solve(String input) {
            Arrays.stream(input.split("\r\n")).forEach(this::readConnections);
            return findAllPathsToOut(connectionsMap.get("you"));
        }

        private void readConnections(String line) {
            var nodeAndConnections = line.split(":");
            var nodeName = nodeAndConnections[0];
            var connections = nodeAndConnections[1].trim().split(" ");

            NodeConnections currentNode = connectionsMap.computeIfAbsent(nodeName, NodeConnections::new);
            for (String connection : connections) {
                currentNode.addConnection(connectionsMap.computeIfAbsent(connection, NodeConnections::new));
            }
        }

        private int findAllPathsToOut(NodeConnections you) {
            int result = 0;
            for (NodeConnections connection : you.connections) {
                if (connection.nodeName.equals("out")) {
                    return 1;
                }
                result += findAllPathsToOut(connection);
            }
            return result;
        }
    }

    public static class Part2 implements PuzzleSolver {

        private final Map<String, NodeConnections> connectionsMap = new HashMap<>();

        @Override
        public long solve(String input) {
            Arrays.stream(input.split("\r\n")).forEach(this::readConnections);
            return findAllPathsToOut(connectionsMap.get("svr"), new HashSet<>(), new HashMap<>());
        }

        private void readConnections(String line) {
            var nodeAndConnections = line.split(":");
            var nodeName = nodeAndConnections[0];
            var connections = nodeAndConnections[1].trim().split(" ");

            NodeConnections currentNode = connectionsMap.computeIfAbsent(nodeName, NodeConnections::new);
            for (String connection : connections) {
                currentNode.addConnection(connectionsMap.computeIfAbsent(connection, NodeConnections::new));
            }
        }

        private long findAllPathsToOut(NodeConnections you, Set<String> seen, Map<String, Long> cache) {
            if (!you.nodeName.equals("out") && cache.containsKey(you.nodeName)) {
                return cache.get(you.nodeName);
            }
            long result = 0;
            for (NodeConnections connection : you.connections) {
                if (connection.nodeName.equals("out")) {
                    if (seen.contains("dac") && seen.contains("fft")) {
                        return 1;
                    }
                }
                Set<String> nextSeen = new HashSet<>(seen);
                nextSeen.add(you.nodeName);
                result += findAllPathsToOut(connection, nextSeen, cache);
//                cache.put(connection.nodeName, result);
            }
            cache.put(you.nodeName, result);
            return result;
        }
    }

    private record NodeConnections(String nodeName, List<NodeConnections> connections) {
        public NodeConnections(String nodeName) {
            this(nodeName, new ArrayList<>());
        }

        public void addConnection(NodeConnections connection) {
            this.connections.add(connection);
        }
    }
}
