package utilities.pathfinding.example;

import java.util.ArrayList;
import java.util.Collection;
import utilities.pathfinding.AStarPathFinder;

public class UnlinkedNodeAStarFinder extends AStarPathFinder<UnlinkedNode>{

    private UnlinkedNode[][] myMap;

    public UnlinkedNodeAStarFinder (UnlinkedNode[][] nodeMap) {
        myMap = nodeMap;
    }

    @Override
    public Integer getCost (UnlinkedNode beginningNode, UnlinkedNode endingNode) {
        int beginningX = beginningNode.getX();
        int beginningY = beginningNode.getY();
        int endingX = endingNode.getX();
        int endingY = endingNode.getY();
        return Math.abs(endingX - beginningX) + Math.abs(endingY - beginningY);
    }

    @Override
    public Collection<UnlinkedNode> getNeighbors (UnlinkedNode node) {
        int[][] myDefault2DDirections = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, };
        int x = node.getX();
        int y = node.getY();
        ArrayList<UnlinkedNode> nodeList = new ArrayList<>();
        for (int[] direction : myDefault2DDirections) {
            int neighborX = x + direction[0];
            int neighborY = y + direction[1];
            if (neighborX >= 0 && neighborX < myMap.length && neighborY >= 0 &&
                neighborY < myMap[0].length) {
                nodeList.add(myMap[neighborX][neighborY]);
            }
        }
        return nodeList;
    }

    @Override
    public Number getHeuristicValue (UnlinkedNode node, UnlinkedNode destination) {
        int beginningX = node.getX();
        int beginningY = node.getY();
        int endingX = destination.getX();
        int endingY = destination.getY();
        return Math.sqrt(1.0 * Math.pow(endingX - beginningX, 2) +
                         Math.pow(endingY - beginningY, 2));
    }

    @Override
    public int breakTie (UnlinkedNode node, UnlinkedNode other) {
        Integer x = node.getX();
        Integer otherX = other.getX();
        int xCompareValue = x.compareTo(otherX);
        return xCompareValue == 0 ? ((Integer) node.getY()).compareTo((Integer) other.getY())
                                 : xCompareValue;
    }

}
