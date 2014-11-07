package gameAuthoring.actorDataManager;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.data.Data;
import gameAuthoring.data.EnemyData;

public class EnemyDataManager extends ActorDataManager implements Iterable<EnemyData> {

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
