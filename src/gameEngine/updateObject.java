package gameEngine;

import gameEngine.actors.behaviors.updateInterface;

public abstract class updateObject {
    protected int myValue;
    public updateObject(int value){
        myValue=value;
    }
    public abstract void update(updateInterface manager);
}
