package gameAuthoring.scenes.actorBuildingScenes;

import java.util.Observable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class FileMenu extends Observable {
    
    private MenuBar myFileMenu;
    
    public FileMenu() {
        myFileMenu = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem finishedBuildingItem = new MenuItem("Finished");
        finishedBuildingItem.setOnAction(event->handleClick());
        menu.getItems().add(finishedBuildingItem);
        myFileMenu.getMenus().add(menu);
    }

    private void handleClick () {
        this.setChanged();
        this.notifyObservers();
    }

    public MenuBar getNode () {
        return myFileMenu;
    }
}
