package gameEngine.Data;


public class NullTowerInfoObject extends TowerInfoObject{

	public NullTowerInfoObject() {
		super("No upgrade available", "./src/gamePlayer/playerImages/nullupgrade.png", 0, 0, 0);
	}
	
	@Override
	public TowerInfoObject getMyUpgrade(){
		return new NullTowerInfoObject();
	}
	
	@Override
	public String getName(){
		return "No update available";
	}

}
