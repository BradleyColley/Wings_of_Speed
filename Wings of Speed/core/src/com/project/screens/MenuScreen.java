package com.project.screens;

/*
 * The MenuScreen implements the Screen interface inheriting its methods. This class acts
 * like the game interface navigating the user to other screens and handles the game state. 
 * 
 * The enum game state handles the operation status of Wings of Speed defining when to activate 
 * certain methods based on the state. 
 * 
 * The structure contains a couple of tables outlining the interface for the Menu and Select Level 
 * screens. Settings has been created within this class as the functionality pops up displaying a 
 * list of icon settings to choose from. 
 * 
 * I've created a message() method which displays the popup messages informing the user on certain
 * activities. Reason why I have this and not using the Android toast API is because I couldn't figure 
 * out how to implement this API into the libGDX framework.  
 * 
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.project.game.WOS;
import com.project.objects.Clouds;
import com.project.utilities.Assets;
import com.project.utilities.DataManagement;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class MenuScreen implements Screen {

	private Label mainTitle, lvlTitle, leaderTitle;
	private Sprite bEffect, rEffect, lEffect, lBird, mBird, rBird;
	private Float[] scale;
	private Button[] btn;
	private ButtonStyle[] btnS;
	private String status = "";
	private float runTime = 0f;
	
	public Table btnLayout, lblLayout, lvlLayout, lvlTLayout, stLayout, iLayout, lLayout;
	public static Boolean info = false;
	public static Skin s;

	public static enum GameState  {
		MENU, 
		LEVEL1, READY_1, RUNNING_1, GAMEOVER1, COMPLETE1,
		LEVEL2, READY_2, RUNNING_2, GAMEOVER2, COMPLETE2,
		LEVEL3, READY_3, RUNNING_3, GAMEOVER3, COMPLETE3,
		CUSTOMLEVEL, READY_C, RUNNING_C, GAMEOVERC, COMPLETEC, 
		HIGHSCORE
	}

	public static GameState statusOfGame;

	public MenuScreen(WOS main) {
		Gdx.app.log("MainScreen", "Loaded");

		statusOfGame = GameState.MENU;
		Gdx.app.log("GameState", statusOfGame.toString());

		batch = new SpriteBatch();
		//Calls the ScrollManager class to activate the scrolling functionality of the background clouds.
		sManager = new ScrollManager(); 
		new DataManagement(); //Class that stores data in.
		new SelectBirdCharacter();

		//This if statement makes the listIterator to move to the orange bird within the Array of SelectBirdCharacter class.
		if(SelectBirdCharacter.trigger != SelectBirdCharacter.birds.get(0)) {
			SelectBirdCharacter.trigger = SelectBirdCharacter.listIterator.next();
			SelectBirdCharacter.repeated = "double2";
		} 

		s = Assets.uiSkin;

	       /*
		* The if statement below operates when the levelInput ArrayList is empty. If it is 
		* then run the for loop to add in blank slots into the array to prevent the custom level
		* screen from crashing.
		* 
		*/
		if(levelInput.isEmpty()) {
			for(int i = 0; i <= 9;) {
				levelInput.add("");
				i++;
			}
		}
	}

	public void show() {
		//Declares how big the arrays are within these variables.
		scale = new Float[33];
		btn = new Button[18];
		btnS = new ButtonStyle[18];

		stage = new Stage();
		btnLayout = new Table();
		lblLayout = new Table();
		lvlLayout = new Table();
		lvlTLayout = new Table();
		iLayout = new Table();
		stLayout = new Table();
		lLayout = new Table();

		//Loads the table layouts.
		uiLayout();
		settingsLayout();
		drawInfoLayout();
		drawLeaderboardUI();
		titleGraphics();

		//Hides the not required layouts at this present moment.
		stLayout.setVisible(false);
		iLayout.setVisible(false);
		lLayout.setVisible(false);

		//Sets the user input to interact with the stage events containing the tables functionality. 
		Gdx.input.setInputProcessor(stage);

		//Defines the orange bird animation.
		anim = Assets.aniOB = new Animation(0.12f, Assets.oBird);
		anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		scale[7] = (W/240f) / f2.getSpaceWidth();
		scale[8] = (H/40f) / f2.getLineHeight();

	       /* 
	        * Makes sure the font is scaled to the according size to stop using other screens 
		* font sizes when going back to this screen.
		*/
		if(f2.getScaleX() == 1.0) {
			f2.setScale(scale[7], scale[8]);
		}
	}

	public void render(float delta) {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Prevents background flickering.
		sManager.update(delta); //Runs the ScrollManager update() method to continually update the clouds X and Y locations.
		runTime += delta/1.2f; //Reduces the delta runtime speed for the bird animation.
		batch.begin();
		skyCloudsUI(); //Renders the background and clouds.
		
		//If the Menu interface is active then draw these graphical elements.
		if(btnLayout.isVisible() == true) {
			bEffect.draw(batch);
			lEffect.draw(batch);
			rEffect.draw(batch);
			lBird.draw(batch);
			lBird.setRegion(anim.getKeyFrame(runTime)); //Uses the runTime variable to slow down the bird animation.
		} 
		batch.end();		

		stage.act(delta);
		stage.draw(); //Draws the stage.

		batch.begin();
		//Draw the other elements so they appear over the title once stage has been called.
		if(btnLayout.isVisible() == true) {
			mBird.draw(batch);
			rBird.draw(batch);
		}

	       /* If the info Boolean variable becomes true then draw the how to play guide image. This only 
		* gets activated once the user presses the info button icon within settings.
		*/
		if(info == true) {
			scale[0] = W/1.0f / Assets.howtPly.getWidth();
			scale[1] = H/1.0f / Assets.howtPly.getHeight();
			batch.draw(Assets.howtPly, 0, 0, Assets.howtPly.getWidth()*scale[0], Assets.howtPly.getHeight()*scale[1]);

			drawInstructionBird(); //Draws the active bird onto the instruction guide. 
		}
		messages(); //Draws the message popups.
		batch.end();
	}

        /*
         * This method acts as the Menu GUI.  
	 */
	public void uiLayout() {
		btnS[4] = new Button.ButtonStyle();
		btnS[4].up = s.getDrawable("play_button");
		btn[4] = new Button(btnS[4]);	
		btn[4].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				hide(); //Calls the hide() method to hide the current interface to allow the Select level GUI to appear.
				if(lvlTLayout.isVisible() == false) {
					lvlTLayout.setVisible(true);
					lvlLayout.setVisible(true);
				} else {
					selectLevelUI();
				}
			}
		});

		btnS[5] = new Button.ButtonStyle();
		btnS[5].up = s.getDrawable("settings_button");
		btn[5] = new Button(btnS[5]);
		btn[5].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if(stLayout.isVisible() == false) {
					stLayout.setVisible(true);
				} else {
					stLayout.setVisible(false);
				}
			}
		});

		btnS[6] = new Button.ButtonStyle();
		btnS[6].up = s.getDrawable("shop_button");
		btn[6] = new Button(btnS[6]);
		btn[6].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				batch.dispose();
				Cage localSettings = new Cage(game);
				localSettings.render(Gdx.graphics.getDeltaTime());
				((Game)Gdx.app.getApplicationListener()).setScreen(localSettings);
			}
		});

		mainTitle = new Label("Wings of Speed", s);

		//Scales the buttons and title to the proper scale according to the screen ratio.
		scale[13] = (W/10f) / btn[4].getWidth();
		scale[14] = (W/12f) / btn[5].getWidth();
		scale[15] = (W/1.5f) / mainTitle.getWidth();
		scale[16] = (H/6.6f) / mainTitle.getHeight();

		mainTitle.setFontScale(scale[15], scale[16]);

		lblLayout.add(mainTitle);
		btnLayout.add();
		btnLayout.add(btn[4]).size(btn[4].getWidth()*scale[13], btn[4].getHeight()*scale[13]).center();
		btnLayout.add();
		btnLayout.row().expand();
		btnLayout.add(btn[5]).size(btn[5].getWidth()*scale[14], btn[5].getHeight()*scale[14]).left().bottom().padBottom(-H/10f);
		btnLayout.add();
		btnLayout.add(btn[6]).size(btn[6].getWidth()*scale[14], btn[6].getHeight()*scale[14]).right().bottom().padBottom(-H/10f);
		//Adds the tables as actors to the stage.
		stage.addActor(btnLayout); //Button layout
		stage.addActor(lblLayout); //Title layout
	}

	/*
	 * selectLevelUI() method is responsible for displaying the Select level GUI.
	 */
	public void selectLevelUI() {
		btnS[7] = new Button.ButtonStyle();
		btnS[7].up = s.getDrawable("selectLevel1");
		btn[7] = new Button(btnS[7]);
		btn[7].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				statusOfGame = GameState.LEVEL1; //Changes the game state to LEVEL1 to start the World and Rendering methods.
				status = "L1";
				cloud = Assets._clouds; //Updates the cloud sprite variable to the white clouds assets.
				//Updates the Global variables to accept these asset preferences.  
				en_W = (int) Assets.enemy[0].getWidth(); 
				en_H = (int) Assets.enemy[0].getHeight();
				crea_W = (int) Assets.crea[1].getWidth();
				crea_H = (int) Assets.crea[1].getHeight();

				//Removes the how to play guide to prevent showing up after restarting the level over and over.
				DataManagement.prefs.remove(DataManagement.getHowtPly1()); 

				SelectLevel select = new SelectLevel(game); //Loads the SelectLevel class.
				((Game)Gdx.app.getApplicationListener()).setScreen(select); //Sets the screen.
			}
		});

		btnS[8] = new Button.ButtonStyle();
		//If the stored twigScore variable equals to 25 then display the unlocked icon.
		if(DataManagement.prefs.getInteger(DataManagement.getTwigScore()) == 25) {
			btnS[8].up = s.getDrawable("selectLevel2");
		} else {
			btnS[8].up = s.getDrawable("selectLevel2_locked");
		}
		btn[8] = new Button(btnS[8]);
		btn[8].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				//Only become unlocked if the twigScore equals to 25.
				if(DataManagement.prefs.getInteger(DataManagement.getTwigScore()) == 25) {
					statusOfGame = GameState.LEVEL2; //Changes the game state to LEVEL2 to start the World and Rendering methods.
					status = "L2";
					en_W = (int) Assets.enemy[0].getWidth();
					en_H = (int) Assets.enemy[0].getHeight();
					crea_W = (int) Assets.crea[23].getWidth();
					crea_H = (int) Assets.crea[23].getHeight();

					//Removes the how to play guide to prevent showing up after restarting the level over and over.
					DataManagement.prefs.remove(DataManagement.getHowtPly2());

					SelectLevel select = new SelectLevel(game);
					((Game)Gdx.app.getApplicationListener()).setScreen(select);
				} else {
					status = "Locked";
				}
			}
		});

		//Temporary code waiting for level 3 gameplay for the future.
		btnS[9] = new Button.ButtonStyle();
		btnS[9].up = s.getDrawable("selectLevel3_locked");
		btn[9] = new Button(btnS[9]);
		btn[9].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				status = "Coming soon";
			}
		});

		btnS[10] = new Button.ButtonStyle();
		btnS[10].up = s.getDrawable("selectCustom");
		btn[10] = new Button(btnS[10]);
		btn[10].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {	
				statusOfGame = GameState.CUSTOMLEVEL; //Changes the game state to CUSTOMLEVEL to start the SelectLevel methods.
				status = "LC";

				SelectLevel select = new SelectLevel(game);
				((Game)Gdx.app.getApplicationListener()).setScreen(select);		
			}
		});

		btnS[11] = new Button.ButtonStyle();
		btnS[11].up = s.getDrawable("back_button");
		btn[11] = new Button(btnS[11]);
		btn[11].addListener(new ClickListener() {
			//Hides the current interface and displays the Menu screen GUI.
			public void clicked(InputEvent event, float x, float y) {
				btnLayout.setVisible(true);
				lblLayout.setVisible(true);
				lvlTLayout.setVisible(false);
				lvlLayout.setVisible(false);	
			}
		});

		lvlTitle = new Label("Select Level", s);

		scale[17] = (W/4f) / btn[7].getWidth();
		scale[18] = (W/2.5f) / lvlTitle.getWidth();
		scale[19] = (H/8f) / lvlTitle.getHeight();

		lvlTitle.setFontScale(scale[18], scale[19]);

		lvlTLayout.add(lvlTitle);
		lvlLayout.row().expand();
		lvlLayout.add(btn[7]).size(btn[7].getWidth()*scale[17], btn[7].getHeight()*scale[17]).center();
		lvlLayout.add(btn[8]).size(btn[8].getWidth()*scale[17], btn[8].getHeight()*scale[17]).center();
		lvlLayout.add(btn[9]).size(btn[9].getWidth()*scale[17], btn[9].getHeight()*scale[17]).center();
		lvlLayout.row().padBottom((H/10f) - (lvlLayout.getHeight() / 2));
		lvlLayout.add();
		lvlLayout.add(btn[10]).size(btn[10].getWidth()*scale[17], btn[10].getHeight()*scale[17]);
		lvlLayout.row().padBottom((H/-8.4f) - (lvlLayout.getHeight() / 2));
		scale[17] = (W/12f) / btn[11].getWidth();
		lvlLayout.add(btn[11]).size(btn[11].getWidth()*scale[17], btn[11].getHeight()*scale[17]).left().padLeft(W/100f);
		stage.addActor(lvlTLayout);
		stage.addActor(lvlLayout);
	}

	/*
	 * This method acts as the Settings GUI.
	 */
	public void settingsLayout() {
		btnS[12] = new Button.ButtonStyle();
		btnS[12].up = s.getDrawable("sound_button");
		btn[12] = new Button(btnS[12]);	
		btn[12].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				soundStatus = false; 
				btn[12].setVisible(false); //sound on
				btn[13].setVisible(true); //sound off
			}
		});

		btnS[13] = new Button.ButtonStyle();
		btnS[13].up = s.getDrawable("soundOff_button");
		btn[13] = new Button(btnS[13]);	
		btn[13].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				soundStatus = true;
				btn[13].setVisible(false); //sound off
				btn[12].setVisible(true); //sound on
			}
		});

		//Switches which sound button to display.
		if(soundStatus == true) {
			btn[12].setVisible(true);
			btn[13].setVisible(false);
		} else {
			btn[12].setVisible(false);
			btn[13].setVisible(true);
		}

		btnS[14] = new Button.ButtonStyle();
		btnS[14].up = s.getDrawable("character_button");
		btn[14] = new Button(btnS[14]);
		btn[14].addListener(new ClickListener() {
			//Loads the SelectBirdCharacter screen.
			public void clicked(InputEvent event, float x, float y) {
				SelectBirdCharacter character = new SelectBirdCharacter();
				((Game)Gdx.app.getApplicationListener()).setScreen(character);
			}
		});

		btnS[15] = new Button.ButtonStyle();
		btnS[15].up = s.getDrawable("info_button");
		btn[15] = new Button(btnS[15]);
		btn[15].addListener(new ClickListener() {
			//Loads the overview How to play guide.
			public void clicked(InputEvent event, float x, float y) {
				info = true;
				hide();
				iLayout.setVisible(true);
			}
		});

		btnS[16] = new Button.ButtonStyle();
		btnS[16].up = s.getDrawable("leader_button");
		btn[16] = new Button(btnS[16]);
		btn[16].addListener(new ClickListener() {
			//Loads the leaderboard GUI displaying three buttons.
			public void clicked(InputEvent event, float x, float y) {
				hide();
				lLayout.setVisible(true);
			}
		});

		btnS[3] = new Button.ButtonStyle();
		btnS[3].up = s.getDrawable("achievement_button");
		btn[3] = new Button(btnS[3]);
		btn[3].addListener(new ClickListener() {
			//Loads the achievement screen via Google Play Services.
			public void clicked(InputEvent event, float x, float y) {
				if (WOS.gServices.isSignedIn()) {
					WOS.gServices.getAchievements(); //Retrieves the users achievements.
				} else {
					WOS.gServices.signIn(); //Calls the signIn() method if the user isn't signed in.
				}
			}
		});

		scale[20] = (W/17f) / btn[12].getWidth();

		stLayout.add(btn[15]).size(btn[15].getWidth()*scale[20], btn[15].getHeight()*scale[20]).padTop(H/66f);
		stLayout.row();
		stLayout.add(btn[12]).size(btn[12].getWidth()*scale[20], btn[12].getHeight()*scale[20]).padTop(H/66f);
		stLayout.add(btn[13]).size(btn[13].getWidth()*scale[20], btn[13].getHeight()*scale[20]).padLeft(-W/16.8f).padTop(H/66f);
		stLayout.row();
		stLayout.add(btn[16]).size(btn[16].getWidth()*scale[20], btn[16].getHeight()*scale[20]).padTop(H/66f);
		stLayout.row();
		stLayout.add(btn[3]).size(btn[3].getWidth()*scale[20], btn[3].getHeight()*scale[20]).padTop(H/66f);
		stLayout.row();
		stLayout.add(btn[14]).size(btn[14].getWidth()*scale[20], btn[14].getHeight()*scale[20]).padTop(H/66f);
		stage.addActor(stLayout);
	}

	/*
	 * This method runs the how to play guide GUI.
	 */
	public void drawInfoLayout() {
		btnS[17] = new Button.ButtonStyle();
		btnS[17].up = s.getDrawable("btnClose");
		btn[17] = new Button(btnS[17]);
		btn[17].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				info = false;
				btnLayout.setVisible(true);
				lblLayout.setVisible(true);
				stLayout.setVisible(false);
				iLayout.setVisible(false);
			}
		});

		scale[2] = (W/24f) / btn[17].getWidth();

		iLayout.add(btn[17]).size(btn[17].getWidth()*scale[2], btn[17].getHeight()*scale[2]).expand();
		stage.addActor(iLayout);
	}

	/*
	 * This method is responsible of displaying the leaderboard GUI.
	 */
	public void drawLeaderboardUI() {
		btnS[0] = new Button.ButtonStyle();
		btnS[0].up = s.getDrawable("back_button");
		btn[0] = new Button(btnS[0]);	
		btn[0].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btnLayout.setVisible(true);
				lblLayout.setVisible(true);
				lLayout.setVisible(false);	
			}
		});

		btnS[1] = new Button.ButtonStyle();
		btnS[1].up = s.getDrawable("leader1_button");
		btn[1] = new Button(btnS[1]);	
		btn[1].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (WOS.gServices.isSignedIn()) {
					WOS.gServices.showLeaderboard1(); //Loads the level 1 leaderboard.
				} else {
					WOS.gServices.signIn();
				}
			}
		});

		btnS[2] = new Button.ButtonStyle();
		btnS[2].up = s.getDrawable("leader2_button");
		btn[2] = new Button(btnS[2]);	
		btn[2].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (WOS.gServices.isSignedIn()) {
					WOS.gServices.showLeaderboard2(); //Loads the level 2 leaderboard.
				} else {
					WOS.gServices.signIn();
				}
			}
		});

		leaderTitle = new Label("Select LeaderBoard", s);

		scale[10] = (W/2.0f) / leaderTitle.getWidth();
		scale[11] = (H/6.5f) / leaderTitle.getHeight();

		leaderTitle.setFontScale(scale[10], scale[11]);

		scale[9] = (W/12f) / btn[0].getWidth();
		scale[12] = (W/10f) / btn[1].getWidth();

		lLayout.add(leaderTitle).right().padRight(-W/4.25f).padTop(H/8f);
		lLayout.row().expand();
		lLayout.add(btn[1]).size(btn[0].getWidth()*scale[12], btn[0].getHeight()*scale[12]).bottom().padLeft(W/9.3f);
		lLayout.add(btn[2]).size(btn[1].getWidth()*scale[12], btn[1].getHeight()*scale[12]).bottom().padRight(W/8f);
		lLayout.row().expand();
		lLayout.add(btn[0]).size(btn[0].getWidth()*scale[9], btn[0].getHeight()*scale[9]).left().bottom().padBottom(H/50f).padLeft(W/98f);
		stage.addActor(lLayout);
	}

	/*
	 * This method is to select the correct bird for the how to play guide by calling the 
	 * getBirdSelector() method within DataManagement class to find out which bird is currently
	 * active.
	 */
	public void drawInstructionBird() {
		Sprite bird = null;
		float h = H/1.5f;
		if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 0) {
			bird = Assets.birds[3];
			scale[3] = W/8.0f / bird.getWidth();
			scale[4] = H/5.0f / bird.getHeight();
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 1) {
			bird = Assets.birds[8];
			scale[3] = W/8.0f / bird.getWidth();
			scale[4] = H/5.0f / bird.getHeight();
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 2) {
			bird = Assets.birds[12];
			scale[3] = W/10.0f / bird.getWidth();
			scale[4] = H/8.0f / bird.getHeight();
			h = H/1.4f;
		}
		else if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == 3) {
			bird = Assets.birds[16];
			scale[3] = W/8.0f / bird.getWidth();
			scale[4] = H/8.0f / bird.getHeight();
			h = H/1.4f;
		}

		if(bird != null) { //If bird is not null then draw the bird within the render() method.
			batch.draw(bird, W/122f, h, bird.getWidth() * scale[3], bird.getHeight() * scale[4]);	
		}
	}

	/*
	 * This method acts like the toast API displaying small message popups.
	 */
	public void messages() {
		if(status.equals("Coming soon")) {
			scale[5] = (W/8f) / Assets.message.getWidth();
			scale[6] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.28f, H/82f, Assets.message.getWidth()*scale[5], Assets.message.getHeight()*scale[6]);
			f2.draw(batch,"Coming soon", (f2.getSpaceWidth()/2) + (W/2.20f), (f2.getLineHeight()/2) + (H/22f));
		} else if(status.equals("Locked")) {
			scale[5] = (W/8f) / Assets.message.getWidth();
			scale[6] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.34f, H/82f, Assets.message.getWidth()*scale[5], Assets.message.getHeight()*scale[6]);
			f2.draw(batch,"Level locked", (f2.getSpaceWidth()/2) + (W/2.23f), (f2.getLineHeight()/2) + (H/22f));
		}
		//Runs a timer within the if statement displaying how long the message appears for.
		if(status.equals("Coming soon") || status.equals("Locked")) {
			Timer.schedule(new Task() {
				@Override
				public void run() {
					status = "End";
				}
			}, 1.5f);
		}
	}

	/*
	 * This method is responsible of drawing the graphical elements around the Wings of Speed title.
	 * The Skin "s" variable is being used via the Global class and retrieving the sprites within its 
	 * atlas.
	 */
	public void titleGraphics() {
		bEffect = s.getSprite("bottomTitle_effect");
		if(bEffect.getWidth() == 352.0) {

			scale[21] = (W/7.8f) / bEffect.getWidth();
			scale[22] = (H/5f) / bEffect.getHeight();
			bEffect.setSize(bEffect.getRegionWidth() * scale[21], bEffect.getRegionHeight() * scale[22]);
			bEffect.setPosition((W /  1.75f) - (bEffect.getWidth() / 2), (H /  1.91f) - (bEffect.getHeight() / 2));
		}

		mBird = s.getSprite("middleBird");
		if(mBird.getWidth() == 252.0) {

			scale[23] = (W/18f) / mBird.getWidth();
			scale[24] = (H/9f) / mBird.getHeight();
			mBird.setSize(mBird.getRegionWidth() * scale[23], mBird.getRegionHeight() * scale[24]);
			mBird.setPosition((W /  2.06f) - (mBird.getWidth() / 2), (H/ 1.42f) - (mBird.getHeight() / 2));
		}

		lEffect = s.getSprite("leftTitle_effect");
		if(lEffect.getWidth() == 182.0) {

			scale[25] = (W/15f) / lEffect.getWidth();
			scale[26] = (H/5f) / lEffect.getHeight();
			lEffect.setSize(lEffect.getRegionWidth() * scale[25], lEffect.getRegionHeight() * scale[26]);
			lEffect.setPosition((W /  4.3f) - (lEffect.getWidth() / 2), (H /  1.31f) - (lEffect.getHeight() / 2));
		}

		lBird = new Sprite(Assets.birds[0]);
		scale[27] = (W/13f) / lBird.getWidth();
		scale[28] = (H/7f) / lBird.getHeight();
		lBird.setSize(lBird.getRegionWidth() * scale[27], lBird.getRegionHeight() * scale[28]);
		lBird.setPosition((W /  3.4f) - (lBird.getWidth() / 2), (H /  1.27f) - (lBird.getHeight() / 2));

		rEffect = s.getSprite("rightTitle_effect");
		if(rEffect.getWidth() == 352.0) {

			scale[29] = (W/6f) / rEffect.getWidth();
			scale[30] = (H/4f) / rEffect.getHeight();
			rEffect.setSize(rEffect.getRegionWidth() * scale[29], rEffect.getRegionHeight() * scale[30]);
			rEffect.setPosition((W /  1.39f) - (rEffect.getWidth() / 2), (H /  1.301f) - (rEffect.getHeight() / 2));
		}

		rBird = s.getSprite("rightBird");
		if(rBird.getWidth() == 128.0) {

			scale[31] = (W/19f) / rBird.getWidth();
			scale[32] = (H/10f) / rBird.getHeight();
			rBird.setSize(rBird.getRegionWidth() * scale[31], rBird.getRegionHeight() * scale[32]);
			rBird.setPosition((W /  1.21f) - (rBird.getWidth() / 2), (H /  1.342f) - (rBird.getHeight() / 2));
		}
	}

	/*
	 * This method retrieves the correct preferences of X and Y coordinates within the ScrollManager class and 
	 * renders to the screen displaying as the scrollable background moving leftwards.
	 */
	public void skyCloudsUI() {
		//Draws the blue sky.
		batch.draw(Assets.bgMainSky, 0, (H/2)/(Assets.bgMainSky.getHeight()/2), Assets.bgMainSky.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);

		//Renders the clouds constantly updating their X and Y locations.
		batch.draw(Assets._clouds[0], ScrollManager.clouds[0].getX(), ScrollManager.clouds[0].getY(),
				Assets._clouds[0].getRegionWidth() * Clouds.s[0], Assets._clouds[0].getRegionHeight() * Clouds.s[1]);
		batch.draw(Assets._clouds[0], ScrollManager.clouds[1].getX(), ScrollManager.clouds[1].getY(),
				Assets._clouds[0].getRegionWidth() * Clouds.s[0], Assets._clouds[0].getRegionHeight() * Clouds.s[1]);
		batch.draw(Assets._clouds[1], ScrollManager.clouds[2].getX(), ScrollManager.clouds[2].getY(),
				Assets._clouds[1].getRegionWidth() * Clouds.s[2] , Assets._clouds[1].getRegionHeight() * Clouds.s[3]);
		batch.draw(Assets._clouds[1], ScrollManager.clouds[3].getX(), ScrollManager.clouds[3].getY(),
				Assets._clouds[1].getRegionWidth() * Clouds.s[2], Assets._clouds[1].getRegionHeight() * Clouds.s[3]);  
		batch.draw(Assets._clouds[2], ScrollManager.clouds[4].getX(), ScrollManager.clouds[4].getY(),
				Assets._clouds[2].getRegionWidth() * Clouds.s[4] , Assets._clouds[2].getRegionHeight() * Clouds.s[5]);
		batch.draw(Assets._clouds[2], ScrollManager.clouds[5].getX(), ScrollManager.clouds[5].getY(),
				Assets._clouds[2].getRegionWidth() * Clouds.s[4], Assets._clouds[2].getRegionHeight() * Clouds.s[5]);		
		batch.draw(Assets._clouds[3], ScrollManager.clouds[6].getX(), ScrollManager.clouds[6].getY(),
				Assets._clouds[3].getRegionWidth() * Clouds.s[6], Assets._clouds[3].getRegionHeight() * Clouds.s[7]); 
		batch.draw(Assets._clouds[3], ScrollManager.clouds[7].getX(), ScrollManager.clouds[7].getY(),
				Assets._clouds[3].getRegionWidth() * Clouds.s[6] , Assets._clouds[3].getRegionHeight() * Clouds.s[7]);
		batch.draw(Assets._clouds[3], ScrollManager.clouds[8].getX(), ScrollManager.clouds[8].getY(),
				Assets._clouds[3].getRegionWidth() * Clouds.s[6], Assets._clouds[3].getRegionHeight() * Clouds.s[7]);	
		batch.draw(Assets._clouds[4], ScrollManager.clouds[9].getX(), ScrollManager.clouds[9].getY(),
				Assets._clouds[4].getRegionWidth() * Clouds.s[8] , Assets._clouds[4].getRegionHeight() * Clouds.s[9]);	
		batch.draw(Assets._clouds[4], ScrollManager.clouds[10].getX(), ScrollManager.clouds[10].getY(),
				Assets._clouds[4].getRegionWidth() * Clouds.s[8], Assets._clouds[4].getRegionHeight() * Clouds.s[9]);	
		batch.draw(Assets._clouds[4], ScrollManager.clouds[11].getX(), ScrollManager.clouds[11].getY(),
				Assets._clouds[4].getRegionWidth() * Clouds.s[8], Assets._clouds[4].getRegionHeight() * Clouds.s[9]);
	}

	/*
	 * This resize method is responsible for resizing the table layouts and background 
	 * to the suitable dimensions of the screen ratio. 
	 */
	public void resize(int width, int height) {
		//This if statement repeats drawing the blue sky background to fill up the screen.
		if(Gdx.graphics.getWidth() < 799) {
			REPEAT_W = width /  Assets.bgMainSky.getWidth() * 2;
		} else {
			REPEAT_W = width /  Assets.bgMainSky.getWidth() + 1;
		}
		REPEAT_H = height / Assets.bgMainSky.getHeight() + 1;    

		btnLayout.setSize(W/1.02f, H/3);
		btnLayout.setPosition((W/2) - (btnLayout.getWidth() / 2), (H/3.5f) - (btnLayout.getHeight() / 2));
		lblLayout.setSize(W, H/4);
		lblLayout.setPosition((W/2) - (lblLayout.getWidth() / 2), (H/1.5f) - (lblLayout.getHeight() / 2));
		lvlTLayout.setSize(W, H/4);
		lvlTLayout.setPosition((W/2) - (lvlTLayout.getWidth() / 2), (H/1.3f) - (lvlTLayout.getHeight() / 2));
		lvlLayout.setSize(W, H/2);
		lvlLayout.setPosition((W/2) - (lvlLayout.getWidth() / 2), (H/1.9f) - (lvlLayout.getHeight() / 2));

		stLayout.setSize(W/10, H/2.3f);
		stLayout.setPosition((W/18.8f) - (stLayout.getWidth() / 2), (H/2.20f) - (stLayout.getHeight() / 2.2f));

		iLayout.setSize(W/20, H/10);
		iLayout.setPosition((W/1.028f) - (iLayout.getWidth() / 2), (H/1.05f) - (iLayout.getHeight() / 2));

		lLayout.setSize(W, H);
		lLayout.setPosition(0, 0);
	}

	public void hide() {
		btnLayout.setVisible(false);
		lblLayout.setVisible(false);
		stLayout.setVisible(false);
	}

	public void dispose() {
		batch.dispose();
	}

	public static GameState getStatusOfGame() {
		return statusOfGame;
	}

	public void setStatusOfGame(GameState updatedStatus) {
		statusOfGame = updatedStatus;
	}
	
	public void pause(){}

	public void resume(){}
}
