package utilities.GSON.objectWrappers;

import java.util.List;

public class GameStateWrapper {
	
	private int myLevel;
	private double myHealth;
	private double myMoney;
	private String myName;
	private List<DataWrapper> myTowers;
	
	public GameStateWrapper(String name, int level, double health, double money, List<DataWrapper> towers){
		myName = name;
		myLevel = level;
		myHealth = health;
		myMoney = money;	
		myTowers = towers;
	}
	
	public String getName(){
		return myName;
	}
	
	public int getLevel(){
		return myLevel;
	}
	
	public double getHealth(){
		return myHealth;
	}
	
	public double getMoney(){
		return myMoney;
	}

	public List<DataWrapper> getTowerWrappers() {
		return myTowers;
	}
}
