package com.project.utilities;

/*
 * This AssetsManager class acts as the main class in loading all of the Wings of Speed
 * assets by calling the AssetManager API. 
 */

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsManager {

	public static AssetManager assetM = new AssetManager();

	/*
	 * This method is only used once within the SplashScreen class
	 * to load all of the assets from their atlases etc.
	 */
	public static void load() {

		assetM.load("img/obstacles/cloudsUI.atlas", TextureAtlas.class);
		assetM.load("skins/menuUI.atlas", TextureAtlas.class);
		assetM.load("img/birds/oBird.atlas", TextureAtlas.class);
		assetM.load("img/birds/birdsUI_2.atlas", TextureAtlas.class);
		assetM.load("img/birds/death/deathUI.atlas", TextureAtlas.class);
		assetM.load("img/enemies/enemyUI.atlas", TextureAtlas.class);
		assetM.load("img/obstacles/batsUI.atlas", TextureAtlas.class);
		assetM.load("skins/retryUI.atlas", TextureAtlas.class);
		assetM.load("img/creatures/creaturesUI.atlas", TextureAtlas.class);
		assetM.load("img/collectables/collectablesUI.atlas", TextureAtlas.class);
		assetM.load("img/ui/pUps_UI.atlas", TextureAtlas.class);
		assetM.load("img/ui/charUI.atlas", TextureAtlas.class);
		assetM.load("img/ui/purchaseUI.atlas", TextureAtlas.class);
		assetM.load("img/ui/previewUI.atlas", TextureAtlas.class);

		assetM.load("img/ui/bgSky.png", Texture.class);
		assetM.load("img/ui/clearBG.png", Texture.class);
		assetM.load("img/ui/stars.png", Texture.class);
		assetM.load("img/ui/treeBG.png", Texture.class);
		assetM.load("img/collectables/twig.png", Texture.class);
		assetM.load("img/collectables/feather.png", Texture.class);
		assetM.load("img/birds/oBird3.png", Texture.class);
		assetM.load("img/birds/owl.png", Texture.class);
		assetM.load("img/birds/yBird.png", Texture.class);
		assetM.load("img/birds/bBird.png", Texture.class);
		assetM.load("img/birds/gBird.png", Texture.class);
		assetM.load("img/birds/gBird_L.png", Texture.class);
		assetM.load("img/ui/activeBtn.png", Texture.class);
		assetM.load("img/ui/titleEffect.png", Texture.class);
		assetM.load("img/ui/birdPeak.png", Texture.class);
		assetM.load("img/ui/cageBG.png", Texture.class);
		assetM.load("img/ui/activeBG.png", Texture.class);
		assetM.load("img/ui/customBG.png", Texture.class);
		assetM.load("img/ui/preview.png", Texture.class);
		assetM.load("img/ui/bottomBG.png", Texture.class);
		assetM.load("img/ui/howToPlay.png", Texture.class);
		assetM.load("img/ui/howToPlay_lvl1.png", Texture.class);
		assetM.load("img/ui/howToPlay_lvl2.png", Texture.class);
		assetM.load("img/ui/toastMessage.png", Texture.class);
		assetM.load("img/ui/locked.png", Texture.class);
		assetM.load("img/ui/shield.png", Texture.class);

		assetM.load("skins/uiRetry.json", Skin.class, new SkinLoader.SkinParameter("skins/levelUI.atlas"));
		assetM.load("skins/uiMenu.json", Skin.class, new SkinLoader.SkinParameter("skins/menuUI.atlas"));
		assetM.load("skins/uiCage.json", Skin.class, new SkinLoader.SkinParameter("skins/cageUI.atlas"));
		assetM.load("skins/uiCustom.json", Skin.class, new SkinLoader.SkinParameter("skins/customUI.atlas"));

		assetM.load("fonts/ravie_white.fnt", BitmapFont.class);
		assetM.load("fonts/ravie_black.fnt", BitmapFont.class);
		assetM.load("fonts/ravie_black1.fnt", BitmapFont.class);
		assetM.load("fonts/small.fnt", BitmapFont.class);

		assetM.load("sounds/tweet.wav", Sound.class);
		assetM.load("sounds/coin.wav", Sound.class);
		assetM.load("sounds/death1.wav", Sound.class);
		assetM.load("sounds/death2.wav", Sound.class);
		assetM.load("sounds/collectable.wav", Sound.class);
		assetM.load("sounds/badPU.wav", Sound.class);
		assetM.load("sounds/goodPU.wav", Sound.class);
	}

	/*
	 * This method is only called once within the SplashScreen class
	 * to determine the progress of loading the assets.
	 */
	public boolean update() {
		return assetM.update();
	}

	/*
	 * This method is only called once the application ends to dispose
	 * every single asset in the game.
	 */
	public static void dispose() {
		assetM.dispose();
	}
}
