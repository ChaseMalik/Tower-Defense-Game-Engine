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
public class BaseBaddie {

    private ISpawn mySpawn;
    private IMove myMove;
    private IAttack myAttack;
    private IDefend myDefend;
    private ITerminate myTerminate;

    public BaseBaddie (ISpawn spawn, IMove move, IAttack attack, IDefend defend, ITerminate terminate) {
        mySpawn = spawn;
        myMove = move;
        myAttack = attack;
        myDefend = defend;
        myTerminate = terminate;
    }

    public void update () {
        mySpawn.spawn();
        myMove.move();
        myAttack.attack();
        myDefend.defend();
        myTerminate.terminate(); //onDeath()?
    }

}
