package gamePlayer.guiItems.controlDockButtons;

import gamePlayer.guiItems.GuiItem;
//import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
//import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Slider;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockSlider implements GuiItem {
    protected XMLParser myParser;
//    protected ImageView myImageView;
//    protected ToggleButton myButton;
    protected Slider mySlider;

    protected Dimension2D sliderSize;
    protected Dimension2D imageSize;

    @Override
    public void initialize(Dimension2D containerSize) {
//        myImageView = new ImageView();
//        myButton = new ToggleButton();
//        myButton.getStyleClass().add("ControlDockSlider");
        mySlider.getStyleClass().add("ControlDockSlider");
    }

//    protected void setupImageViews(String path1, String path2) {
//        Image image1 = null;
//        Image image2 = null;
//        try {
//            image1 = new Image(path1);
//            image2 = new Image(path2);
//        } catch (NullPointerException npe) {
//            ExceptionHandler.getInstance().handle(npe);
//        }
//        myImageView.imageProperty().bind(
//                Bindings.when(myButton.selectedProperty())
//                        .then(image1)
//                        .otherwise(image2));
//    }

    protected void setUpSizing(Dimension2D containerSize) {
        Dimension2D sliderRatio = myParser.getDimension("SizeRatio");
//        Dimension2D imageRatio = myParser.getDimension("ImageRatio");

        sliderSize = new Dimension2D(sliderRatio.getWidth()
                * containerSize.getWidth(), sliderRatio.getHeight()
                * containerSize.getHeight());
//        imageSize = new Dimension2D(imageRatio.getWidth()
//                * sliderSize.getWidth(), imageRatio.getHeight()
//                * sliderSize.getHeight());

        mySlider.setMinSize(sliderSize.getWidth(), sliderSize.getHeight());
        mySlider.setPrefSize(sliderSize.getWidth(), sliderSize.getHeight());
//        myImageView.setFitHeight(imageSize.getHeight());
//        myImageView.setFitWidth(imageSize.getWidth());
    }
}
