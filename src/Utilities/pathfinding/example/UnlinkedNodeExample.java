package Utilities.pathfinding.example;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Random;


/**
 * @author Duke
 * 
 *         An example using Node. Refer to NodeAStarFinder for actual AStarFinder implementation.
 *         Exact same map structure and finder implementation as the AStarTest.
 *         Represents the same 2D array based data structure used in LinkedNodeExample.
 *         However, instead of using a linked map, it uses nodes arranged in a 2D array The
 *         neighbors are nodes on the left/right and on top/bottom. No diagonals.
 */
public class UnlinkedNodeExample {

    public static void main (String[] args) {
        Random rand = new Random();
        int xMax = rand.nextInt(100);
        int yMax = rand.nextInt(100);
        UnlinkedNode[][] nodeMap = createMap(xMax, yMax);
        UnlinkedNodeAStarFinder finder = new UnlinkedNodeAStarFinder(nodeMap);

        int startX = rand.nextInt(xMax);
        int startY = rand.nextInt(yMax);
        int endX = rand.nextInt(xMax);
        int endY = rand.nextInt(yMax);
        UnlinkedNode start = new UnlinkedNode(startX, startY);
        UnlinkedNode destination = new UnlinkedNode(endX, endY);
        List<UnlinkedNode> result = finder.findPath(start, destination);
        System.out.println("Path from " + start + " to " + destination);
        for (UnlinkedNode node : result) {
            System.out.println(node);
        }
    }

    private static UnlinkedNode[][] createMap (int xMax, int yMax) {
        UnlinkedNode[][] nodeMap = new UnlinkedNode[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                UnlinkedNode node = new UnlinkedNode(i, j);
                nodeMap[i][j] = node;
            }
        }
        return nodeMap;
    }
}
