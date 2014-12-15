// This entire file is part of my masterpiece.
// Austin Kyker

package utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes;

import gameAuthoring.mainclasses.Constants;
import java.io.File;
import java.util.Observable;
import utilities.multilanguage.MultiLanguageUtility;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * This utility allows for the creation of drag and drop file panes.
 * Simply specify the extensions of files you accept, and where you want
 * the file to be dropped. The class extends Observable so you can
 * set up an observer class to listen for when a file is dropped.
 * 
 * @author Austin Kyker
 *
 */
public abstract class DragAndDropFilePane extends Observable {

    private static final int LAYOUTX_ADJ = 50;
    private static final int LAYOUTY_ADJ = 20;

    private String[] myValidExtensions;
    protected VBox myContainer;
    protected VBox myDragAndDropPane;
    protected File myFile;

    /**
     * @param validExtensions The acceptable extensions drag and dropped file. Ex: [.jpg, .png]
     * @param fileDestination where you want the file dragged and dropped to be saved.
     */
    public DragAndDropFilePane (double width, double height, String[] validExtensions) {
        myValidExtensions = validExtensions;
        myContainer = new VBox();
        myContainer.setAlignment(Pos.CENTER);
        myDragAndDropPane = new VBox();
        myDragAndDropPane.setPrefSize(width, height);
        myDragAndDropPane.setOnDragOver(event -> handleDragOver(event));
        myDragAndDropPane.setOnDragExited(event -> handleDragExit(event));
        myDragAndDropPane.setOnDragDropped(event -> handleFileDrop(event));
        showInstructionLabel(width, height);
        myContainer.getChildren().add(myDragAndDropPane);
    }

    private void showInstructionLabel (double width, double height) {
        Pane labelPane = new Pane();
        Label label = new Label();
        label.textProperty().bind(MultiLanguageUtility.getInstance()
                                          .getStringProperty(Constants.DRAG_AND_DROP));
        label.setLayoutX(width / 2 - LAYOUTX_ADJ);
        label.setLayoutY(height / 2 - LAYOUTY_ADJ);
        labelPane.getChildren().add(label);
        myDragAndDropPane.getChildren().add(labelPane);
    }

    private void handleFileDrop (DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            for (File file : db.getFiles()) {
                String fileName = file.getPath();
                for (String extension : myValidExtensions) {
                    if (fileName.toLowerCase().contains(extension)) {
                        actOnFile(file);
                        break;
                    }
                }
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Subclasses will be able to implement a method that is called when
     * the user drags and drops a file (e.g. copy file to specified directory).
     * @param file
     */
    protected abstract void actOnFile (File file);

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

    public void reset () {
        myContainer.getChildren().clear();
        myContainer.getChildren().add(myDragAndDropPane);
    }
}