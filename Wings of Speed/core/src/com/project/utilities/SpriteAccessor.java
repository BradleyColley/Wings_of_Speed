package com.project.utilities;

/*
 * This class lets you to use the tween fade out functionality to get the setupTween method() 
 * working within the SplashScreen class. However I had to follow a guide to understand 
 * roughly on what to implement. 
 * 
 * https://code.google.com/p/java-universal-tween-engine/wiki/TweenAccessor
 * 
 */

import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {

    public static final int ALPHA = 1;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
        case ALPHA:
            returnValues[0] = target.getColor().a; //Returns the fade out colour.
            return 1;
        default:
            return 0;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
        case ALPHA:
            target.setColor(1, 1, 1, newValues[0]); //Sets the alpha fade out colour to black.
            break;
        }
    }
}