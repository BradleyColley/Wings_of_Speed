package com.project.wos;

/*
 * This class acts as the main activity for Wings of Speed to run onto Android
 * platforms. The class extends the AndroidApplication interface and implements the
 * PlayServices interface I've created within my core project creating the basic
 * functions for the Google Play Services to call upon.
 * 
 */

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.project.game.WOS;
import com.project.utilities.PlayServices;

import example.games.basegameutils.GameHelper;
import example.games.basegameutils.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

	public GameHelper _gameHelper; //Calls the GameHelper class from the example.games.basegameUtils package.
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Creates the GameHelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSignInSucceeded() {
				// TODO Auto-generated method stub
			}
		};

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WOS(this), config); //Loads the WOS class.
		_gameHelper.setup(gameHelperListener);
	}
	
	/*
	 * This method is responsible for signing into the Google Play Services to allow the user to sign 
	 * into their play services account. This launches a runnable thread to initialize the signin()
	 * function.
	 * 
	 */
	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("Android", "Sign in failure" + e.getMessage());
		}
	}

	/*
	 * This method is responsible for signing out of the Google Play Services to allow the user
	 * to sign out of their account. This launches a runnable thread to run the signOut() 
	 * function from the gameHelper class.
	 * 
	 */
	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable() {
				//@Override
				public void run()
				{
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("Android", "Sign out failure: " + e.getMessage());
		}
	}

	/*
	 * This method is responsible for submitting the users high score to the level 1
	 * leaderboard. This is done by inputting my leaderboard_id string from the ids.xml
	 * file containing the digits and sends the score variable once this method is called
	 * within the core project of my classes.
	 * 
	 */
	@Override
	public void submitLeaderScore1(long score) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
		} else {
			_gameHelper.onStart(this);
		}
	}

	/*
	 * This method is called upon once the user selects the leaderboard 1 button 
	 * within the leaderboard GUI of MenuScreen class. Once pressed it will launch 
	 * this functionality by retrieving the leaderboard data by inputting in my leaderboard
	 * level 1 ID only if the user is signed in.
	 * 
	 */
	@Override
	public void showLeaderboard1() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), 9002);
		} else {
			_gameHelper.onStart(this);
		}
	}

	/*
	 * This method is responsible for submitting the users high score to the level 2
	 * leaderboard. This is done by inputting my leaderboard_id2 string from the ids.xml
	 * file containing the digits and sends the score variable once this method is called
	 * within the core project of my classes.
	 * 
	 */
	@Override
	public void submitLeaderScore2(long score) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id2), score);
		} else {
			_gameHelper.onStart(this);
		}
	}
	
	/*
	 * This method is called upon once the user selects the leaderboard 2 button 
	 * within the leaderboard GUI of MenuScreen class. Once pressed it will launch 
	 * this functionality by retrieving the leaderboard data by inputting in my leaderboard
	 * level 2 ID only if the user is signed in.
	 * 
	 */
	@Override
	public void showLeaderboard2() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id2)), 9002);
		} else {
			_gameHelper.onStart(this);
		}	
	}

	/*
	 * This method returns the signedIn() Boolean statement if its true or false.
	 * 
	 */
	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}

	/*
	 * This method calls the onStart() method within the GameHelper class to
	 * start the activity.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		_gameHelper.onStart(this);
	}

	/*
	 * This method calls the onStop() method within the GameHelper class
	 * to stop the activity.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		_gameHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * This method gets called upon to get the achievements from the Google play Services
	 * once the user opens up the achievements screen within the settings of Wings of Speed. 
	 * 
	 */
	@Override
	public void getAchievements() {
		if (_gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(_gameHelper.getApiClient()), 9002);
		}
	}

	/*
	 * This method is called upon within the core project classes to unlock
	 * an achievement if its been met to its requirements defined in the 
	 * classes. If it has it will send the achievementId value through 
	 * the Google Play Services to unlock the achievement for the user.
	 *
	 */
	@Override
	public void unlockAchievement(String achievementId) {
		Games.Achievements.unlock(_gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void rateGame() {
		// Temporary method to be used for the future to implement the rate game functionality.
	}
}