package com.project.utilities;

/*
 * DataManagement class acts as the storage system to save Wings of Speed data into an 
 * hashmap using the Preferences API.
 */

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.screens.SelectBirdCharacter;
import com.project.universe.World;

public class DataManagement {

	public static Preferences prefs = Gdx.app.getPreferences("StorageSystem");
	private static String highScore_1, highScore_2, customScore, coins, twigScore, featherScore, birdSelector, 
	howtPly1, howtPly2;
	public static ArrayList <String> characterStore;

	public DataManagement() {	
		highScore_1 = "highscore1";
		highScore_2 = "highscore2";
		customScore = "highscore4";
		coins = "chest";

		twigScore = "level1_Score";
		featherScore = "level2_Score";

		birdSelector = "activeBird";

		//Declares all of the bird characters into the array.
		characterStore = new ArrayList<String>();
		characterStore.add("orangeBird");
		characterStore.add("blackBird");
		characterStore.add("yellowBird");
		characterStore.add("greenBird");	
		characterStore.add("owlBird");	

		prefs.putInteger(characterStore.get(0), 0);
		prefs.putInteger(characterStore.get(4), 4);
		prefs.flush(); //This saves the preferences file.

		howtPly1 = "level 1";
		howtPly2 = "level 2";
	}

	//Only appears for first timers.
	public void saveHowToPlay1() {
		if(!prefs.contains(howtPly1))
		{
			prefs.putInteger(howtPly1, 0);
			prefs.flush(); // This saves the preferences file.
			System.out.println("Saved first playtime");
		}
	}
	
	public void saveHowToPlay2() {
		if(!prefs.contains(howtPly2))
		{
			prefs.putInteger(howtPly2, 0);
			prefs.flush(); // This saves the preferences file.
			System.out.println("Saved first playtime");
		}
	}
	
	/*
	 * This method is used to save the highscore for all levels. 
	 */
	public void saveHighscore() {
		if(MenuScreen.statusOfGame.equals(GameState.GAMEOVER1) || MenuScreen.statusOfGame.equals(GameState.COMPLETE1)) {
			//If preferences doesn't contain a string value of highscore_1 then create one.
			if(!prefs.contains(highScore_1))
			{
				prefs.putInteger(highScore_1, 0);
				prefs.flush(); // This saves the preferences file.
			}
			
			//If the stored value is less than the World high score then,
			if(prefs.getInteger(highScore_1) < World.highscore)
			{
				prefs.remove(highScore_1); //Remove the current score.
				prefs.putInteger(highScore_1, (int) World.highscore); //Input a newer version.
				prefs.flush(); //Save the data.
			}
		} else if(MenuScreen.statusOfGame.equals(GameState.GAMEOVER2) || MenuScreen.statusOfGame.equals(GameState.COMPLETE2)) {
			//If preferences doesn't contain a string value of highscore_2 then create one.
			if(!prefs.contains(highScore_2))
			{
				prefs.putInteger(highScore_2, 0);
				prefs.flush(); // This saves the preferences file.
			}

			//If the stored value is less than the World high score then,
			if(prefs.getInteger(highScore_2) < World.highscore)
			{
				prefs.remove(highScore_2); //Remove the current score.
				prefs.putInteger(highScore_2, (int) World.highscore); //Input a newer version.
				prefs.flush(); //Save the data.
			}
		} else if(MenuScreen.statusOfGame.equals(GameState.GAMEOVERC) || MenuScreen.statusOfGame.equals(GameState.COMPLETEC)) {
			//If preferences doesn't contain a string value of customScore then create one.
			if(!prefs.contains(customScore))
			{
				prefs.putInteger(customScore, 0); //Remove the current score.
				prefs.flush(); // This saves the preferences file.
			}

			//If the stored value is less than the World high score then,
			if(prefs.getInteger(customScore) < World.highscore)
			{
				prefs.remove(customScore); //Remove the current score.
				prefs.putInteger(customScore, (int) World.highscore); //Input a newer version.
				prefs.flush(); //Save the data.
			}
		}
	}

	/*
	 * This method is used to save the coin amount.
	 */
	public void saveCoins() {
		//If the hashmap doesn't contain the string value of coins then create one.
		if(!prefs.contains(coins))
		{
			prefs.putInteger(coins, 0);
			prefs.flush(); // This saves the preferences file.
			System.out.println("Created coin chest");
		} 

		if(prefs.contains(coins))
		{
			int updatedCoin = prefs.getInteger(coins) + World.getCoinScore(); //Updates the coin amount value

			prefs.putInteger(coins, updatedCoin); //Adds in the a new value.
			prefs.flush(); //Saves.
		}
	}

	/*
	 * This method is used to save the level one objective score once reached to 25.
	 */
	public void saveLvlOneScore() {
		//If the game state equals COMPLETE1 then,
		if(MenuScreen.statusOfGame == GameState.COMPLETE1) {
			//Create a element within the hashmap containing twigScore if it doesn't exist.
			if(!prefs.contains(twigScore))
			{
				prefs.putInteger(twigScore, 0);
				prefs.flush();
			}

			//If the twigScore is less than the World twigScore then,
			if(prefs.getInteger(twigScore) < World.twigScore)
			{
				prefs.remove(twigScore); //Remove value
				prefs.putInteger(twigScore, (int) World.twigScore); //Add in a newer version
				prefs.flush(); //Save data.
			}
		}
	}

	/*
	 * This method is used to save the level two objective score once reached to 30.
	 */
	public void saveLvlTwoScore() {
		//If the game state equals COMPLETE2 then,
		if(MenuScreen.statusOfGame == GameState.COMPLETE2) {
			//Create a element within the hashmap containing featherScore if it doesn't exist.
			if(!prefs.contains(featherScore))
			{
				prefs.putInteger(featherScore, 0);
				prefs.flush();
			}

			if(prefs.getInteger(featherScore) < World.featherScore)
			{
				prefs.remove(featherScore); //Remove value 
				prefs.putInteger(featherScore, (int) World.featherScore); //Add in a newer version
				prefs.flush(); //Save data.
			}
		}
	}

	/*
	 * This method is only used when the user chooses a new bird character.
	 */
	public void saveBird() {
		if(!prefs.contains(birdSelector))
		{
			prefs.putInteger(birdSelector, 0);
			prefs.flush(); 
		}
		if(prefs.getInteger(birdSelector) <= SelectBirdCharacter.birds.size())
		{
			prefs.remove(birdSelector);
			prefs.putInteger(birdSelector, Integer.valueOf(SelectBirdCharacter.trigger));
			prefs.flush();
		}
	}

	/*
	 * This method is only used when the user purchases bird 1 in the store.
	 */
	public void buyBird1() {
		if(!prefs.contains(characterStore.get(1))) {
			prefs.putInteger(characterStore.get(1), 1);
			prefs.flush(); 

		}
	}

	/*
	 * This method is only used when the user purchases bird 2 in the store.
	 */
	public void buyBird2() {
		if(!prefs.contains(characterStore.get(2))) {
			prefs.putInteger(characterStore.get(2), 2);
			prefs.flush(); 
		}
	}

	/*
	 * This method is only used when the user purchases bird 3 in the store.
	 */
	public void buyBird3() {
		if(!prefs.contains(characterStore.get(3))) {
			prefs.putInteger(characterStore.get(3), 3);
			prefs.flush(); 
		}
	}


	public static String getHighScore_1() {
		return highScore_1;
	}

	public static String getCoins() {
		return coins;
	}

	public static String getHighScore_2() {
		return highScore_2;
	}

	public static String getTwigScore() {
		return twigScore;
	}

	public static String getFeatherScore() {
		return featherScore;
	}

	public static String getBirdSelector() {
		return birdSelector;
	}

	public static String getCustomScore() {
		return customScore;
	}

	public static String getHowtPly1() {
		return howtPly1;
	}

	public static String getHowtPly2() {
		return howtPly2;
	}
}