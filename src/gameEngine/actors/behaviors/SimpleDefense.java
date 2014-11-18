package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;

public class SimpleDefense extends BaseDefendBehavior{

    SimpleDefense (double health, List<String> harmfulBullets) {
        super(health, harmfulBullets);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        List<Node> enemies=((Group)actor.getParent()).getChildren();
        Group projectiles=(Group)((Group)actor.getParent().getParent()).getChildren().get(1);
        List<Node> projToRemove=new ArrayList<Node>();
        for(Node n: projectiles.getChildren()){
            if(actor.intersects(n.getBoundsInLocal()) && actor.isVisible()){
                myHealth-=((BaseProjectile) n).getInfo().getMyDamage();
                projToRemove.add(n);
            }
            if(myHealth<=0){
                enemies.remove(actor);
                break;
            }
        }
        for(Node n: projToRemove){
            projectiles.getChildren().remove(n);
        }
    }

    @Override
    public IBehavior copy () {
        return new SimpleDefense(myHealth, myHarmfulBullets);
    }

}
