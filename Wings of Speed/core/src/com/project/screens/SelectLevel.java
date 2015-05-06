package com.project.screens;

/*
 * The SelectLevel implements the Screen interface inheriting its methods. This class's purpose 
 * is to check which level has been activated via the game state upon presses one of the level icons 
 * within the MenuScreen class and defines all of the custom level preferences below if the game state
 * equals CUSTOMLEVEL prevent this class calling upon the World and Rendering classes to start the gameplay.
 * 
 * Declared a wide range of String arrays for the custom level to contain lists of data for the user to select
 * upon within the select boxes.
 * 
 * The Boolean variables are used to activate certain statements to allow the user to change the colour once 
 * the user has selected an object before. 
 * 
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.game.WOS;
import com.project.screens.MenuScreen.GameState;
import com.project.universe.Rendering;
import com.project.universe.World;
import com.project.utilities.Assets;
import com.project.utilities.InputHandler;

import static com.project.utilities.Global.*;

public class SelectLevel implements Screen {
	private Table mLayout, lLayout, rLayout;
	private Button[] btn;
	private ButtonStyle[] btnS;
	private Label[] lbl;
	private SelectBox<String>[] list;
	private ScrollPane[] scroll;
	private Float[] scale;
	private String[] backgroundList={"  Select Background", "  Blue Sky", "  Stars"};
	private String[] obstacleList={"  Select Obstacle", "  Clouds", "  Bats"};
	private String[] enemyList={"  Select Enemy", "  Bird", "  Bat", "  Wasp"};
	private String[] creatureList={"  Select Creature", "  Dragonfly", "  Bee", "  Butterfly", "  Wasp"};
	private String[] victoryList={"  Select Collectable", "  Twig", "  Feather"};
	private String[] victoryNumber={"  Select Amount", "  12", "  18", "  22", "  24", "  26", "  28", "  30", "  32", "  34", "  36"};
	private String[] gameSpeed={"  Select Speed", "  2x", "  4x", "  6x", "  8x", "  12x"};
	private String[] colourList={""}, colourList2={""}, colourList3={""};
	private Boolean obstacleStatus = false, enemyStatus = false, creatureStatus = false, collectableStatus = false;
	private Sprite[] obstacleColour;
	private float runTime = 0;

	@SuppressWarnings("unchecked")
	public SelectLevel(WOS game) {
		Gdx.app.log("SelectLevel", "Loaded");

		//If the game state doesn't equal to CUSTOMLEVEL then load the World and Rendering classes.
		if(!MenuScreen.getStatusOfGame().equals(MenuScreen.GameState.CUSTOMLEVEL)) {
			world= new World(MIDPOINT_W, MIDPOINT_H); //Initialise world
			renderer = new Rendering(world, (int) MIDPOINT_W, MIDPOINT_H); //Initialise rendering
			Gdx.input.setInputProcessor(new InputHandler(world.getBird()));	//Sets the input to the InputHandler class targeting the bird object.
		} else {
			//If the game state equals CUSTOMLEVEL then it will load all of the operations below.
			batch = new SpriteBatch();
			mLayout = new Table(); 
			lLayout = new Table();
			rLayout = new Table();
			btn = new Button[5];
			btnS = new ButtonStyle[5];
			lbl = new Label[11];
			list = new SelectBox[10];
			scroll = new ScrollPane[10];
			scale = new Float[45];
			stage = new Stage();

			scale[43] = (W/160f) / f1.getSpaceWidth();
			scale[44] = (H/40f) / f1.getLineHeight();
			if(f1.getScaleX() == 1.0) {
				f1.setScale(scale[43], scale[44]);
			}

			drawMiddleLayout(); //Draws the middle section of the screen.
			drawLeftLayout(); //Draws the left section of the screen.
			drawRightLayout(); //Draws the right section of the screen.
			Gdx.input.setInputProcessor(stage); //Sets the user input to the stage to interact with the table layouts.
		}
	}

	@Override
	public void render(float delta) {
		if(!MenuScreen.getStatusOfGame().equals(MenuScreen.GameState.CUSTOMLEVEL))
		{
			runTime += delta;
			world.update(delta); //Updates the game world.
			Rendering.render(runTime); //Renders the game world.
		} else {
		//If the game state equals CUSTOMLEVEL then perform all of these operations below.
			Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			drawUI(); //Draws the custom level interface.
			batch.end();
			stage.act(delta);
			stage.draw();
			
			/*
			 * Below in these if statements define what the user can select upon 
			 * within the select boxes on the screen. For instance, once the user
			 * selects an obstacle the next if statement will open up to allow the
			 * user to choose a specific colour of that obstacle and so on.
			 */
			if(list[1].getSelected().equals("  Select Obstacle")) {
				colourList = new String[] {"  Colour unavailable"};
				list[2].setItems(colourList);
			} else if(list[1].getSelected().equals("  Clouds")) {
				colourList = new String[] {"  Select Colour", "  White", "  Dark Grey", "  Black"};
				list[2].setItems(colourList);
			} else if(list[1].getSelected().equals("  Bats")) {
				colourList = new String[] {"  Select Colour", "  Dark Red", "  Dark Grey"};
				list[2].setItems(colourList);
			}

			if(list[3].getSelected().equals("  Select Enemy")) {
				colourList2 = new String[] {"  Colour unavailable"};
				list[4].setItems(colourList2);
			} else if(list[3].getSelected().equals("  Bird")) {
				colourList2 = new String[] {"  Select Colour", "  Brown/White", "  Biege/Grey", "  Black/Grey", "  White/Black"};
				list[4].setItems(colourList2);
			} else if(list[3].getSelected().equals("  Bat")) {
				colourList2 = new String[] {"  Dark Grey"};
				list[4].setItems(colourList2);
			} else if(list[3].getSelected().equals("  Wasp")) {
				colourList2 = new String[] {"  Select Colour", "  Yellow", "  Orange"};
				list[4].setItems(colourList2);
			} 

			if(list[5].getSelected().equals("  Select Creature")) {
				colourList3 = new String[] {"  Colour unavailable"};
				list[6].setItems(colourList3);
			} else if(list[5].getSelected().equals("  Dragonfly")) {
				colourList3 = new String[] {"  Select Colour", "  Pink", "  Blue", "  Green", "  Yellow"};
				list[6].setItems(colourList3);
			} else if(list[5].getSelected().equals("  Bee")) {
				colourList3 = new String[] {"  Select Colour", "  Blue", "  Brown", "  Yellow"};
				list[6].setItems(colourList3);
			} else if(list[5].getSelected().equals("  Butterfly")) {
				colourList3 = new String[] {"  Select Colour", "  Blue", "  Orange", "  Purple", "  Red"};
				list[6].setItems(colourList3);
			}  else if(list[5].getSelected().equals("  Wasp")) {
				colourList3 = new String[] {"  Select Colour", "  Yellow", "  Orange"};
				list[6].setItems(colourList3);
			} 
		}
	}

	/*
	 * This method draws the middle layout onto the screen and retrieves all of the data selected inside the 
	 * select boxes then launches the gameplay.
	 */
	public void drawMiddleLayout() {
		s = Assets.clSkin;

		btnS[0] = new Button.ButtonStyle();
		btnS[0].up = s.getDrawable("btnLoad");
		btn[0] = new Button(btnS[0]);	
		btn[0].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y) {
				/*
				 * Within this massive if statement contains the key elements of loading the custom level game play. 
				 * Users only can activate the load button once the if statement meets the bracket requirements. If the statement
				 * doesn't equal to the defaulted select boxes information then proceed. 
				 * 
				 * The levelInput ArrayList will retrieve all of the information selected by calling getSelected() within each
				 * list that is linked up to each select box. Once the information is stored it will proceed onto the next statements. 
				 * 
				 * levelInput will check to see if the element 1 equals a certain obstacle to update the status variable so the CustomLevel
				 * class can run a method to render the correct obstacle and checks to see what colour the user has selected as well.
				 * 
				 * The levelInput ArrayList checks mostly all of the objects and colours to update the correct information relating to what
				 * the user has selected upon.
				 * 
				 * Once it reaches to the end of the statement it will change the game state from CUSTOMLEVEL to READYC which will 
				 * launch the CustomLevel, World and Rendering classes.
				 */
				if(!list[0].getSelected().equals("  Select Background") && !list[2].getSelected().equals("  Colour unavailable")
						&& !list[4].getSelected().equals("  Colour unavailable") && !list[6].getSelected().equals("  Colour unavailable")
						&& !list[7].getSelected().equals("  Select Collectable") && !list[8].getSelected().equals("  Select Amount")
						&& !list[9].getSelected().equals("  Select Speed")) {
					
					levelInput.add(0, list[0].getSelected());
					levelInput.add(1, list[1].getSelected());
					levelInput.add(2, list[2].getSelected());
					levelInput.add(3, list[3].getSelected());
					levelInput.add(4, list[4].getSelected());
					levelInput.add(5, list[5].getSelected());
					levelInput.add(6, list[6].getSelected());
					levelInput.add(7, list[7].getSelected());
					levelInput.add(8, list[8].getSelected());
					levelInput.add(9, list[9].getSelected());

					if(levelInput.get(1) == "  Clouds") {
						status = "C1";
					} else if(levelInput.get(1) == "  Bats") {
						status = "C2";
					}

					if(levelInput.get(2).equals("  Dark Grey")) {
						cloud = Assets.c_grey;
					} else if(levelInput.get(2).equals("  Black")) {
						cloud = Assets.c_black;
					} else {
						cloud = Assets._clouds;
					}

					if(levelInput.get(3).equals("   Bat")) {		
						en_W = (int) Assets.enemy[22].getWidth();
						en_H = (int) Assets.enemy[22].getHeight();
					} else if(levelInput.get(3).equals("   Wasp")) {		
						en_W = (int) Assets.enemy[16].getWidth();
						en_H = (int) Assets.enemy[16].getHeight();
					} else {
						en_W = (int) Assets.enemy[0].getWidth();
						en_H = (int) Assets.enemy[0].getHeight();
					}

					if(levelInput.get(5).equals("   Dragonfly")) {		
						crea_W = (int) Assets.crea[1].getWidth();
						crea_H = (int) Assets.crea[1].getHeight();
					} else if(levelInput.get(5).equals("   Bee")) {		
						crea_W = (int) Assets.crea[17].getWidth();
						crea_H = (int) Assets.crea[16].getHeight();
					} else if(levelInput.get(5).equals("   Butterfly")) {		
						crea_W = (int) Assets.crea[26].getWidth();
						crea_H = (int) Assets.crea[26].getHeight();
					} else {
						crea_W = (int) Assets.crea[42].getWidth();
						crea_H = (int) Assets.crea[42].getHeight();
					}

					//Unlocks an achievement once the user creates a custom level.
					if (WOS.gServices.isSignedIn()) {
						WOS.gServices.unlockAchievement("CgkIqv-lwqMdEAIQAw");
					}

					MenuScreen.statusOfGame = GameState.READY_C;
					new SelectLevel(game);		
				}
			}
		});

		btnS[1] = new Button.ButtonStyle();
		btnS[1].up = Assets.uiSkin.getDrawable("back_button");
		btn[1] = new Button(btnS[1]);
		btn[1].addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				MenuScreen menu = new MenuScreen(game);
				((Game)Gdx.app.getApplicationListener()).setScreen(menu);
				menu.hide();
				menu.selectLevelUI();
			}
		});

		lbl[0] = new Label("Custom Level", s);
		scale[0] = (W/3.5f) / lbl[0].getWidth();
		scale[1] = (H/12f) / lbl[0].getHeight();

		lbl[0].setFontScale(scale[0], scale[1]);

		scale[2] = (W/5f) / btn[0].getWidth();
		scale[3] = (H/10f) / btn[0].getHeight();
		scale[4] = (W/12f) / btn[1].getWidth();

		mLayout.add();
		mLayout.add(lbl[0]).size(lbl[0].getWidth()*scale[0], lbl[0].getHeight()*scale[1]).top().center().padRight(W/12f);
		mLayout.add();
		mLayout.row();
		mLayout.add(btn[1]).size(btn[1].getWidth()*scale[4], btn[1].getHeight()*scale[4]).left().padLeft(W/100f).bottom();
		mLayout.add(btn[0]).size(btn[0].getWidth()*scale[2], btn[0].getHeight()*scale[3]).expand().padTop(H/1.8f).center().padRight(W/12f);
		stage.addActor(mLayout);		
	}

	/*
	 * This method draws the left layout onto the screen displaying the select boxes for the user to 
	 * choose their level preferences.
	 */
	public void drawLeftLayout() {
		scale[7] = (W/180f) / s.getFont("medium").getSpaceWidth();
		scale[8] = (H/35f) / s.getFont("medium").getLineHeight();
		scale[9] = (W/140f) / s.getFont("small").getSpaceWidth();
		scale[10] = (H/26f) / s.getFont("small").getLineHeight();
		scale[21] = (W/220f) / s.getFont("ravie_blackF").getSpaceWidth();
		scale[22] = (H/40f) / s.getFont("ravie_blackF").getLineHeight();

		if(s.getFont("medium").getScaleX() == 1.0) {
			s.getFont("medium").setScale(scale[7], scale[8]);
			s.getFont("small").setScale(scale[9], scale[10]);
			s.getFont("ravie_blackF").setScale(scale[21], scale[22]);
		}

		list[0] = new SelectBox<String>(s);
		list[0].setItems(backgroundList);
		list[0].setMaxListCount(2);
		scroll[0] = new ScrollPane(list[0], s);		

		list[1] = new SelectBox<String>(s);
		list[1].setItems(obstacleList);
		list[1].setMaxListCount(2);
		scroll[1] = new ScrollPane(list[1], s);		

		list[2] = new SelectBox<String>(s, "default-small");
		list[2].setMaxListCount(2);
		scroll[2] = new ScrollPane(list[2], s);		
		list[2].addCaptureListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y)
			{
				obstacleStatus = true; //Activates the colour drop down box.
			}
		});

		list[3] = new SelectBox<String>(s);
		list[3].setItems(enemyList);
		list[3].setMaxListCount(2);
		scroll[3] = new ScrollPane(list[3], s);		

		list[4] = new SelectBox<String>(s, "default-small");
		list[4].setMaxListCount(2);
		scroll[4] = new ScrollPane(list[4], s);		
		list[4].addCaptureListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y)
			{
				enemyStatus = true; //Activates the colour drop down box.
			}
		});

		lbl[1] = new Label("1) Background:", s, "default-small");
		scale[13] = (W/12f) / lbl[1].getWidth();
		scale[14] = (H/33f) / lbl[1].getHeight();
		lbl[1].setFontScale(scale[13], scale[14]);

		lbl[2] = new Label("2) Obstacle:", s, "default-small");
		scale[15] = (W/12f) / lbl[2].getWidth();
		scale[16] = (H/34f) / lbl[2].getHeight();
		lbl[2].setFontScale(scale[15], scale[16]);

		lbl[3] = new Label("a) Colour:", s, "default-small");
		scale[17] = (W/18f) / lbl[3].getWidth();
		scale[18] = (H/40f) / lbl[3].getHeight();
		lbl[3].setFontScale(scale[17], scale[18]);

		lbl[4] = new Label("4) Enemy:", s, "default-small");
		lbl[4].setFontScale(scale[15], scale[16]);

		lbl[5] = new Label("a) Colour:", s, "default-small");
		lbl[5].setFontScale(scale[17], scale[18]);

		scale[5] = (W/3.0f) / list[0].getMinWidth();
		scale[6] = (H/11f) / list[0].getMinHeight();
		scale[23] = (W/3.0f) / list[1].getMinWidth();
		scale[24] = (H/11f) / list[1].getMinHeight();
		scale[25] = (W/3.0f) / list[3].getMinWidth();
		scale[26] = (H/11f) / list[3].getMinHeight();
		scale[11] = (W/4.0f) / list[2].getMinWidth();
		scale[12] = (H/15f) / list[2].getMinHeight();

		lLayout.add(lbl[1]).size(lbl[1].getWidth()*scale[13], lbl[1].getHeight()*scale[14]).left().padLeft(-W/42f);
		lLayout.row();
		lLayout.add(list[0]).size(list[0].getMinWidth()*scale[5], list[0].getMinHeight()*scale[6]).left().padLeft(-W/34f);
		lLayout.row().expand();
		lLayout.add(lbl[2]).size(lbl[2].getWidth()*scale[15], lbl[2].getHeight()*scale[16]).left().padLeft(-W/42f).padTop(H/55);
		lLayout.row().expand();
		lLayout.add(list[1]).size(list[1].getMinWidth()*scale[23], list[1].getMinHeight()*scale[24]).left().padLeft(-W/34f).padTop(-H/12f);
		lLayout.row().expand();
		lLayout.add(lbl[3]).size(lbl[3].getWidth()*scale[17], lbl[3].getHeight()*scale[18]).left().padTop(-H/7.3f);
		lLayout.row().expand();
		lLayout.add(list[2]).size(list[2].getMinWidth()*scale[11], list[2].getMinHeight()*scale[12]).left().padTop(-H/5.1f);
		lLayout.row().expand();
		lLayout.add(lbl[4]).size(lbl[4].getWidth()*scale[15], lbl[4].getHeight()*scale[16]).left().padLeft(-W/42f).top().padTop(-H/25);
		lLayout.row().expand();
		lLayout.add(list[3]).size(list[3].getMinWidth()*scale[25], list[3].getMinHeight()*scale[26]).left().padLeft(-W/34f).top().padTop(-H/10.5f);
		lLayout.row().expand();
		lLayout.add(lbl[5]).size(lbl[5].getWidth()*scale[17], lbl[5].getHeight()*scale[18]).left().padTop(-H/4.5f);
		lLayout.row().expand();
		lLayout.add(list[4]).size(list[4].getMinWidth()*scale[11], list[4].getMinHeight()*scale[12]).left().padTop(-H/3.5f);
		stage.addActor(lLayout);
	}

	/*
	 * This method draws the right layout onto the screen displaying the select boxes for the user to 
	 * choose their level preferences.
	 */
	public void drawRightLayout() {

		list[5] = new SelectBox<String>(s);
		list[5].setItems(creatureList);
		list[5].setMaxListCount(2);
		scroll[5] = new ScrollPane(list[5], s);		

		list[6] = new SelectBox<String>(s, "default-small");
		list[6].setMaxListCount(2);
		scroll[6] = new ScrollPane(list[6], s);		
		list[6].addCaptureListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y)
			{
				creatureStatus = true; //Activates the colour drop down box.
			}
		});

		list[7] = new SelectBox<String>(s);
		list[7].setItems(victoryList);
		list[7].setMaxListCount(2);
		scroll[7] = new ScrollPane(list[7], s);		
		list[7].addCaptureListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y)
			{
				collectableStatus = true; //Activates the colour drop down box.
			}
		});

		list[8] = new SelectBox<String>(s);
		list[8].setItems(victoryNumber);
		list[8].setMaxListCount(2);
		scroll[8] = new ScrollPane(list[8], s);		

		list[9] = new SelectBox<String>(s);
		list[9].setItems(gameSpeed);
		list[9].setMaxListCount(2);
		scroll[9] = new ScrollPane(list[9], s);		

		lbl[6] = new Label("4) Creature (Power-ups / Set-backs):", s, "default-small");
		lbl[6].setFontScale(scale[15], scale[16]);

		lbl[7] = new Label("a) Colour:", s, "default-small");
		lbl[7].setFontScale(scale[17], scale[18]);

		lbl[8] = new Label("5) Collectable (Winning condition):", s, "default-small");
		lbl[8].setFontScale(scale[15], scale[16]);

		lbl[9] = new Label("a) Collectables Required:", s, "default-small");
		lbl[9].setFontScale(scale[17], scale[18]);

		lbl[10] = new Label("6) Game Speed:", s, "default-small");
		lbl[10].setFontScale(scale[15], scale[16]);

		scale[27] = (W/3.0f) / list[5].getMinWidth();
		scale[28] = (H/11f) / list[5].getMinHeight();
		scale[29] = (W/3.0f) / list[7].getMinWidth();
		scale[30] = (H/11f) / list[7].getMinHeight();
		scale[31] = (W/3.0f) / list[9].getMinWidth();
		scale[32] = (H/11f) / list[9].getMinHeight();
		scale[33] = (W/4.0f) / list[8].getMinWidth();
		scale[34] = (H/15f) / list[8].getMinHeight();

		rLayout.add(lbl[6]).size(lbl[6].getWidth()*scale[13], lbl[6].getHeight()*scale[14]).left().padLeft(W/48f);
		rLayout.row();
		rLayout.add(list[5]).size(list[5].getMinWidth()*scale[27], list[5].getMinHeight()*scale[28]).padRight(-W/32f);
		rLayout.row().expand();
		rLayout.add(lbl[7]).size(lbl[7].getWidth()*scale[13], lbl[7].getHeight()*scale[14]).top().left().padLeft(W/23f);
		rLayout.row().expand();
		rLayout.add(list[6]).size(list[6].getMinWidth()*scale[11], list[6].getMinHeight()*scale[12]).padRight(-W/32f).top().padTop(-H/12.8f);
		rLayout.row().expand();
		rLayout.add(lbl[8]).size(lbl[8].getWidth()*scale[13], lbl[8].getHeight()*scale[14]).padTop(-H/7.3f).left().padLeft(W/48f);
		rLayout.row().expand();
		rLayout.add(list[7]).size(list[7].getMinWidth()*scale[29], list[7].getMinHeight()*scale[30]).padTop(-H/5.9f).padRight(-W/32f);
		rLayout.row().expand();
		rLayout.add(lbl[9]).size(lbl[9].getWidth()*scale[13], lbl[9].getHeight()*scale[14]).top().left().padLeft(W/23f).padTop(-H/13.5f);
		rLayout.row().expand();
		rLayout.add(list[8]).size(list[8].getMinWidth()*scale[33], list[8].getMinHeight()*scale[34]).padRight(-W/32f).top().padTop(-H/7.9f);
		rLayout.row().expand();
		rLayout.add(lbl[10]).size(lbl[10].getWidth()*scale[13], lbl[10].getHeight()*scale[14]).padTop(-H/4.1f).left().padLeft(W/48f);
		rLayout.row().expand();
		rLayout.add(list[9]).size(list[9].getMinWidth()*scale[31], list[9].getMinHeight()*scale[32]).padTop(-H/3.5f).padRight(-W/32f);
		rLayout.row().expand();
		stage.addActor(rLayout);	
	}

	/*
	 * This method renders the GUI and displaying the preview content onto the screen depending on what the
	 * user has selected from the select boxes.
	 */
	public void drawUI() {
		batch.draw(Assets.bgCustom, 0, (H/2)/(Assets.bgCustom.getHeight()/2), Assets.bgCustom.getWidth() * REPEAT_W, 
				H, 0, REPEAT_H, REPEAT_W, 0);	

		scale[19] = (W/3.5f) / Assets.preview.getWidth();
		scale[20] = (H/3.0f) / Assets.preview.getHeight();
		scale[31] = (W/1.0f) / Assets.bottomBG.getWidth();
		scale[32] = (H/6.5f) / Assets.bottomBG.getHeight();

		batch.draw(Assets.preview, W/2.78f, H/2.6f, Assets.preview.getWidth()*scale[19], Assets.preview.getHeight()*scale[20]);
		batch.draw(Assets.bottomBG, 0, -H/142f, Assets.bottomBG.getWidth()*scale[31], Assets.bottomBG.getHeight()*scale[32]);
		f1.draw(batch, "Preview", (f1.getSpaceWidth()/2) + (W/2.17f), (f1.getLineHeight()/2) + (H/1.38f));

		/*
		 * All of the if statements below will render the preview content depending on what the user
		 * has selected. For instance, if the user selects the blue sky as a background then the middle
		 * box of the GUI will render this image onto it. If the user selects a dragonfly as a creature then
		 * the GUI will render this sprite onto the preview area and so on.
		 */
		if(list[0].getSelected().equals("  Blue Sky")) { 
			scale[35] = (W/3.78f) / Assets.b_BG.getWidth();
			scale[36] = (H/3.38f) / Assets.b_BG.getHeight();

			batch.draw(Assets.b_BG, W/2.7f, H/2.48f, Assets.b_BG.getWidth()*scale[35], Assets.b_BG.getHeight()*scale[36]);
		} else if(list[0].getSelected().equals("  Stars")) {
			scale[35] = (W/3.735f) / Assets.s_BG.getWidth();
			scale[36] = (H/3.3f) / Assets.s_BG.getHeight();

			batch.draw(Assets.s_BG, W/2.71f, H/2.498f, Assets.s_BG.getWidth()*scale[35], Assets.s_BG.getHeight()*scale[36]);
		}

		if(obstacleStatus == true) {
			if(list[1].getSelected().equals("  Clouds")) {
				if(list[2].getSelected().equals("  Dark Grey")) {
					obstacleColour = Assets.c_grey;
				} else if(list[2].getSelected().equals("  Black")) {
					obstacleColour = Assets.c_black;

				} else if(list[2].getSelected().equals("  White")) {
					obstacleColour = Assets._clouds;
				}

				if(obstacleColour != null) {
					scale[37] = (W/22f) / obstacleColour[1].getWidth();
					scale[38] = (H/22f) / obstacleColour[1].getHeight();
					batch.draw(obstacleColour[1], W/2.71f, H/2.2f, obstacleColour[1].getWidth()*scale[37], obstacleColour[1].getHeight()*scale[38]);
					batch.draw(obstacleColour[2], W/2.0f, H/1.8f, obstacleColour[2].getWidth()*scale[37], obstacleColour[2].getHeight()*scale[38]);
					batch.draw(obstacleColour[3], W/1.7f, H/2.4f, obstacleColour[3].getWidth()*scale[37], obstacleColour[3].getHeight()*scale[38]);
					batch.draw(obstacleColour[4], W/2.6f, H/1.6f, obstacleColour[4].getWidth()*scale[37], obstacleColour[4].getHeight()*scale[38]);
					batch.draw(obstacleColour[1], W/2.3f, H/1.85f, obstacleColour[1].getWidth()*scale[37], obstacleColour[1].getHeight()*scale[38]);
					batch.draw(obstacleColour[3], W/1.95f, H/1.55f, obstacleColour[3].getWidth()*scale[37], obstacleColour[3].getHeight()*scale[38]);
					batch.draw(obstacleColour[4], W/2.1f, H/2.4f, obstacleColour[4].getWidth()*scale[37], obstacleColour[4].getHeight()*scale[38]);
					batch.draw(obstacleColour[2], W/1.82f, H/1.98f, obstacleColour[2].getWidth()*scale[37], obstacleColour[2].getHeight()*scale[38]);
				}
			} else if(list[1].getSelected().equals("  Bats")) {
				Sprite bat = null;
				obstacleColour = Assets.en2;
				if(list[2].getSelected().equals("  Dark Grey")) {
					bat = obstacleColour[2];
				} else if(list[2].getSelected().equals("  Dark Red")) {
					bat = obstacleColour[4];
				} 

				if(bat != null) {
					scale[37] = (W/28f) / bat.getWidth();
					scale[38] = (H/18f) / bat.getHeight();
					batch.draw(bat, W/2.71f, H/2.2f, bat.getWidth()*scale[37], bat.getHeight()*scale[38]);
					batch.draw(bat, W/2.0f, H/1.8f, bat.getWidth()*scale[37], obstacleColour[2].getHeight()*scale[38]);
					batch.draw(bat, W/1.7f, H/2.4f, bat.getWidth()*scale[37], obstacleColour[3].getHeight()*scale[38]);
					batch.draw(bat, W/2.6f, H/1.6f, bat.getWidth()*scale[37], obstacleColour[4].getHeight()*scale[38]);
					batch.draw(bat, W/2.3f, H/1.85f, bat.getWidth()*scale[37], bat.getHeight()*scale[38]);
					batch.draw(bat, W/1.95f, H/1.55f, bat.getWidth()*scale[37], bat.getHeight()*scale[38]);
					batch.draw(bat, W/2.1f, H/2.4f, bat.getWidth()*scale[37], bat.getHeight()*scale[38]);
					batch.draw(bat, W/1.82f, H/1.98f, bat.getWidth()*scale[37], bat.getHeight()*scale[38]);
				}
			}
		}

		if(enemyStatus == true) {
			Sprite enemyColour = null;
			if(list[3].getSelected().equals("  Bird")) {
				if(list[4].getSelected().equals("  Brown/White")) {
					enemyColour = Assets.enemy[2];
				} else if(list[4].getSelected().equals("  Biege/Grey")) {
					enemyColour = Assets.enemy[5];
				} else if(list[4].getSelected().equals("  Black/Grey")) {
					enemyColour = Assets.enemy[9];
				} else if(list[4].getSelected().equals("  White/Black")) {
					enemyColour = Assets.enemy[13];
				}
			} 
			else if(list[3].getSelected().equals("  Bat")) {
				enemyColour = Assets.enemy[23];
			} 
			else if(list[3].getSelected().equals("  Wasp")) {
				if(list[4].getSelected().equals("  Yellow")) {
					enemyColour = Assets.enemy[20];
				} else if(list[4].getSelected().equals("  Orange")) {
					enemyColour = Assets.enemy[17];
				}
			}
			if(enemyColour != null) {
				scale[37] = (W/28f) / enemyColour.getWidth();
				scale[38] = (H/18f) / enemyColour.getHeight();
				batch.draw(enemyColour, W/2.4f, H/2.0f, enemyColour.getWidth()*scale[37], enemyColour.getHeight()*scale[38]);
			}
		}

		if(creatureStatus == true) {
			Sprite creatureColour = null;
			if(list[5].getSelected().equals("  Dragonfly")) {
				if(list[6].getSelected().equals("  Pink")) {
					creatureColour = Assets.crea[2];
				} else if(list[6].getSelected().equals("  Blue")) {
					creatureColour = Assets.crea[6];
				} else if(list[6].getSelected().equals("  Green")) {
					creatureColour = Assets.crea[10];
				} else if(list[6].getSelected().equals("  Yellow")) {
					creatureColour = Assets.crea[14];
				}
			} 
			else if(list[5].getSelected().equals("  Bee")) {
				if(list[6].getSelected().equals("  Blue")) {
					creatureColour = Assets.crea[18];
				} else if(list[6].getSelected().equals("  Brown")) {
					creatureColour = Assets.crea[21];
				} else if(list[6].getSelected().equals("  Yellow")) {
					creatureColour = Assets.crea[24];
				} 
			}
			else if(list[5].getSelected().equals("  Butterfly")) {
				if(list[6].getSelected().equals("  Blue")) {
					creatureColour = Assets.crea[27];
				} else if(list[6].getSelected().equals("  Orange")) {
					creatureColour = Assets.crea[31];
				} else if(list[6].getSelected().equals("  Purple")) {
					creatureColour = Assets.crea[35];
				} else if(list[6].getSelected().equals("  Red")) {
					creatureColour = Assets.crea[39];
				}
			}
			else if(list[5].getSelected().equals("  Wasp")) {
				if(list[6].getSelected().equals("  Orange")) {
					creatureColour = Assets.crea[43];
				} else if(list[6].getSelected().equals("  Yellow")) {
					creatureColour = Assets.crea[46];
				} 
			}	
			if(creatureColour != null) {
				scale[39] = (W/32f) / creatureColour.getWidth();
				scale[40] = (H/22f) / creatureColour.getHeight();
				batch.draw(creatureColour, W/1.7f, H/1.6f, creatureColour.getWidth()*scale[39], creatureColour.getHeight()*scale[40]);
			}
		}

		if(collectableStatus == true) {
			Texture collectable = null;
			if(list[7].getSelected().equals("  Twig")) {
				collectable = Assets.twig;
			} else if(list[7].getSelected().equals("  Feather"))  {
				collectable = Assets.feather;
			}
			if(collectable != null) {
				scale[41] = (W/34f) / collectable.getWidth();
				scale[42] = (H/30f) / collectable.getHeight();
				batch.draw(collectable, W/1.9f, H/2.1f, collectable.getWidth()*scale[41], collectable.getHeight()*scale[42]);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		if(Gdx.graphics.getWidth() < 799){
			REPEAT_W = width / Assets.bgCustom.getWidth() *2 ;
		} else {
			REPEAT_W = width / Assets.bgCustom.getWidth()+1;
		}
		REPEAT_H = height / Assets.bgCustom.getHeight() + 1;  

		if(MenuScreen.getStatusOfGame().equals(MenuScreen.GameState.CUSTOMLEVEL))
		{
			mLayout.setSize(W/1f, H/1.07f);
			mLayout.setPosition((W/2f) - (mLayout.getWidth() / 2f), (H/2.2f) - (mLayout.getHeight() / 2.17f));

			lLayout.setSize(W/3f, H/1.2f);
			lLayout.setPosition((W/5f) - (lLayout.getWidth() / 2f), (H/2.2f) - (lLayout.getHeight() / 2f));

			rLayout.setSize(W/3f, H/1.2f);
			rLayout.setPosition((W/1.23f) - (rLayout.getWidth() / 2f), (H/2.2f) - (rLayout.getHeight() / 2f));
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}

	@Override
	public void show() {}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
