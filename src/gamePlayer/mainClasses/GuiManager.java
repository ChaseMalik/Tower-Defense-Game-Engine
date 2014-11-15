package gamePlayer.mainClasses;

import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import java.io.File;
import javafx.stage.Stage;

/**
 * GuiManager class MUST implement ALL of the interfaces in the guiItemsListeners package
 * @author allankiplagat
 *
 */
public class GuiManager implements VoogaMenuBarListener  {
    private DummyGameManager myGameManager;
    private Stage myStage; 
    
    public GuiManager(DummyGameManager manager) {
        myGameManager = manager;
    }
    
    public void setStage(Stage stage) {
        myStage = stage;
    }
    
    @Override
    public void loadGame () {
        File file = FileLoader.getInstance().load(myStage);
        System.out.println(file.getAbsolutePath()+"\n");
    }

    @Override
    public void saveGame () {
        System.out.println("Save game");
    }
    
}
