package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public abstract class BaseOnHitBehavior implements IBehavior{
    double myDuration;
    double myInitialDuration;
    double myMultiplier;
    protected String myString;
    
    public BaseOnHitBehavior(double duration, double multiplier){
        myDuration=duration;
        myInitialDuration=duration;
        myMultiplier=multiplier;
    }

    public BaseOnHitBehavior(double multiplier){
        this(30.0, multiplier);
    }
    
    public BaseOnHitBehavior (List<Double> list) {
        // TODO Auto-generated constructor stub
    }

    public void execute(BaseActor actor){
        if(myDuration==myInitialDuration)
            start(actor);
        
        during(actor);
        
        if(myDuration==0)
            end(actor);

        myDuration--;
    }
    
    public abstract void during (BaseActor actor);

    public abstract void start (BaseActor actor);
   
    public abstract void end(BaseActor actor);    
    

    @Override
    public String toString(){
        return myString;
    }

}
