package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.welcomeScreen.screens.WelcomeScreen;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import utilities.XMLParsing.XMLParser;

public class WelcomeScreenManager {
    
    public static final String propertiesPath = "./src/gamePlayer/properties/WelcomeScreenProperties.XML";
    private XMLParser myParser;
    
    public WelcomeScreenManager(Stage stage) {
            myParser = new XMLParser(new File(propertiesPath));
            init(stage);
            //startGame(stage);
    }
       
    
    private void startGame(Stage stage) {
        new GuiManager(stage);
        GuiConstants.GUI_MANAGER.init();
    }
    
    private void init(Stage stage) {
        Group group  = new Group();
        Scene scene = new Scene(group,GuiConstants.WINDOW_WIDTH,GuiConstants.WINDOW_HEIGHT);
    
        String styleSheetPath = myParser.getValuesFromTag("StyleSheet").get(0);
        scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());
        
        initializeWelcomeScreen(group);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initializeWelcomeScreen (Group group) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setBackgroundImage(myParser.getValuesFromTag("BackgroundImage").get(0));
        welcomeScreen.setCenterContent(new Circle(0,100,100));
        group.getChildren().add(welcomeScreen);
    }
}
