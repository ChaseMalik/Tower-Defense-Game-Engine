package gamePlayer.guiFeatures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.leapmotion.leap.Controller;

/**
 * Controller for the Leap Motion Device. Any class can register a method to
 * call when a certain gesture or action happens measurable by the device
 * 
 * @author brianbolze
 */
public class LMController {
	
	private enum EventType {
		DEVICE_CONNECTED, SWIPE_LEFT, SWIPE_RIGHT, SWIPE_DOWN, SWIPE_UP, CIRCLE_CW, CIRCLE_CCW
	}

	private Map<EventType, Set<EventHandler<ActionEvent>>> myHandlerMap;
	public static Controller DeviceController;
	public static LeapMotionListener DeviceListener;

	private static LMController myReference;

	private LMController() {
		myHandlerMap = new HashMap<EventType, Set<EventHandler<ActionEvent>>>();
	}

	public static LMController getInstance() {
		if (myReference == null) {
			myReference = new LMController();
		}
		return myReference;
	}

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

	public void clearAllHandlers() {
		myHandlerMap.clear();
	}

	public void onConnect(EventHandler<ActionEvent> handler) {
		addHandler(EventType.DEVICE_CONNECTED, handler);
	}

	public void onSwipeLeft(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_LEFT, handler);
	}

	public void onSwipeRight(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_RIGHT, handler);
	}

	public void onSwipeUp(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_UP, handler);
	}

	public void onSwipeDown(EventHandler<ActionEvent> handler) {
		addHandler(EventType.SWIPE_DOWN, handler);
	}

	public void onCircleCW(EventHandler<ActionEvent> handler) {
		addHandler(EventType.CIRCLE_CW, handler);
	}

	public void onCircleCCW(EventHandler<ActionEvent> handler) {
		addHandler(EventType.CIRCLE_CCW, handler);
	}
	
	public void removeOnConnect(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.DEVICE_CONNECTED, handler);
	}
	
	public void removeOnSwipeLeft(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_LEFT, handler);
	}
	
	public void removeOnSwipeRight(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_RIGHT, handler);
	}
	
	public void removeOnSwipeUp(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_UP, handler);
	}
	
	public void removeOnSwipeDown(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.SWIPE_DOWN, handler);
	}
	
	public void removeOnCircleCW(EventHandler<ActionEvent> handler) {
		removeHandler(EventType.CIRCLE_CW, handler);
	}
	
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
		if (!myHandlerMap.containsKey(event) || !myHandlerMap.get(event).contains(handler)) {
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

	public void setTimeLimit(boolean isSwipeTimeLimit) {
		DeviceListener.setTimeLimit(isSwipeTimeLimit);
	}
}
