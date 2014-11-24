package gamePlayer.guiItems.controlDockButtons;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockButton implements GuiItem {
	
	protected XMLParser myParser;
	protected ImageView myImageView;
	protected ToggleButton myButton;

	protected Dimension2D buttonSize;
	protected Dimension2D imageSize;

	@Override
	public void initialize(Dimension2D containerSize) {
		myImageView = new ImageView();
		myButton = new ToggleButton();
		myButton.getStyleClass().add("ControlDockButton");
	}

	protected void setupImageViews(String path1, String path2) {
		Image image1 = null;
		Image image2 = null;
		try {
			image1 = new Image(path1);
			image2 = new Image(path2);
		} catch (NullPointerException npe) {
			ExceptionHandler.getInstance().handle(npe);
		}
		myImageView.imageProperty().bind(
				Bindings.when(myButton.selectedProperty())
						.then(image1)
						.otherwise(image2));
	}

	protected void setUpSizing(Dimension2D containerSize) {
		Dimension2D buttonRatio = myParser.getDimension("SizeRatio");
		Dimension2D imageRatio = myParser.getDimension("ImageRatio");

		buttonSize = new Dimension2D(buttonRatio.getWidth()
				* containerSize.getWidth(), buttonRatio.getHeight()
				* containerSize.getHeight());
		imageSize = new Dimension2D(imageRatio.getWidth()
				* buttonSize.getWidth(), imageRatio.getHeight()
				* buttonSize.getHeight());

		myButton.setMinSize(buttonSize.getWidth(), buttonSize.getHeight());
		myButton.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());
		myImageView.setFitHeight(imageSize.getHeight());
		myImageView.setFitWidth(imageSize.getWidth());
	}

}
