package com.project.utilities;

/*
 * Assets class retrieves the loaded assets from AssetsManager for the classes
 * to call upon the declared variables of Textures, TextureRegions, Sprites, Animations,
 * Skins and Sounds.
 */

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.project.utilities.Global.*;

public class Assets {

	public static Texture animTexture, bgStars, bgMainSky, twig, clearBG, levelUI, feather, sp, bird1, bird2, bird3, bird4, bird5, aBtn, tEffect,
	birdPeak, gBird_L, cageBG, activeBG, bgCustom, preview, bottomBG, howtPly, howtPly1, howtPly2, message, locked, shield;
	public static TextureRegion a1, a2, a3, a4, a5, a6, a7, a8, a9;
	public static TextureRegion[] birdSS, oBird, bBird, yBird, gBird, moss, powers1, en_brown, en_beige, en_black, en_white, en_bat, enWasp_yellow,
	enWasp_orange, bee, bats_grey, bats_red, dFly_pink, dFly_blue, dFly_green, dFly_yellow, bee_blue, bee_brown, bee_yellow, bFly_blue, bFly_orange,
	bFly_purple, bFly_red, wasp_orange, wasp_yellow;
	public static Sprite[] _clouds, c_grey, c_black, en2, birds, enemy, crea; 
	public static Sprite bird, coin, pUps1a, pUps2a, pUps3a, pUps4a, treeBG, charLogo, oDeath, bDeath, yDeath, gDeath, pUI1, pUI2, pUI3, b_BG, s_BG;
	public static Animation aniSS, aniOB, aniBB, aniYB, aniGB, aniMoss, aniPowers1, aniEnBrown, aniEnBeige, aniEnBlack, aniEnWhite, aniBats, aniEnBat, 
	aniEnWasp_yellow, aniEnWasp_orange, aniBee;
	public static Animation[] aniCrea;
	public static TextureAtlas getClouds, getBird, getCreatures, getCollects, getUps, getEnemy2, getCharUI, splashScreen, getDeath, 
	getUI, getEnemies, getPreview;
	public static Skin uiSkin, rSkin, cSkin, clSkin;
	public static Sound tweet, collect, death1, death2, pu1, pu2, collectable;

	/*
	 * This method only works when the SplashScreen runs to display the loading 
	 * screen image.
	 */
	public static void splashScreen_Assets() {
		sp = new Texture("img/ui/splashscreen.png");
	}

	/*
	 * This method is only called once within the SplashScreen class of render() method
	 * to load all of the retrieved assets from AssetsManager.
	 */
	public static void load() {		
		_clouds = new Sprite[5];
		c_grey = new Sprite[5];
		c_black = new Sprite[5];
		getClouds = AssetsManager.assetM.get("img/obstacles/cloudsUI.atlas", TextureAtlas.class);
		_clouds[0] = getClouds.createSprite("cloudsSmall_white");
		_clouds[1] = getClouds.createSprite("cloudsMedium_white");
		_clouds[2] = getClouds.createSprite("cloudsMedium02_white");
		_clouds[3] = getClouds.createSprite("cloudsMedium03_white");
		_clouds[4] = getClouds.createSprite("cloudsLarge_white");
		c_grey[0] = getClouds.createSprite("cloudsSmall_grey");
		c_grey[1] = getClouds.createSprite("cloudsMedium_grey");
		c_grey[2] = getClouds.createSprite("cloudsMedium02_grey");
		c_grey[3] = getClouds.createSprite("cloudsMedium03_grey");
		c_grey[4] = getClouds.createSprite("cloudsLarge_grey");
		c_black[0] = getClouds.createSprite("cloudsSmall_black");
		c_black[1] = getClouds.createSprite("cloudsMedium_black");
		c_black[2] = getClouds.createSprite("cloudsMedium02_black");
		c_black[3] = getClouds.createSprite("cloudsMedium03_black");
		c_black[4] = getClouds.createSprite("cloudsLarge_black");

		bgMainSky = AssetsManager.assetM.get("img/ui/bgSky.png", Texture.class);
		bgMainSky.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		birds = new Sprite[19];
		getBird = AssetsManager.assetM.get("img/birds/birdsUI_2.atlas", TextureAtlas.class);
		birds[0] = getBird.createSprite("oBird1");
		birds[1] = getBird.createSprite("oBird2");
		birds[2] = getBird.createSprite("oBird3");
		birds[3] = getBird.createSprite("oBird4");
		birds[4] = getBird.createSprite("oBird5");
		birds[5] = getBird.createSprite("oBird6");
		birds[6] = getBird.createSprite("oBird7");
		birds[7] = getBird.createSprite("bBird1");
		birds[8] = getBird.createSprite("bBird2");
		birds[9] = getBird.createSprite("bBird3");
		birds[10] = getBird.createSprite("bBird4");
		birds[11] = getBird.createSprite("yBird1");
		birds[12] = getBird.createSprite("yBird2");
		birds[13] = getBird.createSprite("yBird3");
		birds[14] = getBird.createSprite("yBird4");
		birds[15] = getBird.createSprite("gBird1");
		birds[16] = getBird.createSprite("gBird2");
		birds[17] = getBird.createSprite("gBird3");
		birds[18] = getBird.createSprite("gBird4");
		birdAnimations();

		bird1 = AssetsManager.assetM.get("img/birds/oBird3.png", Texture.class);
		bird1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bird2 = AssetsManager.assetM.get("img/birds/owl.png", Texture.class);
		bird2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bird3 = AssetsManager.assetM.get("img/birds/yBird.png", Texture.class);
		bird3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bird4 = AssetsManager.assetM.get("img/birds/bBird.png", Texture.class);
		bird4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bird5 = AssetsManager.assetM.get("img/birds/gBird.png", Texture.class);
		bird5.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		gBird_L = AssetsManager.assetM.get("img/birds/gBird_L.png", Texture.class);
		gBird_L.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		twig = AssetsManager.assetM.get("img/collectables/twig.png", Texture.class);
		feather = AssetsManager.assetM.get("img/collectables/feather.png", Texture.class);
		feather.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		clearBG = AssetsManager.assetM.get("img/ui/clearBG.png", Texture.class);
		clearBG.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		uiSkin = AssetsManager.assetM.get("skins/uiMenu.json");
		rSkin = AssetsManager.assetM.get("skins/uiRetry.json");
		cSkin = AssetsManager.assetM.get("skins/uiCage.json");
		clSkin = AssetsManager.assetM.get("skins/uiCustom.json");

		crea = new Sprite[48];
		getCreatures = AssetsManager.assetM.get("img/creatures/creaturesUI.atlas", TextureAtlas.class);
		crea[0] = getCreatures.createSprite("worm");
		crea[1] = getCreatures.createSprite("moss_pink1");
		crea[2] = getCreatures.createSprite("moss_pink2");
		crea[3] = getCreatures.createSprite("moss_pink3");
		crea[4] = getCreatures.createSprite("moss_pink4");
		crea[5] = getCreatures.createSprite("moss_blue1");
		crea[6] = getCreatures.createSprite("moss_blue2");
		crea[7] = getCreatures.createSprite("moss_blue3");
		crea[8] = getCreatures.createSprite("moss_blue4");
		crea[9] = getCreatures.createSprite("moss_green1");
		crea[10] = getCreatures.createSprite("moss_green2");
		crea[11] = getCreatures.createSprite("moss_green3");
		crea[12] = getCreatures.createSprite("moss_green4");
		crea[13] = getCreatures.createSprite("moss_yellow1");
		crea[14] = getCreatures.createSprite("moss_yellow2");
		crea[15] = getCreatures.createSprite("moss_yellow3");
		crea[16] = getCreatures.createSprite("moss_yellow4");
		crea[17] = getCreatures.createSprite("bee_blue1");
		crea[18] = getCreatures.createSprite("bee_blue2");
		crea[19] = getCreatures.createSprite("bee_blue3");
		crea[20] = getCreatures.createSprite("bee_brown1");
		crea[21] = getCreatures.createSprite("bee_brown2");
		crea[22] = getCreatures.createSprite("bee_brown3");
		crea[23] = getCreatures.createSprite("bee_yellow1");
		crea[24] = getCreatures.createSprite("bee_yellow2");
		crea[25] = getCreatures.createSprite("bee_yellow3");
		crea[26] = getCreatures.createSprite("fly_blue1");
		crea[27] = getCreatures.createSprite("fly_blue2");
		crea[28] = getCreatures.createSprite("fly_blue3");
		crea[29] = getCreatures.createSprite("fly_blue4");
		crea[30] = getCreatures.createSprite("fly_orange1");
		crea[31] = getCreatures.createSprite("fly_orange2");
		crea[32] = getCreatures.createSprite("fly_orange3");
		crea[33] = getCreatures.createSprite("fly_orange4");
		crea[34] = getCreatures.createSprite("fly_purple1");
		crea[35] = getCreatures.createSprite("fly_purple2");
		crea[36] = getCreatures.createSprite("fly_purple3");
		crea[37] = getCreatures.createSprite("fly_purple4");
		crea[38] = getCreatures.createSprite("fly_red1");
		crea[39] = getCreatures.createSprite("fly_red2");
		crea[40] = getCreatures.createSprite("fly_red3");
		crea[41] = getCreatures.createSprite("fly_red4");
		crea[42] = getCreatures.createSprite("wasp_orange1");
		crea[43] = getCreatures.createSprite("wasp_orange2");
		crea[44] = getCreatures.createSprite("wasp_orange3");
		crea[45] = getCreatures.createSprite("wasp_yellow1");
		crea[46] = getCreatures.createSprite("wasp_yellow2");
		crea[47] = getCreatures.createSprite("wasp_yellow3");
		creatureAnimations();

		getCollects = AssetsManager.assetM.get("img/collectables/collectablesUI.atlas", TextureAtlas.class);
		coin = getCollects.createSprite("coin");

		enemy = new Sprite[25];
		getEnemies = AssetsManager.assetM.get("img/enemies/enemyUI.atlas", TextureAtlas.class);
		enemy[0] = getEnemies.createSprite("bird_brown1");
		enemy[1] = getEnemies.createSprite("bird_brown2");
		enemy[2] = getEnemies.createSprite("bird_brown3");
		enemy[3] = getEnemies.createSprite("bird_brown4");
		enemy[4] = getEnemies.createSprite("bird_beige1");
		enemy[5] = getEnemies.createSprite("bird_beige2");
		enemy[6] = getEnemies.createSprite("bird_beige3");
		enemy[7] = getEnemies.createSprite("bird_beige4");
		enemy[8] = getEnemies.createSprite("bird_black1");
		enemy[9] = getEnemies.createSprite("bird_black2");
		enemy[10] = getEnemies.createSprite("bird_black3");
		enemy[11] = getEnemies.createSprite("bird_black4");
		enemy[12] = getEnemies.createSprite("bird_white1");
		enemy[13] = getEnemies.createSprite("bird_white2");
		enemy[14] = getEnemies.createSprite("bird_white3");
		enemy[15] = getEnemies.createSprite("bird_white4");
		enemy[16] = getEnemies.createSprite("wasp_orange1");
		enemy[17] = getEnemies.createSprite("wasp_orange2");
		enemy[18] = getEnemies.createSprite("wasp_orange3");
		enemy[19] = getEnemies.createSprite("wasp_yellow1");
		enemy[20] = getEnemies.createSprite("wasp_yellow2");
		enemy[21] = getEnemies.createSprite("wasp_yellow3");
		enemy[22] = getEnemies.createSprite("bat1");
		enemy[23] = getEnemies.createSprite("bat2");
		enemy[24] = getEnemies.createSprite("bat3");
		enemyAnimations();

		en2 = new Sprite[6];
		getEnemy2 = AssetsManager.assetM.get("img/obstacles/batsUI.atlas", TextureAtlas.class);
		en2[0] = getEnemy2.createSprite("bat_black1");
		en2[1] = getEnemy2.createSprite("bat_black2");
		en2[2] = getEnemy2.createSprite("bat_black3");
		en2[3] = getEnemy2.createSprite("bat_red1");
		en2[4] = getEnemy2.createSprite("bat_red2");
		en2[5] = getEnemy2.createSprite("bat_red3");
		batAnimations();

		getDeath = AssetsManager.assetM.get("img/birds/death/deathUI.atlas", TextureAtlas.class);
		oDeath = getDeath.createSprite("oBird");
		bDeath = getDeath.createSprite("bBird");
		yDeath = getDeath.createSprite("yBird");
		gDeath = getDeath.createSprite("gBird");

		getCharUI = AssetsManager.assetM.get("img/ui/charUI.atlas", TextureAtlas.class);
		treeBG = getCharUI.createSprite("treeBG");
		charLogo = getCharUI.createSprite("selectChar_logo");

		bgStars = AssetsManager.assetM.get("img/ui/stars.png", Texture.class);
		bgStars.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

		aBtn = AssetsManager.assetM.get("img/ui/activeBtn.png", Texture.class);
		aBtn.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		tEffect = AssetsManager.assetM.get("img/ui/titleEffect.png", Texture.class);
		tEffect.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		birdPeak = AssetsManager.assetM.get("img/ui/birdPeak.png", Texture.class);
		birdPeak.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		cageBG = AssetsManager.assetM.get("img/ui/cageBG.png", Texture.class);
		cageBG.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		activeBG = AssetsManager.assetM.get("img/ui/activeBG.png", Texture.class);
		activeBG.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bgCustom = AssetsManager.assetM.get("img/ui/customBG.png", Texture.class);
		bgCustom.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		preview = AssetsManager.assetM.get("img/ui/preview.png", Texture.class);
		preview.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		bottomBG = AssetsManager.assetM.get("img/ui/bottomBG.png", Texture.class);
		bottomBG.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		howtPly = AssetsManager.assetM.get("img/ui/howToPlay.png", Texture.class);
		howtPly.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		howtPly1 = AssetsManager.assetM.get("img/ui/howToPlay_lvl1.png", Texture.class);
		howtPly1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		howtPly2 = AssetsManager.assetM.get("img/ui/howToPlay_lvl2.png", Texture.class);
		howtPly2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		message = AssetsManager.assetM.get("img/ui/toastMessage.png", Texture.class);
		message.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		locked = AssetsManager.assetM.get("img/ui/locked.png", Texture.class);
		locked.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shield = AssetsManager.assetM.get("img/ui/shield.png", Texture.class);
		shield.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		getUI = AssetsManager.assetM.get("img/ui/purchaseUI.atlas", TextureAtlas.class);
		pUI1 = getUI.createSprite("btnPurchased1");
		pUI2 = getUI.createSprite("btnPurchased2");
		pUI3 = getUI.createSprite("btnPurchased3");

		getPreview = AssetsManager.assetM.get("img/ui/previewUI.atlas", TextureAtlas.class);
		b_BG = getPreview.createSprite("blueSky_BG");
		s_BG = getPreview.createSprite("stars_BG");

		//Bitmap Font assets.
		f1 = AssetsManager.assetM.get("fonts/ravie_black1.fnt", BitmapFont.class);
		f2 = AssetsManager.assetM.get("fonts/small.fnt", BitmapFont.class);
		f3 = AssetsManager.assetM.get("fonts/ravie_white.fnt", BitmapFont.class);
		f4 = AssetsManager.assetM.get("fonts/ravie_black.fnt", BitmapFont.class);

		//Sound assets.
		tweet = AssetsManager.assetM.get("sounds/tweet.wav", Sound.class);
		collect = AssetsManager.assetM.get("sounds/coin.wav", Sound.class);
		death1 = AssetsManager.assetM.get("sounds/death1.wav", Sound.class);
		death2 = AssetsManager.assetM.get("sounds/death2.wav", Sound.class);
		collectable = AssetsManager.assetM.get("sounds/collectable.wav", Sound.class);
		pu1 = AssetsManager.assetM.get("sounds/badPU.wav", Sound.class);
		pu2 = AssetsManager.assetM.get("sounds/goodPU.wav", Sound.class);
	}

	/*
	 * The method is used for bird animations declaring the TextureRegion part 
	 * to prevent longer loading times when starting the gameplay levels up.
	 */
	public static void birdAnimations() {
		oBird = new TextureRegion[] {birds[0], birds[1], birds[2], birds[3], birds[4], birds[5], birds[6]};		
		bBird = new TextureRegion[] {birds[7], birds[8],  birds[9], birds[10]};
		yBird = new TextureRegion[] {birds[11], birds[12], birds[13], birds[14]};
		gBird = new TextureRegion[] {birds[15], birds[16], birds[17], birds[18]};
	}

	/*
	 * The method is used for dragonfly animations.
	 */
	public static void dragonflyAnimations() {
		moss = new TextureRegion[] {crea[1], crea[2], crea[3], crea[4]};
		aniMoss = new Animation(0.025f, moss);
		aniMoss.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
	}

	/*
	 * The method is used for creature animations.
	 */
	public static void creatureAnimations() {
		dFly_pink = new TextureRegion[] {crea[1], crea[2], crea[3], crea[4]};		
		dFly_blue = new TextureRegion[] {crea[5], crea[6], crea[7], crea[8]};
		dFly_green = new TextureRegion[] {crea[9], crea[10], crea[11], crea[12]};
		dFly_yellow = new TextureRegion[] {crea[13], crea[14], crea[15], crea[16]};
		bee_blue = new TextureRegion[] {crea[17], crea[18], crea[19]};
		bee_brown = new TextureRegion[] {crea[20], crea[21], crea[22]};
		bee_yellow = new TextureRegion[] {crea[23], crea[24], crea[25]};
		bFly_blue = new TextureRegion[] {crea[26], crea[27], crea[28], crea[29]};
		bFly_orange = new TextureRegion[] {crea[30], crea[31], crea[32], crea[33]};
		bFly_purple = new TextureRegion[] {crea[34], crea[35], crea[36], crea[37]};
		bFly_red = new TextureRegion[] {crea[38], crea[39], crea[40], crea[41]};
		wasp_orange = new TextureRegion[] {crea[42], crea[43], crea[44]};
		wasp_yellow = new TextureRegion[] {crea[45], crea[46], crea[47]};
	}

	/*
	 * The method is used for enemy animations.
	 */
	public static void enemyAnimations() {
		en_brown = new TextureRegion[] {enemy[0], enemy[1], enemy[2], enemy[3]};
		aniEnBrown = new Animation(0.11f, en_brown);
		aniEnBrown.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		en_beige = new TextureRegion[] {enemy[4], enemy[5], enemy[6], enemy[7]};
		aniEnBeige = new Animation(0.11f, en_beige);
		aniEnBeige.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		en_black = new TextureRegion[] {enemy[8], enemy[9], enemy[10], enemy[11]};
		aniEnBlack = new Animation(0.11f, en_black);
		aniEnBlack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		en_white = new TextureRegion[] {enemy[12], enemy[13], enemy[14], enemy[15]};
		aniEnWhite= new Animation(0.11f, en_white);
		aniEnWhite.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		en_bat = new TextureRegion[] {enemy[22], enemy[22], enemy[22], enemy[23], enemy[23], enemy[23], enemy[24], enemy[24], enemy[24]};
		aniEnBat= new Animation(0.05f, en_bat);
		aniEnBat.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		enWasp_orange = new TextureRegion[] {enemy[16], enemy[16], enemy[17], enemy[17], enemy[18]};
		aniEnWasp_orange = new Animation(0.05f, enWasp_orange);
		aniEnWasp_orange.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		enWasp_yellow = new TextureRegion[] {enemy[19], enemy[19], enemy[20], enemy[20], enemy[21]};
		aniEnWasp_yellow = new Animation(0.05f, enWasp_yellow);
		aniEnWasp_yellow.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
	}

	/*
	 * The method is used for bat animations.
	 */
	public static void batAnimations() {
		bats_grey = new TextureRegion[] {en2[0], en2[0], en2[0], en2[1], en2[1], en2[1], en2[2], en2[2], en2[2]};
		bats_red = new TextureRegion[] {en2[3], en2[3], en2[3], en2[4], en2[4], en2[4], en2[5], en2[5], en2[5]};
		aniBats = new Animation(0.05f, bats_grey);
		aniBats.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
	}

	/*
	 * The method is used to dispose all of the assets when finished.
	 */
	public static void dispose() {
		animTexture.dispose();
		bgStars.dispose();
		bgMainSky.dispose();
		twig.dispose();
		clearBG.dispose();
		levelUI.dispose();
		feather.dispose();
		sp.dispose();
		bird1.dispose();
		bird2.dispose();
		bird3.dispose();
		bird4.dispose();
		bird5.dispose();
		aBtn.dispose();
		tEffect.dispose();
		birdPeak.dispose();
		gBird_L.dispose();
		cageBG.dispose();
		activeBG.dispose();
		bgCustom.dispose();
		preview.dispose();
		bottomBG.dispose();
		howtPly.dispose();
		howtPly1.dispose();
		howtPly2.dispose();
		message.dispose();
		locked.dispose();
		getClouds.dispose();
		getBird.dispose();
		getCreatures.dispose();
		getCollects.dispose();
		getUps.dispose();
		getEnemy2.dispose();
		getCharUI.dispose();
		splashScreen.dispose();
		getDeath.dispose();
		getUI.dispose();
		getEnemies.dispose();
		getPreview.dispose();
		uiSkin.dispose();
		rSkin.dispose();
		cSkin.dispose();
		clSkin.dispose();
		tweet.dispose();
		collect.dispose();
		death1.dispose();
		death2.dispose();
		pu1.dispose();
		pu2.dispose();
		collectable.dispose();
		shield.dispose();
	}
}
