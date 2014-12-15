// This entire file is part of my masterpiece.
// Brian Bolze (beb23)
package utilities.LeapMotion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.leapmotion.leap.Controller;

/**
 * Controller for the Leap Motion Device. Any class can register a method to
 * call when a certain gesture or action happens measurable by the device. New
 * gesture events can be created in the LeapMotionListener class, this class
 * hides the lower level details and calculations on the different gesture
 * types.
 * 
 * WARNING: This code WILL NOT COMPILE if you do not have the appropriate JARs
 * installed for the LeapMotion device. The required libraries can be downloaded
 * by following the link here: https://www.leapmotion.com/setup
 * 
 * @author brianbolze
 */
public class LMController {

	private enum EventType {
		DEVICE_CONNECTED, SWIPE_LEFT, SWIPE_RIGHT, SWIPE_DOWN, SWIPE_UP, CIRCLE_CW, CIRCLE_CCW
	}

	private Controller DeviceController;
	private LeapMotionListener DeviceListener;
	private Map<EventType, Set<EventHandler<ActionEvent>>> myHandlerMap;

	private static LMController myReference;

	private LMController() {
		myHandlerMap = new HashMap<EventType, Set<EventHandler<ActionEvent>>>();
	}

	/**
	 * 
	 * @return Returns the Singleton instance of the LMController, instantiating
	 *         a new object if not already created
	 */
	public static LMController getInstance() {
		if (myReference == null) {
			myReference = new LMController();
		}
		return myReference;
	}

	/**
	 * 
	 * @return True if the Leap Motion device is actively connected, otherwise
	 *         false
	 */
	public boolean deviceIsConnected() {
		return DeviceController != null;
	}

	public void activateDevice() {
		try {
			DeviceController = new Controller();
			DeviceListener = new LeapMotionListener();
			DeviceController.addListener(DeviceListener);
		} catch (Exception e) {
			System.out.println("Error connecting Leap Motion");
			return;
		}
	}

	/**
	 * 
	 * @param isGestureLimit
	 *            if true, the Leap Motion will set a limiter on the amount of
	 *            gestures that can be detected in a given interval
	 */
	public void setTimeLimit(boolean isGestureLimit) {
		DeviceListener.setTimeLimit(isGestureLimit);
	}

	/**
	 * Removes all behavior bound to the Leap Motion
	 */
	public void clearAllHandlers() {
		myHandlerMap.clear();
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion is connected
	 */
	public void onConnect(EventHandler<ActionEvent> handler) {
		addHandler(EventType.DEVICE_CONNECTED, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the left direction
	 */
	public void onSwipeLeft(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_LEFT, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the right direction
	 */
	public void onSwipeRight(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_RIGHT, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the upward direction
	 */
	public void onSwipeUp(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_UP, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the downward direction
	 */
	public void onSwipeDown(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_DOWN, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a circle gesture in the clockwise
	 *            direction
	 */
	public void onCircleCW(EventHandler<ActionEvent> handler) {
		addHandler(EventType.CIRCLE_CW, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Adds the action to be the list of actions invoked when the
	 *            Leap Motion detects a circle gesture in the counter-clockwise
	 *            direction
	 */
	public void onCircleCCW(EventHandler<ActionEvent> handler) {
		addHandler(EventType.CIRCLE_CCW, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion is connected
	 */
	public void removeOnConnect(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.DEVICE_CONNECTED, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the left direction
	 */
	public void removeOnSwipeLeft(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_LEFT, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the right direction
	 */
	public void removeOnSwipeRight(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_RIGHT, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the upward direction
	 */
	public void removeOnSwipeUp(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_UP, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a swipe gesture in the downward direction
	 */
	public void removeOnSwipeDown(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_DOWN, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a circle gesture in the clockwise
	 *            direction
	 */
	public void removeOnCircleCW(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.CIRCLE_CW, handler);
	}

	/**
	 * 
	 * @param handler
	 *            Removes the action to the list of actions invoked when the
	 *            Leap Motion detects a circle gesture in the counter-clockwise
	 *            direction
	 */
	public void removeOnCircleCCW(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.CIRCLE_CCW, handler);
	}

	// /////////////////////
	// THESE METHODS ARE CALLED BY LEAPMOTIONLISTENER
	// /////////////////////
	protected void swipeLeft() {
		invoke(EventType.SWIPE_LEFT);
	}

	protected void swipeRight() {
		invoke(EventType.SWIPE_RIGHT);
	}

	protected void swipeUp() {
		invoke(EventType.SWIPE_UP);
	}

	protected void swipeDown() {
		invoke(EventType.SWIPE_DOWN);
	}

	protected void circleCW() {
		invoke(EventType.CIRCLE_CW);
	}

	protected void circleCCW() {
		invoke(EventType.CIRCLE_CCW);
	}

	protected void connect() {
		invoke(EventType.DEVICE_CONNECTED);
	}

	private void addHandler(EventType event, EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey(event)) {
			myHandlerMap.put(event, new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get(event).add(handler);
	}

	private void removeHandler(EventType event,
			EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey(event)
				|| !myHandlerMap.get(event).contains(handler)) {
			return;
		}
		myHandlerMap.get(event).remove(handler);

	}

	private void invoke(EventType eventType) {
		if (!myHandlerMap.containsKey(eventType))
			return;
		Set<EventHandler<ActionEvent>> handlers = myHandlerMap.get(eventType);
		handlers.forEach((handler) -> handler.handle(new ActionEvent()));
	}
}
