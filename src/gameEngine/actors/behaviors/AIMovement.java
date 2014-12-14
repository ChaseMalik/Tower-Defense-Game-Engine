package gameEngine.actors.behaviors;

import gameEngine.AI.GridPathFinder;
import gameEngine.ManagerInterface.InformationInterface;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import java.util.List;
import javafx.geometry.Point2D;


public class AIMovement extends BaseMovementBehavior {
    private transient GridPathFinder myPathFinder;
    private transient List<Point2D> myAIRoute;

    public AIMovement (List<Double> list) {
        super(list);
        myPathFinder = new GridPathFinder();
        myIndex = -1;
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        InformationInterface info = actor.getInfoInterface();
        BaseEnemy enemy = (BaseEnemy) actor;
        if (myIndex == -1) {
            actor.getNode().setVisible(true);
            move(enemy, enemy.getStart().getPoint());
            myAIRoute =
                    myPathFinder.getPath(enemy, info.getReferencePane(),
                                         info.getExistingTowerTiles());
            myIndex = 0;
        }
        else if (info.checkNewPath()) {
            myAIRoute =
                    myPathFinder.getPath(enemy, info.getReferencePane(),
                                         info.getExistingTowerTiles());
            myIndex = 0;
        }
        if (myAIRoute == null) { return; }
        findNextTarget(actor, myAIRoute);
    }

    @Override
    public IBehavior copy () {
        return new AIMovement(myList);
    }

}
