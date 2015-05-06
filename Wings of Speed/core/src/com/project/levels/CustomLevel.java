package com.project.levels;

/*
 * The CustomLevel class extends the Rendering class gaining access to its protected variables. This class's
 * purpose is running the correct methods within the if statements to render the correct assets, collisions and gameplay
 * essentials by pulling data from ScrollManager, BirdCharacter, Clouds, Collectables and Enemy classes.
 * 
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.project.objects.Clouds;
import com.project.objects.Collectables;
import com.project.objects.Enemy;
import com.project.universe.Rendering;
import com.project.universe.World;
import com.project.utilities.Assets;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class CustomLevel extends Rendering {
	protected static float repeatY, repeatX;
	public static float runTime = 0;
	
	public CustomLevel(World world, int gameHeight, float midPointY2) {
		super(world, gameHeight, midPointY2);
	}

	/*
	 * This method runs within the Rendering class once the game state equals READYC.
	 */
	public static void ready(float delta) {
		drawBackground();
		Rendering.drawCoin();
		drawObstacles(delta);
		resize();   
	}

	/*
	 * This method runs the gameplay within the Rendering class once the game state equals RUNNINGC. 
	 */
	public static void run(float delta) {
		runTime = delta;
		ready(delta);
		drawEnemy(delta);
		drawCreatures(delta);
		drawCollectables();
		Collectables.powerUpBoundaries();
		Enemy.enemy1Boundaries();
		Collectables.coinBoundaries();
	}

	/*
	 * This method draws the gameplay background depending on users selection.
	 */
	public static void drawBackground() {
		//If levelInput ArrayList element 0 equals to Blue Sky then render this background image.
		if(levelInput.get(0).equals("  Blue Sky")) {
			batch.draw(Assets.bgMainSky, 0, (H/2)/(Assets.bgMainSky.getHeight()/2), Assets.bgMainSky.getWidth() * REPEAT_W, 
					H, 0, REPEAT_H, REPEAT_W, 0);
		} else if(levelInput.get(0).equals("  Stars")) {
			batch.draw(Assets.bgStars, 0, (H/2)/(Assets.bgStars.getHeight()/2), Assets.bgStars.getWidth() * repeatY, H, 
					0, repeatX, repeatY, 0);
		}
	}

	/*
	 * This method draws the gameplay obstacles depending on the users selection.
	 */
	public static void drawObstacles(float delta) {
		/* If element 1 within the array equals Clouds then run the collision method and 
		 * call the other two methods from the LevelOne class to run the scale selector 
		 * and cloud graphics methods.
		 */
		if(levelInput.get(1).equals("  Clouds")) {
			Clouds.cloudBoundaries();
			LevelOne.scaleCloudsSelector();
			LevelOne.screenUI();
		} else if(levelInput.get(1).equals("  Bats")) {
		//If element 1 equals Bats instead then scale the sprites to the proper size then,
			scale[64] = (W/16f) / Assets.en2[0].getWidth();
			scale[65] = (H/8f) / Assets.en2[0].getHeight();
			//Find which colour the user has choosen for the bat obstacles.
			if(levelInput.get(2).equals("  Dark Grey")) {
				for(int i = 0; i <= 17;)
				{
					batch.draw(Assets.aniBats.getKeyFrame(runTime), ScrollManager.bats.get(i).getX(), ScrollManager.bats.get(i).getY(), 
							scale[64] * Assets.en2[0].getWidth(), scale[65] *  Assets.en2[0].getHeight());
					i++;
				}
			} else {
				Animation anim = Assets.aniBats;
				anim = new Animation(0.05f, Assets.bats_red);
				anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
				for(int i = 0; i <= 17;)
				{
					batch.draw(anim.getKeyFrame(runTime), ScrollManager.bats.get(i).getX(), ScrollManager.bats.get(i).getY(), 
							scale[64] * Assets.en2[0].getWidth(), scale[65] *  Assets.en2[0].getHeight());
					i++;
				}
			}
			Enemy.batBoundaries(); //Runs the bat collisions.
		}
	}

	/*
	 * This method draws the gameplay enemy depending on the users selection.
	 */
	public static void drawEnemy(Float delta) {
		//If the element 3 equals Bird then scale the sprites to according screen ratio then,
		if(levelInput.get(3).equals("  Bird")) {
			scale[60] = (W/14f) / Assets.enemy[0].getWidth();
			scale[61] = (H/14f) / Assets.enemy[0].getHeight();
			//Find what colour the user has selected and define the animation variable to the correct asset.
			if(levelInput.get(4).equals("  Brown/White")) {
				animEnemy = Assets.aniEnBrown;
			} else if(levelInput.get(4).equals("  Biege/Grey")) {
				animEnemy = Assets.aniEnBeige;
			} else if(levelInput.get(4).equals("  Black/Grey")) {
				animEnemy = Assets.aniEnBlack;
			} else if(levelInput.get(4).equals("  White/Black")) {
				animEnemy = Assets.aniEnWhite;
			} 
		} else if(levelInput.get(3).equals("  Bat")) {
			scale[60] = (W/12f) / Assets.enemy[22].getWidth();
			scale[61] = (H/4f) / Assets.enemy[22].getHeight();
			animEnemy = Assets.aniEnBat;
		} else if(levelInput.get(3).equals("  Wasp")) {
			scale[60] = (W/16.5f) / Assets.enemy[16].getWidth();
			scale[61] = (H/13f) / Assets.enemy[16].getHeight();
			if(levelInput.get(4).equals("  Yellow")) {
				animEnemy = Assets.aniEnWasp_yellow;
			} else {
				animEnemy = Assets.aniEnWasp_orange;
			}
		}
	}

	/*
	 * This method draws the gameplay creatures depending on the users selection.
	 */
	public static void drawCreatures(float delta) {
		Animation animCrea;
		TextureRegion[] getCrea = null;
		float time = 0.1f;
		//If element 5 equals Dragonfly then scale the sprites and set the animation time then,
		if(levelInput.get(5).equals("  Dragonfly")) {
			scale[58] = (W/20f) / Assets.crea[1].getWidth();
			scale[59] = (H/20f) / Assets.crea[1].getHeight();
			time = 0.025f;
			//Find what colour the user has selected upon within the array and link the TextureRegion varaiable to the correct asset.
			if(levelInput.get(6).equals("  Pink")) {
				getCrea = Assets.dFly_pink;
			} else if(levelInput.get(6).equals("  Blue")) {
				getCrea = Assets.dFly_blue;
			} else if(levelInput.get(6).equals("  Green")) {
				getCrea = Assets.dFly_green;
			} else if(levelInput.get(6).equals("  Yellow")) {
				getCrea = Assets.dFly_yellow;
			} 
		} else if(levelInput.get(5).equals("  Bee")) {
			scale[58] = (W/32f) / Assets.crea[17].getWidth();
			scale[59] = (H/18f) / Assets.crea[17].getHeight();
			if(levelInput.get(6).equals("  Blue")) {
				getCrea = Assets.bee_blue;
			} else if(levelInput.get(6).equals("  Brown")) {
				getCrea = Assets.bee_brown;
			} else if(levelInput.get(6).equals("  Yellow")) {
				getCrea = Assets.bee_yellow;
			}
		} else if(levelInput.get(5).equals("  Butterfly")) {
			scale[58] = (W/22f) / Assets.crea[26].getWidth();
			scale[59] = (H/12f) / Assets.crea[26].getHeight();
			if(levelInput.get(6).equals("  Blue")) {
				getCrea = Assets.bFly_blue;
			} else if(levelInput.get(6).equals("  Orange")) {
				getCrea = Assets.bFly_orange;
			} else if(levelInput.get(6).equals("  Purple")) {
				getCrea = Assets.bFly_purple;
			} else if(levelInput.get(6).equals("  Red")) {
				getCrea = Assets.bFly_red;
			}
		} else if(levelInput.get(5).equals("  Wasp")) {
			scale[58] = (W/28f) / Assets.crea[42].getWidth();
			scale[59] = (H/20f) / Assets.crea[42].getHeight();
			if(levelInput.get(6).equals("  Orange")) {
				getCrea = Assets.wasp_orange;
			} else if(levelInput.get(6).equals("  Yellow")) {
				getCrea = Assets.wasp_yellow;
			} 
		}
		//Runs the updated animation variables 
		animCrea = new Animation(time, getCrea);
		animCrea.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		batch.draw(animCrea.getKeyFrame(delta), ScrollManager.pUps.get(0).getX(), ScrollManager.pUps.get(0).getY(), 
				ScrollManager.pUps.get(0).getWidth() * scale[58],  ScrollManager.pUps.get(0).getHeight() * scale[59]);
	}

	/*
	 * This method draws the gameplay collectables depending on the users selection.
	 */
	public static void drawCollectables() {

		Texture collectable = null;

		//If element 7 equals to the Twig then define the variable to equal to the twig asset and run the collisions.
		if(levelInput.get(7).equals("  Twig")) {
			collectable = Assets.twig;
			Collectables.twigBoundaries();
		} else { 
			collectable = Assets.feather;
			Collectables.scale[0] = (W/35f) / Assets.feather.getWidth();
			Collectables.scale[1] = (W/35f) / Assets.feather.getHeight();
			Collectables.featherBoundaries();
		}
		//Use the updated collectable variable to draw the correct collectable asset.
		batch.draw(collectable, ScrollManager.objective.get(0).getX(), ScrollManager.objective.get(0).getY(), collectable.getWidth() * Collectables.scale[0],
				collectable.getHeight() * Collectables.scale[1]);	
		collectable.setFilter(TextureFilter.Linear, TextureFilter.Linear); //Setting the texture filter prevents flickering.
	}

	/*
	 * This method repeats the correct background image depending on the users selection.
	 */
	public static void resize() {
		if(levelInput.get(0).equals("  Blue Sky")) {
			if(Gdx.graphics.getWidth() < 799){
				REPEAT_W = W / Assets.bgMainSky.getWidth()*2;
			} else {
				REPEAT_W = W / Assets.bgMainSky.getWidth()+1;
			}
			REPEAT_H = H / Assets.bgMainSky.getHeight() + 1; 
		}

		if(levelInput.get(0).equals("  Stars")) {
			repeatY = W / Assets.bgStars.getWidth() + 1;
			repeatX = H / Assets.bgStars.getHeight() + 1 ; 
		}
	}
}