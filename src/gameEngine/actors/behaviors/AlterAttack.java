package gameEngine.actors.behaviors;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import gameEngine.actors.BaseActor;


public abstract class AlterAttack implements IBehavior {
    protected double myTotalChange;
    protected double myRange;

    public AlterAttack (double totalChange, double range) {
        myTotalChange = totalChange;
        myRange = range;
    }

    @Override
    public void execute (BaseActor actor) {
        Circle bounds = new Circle(myRange);
        bounds.setCenterX(actor.getX());
        bounds.setCenterY(actor.getY());

        Group actorsGroup = (Group)actor.getParent();

        for (Node n : actorsGroup.getChildren()) {
            BaseActor a = (BaseActor)n;
            if (bounds.intersects(n.getBoundsInLocal())) {
                BaseAttackBehavior attack = (BaseAttackBehavior)a.getBehavior("attack");
                changeParam(attack);
            }
        }
    }

    protected abstract void changeParam (BaseAttackBehavior attack);
}
