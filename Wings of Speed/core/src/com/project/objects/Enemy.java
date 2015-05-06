package com.project.objects;

/*
 * The Enemy class extends the Scrollable class to gain access to its protected variables and methods. Purpose of this class
 * is to define all of the Enemy objects and collisions for the ScrollManager, World and Rendering classes to retrieve data 
 * from.
 */

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.utilities.Assets;
import com.project.utilities.ScrollManager;
import com.project.utilities.Scrollable;

import static com.project.utilities.Global.*;

public class Enemy extends Scrollable {

	public static Circle c1a, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19;
	public static List <Circle> circle;
	public static Rectangle r1a;
	public static Float[] scale;

	public Enemy(float x, float y, int width, int height, float scrollSpeed) 
	{
		super(x, y, width, height, scrollSpeed);
		scale = new Float[8];
		c1a = new Circle();
		r1a = new Rectangle();
		//Adds the circle collisions into an ArrayList to make it easier to select upon.
		circle = new ArrayList<Circle>();
		circle.add(c2 = new Circle());
		circle.add(c3 = new Circle());
		circle.add(c4 = new Circle());
		circle.add(c5 = new Circle());
		circle.add(c6 = new Circle());
		circle.add(c7 = new Circle());
		circle.add(c8 = new Circle());
		circle.add(c9 = new Circle());
		circle.add(c10 = new Circle());
		circle.add(c11 = new Circle());
		circle.add(c12 = new Circle());
		circle.add(c13 = new Circle());
		circle.add(c14 = new Circle());
		circle.add(c15 = new Circle());
		circle.add(c16 = new Circle());
		circle.add(c17 = new Circle());
		circle.add(c18 = new Circle());
		circle.add(c19 = new Circle());
	}

	/*
	 * This method defines enemy bird collisions. The if statements run which collision
	 * to select upon depending on the game state or the users selection within the custom
	 * level.
	 * 
	 */
	public static void enemy1Boundaries() {
		if(MenuScreen.statusOfGame.equals(GameState.RUNNING_1) || MenuScreen.statusOfGame.equals(GameState.RUNNING_2) || levelInput.get(3).equals("  Bird")) {
			scale[0] = W/140f / Assets.enemy[0].getWidth();
			scale[1] = H/140f / Assets.enemy[0].getHeight();
			scale[2] = W/20f / Assets.enemy[0].getWidth();
			scale[3] = H/28f / Assets.enemy[0].getHeight();

			c1a.set(ScrollManager.enemies.get(0).getX() + (W/68f), 
					ScrollManager.enemies.get(0).getY() + (H/20f), (scale[0] * Assets.enemy[0].getWidth())+(scale[1] *  Assets.enemy[0].getHeight()));

			r1a.set(ScrollManager.enemies.get(0).getX() + (W/50f), ScrollManager.enemies.get(0).getY() + (H/75f), 
					scale[2] * Assets.enemy[0].getWidth(), (scale[3]) * Assets.enemy[0].getHeight());
		} 
		else if(levelInput.get(3).equals("  Bat")) {
			scale[0] = W/55f / Assets.enemy[22].getWidth();
			scale[1] = H/55f / Assets.enemy[22].getHeight();

			c1a.set(ScrollManager.enemies.get(0).getX() + (W/26f), 
					ScrollManager.enemies.get(0).getY() + (H/14f), (scale[0] * Assets.enemy[22].getWidth())+(scale[1] *  Assets.enemy[22].getHeight()));
		}  
		else if(levelInput.get(3).equals("  Wasp")) {
			scale[0] = W/60f / Assets.enemy[16].getWidth();
			scale[1] = H/60f / Assets.enemy[16].getHeight();

			c1a.set(ScrollManager.enemies.get(0).getX() + (W/35f), 
					ScrollManager.enemies.get(0).getY() + (H/30f), (scale[0] * Assets.enemy[16].getWidth())+(scale[1] *  Assets.enemy[16].getHeight()));
		}
	}

	/*
	 * This method is responsible for the bat collisions and runs a for loop to repeat the 
	 * same collision shapes around the bat objects.
	 */
	public static void batBoundaries() {
		scale[4] = W/62f / Assets.en2[0].getWidth();
		scale[5] = H/62f / Assets.en2[0].getHeight();

		for(int i =0; i <= 17;)
		{
			circle.get(i).set(ScrollManager.bats.get(i).getX() + (W/35f), 
					ScrollManager.bats.get(i).getY() + (H/16.5f), (scale[5] * Assets.en2[0].getWidth())+(scale[5] *  Assets.en2[0].getHeight()));
			i++;
		}
	}

	/*
	 * This method is responsible for calculating the collisions between the character and bird enemy. 
	 * Once the character collides into the enemy bird it will return a true Boolean statement, activating a method
	 * within the World class to call upon the game over method.
	 */
	public static boolean enemy1Collision(BirdCharacter bird) {
		return (Intersector.overlaps(bird.getbirdCircle(), c1a) || Intersector.overlaps(bird.getbirdCircle(), r1a));
	}

	/*
	 * This method is responsible for calculating the collisions between the bird and bats. 
	 * Once the bird collides into bats it will return a true Boolean statement, activating a method
	 * within the World class to call upon a new functionality.
	 */
	public static boolean batCollision(BirdCharacter bird) {
		return (Intersector.overlaps(bird.getbirdCircle(), circle.get(0)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(1)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(2)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(3)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(4)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(5)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(6)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(7)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(8)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(9)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(10)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(11)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(12)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(13)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(14)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(15)) ||
				Intersector.overlaps(bird.getbirdCircle(), circle.get(16)) || Intersector.overlaps(bird.getbirdCircle(), circle.get(17)));
	}
	
	/*
	 * This method gets called within the World class to reset all of the 
	 * enemy positions and scrollspeed.
	 */
	public void onRestart(float x, float y, float scrollSpeed) {
		position.x = x;
		position.y = y;
		velocity.x = scrollSpeed;
	}	
}