package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Specific implementation of the movement behavior for an actor, whereby the actor moves
 * from point to point along a route that is randomly chosen from the given options
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class LinearMovement extends BaseMovementBehavior {
    
    public LinearMovement(List<Double> list) {
        super(list);
    }

    public LinearMovement(double speed, BackendRoute route){
        super(speed,route);
    }
    @Override
    public void execute (BaseActor actor) {
        if (myIndex == 0) {
            move(actor, myRoute.get(myIndex));
            myIndex++;
            return;
        }
        findNextTarget(actor,myRoute.stream().map(v ->v.getPoint()).collect(Collectors.toList()));
        actor.getNode().setVisible(myRoute.get(myIndex-1).isVisible());

    }

    private void move (BaseActor a, VisibilityPoint point) {
       super.move(a, point.getPoint());
        a.getNode().setVisible(point.isVisible());
    }

    @Override
    public IBehavior copy () {

        return new LinearMovement(myList);

    }



}
