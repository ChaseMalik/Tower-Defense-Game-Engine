package utilities.GSON;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.BaseTower;
import gameEngine.actors.ProjectileInfo;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.actors.behaviors.LinearMovement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.google.gson.Gson;

public class GSONTester {
	
	private static final String IMAGE_PATH = "./src/utilities/GSON/test.png";

	
	@Test
	public void testTowerGSONWriteAndRead(){
		
		Gson gson = new Gson();
		
		GSONFileWriter writer = new GSONFileWriter();
		GSONFileReader reader = new GSONFileReader();
		
		Map<String,IBehavior> map = new HashMap<String, IBehavior>();
		map.put("movement", new LinearMovement(5));
		
		ProjectileInfo info = new ProjectileInfo(IMAGE_PATH, new LinearMovement(5), null,new ArrayList<String>());
		
		BaseTower tower1 = new BaseTower(map, IMAGE_PATH,"b", 1.0, 200, 200, info);
		BaseTower tower2 = new BaseTower(map, IMAGE_PATH, "d", 2.0, 200, 200, info);
		BaseTower tower3 = new BaseTower(map, IMAGE_PATH, "d", 2.0, 200, 200, info);
		
		TowerUpgradeGroup group1 = new TowerUpgradeGroup(tower1);
		group1.addTower(tower2);
		TowerUpgradeGroup group2 = new TowerUpgradeGroup(tower3);	
		ArrayList<TowerUpgradeGroup> towerTestList= new ArrayList<TowerUpgradeGroup>();
		
		towerTestList.add(group1);
		towerTestList.add(group2);
		
		//writer.writeToFile("tower", gson.toJson(towerTestList, new TypeToken<List>() {}.getType() ), "./Game/");
		
	}

}
