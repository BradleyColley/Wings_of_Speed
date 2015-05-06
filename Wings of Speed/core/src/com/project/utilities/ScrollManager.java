package com.project.utilities;

/*
 * The ScrollManager class is the main area to handle updating my gameplay objects
 * to move them across the screen adjusting their scrollspeed and resetting their
 * locations once they disappear.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.project.levels.LevelOne;
import com.project.objects.Clouds;
import com.project.objects.Collectables;
import com.project.objects.Enemy;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.universe.World;
import static com.project.utilities.Global.*;

public class ScrollManager {

	public static Clouds[] clouds;
	public static List<Collectables> objective;
	public static List<Collectables> coin;
	public static List<Collectables> pUps;
	public static List<Enemy> enemies;
	public static List<Enemy> bats;
	private Vector2 velocity2;
	private static Random r, r2;
	private static int cW = 182;
	private static int cH = 138;
	private static float x = W, y = H;
	private static float y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y11, y12, y13, y14, y15, y16; 
	public static int j = 0, e = 0, t = 0, p = 0, x_O, y_O, x_C, y_C, x_P, y_P, y_E1, y_E2;

	public ScrollManager() {

		//Only the MenuScreen class will accept this scrollSpeed value.
		if(status == "menu") {
			scrollSpeed = (int) (-x/30f); 
		} 
		r = new Random();
		r2 = new Random();
		cloudGenerator();

		objective = new ArrayList<Collectables>();
		if(objective.isEmpty()) {
			y_O = r.nextInt(Math.round(y)); //Creates a random number for position Y.
			//This loops through all of the clouds to make sure the objectives X and Y coordinates don't appear around the clouds.
			for(int i = 0; i < 16;) {
				if(y_O != clouds[i].getTailX() || y_O != clouds[i].getY() || y_O != clouds[i].getHeight() || y_O != clouds[i].getWidth()) {
					objective.add(new Collectables(x/2, y_O, 152, 288, 0));
				}	
				i++;
			}
		}

		coin = new ArrayList<Collectables>();
		if(coin.isEmpty()) {
			y_C = r2.nextInt(Math.round(y)); //Creates a random number for position Y.
			coin.add(new Collectables(x, y_C, 252, 252, 0)); //Sets the default attributes for coin.
		}
		new Vector2(x, y);
		velocity2 = new Vector2(0, y/20);

		pUps = new ArrayList<Collectables>();
		if(pUps.isEmpty()) {
			y_P = r.nextInt(Math.round(y)); //Creates a random number for position Y.
			pUps.add(new Collectables(x, y_P, crea_W, crea_H, -x/3f)); //Sets the default attributes for creatures.
		}

		enemyGenerator();
	}

	/*
	 * This method is responsible for generating all of the clouds locations and speed.
	 */
	public void cloudGenerator() {
		clouds = new Clouds[16];

		y1 = y/1.5f; y2 = y/12f; y3 = y/4f; y4 = y/1.09f; y5 = y/2f; y6 = y/1.3f; y7 = y/10f; y8 = y/3f;
		y9 = y/1.8f; y10 = y/1.3f; y11 = y/-15f; y12 = y/4f; y13 = y/10f; y14 = y/3f; y15 = y/4f; y16 = y/6f;

		clouds[0] = new Clouds(MIDPOINT_W/1.5f, y1, cW, cH, scrollSpeed);
		clouds[1] = new Clouds(clouds[0].getTailX()+(x/2f), y2, cW, cH, scrollSpeed);
		clouds[2] = new Clouds(MIDPOINT_W/5f, y3, cW, cH, scrollSpeed);
		clouds[3]  = new Clouds(clouds[2].getTailX()+(x/3.5f), y4, cW, cH, scrollSpeed);
		clouds[4]  = new Clouds(x/1f, y5, cW, cH, scrollSpeed);
		clouds[5]  = new Clouds(clouds[4].getTailX()+(x/4f), y6, cW, cH, scrollSpeed);
		clouds[6]  = new Clouds(MIDPOINT_W/1.5f,y7, cW, cH, scrollSpeed);
		clouds[7]  = new Clouds(clouds[6].getTailX()+(x/4f), y8, cW, cH, scrollSpeed);
		clouds[8]  = new Clouds(clouds[7].getTailX()+(x/3f), y9, cW, cH, scrollSpeed);
		clouds[9]  = new Clouds(MIDPOINT_W/4f, y10, cW, cH, scrollSpeed);
		clouds[10] = new Clouds(clouds[9].getTailX()+(x/3f), y11, cW, cH, scrollSpeed);
		clouds[11] = new Clouds(clouds[10].getTailX()+(x/3.5f), y12, cW, cH, scrollSpeed);
		clouds[12] = new Clouds(MIDPOINT_W/1.5f, y13, cW, cH, scrollSpeed);
		clouds[13]  = new Clouds(clouds[12].getTailX()+(x/4f), y14, cW, cH, scrollSpeed);
		clouds[14]  = new Clouds(clouds[13].getTailX()+(x/3f), y15, cW, cH, scrollSpeed);
		clouds[15] = new Clouds(MIDPOINT_W/9f, y16, cW, cH, scrollSpeed);
	}

	/*
	 * This method is responsible for generating all of the enemy locations and speed.
	 */
	public void enemyGenerator() {
		enemies = new ArrayList<Enemy>();
		if(enemies.isEmpty()) {
			y_E1 = r.nextInt(Math.round((y/1f) - (y/2f) + y/4f)); //Creates a random number for position Y.
			enemies.add(new Enemy(x+(200*10), y_E1, en_W, en_H, enemySpeed));//Enemy bird
		}

		bats = new ArrayList<Enemy>();
		if(bats.isEmpty()) {
			for(int i = 0; i <= 17;) //Creates 17 bats.
			{
				bats.add(new Enemy(0, 0, 252, 327, scrollSpeed));
				i++;
			}
		}
	}
	
    /*
     * This method updates the objects location and speed constantly.
     */
	public void update (float delta) {
	
		//Constantly updates all of the clouds.
		for(int i = 0; i <= 11; i++) {
			clouds[i].update(delta);
		}

		coin.get(0).setVelocity(velocity2); //Sets coin velocity to move the coin around.
		
		//If the coin Y position is greater than the border then move down otherwise go up.
		if(coin.get(0).getY() > World.getuBorder()) {
			velocity2.y = -y/20;
		} else if (coin.get(0).getY() < x/80f) 
		{
			velocity2.y = +y/20;
		}

		coin.get(0).update(delta);
		objective.get(0).update(delta);
		pUps.get(0).update(delta);
		enemies.get(0).update(delta);

		//Updates the rest of the clouds that don't belong to the Menu background.
		if(MenuScreen.getStatusOfGame().equals(GameState.RUNNING_1) || MenuScreen.getStatusOfGame().equals(GameState.RUNNING_C))
		{
			for(int i = 12; i <= 15; i++) 
			{
				clouds[i].update(delta);
			}
		}
		//Updates the bats within level 2 or custom level.
		if(MenuScreen.getStatusOfGame().equals(GameState.RUNNING_2) || MenuScreen.getStatusOfGame().equals(GameState.RUNNING_C))
		{
			for(int i = 0; i <= 17; i++) 
			{
				bats.get(i).update(delta);
			}

			lvl2Reset();
		}
		onReset();
	}

	/*
	 * This method resets X and Y coordinates for objects once they 
	 * disappear on the left hand side of the screen.
	 */
	public static void onReset() {
		//Resets the coin if its scrolledLeft.
		if(coin.get(0).isScrolledLeft()) 
		{
			y_C = r2.nextInt(Math.round(y));	
			coin.get(0).reset(x, y_C);
		}

		//Resets the enemy if its scrolledLeft.
		if(enemies.get(0).isScrolledLeft())
		{
			y_E1 = r.nextInt(Math.round((y/1f) - (y/2f) + y/4f));
			enemies.get(0).reset(x+(500*10), y_E1);
		}

		//Resets the objectives if they have scrolledLeft.
		if(MenuScreen.getStatusOfGame().equals(GameState.RUNNING_1) || MenuScreen.getStatusOfGame().equals(GameState.RUNNING_C)) {
			if(objective.get(0).isScrolledLeft()) 
			{
				int y_T = r.nextInt(Math.round(y));	
				int x_T = r.nextInt(Math.round(x));	
				for(int i = 0; i < 1;)
				{
					if(y_T != clouds[i].getTailX() || y_T != clouds[i].getY() || y_T != clouds[i].getHeight() || y_T != clouds[i].getWidth()) 
					{
						objective.get(0).reset(x+x_T, y_T);
					}
					i++;
				}
			}
			//Resets the creatures if they have scrolledLeft.
			if(pUps.get(0).isScrolledLeft()) 
			{
				y_P = r2.nextInt(Math.round(y));	
				pUps.get(0).reset(x+(800*10), y_C);
			}

			//Resets the cloud locations depending if they have scrolledLeft.
			if (clouds[0].isScrolledLeft()) {
				y1 = r.nextInt(Math.round(y/1f));	
				if(y1 < y/2) {
					//Checks if y1 is less than screen height
				} else {
					clouds[0].reset(x, y1);
					LevelOne.clouds[0] = cloud[r.nextInt(5-1)+1];
				}
			} else if (clouds[1].isScrolledLeft()) {
				y2 = r.nextInt(Math.round((y/1f) - (y/2f) + y/1f ));
				clouds[1].reset(x, y2);
				LevelOne.clouds[1] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[2].isScrolledLeft()) {	
				y3 = r.nextInt(Math.round(y));
				clouds[2].reset(x, y3);
				LevelOne.clouds[2] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[3].isScrolledLeft()) {
				y4 = r.nextInt(Math.round(y));
				clouds[3].reset(x, y4);
				LevelOne.clouds[3] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[4].isScrolledLeft()) {
				y5 = r.nextInt(Math.round(y/1f));
				if(y1 < y/2) {
					//Checks if y1 is less than screen height
				} else {
					clouds[4].reset(x, y5);
					LevelOne.clouds[4] = cloud[r.nextInt(5-1)+1];
				}
			} else if (clouds[5].isScrolledLeft()) {
				y6 = r.nextInt(Math.round((y/1f) - (y/2f) + y/1f ));
				clouds[5].reset(x, y6);
				LevelOne.clouds[5] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[6].isScrolledLeft()) {
				y7 = r.nextInt(Math.round(y));
				clouds[6].reset(x, y7);	
				LevelOne.clouds[6] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[7].isScrolledLeft()) {
				y8 = r.nextInt(Math.round(y/2));
				clouds[7].reset(x, y8);
				LevelOne.clouds[7] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[8].isScrolledLeft()) {
				y9 = r.nextInt(Math.round((y/1f) - (y/2f) + y/1f ));
				clouds[8].reset(x, y9);
				LevelOne.clouds[8] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[9].isScrolledLeft()) {
				y10 = r.nextInt(Math.round(y));
				clouds[9].reset(x, y10);
				LevelOne.clouds[9] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[10].isScrolledLeft()) {
				y11 = r.nextInt(Math.round(y/10f));
				clouds[10].reset(x, y11);
				LevelOne.clouds[10] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[11].isScrolledLeft()) {
				y12 = r.nextInt(Math.round(y/5f));
				clouds[11].reset(x, y12);
				LevelOne.clouds[11] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[12].isScrolledLeft()) {
				y13 = r.nextInt(Math.round((y/1f) - (y/2f) + y/1f ));
				clouds[12].reset(x, y13);
				LevelOne.clouds[12] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[13].isScrolledLeft()) {
				y14 =  r.nextInt(Math.round(y/1f));	
				if(y1 < y/2) {
					//Checks if y1 is less than screen height
				} else {
					clouds[13].reset(x, y14);
					LevelOne.clouds[13] = cloud[r.nextInt(5-1)+1];
				}
			} else if (clouds[14].isScrolledLeft()) {
				y15 = r.nextInt(Math.round(y/5f));
				clouds[14].reset(x, y15);
				LevelOne.clouds[14] = cloud[r.nextInt(5-1)+1];
			} else if (clouds[15].isScrolledLeft()) {
				y16 = r.nextInt(Math.round(y/1f));
				if(y1 < y/2) {
					//Checks if y1 is less than screen height
				} else {
					clouds[15].reset(x, y16);
					LevelOne.clouds[15] = cloud[r.nextInt(5-1)+1];
				}
			} 
		} else {
			if (clouds[0].isScrolledLeft()) {
				clouds[0].reset(x, clouds[0].getY());
			} else if (clouds[1].isScrolledLeft()) {
				clouds[1].reset(x, clouds[1].getY());
			} else if (clouds[2].isScrolledLeft()) {
				clouds[2].reset(x, clouds[2].getY());
			} else if (clouds[3].isScrolledLeft()) {
				clouds[3].reset(x, clouds[3].getY());
			} else if (clouds[4].isScrolledLeft()) {
				clouds[4].reset(x, clouds[4].getY());
			} else if (clouds[5].isScrolledLeft()) {
				clouds[5].reset(x, clouds[5].getY());
			} else if (clouds[6].isScrolledLeft()) {
				clouds[6].reset(x, clouds[6].getY());
			} else if (clouds[7].isScrolledLeft()) {
				clouds[7].reset(x, clouds[7].getY());
			} else if (clouds[8].isScrolledLeft()) {
				clouds[8].reset(x, clouds[8].getY());
			} else if (clouds[9].isScrolledLeft()) {
				clouds[9].reset(x, clouds[9].getY());
			} else if (clouds[10].isScrolledLeft()) {
				clouds[10].reset(x, clouds[10].getY());
			} else if (clouds[11].isScrolledLeft()) {
				clouds[11].reset(x, clouds[11].getY());
			}
		}
	}

	/*
	 * This method is called to reset all of the 
	 * level 2 elements.
	 */
	public static void lvl2Reset() {
		if(coin.get(0).isScrolledLeft()) 
		{
			y_C = r2.nextInt(Math.round(y));	
			coin.get(0).reset(x, y_C);
		}

		if(enemies.get(0).isScrolledLeft())
		{
			y_E1 = r.nextInt(Math.round((y/1f) - (y/2f) + y/4f));
			enemies.get(0).reset(x+(500*10), y_E1);
		}

		for(int i = 0; i <= 17;)
		{
			if(bats.get(i).isScrolledLeft())
			{
				y_E2 = r.nextInt(Math.round(y));
				bats.get(i).reset(x, y_E2);
			}
			i++;
		}

		if(objective.get(0).isScrolledLeft()) 
		{
			int y_T = r.nextInt(Math.round(y));	
			int x_T = r.nextInt(Math.round(x));	
			for(int i = 0; i < 1;)
			{
				if(y_T != objective.get(i).getTailX() || y_T != objective.get(i).getY() || y_T != objective.get(i).getHeight() || y_T != objective.get(i).getWidth()) 
				{
					objective.get(0).reset(x+x_T, y_T);
				}
				i++;
			}
		}

		if(pUps.get(0).isScrolledLeft()) 
		{
			y_P = r2.nextInt(Math.round(y));	
			pUps.get(0).reset(x+(800*10), y_C);
		}
	}

	/*
	 * This method is called to stop all of the objects.
	 */
	public void stop() {

		while(j <= 15) {
			clouds[j].stop();
			j++;
		}

		while(t <= 0){
			objective.get(t).stop();
			t++;
		}

		while(p <= 0){
			pUps.get(p).stop();
			p++;
		}

		while(e <= 0){
			enemies.get(e).stop();
			e++;
		}
		int b = 0;
		while(b <= 17) {
			bats.get(b).stop();
			b++;
		}
	}

	/*
	 * This method is called to reset the level one gameplay elements speed.
	 */
	public void resetLvlOneSpeed() {
		scrollSpeed = -W/5f;
		coinSpeed = -W/3.5f;
		abilitySpeed = -W/3.0f;
		enemySpeed = -W/2f;
		velocity2 = new Vector2(coinSpeed, y/20);
	}

	/*
	 * This method is called to restart gameplay objects.
	 */
	public void onRestart() {
		y1 = y/1.5f; y2 = y/12f; y3 = y/4f; y4 = y/1.09f; y5 = y/2f; y6 = y/1.3f; y7 = y/10f; y8 = y/3f;
		y9 = y/1.8f; y10 = y/1.3f; y11 = y/-15f; y12 = y/4f; y13 = y/10f; y14 = y/3f; y15 = y/4f; y16 = y/6f;

		clouds[0].onRestart(MIDPOINT_W/1.5f, y1, scrollSpeed);
		clouds[1].onRestart(clouds[0].getTailX()+(x/2f), y2, scrollSpeed);
		clouds[2].onRestart(MIDPOINT_W/5f, y3, scrollSpeed);
		clouds[3].onRestart(clouds[2].getTailX()+(x/3.5f), y4, scrollSpeed);
		clouds[4].onRestart(x/1f, y5, scrollSpeed);
		clouds[5].onRestart(clouds[4].getTailX()+(x/4f), y6, scrollSpeed);
		clouds[6].onRestart(MIDPOINT_W/1.5f, y7, scrollSpeed);
		clouds[7].onRestart(clouds[6].getTailX()+(x/4f), y8, scrollSpeed);
		clouds[8].onRestart(clouds[7].getTailX()+(x/3f), y9, scrollSpeed);
		clouds[9].onRestart(MIDPOINT_W/4f, y10, scrollSpeed);
		clouds[10].onRestart(clouds[9].getTailX()+(x/3f), y11, scrollSpeed);
		clouds[11].onRestart(clouds[10].getTailX()+(x/3.5f), y12, scrollSpeed);
		clouds[12].onRestart(MIDPOINT_W/1.5f, y13, scrollSpeed);
		clouds[13].onRestart(clouds[12].getTailX()+(x/4f), y14, scrollSpeed);
		clouds[14].onRestart(clouds[13].getTailX()+(x/3f), y15, scrollSpeed);
		clouds[15].onRestart(MIDPOINT_W/9f, y16, scrollSpeed);   	

		resetObjects();
	}

	/*
	 * This method is called to reset objects.
	 */
	public void resetObjects() {		
		objective.get(0).onRestart(x/2, objective.get(0).getY(), scrollSpeed);	
		coin.get(0).onRestart(x, coin.get(0).getY(), coinSpeed);
		pUps.get(0).onRestart(x, pUps.get(0).getY(), abilitySpeed);
		enemies.get(0).onRestart(x+(200*10), enemies.get(0).getY(), enemySpeed);	
	}

	/*
	 * This method is called to reset the level 2 elements speed.
	 */
	public void resetLvlTwoSpeed() {
		scrollSpeed = -W/4f;
		coinSpeed = -W/3f;
		abilitySpeed = -W/2.7f;
		enemySpeed = -W/2f;
		velocity2 = new Vector2(coinSpeed, y/20);
	}

	/*
	 * This method is called to restart the level 2 object locations and speed.
	 */
	public void level2Restart() {
		resetBats();
		coin.get(0).onRestart(x, coin.get(0).getY(), scrollSpeed);
		pUps.get(0).onRestart(x, pUps.get(0).getY(), abilitySpeed);
		objective.get(0).onRestart(x/2, objective.get(0).getY(), scrollSpeed);	
		enemies.get(0).onRestart(x+(200*10), enemies.get(0).getY(), enemySpeed);	
	}

	/*
	 * This method is called to restart the custom level elements.
	 */
	public void restartCustom() {

		if(levelInput.get(9).equals("  12x")) {
			scrollSpeed = -W/2f;
			coinSpeed = -W/2.0f;
			abilitySpeed = -W/1.5f;
			enemySpeed = -W/1f;
			velocity2 = new Vector2(coinSpeed, y/20);
		}
		else if(levelInput.get(9).equals("  8x")) {
			scrollSpeed = -W/4f;
			coinSpeed = -W/2.5f;
			abilitySpeed = -W/2.0f;
			enemySpeed = -W/1.5f;
			velocity2 = new Vector2(coinSpeed, y/20);
		}
		else if(levelInput.get(9).equals("  6x")) {
			scrollSpeed = -W/6f;
			coinSpeed = -W/3.0f;
			abilitySpeed = -W/2.5f;
			enemySpeed = -W/2.0f;
			velocity2 = new Vector2(coinSpeed, y/20);
		}
		else if(levelInput.get(9).equals("  4x")) {
			scrollSpeed = -W/8f;
			coinSpeed = -W/4.0f;
			abilitySpeed = -W/3.0f;
			enemySpeed = -W/2.2f;
			velocity2 = new Vector2(coinSpeed, y/40);
		}
		else if(levelInput.get(9).equals("  2x")) {
			scrollSpeed = -W/10f;
			coinSpeed = -W/4.2f;
			abilitySpeed = -W/3.5f;
			enemySpeed = -W/2.5f;
			velocity2 = new Vector2(coinSpeed, y/50);
		}

		if(levelInput.get(1).equals("  Clouds")) {
			onRestart();
		} else if(levelInput.get(1).equals("  Bats")) {
			level2Restart();
		}
	}

	/* 
	 * This method is called to reset all of the bat objects location and speed.
	 */
	public void resetBats() {
		bats.get(0).onRestart(x/10f, y1, scrollSpeed);
		bats.get(1).onRestart(x/2f, y2, scrollSpeed);
		bats.get(2).onRestart(x/5f, y3, scrollSpeed);
		bats.get(3).onRestart(x/3.5f, y4, scrollSpeed);
		bats.get(4).onRestart(x/1.5f, y5, scrollSpeed);
		bats.get(5).onRestart(x/3f, y6, scrollSpeed);
		bats.get(6).onRestart(x/9f, y7, scrollSpeed);
		bats.get(7).onRestart(x/1.3f, y8, scrollSpeed);
		bats.get(8).onRestart(x/1.1f, y9, scrollSpeed);
		bats.get(9).onRestart(x/1.5f, y/1.07f, scrollSpeed);
		bats.get(10).onRestart(x/2f, y14, scrollSpeed);
		bats.get(11).onRestart(x/1.2f, y/-40f, scrollSpeed);
		bats.get(12).onRestart(bats.get(0).getTailX()+(x/2), y15, scrollSpeed);
		bats.get(13).onRestart(bats.get(1).getTailX()+(x/2f), y1, scrollSpeed);
		bats.get(14).onRestart(bats.get(2).getTailX()+(x/1f), y9, scrollSpeed);
		bats.get(15).onRestart(bats.get(3).getTailX()+(x/2f), y3, scrollSpeed);
		bats.get(16).onRestart(bats.get(4).getTailX()+(x/2f), y7, scrollSpeed);
		bats.get(17).onRestart(bats.get(5).getTailX()+(x/2f), y16, scrollSpeed);
	}
}