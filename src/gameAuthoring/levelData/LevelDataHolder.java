package gameAuthoring.levelData;

import java.util.Iterator;
import java.util.List;
import gameAuthoring.mainclasses.Data;
import gameAuthoring.mainclasses.DataHolder;

public class LevelDataHolder extends DataHolder implements Iterator<LevelData> {

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
