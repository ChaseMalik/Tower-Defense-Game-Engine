package gameEngine;

import gameEngine.actors.BaseActor;
import gameEngine.levels.BaseLevel;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import utilities.ErrorPopup;


public class MainGameManager implements GameManager {

    private static final double ONE_SECOND_IN_NANO = 1000000000.0;
    private static final int FPS = 30;

    private AtomicBoolean myIsRunning;
    private AtomicBoolean myPauseRequested;
    private AtomicBoolean readyToPlay;

    private double myRenderInterval;
    private double myUpdateInterval;
    // 1 is normal speed
    private AtomicInteger myUpdateSpeed;

    private double myLastUpdateTime;
    private double myLastRenderTime;

    public MainGameManager () {
        myIsRunning = new AtomicBoolean(false);
        readyToPlay = new AtomicBoolean(false);
        myPauseRequested = new AtomicBoolean(false);

        myRenderInterval = ONE_SECOND_IN_NANO / FPS;
        myUpdateInterval = myRenderInterval;
        myUpdateSpeed = new AtomicInteger(1);

        myLastUpdateTime = System.nanoTime();
        myLastRenderTime = System.nanoTime();
    }

    public void speedUp (int magnitude) {
        myUpdateSpeed.set(magnitude);
    }

    @Override
    public void loadState (String fileName) {
    }

    @Override
    public void saveState (String fileName) {
    }

    @Override
    public void initializeGame (String fileName) {

    }

    @Override
    public void pause () {
        myPauseRequested.set(true);
    }

    public void resume () {
        myPauseRequested.set(false);
    }

    public synchronized void addActor (BaseActor actor) {
        
    }

    @Override
    public void start () {
        if (readyToPlay.get()) {
            myIsRunning.set(true);
            Thread gameLoop = new Thread() {
                public void run () {
                    gameLoop();
                }
            };
            gameLoop.start();
        }
        else {
            new ErrorPopup("Not yet ready to play");
        }
    }

    private void gameLoop () {
        while (myIsRunning.get()) {
            double now = System.nanoTime();
            if (!myPauseRequested.get() && readyToPlay.get()) {
                double adjustedUpdateInterval = myUpdateInterval / myUpdateSpeed.get();
                double updateTimeDifference = now - myLastUpdateTime;
                double timeBetweenUpdateAndRender = now - myLastRenderTime;
                //Allow for catchup in the case of inaccurate thread waking up.
                do {
                    double updateStart = System.nanoTime();
                    update();
                    double updateEnd = System.nanoTime();
                    myLastUpdateTime += Math.max(adjustedUpdateInterval, updateEnd - updateStart);
                    updateTimeDifference = now - myLastUpdateTime;
                    timeBetweenUpdateAndRender = updateEnd - myLastRenderTime;
                } // get rid of second check if frame dropping is unimportant. Allows for more
                  // accurate updates before render
                while (updateTimeDifference > adjustedUpdateInterval &&
                       timeBetweenUpdateAndRender < myRenderInterval);
                if (now - myLastRenderTime >= myRenderInterval) {
                    render();
                    myLastRenderTime = now;
                }

                while (now - myLastRenderTime < myRenderInterval &&
                       now - myLastUpdateTime < adjustedUpdateInterval) {
                    Thread.yield();
                    try {
                        Thread.sleep(0, 500);
                    }
                    catch (InterruptedException e) {
                        // probably fine
                    }
                    now = System.nanoTime();
                }
            }
            else {
                Thread.yield();
                try {
                    Thread.sleep(0, 500);
                }
                catch (InterruptedException e) {
                    // Probably fine
                }
            }
        }
    }

    private void update () {

    }

    private void render () {

    }

    @Override
    public void quit () {
        myIsRunning.set(false);
    }

    @Override
    public void loadLevel (BaseLevel level) {
        readyToPlay.set(false);
        // load
        readyToPlay.set(true);
    }
}
