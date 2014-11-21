package utilities;



import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import utilities.errorPopup.ErrorPopup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GSONWriteToFile {

	private Gson gson = new Gson();
	private Type listType;

	public GSONWriteToFile(){
		listType = new TypeToken<List<BaseEnemy>>() {}.getType();
	}


	public void writeEnemiesToFile(List<BaseEnemy> enemies, String fileName){
		String json = gson.toJson(enemies, listType);
		writeToFile(fileName, json);
	}

	public void writeTowersToFile(List<BaseTower> towers, String fileName){
		String json = gson.toJson(towers, listType);
		writeToFile(fileName, json);
	}

	public void writeActorsToFile(List<BaseActor> actors, String fileName){
		String json = gson.toJson(actors,listType);
		writeToFile(fileName, json);
	}
	

	private void writeToFile(String fileName, String json) {
		try{
			FileWriter writer = new FileWriter("./src/files/" + fileName +".json");
			writer.write(json);
			writer.close();				
		}catch(IOException e){
			new ErrorPopup("File to store actors could not be found.");
		}
	}
}
