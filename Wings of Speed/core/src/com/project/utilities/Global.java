package com.project.utilities;

/*
 * These variables can be accessed by any class within the project to prevent calling
 * unnecessary code.
 */

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.project.game.WOS;
import com.project.objects.BirdCharacter;
import com.project.universe.Rendering;
import com.project.universe.World;

public class Global {

	//Camera variables
	public static final float W = Gdx.graphics.getWidth(); //Retrieves the screen width from users device.
	public static final float H = Gdx.graphics.getHeight(); //Retrieves the screen height from users device.
	public static final float MIDPOINT_W = W/2; 
	public static final float MIDPOINT_H = H/2;

	//Variables
	public static float REPEAT_W = 0;
	public static float REPEAT_H = 0;
	public static int REPEAT_W2 = 0;
	public static String status;
	public static int en_W = 0;
	public static int en_H = 0;
	public static int crea_W = 0;
	public static int crea_H = 0;	
	public static int winValue = 0;
	public static float scrollSpeed = 0, enemySpeed = 0, abilitySpeed = 0, coinSpeed = 0;
	//soundSwitch variable prevents sounds running in the gameover and complete game states.
	public static Boolean soundStatus = true, soundSwitch = true; 

	//Classes
	public static WOS game;
	public static ScrollManager sManager;
	public static World world;
	public static DataManagement dManager;
	public static Rendering renderer;
	public static BirdCharacter bird;

	//Imports
	public static SpriteBatch batch;
	public static Stage stage, stage2;
	public static Animation anim;
	public static Skin s;
	public static Sprite[] cloud = Assets._clouds;
	public static List <String> levelInput = new ArrayList<String>();
	public static BitmapFont f1, f2, f3, f4;
	public static ShapeRenderer sr;
}
