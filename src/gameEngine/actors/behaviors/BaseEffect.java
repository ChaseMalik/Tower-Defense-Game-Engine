package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;
/**
 *  Behavior that defines the effects an actor can have
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseEffect implements IBehavior{
    protected List<Double> myList;
    double myDuration;
    double myInitialDuration;
    protected String myString;
    
    public BaseEffect(double duration, double multiplier){
        myDuration=duration;
        myInitialDuration=duration;
    }

    public BaseEffect(){

    }
    
    public BaseEffect (List<Double> list) {
        // TODO Auto-generated constructor stub
        myList=list;
        myDuration=list.get(0);
        myInitialDuration=myDuration;
    }

    public void execute(BaseActor actor){

        if(myDuration==myInitialDuration)
            start(actor);
        
        during(actor);
        
        if(myDuration<=0)
            end(actor);

        myDuration--;
    }
    /*
     * what to do while timer is between start and end
     */
    public abstract void during (BaseActor actor);
    /*
     * what to do on start of effect
     */
    public abstract void start (BaseActor actor);
    /*
     * what to do at end of effect
     */
    public abstract void end(BaseActor actor);    
    

    @Override
    public String toString(){
        return myString;
    }

}
