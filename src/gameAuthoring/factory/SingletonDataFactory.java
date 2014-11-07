package gameAuthoring.factory;

import gameAuthoring.actorData.*;

/**
 * A data factory to produce 
 * @author Austin Kyker
 *
 */
public class SingletonDataFactory {
    private static final String DATA_CLASSES_PATH = "gameAuthoring.actorData.";
    private static final String DATA = "Data";
    private static SingletonDataFactory myFactoryInstance;

    private SingletonDataFactory(){}

    public static SingletonDataFactory getInstance() {
        if(myFactoryInstance == null) {
            myFactoryInstance = new SingletonDataFactory();
        }
        return myFactoryInstance;
    }

    /**
     * Builds an actor data instance. This will be called when the user
     * is creating enemies, towers, and levels.
     */
    public ActorData buildActorDataInstance(String actorType)  {
        try{
            return (ActorData) Class.forName(DATA_CLASSES_PATH + actorType + DATA).getConstructor().newInstance();
        }
        catch(Exception E) {  
            E.printStackTrace();
            return new NullData(); 
        }
    }
}
