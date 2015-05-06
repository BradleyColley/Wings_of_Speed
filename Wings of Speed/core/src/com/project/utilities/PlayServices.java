package com.project.utilities;

/*
 * This PlayServices interface acts as the foundation of calling the Google Play
 * Services API functions.
 */

public interface PlayServices {

	public void signIn();

	public void signOut();

	public void rateGame(); //Temporary code to implement in the future for users to rate the game.

	public void submitLeaderScore1(long score);

	public void submitLeaderScore2(long score);

	public void showLeaderboard1();

	public void showLeaderboard2();

	public boolean isSignedIn();

	public void unlockAchievement(String achievementId);

	public void getAchievements();
}
