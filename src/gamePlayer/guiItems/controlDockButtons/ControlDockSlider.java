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
import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockSlider implements GuiItem {

	protected XMLParser myParser;
	protected Slider mySlider;
	private Label myLabel;
	private Pane myPane;
	public static final String LABEL = "GameSpeed";

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

    protected void setUpSizing(Dimension2D containerSize) {
        Dimension2D sliderRatio = myParser.getDimension("SizeRatio");
        Dimension2D sliderSize = RatiosToDim.convert(containerSize, sliderRatio);
    	mySlider.setMaxSize(sliderSize.getWidth(), sliderSize.getHeight());
    }
}
