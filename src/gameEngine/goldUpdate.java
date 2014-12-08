package gameEngine;



public class goldUpdate extends updateObject {

    public goldUpdate (double myGold) {
        super(myGold);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update (UpdateInterface manager) {
        // TODO Auto-generated method stub
        manager.setMyGold(myValue+manager.getMyGold());
    }

}
