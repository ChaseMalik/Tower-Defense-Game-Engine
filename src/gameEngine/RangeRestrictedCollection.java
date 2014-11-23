package gameEngine;

import gameEngine.actors.BaseActor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class RangeRestrictedCollection<T extends BaseActor> extends Group implements Iterable<T>{

    private Collection<T> myActorsList;
    
    public RangeRestrictedCollection (Collection<T> actors) {
        myActorsList = actors;
    }
    
    public RangeRestrictedCollection () {
        myActorsList = new ArrayList<>();
    }
    
    public void remove(T actor) {
        myActorsList.remove(actor);
        getChildren().remove(actor.getNode());
    }
    
    public void add(T actor) {
        myActorsList.add(actor);
        getChildren().add(actor.getNode());
    }
    
    public List<BaseActor> getActorsInRange(BaseActor actor) {
        Node actorNode = actor.getRange();
        ArrayList<BaseActor> actorsInRange = new ArrayList<>();
        for(T otherActor : myActorsList) {
            Node otherActorNode = otherActor.getNode();
            if (!actorNode.equals(otherActor) && actorNode.intersects(otherActorNode.getBoundsInLocal())) {
                actorsInRange.add(otherActor);
            }
        }
        return actorsInRange;
    }
    

    @Override
    public Iterator<T> iterator () {
        return myActorsList.iterator();
    }
}
