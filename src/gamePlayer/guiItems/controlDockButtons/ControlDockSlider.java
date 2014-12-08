package gamePlayer.guiItems.controlDockButtons;

import gamePlayer.guiItems.GuiItem;
//import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
//import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockSlider implements GuiItem {

	protected XMLParser myParser;
	protected Slider mySlider;
	private Label myLabel;
	private Pane myPane;

	protected Dimension2D sliderSize, mySize;
	private double heightRatio;

	@Override
	public void initialize(Dimension2D containerSize) {
		mySlider = new Slider();
		setupPane(containerSize);
		addLabel();
		myPane.getChildren().addAll(mySlider, myLabel);
	}

	@Override
	public Node getNode() {
		return myPane;
	}

	protected void setUpSizing(Dimension2D containerSize) {

		sliderSize = new Dimension2D(mySize.getWidth(),
				mySize.getHeight() * heightRatio);
		mySlider.setPrefSize(sliderSize.getWidth(), sliderSize.getHeight());
	}

	private void setupPane(Dimension2D containerSize) {
		myPane = new VBox();

		Dimension2D sizeRatios = myParser.getDimension("SizeRatio");
		mySize = new Dimension2D(sizeRatios.getWidth()
				* containerSize.getWidth(), sizeRatios.getHeight()
				* containerSize.getHeight());
		heightRatio = myParser.getDoubleValuesFromTag("SliderHeightRatio").get(
				0);
	}

	private void addLabel() {
		String text = myParser.getValuesFromTag("Label").get(0);
		myLabel = new Label(text);
		myLabel.setMinSize(mySize.getWidth(), (1-heightRatio)*mySize.getHeight());
		myLabel.setAlignment(Pos.CENTER);
	}
}
