package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

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
    
    public abstract void during (BaseActor actor);

    public abstract void start (BaseActor actor);
   
    public abstract void end(BaseActor actor);    
    

    @Override
    public String toString(){
        return myString;
    }

}
