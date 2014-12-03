package utilities.GSON;
import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.levels.BaseLevel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import utilities.errorPopup.ErrorPopup;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class GSONFileReader {



	GsonBuilder gson;
    private List<TowerUpgradeGroup> towerList;
    private List<BaseLevel> levelList;

    public GSONFileReader(){
    	gson = new GsonBuilder();
    	gson.registerTypeAdapter(IBehavior.class, new IBehaviorClassAdapter());
    }

    public List<TowerUpgradeGroup> readTowersFromGameDirectory(String gameDir){
        try{		
            BufferedReader br = new BufferedReader(new FileReader(gameDir + "towers.json"));	
            towerList = gson.create().fromJson(br, new TypeToken<List<TowerUpgradeGroup>>() {}.getType() );	
        } catch(IOException e){
            new ErrorPopup("File" + gameDir + "towers.json could not be found.");
        }		
        return towerList;		
    }


    public List<BaseLevel> readLevelsFromGameDirectory(String gameDir){
        try{		
            BufferedReader br = new BufferedReader(new FileReader(gameDir + "levels.json"));        
            levelList = gson.create().fromJson(br, new TypeToken<List<BaseLevel>>() {}.getType());  
        } catch(IOException e){
            new ErrorPopup("File" + gameDir + "levels.json could not be found.");
        }		

        return levelList;
    }
}
