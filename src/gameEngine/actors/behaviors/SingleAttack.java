package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathRoute;
import gameEngine.actors.BaseActor;

public class SingleAttack extends BaseAttackBehavior{

    public SingleAttack (int attackSpeed, double range, BaseActor projectile) {
        super(attackSpeed, range, projectile);

    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.getX();
        actor.getY();
        PathRoute route=new PathRoute();
        PathStartingLocation start=new PathStartingLocation(actor.getX(),actor.getY());
        PathEndingLocation end=new PathEndingLocation(0,0);
         
    }

}
