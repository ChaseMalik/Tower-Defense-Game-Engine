package gameAuthoring.pathData;

import gameAuthoring.mainclasses.Data;
import gameAuthoring.mainclasses.DataHolder;
import java.util.LinkedList;

public class PathDataHolder extends DataHolder {
    private LinkedList<PathComponent> path;

    @Override
    public void addData (Data dataObj) {
        path.add((PathComponent) dataObj);
    }
    
}
