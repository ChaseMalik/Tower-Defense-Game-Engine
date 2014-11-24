package utilities.GSON;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import utilities.errorPopup.ErrorPopup;
import com.google.gson.Gson;

public class GSONFileWriter {

    private Gson gson = new Gson();

    public void writeGameFile (List<BaseEnemy> enemies,
                               List<TowerUpgradeGroup> towerGroups,
                               List<BaseLevel> levels,
                               List<BackendRoute> backendRoutes,
                               String directory) {
        writeToFile("enemies", gson.toJson(enemies), directory);
        writeToFile("towers", gson.toJson(towerGroups), directory);
        writeToFile("levels", gson.toJson(levels), directory);    
        writeToFile("routes", gson.toJson(backendRoutes), directory);
    }

    private void writeToFile(String fileName, String json, String directory) {
        try{
            File file = new File(directory + fileName +".json");
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();				
        }catch(IOException e) {
            e.printStackTrace();
            new ErrorPopup("File to store actors could not be found.");
        }
    }
}