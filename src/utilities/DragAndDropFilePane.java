package utilities;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Observable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 * This utility allows for the creation of drag and drop file panes. Simply specify the extensions of files
 * you accept, and where you want the file to be dropped. The class extends Observable so you can
 * set up an observer class to listen for when a file is dropped.
 * @author Austin Kyker
 *
 */
public class DragAndDropFilePane extends Observable {
    
    private String[] myValidExtensions;
    private String myFileDestination;
    private Pane myPane;
    private File myFile;

    /**
     * @param validExtensions The extensions you will accept for the drag and dropped file. Ex: [.jpg, .png]
     * @param fileDestination where you want the file dragged and dropped to be saved.
     */
    public DragAndDropFilePane(double width, double height, String[] validExtensions, String fileDestination) {
        myValidExtensions = validExtensions;
        myFileDestination = fileDestination;
        myPane = new Pane();
        myPane.setPrefSize(width, height);
        myPane.setOnDragOver(event->handleDragOver(event));
        myPane.setOnDragExited(event->handleDragExit(event));
        myPane.setOnDragDropped(event->handleFileDrop(event));
        showInstructionLabel(width, height);
    }

    private void showInstructionLabel (double width, double height) {
        Label label = new Label("Drag and Drop Map Image");
        label.setLayoutX(width/2 - 70);
        label.setLayoutY(height/2-20);
        myPane.getChildren().add(label);
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
                        File targetFile = new File(myFileDestination + file.getName().toString());
                        try {
                            myFile = file;
                            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
                            this.setChanged();
                            this.notifyObservers();
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
        myPane.setStyle("-fx-background-color: white;");
        event.consume();
    }


    private void handleDragOver (DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
            myPane.setStyle("-fx-background-color: lightgreen");
        }
        else {
            event.consume();
        }
    }

    public Node getPane () {
        return myPane;
    }

    public FileInputStream getImageStream () {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(myFile);
        }
        catch (FileNotFoundException e) {
            new ErrorPopup("File was not found");
        }
        return stream;
    }
}