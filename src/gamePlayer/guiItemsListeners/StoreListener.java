package gamePlayer.guiItemsListeners;

import java.util.Collection;
import java.util.List;

import gameEngine.TowerInfoObject;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItems.store.StoreItem;

public interface StoreListener {
    public void registerStore(Store store);
    public void fillStore(Collection<TowerInfoObject> towersAvailable);
    public void refreshStore();
}
