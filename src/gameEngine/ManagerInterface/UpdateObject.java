package gameEngine.ManagerInterface;


public abstract class UpdateObject {
    protected double myValue;
    public UpdateObject(double value){
        myValue=value;
    }
    public abstract void update(UpdateInterface manager);
}
