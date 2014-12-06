package utilities.GSON;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.levels.BaseLevel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Point2D;
import utilities.errorPopup.ErrorPopup;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileWriter {

    private GsonBuilder gson = new GsonBuilder();

    public void writeGameFile (List<TowerUpgradeGroup> towerGroups,
                               List<BaseLevel> levels,
                               String directory) {

    	gson.registerTypeAdapter(IBehavior.class, new IBehaviorClassAdapter());
        writeToFile(directory + "towers.json", gson.create().toJson(towerGroups, new TypeToken<List<TowerUpgradeGroup>>() {}.getType()));
        writeToFile(directory + "levels.json", gson.create().toJson(levels, new TypeToken<List<BaseLevel>>() {}.getType()));
    }
    
    public void writeTowerRegions(String directory, boolean[][] validRegions){
        writeToFile(directory + "locations.json", gson.create().toJson(validRegions, validRegions.getClass()));
    }
    
    public void writeToFile(String fileName, String json) {
        try{
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();				
        }catch(IOException e) {
            new ErrorPopup("File to store actors could not be found.");
        }
    }
    
    public String convertActorsToJson(Iterator<BaseTower> towerList){
    	
    	List<DataWrapper> wrappedTowerList = new ArrayList<DataWrapper>();
    	
    	while(towerList.hasNext()){
    	    BaseTower tower = towerList.next();
    		DataWrapper dw = new DataWrapper(tower, tower.getNode().getX(), tower.getNode().getY());
   		
    		wrappedTowerList.add(dw);  		 		
    	}
    	
    	return gson.create().toJson(wrappedTowerList,new TypeToken<List<DataWrapper>>() {}.getType());
    	
    }
    

    
}