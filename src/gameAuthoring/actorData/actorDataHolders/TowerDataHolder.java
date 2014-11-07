package gameAuthoring.actorData.actorDataHolders;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.actorData.TowerData;
import gameAuthoring.mainclasses.Data;
import gameAuthoring.mainclasses.DataHolder;

public class TowerDataHolder extends DataHolder implements Iterable<TowerData> {

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
