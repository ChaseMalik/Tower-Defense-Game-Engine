package gameEngine;

import gameEngine.actors.BaseTower;
import java.util.ArrayList;
import java.util.List;
import utilities.GSON.DataWrapper;
import utilities.networking.HTTPConnection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CoOpManager extends SingleThreadedEngineManager {


    private static final String SERVER_URL = "https://voogasalad.herokuapp.com/";
    private static final HTTPConnection HTTP_CONNECTOR = new HTTPConnection(SERVER_URL);
    
    public CoOpManager (Pane engineGroup) {
        super(engineGroup);
    }

    private void writeTowersToServer () {
        String parameters = "master_json=" + convertTowersToString();
        System.out.println(myTowerGroup.getChildren().size());
        HTTP_CONNECTOR.sendPost("update_master_json", parameters);
    }
    
    private String convertTowersToString () {
        List<DataWrapper> wrapper = new ArrayList<>();
        for (BaseTower tower : myTowerGroup) {
            wrapper.add(new DataWrapper(tower));
        }
        return myFileWriter.convertWrappersToJson(wrapper);
    }

    private void getTowersFromServer () {
        List<DataWrapper> listFromServer =
                myFileReader.readWrappers(HTTP_CONNECTOR.sendGet("get_master_json"));
        if(listFromServer == null){
            return;
        }
        for(BaseTower tower: myTowerGroup){
            if(!listFromServer.contains(new DataWrapper(tower))){
                System.out.println("removing tower " + tower.toString() + " at " + tower.getX() + "," + tower.getY());
                myTowerGroup.addActorToRemoveBuffer(tower);
            }
            else{
                listFromServer.remove(new DataWrapper(tower));
            }
        }
        for(DataWrapper wrapper: listFromServer){
            System.out.println("adding tower " + wrapper.getName() + " at " + wrapper.getX() + "," + wrapper.getY());
            addTowerFromServer(wrapper);
        }
        
        System.out.println(myTowerGroup.getChildren().size());
    }
    @Override
    public void removeTower (ImageView node) {
        getTowersFromServer();
        super.removeTower(node);
        writeTowersToServer();
    }
    @Override
    public ImageView addTower(String name, double x, double y){
        getTowersFromServer();
        ImageView ans = super.addTower(name, x, y);
        writeTowersToServer();
        return ans;
    }

    private void addTowerFromServer(DataWrapper w){
        super.addTower(w.getName(), w.getX(), w.getY());
    }
    
    if (myUpdateServerTimer % 150 == 0) {
        getTowersFromServer();
    }
    myUpdateServerTimer++;
    
    
    
}
