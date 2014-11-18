package gameEngine.actors.behaviors;

public class BuffAttackSpeed extends AlterAttack {

    public BuffAttackSpeed (double percentBuff, double range) {
        super(percentBuff, range);
    }

    @Override
    public IBehavior copy () {
        return new BuffAttackSpeed(myTotalChange, myRange);
    }

    @Override
    protected void changeParam (BaseAttackBehavior attack) {
        attack.setAttackSpeed((int) (attack.getAttackSpeed() * (1 + myTotalChange)));
    }

}
