package utilities;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class GSONWriteToFile {


	public void writeEnemiesToFile(String filename){


		Gson gson = new Gson();
		String json = gson.toJson(enemyData);

		try{
			FileWriter writer = new FileWriter("src/files/" + filename +".json");
			writer.write(json);
			writer.close();				
		}catch(IOException e){
			e.printStackTrace();
		}
	}



}
