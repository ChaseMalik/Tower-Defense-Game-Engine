package gameEngine;

import gameEngine.levels.BaseLevel;

public interface GameManager {
    /**
     * @author $cotty $haw
     *
     */
    
    /**
     * Loads the game state
     * 
     * @param fileName Name of file to load
     */
    public void loadState (String fileName);

    /**
     * Saves the game's current state in the file specified by fileName
     * 
     * @param fileName Name of save destination
     */
    public void saveState (String fileName);

    /**
     * Initializes the game environment
     * 
     * @param fileName Name of game environment file to initialize
     */
    public void initializeGame (String fileName);

    /**
     * Pauses the game
     */
    public void pause ();

    /**
     * Plays or resumes the game
     */
    public void start ();

    public void loadLevel(BaseLevel level);
    
    /**
     * Quits the game
     */
    public void quit ();
}
