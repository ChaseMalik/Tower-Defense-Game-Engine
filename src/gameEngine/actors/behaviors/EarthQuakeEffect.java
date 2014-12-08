package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public class EarthQuakeEffect extends BaseOnHitBehavior {
    
    public EarthQuakeEffect(List<Double> list){
   
    }
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        double m=actor.getInfoObject().getEarthquakeMagnitude();
        if(m>=0){
         for(BaseActor a: actor.getEnemiesInRange(1000)){
             BaseDefendBehavior d=((BaseDefendBehavior)a.getBehavior("defend"));
             if(d.getHealth()-m<0){
                 d.onDeath(a);
             }else{
                 d.setHealth(d.getHealth()-m);
             }
         }
        }
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

}
