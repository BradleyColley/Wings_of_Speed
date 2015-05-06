package com.project.universe;

/*
 * This Rendering class acts as the Wings of Speed universe to render all of the gameplay assets following
 * a switch statement calling specific methods depending on the game state condition. This class will render 
 * all of the gameplay assets, ready, retry and complete interfaces for the user to interact with.
 */

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.project.game.WOS;
import com.project.levels.CustomLevel;
import com.project.levels.LevelOne;
import com.project.levels.LevelTwo;
import com.project.screens.MenuScreen;
import com.project.screens.MenuScreen.GameState;
import com.project.screens.Cage;
import com.project.utilities.Assets;
import com.project.utilities.DataManagement;
import com.project.utilities.InputHandler;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class Rendering {

	protected static Animation animEnemy;
	protected static Random r;
	protected static Table rLayout, iLayout;

	private static Button[] btn;
	private static ButtonStyle[] btnS;
	private static Label[] lbl;
	private static Image[] img;
	private static Texture object;
	private static int objectiveScore;

	public static Stage stage;
	public static Sprite[] clouds;
	public static Float[] scale;
	public static String powerUp;
	public static Boolean info = false;
	public static double gameTimer = 0.12f, miles;

	public Rendering(World myWorld, int gameHeight, float midPointY2) {
		world = myWorld;

		batch = new SpriteBatch();
		r = new Random();
		clouds = new Sprite[16];
		scale = new Float[75];

		f3 = new BitmapFont(Gdx.files.internal("fonts/ravie_white.fnt"));
		f4 = new BitmapFont(Gdx.files.internal("fonts/ravie_black.fnt"));

		scale[32] = (W/18f) / Assets.birds[0].getWidth();
		scale[33] = (H/12f) / Assets.birds[0].getHeight();

		scale[34] = (W/100f) / f3.getSpaceWidth();
		scale[35] = (H/30f) / f3.getLineHeight();

		scale[50] = (W/78f) / f4.getSpaceWidth();
		scale[51] = (H/15f) / f4.getLineHeight();

		f3.setScale(scale[34], scale[35]);
		f4.setScale(scale[50], scale[51]);
		f3.setColor(Color.DARK_GRAY);

		/*
		 * This if statement only runs when the game state equals LEVEL1 or
		 * the user has chosen clouds as an obstacle within custom level. 
		 * 
		 * Purpose of these statements is they run for loops producing random
		 * values to generate random cloud types within its array for the gameplay.
		 */
		if(MenuScreen.statusOfGame.equals(MenuScreen.GameState.LEVEL1) ||
				levelInput.get(2).equals("  White")){
			for(int i=0; i < 16;)
			{
				//(5-1)+1 means the Random function will never select element 0 as I don't want the gameplay rendering the tiny clouds.
				clouds[i] = Assets._clouds[r.nextInt(5-1)+1]; 
				i++;
			}
		} else if(levelInput.get(2).equals("  Dark Grey")){
			for(int i=0; i < 16;)
			{
				clouds[i] = Assets.c_grey[r.nextInt(5-1)+1];
				i++;
			}
		} else if(levelInput.get(2).equals("  Black")){
			for(int i=0; i < 16;)
			{
				clouds[i] = Assets.c_black[r.nextInt(5-1)+1];
				i++;
			}
		}

		stage = new Stage();
		stage2 = new Stage();
		rLayout = new Table();
		iLayout = new Table();
		btn = new Button[5];
		btnS = new ButtonStyle[5];
		lbl = new Label[5];
		img = new Image[3];
		
		drawInfoLayout();
		
		iLayout.setVisible(false);
		scale[56] = (W/35.0f) / Assets.coin.getWidth();
		scale[57]  = (H/20.0f) / Assets.coin.getHeight();
	}

	/*
	 * This method will be rendering all of the assets within this universe by following 
	 * a switch case statement based on the game state.
	 */
	public static void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	

		switch (MenuScreen.getStatusOfGame()) {
		case LEVEL1:
			batch.begin();
			LevelOne.ready();
			drawReady(delta);
			batch.end();
			stage.draw();
			resize();
			break;

		case RUNNING_1:
			batch.begin();
			LevelOne.run(delta);
			run(delta);
			batch.end();
			break;

		case GAMEOVER1:
			batch.begin();
			LevelOne.ready();
			drawBird(delta);
			clearBG();
			batch.end();
			stage.draw();
			drawLevelElemnets();
			drawHighscore();
			break;

		case LEVEL2:
			batch.begin();
			LevelTwo.ready(delta);
			drawReady(delta);
			batch.end();
			stage.draw();
			resize();
			break;

		case RUNNING_2:
			batch.begin();
			LevelTwo.run(delta);
			run(delta);
			batch.end();
			break;

		case GAMEOVER2:
			batch.begin();
			LevelTwo.ready(delta);
			drawBird(delta);
			clearBG();
			batch.end();
			stage.draw();
			drawLevelElemnets();
			drawHighscore();
			break;

		case READY_C:
			batch.begin();
			CustomLevel.ready(delta);
			drawBird(delta);
			clearBG();
			drawReady(delta);
			batch.end();
			resize();
			break;

		case RUNNING_C:
			batch.begin();
			CustomLevel.run(delta);
			run(delta);
			batch.end();
			break;

		case GAMEOVERC:
			batch.begin();
			CustomLevel.ready(delta);
			drawBird(delta);
			clearBG();
			batch.end();
			stage.draw();
			drawLevelElemnets();
			drawHighscore();
			break;

		case COMPLETE1:
		case COMPLETE2:
		case COMPLETE3:
		case COMPLETEC:
			batch.begin();
			levelChooser(delta);
			clearBG();
			batch.end();
			stage.draw();
			drawLevelElemnets();
			drawHighscore();
			break;

		default:
			break;
		}	
	}

	/*
	 * This method works once the user has completed a certain level which will 
	 * run certain methods from other classes. 
	 */
	public static void levelChooser(float delta) {
		if(MenuScreen.statusOfGame == GameState.COMPLETE1) {
			LevelOne.drawBG();
			LevelOne.screenUI();
			World.twigScore = 0;
		} 
		else if(MenuScreen.statusOfGame == GameState.COMPLETE2) {
			LevelTwo.drawBG();
			LevelTwo.screenUI(delta);
			World.featherScore = 0;
		} 
		else if(MenuScreen.statusOfGame == GameState.COMPLETEC) {
			CustomLevel.drawBackground();
			CustomLevel.drawEnemy(delta);
		}
	}
	
	/*
	 * This method draws the ready font onto the display.
	 */
	public static void drawReady(float delta) {
		if(info == false) {
			drawBird(delta);
			clearBG();
			Gdx.input.setInputProcessor(new InputHandler(bird));
			f3.draw(batch, "READY?", (f3.getSpaceWidth()/2f) + (W/2.3f), (f3.getLineHeight()/2) + (H/2f));
		} else {
			Gdx.input.setInputProcessor(stage);
		}
	}

	/*
	 * This method renders a transparent background on top of the current level 
	 * background to create the ready interface.
	 */
	public static void clearBG() {
		batch.draw(Assets.clearBG, 0, (H/2)/(Assets.clearBG.getHeight()/2), Assets.clearBG.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);
	}

	/*
	 * This method works when the game state becomes RUNNING_1, RUNNING_2 or RUNNINGC to 
	 * render the gameplay assets.
	 */
	public static void run(float delta) {	
		miles += gameTimer; //Calculates the distance counter.

		if(MenuScreen.statusOfGame == GameState.RUNNING_1) {
			objectiveScore = Math.round(World.twigScore);
			object = Assets.twig; //Sets the object variable to the twig objective asset.
		} 
		else if(MenuScreen.statusOfGame == GameState.RUNNING_2) {
			objectiveScore = Math.round(World.featherScore);
			object = Assets.feather; //Sets the object variable to the feather objective asset.
		} 
		else if(MenuScreen.statusOfGame == GameState.RUNNING_C) {
			if(levelInput.get(7).equals("  Feather")) {
				objectiveScore = Math.round(World.featherScore);
				object = Assets.feather; 
			} 
			else {
				objectiveScore = Math.round(World.twigScore);
				object = Assets.twig;
			}
		}
		drawLevelUI();
		drawBird(delta);
		drawPowerUpText();
		drawEnemies(delta);
		drawShield();
	}

	/*
	 * This method renders the bird character onto the gameplay depending on what 
	 * bird is currently active.
	 */
	public static void drawBird(float delta) {
		bird = world.getBird();//Change to myUniverse
		if(bird.isAlive) {
			if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0) {
				Animation ani;
				ani = Assets.aniOB = new Animation(0.07f, Assets.oBird);
				ani.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

				batch.draw(ani.getKeyFrame(delta), bird.getX(), bird.getY(), bird.getWidth() * scale[32], 
						bird.getHeight() * scale[33]);	
			} 
			else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1) {
				Animation ani;
				ani = Assets.aniBB = new Animation(0.09f, Assets.bBird);
				ani.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

				batch.draw(ani.getKeyFrame(delta), bird.getX(), bird.getY(), bird.getWidth() * scale[32], 
						bird.getHeight() * scale[33]);	
			}
			else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2) {
				scale[32] = (W/19f) / Assets.birds[10].getWidth();
				scale[33] = (H/17f) / Assets.birds[10].getHeight();

				Animation ani;
				ani = Assets.aniYB = new Animation(0.10f, Assets.yBird);
				ani.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

				batch.draw(ani.getKeyFrame(delta), bird.getX(), bird.getY(), bird.getWidth() * scale[32], 
						bird.getHeight() * scale[33]);	
			} 
			else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3) {
				scale[32] = (W/18f) / Assets.birds[15].getWidth();
				scale[33] = (H/24f) / Assets.birds[15].getHeight();

				Animation ani;
				ani = Assets.aniGB = new Animation(0.10f, Assets.gBird);
				ani.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

				batch.draw(ani.getKeyFrame(delta), bird.getX(), bird.getY(), bird.getWidth() * scale[32], 
						bird.getHeight() * scale[33]);	
			}
		}

		//This if statement renders the death graphic by displaying feathers onto the gameplay and removing the bird.
		if(!bird.isAlive) {
			if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0)
			{	
				scale[62] = W/5f / Assets.oDeath.getWidth();
				scale[63] = H/5f / Assets.oDeath.getHeight();
				batch.draw(Assets.oDeath, bird.getX()+(-W/80f), bird.getY()+(-H/10f), bird.getWidth() * scale[62], 
						bird.getHeight() * scale[63]);
			} else 	if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1)
			{	
				scale[62] = W/5f / Assets.bDeath.getWidth();
				scale[63] = H/5f / Assets.bDeath.getHeight();
				batch.draw(Assets.bDeath, bird.getX()+(-W/80f), bird.getY()+(-H/10f), bird.getWidth() * scale[62], 
						bird.getHeight() * scale[63]);
			} else 	if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2)
			{	
				scale[62] = W/5f / Assets.yDeath.getWidth();
				scale[63] = H/5f / Assets.yDeath.getHeight();
				batch.draw(Assets.yDeath, bird.getX()+(-W/80f), bird.getY()+(-H/10f), bird.getWidth() * scale[62], 
						bird.getHeight() * scale[63]);
			} else 	if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3)
			{	
				scale[62] = W/5f / Assets.gDeath.getWidth();
				scale[63] = H/5f / Assets.gDeath.getHeight();
				batch.draw(Assets.gDeath, bird.getX()+(-W/80f), bird.getY()+(-H/10f), bird.getWidth() * scale[62], 
						bird.getHeight() * scale[63]);
			}
		}
	}

	/*
	 * This method draws the coin element for the users to collect.
	 */
	public static void drawCoin() {
		batch.draw(Assets.coin, ScrollManager.coin.get(0).getX(), ScrollManager.coin.get(0).getY(), Assets.coin.getWidth() * scale[56],
				Assets.coin.getHeight() * scale[57]);
	}

	/*
	 * This method draws the enemies onto the gameplay.
	 */
	public static void drawEnemies(float delta) {
		batch.draw(animEnemy.getKeyFrame(delta), ScrollManager.enemies.get(0).getX(), ScrollManager.enemies.get(0).getY(), 
				ScrollManager.enemies.get(0).getWidth() * scale[60],  ScrollManager.enemies.get(0).getHeight() * scale[61]);
	}

	/*
	 * This method displays the powerup or setback ability text onto the gameplay 
	 * once the user has collected a creature which will give the user a random ability then 
	 * the text will inform the user what it is.
	 */
	public static void drawPowerUpText() {
		if(World.i ==0 || World.i ==1 || World.i ==2){
			f3.draw(batch, powerUp, (f3.getSpaceWidth()/2) + (W/2.5f), (f3.getLineHeight()/2) + (H/1.05f));
			Timer.schedule(new Task() {
				@Override
				public void run() {
					World.i = -1;
				}
			}, 2f);
		}
	}

	/*
	 * This method renders the temporary shield ability if the user gets given this ability
	 * via the creature.
	 */
	public static void drawShield() {
		if(bird.shieldActive == true) {
			float width_x = (W/16f) / Assets.shield.getWidth();
			float width_y = (H/10f) / Assets.shield.getHeight();

			batch.draw(Assets.shield, bird.getX()+(-W/200f), bird.getY()+(-H/120f), Assets.shield.getWidth()*width_x, Assets.shield.getHeight()*width_y);
		}
	}

	/*
	 * This method renders the level GUI displaying the distance, coins and objective counters.
	 */
	public static void drawLevelUI() {
		scale[66] = (W/40f) / Assets.feather.getWidth();
		scale[67] = (H/26f) / Assets.feather.getHeight();
		scale[68] = (W/55f) / Assets.coin.getWidth();
		scale[69] = (H/35f) / Assets.coin.getHeight();

		batch.draw(object, W/1.063f, H/1.05f, object.getWidth() * scale[66],  object.getHeight() * scale[67]);	
		f3.draw(batch, String.format("%04d" , Math.round(miles)), (f3.getSpaceWidth()/2) + (W/240f), (f3.getLineHeight()/2) + (H/1.03f));
		f3.draw(batch, "M", (f3.getSpaceWidth()/2) + (W/15), (f3.getLineHeight()/2) + (H/1.03f));
		f3.draw(batch, String.format("%02d" , objectiveScore), (f3.getSpaceWidth()/2) + (W/1.04f), (f3.getLineHeight()/2) + (H/1.03f));
		f3.draw(batch, String.format("%03d" , World.getCoinScore()), (f3.getSpaceWidth()/2) + (W/240f), (f3.getLineHeight()/2) + (H/1.06f));
		batch.draw(Assets.coin, W/18f, H/1.069f, Assets.coin.getWidth() * scale[68],  Assets.coin.getHeight() * scale[69]);	
	}

	/*
	 * This method draws the retry or complete interface by using a table based upon
	 * retrieving images from the skin atlas and using a json file to define attributes. 
	 */
	public static void drawLevel() {
		Skin s = Assets.rSkin;

		btnS[0] = new Button.ButtonStyle();
		btnS[0].up = s.getDrawable("homeBtn");
		btn[0] = new Button(btnS[0]);	
		btn[0].addListener(new ClickListener() {
			//Sets the button to the Menu screen.
			public void clicked(InputEvent event, float x, float y) {
				status = "menu";
				MenuScreen menu = new MenuScreen(game);
				((Game)Gdx.app.getApplicationListener()).setScreen(menu);
			}
		});

		btnS[1] = new Button.ButtonStyle();
		//If the game state equals COMPLETE1 then display the fly on button otherwise the retry button.
		if(MenuScreen.getStatusOfGame().equals(GameState.COMPLETE1))
		{
			btnS[1].up = s.getDrawable("flyOnBtn");
		} else {
			btnS[1].up = s.getDrawable("retryBtn");
		}
		btn[1] = new Button(btnS[1]);	
		btn[1].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y)
			{
				//Sets this button to restart the game level or move onto level 2.
				if(MenuScreen.getStatusOfGame().equals(GameState.GAMEOVER1)) {
					World.restartLevel1();
				} else if(MenuScreen.getStatusOfGame().equals(GameState.GAMEOVER2) || MenuScreen.getStatusOfGame().equals(GameState.COMPLETE2))
				{
					World.restartLevel2();
				} else if(MenuScreen.getStatusOfGame().equals(GameState.GAMEOVERC) || MenuScreen.getStatusOfGame().equals(GameState.COMPLETEC))
				{
					World.restartLevelC();
				} else if(MenuScreen.statusOfGame == GameState.COMPLETE1)
				{
					//Moves onto level 2.
					status = "L2";
					new ScrollManager();
					//Unlocks an achievement via the Google Play Services using my Achievement ID.
					if (WOS.gServices.isSignedIn()) {
						WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQAg");
					}
					World.restartLevel2();
					World.twigScore = 0;
				}
			}
		});

		btnS[2] = new Button.ButtonStyle();
		btnS[2].up = s.getDrawable("shopBtn");
		btn[2] = new Button(btnS[2]);	
		btn[2].addListener(new ClickListener()
		{
			//Sets the button to the shop screen.
			public void clicked(InputEvent event, float x, float y) {
				Cage localSettings = new Cage(game);
				localSettings.render(Gdx.graphics.getDeltaTime());
				((Game)Gdx.app.getApplicationListener()).setScreen(localSettings);
			}
		});

		//Below draws out the font for certain game state requirements. 
		if(MenuScreen.getStatusOfGame().equals(GameState.COMPLETE1) || MenuScreen.getStatusOfGame().equals(GameState.COMPLETE2) 
				|| MenuScreen.getStatusOfGame().equals(GameState.COMPLETEC)) {
			lbl[0] = new Label("Level", s);
			scale[36] = (W/5f) / lbl[0].getWidth();
			scale[37] = (H/8f) / lbl[0].getHeight();
		} else {
			lbl[0] = new Label("You Flew", s);
			scale[36] = (W/3f) / lbl[0].getWidth();
			scale[37] = (H/7f) / lbl[0].getHeight();
		}
		lbl[1] = new Label("Collected", s);

		scale[38] = (W/8f) / lbl[1].getWidth();
		scale[39] = (H/14f) / lbl[1].getHeight();

		lbl[0].setFontScale(scale[36], scale[37]);
		lbl[1].setFontScale(scale[38], scale[39]);

		//The users bird character will display on the retry or complete interfaces.
		if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0) {
			img[0] = new Image(Assets.birds[3]);
			scale[40] = (W/7f) / img[0].getWidth();
			scale[41] = (H/4f) / img[0].getHeight();
		} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1) {
			img[0] = new Image(Assets.birds[9]);
			scale[40] = (W/7f) / img[0].getWidth();
			scale[41] = (H/4f) / img[0].getHeight();
		} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2) {
			img[0] = new Image(Assets.birds[13]);
			scale[40] = (W/7f) / img[0].getWidth();
			scale[41] = (H/4f) / img[0].getHeight();
		} else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3) {
			img[0] = new Image(Assets.gBird_L);
			scale[40] = (W/7f) / img[0].getHeight();
			scale[41] = (H/4f) / img[0].getHeight();
		}

		//Below draws out the font for certain game state requirements. 
		if(MenuScreen.getStatusOfGame().equals(GameState.COMPLETE1) || MenuScreen.getStatusOfGame().equals(GameState.COMPLETE2)
				|| MenuScreen.getStatusOfGame().equals(GameState.COMPLETEC)) {
			lbl[2] = new Label("Complete!", s);
			scale[42] = (W/3f) / lbl[2].getWidth();
			scale[43] = (H/7f) / lbl[2].getHeight();
		} else {
			lbl[2] = new Label(String.valueOf(World.highscore + " m!"), s); //Gets the highscore from World class.
			scale[42] = (W/8f) / lbl[2].getWidth();
			scale[43] = (H/8f) / lbl[2].getHeight();
		}

		lbl[3] = new Label(String.valueOf(World.getCoinScore()), s);

		scale[44] = (W/35f) / lbl[3].getWidth();
		scale[45] = (H/16f) / lbl[3].getHeight();

		lbl[2].setFontScale(scale[42], scale[43]);
		lbl[3].setFontScale(scale[44], scale[45]);

		img[1] = new Image();
		img[1].setDrawable(s, "scoreBG");

		scale[46] = (W/3f) / img[1].getDrawable().getMinWidth();
		scale[47] = (H/5f) / img[1].getDrawable().getMinHeight();
		scale[48] = (W/3f) / btn[1].getWidth();
		scale[49] = (H/6f) / btn[1].getHeight();

		rLayout.add(img[0]).size(img[0].getWidth()*scale[40], img[0].getHeight()*scale[41]).center().expand((int) (W/2.2f), 0);
		rLayout.add(lbl[0]).size(lbl[0].getWidth()*scale[36], lbl[0].getHeight()*scale[37]).padTop(H/18f);
		rLayout.add(lbl[1]).size(lbl[1].getWidth()*scale[38], lbl[1].getHeight()*scale[39]).expand((int) (W/2f), 0).bottom().padBottom(H/50f);
		rLayout.row();
		rLayout.add();
		rLayout.add(lbl[2]).size(lbl[2].getWidth()*scale[42], lbl[2].getHeight()*scale[43]).top();
		rLayout.add(lbl[3]).size(lbl[3].getWidth()*scale[44], lbl[3].getHeight()*scale[45]).top().padRight(H/12f);
		rLayout.row();
		rLayout.add();
		if(MenuScreen.getStatusOfGame().equals(GameState.COMPLETE1) || MenuScreen.getStatusOfGame().equals(GameState.COMPLETE2))
		{
			rLayout.add(img[1]).size(img[1].getDrawable().getMinWidth()*scale[46], img[1].getDrawable().getMinHeight()*scale[47]).padTop(H/6.2f);
		} else {
			rLayout.add(img[1]).size(img[1].getDrawable().getMinWidth()*scale[46], img[1].getDrawable().getMinHeight()*scale[47]).padTop(H/5.9f);
		}
		rLayout.add();
		rLayout.row().expand().bottom();
		rLayout.add(btn[0]).size(btn[1].getWidth()*scale[48], btn[1].getHeight()*scale[49]);
		rLayout.add(btn[1]).size(btn[1].getWidth()*scale[48], btn[1].getHeight()*scale[49]);
		rLayout.add(btn[2]).size(btn[1].getWidth()*scale[48], btn[1].getHeight()*scale[49]);
		stage.addActor(rLayout);
	}

	/*
	 * This method renders the highscore on the retry or complete interfaces.
	 */
	public static void drawHighscore() {
		int highscore = 0;
		String scoreName;
		float score_w = 0, score_h = 0, num_w = 0, num_h = 0;
		
		//Retrieves the correct highscore from DataManagement.
		if(MenuScreen.statusOfGame == GameState.GAMEOVER1 || MenuScreen.statusOfGame == GameState.COMPLETE1)
		{
			highscore = DataManagement.prefs.getInteger(DataManagement.getHighScore_1());
		} else if(MenuScreen.statusOfGame == GameState.GAMEOVER2 || MenuScreen.statusOfGame == GameState.COMPLETE2)
		{
			highscore = DataManagement.prefs.getInteger(DataManagement.getHighScore_2());
		} else if(MenuScreen.statusOfGame == GameState.GAMEOVERC || MenuScreen.statusOfGame == GameState.COMPLETEC)
		{
			highscore = DataManagement.prefs.getInteger(DataManagement.getCustomScore());
		}

		//Checks if the users highscore within DataMangement class is higher than its current stored value if so
		//it will display "NEW Highscore" onto the interface.
		if(DataManagement.prefs.getInteger(DataManagement.getHighScore_1()) == World.highscore || 
				DataManagement.prefs.getInteger(DataManagement.getHighScore_2()) == World.highscore ||
				DataManagement.prefs.getInteger(DataManagement.getCustomScore()) == World.highscore) {
			scoreName = "NEW Highscore!";
			score_w = (f4.getSpaceWidth()/2f) + (W/2.965f);
			score_h = (f4.getLineHeight()/2) + (H/3.5f);
			num_w = (f4.getSpaceWidth()/2f) + (W/2.15f);
			num_h = (f4.getLineHeight()/2) + (H/4.8f);
		} else {
			scoreName = "Highscore";
			score_w = (f4.getSpaceWidth()/2f) + (W/2.55f);
			score_h = (f4.getLineHeight()/2) + (H/3.5f);
			num_w = (f4.getSpaceWidth()/2f) + (W/2.12f);
			num_h = (f4.getLineHeight()/2) + (H/4.8f);
		}

		batch.begin();
		f4.draw(batch, scoreName, score_w, score_h);
		f4.draw(batch, String.valueOf(highscore), num_w, num_h);
		batch.end();
	}

	/*
	 * This method renders the gameplay elements of coins and creatures onto the 
	 * retry or complete interfaces.
	 */
	public static void drawLevelElemnets() {
		scale[52] = (W/6.5f) / Assets.crea[0].getWidth();
		scale[53]  = (H/6.5f) / Assets.crea[0].getHeight();
		scale[54] = (W/6.5f) / Assets.crea[1].getWidth();
		scale[55]  = (H/5.0f) / Assets.crea[1].getHeight();

		batch.begin();
		batch.draw(Assets.crea[0], W/5.8f , H/7.3f , Assets.crea[0].getWidth() * scale[52], Assets.crea[0].getHeight() * scale[53]);
		batch.draw(Assets.crea[1], W/1.48f , H/6.45f, Assets.crea[1].getWidth() * scale[54], Assets.crea[1].getHeight() * scale[55]);
		batch.draw(Assets.coin, W/1.18f , H/1.66f, Assets.coin.getWidth() * scale[56], Assets.coin.getHeight() * scale[57]);
		batch.end();
	}

	/*
	 * This method displays the how to play guides on level 1 or 2.
	 */
	public static void drawHowToPlay() {
		if(MenuScreen.getStatusOfGame().equals(GameState.LEVEL1) && !DataManagement.prefs.contains(DataManagement.getHowtPly1())) {
			info = true;
			iLayout.setVisible(true);
			scale[70] = W/1.0f / Assets.howtPly1.getWidth();
			scale[71] = H/1.0f / Assets.howtPly1.getHeight();
			batch.draw(Assets.howtPly1, 0, 0, Assets.howtPly1.getWidth()*scale[70], Assets.howtPly1.getHeight()*scale[71]);
			drawInstructionBird();
		}
		else if(MenuScreen.getStatusOfGame().equals(GameState.LEVEL2) && !DataManagement.prefs.contains(DataManagement.getHowtPly2())) {
			info = true;
			iLayout.setVisible(true);
			scale[70] = W/1.0f / Assets.howtPly2.getWidth();
			scale[71] = H/1.0f / Assets.howtPly2.getHeight();
			batch.draw(Assets.howtPly2, 0, 0, Assets.howtPly2.getWidth()*scale[70], Assets.howtPly2.getHeight()*scale[71]);
			drawInstructionBird();
		}
	}

	/*
	 * This method draws the correct active bird character onto the 
	 * how to play guides for level 1 or 2.
	 */
	public static void drawInstructionBird() {
		Sprite bird = null;
		float h = H/1.5f;
		if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0) {
			bird = Assets.birds[3];
			scale[72] = W/8.0f / bird.getWidth();
			scale[73] = H/5.0f / bird.getHeight();
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1) {
			bird = Assets.birds[8];
			scale[72] = W/8.0f / bird.getWidth();
			scale[73] = H/5.0f / bird.getHeight();
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2) {
			bird = Assets.birds[12];
			scale[72] = W/10.0f / bird.getWidth();
			scale[73] = H/8.0f / bird.getHeight();
			h = H/1.4f;
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3) {
			bird = Assets.birds[16];
			scale[72] = W/8.0f / bird.getWidth();
			scale[73] = H/8.0f / bird.getHeight();
			h = H/1.4f;
		}

		if(bird != null) {
			batch.draw(bird, W/122f, h, bird.getWidth() * scale[72], bird.getHeight() * scale[73]);	
		}
	}

	/*
	 * This method will render the close button icon onto the how to 
	 * play guide for the user to close the image.
	 */
	public static void drawInfoLayout() {
		btnS[4] = new Button.ButtonStyle();
		btnS[4].up = MenuScreen.s.getDrawable("btnClose");
		btn[4] = new Button(btnS[4]);
		btn[4].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				info = false;
				DataManagement manager = new DataManagement();
				if(MenuScreen.getStatusOfGame().equals(GameState.LEVEL1)) {
					manager.saveHowToPlay1();
				} else {
					manager.saveHowToPlay2();
				}
				iLayout.setVisible(false);
			}
		});

		scale[72] = (W/24f) / btn[4].getWidth();

		iLayout.add(btn[4]).size(btn[4].getWidth()*scale[72], btn[4].getHeight()*scale[72]).expand();
		stage.addActor(iLayout);
	}

	public static void resize() {

		if(Gdx.graphics.getWidth() < 799){
			REPEAT_W = W / Assets.clearBG.getWidth()*2;
		} else {
			REPEAT_W = W / Assets.clearBG.getWidth()+1;
		}
		REPEAT_H = H / Assets.clearBG.getHeight() + 1;   

		rLayout.setSize(W/1f, H/1.1f);
		rLayout.setPosition((W/2f) - (rLayout.getWidth() / 2f), (H/2.2f) - (rLayout.getHeight() / 2f));
		iLayout.setSize(W/20, H/10);
		iLayout.setPosition((W/1.028f) - (iLayout.getWidth() / 2), (H/1.05f) - (iLayout.getHeight() / 2));
	}
}
