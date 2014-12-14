package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public class AoEEffect extends BaseEffect{
    private double myDamage;
    private double myRange;
    public AoEEffect (List<Double> list) {
        super(list);
        myDamage=list.get(0);
        myRange=list.get(1);
        myDuration=1;
        myInitialDuration=1;
        myString="AoEEffect";
        // TODO Auto-generated constructor stub
    }


    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new AoEEffect(myList);
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        for(BaseActor a:actor.getEnemiesInRange(myRange)){
            BaseDefendBehavior d=((BaseDefendBehavior)a.getBehavior("defend"));
            if(d.getHealth()-myDamage<0){
                d.onDeath(a);
            }else{
                d.setHealth(d.getHealth()-myDamage);
            }
                
        }
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.removeDebuff(this);
    }

}
