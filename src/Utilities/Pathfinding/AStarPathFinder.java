package Utilities.Pathfinding;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;


/**
 * @author Duke
 *
 *         Utility class for finding the least cost path in a network of nodes using A* path finding
 *         algorithm. To use, create a class that extends this abstract, and define all the
 *         unimplemented behavior, which are namely the heuristic function, how to get the cost
 *         between nodes and how to get neighbors. Then, instantiate an instance of the extending
 *         class, and call the method findPath by passing in the starting node and the ending node.
 *         The only other requirement apart from implementing the abstract methods is that the type
 *         specified in the generic correctly implements the equals method inherited from Object and
 *         that cost and heuristic values be positive numbers.
 * @param <T> Any object type.
 */
public abstract class AStarPathFinder<T> implements IPathFinder<T> {

    private class PQ extends PriorityQueue<PQTuple> {

        @Override
        public boolean add (PQTuple tuple) {
            if (contains(tuple)) {
                remove(tuple);
            }
            return super.add(tuple);
        }
    }

    private class PQTuple implements Comparable<PQTuple> {
        private T myNode;

        private Number myValue;

        public PQTuple (T node, Number value) {
            myNode = node;
            myValue = value;
        }

        public T getNode () {
            return myNode;
        }

        public Number getValue () {
            return myValue;
        }

        @Override
        public boolean equals (Object o) {
            if (o == null || !(o instanceof AStarPathFinder.PQTuple)) { return false; }
            PQTuple other = (PQTuple) o;
            return myNode.equals(other.getNode());
        }

        @Override
        public int compareTo (AStarPathFinder<T>.PQTuple o) {
            Double doubleValue = myValue.doubleValue();
            Double otherValue = o.getValue().doubleValue();
            return doubleValue.compareTo(otherValue);
        }
    }

    /**
     * Gets the h(x) heuristic value from node to destination. If no heuristic is to be defined,
     * then simply return 0 every time (becomes Djikstra's algorithm then)
     * 
     * @param node Node in question
     * @param destination Destination node
     * @return Heuristic value
     */
    public abstract Number getHeuristicValue (T node, T destination);

    @Override
    public boolean isDestination (T node, T destinationNode) {
        return node.equals(destinationNode);
    }

    @Override
    public List<T> findPath (T start, T destination) {
        PQ frontierQueue = new PQ();
        HashSet<T> visitedNodes = new HashSet<>();
        HashMap<T, Number> nodeToValueMap = new HashMap<>();
        HashMap<T, ArrayList<T>> nodeToCurrentPathMap = new HashMap<>();

        PQTuple startTuple = new PQTuple(start, getHeuristicValue(start, destination));
        frontierQueue.add(startTuple);

        ArrayList<T> beginningRoute = new ArrayList<>();
        nodeToCurrentPathMap.put(start, beginningRoute);

        nodeToValueMap.put(start, 0);

        while (!frontierQueue.isEmpty()) {
            PQTuple tupleToCheck = frontierQueue.poll();
            T currentNode = tupleToCheck.getNode();
            if (isDestination(currentNode, destination)) {
                ArrayList<T> pathUpToDestination = nodeToCurrentPathMap.get(currentNode);
                pathUpToDestination.add(currentNode);
                return pathUpToDestination;
            }
            visitedNodes.add(currentNode);
            Number valueSoFar = nodeToValueMap.get(currentNode);
            ArrayList<T> pathSoFar = nodeToCurrentPathMap.get(currentNode);
            Collection<T> nextNodes = getNeighbors(currentNode);
            for (T neighboringNextNode : nextNodes) {
                if (!visitedNodes.contains(neighboringNextNode)) {
                    Number gScore =
                            addNumbers(valueSoFar, getCost(currentNode, neighboringNextNode));
                    Number currentRecordedValue = nodeToValueMap.get(neighboringNextNode);
                    if (currentRecordedValue == null ||
                        gScore.doubleValue() < currentRecordedValue.doubleValue()) {
                        nodeToValueMap.put(neighboringNextNode, gScore);
                        ArrayList<T> pathSoFarCopy = new ArrayList<>(pathSoFar);
                        pathSoFarCopy.add(currentNode);
                        nodeToCurrentPathMap.put(neighboringNextNode, pathSoFarCopy);

                        Number hScore = getHeuristicValue(neighboringNextNode, destination);
                        Number totalScore = addNumbers(gScore, hScore.doubleValue());

                        PQTuple tuple = new PQTuple(neighboringNextNode, totalScore);
                        frontierQueue.add(tuple);
                    }
                }
            }

        }
        return null;
    }

    private Number addNumbers (Number ... numbers) {
        Double total = 0.0;
        for (Number number : numbers) {
            total += number.doubleValue();
        }
        return total;
    }
}
