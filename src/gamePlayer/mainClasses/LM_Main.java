package gamePlayer.mainClasses;

import gamePlayer.Listeners.LeapMotionListener;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.managers.WelcomeManager;
import javafx.application.Application;
import javafx.stage.Stage;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;

public class LM_Main extends Application {
	
	public static Controller LMController;
	public static Listener LMListener;
    
    public static void main (String[] args) {
    	LMController = new Controller();
		LMListener = new LeapMotionListener();
		LMController.addListener(LMListener);
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        //builder will set up the application using the stage and given properties
    	WelcomeManager welcomeManager = new WelcomeManager(stage);
    	new GuiManager(stage);
    	welcomeManager.init();
    }
}
