package gameAuthoring.factory;

import gameAuthoring.actorData.ActorData;

/**
 * A data factory to produce 
 * @author Austin Kyker
 *
 */
public class SingletonDataFactory {
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
    public static ActorData buildActorDataInstance(String actorType)  {
        try{
            return (ActorData) Class.forName(actorType.concat(DATA)).getConstructor().newInstance();
        }
        catch(Exception E) {}
        return null;
    }
}
