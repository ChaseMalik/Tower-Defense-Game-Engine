package gamePlayer.mainClasses;

import gameEngine.GameManager;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import java.io.File;
import javafx.stage.Stage;

/**
 * GuiManager class MUST implement ALL of the interfaces in the guiItemsListeners package
 * @author allankiplagat
 *
 */
public class GuiManager implements VoogaMenuBarListener  {
    private GameManager myGameManager;
    
    public GuiManager(GameManager manager) {
        myGameManager = manager;
    }
    
    @Override
    public void readLoadedFile (File file) {
        System.out.println(file.getAbsolutePath()+"\n");
    }

    @Override
    public Stage getStage () {
        // TODO Return the actual stage
        return null;
    }
    
}
