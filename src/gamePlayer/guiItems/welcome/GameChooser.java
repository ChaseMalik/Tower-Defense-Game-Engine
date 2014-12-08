package gamePlayer.guiItems.welcome;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.welcomeScreen.availableGames.GameDescriptionLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class GameChooser extends ScrollPane implements GuiItem {
    
	private VBox myBox;
    
    public GameChooser (String directory) {        
        myBox = new VBox();
        loadGameDescriptions(directory);
        this.setContent(myBox);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);    
    }
    
    private void loadGameDescriptions (String directory) {
        GameDescriptionLoader loader = new GameDescriptionLoader();      
        myBox.getChildren().addAll(loader.getDescriptions(directory));
    }

	@Override
	public void initialize(Dimension2D containerSize) {
        myBox.setPrefSize(containerSize.getWidth(),containerSize.getHeight());

	}

	@Override
	public Node getNode() {
		return myBox;
	}
}
