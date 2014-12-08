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
	 * @param singleType: SinglePlayer or Co-Op
	 */
    void makeDirectory (String gameName, String gameType);

}
