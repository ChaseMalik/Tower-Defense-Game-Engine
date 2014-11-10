package Utilities;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class DragAndDropFilePane extends Pane {
    
    private String[] myValidExtensions;
    private String myFileDestination;

    /**
     * 
     * @param validExtensions The extensions you will accept for the drag and dropped file. Ex: [.jpg, .png]
     * @param fileDestination where you want the file dragged and dropped to be saved.
     */
    public DragAndDropFilePane(double width, double height, String[] validExtensions, String fileDestination) {
        myValidExtensions = validExtensions;
        myFileDestination = fileDestination;
        setOnDragOver(event->handleDragOver(event));
        setOnDragExited(event->handleDragExit(event));
        setOnDragDropped(event->handleFileDrop(event));
    }

    private void handleFileDrop (DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            for (File file : db.getFiles()) {
                String fileName = file.toPath().toString();
                for(String extension:myValidExtensions){
                    if(fileName.toLowerCase().contains(extension)){
                        File targetFile = new File(myFileDestination);
                        try {
                            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
                        }
                        catch (IOException e) {
                            new ErrorPopup("Invalid file");
                        }
                        break;
                    }
                }
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }



    private void handleDragExit (DragEvent event) {
        setStyle("-fx-background-color: white;");
        event.consume();
    }


    private void handleDragOver (DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
            setStyle("-fx-background-color: lightgreen");
        }
        else {
            event.consume();
        }
    }
}