package gamePlayer.guiItemsListeners;

import java.util.List;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItems.store.StoreItem;

public interface StoreListener {
    public void registerStore(Store store);
    public void fillStore(List<StoreItem> storeItems);
    public void refreshStore();
}
