package com.project.game;

/* 
 * GAME CLASS
 * 
 * This class extends the Game class which has an implementation of the ApplicationListener. By extending this class 
 * and the subclass within creates the WOS class to gain access to these useful methods to run the game.
 * 
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.project.screens.SplashScreen;
import com.project.utilities.Assets;
import com.project.utilities.AssetsManager;
import com.project.utilities.PlayServices;

public class WOS extends Game {

	public static PlayServices gServices;

	public WOS(PlayServices googleServices) {
		super();
		WOS.gServices = (googleServices);
	}
	
	/*
	 *The create() method is firstly created upon loading the application.
	 *It will call the setScreen() method to change the active screen to
	 *the SplashScreen instead alongside loading the splashScreen assets method
	 *within the Assets class. 
	 */
	@Override
	public void create() {
		Gdx.app.log("WingsOfSpeed", "Created");
		Assets.splashScreen_Assets();
		setScreen(new SplashScreen(this));
	}

	/*
	 * The dispose() method is only called upon once the application closes. 
	 * Once closed it will dispose the assets within the game and the 
	 * current screen active will be disposed off.
	 */
	@Override
	public void dispose() {
		Gdx.app.log("WingsOfSpeed", "Disposed");
		super.dispose();
		Assets.dispose();
		AssetsManager.dispose();
		getScreen().dispose();
	}
}
