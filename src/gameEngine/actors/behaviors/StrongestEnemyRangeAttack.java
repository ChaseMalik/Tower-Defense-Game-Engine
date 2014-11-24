package gameEngine.actors.behaviors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.RealActor;


public class StrongestEnemyRangeAttack extends RangeAttack {

    public static final String DEFEND = "defend";
    
    public StrongestEnemyRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void performAttack (BaseActor actor) {
        RealActor shooter = (RealActor)actor;
        BaseActor shootable = getStrongestActor(shooter, actor.getEnemiesInRange());
        if (shootable.equals(null))
            return;
        shootActorFromActor(shootable, actor);
    }

    private BaseActor getStrongestActor (BaseActor actor, List<BaseActor> enemies) {
        BaseActor strong = null;
        double maxHealth = Integer.MIN_VALUE;
        for (BaseActor e : enemies) {
            BaseDefendBehavior m = (BaseDefendBehavior)e.getBehavior(DEFEND);
            if (m.getHealth() > maxHealth) {
                strong = e;
                maxHealth = m.getHealth();
            }
        }
        return strong;
    }

    @Override
    public IBehavior copy () {
        return new StrongestEnemyRangeAttack(myAttackSpeed);
    }

    @Override
    public Set<Class<? extends BaseActor>> getType () {
        Set<Class<? extends BaseActor>> a = new HashSet<Class<? extends BaseActor>>();
        a.add(BaseEnemy.class);
        return a;
    }
}
