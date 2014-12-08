package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WelcomeScreen extends AnchorPane {
	public static final double WIDTH = GuiConstants.WINDOW_WIDTH;
	public static final double HEIGHT = GuiConstants.WINDOW_HEIGHT;

	public static final double VBOX_WIDTH = WIDTH / 2.5;
	public static final double VBOX_HEIGHT = HEIGHT;
	private static final double VBOX_PADDING = 20;

	public static final double PANE_WIDTH = VBOX_WIDTH;
	public static final double PANE_HEIGHT = VBOX_HEIGHT / 3;

    public static final double CENTER_PANE_WIDTH = PANE_WIDTH;
    public static final double CENTER_PANE_HEIGHT = PANE_HEIGHT;
    
    private Pane centerLargePane;
    private VBox vbox;
	
	private Pane topPane;
	private Pane centerPane;
	private Pane bottomPane;

    public WelcomeScreen() {
        this.setPrefSize(WIDTH, HEIGHT);
        
        topPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        centerPane = createPane(CENTER_PANE_WIDTH,CENTER_PANE_HEIGHT);
        bottomPane = createPane(PANE_WIDTH, PANE_HEIGHT);
              
        vbox = new VBox(VBOX_PADDING);
        vbox.setPrefSize(VBOX_WIDTH,VBOX_HEIGHT);
        vbox.setLayoutX((WIDTH/2)-(VBOX_WIDTH/2));
        vbox.setLayoutY(0);
        vbox.toFront();
        vbox.getChildren().addAll(topPane,centerPane,bottomPane);
    }
	
	private Pane createPane(Double width, Double height) {
		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		pane.setMaxSize(width, height);
		return pane;
	}

	public void setBackgroundImage(ImageView image) {
		this.getChildren().add(image);
		image.toBack();
	}


	public void swipeForward(Node node) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(200),
				centerPane);
		
		if (centerPane.getTranslateX() != 0) {
			centerPane.setTranslateX(WIDTH);
			setCenterContent(node);
		} else {
			tt.onFinishedProperty().set(event -> swipeForward(node));
			
		}
		tt.setByX(-WIDTH);
		tt.setCycleCount(1);
		tt.setAutoReverse(false);
		tt.play();
	}
	
	public void swipeBack(Node node) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(200),
				centerPane);
		
		if (centerPane.getTranslateX() != 0) {
			centerPane.setTranslateX(-WIDTH);
			setCenterContent(node);
		} else {
			tt.onFinishedProperty().set(event -> swipeBack(node));
		}
		
		tt.setByX(WIDTH);
		tt.setCycleCount(1);
		tt.setAutoReverse(false);
		tt.play();
	}
    
    public void setTopContent(Node node) {
        setPaneContent(topPane,node);
    }
    
    public void setCenterContent(Node node) {
        setPaneContent(centerPane,node); 
    }
    
    public void setBottomContent(Node node) {
        setPaneContent(bottomPane,node);
    }
    
    private void setPaneContent(Pane pane,Node node) {
        this.getChildren().remove(centerLargePane);
        
        if (!this.getChildren().contains(vbox)) {
            this.getChildren().add(vbox);
        } 
        
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
}
