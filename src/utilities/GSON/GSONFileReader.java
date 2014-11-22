package utilities.GSON;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

import utilities.errorPopup.ErrorPopup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class GSONFileReader {

	Gson gson = new Gson();
	Type listType;
	private List<BaseEnemy> enemyList;
	private List<BaseTower> towerList;
	private List<BaseActor> actorList;

	public GSONFileReader(){
		listType = new TypeToken<List<BaseEnemy>>() {}.getType();
	}

	public List<BaseEnemy> readEnemyFromFile(String fileName){
		try{		
			BufferedReader br = new BufferedReader( new FileReader("src/Files/" +fileName+"json"));	
			enemyList = gson.fromJson(br, listType );	
		} catch(IOException e){
			new ErrorPopup("File to store actors could not be found.");
		}		
		return enemyList;		
	}

	public List<BaseTower> readTowerFromFile(String fileName){
		try{		
			BufferedReader br = new BufferedReader( new FileReader("src/Files/" +fileName+"json"));	
			towerList = gson.fromJson(br, listType );	
		} catch(IOException e){
			new ErrorPopup("File to store actors could not be found.");
		}		
		return towerList;		
	}

	public List<BaseActor> readActorFromFile(String fileName){
		try{		
			BufferedReader br = new BufferedReader( new FileReader("src/Files/" +fileName+"json"));	
			actorList = gson.fromJson(br, listType );	
		} catch(IOException e){	
			new ErrorPopup("File to store actors could not be found.");
		}	
		return actorList;
	}
}
