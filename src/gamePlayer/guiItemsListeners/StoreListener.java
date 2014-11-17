package gamePlayer.guiItemsListeners;

public interface StoreListener {
	public void buyItem(String itemID);
	public void registerTowerListener(SelectTowerListener listener);
}
