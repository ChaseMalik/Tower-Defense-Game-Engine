package gamePlayer.guiItemsListeners;

import java.io.File;
import javafx.stage.Stage;

public interface VoogaMenuBarListener {
    public void readLoadedFile(File file);
    public Stage getStage();
}
