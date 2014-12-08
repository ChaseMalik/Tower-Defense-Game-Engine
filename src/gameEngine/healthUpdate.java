package gameEngine;


public class healthUpdate extends updateObject {

    public healthUpdate (double value) {
        super(value);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update (UpdateInterface manager) {
        // TODO Auto-generated method stub
        manager.setMyHealth(myValue+manager.getMyHealth());

    }

}
