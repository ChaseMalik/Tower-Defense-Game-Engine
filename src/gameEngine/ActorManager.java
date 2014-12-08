//package gameEngine;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import gameEngine.actors.BaseActor;
//import gameEngine.actors.BaseEnemy;
//import gameEngine.actors.BaseProjectile;
//import gameEngine.actors.BaseTower;
//import gameEngine.actors.InfoObject;
//import javafx.scene.layout.Pane;
//
//public class ActorManager {
//
//	public ActorManager(Pane engineGroup) {
//		
//	}
//	
//	private void updateActors(
//			RangeRestrictedCollection<? extends BaseActor> actorGroup) {
//		for (BaseActor actor : actorGroup) {
//			if (actor.isDead()) {
//				actorGroup.addActorToRemoveBuffer(actor);
//			} else {
//				InfoObject requiredInfo = getRequiredInformation(actor);
//				actor.update(requiredInfo);
//			}
//		}
//	}
//	
//	private InfoObject getRequiredInformation(BaseActor actor) {
//		Collection<Class<? extends BaseActor>> infoTypes = actor.getTypes();
//		List<BaseActor> enemyList = new ArrayList<>();
//		List<BaseActor> towerList = new ArrayList<>();
//		List<BaseActor> projectileList = new ArrayList<>();
//		for (Class<? extends BaseActor> infoType : infoTypes) {
//			if (BaseEnemy.class.isAssignableFrom(infoType)) {
//				enemyList = myEnemyGroup.getActorsInRange(actor);
//			}
//			if (BaseTower.class.isAssignableFrom(infoType)) {
//				towerList = myTowerGroup.getActorsInRange(actor);
//			}
//			if (BaseProjectile.class.isAssignableFrom(infoType)) {
//				projectileList = myProjectileGroup.getActorsInRange(actor);
//			}
//		}
//		return new InfoObject(enemyList, towerList, projectileList, myTowerLocationByGrid, myTowerTiles);
//	}
//}
