package utilities.GSON;
import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.levels.BaseLevel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import utilities.IBehaviorClassAdapter;
import utilities.errorPopup.ErrorPopup;

import com.google.gson.Gson;
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

    public List<TowerUpgradeGroup> readTowerFromFile(String fileName){
        try{		
            BufferedReader br = new BufferedReader( new FileReader(fileName+".json"));	
            towerList = gson.create().fromJson(br, new TypeToken<List<TowerUpgradeGroup>>() {}.getType() );	
        } catch(IOException e){
            new ErrorPopup("File" +fileName+ ".json could not be found.");
        }		
        return towerList;		
    }


    public List<BaseLevel> readLevelfromFile(String fileName){
        try{		
            BufferedReader br = new BufferedReader( new FileReader(fileName+".json"));        
            levelList = gson.create().fromJson(br, new TypeToken<List<BaseLevel>>() {}.getType());  
        } catch(IOException e){
            new ErrorPopup("File" +fileName+ ".json could not be found.");
        }		

        return levelList;
    }
}
