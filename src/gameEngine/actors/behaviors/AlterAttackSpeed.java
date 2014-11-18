package gameEngine.actors.behaviors;

/**
 * Alters attack speed of nearby neighbors of the same type
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class AlterAttackSpeed extends AlterAttack {

    public AlterAttackSpeed (double percentBuff, double range) {
        super(percentBuff, range);
    }

    @Override
    public IBehavior copy () {
        return new AlterAttackSpeed(myTotalChange, myRange);
    }

    @Override
    protected void changeParam (BaseAttackBehavior attack) {
        attack.setAttackSpeed((int) (attack.getAttackSpeed() * myTotalChange));
    }

}
