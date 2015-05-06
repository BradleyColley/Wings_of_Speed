package com.project.objects;

/*
 * This BirdCharacter class defines the bird preferences of its physics, alive conditions 
 * and which bird is currently active to draw the collisions around.  
 */

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.utilities.Assets;
import com.project.utilities.DataManagement;

import static com.project.utilities.Global.*;

public class BirdCharacter {

	private Vector2 position, acceleration;
	private Circle birdCircle;
	private float width, height, r1, r2;
	
	public Vector2 velocity;
	public static float num = H/3f;
	public boolean isAlive, shieldActive;

	public BirdCharacter(float x, float y, float width, float height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		birdCircle = new Circle(); 
		
		isAlive = true;	
	}

	/*
	 * This method updates the birds attributes within the gameplay.
	 */
	public void update(float delta) {

		velocity.add(acceleration.cpy().scl(delta)); //Adds a copy of acceleration to velocity.

		//Sets the gravity of the birds atmosphere.
		if (velocity.y < 0) {
			velocity.y = -160;
		}

		position.add(velocity.cpy().scl(delta)); //Adds a copy of the velocity to the position.

		//Ceiling collision, checks the birds position against the height of the screen
		if (position.y >= H/1.1f) {
			velocity.y = -300;
		}
		
		//If the bird is alive then perform these collision statements to draw the circle around the active bird.
		if(isAlive == true) {
			if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0)
			{
				r1 = (W/82f) / Assets.birds[0].getWidth();
				r2 = (H/82f) / Assets.birds[0].getHeight();
				birdCircle.set(position.x + W/35f, position.y + H/24f, (r1*Assets.birds[0].getWidth()) +
						(r2*Assets.birds[0].getHeight()));
			} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1) 
			{
				r1 = (W/76f) / Assets.birds[0].getWidth();
				r2 = (H/76f) / Assets.birds[0].getHeight();
				birdCircle.set(position.x + W/37f, position.y + H/24f, (r1*Assets.birds[0].getWidth()) +
						(r2*Assets.birds[0].getHeight()));
			} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2) 
			{
				r1 = (W/90f) / Assets.birds[10].getWidth();
				r2 = (H/90f) / Assets.birds[10].getHeight();
				birdCircle.set(position.x + W/30f, position.y + H/30f, (r1*Assets.birds[10].getWidth()) +
						(r2*Assets.birds[10].getHeight()));
			} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3) 
			{
				r1 = (W/90f) / Assets.birds[15].getWidth();
				r2 = (H/90f) / Assets.birds[15].getHeight();
				birdCircle.set(position.x + W/35f, position.y + H/32f, (r1*Assets.birds[15].getWidth()) +
						(r2*Assets.birds[15].getHeight()));
			}
		}
	}

	/*
	 * This method acts as the users input to move the bird by 
	 * tapping on the screen.
	 */
	public void onClick() {
		if (isAlive) {
			velocity.y = num; //Sets the velocity of the bird to num value.
			if(soundStatus.equals(true) && soundSwitch.equals(true)) { //Sound effect statement.
				Assets.tweet.play(0.1f);
			}
		}
	}

	/*
	 * This method is responsible for the birds death condition. It will set the isAlive Boolean condition
	 * to false and turn off the sound effects upon death so no effects carry on running in the background. Then
	 * set the game state condition to game over depending what the game state was currently equaling too.
	 */
	public void die() {
		isAlive = false;
		if(soundStatus == true) {
			soundSwitch = false;
		}
		bird.shieldActive = false; //Turns off the shield if the user received it through a creature. 
		if(MenuScreen.statusOfGame == GameState.RUNNING_1)
		{
			MenuScreen.statusOfGame = GameState.GAMEOVER1;
		} else if(MenuScreen.statusOfGame == GameState.RUNNING_2)
		{
			MenuScreen.statusOfGame = GameState.GAMEOVER2;
		} else if(MenuScreen.statusOfGame == GameState.RUNNING_C)
		{
			MenuScreen.statusOfGame = GameState.GAMEOVERC;
		}
	}

	/*
	 * This method is called within the World class to restart the bird's position.
	 */
	public void onRestart(float i) {
		position.y = i;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = -W;
		birdCircle.set(position.x + position.x/9f, i + position.x/10f, (W/40f) / Assets.birds[0].getWidth());
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public Circle getbirdCircle() {
		return birdCircle;
	}

	public void setbirdCircle(Circle birdCircle) {
		this.birdCircle = birdCircle;
	}
}
