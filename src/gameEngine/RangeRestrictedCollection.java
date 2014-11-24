package gameEngine;

import gameEngine.actors.BaseActor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class RangeRestrictedCollection<T extends BaseActor> extends Group
		implements Iterable<T> {

	private Collection<T> myActorsList;
	private Collection<T> myActorsToRemove;

	public RangeRestrictedCollection(Collection<T> actors) {
		myActorsList = actors;
		myActorsToRemove = new HashSet<>();
	}

	public RangeRestrictedCollection() {
	    myActorsToRemove = new ArrayList<>();
		myActorsList = new ArrayList<>();
	}

	public void remove(T actor) {
		myActorsList.remove(actor);
		getChildren().remove(actor.getNode());
	}

	public void add(T actor) {
	        if(actor != null){
	            myActorsList.add(actor);
	            getChildren().add(actor.getNode());
	        }		
	}

	public List<BaseActor> getActorsInRange(BaseActor actor) {
		Node actorNode = actor.getRange();
		ArrayList<BaseActor> actorsInRange = new ArrayList<>();
		for (T otherActor : myActorsList) {
			Node otherActorNode = otherActor.getNode();
			if (!actorNode.equals(otherActor)
					&& !shouldBeRemoved(otherActor)
					&& actorNode.intersects(otherActorNode.getBoundsInLocal())) {
				actorsInRange.add(otherActor);
			}
		}
		return actorsInRange;
	}

	public void clearAndExecuteRemoveBuffer() {
		for (T actor : myActorsToRemove) {
			remove(actor);
		}
		myActorsToRemove.clear();
	}

	private boolean shouldBeRemoved(T actor) {
		return myActorsToRemove.contains(actor);
	}

	public boolean addActorToRemoveBuffer(BaseActor actor) {
	    boolean canBeRemoved = false;
	        if(actor != null){	            
	                try{
	                        canBeRemoved = myActorsList.contains(actor);
	                        if(canBeRemoved){
	                                T castedActor = (T) actor;
	                                myActorsToRemove.add(castedActor);
	                        }       
	                }
	                catch(ClassCastException | NullPointerException ex) {
	                        canBeRemoved = false;
	                }
	        }		
		return canBeRemoved;
	}

	@Override
	public Iterator<T> iterator() {
		return myActorsList.iterator();
	}
}
