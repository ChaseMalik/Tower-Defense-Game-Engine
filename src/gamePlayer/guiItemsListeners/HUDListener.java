package gamePlayer.guiItemsListeners;

import java.util.List;
import gamePlayer.guiItems.headsUpDisplay.GameStats;
import gamePlayer.guiItems.headsUpDisplay.HUD;

/**
 * Listener interface for StatsBoardListener GuiItem
 * @author allankiplagat
 *
 */
public interface HUDListener {
    public void setGameStats(List<GameStats> stats);
    public void registerStatsBoard(HUD hud);
}
