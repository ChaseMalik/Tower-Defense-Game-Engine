package gameEngine.actors.behaviors;

import java.util.List;

public abstract class MeleeAttack extends BaseAttack{
    
    public MeleeAttack(List<Double> list){
        super(list);
    }
    public MeleeAttack (double attackSpeed) {
        super(attackSpeed);
        // TODO Auto-generated constructor stub
    }
}
