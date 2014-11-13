package Utilities.Pathfinding;

import java.util.Collection;

public interface IPathFinder<T> {

    public Number getCost (T beginningNode, T endingNode);

    public Collection<T> getNextNodes (T start);

    public boolean isDestination (T node);

    public Number getHeuristicValue (T node, T destination);
    
    public Collection<T> findPath (T start, T destination);
}
