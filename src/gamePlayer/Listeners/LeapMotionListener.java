package gamePlayer.Listeners;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

public class LeapMotionListener extends Listener {

	private ObjectProperty<Point2D> point = new SimpleObjectProperty<>();
	private long swipeTimer;
	private static final long SWIPE_TIME = 500;

	public ObservableValue<Point2D> pointProperty() {
		return point;
	}

	@Override
	public void onConnect(Controller c) {
		super.onConnect(c);
		System.out.println("Device Connected!");
		c.enableGesture(Gesture.Type.TYPE_SWIPE);
		//c.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		//c.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		setupTimers();
	}

	@Override
	public void onFrame(Controller c) {

		Frame frame = c.frame();

		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);
			switch (gesture.type()) {
			case TYPE_SWIPE:
				if (System.currentTimeMillis() - swipeTimer > SWIPE_TIME) {
					swipeTimer = System.currentTimeMillis();
					swipe(new SwipeGesture(gesture));
				}
				break;
			case TYPE_KEY_TAP:
				System.out.println("Key tap!");
				break;
			case TYPE_SCREEN_TAP:
				System.out.println("Screen tap!");
				Vector position = frame.fingers().frontmost()
						.stabilizedTipPosition();
				InteractionBox iBox = frame.interactionBox();
				Vector normalizedPosition = iBox.normalizePoint(position);
				screenTap(normalizedPosition);
				break;
			default:
				break;
			}
		}
	}

	private void swipe(SwipeGesture gesture) {
		
		System.out.println("SWIPE!!!");
		
		System.out.println("Speed: " + gesture.speed());

		Vector swipeDirection = gesture.direction();		
		double roll = Math.abs(swipeDirection.roll());
		if (roll < .80) {
			//Platform.runLater(() -> GuiConstants.GUI_MANAGER.down());
			System.out.println("Down");
		} else if (roll < 2.50) {
			if (swipeDirection.roll() > 0) {
				//Platform.runLater(() -> GuiConstants.GUI_MANAGER.right());
				System.out.println("Right");
			} else {
				//Platform.runLater(() -> GuiConstants.GUI_MANAGER.left());
				System.out.println("Left");
			}
		} else {
			System.out.println("Up");
		}

		//Platform.runLater(() -> Constants.GUI_MANAGER.clearWorld());
	}

	private void screenTap(Vector tapPosition) {
		System.out.println("[" + tapPosition.getX() + "," + tapPosition.getY()
				+ "]");
		tapPosition.setY(Math.min(1, 1 - tapPosition.getY()));
		tapPosition.setX(Math.min(1, tapPosition.getX()));
		// Platform.runLater(() ->
		// Constants.GUI_MANAGER.tapScreen(tapPosition.getX(),
		// tapPosition.getY()));
		System.out.println("[" + tapPosition.getX() + "," + tapPosition.getY()
				+ "]");
	}

	private void setupTimers() {
		swipeTimer = System.currentTimeMillis();
	}

}
