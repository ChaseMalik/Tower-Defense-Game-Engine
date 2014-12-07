package gamePlayer.guiItemsListeners;

import java.util.List;
import gamePlayer.guiItems.headsUpDisplay.GameStat;
import gamePlayer.guiItems.headsUpDisplay.HUD;

/**
 * Listener interface for StatsBoardListener GuiItem
 * @author allankiplagat
 *
 */
public interface HUDListener {
    public void setGameStats(List<GameStat> stats);
    public void registerStatsBoard(HUD hud);
}
