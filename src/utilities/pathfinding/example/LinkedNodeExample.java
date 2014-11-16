package utilities.pathfinding.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @author Duke
 * 
 *         An example using LinkedNode. Refer to LinkedNodeAStarFinder for actual AStarFinder
 *         implementation. Represents the same 2D array based data structure used in NodeExample.
 *         However, instead of using a 2D array, it uses linked nodes which have pointers to its
 *         valid neighbors. The neighbors are nodes on the left/right and on top/bottom. No diagonals.
 */
public class LinkedNodeExample {

    public static void main (String[] args) {
        Random rand = new Random();
        int xMax = rand.nextInt(100);
        int yMax = rand.nextInt(100);
        Map<Position, LinkedNode> nodeMap = createMap(xMax, yMax);
        updateNodesWithNeighbors(nodeMap, xMax, yMax);
        ArrayList<LinkedNode> nodeList = new ArrayList<>(nodeMap.values());
        int startIndex = rand.nextInt(nodeList.size());
        int endIndex = rand.nextInt(nodeList.size());
        LinkedNode start = nodeList.get(startIndex);
        LinkedNode destination = nodeList.get(endIndex);

        LinkedNodeAStarFinder pathFinder = new LinkedNodeAStarFinder();
        List<LinkedNode> result = pathFinder.findPath(start, destination);
        System.out.println("Path from " + start + " to " + destination);
        for (LinkedNode node : result) {
            System.out.println(node);
        }

    }

    private static class Position {

        private int myX;
        private int myY;

        public Position (int x, int y) {
            myX = x;
            myY = y;
        }

        public int getX () {
            return myX;
        }

        public int getY () {
            return myY;
        }

        @Override
        public int hashCode () {
            int hash = 7;
            hash = 71 * hash + myX;
            hash = 71 * hash + myY;
            return hash;
        }

        @Override
        public boolean equals (Object o) {
            if (o == null || !(o instanceof Position)) { return false; }
            Position other = (Position) o;
            return myX == other.getX() && myY == other.getY();
        }
    }

    private static Map<Position, LinkedNode> createMap (int xMax, int yMax) {
        HashMap<Position, LinkedNode> map = new HashMap<>();
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                LinkedNode node = new LinkedNode(i, j);
                Position position = new Position(i, j);
                map.put(position, node);
            }
        }
        return map;
    }

    private static void updateNodesWithNeighbors (Map<Position, LinkedNode> map, int xMax, int yMax) {
        int[][] directions = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, };
        for (LinkedNode node : map.values()) {
            int nodeX = node.getX();
            int nodeY = node.getY();
            for (int[] direction : directions) {
                int neighborX = nodeX + direction[0];
                int neighborY = nodeY + direction[1];
                if (neighborX >= 0 && neighborX < xMax && neighborY >= 0 &&
                    neighborY < yMax) {
                    Position neighborPosition = new Position(neighborX, neighborY);
                    LinkedNode neighborNode = map.get(neighborPosition);
                    node.addNeighbor(neighborNode);
                }
            }
        }
    }
}
