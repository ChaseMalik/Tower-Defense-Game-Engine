package gameEngine;

public class TowerInfoObject {

	private TowerInfoObject myUpgrade;
	private String myName;
	private String myImageLocation;
	private int myCost;
	
	public TowerInfoObject(String towerName, String imageLocation, int cost) {
		myName = towerName;
		myImageLocation = imageLocation;
		myCost = cost;
	}
	
	public void setNextTower(TowerInfoObject upgrade) {
		myUpgrade = upgrade;
	}
	
	public String getName() {
		return myName;
	}
	
	public String getImageLocation() {
		return myImageLocation;
	}
	
	public int getCost() {
		return myCost;
	}
	
	public TowerInfoObject getMyUpgrade() {
		return myUpgrade;
	}
}
