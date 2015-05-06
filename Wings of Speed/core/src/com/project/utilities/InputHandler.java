package com.project.utilities;

/*
 * This InputHandler class implements the InputProcessor interface to use its methods to 
 * receive input events from touching the screen.
 */

import com.badlogic.gdx.InputProcessor;
import com.project.objects.BirdCharacter;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.universe.Rendering;
import com.project.universe.World;

public class InputHandler implements InputProcessor {

	public static BirdCharacter myBird;
	public float delta;

	public InputHandler(BirdCharacter birdCharacter) {
		myBird = birdCharacter;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		//These if statements change the game state to run the gameplay.
		if (MenuScreen.getStatusOfGame().equals(GameState.LEVEL1) && Rendering.info.equals(false)) {
			World.startLevel1();
		} else if (MenuScreen.getStatusOfGame().equals(GameState.LEVEL2) && Rendering.info.equals(false)) 
		{
			World.startLevel2();
		}  else if (MenuScreen.getStatusOfGame().equals(GameState.READY_C)) 
		{
			World.startLevel3();
		}

		myBird.onClick(); //Calls the BirdCharacter class onClick() method to move the bird character.

		return true; //Returns true once the user has tapped.
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}