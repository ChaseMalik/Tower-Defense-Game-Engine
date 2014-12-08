package gameEngine;


public abstract class updateObject {
    protected double myValue;
    public updateObject(double value){
        myValue=value;
    }
    public abstract void update(UpdateInterface manager);
}
