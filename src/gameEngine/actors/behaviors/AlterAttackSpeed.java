package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Set;

/**
 * Alters attack speed of nearby neighbors of the same type
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class AlterAttackSpeed extends AlterAttack {

    public AlterAttackSpeed (double percentBuff) {
        super(percentBuff);
    }

    @Override
    public IBehavior copy () {
        return new AlterAttackSpeed(myTotalChange);
    }

    @Override
    protected void changeParam (NewAttack attack) {
    //    attack.setAttackSpeed((int) (attack.getAttackSpeed() * myTotalChange));
    }

    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }

}
