package gameEngine.tests;

import static org.junit.Assert.*;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.actors.behaviors.LinearMovement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;


public class BehaviorTester {

    private List<BackendRoute> myOptions;
    private static final double EPSILON = 0.00000001;

    @Before
    public void setup () {
        BackendRoute route1 = new BackendRoute(new Point2D(0, 0), new Point2D(10, 10));
        BackendRoute route2 = new BackendRoute(new Point2D(0, 0), new Point2D(-3, 4));
        myOptions = Arrays.asList(route1, route2);
    }

    @Test
    public void testMovementBehaviorSimple () {
        BackendRoute route = new BackendRoute(new Point2D(0, 0), new Point2D(-3, 4));
        myOptions = Arrays.asList(route);

        Map<String, IBehavior> movement = new HashMap<String, IBehavior>() {
            {
                put(null, new LinearMovement(myOptions, 2.5));
            }
        };

        BaseActor actor = createActor(movement);
        updateAndCheck(actor, 0, 0);
        actor.update(null);
        updateAndCheck(actor, -3, 4);
    }

    @Test
    public void testMovementBehaviorSimple2 () {
        BackendRoute route = new BackendRoute(new Point2D(0, 0), new Point2D(10, 10));
        myOptions = Arrays.asList(route);
        Map<String, IBehavior> movement = new HashMap<String, IBehavior>() {
            {
                put(null, new LinearMovement(myOptions, Math.sqrt(18)));
            }
        };
        BaseActor actor = createActor(movement);
        actor.update(null);
        updateAndCheck(actor, 3, 3);
        updateAndCheck(actor, 6, 6);
    }

    @Test
    public void testMovementClone () {
        Map<String, IBehavior> movement = new HashMap<String, IBehavior>() {
            {
                put(null, new LinearMovement(myOptions, 2.5));
            }
        };

        BaseActor actor = createActor(movement);
        actor.update(null);
        actor.update(null);
        List<BaseActor> clones = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            clones.add(actor.copy());
        }
        boolean answer = false;
        for (BaseActor a : clones) {
            a.update(null);
            a.update(null);
            if (a.getX() != actor.getX()) {
                answer = true;
            }
        }
        assertEquals(true, answer);
    }

    private BaseActor createActor(Map<String,IBehavior> move){
        return new BaseActor(move,"./src/gameAuthoring/Resources/towerImages/bowser.jpg", null, 0);
    }
    
    private void updateAndCheck (BaseActor actor, double x, double y) {
        actor.update(null);
        assertEquals(x, actor.getX(), EPSILON);
        assertEquals(y, actor.getY(), EPSILON);
    }
}
