package utilities.JavaFXutilities.ratioSizing;

import javafx.geometry.Dimension2D;

/**
 * 
 * @author Greg Lyons
 * 
 * This class's static convert method is used to multiply ratios 
 * by given width and height dimensions to output a new width and height Dimension2D.
 * 
 * The GuiBuilder system uses many XML files that contain ratios for sizing within dimensions of a container,
 * and this class helps to avoid repeated code in parsing these XML files.
 *
 */

public class RatiosToDim {

	public static Dimension2D convert(Dimension2D containerSize, Dimension2D ratio) {
		double width = containerSize.getWidth() * ratio.getWidth();
		double height = containerSize.getHeight() * ratio.getHeight();
		return new Dimension2D(width, height);
	}
	
}
