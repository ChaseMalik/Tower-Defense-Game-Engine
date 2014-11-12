package gamePlayer.utilityFunctions;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileLoader  {
    private static FileLoader myReference = null;
    private FileLoader() {
    }
    
    public static FileLoader getInstance() {
        if (myReference==null) {
            myReference = new FileLoader();
        }
        return myReference;
    }
    
    public File load(Window stage) {
        FileChooser chooser = new FileChooser();
        return chooser.showOpenDialog(stage);
    }
}
