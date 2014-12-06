package gamePlayer.mainClasses.welcomeScreen;

import java.io.File;
import utilities.XMLParsing.XMLParser;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.welcomeScreen.screens.MainSplashScreen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());
        
        loadMainSplashScreen(group);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void loadMainSplashScreen (Group group) {
        MainSplashScreen mainScreen = new MainSplashScreen();
        group.getChildren().add(mainScreen);
    }
}
