package gameEngine;

public class TowerInfoObject {

    private TowerInfoObject myUpgrade;
    private String myName;
    private String myImageLocation;
    private int myBuyCost;
    private int mySellCost;
    private double myRange;

    public TowerInfoObject (String towerName,
                            String imageLocation,
                            int buyCost,
                            int sellCost,
                            double range) {
        myName = towerName;
        myImageLocation = imageLocation;
        myBuyCost = buyCost;
        mySellCost = sellCost;
        myRange = range;
    }

    public void setNextTower (TowerInfoObject upgrade) {
        myUpgrade = upgrade;
    }

    public String getName () {
        return myName;
    }

    public String getImageLocation () {
        return myImageLocation;
    }

    public int getBuyCost () {
        return myBuyCost;
    }

    public int getSellCost () {
        return mySellCost;
    }

    public double getRange () {
        return myRange;
    }

    public TowerInfoObject getMyUpgrade () {
        return myUpgrade;
    }
}
