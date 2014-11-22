package gamePlayer.towerUpgrade;

public class TowerUpgradeInfo {
	
	private String myName;
	private String myImagePath;
	private double mySellPrice;
	
	private double myX;
	private double myY;
	
	private String myUpgrade1Label;
	private String myUpgrade2Label;
	private Class upgradeTower1;
	private Class upgradeTower2;

	public TowerUpgradeInfo(String name, String imagePath, double sellPrice, double x, double y,
			String upgrade1, String upgrade2,  Class upgradeT1, Class upgradeT2) {
		
		myName = name;
		myImagePath = imagePath;
		mySellPrice = sellPrice;
		
		myX = x;
		myY = y;
		
		myUpgrade1Label = upgrade1;
		myUpgrade2Label = upgrade2;
		upgradeTower1 = upgradeT1;
		upgradeTower2 = upgradeT2;
	}
	
	public String getName(){
		return myName;
	}
	
	public String getImagePath(){
		return myImagePath;
	}
	
	public double getPrice(){
		return mySellPrice;
	}
	
	public double getX(){
		return myX;
	}
	
	public double getY(){
		return myY;
	}
	
	public String getLabel1(){
		return myUpgrade1Label;
	}
	
	public String getLabel2(){
		return myUpgrade2Label;
	}
	
	public Class getTower1(){
		return upgradeTower1;
	}
	
	public Class getTower2(){
		return upgradeTower2;
	}
}
