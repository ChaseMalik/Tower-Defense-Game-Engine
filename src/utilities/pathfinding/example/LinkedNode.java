package utilities.pathfinding.example;

import java.util.ArrayList;
import java.util.Collection;

public class LinkedNode extends UnlinkedNode implements Comparable<LinkedNode> {

    Collection<LinkedNode> myNext;
    
    public LinkedNode (int x, int y) {
        super(x, y);
        myNext = new ArrayList<LinkedNode>();
    }

    public Collection<LinkedNode> getNeighbors(){
        return new ArrayList<>(myNext);
    }
    
    public void addNeighbor(LinkedNode neighbor){
        myNext.add(neighbor);
    }

    @Override
    public int compareTo (LinkedNode o) {
        Integer x = getX();
        Integer otherX = o.getX();
        int xCompareValue = x.compareTo(otherX);
        return xCompareValue == 0 ? ((Integer) getY()).compareTo((Integer) o.getY())
                                 : xCompareValue;
    }
}
