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
    private List<DataWrapper> wrappedEnemies;
    private List<DataWrapper> wrappedTowers;

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
        List<BaseTower> list = new ArrayList<>();
        for(TowerUpgradeGroup g:towerList){
            for(BaseTower t: g){
                list.add(t);
            }
        }
        GSONFileWriter g = new GSONFileWriter();
        g.convertActorsToJson(list);
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
    
   
    public List<BaseEnemy> readEnemies(String gameDir){
    	
    	List<BaseEnemy> enemyList = new ArrayList<BaseEnemy>();
    	try{
    		BufferedReader br = new BufferedReader(new FileReader(gameDir + "wrappedEnemies.json"));
    		wrappedEnemies = gson.create().fromJson(br, new TypeToken<List<BaseLevel>>() {}.getType());
    	}catch(IOException e){
    		new ErrorPopup("File" + gameDir + ".");
    	}
    	
	
    	for(DataWrapper enemy:wrappedEnemies){
    		
    		BaseEnemy newEnemy = (BaseEnemy)enemy.getActor();
    		
    		newEnemy.makeNode(enemy.getPoint());
    		enemyList.add(newEnemy); 		
    		
    	}
    	
    	return enemyList;
    }
    
    
    
    public List<BaseTower> readTower(String towers){
    	
    	List<BaseTower> towerList = new ArrayList<BaseTower>();
    	wrappedTowers = gson.create().fromJson(towers, new TypeToken<List<BaseLevel>>() {}.getType());
    	
	
    	for(DataWrapper tower:wrappedTowers){
    		
    		BaseTower newTower = (BaseTower)tower.getActor();
    		
    		newTower.makeNode(tower.getPoint());
    		towerList.add(newTower); 		
    		
    	}
    	
    	return towerList;
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
}
