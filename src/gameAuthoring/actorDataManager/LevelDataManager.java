package gameAuthoring.actorDataManager;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.data.Data;
import gameAuthoring.data.LevelData;

public class LevelDataManager extends ActorDataManager implements Iterator<LevelData> {

    private List<LevelData> myData;

    @Override
    public void addData (Data dataObj) {
        myData.add((LevelData) dataObj);
    }

    @Override
    public boolean hasNext () {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public LevelData next () {
        // TODO Auto-generated method stub
        return null;
    }
}
