package utilities.GSON;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.levels.BaseLevel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
    

    
}