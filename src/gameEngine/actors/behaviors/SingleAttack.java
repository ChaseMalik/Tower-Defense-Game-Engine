package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.ProjectileInfo;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * Attack behavior that produces one projectile when it is able to attack and it shoots it
 * at the location of an enemy within its range 
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class SingleAttack extends BaseAttackBehavior{

    public SingleAttack (int attackSpeed, double range, ProjectileInfo projectile) {
        super(attackSpeed, range, projectile);

    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        Circle range=new Circle();
        range.setCenterX(actor.getX());
        range.setCenterY(actor.getY());
        range.setRadius(myRange);
        Group enemies=(Group)((Group)actor.getParent().getParent()).getChildren().get(0);
        Group projectiles=(Group)((Group)actor.getParent().getParent()).getChildren().get(1);
        for(Node n: enemies.getChildren()){
            if(range.intersects(n.getBoundsInLocal()) && myCooldown==0){
                BackendRoute route=new BackendRoute(new Point2D(actor.getX(), actor.getY()), new Point2D(((ImageView) n).getX(),((ImageView) n).getY())); 
                List<BackendRoute> list = Arrays.asList(route);
                LinearMovement move=new LinearMovement(list, myProjectileInfo.getMySpeed()); 
                projectiles.getChildren().add(new BaseProjectile(myProjectileInfo, move));
                myCooldown=myAttackSpeed;
                return;
            }
        }       
    }

    @Override
    public IBehavior copy () {
        return new SingleAttack(myAttackSpeed,myRange,myProjectileInfo);
    }

}
