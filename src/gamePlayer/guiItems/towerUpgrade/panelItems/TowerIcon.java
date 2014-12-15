package gamePlayer.guiItems.towerUpgrade.panelItems;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import gameEngine.Data.TowerInfoObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerIcon extends ImageView implements UpgradePanelItem{
	
	private Dimension2D myIconSize;
	
	public TowerIcon(Dimension2D iconSize, EventHandler<ActionEvent> handler) {
		super();
		this.setFitWidth(iconSize.getWidth());
		this.setFitHeight(iconSize.getWidth());
		this.setStyle("-fx-stroke: green; -fx-stroke-width: 5");
		myIconSize = iconSize;
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		Image newImage = StringToImageViewConverter.getImageView(
						myIconSize.getWidth(), myIconSize.getHeight(), 
						tower.getImageLocation()).getImage();
		this.setImage(newImage);
	}

}
