package gameEngine;

import gameEngine.actors.behaviors.updateInterface;

public class healthUpdate extends updateObject {

    public healthUpdate (int value) {
        super(value);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update (updateInterface manager) {
        // TODO Auto-generated method stub
        manager.setMyHealth(myValue+manager.getMyHealth());

    }

}
