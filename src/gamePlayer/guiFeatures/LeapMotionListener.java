package gamePlayer.guiFeatures;

import javafx.application.Platform;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

public class LeapMotionListener extends Listener {

	private long swipeTimer, circleTimer;
	private static final long SWIPE_TIME_LIMITER = 500;
	private static final long CIRCLE_TIME_LIMITER = 1000;
	private LMController controller = LMController.getInstance();

	@Override
	public void onConnect(Controller c) {
		super.onConnect(c);
		System.out.println("Device Connected!");
		c.enableGesture(Gesture.Type.TYPE_SWIPE);
		c.enableGesture(Gesture.Type.TYPE_CIRCLE);
		// c.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		// c.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		
		setupTimers();
		Platform.runLater(() -> controller.connect());
	}

	@Override
	public void onFrame(Controller c) {

		Frame frame = c.frame();

		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);
			switch (gesture.type()) {
			case TYPE_SWIPE:
				swipe(new SwipeGesture(gesture));
				break;
			case TYPE_CIRCLE:
				//System.out.println("Circle!");
				circle(new CircleGesture(gesture));
				break;
			case TYPE_KEY_TAP:
				//System.out.println("Key tap!");
				break;
			case TYPE_SCREEN_TAP:
				//System.out.println("Screen tap!");
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

	private void circle(CircleGesture circle) {

		// Limit amount of swipes they can do
		if (System.currentTimeMillis() - circleTimer < CIRCLE_TIME_LIMITER)
			return;
		circleTimer = System.currentTimeMillis();

		if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 2) { // Clock-wise
			Platform.runLater(() -> controller.circleCW());
		} else { // Counter-Clockwise
			Platform.runLater(() -> controller.circleCCW());
		}
	}

	private void swipe(SwipeGesture gesture) {

		// Limit amount of swipes they can do
		if (System.currentTimeMillis() - swipeTimer < SWIPE_TIME_LIMITER)
			return;
		swipeTimer = System.currentTimeMillis();

		Vector swipeDirection = gesture.direction();
		double roll = Math.abs(swipeDirection.roll());
		if (roll < .80) {
			//System.out.println("Down");
			Platform.runLater(() -> controller.swipeDown());
		} else if (roll < 2.50) {
			if (swipeDirection.roll() > 0) {
				//System.out.println("Right");
				Platform.runLater(() -> controller.swipeRight());
			} else {
				//System.out.println("Left");
				Platform.runLater(() -> controller.swipeLeft());
			}
		} else {
			//System.out.println("Up");
			Platform.runLater(() -> controller.swipeUp());
		}
	}

	private void screenTap(Vector tapPosition) {
		System.out.println("[" + tapPosition.getX() + "," + tapPosition.getY()
				+ "]");
		tapPosition.setY(Math.min(1, 1 - tapPosition.getY()));
		tapPosition.setX(Math.min(1, tapPosition.getX()));

		System.out.println("[" + tapPosition.getX() + "," + tapPosition.getY()
				+ "]");
	}

	private void setupTimers() {
		swipeTimer = System.currentTimeMillis();
		circleTimer = System.currentTimeMillis();
	}

}
