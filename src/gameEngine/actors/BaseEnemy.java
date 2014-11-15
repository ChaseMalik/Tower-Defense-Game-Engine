package gameEngine.actors;

import gameEngine.actors.behaviors.IAttack;
import gameEngine.actors.behaviors.IDefend;
import gameEngine.actors.behaviors.IMove;
import gameEngine.actors.behaviors.ISpawn;
import gameEngine.actors.behaviors.ITerminate;

/**
 * @author $cotty $haw
 *
 */
public class BaseEnemy {

    private IMove myMove;
    private IAttack myAttack;
    private IDefend myDefend;
    private ITerminate myTerminate;

    public BaseEnemy (IMove move, IAttack attack, IDefend defend, ITerminate terminate) {
        myMove = move;
        myAttack = attack;
        myDefend = defend;
        myTerminate = terminate;
    }

    public void update () {
        myMove.move();
        myAttack.attack();
        myDefend.defend();
    }
}
    