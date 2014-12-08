package utilities.GSON.objectWrappers;

public class GameStateWrapper {
	
	private int myLevel;
	private int myHealth;
	private int myMoney;
	private String myName;
	
	public GameStateWrapper(String name, int level, int health, int money){
		myName = name;
		myLevel = level;
		myHealth = health;
		myMoney = money;		
	}
	
	public String getName(){
		return myName;
	}
	
	public int getLevel(){
		return myLevel;
	}
	
	public int getHealth(){
		return myHealth;
	}
	
	public int getMoney(){
		return myMoney;
	}

}
