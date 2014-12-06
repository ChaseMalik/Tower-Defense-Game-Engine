package gamePlayer.guiFeatures;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Class loads directories
 * @author allankiplagat
 *
 */
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
    
    /**
     * @param stage the stage that directory loading is associated with
     * @return
     */
    public File load(Window stage) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File("./Games/"));
        return chooser.showDialog(stage);
    }
    
    /**
     * @param stage the stage that file loading is associated with
     * @param extensions to be filtered 
     * @return
     */
    public File load(Window stage, String fileType, String ... extensions) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter(fileType, extensions));
        return chooser.showOpenDialog(stage);
    }
}
