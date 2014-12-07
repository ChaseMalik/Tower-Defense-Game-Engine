package utilities.GSON;
import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.levels.BaseLevel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utilities.errorPopup.ErrorPopup;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class GSONFileReader {



	GsonBuilder gson;
    private List<TowerUpgradeGroup> towerList;
    private List<BaseLevel> levelList;
    private boolean[][] locations;
    private GameStateWrapper gameState;

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
       
    public List<DataWrapper> readWrappers(String towers){
    	
    	return gson.create().fromJson(towers, new TypeToken<List<DataWrapper>>() {}.getType());
    
    }
    
    public boolean[][] readTowerRegionsFromGameDirectory(String gameDir){
        try{            
            BufferedReader br = new BufferedReader(new FileReader(gameDir + "locations.json"));        
            locations = gson.create().fromJson(br, new boolean[0][0].getClass());  
        } catch(IOException e){
            new ErrorPopup("File" + gameDir + "locations.json could not be found.");
        }               

        return locations;
    }
    
    public GameStateWrapper readGameStateFromJSon(String directory){
    	
        try{            
            BufferedReader br = new BufferedReader(new FileReader(directory + "gameState.json"));   
            gameState = gson.create().fromJson(br, GameStateWrapper.class);
        } catch(IOException e){
            new ErrorPopup("File could not be found.");
        }   
    	
        return gameState;
    }
    
}
