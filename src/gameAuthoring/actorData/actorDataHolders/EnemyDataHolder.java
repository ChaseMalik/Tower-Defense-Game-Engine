package gameAuthoring.actorData.actorDataHolders;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.actorData.EnemyData;
import gameAuthoring.mainclasses.Data;
import gameAuthoring.mainclasses.DataHolder;

public class EnemyDataHolder extends DataHolder implements Iterable<EnemyData> {

    private List<EnemyData> myData;

    @Override
    public void addData (Data dataObj) {
        myData.add((EnemyData) dataObj);
    }

    @Override
    public Iterator<EnemyData> iterator () {
        return myData.iterator();
    }
}
