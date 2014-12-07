package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class WelcomeScreen extends Pane {
    public static final double WIDTH = GuiConstants.WINDOW_WIDTH;
    public static final double HEIGHT = GuiConstants.WINDOW_HEIGHT;
    
    private static final double VBOX_WIDTH = WIDTH/4;
    private static final double VBOX_HEIGHT= HEIGHT;
    
    public static final double PANE_WIDTH  = VBOX_WIDTH;
    public static final double PANE_HEIGHT = VBOX_HEIGHT/3;
    
    private Pane topPane;
    private Pane centerPane;
    private Pane bottomPane;
    
    public WelcomeScreen() {
        this.setPrefSize(WIDTH, HEIGHT);
        
        topPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        centerPane = createPane(PANE_WIDTH,PANE_HEIGHT);
        bottomPane = createPane(PANE_WIDTH, PANE_HEIGHT);
        
        VBox vbox = new VBox(20);
        vbox.setPrefSize(VBOX_WIDTH,VBOX_HEIGHT);
        vbox.setLayoutX((WIDTH/2)-(VBOX_WIDTH/2));
        vbox.setLayoutY(0);
        this.getChildren().add(vbox);
        vbox.toFront();

        vbox.getChildren().addAll(topPane,centerPane,bottomPane);
    }
    
    private Pane createPane(Double width, Double height) {
        Pane pane = new Pane();
        pane.setMinSize(width, height);
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
    
    public void setBottomContent(Node node) {
        setPaneContent(bottomPane,node);
    }
    
    private void setPaneContent(Pane pane,Node node) {
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    
}
