package com.project.screens;

/*
 * This class implements the Screen interface which represents one of many application screens meaning 
 * the screen can be linked to a Splash screen, Menu screen and so on by switching the Screen calling 
 * the Game.setScreen() method.
 * 
 * This class represents as the Splash screen to load assets in the back end, once complete it will
 * switch the Screen by calling the setScreen() method and changing it to the Menu screen.
 * 
 */

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.project.game.WOS;
import com.project.utilities.Assets;
import com.project.utilities.AssetsManager;
import com.project.utilities.SpriteAccessor;

import static com.project.utilities.Global.*;

public class SplashScreen implements Screen {

	private TweenManager tweenM;
	private Sprite sprite;
	private float scaleFontW, scaleFontH, scaleW, scaleH;
	private BitmapFont font;

	public SplashScreen(WOS main) {
		game = main;
	}
	
    /*
     * The show() method is called upon once the setScreen has been called from the previous 
     * class. For instance, WOS class would have called setScreen() method to set the new screen as 
     * the SplashScreen class. This would call show() method first before any others. 
     *  
     */
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true); //Disables the Android back button.
		batch = new SpriteBatch();
		sprite = new Sprite(Assets.sp);
		scaleW = (W/1f) / Assets.sp.getWidth();
		scaleH = (H/1f) / Assets.sp.getHeight();

		sprite.setSize(sprite.getRegionWidth() * scaleW, sprite.getRegionHeight() * scaleH);
		sprite.setPosition(0, 0);

		font = new BitmapFont(Gdx.files.internal("fonts/ravie_black.fnt"), false);
		scaleFontW = (W/90f) / font.getSpaceWidth();
		scaleFontH = (H/20f) / font.getLineHeight();
		font.setScale(scaleFontW, scaleFontH);
		AssetsManager.load(); //Loads the assets.
		setupTween();
	}
	
	/*
	 * The render() method is implemented from the Screen interface and called upon when
	 * the screen should render.
	 * 
	 * Below contains an if statement relating to loading the Assets. Once AssetsManager.load()
	 * has been executed in show() method then the update() method within AssetsManager will 
	 * return the Boolean statement true. Launching the next operations of Assets.load() method
	 * which will retrieve all of the assets that have been loaded and starts the TweenManager 
	 * fade out effect. 
	 * 
	 * The purpose of having glClearColor() method is it prevents the background image from flickering 
	 * when its been rendered in black then renders the image on top inside the (SpriteBatch) batch() methods.
	 */
	@Override
	public void render(float delta) {
		if(AssetsManager.assetM.update()==true) {
			Assets.load();
			status = "menu";
			tweenM.update(delta);
		}

		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		sprite.draw(batch);
		//getProgress() method displays counting up to 100% until the assets have loaded.
		font.drawMultiLine(batch, (int)(AssetsManager.assetM.getProgress()*100)+"%", (W/2.02f), (H/3.4f));
		batch.end();
	}

	/*
	 * This method creates the Tween fade out effect for the loading screen. It uses 
	 * the Tween engine by calling the TweenManager class. 
	 * 
	 * Also uses the SpriteAccessor, Value and ValueAccessor classes to make the sprite 
	 * image to fade out when switching the screen to Menu.
	 */
	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tweenM = new TweenManager();

		TweenCallback cb = new TweenCallback() {
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setScreen(new MenuScreen(game));
			}
		};
		Tween.to(sprite, SpriteAccessor.ALPHA, 2f).target(0)
		.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenM);
	}
	
	@Override
	public void dispose() { 
		batch.dispose();
		font.dispose();
	}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause(){}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
