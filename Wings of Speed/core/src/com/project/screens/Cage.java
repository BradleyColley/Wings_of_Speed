package com.project.screens;

/*
 * The Cage (shop) implements the Screen interface inheriting its methods. Purpose of this class
 * is to direct the user to the bird content GUI to purchase new characters.
 *  
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.project.game.WOS;
import com.project.utilities.Assets;
import com.project.utilities.DataManagement;
import com.project.utilities.ScrollManager;

import static com.project.utilities.Global.*;

public class Cage implements Screen {

	private Table cLayout, bLayout;
	private Button[] btn;
	private ButtonStyle[] btnS;
	private Label[] bLbl;
	private Float[] scale;

	public Cage(WOS main) {
		batch = new SpriteBatch();
		sManager = new ScrollManager();
		dManager = new DataManagement();
		stage = new Stage();
		cLayout = new Table();
		bLayout = new Table();
		
		btn = new Button[8];
		btnS = new ButtonStyle[8];
		bLbl = new Label[6];
		scale = new Float[25];
		
		drawLayout();
		drawCharacterLayout();
		bLayout.setVisible(false);
		
		f1.setScale(1);
		scale[7] = (W/120f) / f1.getSpaceWidth();
		scale[8] = (H/35f) / f1.getLineHeight();
		
	       /* Makes sure the font is scaled to the according size to stop using other screens 
		* font sizes when going back to this screen.
		*/		
		if(f1.getScaleX() != 0.0) {
			f1.setScale(scale[7], scale[8]);
		}		

		scale[23] = (W/175f) / f2.getSpaceWidth();
		scale[24] = (H/32f) / f2.getLineHeight();
		
		if(f2.getScaleX() == 1.0) {
			f2.setScale(scale[23], scale[24]);
		}	
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sManager.update(delta);
		batch.begin();
		cageUI(); //Renders the GUI.
		messages();
		batch.end();
		stage.draw();
		
		//Changes the Android back button functionality to go back to this screen.
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			MenuScreen menu = new MenuScreen(game);
			((Game)Gdx.app.getApplicationListener()).setScreen(menu);
		}	
	}

	/*
	 * This method is responsible for rendering the Cage GUI. 
	 */
	public void cageUI() {
		//Draws the blue sky within the Render() method.
		batch.draw(Assets.bgMainSky, 0, (H/2)/(Assets.bgMainSky.getHeight()/2), Assets.bgMainSky.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);

		scale[5] = (W/5f) / Assets.tEffect.getWidth();
		scale[6] = (H/5f) / Assets.tEffect.getHeight();		
		batch.draw(Assets.tEffect, W/2.38f, H/1.36f, Assets.tEffect.getWidth()*scale[5], Assets.tEffect.getHeight()*scale[6]);	

		//Displays users coin chest overall amount by retrieving it from the DataManagement class where it stores information.
		f1.draw(batch,String.format("%04d",DataManagement.prefs.getInteger(DataManagement.getCoins())), (f1.getSpaceWidth()/2) + (W/1.12f), 
				(f1.getLineHeight()/2) + (H/1.03f)); 

		scale[9] = (W/55f) / Assets.coin.getWidth();
		scale[10] = (H/35f) / Assets.coin.getHeight();	
		batch.draw(Assets.coin, W/1.03f, H/1.042f, Assets.coin.getWidth()*scale[9],  Assets.coin.getHeight()*scale[10]);	

		scale[11] = (W/8f) / Assets.birdPeak.getWidth();
		scale[12] = (H/8f) / Assets.birdPeak.getHeight();	
		batch.draw(Assets.birdPeak, W/1.135f, -H/210f, Assets.birdPeak.getWidth()*scale[11],  Assets.birdPeak.getHeight()*scale[12]);	

		/*
		 * This if statement draws the bird GUI for users to purchase characters.
		 */
		if(bLayout.isVisible()) {
			scale[14] = (H/4.8f) / Assets.cageBG.getHeight();	
			batch.draw(Assets.cageBG, 0, H/1.85f, Assets.cageBG.getWidth() * REPEAT_W2, 
					Assets.cageBG.getHeight()*scale[14]);
			batch.draw(Assets.cageBG, 0, H/2.83f, Assets.cageBG.getWidth() * REPEAT_W2, 
					Assets.cageBG.getHeight()*scale[14]);
			batch.draw(Assets.cageBG, 0, H/6.02f, Assets.cageBG.getWidth() * REPEAT_W2, 
					Assets.cageBG.getHeight()*scale[14]);
			
			batch.draw(Assets.coin, W/1.12f, H/1.683f, Assets.coin.getWidth()*scale[9], Assets.coin.getHeight()*scale[10]);	
			batch.draw(Assets.coin, W/1.11f, H/2.455f, Assets.coin.getWidth()*scale[9], Assets.coin.getHeight()*scale[10]);	
			batch.draw(Assets.coin, W/1.11f, H/4.0f, Assets.coin.getWidth()*scale[9], Assets.coin.getHeight()*scale[10]);	

			/* If DataManagement class contains purchased birds in these element positions inside the 
			 * characterStore ArrayList then draw the updated background row.
			 */
			if(DataManagement.prefs.contains(DataManagement.characterStore.get(1))) {
				batch.draw(Assets.activeBG, 0, H/1.85f, Assets.activeBG.getWidth() * REPEAT_W2, 
						Assets.activeBG.getHeight()*scale[14]);
			}
			if(DataManagement.prefs.contains(DataManagement.characterStore.get(2))) {
				batch.draw(Assets.activeBG, 0, H/2.83f, Assets.activeBG.getWidth() * REPEAT_W2, 
						Assets.activeBG.getHeight()*scale[14]);
			}
			if(DataManagement.prefs.contains(DataManagement.characterStore.get(3))) {
				batch.draw(Assets.activeBG, 0, H/6.02f, Assets.activeBG.getWidth() * REPEAT_W2, 
						Assets.activeBG.getHeight()*scale[14]);
			}
		}
	}

	/*
	 * This method acts like the toast API displaying small message popups.
	 */
	public void messages() {
		if(status.equals("Purchased")) {
			scale[21] = (W/8f) / Assets.message.getWidth();
			scale[22] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.28f, H/82f, Assets.message.getWidth()*scale[21], Assets.message.getHeight()*scale[22]);
			f2.draw(batch,"Purchased", (f2.getSpaceWidth()/2) + (W/2.21f), (f2.getLineHeight()/2) + (H/22f));
		} else if(status.equals("Denied")) {
			scale[21] = (W/5.5f) / Assets.message.getWidth();
			scale[22] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.34f, H/82f, Assets.message.getWidth()*scale[21], Assets.message.getHeight()*scale[22]);
			f2.draw(batch,"Already Purchased", (f2.getSpaceWidth()/2) + (W/2.31f), (f2.getLineHeight()/2) + (H/22f));
		}
		else if(status.equals("Requirements not met")) {
			scale[21] = (W/4.58f) / Assets.message.getWidth();
			scale[22] = (H/14f) / Assets.message.getHeight();

			batch.draw(Assets.message, W/2.55f, H/82f, Assets.message.getWidth()*scale[21], Assets.message.getHeight()*scale[22]);
			f2.draw(batch,"Requirements not met", (f2.getSpaceWidth()/2) + (W/2.40f), (f2.getLineHeight()/2) + (H/22f));
		}
		if(status.equals("Purchased") || status.equals("Denied") || status.equals("Requirements not met")) {
			Timer.schedule(new Task() {
				@Override
				public void run() {
					status = "End";
				}
			}, 1.5f);
		}
	}

	/*
	 * This method acts as the main table Cage layout.
	 */
	public void drawLayout() {
		Skin s = Assets.cSkin;

		btnS[0] = new Button.ButtonStyle();
		btnS[0].up = s.getDrawable("btnCharacters");
		btn[0] = new Button(btnS[0]);	
		btn[0].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				btn[0].setVisible(false);
				btn[1].setVisible(false);
				btn[2].setVisible(false);
				btn[3].setVisible(false);
				bLayout.setVisible(true);
			}
		});

		//Temporary code for the future waiting to contain clothing for the characters.
		btnS[1] = new Button.ButtonStyle();
		btnS[1].up = s.getDrawable("btnClothing");
		btn[1] = new Button(btnS[1]);	
		btn[1].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
			}
		});

		//Temporary code for the future waiting to contain utilities to temporary buff the gameplay character.
		btnS[2] = new Button.ButtonStyle();
		btnS[2].up = s.getDrawable("btnUtilities");
		btn[2] = new Button(btnS[2]);	
		btn[2].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
			}
		});

		btnS[3] = new Button.ButtonStyle();
		btnS[3].up = Assets.uiSkin.getDrawable("back_button");
		btn[3] = new Button(btnS[3]);

		btn[3].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				status = "menu";
				sManager = new ScrollManager();
				MenuScreen menu = new MenuScreen(game);
				((Game)Gdx.app.getApplicationListener()).setScreen(menu);
			}
		});

		Label lbl = new Label("Cage", s);
		scale[0] = (W/6f) / lbl.getWidth();
		scale[1] = (H/10f) / lbl.getHeight();

		lbl.setFontScale(scale[0], scale[1]);

		scale[2] = (W/7f) / btn[0].getWidth();
		scale[3] = (H/3f) / btn[0].getHeight();
		scale[4] = (W/12f) / btn[3].getWidth();

		cLayout.add();
		cLayout.add(lbl).size(lbl.getWidth()*scale[0], lbl.getHeight()*scale[1]);
		cLayout.add();
		cLayout.row().expand().bottom();
		cLayout.add(btn[0]).size(btn[0].getWidth()*scale[2], btn[0].getHeight()*scale[3]).center().right();
		cLayout.add(btn[1]).size(btn[0].getWidth()*scale[2], btn[0].getHeight()*scale[3]).center();
		cLayout.add(btn[2]).size(btn[0].getWidth()*scale[2], btn[0].getHeight()*scale[3]).center().left();
		cLayout.row();
		cLayout.add(btn[3]).size(btn[3].getWidth()*scale[4], btn[3].getHeight()*scale[4]).left().padLeft(W/100f).bottom();
		stage.addActor(cLayout);
	}

	/*
	 * This method acts as the bird character table layout.
	 */
	public void drawCharacterLayout() {
		Skin s = Assets.cSkin;

		btnS[4] = new Button.ButtonStyle();
		if(!DataManagement.prefs.contains(DataManagement.characterStore.get(1))) {
			btnS[4].up = s.getDrawable("btnBird1");
		} else {
			btnS[4].up = s.getDrawable("btnPurchased1");
		}
		btn[4] = new Button(btnS[4]);	
		btn[4].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				//The if statement only runs if the user meets these requirements to purchase this bird.
				if(DataManagement.prefs.getInteger(DataManagement.getCoins()) >= 850 && //Users coins have to be more than or equal to 850
						!DataManagement.prefs.contains(DataManagement.characterStore.get(1)) && //The bird must not exist in the ArrayList.
						DataManagement.prefs.getInteger(DataManagement.getTwigScore()) == 25) { //TwigScore needs to equal 25 representing level 2 is unlocked.
					bLayout.reset(); 
					dManager.buyBird1(); //Saves the purchased bird in the Array.
					drawCharacterLayout(); //Updates the bird icon to say purchased.
					int coins = DataManagement.prefs.getInteger("chest"); //Retrieves the users coin amount.
					int update = coins - 850; //Sets the update variable with the newly value of their coins by taking away the bird cost.
					DataManagement.prefs.remove(DataManagement.getCoins()); //Removes the coins.
					DataManagement.prefs.putInteger(DataManagement.getCoins(), update); //Sets the coin value to the "update" variable.
					DataManagement.prefs.flush(); //Saves the data.
					status = "Purchased";
				} else if(!DataManagement.prefs.contains(DataManagement.characterStore.get(1))) {
					status = "Requirements not met";
				}
				else {
					status = "Denied";
				}
			}
		});

		btnS[5] = new Button.ButtonStyle();
		if(!DataManagement.prefs.contains(DataManagement.characterStore.get(2))) {
			btnS[5].up = s.getDrawable("btnBird2");
		} else {
			btnS[5].up = s.getDrawable("btnPurchased2");
		}
		btn[5] = new Button(btnS[5]);	
		btn[5].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				if(DataManagement.prefs.getInteger(DataManagement.getCoins()) >= 1650 &&
						!DataManagement.prefs.contains(DataManagement.characterStore.get(2)) && 
						DataManagement.prefs.getInteger(DataManagement.getTwigScore()) == 25) {
					bLayout.reset();
					dManager.buyBird2();
					drawCharacterLayout();
					int coins = DataManagement.prefs.getInteger("chest");
					System.out.println(coins);
					int update = coins - 1650;
					DataManagement.prefs.remove(DataManagement.getCoins());
					DataManagement.prefs.putInteger(DataManagement.getCoins(), update);
					DataManagement.prefs.flush();

					//Unlocks an achievement for purchasing this bird character.
					if (WOS.gServices.isSignedIn()) {
						WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQCg");
					}
					status = "Purchased";
				} else if(!DataManagement.prefs.contains(DataManagement.characterStore.get(2))) {
					status = "Requirements not met";
				}
				else {
					status = "Denied";
				}
			}
		});

		btnS[6] = new Button.ButtonStyle();
		if(!DataManagement.prefs.contains(DataManagement.characterStore.get(3)))
		{
			btnS[6].up = s.getDrawable("btnBird3");
		} else {
			btnS[6].up = s.getDrawable("btnPurchased3");
		}
		btn[6] = new Button(btnS[6]);	
		btn[6].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				if(DataManagement.prefs.getInteger(DataManagement.getCoins()) >= 500 &&
						!DataManagement.prefs.contains(DataManagement.characterStore.get(3))) {
					bLayout.reset();
					dManager.buyBird3();
					drawCharacterLayout();
					int coins = DataManagement.prefs.getInteger("chest");
					System.out.println(coins);
					int update = coins - 500;
					DataManagement.prefs.remove(DataManagement.getCoins());
					DataManagement.prefs.putInteger(DataManagement.getCoins(), update);
					DataManagement.prefs.flush();
					status = "Purchased";
				} else if(!DataManagement.prefs.contains(DataManagement.characterStore.get(3))) {
					status = "Requirements not met";
				}
				else {
					status = "Denied";
				}
			}
		});

		btnS[7] = new Button.ButtonStyle();
		btnS[7].up = Assets.uiSkin.getDrawable("back_button");
		btn[7] = new Button(btnS[7]);
		btn[7].addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btn[0].setVisible(true);
				btn[1].setVisible(true);
				btn[2].setVisible(true);
				btn[3].setVisible(true);
				bLayout.setVisible(false);
			}
		});

		//Scales all of the labels and buttons.
		bLbl[0] = new Label("Hawkeye", s);
		scale[0] = (W/10f) / bLbl[0].getWidth();
		scale[1] = (H/16f) / bLbl[0].getHeight();
		bLbl[0].setFontScale(scale[0], scale[1]);

		bLbl[1] = new Label("Unlock level 2"+"\n"+"\n"+ "850", s);
		scale[19] = (W/8f) / bLbl[1].getWidth();
		scale[20] = (H/10f) / bLbl[1].getHeight();
		bLbl[1].setFontScale(scale[19], scale[20]);

		bLbl[2] = new Label("Calypso", s);
		scale[17] = (W/10f) / bLbl[2].getWidth();
		scale[18] = (H/18f) / bLbl[2].getHeight();
		bLbl[2].setFontScale(scale[17], scale[18]);

		bLbl[3] = new Label("Unlock level 2"+"\n"+"\n"+ "1650", s);
		scale[19] = (W/8f) / bLbl[3].getWidth();
		scale[20] = (H/10f) / bLbl[3].getHeight();
		bLbl[3].setFontScale(scale[19], scale[20]);

		bLbl[4] = new Label("Paco", s);
		scale[19] = (W/14f) / bLbl[4].getWidth();
		scale[20] = (H/20f) / bLbl[4].getHeight();
		bLbl[4].setFontScale(scale[19], scale[20]);

		bLbl[5] = new Label("500", s);
		scale[19] = (W/24f) / bLbl[5].getWidth();
		scale[20] = (H/28f) / bLbl[5].getHeight();
		bLbl[5].setFontScale(scale[19], scale[20]);

		scale[2] = (W/9f) / btn[4].getWidth();
		scale[3] = (H/6f) / btn[4].getHeight();

		bLayout.add(btn[4]).size(btn[4].getWidth()*scale[2], btn[4].getHeight()*scale[3]).center();
		bLayout.add(bLbl[0]).size(W/1.5f, H/5.3f).left().top();
		bLayout.add(bLbl[1]).size(W/7f, H/5.3f).top();
		bLayout.row();
		bLayout.add(btn[5]).size(btn[5].getWidth()*scale[2], btn[5].getHeight()*scale[3]).center();
		bLayout.add(bLbl[2]).size(W/1.5f, H/5.3f).left().top();
		bLayout.add(bLbl[3]).size(W/7f, H/5.3f).top();
		bLayout.row();
		bLayout.add(btn[6]).size(btn[6].getWidth()*scale[2], btn[5].getHeight()*scale[3]).center();
		bLayout.add(bLbl[4]).size(W/1.5f, H/5.3f).left().top();
		bLayout.add(bLbl[5]).size(W/7f, H/5.3f).top();
		bLayout.row().expand();
		bLayout.add(btn[7]).size(btn[7].getWidth()*scale[4], btn[7].getHeight()*scale[4]).left().padLeft(W/100f).bottom().padBottom(H/160f);
		stage.addActor(bLayout);
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

		REPEAT_W2 = width / Assets.cageBG.getWidth()*2;

		cLayout.setSize(W/1f, H/1.1f);
		cLayout.setPosition((W/2f) - (cLayout.getWidth() / 2f), (H/2.2f) - (cLayout.getHeight() / 2.09f));

		bLayout.setSize(W/1f, H/1.38f);
		bLayout.setPosition((W/2f) - (bLayout.getWidth() / 2f), (H/2.78f) - (bLayout.getHeight() / 2.09f));
	}

	@Override
	public void hide() {
		cLayout.setVisible(false);
	}
	
	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void dispose(){}
}
