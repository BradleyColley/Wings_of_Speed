package com.project.objects;

/*
 * The Collectables class extends the Scrollable class to gain access to its protected variables and methods. Purpose of this class
 * is to define all of the collectable objects and collisions for the ScrollManager, World and Rendering classes to retrieve data 
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

public class Collectables extends Scrollable {
	public static Float[] scale;
	public static Rectangle r1a, r1b, r2a;
	public static List<Rectangle> rec;
	public static Circle c1a, c2a, c3a;
	public static boolean isAlive;

	public Collectables(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		scaling();
		r1a = new Rectangle(); r1b = new Rectangle(); r2a = new Rectangle(); 
		c1a = new Circle(); c2a = new Circle(); c3a = new Circle(); 
		rec = new ArrayList<Rectangle>();
		rec.add(r1a);
		rec.add(r1b);
	}

	/*
	 * This method is to scale the twigs.
	 */
	public void scaling() {
		scale = new Float[12];

		scale[0] = (W/30f) / Assets.twig.getWidth();
		scale[1] = (W/26f) / Assets.twig.getHeight();
		scale[2] = (W/80f) / Assets.twig.getWidth();
		scale[3] = (H/30f) / Assets.twig.getHeight();
	}

	/*
	 * This method is responsible for coin collisions.
	 */
	public static void coinBoundaries() {	
		scale[6] = (W/140f) / Assets.coin.getWidth();
		scale[7] = (W/140f) / Assets.coin.getHeight();

		//Draws a circle around the coin object.
		c1a.set(ScrollManager.coin.get(0).getX() + (W/68f), 
				ScrollManager.coin.get(0).getY() + (H/40f), (scale[6] * Assets.coin.getWidth())+(scale[7] *  Assets.coin.getHeight()));
	}

	/*
	 * This method is responsible for twig collisions.
	 */
	public static void twigBoundaries() {
		//Draws rectangles around the twig object to define collisions.
		r1a.set(ScrollManager.objective.get(0).getX() + (W/60f), ScrollManager.objective.get(0).getY(), 
				scale[2] * Assets.twig.getWidth(), (scale[1] / 1.6f) * Assets.twig.getHeight());
		r1b.set(ScrollManager.objective.get(0).getX() + (W/160f), ScrollManager.objective.get(0).getY() + (H/38f), 
				r1a.width, (scale[1] / 1.8f) * Assets.twig.getHeight());
	}

	/*
	 * This method is responsible for feather collisions.
	 */
	public static void featherBoundaries() {
		scale[8] = (W/125f) / Assets.feather.getWidth();
		scale[9] = (W/125f) / Assets.feather.getHeight();
		c3a.set(ScrollManager.objective.get(0).getX() + (W/68f), ScrollManager.objective.get(0).getY() + (H/38f), 
				(scale[8] * Assets.feather.getWidth())+(scale[9] *  Assets.feather.getHeight()));
	}

	/*
	 * This method is responsible for powerup and setback creature collisions. The if statements 
	 * define which collision to select upon depending on the game state or users selection within the 
	 * custom level.
	 */
	public static void powerUpBoundaries() {
		if(MenuScreen.statusOfGame.equals(GameState.RUNNING_1) || levelInput.get(5).equals("  Dragonfly")) {
			scale[4] = (W/70f) / Assets.crea[1].getWidth();
			scale[5] = (H/80f) / Assets.crea[1].getHeight();
			scale[10] = (W/100f) / Assets.crea[1].getWidth();
			scale[11] = (H/180f) / Assets.crea[1].getHeight();

			c2a.set(ScrollManager.pUps.get(0).getX() + ((c2a.radius/2) + (W/80f)), 
					ScrollManager.pUps.get(0).getY() + ((c2a.radius/2) + (H/70f)), 
					(scale[10]*Assets.crea[1].getWidth())+(scale[11]*Assets.crea[1].getHeight()));

			r2a.set(ScrollManager.pUps.get(0).getX() + (W/30f), ScrollManager.pUps.get(0).getY() + (H/85f), 
					scale[4] * Assets.crea[1].getWidth(), (scale[5]/2.5f) * Assets.crea[1].getHeight());
		} 
		else if(MenuScreen.statusOfGame.equals(GameState.RUNNING_2)  || levelInput.get(5).equals("  Bee")) {
			scale[10] = (W/80f) / Assets.crea[17].getWidth();
			scale[11] = (H/220f) / Assets.crea[17].getHeight();

			c2a.set(ScrollManager.pUps.get(0).getX() + ((c2a.radius/2) + (W/105f)), 
					ScrollManager.pUps.get(0).getY() + ((c2a.radius/2) + (H/90f)), 
					(scale[10]*Assets.crea[17].getWidth())+(scale[11]*Assets.crea[17].getHeight()));
		} 
		else if(levelInput.get(5).equals("  Butterfly")) {
			scale[4] = (W/60f) / Assets.crea[26].getWidth();
			scale[5] = (H/32f) / Assets.crea[26].getHeight();
			scale[10] = (W/100f) / Assets.crea[26].getWidth();
			scale[11] = (H/220f) / Assets.crea[26].getHeight();

			c2a.set(ScrollManager.pUps.get(0).getX() + ((c2a.radius/2) + (W/105f)), 
					ScrollManager.pUps.get(0).getY() + ((c2a.radius/2) + (H/80f)), 
					(scale[10]*Assets.crea[26].getWidth())+(scale[11]*Assets.crea[26].getHeight()));

			r2a.set(ScrollManager.pUps.get(0).getX() + (W/45f), ScrollManager.pUps.get(0).getY() + (H/280f), 
					scale[4] * Assets.crea[26].getWidth(), (scale[5] * Assets.crea[26].getHeight()));
		} 
		else if(levelInput.get(5).equals("  Wasp")) {
			scale[10] = (W/80f) / Assets.crea[42].getWidth();
			scale[11] = (H/220f) / Assets.crea[42].getHeight();

			c2a.set(ScrollManager.pUps.get(0).getX() + ((c2a.radius/2) + (W/110f)), 
					ScrollManager.pUps.get(0).getY() + ((c2a.radius/2) + (H/80f)), 
					(scale[10]*Assets.crea[42].getWidth())+(scale[11]*Assets.crea[42].getHeight()));
		} 
	}
	
    /*
     * This method is responsible for calculating the collisions between a coin and the bird.
     * Once the coin collides into the bird character it will return a true Boolean statement, activating a method
     * within the World class to call upon a new functionality. 
     */
	public static boolean coinCollision(BirdCharacter bird) {
		return (Intersector.overlaps(c1a, bird.getbirdCircle()));	
	}

	/*
	 * This method is responsible for calculating the collisions between a twig and the bird. 
	 * Once the twig collides into the bird character it will return a true Boolean statement, activating a method
	 * within the World class to call upon a new functionality.
	 */
	public static boolean twigCollision(BirdCharacter bird) {
		return (Intersector.overlaps(bird.getbirdCircle(), r1a) || Intersector.overlaps(bird.getbirdCircle(), r1b));
	}

	/*
	 * This method is responsible for calculating the collisions between the twig and clouds. 
	 * Once the twig collides into one of the clouds upon regeneration within the gameplay then the twig will need 
	 * to regenerate a new X and Y coordinates where a cloud doesn't exist on the coordinates grid.
	 */
	public static boolean twigCloudCollision() {

		int i = (int) ((int) Math.round((Math.random()*1)));
		Rectangle r = rec.get(i);

		return (Intersector.overlaps(Clouds.c1a, r) || Intersector.overlaps(Clouds.c2a, r) || Intersector.overlaps(Clouds.c3a, r) || Intersector.overlaps(Clouds.c4a, r) || 
				Intersector.overlaps(Clouds.c5a, r) || Intersector.overlaps(Clouds.c6a, r) || Intersector.overlaps(Clouds.c7a, r) ||
				Intersector.overlaps(Clouds.c1b, r) || Intersector.overlaps(Clouds.c2b, r) || Intersector.overlaps(Clouds.c3b, r) || Intersector.overlaps(Clouds.c4b, r) || 
				Intersector.overlaps(Clouds.c5b, r) || Intersector.overlaps(Clouds.c6b, r) || Intersector.overlaps(Clouds.c7b, r) ||
				Intersector.overlaps(Clouds.c1c, r) || Intersector.overlaps(Clouds.c2c, r) || Intersector.overlaps(Clouds.c3c, r) || Intersector.overlaps(Clouds.c4c, r) || 
				Intersector.overlaps(Clouds.c5c, r) || Intersector.overlaps(Clouds.c6c, r) || Intersector.overlaps(Clouds.c7c, r) ||
				Intersector.overlaps(Clouds.c1d, r) || Intersector.overlaps(Clouds.c2d, r) || Intersector.overlaps(Clouds.c3d, r) || Intersector.overlaps(Clouds.c4d, r) || 
				Intersector.overlaps(Clouds.c5d, r) || Intersector.overlaps(Clouds.c6d, r) || Intersector.overlaps(Clouds.c7d, r) ||
				Intersector.overlaps(Clouds.c1e, r) || Intersector.overlaps(Clouds.c2e, r) || Intersector.overlaps(Clouds.c3e, r) || Intersector.overlaps(Clouds.c4e, r) || 
				Intersector.overlaps(Clouds.c5e, r) || Intersector.overlaps(Clouds.c6e, r) || Intersector.overlaps(Clouds.c7e, r) ||
				Intersector.overlaps(Clouds.c1f, r) || Intersector.overlaps(Clouds.c2f, r) || Intersector.overlaps(Clouds.c3f, r) || Intersector.overlaps(Clouds.c4f, r) || 
				Intersector.overlaps(Clouds.c5f, r) || Intersector.overlaps(Clouds.c6f, r) || Intersector.overlaps(Clouds.c7f, r) ||
				Intersector.overlaps(Clouds.c1g, r) || Intersector.overlaps(Clouds.c2g, r) || Intersector.overlaps(Clouds.c3g, r) || Intersector.overlaps(Clouds.c4g, r) || 
				Intersector.overlaps(Clouds.c5g, r) || Intersector.overlaps(Clouds.c6g, r) || Intersector.overlaps(Clouds.c7g, r) ||
				Intersector.overlaps(Clouds.c1h, r) || Intersector.overlaps(Clouds.c2h, r) || Intersector.overlaps(Clouds.c3h, r) || Intersector.overlaps(Clouds.c4h, r) ||
				Intersector.overlaps(Clouds.c5h, r) || Intersector.overlaps(Clouds.c6h, r) || Intersector.overlaps(Clouds.c7h, r) ||
				Intersector.overlaps(Clouds.c1i, r) || Intersector.overlaps(Clouds.c2i, r) || Intersector.overlaps(Clouds.c3i, r) || Intersector.overlaps(Clouds.c4i, r) ||
				Intersector.overlaps(Clouds.c5i, r) || Intersector.overlaps(Clouds.c6i, r) || Intersector.overlaps(Clouds.c7i, r) ||
				Intersector.overlaps(Clouds.c1j, r) || Intersector.overlaps(Clouds.c2j, r) || Intersector.overlaps(Clouds.c3j, r) || Intersector.overlaps(Clouds.c4j, r) ||
				Intersector.overlaps(Clouds.c5j, r) || Intersector.overlaps(Clouds.c6j, r) || Intersector.overlaps(Clouds.c7j, r) ||
				Intersector.overlaps(Clouds.c1k, r) || Intersector.overlaps(Clouds.c2k, r) || Intersector.overlaps(Clouds.c3k, r) || Intersector.overlaps(Clouds.c4k, r) || 
				Intersector.overlaps(Clouds.c5k, r) || Intersector.overlaps(Clouds.c6k, r) || Intersector.overlaps(Clouds.c7k, r) ||
				Intersector.overlaps(Clouds.c1l, r) || Intersector.overlaps(Clouds.c2l, r) || Intersector.overlaps(Clouds.c3l, r) || Intersector.overlaps(Clouds.c4l, r) ||
				Intersector.overlaps(Clouds.c5l, r) || Intersector.overlaps(Clouds.c6l, r) || Intersector.overlaps(Clouds.c7l, r) ||
				Intersector.overlaps(Clouds.c1m, r) || Intersector.overlaps(Clouds.c2m, r) || Intersector.overlaps(Clouds.c3m, r) || Intersector.overlaps(Clouds.c4m, r) || 
				Intersector.overlaps(Clouds.c5m, r) || Intersector.overlaps(Clouds.c6m, r) || Intersector.overlaps(Clouds.c7m, r) ||
				Intersector.overlaps(Clouds.c1n, r) || Intersector.overlaps(Clouds.c2n, r) || Intersector.overlaps(Clouds.c3n, r) || Intersector.overlaps(Clouds.c4n, r) ||
				Intersector.overlaps(Clouds.c5n, r) || Intersector.overlaps(Clouds.c6n, r) || Intersector.overlaps(Clouds.c7n, r) ||
				Intersector.overlaps(Clouds.c1o, r) || Intersector.overlaps(Clouds.c2o, r) || Intersector.overlaps(Clouds.c3o, r) || Intersector.overlaps(Clouds.c4o, r) ||
				Intersector.overlaps(Clouds.c5o, r) || Intersector.overlaps(Clouds.c6o, r) || Intersector.overlaps(Clouds.c7o, r) ||
				Intersector.overlaps(Clouds.c1p, r) || Intersector.overlaps(Clouds.c2p, r) || Intersector.overlaps(Clouds.c3p, r) || Intersector.overlaps(Clouds.c4p, r) || 
				Intersector.overlaps(Clouds.c5p, r) || Intersector.overlaps(Clouds.c6p, r) || Intersector.overlaps(Clouds.c7p, r));
	}

	/*
	 * This method is responsible for calculating the collisions between a feather and the bird. 
	 * Once the feather collides into the bird character it will return a true Boolean statement, activating a method
	 * within the World class to call upon a new functionality.
	 */
	public static boolean featherCollision(BirdCharacter bird) {
		return (Intersector.overlaps(bird.getbirdCircle(), c3a));
	}

	/*
	 * This method is responsible for calculating the collisions between the creature and bird. 
	 * Once the creature collides into the bird character it will return a true Boolean statement, activating a method
	 * within the World class to call upon a new functionality.
	 */
	public static boolean powerUpCollision(BirdCharacter bird) {
		return (Intersector.overlaps(bird.getbirdCircle(), c2a) || Intersector.overlaps(bird.getbirdCircle(), r2a));
	}

	/*
	 * This method gets called within the World class to reset all of the 
	 * collectable positions and scrollspeed.
	 */
	public void onRestart(float x, float y, float scrollSpeed) {
		position.x = x;
		position.y = y;
		velocity.x = scrollSpeed;
	}	
}
