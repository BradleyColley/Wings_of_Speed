package com.project.levels;

/*
 * The LevelOne class extends the Rendering class gaining access to its protected variables. Purpose of this class
 * is to run the level 1 gameplay elements for the Rendering class to call upon within its switch statement depending
 * on the game state conditions.
 * 
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.project.objects.Clouds;
import com.project.objects.Collectables;
import com.project.objects.Enemy;
import com.project.universe.Rendering;
import com.project.universe.World;
import com.project.utilities.Assets;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class LevelOne extends Rendering {
				
	public LevelOne(World world, int gameHeight, float midPointY2) {
		super(world, gameHeight, midPointY2);
	}
		
	/*
	 * This method will be called upon in the Rendering class if the game state equals READY_1.
	 */
	public static void ready() {
		scaleCloudsSelector(); 
		drawBG();
		screenUI();
		drawHowToPlay();
		resize();
	}
	
	/*
	 * This method will be called upon in the Rendering class if the game state equals RUNNING_1.
	 */
	public static void run(float delta) {		
		scale[60] = (W/14f) / Assets.enemy[0].getWidth();
		scale[61] = (H/14f) / Assets.enemy[0].getHeight();
		animEnemy = Assets.aniEnBrown; //Sets the enemy animation to the brown asset.
		scaleCloudsSelector();
		drawBG();
		screenUI();
		twigUI();
		Assets.twig.setFilter(TextureFilter.Linear, TextureFilter.Linear); //Prevents flickering.
		drawPowerUp(delta);
		Clouds.cloudBoundaries();
		Collectables.twigBoundaries();
		Collectables.powerUpBoundaries();
		Collectables.coinBoundaries();
		Enemy.enemy1Boundaries();
		resize();
	}
	
	/*
	 * Renders the blue sky background and the coin element.
	 */
	public static void drawBG() {
		batch.draw(Assets.bgMainSky, 0, (H/2)/(Assets.bgMainSky.getHeight()/2), Assets.bgMainSky.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);

		Rendering.drawCoin();
	}
	
	/*
	 * This method renders all of the clouds by retrieving the X and Y coordinates from ScrollManager class and 
	 * calls the scale float array to set its width and height dimensions.
	 */
	public static void screenUI() {
		
		batch.draw(clouds[0], ScrollManager.clouds[0].getX(), ScrollManager.clouds[0].getY(), scale[0], scale[1]);
		batch.draw(clouds[1], ScrollManager.clouds[1].getX(), ScrollManager.clouds[1].getY(), scale[2], scale[3]);
		batch.draw(clouds[2], ScrollManager.clouds[2].getX(), ScrollManager.clouds[2].getY(), scale[4], scale[5]);
		batch.draw(clouds[3], ScrollManager.clouds[3].getX(), ScrollManager.clouds[3].getY(), scale[6], scale[7]);
		batch.draw(clouds[4], ScrollManager.clouds[4].getX(), ScrollManager.clouds[4].getY(), scale[8], scale[9]);
		batch.draw(clouds[5], ScrollManager.clouds[5].getX(), ScrollManager.clouds[5].getY(), scale[10], scale[11]);
		batch.draw(clouds[6], ScrollManager.clouds[6].getX(), ScrollManager.clouds[6].getY(), scale[12], scale[13]);
		batch.draw(clouds[7], ScrollManager.clouds[7].getX(), ScrollManager.clouds[7].getY(), scale[14], scale[15]);
		batch.draw(clouds[8], ScrollManager.clouds[8].getX(), ScrollManager.clouds[8].getY(), scale[16], scale[17]);
		batch.draw(clouds[9], ScrollManager.clouds[9].getX(), ScrollManager.clouds[9].getY(), scale[18] ,scale[19]);
		batch.draw(clouds[10], ScrollManager.clouds[10].getX(), ScrollManager.clouds[10].getY(), scale[20], scale[21]);
		batch.draw(clouds[11], ScrollManager.clouds[11].getX(), ScrollManager.clouds[11].getY(), scale[22], scale[23]);
		batch.draw(clouds[12], ScrollManager.clouds[12].getX(), ScrollManager.clouds[12].getY(), scale[24], scale[25]);
		batch.draw(clouds[13], ScrollManager.clouds[13].getX(), ScrollManager.clouds[13].getY(), scale[26], scale[27]);
		batch.draw(clouds[14], ScrollManager.clouds[14].getX(), ScrollManager.clouds[14].getY(), scale[28], scale[29]);	
		batch.draw(clouds[15], ScrollManager.clouds[15].getX(), ScrollManager.clouds[15].getY(), scale[30] , scale[31]);

	}

	/*
	 * This method draws the twig element.
	 */
	public static void twigUI() {
		batch.draw(Assets.twig, ScrollManager.objective.get(0).getX(), ScrollManager.objective.get(0).getY(), Assets.twig.getWidth() * Collectables.scale[0],
				Assets.twig.getHeight() * Collectables.scale[1]);
	}
	
	/*
	 * This method draws the creature that contains the powerup or setback abilities. 
	 */
	public static void drawPowerUp(float delta) {
		scale[58] = (W/20f) / Assets.crea[1].getWidth();
		scale[59] = (H/20f) / Assets.crea[1].getHeight();

		Animation dFly;
		dFly = new Animation(0.025f, Assets.dFly_pink); //Sets the create animation to the pink dragonfly asset.
		dFly.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		batch.draw(dFly.getKeyFrame(delta), ScrollManager.pUps.get(0).getX(), ScrollManager.pUps.get(0).getY(), 
				ScrollManager.pUps.get(0).getWidth() * scale[58],  ScrollManager.pUps.get(0).getHeight() * scale[59]);
	}
	
	public static void resize() {
		if(Gdx.graphics.getWidth() < 799){
			REPEAT_W = W / Assets.bgMainSky.getWidth()*2;
		} else {
			REPEAT_W = W / Assets.bgMainSky.getWidth()+1;
		}
		REPEAT_H = H / Assets.bgMainSky.getHeight() + 1;
	}
	
	/*
	 * This method defines the width and height of each cloud within its array constantly updating 
	 * as the clouds will randomly change sizes when they get regenerated once they have left the screen.
	 * 
	 * The clouds array will be receiving random numbers by using the Random function inside the Rendering class.
	 * The cloud array is being called from the Global class retrieving the white cloud assets.
	 */
	public static void scaleCloudsSelector() {	
		//If element 0 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[0] == cloud[0]) {		
			scale[0] = cloud[0].getWidth() * Clouds.s[0];		
			scale[1] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[0] = 0; //Set the i float array to 0. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[0] == cloud[1]) {
			scale[0] = cloud[1].getWidth() * Clouds.s[2];
			scale[1] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[0] = 0;
		} else if(clouds[0] == cloud[2]) {
			scale[0] = cloud[2].getWidth() * Clouds.s[4];
			scale[1] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[0] = 0;
		} else if(clouds[0] == cloud[3]) {
			scale[0] = cloud[3].getWidth() * Clouds.s[6];
			scale[1] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[0] = 0;
		} else if(clouds[0] == cloud[4]) {
			scale[0] = cloud[4].getWidth() * Clouds.s[8];
			scale[1] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[0] = 0;
		}

		//If element 1 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[1] == cloud[0]) {		
			scale[2] = cloud[0].getWidth() * Clouds.s[0];		
			scale[3] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[1] = 1; //Set the i float array to 1. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[1] == cloud[1]) {
			scale[2] = cloud[1].getWidth() * Clouds.s[2];
			scale[3] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[1] = 1;
		} else if(clouds[1] == cloud[2]) {
			scale[2] = cloud[2].getWidth() * Clouds.s[4];
			scale[3] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[1] = 1;
		} else if(clouds[1] == cloud[3]) {
			scale[2] = cloud[3].getWidth() * Clouds.s[6];
			scale[3] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[1] = 1;
		} else if(clouds[1] == cloud[4]) {
			scale[2] = cloud[4].getWidth() * Clouds.s[8];
			scale[3] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[1] = 1;
		}

		//If element 2 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[2] == cloud[0]) {		
			scale[4] = cloud[0].getWidth() * Clouds.s[0];		
			scale[5] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[2] = 2; //Set the i float array to 2. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[2] == cloud[1]) {
			scale[4] = cloud[1].getWidth() * Clouds.s[2];
			scale[5] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[2] = 2;
		} else if(clouds[2] == cloud[2]) {
			scale[4] = cloud[2].getWidth() * Clouds.s[4];
			scale[5] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[2] = 2;
		} else if(clouds[2] == cloud[3]) {
			scale[4] = cloud[3].getWidth() * Clouds.s[6];
			scale[5] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[2] = 2;
		} else if(clouds[2] == cloud[4]) {
			scale[4] = cloud[4].getWidth() * Clouds.s[8];
			scale[5] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[2] = 2;
		}

		//If element 3 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[3] == cloud[0]) {		
			scale[6] = cloud[0].getWidth() * Clouds.s[0];		
			scale[7] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[3] = 3; //Set the i float array to 3. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[3] == cloud[1]) {
			scale[6] = cloud[1].getWidth() * Clouds.s[2];
			scale[7] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[3] = 3;
		} else if(clouds[3] == cloud[2]) {
			scale[6] = cloud[2].getWidth() * Clouds.s[4];
			scale[7] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[3] = 3;
		} else if(clouds[3] == cloud[3]) {
			scale[6] = cloud[3].getWidth() * Clouds.s[6];
			scale[7] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[3] = 3;
		} else if(clouds[3] == cloud[4]) {
			scale[6] = cloud[4].getWidth() * Clouds.s[8];
			scale[7] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[3] = 3;
		}

		//If element 4 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[4] == cloud[0]) {		
			scale[8] = cloud[0].getWidth() * Clouds.s[0];		
			scale[9] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[4] = 4; //Set the i float array to 4. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[4] == cloud[1]) {
			scale[8] = cloud[1].getWidth() * Clouds.s[2];
			scale[9] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[4] = 4;
		} else if(clouds[4] == cloud[2]) {
			scale[8] = cloud[2].getWidth() * Clouds.s[4];
			scale[9] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[4] = 4;
		} else if(clouds[4] == cloud[3]) {
			scale[8] = cloud[3].getWidth() * Clouds.s[6];
			scale[9] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[4] = 4;
		} else if(clouds[4] == cloud[4]) {
			scale[8] = cloud[4].getWidth() * Clouds.s[8];
			scale[9] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[4] = 4;
		}

		//If element 5 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[5] == cloud[0]) {		
			scale[10] = cloud[0].getWidth() * Clouds.s[0];		
			scale[11] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[5] = 5; //Set the i float array to 5. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[5] == cloud[1]) {
			scale[10] = cloud[1].getWidth() * Clouds.s[2];
			scale[11] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[5] = 5;
		} else if(clouds[5] == cloud[2]) {
			scale[10] = cloud[2].getWidth() * Clouds.s[4];
			scale[11] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[5] = 5;
		} else if(clouds[5] == cloud[3]) {
			scale[10] = cloud[3].getWidth() * Clouds.s[6];
			scale[11] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[5] = 5;
		} else if(clouds[5] == cloud[4]) {
			scale[10] = cloud[4].getWidth() * Clouds.s[8];
			scale[11] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[5] = 5;
		}

		//If element 6 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[6] == cloud[0]) {		
			scale[12] = cloud[0].getWidth() * Clouds.s[0];		
			scale[13] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[6] = 6; //Set the i float array to 6. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[6] == cloud[1]) {
			scale[12] = cloud[1].getWidth() * Clouds.s[2];
			scale[13] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[6] = 6;
		} else if(clouds[6] == cloud[2]) {
			scale[12] = cloud[2].getWidth() * Clouds.s[4];
			scale[13] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[6] = 6;
		} else if(clouds[6] == cloud[3]) {
			scale[12] = cloud[3].getWidth() * Clouds.s[6];
			scale[13] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[6] = 6;
		} else if(clouds[6] == cloud[4]) {
			scale[12] = cloud[4].getWidth() * Clouds.s[8];
			scale[13] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[6] = 6;
		}

		//If element 7 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[7] == cloud[0]) {		
			scale[14] = cloud[0].getWidth() * Clouds.s[0];		
			scale[15] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[7] = 7; //Set the i float array to 7. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[7] == cloud[1]) {
			scale[14] = cloud[1].getWidth() * Clouds.s[2];
			scale[15] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[7] = 7;
		} else if(clouds[7] == cloud[2]) {
			scale[14] = cloud[2].getWidth() * Clouds.s[4];
			scale[15] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[7] = 7;
		} else if(clouds[7] == cloud[3]) {
			scale[14] = cloud[3].getWidth() * Clouds.s[6];
			scale[15] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[7] = 7;
		} else if(clouds[7] == cloud[4]) {
			scale[14] = cloud[4].getWidth() * Clouds.s[8];
			scale[15] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[7] = 7;
		}

		//If element 8 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[8] == cloud[0]) {		
			scale[16] = cloud[0].getWidth() * Clouds.s[0];		
			scale[17] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[8] = 8; //Set the i float array to 8. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[8] == cloud[1]) {
			scale[16] = cloud[1].getWidth() * Clouds.s[2];
			scale[17] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[8] = 8;
		} else if(clouds[8] == cloud[2]) {
			scale[16] = cloud[2].getWidth() * Clouds.s[4];
			scale[17] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[8] = 8;
		} else if(clouds[8] == cloud[3]) {
			scale[16] = cloud[3].getWidth() * Clouds.s[6];
			scale[17] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[8] = 8;
		} else if(clouds[8] == cloud[4]) {
			scale[16] = cloud[4].getWidth() * Clouds.s[8];
			scale[17] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[8] = 8;
		}

		//If element 9 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[9] == cloud[0]) {		
			scale[18] = cloud[0].getWidth() * Clouds.s[0];		
			scale[19] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[9] = 9; //Set the i float array to 9. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[9] == cloud[1]) {
			scale[18] = cloud[1].getWidth() * Clouds.s[2];
			scale[19] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[9] = 9;
		} else if(clouds[9] == cloud[2]) {
			scale[18] = cloud[2].getWidth() * Clouds.s[4];
			scale[19] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[9] = 9;
		} else if(clouds[9] == cloud[3]) {
			scale[18] = cloud[3].getWidth() * Clouds.s[6];
			scale[19] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[9] = 9;
		} else if(clouds[9] == cloud[4]) {
			scale[18] = cloud[4].getWidth() * Clouds.s[8];
			scale[19] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[9] = 9;
		}

		//If element 10 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[10] == cloud[0]) {		
			scale[20] = cloud[0].getWidth() * Clouds.s[0];		
			scale[21] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[10] = 10; //Set the i float array to 10. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[10] == cloud[1]) {
			scale[20] = cloud[1].getWidth() * Clouds.s[2];
			scale[21] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[10] = 10;
		} else if(clouds[10] == cloud[2]) {
			scale[20] = cloud[2].getWidth() * Clouds.s[4];
			scale[21] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[10] = 10;
		} else if(clouds[10] == cloud[3]) {
			scale[20] = cloud[3].getWidth() * Clouds.s[6];
			scale[21] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[10] = 10;
		} else if(clouds[10] == cloud[4]) {
			scale[20] = cloud[4].getWidth() * Clouds.s[8];
			scale[21] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[10] = 10;
		}

		//If element 11 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[11] == cloud[0]) {		
			scale[22] = cloud[0].getWidth() * Clouds.s[0];		
			scale[23] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[11] = 11; //Set the i float array to 11. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[11] == cloud[1]) {
			scale[22] = cloud[1].getWidth() * Clouds.s[2];
			scale[23] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[11] = 11;
		} else if(clouds[11] == cloud[2]) {
			scale[22] = cloud[2].getWidth() * Clouds.s[4];
			scale[23] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[11] = 11;
		} else if(clouds[11] == cloud[3]) {
			scale[22] = cloud[3].getWidth() * Clouds.s[6];
			scale[23] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[11] = 11;
		} else if(clouds[11] == cloud[4]) {
			scale[22] = cloud[4].getWidth() * Clouds.s[8];
			scale[23] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[11] = 11;
		}

		//If element 12 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[12] == cloud[0]) {		
			scale[24] = cloud[0].getWidth() * Clouds.s[0];		
			scale[25] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[12] = 12; //Set the i float array to 12. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[12] == cloud[1]) {
			scale[24] = cloud[1].getWidth() * Clouds.s[2];
			scale[25] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[12] = 12;
		} else if(clouds[12] == cloud[2]) {
			scale[24] = cloud[2].getWidth() * Clouds.s[4];
			scale[25] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[12] = 12;
		} else if(clouds[12] == cloud[3]) {
			scale[24] = cloud[3].getWidth() * Clouds.s[6];
			scale[25] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[12] = 12;
		} else if(clouds[12] == cloud[4]) {
			scale[24] = cloud[4].getWidth() * Clouds.s[8];
			scale[25] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[12] = 12;
		}

		//If element 13 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[13] == cloud[0]) {		
			scale[26] = cloud[0].getWidth() * Clouds.s[0];		
			scale[27] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[13] = 13; //Set the i float array to 13. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[13] == cloud[1]) {
			scale[26] = cloud[1].getWidth() * Clouds.s[2];
			scale[27] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[13] = 13;
		} else if(clouds[13] == cloud[2]) {
			scale[26] = cloud[2].getWidth() * Clouds.s[4];
			scale[27] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[13] = 13;
		} else if(clouds[13] == cloud[3]) {
			scale[26] = cloud[3].getWidth() * Clouds.s[6];
			scale[27] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[13] = 13;
		} else if(clouds[13] == cloud[4]) {
			scale[26] = cloud[4].getWidth() * Clouds.s[8];
			scale[27] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[13] = 13;
		}

		//If element 14 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[14] == cloud[0]) {		
			scale[28] = cloud[0].getWidth() * Clouds.s[0];		
			scale[29] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[14] = 14; //Set the i float array to 14. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[14] == cloud[1]) {
			scale[28] = cloud[1].getWidth() * Clouds.s[2];
			scale[29] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[14] = 14;
		} else if(clouds[14] == cloud[2]) {
			scale[28] = cloud[2].getWidth() * Clouds.s[4];
			scale[29] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[14] = 14;
		} else if(clouds[14] == cloud[3]) {
			scale[28] = cloud[3].getWidth() * Clouds.s[6];
			scale[29] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[14] = 14;
		} else if(clouds[14] == cloud[4]) {
			scale[28] = cloud[4].getWidth() * Clouds.s[8];
			scale[29] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[14] = 14;
		}

		//If element 15 in clouds equal element 0 in cloud ArrayList then scale the width and height.
		if(clouds[15] == cloud[0]) {		
			scale[30] = cloud[0].getWidth() * Clouds.s[0];		
			scale[31] = cloud[0].getHeight() * Clouds.s[1];
			Clouds.i[15] = 15; //Set the i float array to 15. Therefore the Clouds class knows which collision to wrap around.
		} else if(clouds[15] == cloud[1]) {
			scale[30] = cloud[1].getWidth() * Clouds.s[2];
			scale[31] = cloud[1].getHeight() * Clouds.s[3];
			Clouds.i[15] = 15;
		} else if(clouds[15] == cloud[2]) {
			scale[30] = cloud[2].getWidth() * Clouds.s[4];
			scale[31] = cloud[2].getHeight() * Clouds.s[5];
			Clouds.i[15] = 15;
		} else if(clouds[15] == cloud[3]) {
			scale[30] = cloud[3].getWidth() * Clouds.s[6];
			scale[31] = cloud[3].getHeight() * Clouds.s[7];
			Clouds.i[15] = 15;
		} else if(clouds[15] == cloud[4]) {
			scale[30] = cloud[4].getWidth() * Clouds.s[8];
			scale[31] = cloud[4].getHeight() * Clouds.s[9];
			Clouds.i[15] = 15;
		}
	}
}
