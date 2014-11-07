package gameAuthoring.control;

import javafx.application.Application;
import javafx.stage.Stage;

public class AuthorControl extends Application {

    public static final double SCREEN_WIDTH = 700;
    public static final double SCREEN_HEIGHT = 600;
    private Stage myStage;
    
    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        StageInitializer.setupStage(myStage);
    }
}
