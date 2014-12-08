package gamePlayer.mainClasses.guiBuilder;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.managers.WelcomeManager;
import gamePlayer.mainClasses.welcomeScreen.GameStartManager;
import utilities.textGenerator.TextGenerator;

/**
 * Class holds GUI constants that GUI elements refer to during initialization and GamePlay
 * @author allankiplagat
 *
 */
public class GuiConstants {
    public static GuiManager GUI_MANAGER;
    public static WelcomeManager WELCOME_MANAGER;
    public static TextGenerator TEXT_GEN;
    public static String GUI_ELEMENT_PROPERTIES_PATH;
    public static GameStartManager GAME_START_MANAGER; 
    public static Boolean DYNAMIC_SIZING;
    
    //Game start manager constants
    public static final String SINGLE_PLAYER_GAME = "SinglePlayer";
    public static final String MULTI_PLAYER_GAME = "MultiPlayer";
    
    //game location paths
    public static final String SINGLE_PLAYER_GAMES_DIRECTORY = "./Games/";
    public static final String MULTI_PLAYER_GAMES_DIRECTORY = "./CoopGames/";
    
    //core container sizes
    public static double CENTER_CONTAINER_WIDTH = BuildingPane.DRAW_SCREEN_WIDTH;
    public static double CENTER_CONTAINER_HEIGHT = AuthorController.SCREEN_HEIGHT;
    
    public static double RIGHT_CONTAINER_WIDTH = 150;//
    public static double RIGHT_CONTAINER_HEIGHT = CENTER_CONTAINER_HEIGHT;//
    
    public static double LEFT_CONTAINER_WIDTH = 0; //
    public static double LEFT_CONTAINER_HEIGHT = CENTER_CONTAINER_HEIGHT; //
    
    public static double TOP_CONTAINER_WIDTH = LEFT_CONTAINER_WIDTH+CENTER_CONTAINER_WIDTH+RIGHT_CONTAINER_WIDTH;
    public static double TOP_CONTAINER_HEIGHT = 30;
    
    public static double BOTTOM_CONTAINER_WIDTH = TOP_CONTAINER_WIDTH;
    public static double BOTTOM_CONTAINER_HEIGHT = 80;
    
    //window size
    public static double WINDOW_WIDTH = TOP_CONTAINER_WIDTH;
    public static double WINDOW_HEIGHT = TOP_CONTAINER_HEIGHT+CENTER_CONTAINER_HEIGHT+BOTTOM_CONTAINER_HEIGHT;
           
}
