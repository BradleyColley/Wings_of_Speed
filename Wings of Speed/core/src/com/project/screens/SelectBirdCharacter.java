package com.project.screens;

/*
 * The SelectBirdCharacter implements the Screen interface inheriting its methods. Purpose of this class is 
 * to allow the user to select their bird character to use within the gameplay.
 * 
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;
import java.util.ListIterator;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.project.game.WOS;
import com.project.utilities.Assets;
import com.project.utilities.AssetsManager;
import com.project.utilities.DataManagement;

import static com.project.utilities.Global.*;

public class SelectBirdCharacter implements Screen {
	private Float[] scale;
	private ImageButton[] btnI;
	private Button[] btn;
	private ButtonStyle[] btnS;
	private String status = "";

	public static Table cLayout;
	public static String repeated, trigger, t;
	public static ArrayList<String> birds;
	public static ListIterator<String> listIterator; 

	public SelectBirdCharacter() {
		batch = new SpriteBatch();
		scale = new Float[18];

		f4.setScale(1);
		scale[2] = (W/60f) / f4.getSpaceWidth();
		scale[3] = (H/12f) / f4.getLineHeight();
	       /* 
	        * Makes sure the font is scaled to the according size to stop using other screens 
		* font sizes when going back to this screen.
		*/
		if(f4.getScaleX() != 0.0) {
			f4.setScale(scale[2], scale[3]);
		}

		f1.setScale(1);
		scale[11] = (W/240f) / f1.getSpaceWidth();
		scale[12] = (H/40f) / f1.getLineHeight();
		if(f1.getScaleX() != 0.0) {
			f1.setScale(scale[11], scale[12]);
		}

		//Adds the 4 birds to the ArrayList.
		birds = new ArrayList<String>();
		birds.add("0");
		birds.add("1");
		birds.add("2");
		birds.add("3");
		listIterator = birds.listIterator(); //listIterator equals the ArrayList.

		drawLayout();

		dManager = new DataManagement();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		drawUI(); //Draws the interface.
		drawBirds(); //Renders the characters.
		messages();
		batch.end();
		stage.draw();
		drawActive(); //Draws the active button.

		//Changes the Android back button functionality to go back to this screen.
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			MenuScreen menu = new MenuScreen(game);
			((Game)Gdx.app.getApplicationListener()).setScreen(menu);
		}
	}

	/*
	 * This method draws out the graphical elements.
	 */
	public void drawUI() {
		scale[0] = (W/1.1f) / Assets.treeBG.getWidth();
		scale[1] = (H/1.01f) / Assets.treeBG.getHeight();
		scale[4] = (W/20f) / Assets.charLogo.getWidth();
		scale[5] = (H/15f) / Assets.charLogo.getHeight();

		batch.draw(Assets.bgMainSky, 0, (H/2)/(Assets.bgMainSky.getHeight()/2), Assets.bgMainSky.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);		
		batch.draw(Assets.treeBG, (-W/180f), H/80f, Assets.treeBG.getWidth() * scale[0], Assets.treeBG.getHeight() * scale[1]);
		f4.draw(batch, "Select Character", (f4.getSpaceWidth()/2) + (W/4.1f), (f4.getLineHeight()/2) + (H/1.08f));
		f1.drawMultiLine(batch,"    PRESS"+ "\n" + "to activate", (f1.getSpaceWidth()/2) + (W/1.1f), (f1.getLineHeight()/2) + (H/4.8f));
		f1.drawMultiLine(batch,"    Abilities"+"\n"+"(Coming soon...)", (f1.getSpaceWidth()/2) + (W/4.5f), (f1.getLineHeight()/2) + (H/3.5f));
		batch.draw(Assets.charLogo, W/1.39f, H/1.105f, Assets.charLogo.getWidth() * scale[4], Assets.charLogo.getHeight() * scale[5]);
	}

	/*
	 * This method acts as the table layout interface for this class.
	 */
	public void drawLayout() {
		stage = new Stage();
		cLayout = new Table();
		btnI = new ImageButton[3];
		btnS = new ButtonStyle[3];
		btn = new Button[3];

		Skin layout = new Skin((TextureAtlas)AssetsManager.assetM.get("img/ui/charUI.atlas", TextureAtlas.class));

		btnI[0] = new ImageButton(layout.getDrawable("leftArrow"));
		btnI[0].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y) {
				/*
				 * When the left arrow is pressed, the listIterator will run 
				 * by going backwards in the ArrayList. I have two if statement
				 * conditions inside the main statement to prevent the user having 
				 * to click the left arrow twice to move in the list. 
				 * 
				 * If I didn't have these conditions, then the user would have to press
				 * the left arrow twice for the first time. 
				 * 
				 * If repeated equals "double1" from the right arrow statement (if it was pressed
				 * before this arrow) then go backwards in the list twice to display the 
				 * previous character. Otherwise just move backwards once.
				 */
				if (listIterator.hasPrevious()) {
					if(repeated == "double1") {
						listIterator.previous();
						trigger = listIterator.previous();
						repeated = "double2";
					}	else {
						trigger = listIterator.previous();
						repeated = "double2";
					}
				}
			}
		});

		btnI[1] = new ImageButton(layout.getDrawable("rightArrow"));
		btnI[1].addListener(new ClickListener()	{
			public void clicked(InputEvent event, float x, float y)
			{
				/*
				 * When the right arrow is pressed, the listIterator will run 
				 * by going forwards in the ArrayList. I have two if statement
				 * conditions inside the main statement to prevent the user having 
				 * to click the right arrow twice to move in the list. 
				 * 
				 * If I didn't have these conditions, then the user would have to press
				 * the right arrow twice for the first time. 
				 * 
				 * If repeated equals "double2" from the left arrow statement (if it was pressed
				 * before this arrow) then go forwards in the list twice to display the 
				 * next character. Otherwise just move forwards once.
				 */
				if (listIterator.hasNext()) {
					if(repeated == "double2") {
						listIterator.next();
						trigger =	listIterator.next();
						repeated = "double1";
					} else {
						trigger = listIterator.next();
						repeated = "double1";
					}
				}
			}
		});

		btnS[0] = new Button.ButtonStyle();
		btnS[0].up = Assets.uiSkin.getDrawable("back_button");
		btn[0] = new Button(btnS[0]);
		btn[0].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				MenuScreen menu = new MenuScreen(game);
				((Game)Gdx.app.getApplicationListener()).setScreen(menu);
			}
		});

		btnI[2] = new ImageButton(layout.getDrawable("non-activeBtn"));
		btnI[2].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				/*
				 * The for loop constantly checks in the if statement if the bird ArrayList element 
				 * is active within the DataMangement class and if its true then the button displays active.
				 * Otherwise it will render the non active button.
				 */
				for(int i = 0; i <= 3;) {
					if(trigger == birds.get(i) && 
							DataManagement.prefs.getInteger(DataManagement.characterStore.get(Integer.valueOf(trigger))) == Integer.valueOf(trigger)) {
						
						dManager.saveBird();
						status = "Active";

						//An achievement gets unlocked once the user activates a bird.
						if (WOS.gServices.isSignedIn()) {
							WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQBA");
						}
					} 
					
					if(DataManagement.prefs.getInteger(DataManagement.characterStore.get(Integer.valueOf(trigger))) != Integer.valueOf(trigger)) {
						status = "Denied";
					}
					i++;
				}		
			}
		});

		scale[6] = (W/8f) / btnI[0].getWidth();
		scale[7] = (H/4f) / btnI[0].getHeight();
		scale[8] = (W/12f) / btn[0].getWidth();

		cLayout.add(btnI[0]).size(btnI[0].getWidth()*scale[6], btnI[0].getHeight()*scale[7]).top().left();
		cLayout.add().size(W/1f, H/1f).expand();
		cLayout.add(btnI[1]).size(btnI[1].getWidth()*scale[6], btnI[1].getHeight()*scale[7]).top().right();
		cLayout.row();
		cLayout.add(btn[0]).size(btn[0].getWidth()*scale[8], btn[0].getHeight()*scale[8]).left().padLeft(W/100f).padBottom(H/54f);
		cLayout.add();
		cLayout.add(btnI[2]).size(btnI[2].getWidth()*scale[8], btnI[2].getHeight()*scale[8]).right().padRight(W/100f).padBottom(H/54f);
		stage.addActor(cLayout);
	}

	/*
	 * This method renders the bird characters onto the screen displaying them unlocked or
	 * locked.
	 */
	public void drawBirds() {		
		if(trigger == birds.get(0)) {	
			scale[9] = (W/5f) / Assets.bird1.getWidth();
			scale[10] = (H/3f) / Assets.bird1.getWidth();

			batch.draw(Assets.bird1, W/2.6f, H/2.55f, Assets.bird1.getWidth() * scale[9], Assets.bird1.getHeight() * scale[10]);
		} 

		if (trigger == birds.get(1)) {
			scale[9] = (W/6f) / Assets.bird4.getWidth();
			scale[10] = (H/3.5f) / Assets.bird4.getWidth();

			batch.draw(Assets.bird4, W/2.45f, H/2.4f, Assets.bird4.getWidth() * scale[9], Assets.bird4.getHeight() * scale[10]);
			//This if statement draws the locked icon on top of the character if it doesn't exist in the characterStore ArrayList.
			if(!DataManagement.prefs.contains(DataManagement.characterStore.get(1))) {
				scale[16] = (W/18f) / Assets.locked.getWidth();
				scale[17] = (H/10f) / Assets.locked.getWidth();

				batch.draw(Assets.locked, W/2.18f, H/1.95f, Assets.locked.getWidth() * scale[16], Assets.locked.getHeight() * scale[17]);
			}
		}

		if (trigger == birds.get(2)) {
			scale[9] = (W/8f) / Assets.bird3.getWidth();
			scale[10] = (H/5f) / Assets.bird3.getWidth();

			batch.draw(Assets.bird3, W/2.4f, H/2.23f, Assets.bird3.getWidth() * scale[9], Assets.bird3.getHeight() * scale[10]);
			//This if statement draws the locked icon on top of the character if it doesn't exist in the characterStore ArrayList.
			if(!DataManagement.prefs.contains(DataManagement.characterStore.get(2))) {
				scale[16] = (W/18f) / Assets.locked.getWidth();
				scale[17] = (H/10f) / Assets.locked.getWidth();

				batch.draw(Assets.locked, W/2.18f, H/1.95f, Assets.locked.getWidth() * scale[16], Assets.locked.getHeight() * scale[17]);
			}
		} else if (trigger == birds.get(3)) {			
			scale[9] = (W/6.5f) / Assets.bird5.getWidth();
			scale[10] = (H/4f) / Assets.bird5.getWidth();

			batch.draw(Assets.bird5, W/2.4f, H/2.3f, Assets.bird5.getWidth() * scale[9], Assets.bird5.getHeight() * scale[10]);
			//This if statement draws the locked icon on top of the character if it doesn't exist in the characterStore ArrayList.
			if(!DataManagement.prefs.contains(DataManagement.characterStore.get(3))) {
				scale[16] = (W/18f) / Assets.locked.getWidth();
				scale[17] = (H/10f) / Assets.locked.getWidth();

				batch.draw(Assets.locked, W/2.18f, H/1.95f, Assets.locked.getWidth() * scale[16], Assets.locked.getHeight() * scale[17]);
			}
		}
	}

	/*
	 * This method is responsible for displaying the active button showing a red dot.
	 */
	public void drawActive() {
		if(DataManagement.prefs.getInteger(DataManagement.getBirdSelector()) == Integer.valueOf(trigger)) {			
			batch.begin();
			batch.draw(Assets.aBtn, W/1.1032f, H/55f, Assets.aBtn.getWidth() * scale[8], Assets.aBtn.getHeight() * scale[8]);
			batch.end();
		} 
	}

	/*
	 * This method acts like the toast API displaying small message popups.
	 */
	public void messages() {
		if(status.equals("Active")) {
			scale[14] = (W/8f) / Assets.message.getWidth();
			scale[15] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.28f, H/82f, Assets.message.getWidth()*scale[14], Assets.message.getHeight()*scale[15]);
			f1.draw(batch,"Active", (f1.getSpaceWidth()/2) + (W/2.1f), (f1.getLineHeight()/2) + (H/22f));
		} else if(status.equals("Denied")) {
			scale[14] = (W/8f) / Assets.message.getWidth();
			scale[15] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.34f, H/82f, Assets.message.getWidth()*scale[14], Assets.message.getHeight()*scale[15]);
			f1.draw(batch,"Bird is locked", (f1.getSpaceWidth()/2) + (W/2.25f), (f1.getLineHeight()/2) + (H/22f));
		}
		if(status.equals("Active") || status.equals("Denied")) {
			Timer.schedule(new Task() {
				@Override
				public void run() {
					status = "End";
				}
			}, 1.5f);
		}
	}

	@Override
	public void resize(int width, int height) {
		//Repeats the blue sky background horizontally and vertically.
		if(Gdx.graphics.getWidth() < 799){
			REPEAT_W = width / Assets.bgMainSky.getWidth() *2 ;
		} else {
			REPEAT_W = width / Assets.bgMainSky.getWidth()+1;
		}
		REPEAT_H = height / Assets.bgMainSky.getHeight() + 1;    

		cLayout.setSize(W/1f, H/1.5f);
		cLayout.setPosition((W/2f) - (cLayout.getWidth() / 2f), (H/3f) - (cLayout.getHeight() / 2f));
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
