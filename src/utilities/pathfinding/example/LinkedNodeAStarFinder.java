package utilities.pathfinding.example;

import utilities.pathfinding.AStarPathFinder;

public class LinkedNodeAStarFinder extends AStarPathFinder<LinkedNode>{

    @Override
    public Number getCost (LinkedNode beginningNode, LinkedNode endingNode) {
        int beginningX = beginningNode.getX();
        int beginningY = beginningNode.getY();
        int endingX = endingNode.getX();
        int endingY = endingNode.getY();
        return Math.abs(endingX - beginningX) + Math.abs(endingY - beginningY);
    }

    @Override
    public Iterable<LinkedNode> getNeighbors (LinkedNode node) {
        return node.getNeighbors();
    }

    @Override
    public int breakTie (LinkedNode node, LinkedNode other) {
        return node.compareTo(other);
    }

    @Override
    public Number getHeuristicValue (LinkedNode node, LinkedNode destination) {
        int beginningX = node.getX();
        int beginningY = node.getY();
        int endingX = destination.getX();
        int endingY = destination.getY();
        return Math.sqrt(1.0 * Math.pow(endingX - beginningX, 2) +
                         Math.pow(endingY - beginningY, 2));
    }

}
