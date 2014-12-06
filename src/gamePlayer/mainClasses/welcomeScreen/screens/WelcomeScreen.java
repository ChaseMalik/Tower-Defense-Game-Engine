package gamePlayer.mainClasses.welcomeScreen.screens;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class WelcomeScreen extends Pane {
    private static final double WIDTH = GuiConstants.WINDOW_WIDTH;
    private static final double HEIGHT = GuiConstants.WINDOW_HEIGHT;
    
    private static final double HBOX_WIDTH = WIDTH*(1/3);
    private static final double HBOX_HEIGHT= HEIGHT;
    
    private static final double PANE_WIDTH  = HBOX_WIDTH;
    private static final double PANE_HEIGHT = (1/3)*HBOX_HEIGHT;
    
    private Pane topPane;
    private Pane centerPane;
    private Pane bottomPane;
    
    public WelcomeScreen() {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);
        
        topPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        centerPane = createPane(PANE_WIDTH,PANE_HEIGHT);
        bottomPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        
        HBox hbox = new HBox();
        hbox.setPrefSize(HBOX_WIDTH,HBOX_HEIGHT);
        hbox.setMaxSize(HBOX_WIDTH,HBOX_HEIGHT);
        hbox.getChildren().add(topPane);
        hbox.getChildren().add(centerPane);
        hbox.getChildren().add(bottomPane);
        
        hbox.setLayoutX((WIDTH/2)-(HBOX_WIDTH/2));
        hbox.setLayoutY(0);
        this.getChildren().add(hbox);
    }
    
    private Pane createPane(Double width, Double height) {
        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        pane.setMaxSize(width, height);
        //add transparent placeholder for alignment
        Rectangle r = new Rectangle(PANE_WIDTH,PANE_HEIGHT);
        //r.setOpacity(1);
        pane.getChildren().add(r);
        
        return pane;
    }
    
    public void setBackgroundImage(String imagePath) {
        Image image = new Image(imagePath,GuiConstants.WINDOW_WIDTH,GuiConstants.WINDOW_HEIGHT,false,true);
        ImageView background = new ImageView(image);
        this.getChildren().add(background);
        background.toBack();
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
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    
}
