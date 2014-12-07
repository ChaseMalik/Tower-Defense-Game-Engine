package gamePlayer.guiFeatures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;

/**
 * Controller for the Leap Motion Device. Any class can register a method to
 * call when a certain gesture or action happens measurable by the device
 * 
 * @author brianbolze
 */
public class LMController {

	private Map<String, Set<EventHandler<ActionEvent>>> myHandlerMap;
	public static Controller DeviceController;
	public static Listener DeviceListener;

	private static LMController myReference;

	private LMController() {
		myHandlerMap = new HashMap<String, Set<EventHandler<ActionEvent>>>();
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
		// TODO : Setup more LeapMotion stuff
	}

	public void clearAllHandlers() {
		myHandlerMap.clear();
	}

	public void onConnect(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("DeviceConnected")) {
			myHandlerMap.put("DeviceConnected",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("DeviceConnected").add(handler);
	}

	public void onSwipeLeft(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("SwipeLeft")) {
			myHandlerMap.put("SwipeLeft",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("SwipeLeft").add(handler);
	}

	public void onSwipeRight(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("SwipeRight")) {
			myHandlerMap.put("SwipeRight",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("SwipeRight").add(handler);
	}

	public void onSwipeUp(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("SwipeUp")) {
			myHandlerMap.put("SwipeUp",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("SwipeUp").add(handler);
	}

	public void onSwipeDown(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("SwipeDown")) {
			myHandlerMap.put("SwipeDown",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("SwipeDown").add(handler);
	}

	public void onCircleCW(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("CircleCW")) {
			myHandlerMap.put("CircleCW",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("CircleCW").add(handler);
	}

	public void onCircleCCW(EventHandler<ActionEvent> handler) {
		if (!myHandlerMap.containsKey("CircleCCW")) {
			myHandlerMap.put("CircleCCW",
					new HashSet<EventHandler<ActionEvent>>());
		}
		myHandlerMap.get("CircleCCW").add(handler);
	}

	// /////////////////////
	// THESE METHODS ARE CALLED BY LEAPMOTIONLISTENER
	// /////////////////////
	protected void swipeLeft() {
		invoke("SwipeLeft");
	}

	protected void swipeRight() {
		invoke("SwipeRight");
	}

	protected void swipeUp() {
		invoke("SwipeUp");
	}

	protected void swipeDown() {
		invoke("SwipeDown");
	}

	protected void circleCW() {
		invoke("CircleCW");
	}

	protected void circleCCW() {
		invoke("CircleCCW");
	}

	protected void connect() {
		invoke("DeviceConnected");
	}

	private void invoke(String eventType) {
		if (!myHandlerMap.containsKey(eventType))
			return;
		Set<EventHandler<ActionEvent>> handlers = myHandlerMap.get(eventType);
		handlers.parallelStream().forEach(
				(handler) -> handler.handle(new ActionEvent()));
	}
}
