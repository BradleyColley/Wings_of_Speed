package com.project.levels;

/*
 * The LevelTwo class extends the Rendering class gaining access to its protected variables. Purpose of this class
 * is to run the level 2 gameplay elements for the Rendering class to call upon within its switch statement depending
 * on the game state conditions.
 * 
 */

import com.badlogic.gdx.graphics.g2d.Animation;
import com.project.objects.Collectables;
import com.project.objects.Enemy;
import com.project.universe.Rendering;
import com.project.universe.World;
import com.project.utilities.Assets;
import com.project.utilities.ScrollManager;
import static com.project.utilities.Global.*;

public class LevelTwo extends Rendering {
	protected static float repeatY, repeatX;
	private static float runTime = 0;
	
	public LevelTwo(World world, int gameHeight, float midPointY2) {
		super(world, gameHeight, midPointY2);
	}

	/*
	 * This method will be called upon in the Rendering class if the game state equals READY_2.
	 */
	public static void ready(float delta) {
		drawBG();
		screenUI(delta);
		drawHowToPlay();
		resize();   
	}

	/*
	 * This method will be called upon in the Rendering class if the game state equals RUNNING_2.
	 */
	public static void run(float delta) {		
		runTime = delta;
		scale[60] = (W/14f) / Assets.enemy[12].getWidth();
		scale[61] = (H/14f) / Assets.enemy[12].getHeight();
		animEnemy = Assets.aniEnWhite; //Sets the enemy animation to the white asset.
		ready(delta);
		featherUI();
		drawPowerUp(delta);
		Collectables.powerUpBoundaries();
		Collectables.coinBoundaries();
		Collectables.featherBoundaries();
		Enemy.enemy1Boundaries();
		Enemy.batBoundaries();
	}
	
	/*
	 * Renders the star background and the coin element.
	 */
	public static void drawBG() {
		batch.draw(Assets.bgStars, 0, (H/2)/(Assets.bgStars.getHeight()/2), Assets.bgStars.getWidth() * repeatY, H, 
				0, repeatX, repeatY, 0);
		Rendering.drawCoin();
	}

	/*
	 * This method draws the bat obstacles within a for loop repeating 17 times.
	 */
	public static void screenUI(float delta) {
		//Scales the bats by using the W and H Global class variables getting the screen width and height.
		scale[64] = (W/16f) / Assets.en2[0].getWidth();
		scale[65] = (H/8f) / Assets.en2[0].getHeight();

		for(int i = 0; i <= 17;) {
			batch.draw(Assets.aniBats.getKeyFrame(runTime), ScrollManager.bats.get(i).getX(), ScrollManager.bats.get(i).getY(), 
					scale[64] * Assets.en2[0].getWidth(), scale[65] *  Assets.en2[0].getHeight());
			i++;
		}
	}

	/*
	 * This method draws the feather collectable element.
	 */
	public static void featherUI() {
		Collectables.scale[0] = (W/35f) / Assets.feather.getWidth();
		Collectables.scale[1] = (W/35f) / Assets.feather.getHeight();
		
		batch.draw(Assets.feather, ScrollManager.objective.get(0).getX(), ScrollManager.objective.get(0).getY(), Assets.feather.getWidth() * Collectables.scale[0],
				Assets.feather.getHeight() * Collectables.scale[1]);
	}
	
	/*
	 * This method draws the creature that contains the powerup or setback abilities. 
	 */
	public static void drawPowerUp(float delta) {
		scale[58] = (W/36f) / Assets.crea[23].getWidth();
		scale[59] = (H/20f) / Assets.crea[23].getHeight();
		Animation bee;
		bee = new Animation(0.1f, Assets.bee_yellow); //Sets the animation to the bee yellow asset.
		bee.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); //Loops the animation.

		batch.draw(bee.getKeyFrame(delta), ScrollManager.pUps.get(0).getX(), ScrollManager.pUps.get(0).getY(), 
				ScrollManager.pUps.get(0).getWidth() * scale[58],  ScrollManager.pUps.get(0).getHeight() * scale[59]);
	}

	/*
	 * Repeats the seamless star background across the screen.
	 */
	public static void resize() { 
		repeatY = W / Assets.bgStars.getWidth() + 1;
		repeatX = H / Assets.bgStars.getHeight() + 1 ;  
	}
}
