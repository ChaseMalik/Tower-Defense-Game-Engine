package gameAuthoring.actorDataManager;

import gameAuthoring.factory.SingletonDataFactory;

public abstract class ActorDataManager {
    
    private String myActorType;
    
    public ActorDataManager(String actorType){
        myActorType = actorType;
    }
    
    public void createData() {
        SingletonDataFactory.getInstance().buildActorDataInstance(myActorType);
    }
}
