package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WelcomeScreen extends AnchorPane {
    public static final double WIDTH = GuiConstants.WINDOW_WIDTH;
    public static final double HEIGHT = GuiConstants.WINDOW_HEIGHT;
    
    public static final double CENTER_PANE_WIDTH = 3*WIDTH/4;
    public static final double CENTER_PANE_HEIGHT = 2*HEIGHT/3;
    public static final double CENTER_PANE_LAYOUT_X = WIDTH/2-CENTER_PANE_WIDTH/2;
    public static final double CENTER_PANE_LAYOUT_Y = HEIGHT-CENTER_PANE_HEIGHT-60;
    
    public static final double VBOX_WIDTH = WIDTH/2.5;
    public static final double VBOX_HEIGHT= HEIGHT;
    private static final double VBOX_PADDING = 20;
    
    public static final double PANE_WIDTH  = VBOX_WIDTH;
    public static final double PANE_HEIGHT = VBOX_HEIGHT/3;
    
    private Pane topPane;
    private Pane centerPane;
    private Pane bottomPane;
    private Pane centerLargePane;
    private VBox vbox;
    
    public WelcomeScreen() {
        this.setPrefSize(WIDTH, HEIGHT);
        
        topPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        centerPane = createPane(PANE_WIDTH,PANE_HEIGHT);
        //allow center pane to grow large to accommodate game descriptions
        centerPane.setMaxSize(PANE_WIDTH, PANE_HEIGHT*2);
        bottomPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        
        centerLargePane = initializeCenterLargePane();
        
        vbox = new VBox(VBOX_PADDING);
        vbox.setPrefSize(VBOX_WIDTH,VBOX_HEIGHT);
        vbox.setLayoutX((WIDTH/2)-(VBOX_WIDTH/2));
        vbox.setLayoutY(0);
        vbox.toFront();
        vbox.getChildren().addAll(topPane,centerPane,bottomPane);
    }
     
    private Pane initializeCenterLargePane() {
        Pane pane = new Pane();
        pane.setMaxSize(CENTER_PANE_WIDTH, CENTER_PANE_HEIGHT);
        pane.setLayoutX(CENTER_PANE_LAYOUT_X);
        pane.setLayoutY(CENTER_PANE_LAYOUT_Y);
        return pane;
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
    
    public void setTopContent(Node node) {
        setPaneContent(topPane,node);
    }
    
    public void setCenterContent(Node node) {
        setPaneContent(centerPane,node); 
    }
    
    public void setCenterLargeContent(Node node) {
        vbox.getChildren().removeAll(centerPane,bottomPane);
        
        centerLargePane.getChildren().clear();
        centerLargePane.getChildren().add(node);
        if (!this.getChildren().contains(centerLargePane)) {
            this.getChildren().add(centerLargePane);
        } 
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
