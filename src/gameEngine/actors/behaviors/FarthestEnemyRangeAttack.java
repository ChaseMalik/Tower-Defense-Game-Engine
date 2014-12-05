package gameEngine.actors.behaviors;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import gameEngine.actors.RealActor;

public class FarthestEnemyRangeAttack extends RangeAttack{

    public FarthestEnemyRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void performAttack (BaseActor actor) {
        Comparator<BaseActor> byFarthest = (BaseActor a1, BaseActor a2) -> Double
                .compare(((BaseMovementBehavior) (a1.getBehavior("movement"))).getRemainingDistance(),
                         ((BaseMovementBehavior) (a2.getBehavior("movement"))).getRemainingDistance());
        Optional<BaseActor>target = actor.getEnemiesInRange().stream().sorted(byFarthest).findFirst();
        if(target.isPresent()){
            shootActorFromActor(target.get(),actor);
        }
    }
    
    @Override
    public IBehavior copy () {
        return new FarthestEnemyRangeAttack(myAttackSpeed);
    }
    
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        Set<Class<? extends BaseActor>> a= new HashSet<Class<? extends BaseActor>>();
        a.add(BaseEnemy.class);
        return a;
    }
}
