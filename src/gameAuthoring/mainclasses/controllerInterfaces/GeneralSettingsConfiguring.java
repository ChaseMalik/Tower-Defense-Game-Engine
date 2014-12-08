package gameAuthoring.mainclasses.controllerInterfaces;

/**
 * An interface for selecting general settings for the game (player's starting
 * gold, health, background sound, etc).
 * @author Austin Kyker
 *
 */
public interface GeneralSettingsConfiguring {
	void setGeneralSettings(String name, int health, int cash);
	
	/**
	 * 
	 * @param gameName
	 * @param singlePlayer: true if single player, false if co-op
	 */
    void makeDirectory (String gameName, boolean singlePlayer);

}
