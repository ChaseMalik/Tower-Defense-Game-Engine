package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import java.util.List;
import java.util.stream.Stream;


/**
 * Behavior that defines the defense of an actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseDefendBehavior implements IBehavior {

    protected double myHealth;
    protected double myInitialHealth;

    BaseDefendBehavior (List<Double> list) {

    }

    BaseDefendBehavior (double health) {
        myHealth = health;
        myInitialHealth = health;
    }

    public double getHealth () {
        return myHealth;
    }

    @Override
    public void execute (BaseActor actor) {
        Stream<BaseActor> a = actor.getProjectilesInRange(actor.getAttackRange()).stream()
                .filter(p ->
                        actor.getNode().intersects(p.getRange().getBoundsInLocal()));

        a.filter(p -> checkTypes((BaseProjectile) p, actor))
                .forEach(p -> gotHit(actor, (BaseProjectile) p));
    }

    public void setHealth (double d) {
        // TODO Auto-generated method stub
        myHealth = d;
    }

    public void gotHit (BaseActor actor, BaseProjectile p) {
        if (p.getInfo().getOnHit() != null) {
            for (IBehavior e : p.getInfo().getOnHit()) {
                IBehavior effect = e.copy();
                actor.addDebuff(effect);
                effect.execute(actor);
            }
        }
        handleBullet(p);
        if (myHealth <= 0) {
            // TODO add enemy cost
            onDeath(actor);
        }
    }

    public abstract void handleBullet (BaseProjectile p);

    public abstract void onDeath (BaseActor actor);

    private boolean checkTypes (BaseProjectile p, BaseActor a) {
        // TODO Auto-generated method stub

        for (String s : p.getInfo().getEnemiesTypes()) {
            if (s.equals(a.toString()))
                return true;
        }
        return false;
    }
}
