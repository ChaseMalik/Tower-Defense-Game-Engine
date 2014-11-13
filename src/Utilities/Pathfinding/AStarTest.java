package Utilities.Pathfinding;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.Test;


public class AStarTest {

    private class TestNode {

        private int myX;
        private int myY;

        public TestNode (int x, int y) {
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
        public boolean equals(Object other){
            if(other == null || !(other instanceof TestNode)){
                return false;
            }
            TestNode otherNode = (TestNode)other;
            return myX == otherNode.getX() && myY == otherNode.getY();
        }
        
        @Override
        public String toString(){
            return myX + ", " + myY;
        }
    }

    private class TestAStarFinder extends AStarPathFinder<TestNode> {

        private TestNode[][] myMap;

        public TestAStarFinder (TestNode[][] nodeMap) {
            myMap = nodeMap;
        }

        @Override
        public Integer getCost (TestNode beginningNode, TestNode endingNode) {
            int beginningX = beginningNode.getX();
            int beginningY = beginningNode.getY();
            int endingX = endingNode.getX();
            int endingY = endingNode.getY();
            return Math.abs(endingX - beginningX) + Math.abs(endingY - beginningY);
        }

        @Override
        public Collection<TestNode> getNeighbors (TestNode node) {
            int[][] myDefault2DDirections = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, };
            int x = node.getX();
            int y = node.getY();
            ArrayList<TestNode> nodeList = new ArrayList<>();
            for (int[] direction : myDefault2DDirections) {
                int neighborX = x + direction[0];
                int neighborY = y + direction[1];
                if(neighborX >= 0 && neighborX < myMap.length && neighborY >= 0 && neighborY < myMap[0].length){
                    nodeList.add(myMap[neighborX][neighborY]);
                }
            }
            return nodeList;
        }

        @Override
        public Number getHeuristicValue (TestNode node, TestNode destination) {
            int beginningX = node.getX();
            int beginningY = node.getY();
            int endingX = destination.getX();
            int endingY = destination.getY();
            return Math.sqrt(1.0 * Math.pow(endingX - beginningX, 2) +
                             Math.pow(endingY - beginningY, 2));
        }

    }

    @Test
    public void testAStarLogic() {
        int xMax = 50;
        int yMax = 39;
        TestNode[][] nodeMap = new TestNode[xMax][yMax];
        for(int i=0; i < xMax; i++){
            for (int j=0; j < yMax; j++){
                TestNode node = new TestNode(i, j);
                nodeMap[i][j] = node;
            }
        }
        TestAStarFinder finder = new TestAStarFinder(nodeMap);
        Random rand = new Random();
        for(int i=0; i < 50; i ++){
            int startX = rand.nextInt(xMax);
            int startY = rand.nextInt(yMax);
            int endX = rand.nextInt(xMax);
            int endY = rand.nextInt(yMax);
            TestNode start = new TestNode(startX, startY);
            TestNode destination = new TestNode(endX, endY);
            List<TestNode> result = finder.findPath(start, destination);
            assertEquals(new Double(Math.abs((endX - startX)) + Math.abs((endY - startY)) +1), new Double(result.size()));
        }
    }
}
