package gameEngine;

import gameEngine.actors.behaviors.updateInterface;


public class goldUpdate extends updateObject {

    public goldUpdate (int value) {
        super(value);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update (updateInterface manager) {
        // TODO Auto-generated method stub
        manager.setMyGold(myValue+manager.getMyGold());
    }

}
