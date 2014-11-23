package gameEngine.tests;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineConcurrencyTester extends Application{

    @Override
    public void start (Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group, 500, 500);
        Loop loop = new Loop();
        EventHandler<KeyEvent> keyPressedHandler = new EventHandler<KeyEvent>() {

            @Override
            public void handle (KeyEvent event) {
                loop.addString("outside of loop");
                //loop.clear();
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressedHandler);
        EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle (MouseEvent event) {
                loop.clear();
            }
        };
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mousePressedHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
    
    private class Loop {
        
        private Timeline myTimeline;
        private static final double FPS = 30;
        private List<String> myPrintoutString;
        private List<String> myStringToAdd;
        
        public Loop(){
            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

                @Override
                public void handle (ActionEvent event) {
                    //extractString();
                    for(int i=0; i < 10000000; i++ ){
                        myPrintoutString.add("fff");
                    }
                }
            };
            KeyFrame frame = new KeyFrame(Duration.millis(1000.0/FPS), eventHandler);
            myTimeline = new Timeline(30, frame);
            myTimeline.setCycleCount(Timeline.INDEFINITE);
            myStringToAdd = new ArrayList<>();
            myPrintoutString = new ArrayList<>();
            myPrintoutString.add("f");
            myTimeline.play();
        }
        
        public synchronized void extractString(){
            myPrintoutString.addAll(myStringToAdd);
        }
        
        public synchronized void addString(String word){
            System.out.println(myPrintoutString.size());
            myPrintoutString.add(word);
            System.out.println(myPrintoutString.size());
        }
        
        public void clear(){
            myPrintoutString.clear();
            System.out.println(myPrintoutString.size());
        }
    }
}
