package gamePlayer.mainClasses;

import gamePlayer.guiItemListenerInterfaces.VoogaMenuBarListenerInterface;
import java.io.File;
import javafx.stage.Stage;

/**
 * Listener class MUST implement all of the interfaces in the listener interfaces package
 * @author allankiplagat
 *
 */
public class GuiListener implements VoogaMenuBarListenerInterface  {

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
