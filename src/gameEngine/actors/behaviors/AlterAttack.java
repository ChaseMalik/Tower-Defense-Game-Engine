package gameEngine.actors.behaviors;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import gameEngine.actors.BaseActor;


/**
 * Class that allows actors to alter the attack behaviors of other actors
 * Concrete classes define how the attack is changed (decrease/increase attack speed/range)
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class AlterAttack implements IBehavior {
    protected double myTotalChange;
    protected double myRange;

    public AlterAttack (double totalChange, double range) {
        myTotalChange = totalChange;
        myRange = range;
    }

    /**
     * Given the effect's range, it finds the actors of the same type within its range
     * It then modifies their attack behavior
     * 
     * @param Actor holding this behavior
     */
    @Override
    public void execute (BaseActor actor) {
        Circle bounds = new Circle(myRange);
        bounds.setCenterX(actor.getX());
        bounds.setCenterY(actor.getY());

        Group actorsGroup = (Group) actor.getParent();

        for (Node n : actorsGroup.getChildren()) {
            BaseActor a = (BaseActor) n;
            if (!a.equals(actor) && bounds.intersects(n.getBoundsInLocal())) {
                BaseAttackBehavior attack = (BaseAttackBehavior) a.getBehavior("attack");
                changeParam(attack);
            }
        }
    }

    /**
     * Method that changes the appropriate parameter in the Attack Behavior by the appropriate
     * amount
     * 
     * @param attack behavior to be modified
     */
    protected abstract void changeParam (BaseAttackBehavior attack);
}
