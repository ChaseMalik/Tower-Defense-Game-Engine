package utilities.GSON.objectWrappers;

public class GeneralSettingsWrapper {

	private int myStartingCash;
	private int myStartingHealth;

	public GeneralSettingsWrapper(int startingCash, int startingHealth){
		myStartingCash = startingCash;
		myStartingHealth = startingHealth;
	}

	public int getStartingCash(){
		return myStartingCash;

	}

	public int getStartingHealth(){
		return myStartingHealth;
	}

}
