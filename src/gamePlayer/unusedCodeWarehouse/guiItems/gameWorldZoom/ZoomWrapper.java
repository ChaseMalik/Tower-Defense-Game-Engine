package gamePlayer.unusedCodeWarehouse.guiItems.gameWorldZoom;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.shape.Rectangle;

public class ZoomWrapper extends ScrollPane {

	private Group myGroup;
	private DoubleProperty ZOOM = new SimpleDoubleProperty(1);
	private DoubleProperty X_TRANS = new SimpleDoubleProperty(0);
	private DoubleProperty Y_TRANS = new SimpleDoubleProperty(0);

	public ZoomWrapper(Group group) {
		myGroup = group;
		setContent(myGroup);
		myGroup.scaleXProperty().bind(ZOOM);
		myGroup.scaleYProperty().bind(ZOOM);
		myGroup.translateXProperty().bind(X_TRANS);
		myGroup.translateYProperty().bind(Y_TRANS);
		setOnZoom(event -> zoom(event));
		setOnScroll(event -> scroll(event));
		layoutBoundsProperty().addListener((Observable, oldValue, newValue) -> bound(newValue));
	}

	private void bound(Bounds bounds) {
		setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
	}
	
	private void zoom(ZoomEvent event) {
		event.consume();
		double zoomFactor = event.getTotalZoomFactor();
		if ((zoomFactor > 1.05 && ZOOM.get() < 3)
				|| (zoomFactor < 0.9 && ZOOM.get() > 1))
			ZOOM.set(Math.max(zoomFactor * ZOOM.get(), 1));
	}

	private void scroll(ScrollEvent event) {
		event.consume();
		double xD = event.getDeltaX();
		double yD = event.getDeltaY();
		double xfactor = ZOOM.get() * (X_TRANS.get() + xD);
		double yfactor = ZOOM.get() * (Y_TRANS.get() + yD);
		if ((xD > 1.1 && xfactor < 10) || (xD < -1.1 && xfactor > -10))
			X_TRANS.set(xfactor);
		if ((yD > 1.1 && yfactor < 10) || (yD < -1.1 && yfactor > -10))
			Y_TRANS.set(yfactor);

	}

}
