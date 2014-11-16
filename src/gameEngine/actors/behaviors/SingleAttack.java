package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.ProjectileInfo;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

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
                LinearMovement move=new LinearMovement(route, myBullet.getMySpeed()); 
                projectiles.getChildren().add(new BaseProjectile(myBullet, move));
                myCooldown=myAttSpeed;
                return;
            }
        }
         
    }

}
