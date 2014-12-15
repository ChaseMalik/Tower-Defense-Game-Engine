package gameAuthoring.mainclasses.controllerInterfaces;

import utilities.GSON.objectWrappers.GeneralSettingsWrapper;


/**
 * An interface for selecting general settings for the game (player's starting
 * gold, health, background sound, etc).
 * 
 * @author Austin Kyker, David Zhang
 *
 */
public interface IGeneralSettingsConfiguring {
    void setGeneralSettings (GeneralSettingsWrapper generalSettingsWrapper);

    /**
     * 
     * @param gameName
     * @param singleType: SinglePlayer or Co-Op
     */
    void makeDirectory (String gameName, String gameType);

}
