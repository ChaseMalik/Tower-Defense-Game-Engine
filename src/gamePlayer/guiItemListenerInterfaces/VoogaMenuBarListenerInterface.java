package gamePlayer.guiItemListenerInterfaces;

import java.io.File;
import javafx.stage.Stage;

public interface VoogaMenuBarListenerInterface {
    public void readLoadedFile(File file);
    public Stage getStage();
}
