package utilities.DragAndDropFilePanes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Observable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import utilities.StringToImageViewConverter;
import utilities.errorPopup.ErrorPopup;

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
    private Pane myContainer;
    private Pane myDragAndDropPane;
    private File myFile;
    private ImageView myImageView;

    /**
     * @param validExtensions The extensions you will accept for the drag and dropped file. Ex: [.jpg, .png]
     * @param fileDestination where you want the file dragged and dropped to be saved.
     */
    public DragAndDropFilePane(double width, double height, String[] validExtensions, String fileDestination) {
        myValidExtensions = validExtensions;
        myFileDestination = fileDestination;
        myContainer = new Pane();
        myDragAndDropPane = new Pane();
        myDragAndDropPane.setPrefSize(width, height);
        myDragAndDropPane.setOnDragOver(event->handleDragOver(event));
        myDragAndDropPane.setOnDragExited(event->handleDragExit(event));
        myDragAndDropPane.setOnDragDropped(event->handleFileDrop(event));
        showInstructionLabel(width, height);
        myContainer.getChildren().add(myDragAndDropPane);
    }

    private void showInstructionLabel (double width, double height) {
        Label label = new Label("Drag and Drop Map Image");
        label.setLayoutX(width/2 - 70);
        label.setLayoutY(height/2-20);
        myDragAndDropPane.getChildren().add(label);
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
                            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
                            myFile = targetFile;
                            drawImage();
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



    private void drawImage () {
        myContainer.getChildren().remove(myDragAndDropPane);
        myImageView = StringToImageViewConverter.getImageView(myDragAndDropPane.getWidth(), 
                                                                      myDragAndDropPane.getHeight(), 
                                                                      myFile.getAbsolutePath());
        myContainer.getChildren().add(myImageView); 
        this.setChanged();
        this.notifyObservers(myFile.getAbsolutePath());               
    }

    private void handleDragExit (DragEvent event) {
        myDragAndDropPane.setStyle("-fx-background-color: white;");
        event.consume();
    }


    private void handleDragOver (DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
            myDragAndDropPane.setStyle("-fx-background-color: lightgreen");
        }
        else {
            event.consume();
        }
    }

    public Pane getPane () {
        return myContainer;
    }

    public ImageView getImageView () {
        return myImageView;
    }
    
    public String getImagePath () {
        return myFile.getAbsolutePath();
    }

    public void setHeight (double height) {
        myImageView.setPreserveRatio(true);
        myImageView.setFitHeight(height);
        myImageView.autosize();
        myContainer.setPrefHeight(height);
        
    }

    public void reset () {
        myContainer.getChildren().clear();
        myContainer.getChildren().add(myDragAndDropPane);
        
    }
}