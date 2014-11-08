package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.Path;
import javafx.scene.Group;

public class SelectComponentPane extends BuildingPane {
    
    private Path myPath;
    private double mouseX;
    private double mouseY;

    public SelectComponentPane (Group group, Path path) {
        super(group);
        myPath = path;
    }

}

//tempLine.setOnMousePressed(new EventHandler<MouseEvent>(){
//
//    @Override
//    public void handle (MouseEvent event) {
//        if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
//            mouseX = event.getSceneX();
//            mouseY = event.getSceneY();   
//            myPath.setSelectedComponent(tempLine);
//        }
//    }
//
//});
//tempLine.setOnMouseDragged(new EventHandler<MouseEvent>(){
//    @Override
//    public void handle (MouseEvent event) {
//        if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
//            double deltaX = event.getSceneX() - mouseX;
//            double deltaY = event.getSceneY() - mouseY;
//            myPath.moveConnectedComponent(tempLine, deltaX, deltaY);
//            mouseX = event.getSceneX();
//            mouseY = event.getSceneY(); 
//        }                           
//    }                      
//});
//tempLine.setOnMouseReleased(new EventHandler<MouseEvent>(){
//
//    @Override
//    public void handle (MouseEvent event) {
//        if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
//            myPath.tryToConnectComponents(tempLine);
//        }
//    }
//});