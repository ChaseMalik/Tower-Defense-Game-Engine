package utilities.JavaFXutilities.DragAndDropFilePanes;

import java.io.File;

import utilities.JavaFXutilities.StringToAudioClipConverter;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.media.AudioTrack;

public abstract class DragAndDropAudioPane extends DragAndDropFilePane {

    protected Node myAudioClip;
    protected File myFile;

    public DragAndDropAudioPane (double width, double height) {
        super(width, height, new String[] {".mp3", ".mp4"});
    }

    //    public DragAndDropAudioPane (int cycleCount, int priority, double balance, double pan, double rate, double volume, String[] validExtensions) {
    //        myCycleCount = cycleCount;
    //        myPriority = priority;
    //        myBalance = balance;
    //        myPan = pan;
    //        myRate = rate;
    //        myVolume = volume;
    //        myValidExtensions = new String[] {".mp3", ".mp4"};
    //    }

    //    public ImageView getImageView () {
    //        return myImageView;
    //    }

    //    public ImageView getImageView () {
    //        return myImageView;
    //    }

    public String getAudioPath () {
        return myFile.getPath();
    }

    //    public void setHeight (double height) {
    //        myImageView.setPreserveRatio(true);
    //        myImageView.setFitHeight(height);
    //        myImageView.autosize();
    //        myContainer.setPrefHeight(height);       
    //    }

    //        public void displayImage() {
    //            myContainer.getChildren().remove(myDragAndDropPane);
    //            myImageView = StringToImageViewConverter.getImageView(myDragAndDropPane.getWidth(), 
    //                                                                          myDragAndDropPane.getHeight(), 
    //                                                                          myFile.getPath());
    //            myContainer.getChildren().add(myImageView); 
    //            this.setChanged();
    //            this.notifyObservers(myFile.getPath());
    //        }

//    public void playAudio () {
//        myContainer.getChildren().remove(myDragAndDropPane);
//        myAudioClip = StringToAudioClipConverter.getAudioClip(myDragAndDropPane.getWidth(), 
//                myDragAndDropPane.getHeight(), 
//                myFile.getPath());
//        myContainer.getChildren().add(myAudioClip); 
//        this.setChanged();
//        this.notifyObservers(myFile.getPath());
//    }
}
