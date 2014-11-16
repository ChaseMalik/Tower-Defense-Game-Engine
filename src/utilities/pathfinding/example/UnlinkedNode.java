package utilities.pathfinding.example;

public class UnlinkedNode {

    private int myX;
    private int myY;

    public UnlinkedNode (int x, int y) {
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
    public boolean equals (Object other) {
        if (other == null || !(other instanceof UnlinkedNode)) { return false; }
        UnlinkedNode otherNode = (UnlinkedNode) other;
        return myX == otherNode.getX() && myY == otherNode.getY();
    }

    @Override
    public String toString () {
        return myX + ", " + myY;
    }
}
