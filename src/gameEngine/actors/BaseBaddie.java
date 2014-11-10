package gameEngine.actors;

import gameEngine.actors.behaviors.IAttack;
import gameEngine.actors.behaviors.IMove;
import gameEngine.actors.behaviors.ITerminate;


public class BaseBaddie {

    private IMove myMove;
    private IAttack myAttack;
    private ITerminate myTerminate;

    public BaseBaddie (IMove move, IAttack attack, ITerminate terminate) {
        myMove = move;
        myAttack = attack;
        myTerminate = terminate;
    }

    public void update () {
        myMove.move();
        myAttack.attack();
    }
}
