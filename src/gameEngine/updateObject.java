package gameEngine;


public abstract class updateObject {
    protected int myValue;
    public updateObject(int value){
        myValue=value;
    }
    public abstract void update(UpdateInterface manager);
}
