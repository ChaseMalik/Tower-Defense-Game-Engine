// This entire file is part of my masterpiece.
// Duke Kim

package utilities.pathfinding;

import java.util.Collection;
import java.util.List;


/**
 * @author Duke
 *         Interface for finding a path from a certain node to a destination node. Can take in
 *         any data object as the generic.
 * @param <T> Any object type.
 */
public interface IPathFinder<T>{

    /**
     * Gets the cost of traveling from beginningNode to endingNode.
     * 
     * @param beginningNode The beginning node in question
     * @param endingNode The ending node in question
     * @return Cost of traveling from beginningNode to endingNode
     */
    public Number getCost (T beginningNode, T endingNode);

    /**
     * Gets the neighbors to which one can move to from the node specified in the parameter.
     * 
     * @param node Node in question
     * @return A collection of neighboring nodes
     */
    public Iterable<T> getNeighbors (T node);

    /**
     * Indicates whether a node is the destination node. In most cases, this method should use the
     * equals method specified in the generic type
     * 
     * @param node Node in question
     * @param destinationNode Destination node
     * @return Whether the node is the destination node.
     */
    public boolean isDestination (T node, T destinationNode);

    /**
     * Finds the least cost path from the node specified by start to the one specified by
     * destination
     * 
     * @param start Starting node
     * @param destination Ending node
     * @return Path from start to destination in order.
     */
    public List<T> findPath (T start, T destination);

    /**
     * Specifies the behavior for breaking ties when the cost values for getting to two nodes is the
     * same. Should return something similar to Comparable's compareTo(other) method.
     * 
     * @param node Node in question. In the case of a class implementing Comparable's
     *        compareTo(other), it would be the equivalent of this.
     * @param other The other node. In the case of a class implementing Comparable's
     *        compareTo(other), it would be the equivalent of the parameter.
     * @return Result of tie breaker.
     */
    public int breakTie (T node, T other);
}
