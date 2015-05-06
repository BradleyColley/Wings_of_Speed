package com.project.universe;

/*
 * This World class acts as the Wings of Speed universe by updating the gameplay mechanics.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.project.game.WOS;
import com.project.objects.BirdCharacter;
import com.project.objects.Clouds;
import com.project.objects.Collectables;
import com.project.objects.Enemy;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.utilities.Assets;
import com.project.utilities.DataManagement;
import com.project.utilities.InputHandler;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class World {

	private static boolean downBoundary;
	private static float uBorder, dBorder;
	private static int coinScore;
	private static Random r;

	public static float midPointY;
	public static int highscore, twigScore, featherScore, object, i = -1;
	public static List<Integer> abilities;

	public World(float midPointX, float _midPointY) {
		sManager = new ScrollManager();
		dManager = new DataManagement();

		//Sets the position of the bird character onto the gameplay screen.
		bird = new BirdCharacter(midPointX/2, midPointY + 5, Assets.birds[0].getWidth(), Assets.birds[0].getHeight());

		midPointY = _midPointY;

		//Defines the border collisions of the gameplay.
		dBorder = -H/20f;
		uBorder = H/1.1f;
		
		abilities = new ArrayList<Integer>();
		r = new Random();
	}

	/*
	 * This method is the most important out of them all as it constantly updates the 
	 * gameplay mechanics depending on the game state condition following the switch
	 * case statement.
	 */
	public void update(float delta) {
		downBoundary = bird.getPosition().y <= dBorder;

		switch (MenuScreen.statusOfGame) {
		case LEVEL1:
			restartLevel1();
			Assets.tweet.stop();
			if(soundStatus == true) {
				soundSwitch = false;
			}
			break;

		case RUNNING_1:
			winValue = 25;
			if(soundStatus == true) {
				soundSwitch = true;
			}
			updateRunning(delta);
			if(twigScore == winValue) {
				MenuScreen.statusOfGame = GameState.COMPLETE1;
				highscore = (int) Rendering.miles;
				dManager.saveHighscore();
				dManager.saveCoins();
				dManager.saveLvlOneScore();
				Rendering.drawLevel();  
			}
			break;

		case LEVEL2:
			restartLevel2();
			if(soundStatus == true) {
				soundSwitch = false;
			}
			break;

		case RUNNING_2:
			winValue = 30;
			if(soundStatus == true) {
				soundSwitch = true;
			}
			updateRunning(delta);
			if(featherScore == winValue) {
				MenuScreen.statusOfGame = GameState.COMPLETE2;
				highscore = (int) Rendering.miles;
				dManager.saveHighscore();
				dManager.saveCoins();	
				dManager.saveLvlTwoScore();
				Rendering.drawLevel();  
			}
			break;

		case READY_C:
			Gdx.input.setInputProcessor(new InputHandler(bird));
			restartLevelC();
			if(soundStatus == true) {
				soundSwitch = false;
			}
			break;

		case RUNNING_C:
			if(soundStatus == true) {
				soundSwitch = true;
			}
			String value = levelInput.get(8).substring(2);
			winValue = Integer.valueOf(value);

			updateRunning(delta);

			if(levelInput.get(7).equals("  Twig")) {
				object = twigScore;
			} else {
				object = featherScore;
			}

			if(object == winValue)
			{
				MenuScreen.statusOfGame = GameState.COMPLETEC;
				highscore = (int) Rendering.miles;
				dManager.saveHighscore();
				dManager.saveCoins();
				Rendering.drawLevel();  
			}
			break;

		case GAMEOVER1:
		case GAMEOVER2:
		case GAMEOVERC:
			gameoverRunning();
			break;

		case COMPLETE1:
		case COMPLETE2:
		case COMPLETEC:
			completeRunning();
			if(soundStatus == true) {
				soundSwitch = false;
			}
			break;

		default:
			break;
		}
	}

	/*
	 * This method works when the gameplay is running which will keep the gameplay
	 * updating until its game over due to a collision.
	 */
	public void updateRunning(float delta) {

		/*
		 * This if statement defines game over. If the bird shield is not active and has collided into a cloud or
		 * fallen outside the boundary or collided into an enemy or collided into a bat then run the game over
		 * operations below.
		 */
		if(bird.shieldActive == false && Clouds.collision(bird) || downBoundary || bird.shieldActive == false && Enemy.enemy1Collision(bird) 
				|| bird.shieldActive == false && Enemy.batCollision(bird)) {
			
			//If the bird collided into the enemy then produce this different sound effect.
			if(Enemy.enemy1Collision(bird) && soundStatus.equals(true)) {
				Assets.death2.play(0.1f);
			} else if(soundStatus.equals(true)) {
				Assets.death1.play(0.1f);
			}
			
			highscore = (int) Rendering.miles; //Highscore equals the distance counter.

			//If highscore is more than or equal to the stored highscore level 1 value then 
			//submit it via the Google Play Services leaderboard 1.
			if(highscore >= DataManagement.prefs.getInteger(DataManagement.getHighScore_1()) 
					&& MenuScreen.statusOfGame.equals(GameState.RUNNING_1)) {
				WOS.gServices.submitLeaderScore1(highscore);
			} 

			//If highscore is more than or equal to the stored highscore level 2 value then 
			//submit it via the Google Play Services leaderboard 2.
			if(highscore >= DataManagement.prefs.getInteger(DataManagement.getHighScore_2())
					&& MenuScreen.statusOfGame.equals(GameState.RUNNING_2)) {
				WOS.gServices.submitLeaderScore2(highscore);
			}

			//If the coinscore is more than or equal to 100 then unlock achievement.
			if (WOS.gServices.isSignedIn() && coinScore >= 100) {
				WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQAA");
			}

			//If the coinscore is more than or equal to 500 then unlock achievement.
			if (WOS.gServices.isSignedIn() && coinScore >= 500) {
				WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQAQ");
			}

			bird.die(); //Call the BirdCharacter die() method.
			dManager.saveHighscore(); //Save the highscore.
			dManager.saveCoins(); //Save the coins.
			Rendering.drawLevel(); //Draw the retry level interface from Rendering class.
		} else {
		//If the bird hasn't met these requirements then carry on updating the gameplay.
			bird.update(delta);
			sManager.update(delta); 	
		}

		//If the twig has collided into a cloud then reset the twig off the screen
		//to regenerate a new location.
		if(Collectables.twigCloudCollision()){
			sManager.objective.get(0).reset(-W,0);
		}

		//If the twig has collided into the bird then reset the twig off the screen
		//and produce a sound effect. Then add 1 point to the objective score.
		if(Collectables.twigCollision(bird)) {
			sManager.objective.get(0).reset(-W,0);
			if(soundStatus.equals(true) && soundSwitch.equals(true)) {
				Assets.collectable.play(0.1f);
			}
			twigScore += 1;
		} else if(Collectables.featherCollision(bird)) {
		//Else reset the feather off the screen and add 1 point to the score.
			sManager.objective.get(0).reset(-W,0);
			if(soundStatus.equals(true) && soundSwitch.equals(true)) {
				Assets.collectable.play(0.1f);
			}
			featherScore += 1;
		}

		//If the bird has collided into a coin then produce a sound and reset the coin off the screen.
		//Then add 1 point to the coin counter and make sure the birds velocity is set to the defaulted value
		//because one of the setback abilities decreases the velocity. 
		if(Collectables.coinCollision(bird)) {
			sManager.coin.get(0).reset(-W,0);

			if(soundStatus.equals(true) && soundSwitch.equals(true)) {
				Assets.collect.play(0.1f);
			}
			coinScore += 1;
			bird.num = H/3f;
		}
		
		//If the bird has collided into a creature then reset the creature off the screen and 
		//retrieve a random value and match it against the switch case statement to give the user
		//a powerup or setback ability.
		if(Collectables.powerUpCollision(bird)) {
			sManager.pUps.get(0).reset(-W,0);
			i = r.nextInt(4);
			switch(i) {
			case 0:
				if(MenuScreen.statusOfGame == GameState.RUNNING_1 || levelInput.get(7).equals("  Twig"))
				{
					Rendering.powerUp = "Reset twigs";
					twigScore = 0;
				} else if(MenuScreen.statusOfGame == GameState.RUNNING_2 || levelInput.get(7).equals("  Feather"))
				{
					Rendering.powerUp = "Reset feathers";
					featherScore = 0;
				}
				if(soundStatus.equals(true) && soundSwitch.equals(true)) {
					Assets.pu1.play(0.1f);
				}
				break;

			case 1:
				Rendering.powerUp = "+10 Coins!";
				if(soundStatus.equals(true) && soundSwitch.equals(true)) {
					Assets.pu2.play(0.1f);
				}
				coinScore += 10;
				break;

			case 2:
				Rendering.powerUp = "Decreased flight!";
				if(soundStatus.equals(true) && soundSwitch.equals(true)) {
					Assets.pu1.play(0.1f);
				}
				bird.num = H/6f; //Decreases the bird's velocity.
				break;
				
			case 3:
				Rendering.powerUp = "Shield for 12s!";
				if(soundStatus.equals(true) && soundSwitch.equals(true)) {
					Assets.pu2.play(0.1f);
				}
				bird.shieldActive = true;
				Timer.schedule(new Task() {
					@Override
					public void run() {
						bird.shieldActive = false;
					}
				}, 12f);
				break;
			}
		}
	}

	/*
	 * This method runs when its game over which will stop the ScrollManager class
	 * from scrolling its objects and change the user input to interact with the stage.
	 */
	public void gameoverRunning() {
		sManager.stop();
		Gdx.input.setInputProcessor(Rendering.stage);  	
	}

	/*
	 * This method runs when the level is complete which will stop the ScrollManager class
	 * from scrolling its objects and change the user input to interact with the stage.
	 */
	public void completeRunning() {
		sManager.stop();
		Gdx.input.setInputProcessor(Rendering.stage);  
	}

	/*
	 * This method works once the game state equals LEVEL1 or GAMEOVER to reset
	 * all of the level 1 gameplay essentials.
	 */
	public static void restartLevel1() {
		Rendering.rLayout.reset();
		twigScore = 0;
		Rendering.miles = 0;
		coinScore = 0;
		highscore = 0;
		bird.onRestart(midPointY + 5);
		bird.getbirdCircle().setPosition(-100000, -10000);
		bird.isAlive = true;
		bird.num = H/3f;
		sManager.resetLvlOneSpeed();
		sManager.onRestart();
		MenuScreen.statusOfGame = GameState.LEVEL1;
	}

	/*
	 * This method works once the game state equals LEVEL2 or GAMEOVER to reset
	 * all of the level 2 gameplay essentials.
	 */
	public static void restartLevel2() {
		Rendering.rLayout.reset();
		featherScore = 0;
		Rendering.miles = 0;
		coinScore = 0;
		highscore = 0;
		bird.isAlive = true;
		bird.onClick();
		bird.onRestart(midPointY + 5);
		bird.getbirdCircle().setPosition(-100000, -10000);
		sManager.resetLvlTwoSpeed();
		sManager.level2Restart();
		MenuScreen.statusOfGame = GameState.LEVEL2;
	}

	/*
	 * This method works once the game state equals READYC or GAMEOVER to reset
	 * all of the custom level gameplay essentials.
	 */
	public static void restartLevelC() {
		Rendering.rLayout.reset();
		object = 0;
		twigScore = 0;
		featherScore = 0;
		Rendering.miles = 0;
		coinScore = 0;
		highscore = 0;
		bird.isAlive = true;
		bird.num = H/3f;
		bird.onRestart(midPointY + 5);
		bird.getbirdCircle().setPosition(-100000, -10000);
		sManager.restartCustom();
		MenuScreen.statusOfGame = GameState.READY_C;
	}

	/*
	 * This method gets called upon to change the game state to RUNNING_1.
	 */
	public static void startLevel1() {
		MenuScreen.statusOfGame = GameState.RUNNING_1;
	}

	/*
	 * This method gets called upon to change the game state to RUNNING_2.
	 */
	public static void startLevel2() {
		MenuScreen.statusOfGame = GameState.RUNNING_2;
	}

	/*
	 * This method gets called upon to change the game state to RUNNING_C.
	 */
	public static void startLevel3() {
		MenuScreen.statusOfGame = GameState.RUNNING_C;
	}

	public static float getdBorder() {
		return dBorder;
	}

	public static void setdBorder(float dBorder) {
		World.dBorder = dBorder;
	}

	public static float getuBorder() {
		return uBorder;
	}

	public static void setuBorder(float uBorder) {
		World.uBorder = uBorder;
	}

	public static int getCoinScore() {
		return coinScore;
	}

	public static void setCoinScore(int coinScore) {
		World.coinScore = coinScore;
	}
	
	public BirdCharacter getBird() {
		return bird;
	}

	public Rendering getRenderer() {
		return renderer;
	}
}
