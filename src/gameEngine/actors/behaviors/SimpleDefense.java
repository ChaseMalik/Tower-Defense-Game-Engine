package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;


public class SimpleDefense extends BaseDefendBehavior {
    public SimpleDefense (double health) {
        super(health);
    }

    @Override
    public void execute (BaseActor actor) {
        for (BaseActor b : actor.getInfoObject().getProjectilesInRange()) {
            BaseProjectile a = (BaseProjectile) b;
            if (actor.getNode().intersects(a.getRange().getBoundsInLocal()) && checkType(a,actor)) {
                if (a.getInfo().getOnHit() != null) {
                    for (IBehavior e : a.getInfo().getOnHit()) {
                        actor.addDebuff(e.copy());
                    }
                }
                myHealth -= a.getInfo().getMyDamage();
                a.died(1);
                if (myHealth <= 0) {
                    // TODO add enemy cost
                    actor.died(1);
                }
            }
        }
    }

    private boolean checkType (BaseProjectile p, BaseActor a) {
        // TODO Auto-generated method stub
        
        for(String s:p.getInfo().getEnemiesTypes()){
            if(s.equals(a.toString()))
                return true;
        }
        return false;
    }

    @Override
    public IBehavior copy () {
        return new SimpleDefense(myInitialHealth);
    }
}
