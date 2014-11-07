package gameAuthoring.actorDataManager;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.data.Data;
import gameAuthoring.data.TowerData;

public class TowerDataManager extends ActorDataManager implements Iterable<TowerData> {

    private List<TowerData> myData;
    
   
    @Override
    public void addData (Data dataObj) {
        myData.add((TowerData) myData);
        
    }

    @Override
    public Iterator<TowerData> iterator () {
        return myData.iterator();
    }
}
