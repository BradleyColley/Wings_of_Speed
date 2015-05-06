package com.project.utilities;

/*
 * This class is the basic foundation of declaring how to scroll objects across the screen to allow
 * my gameplay objects to inherit the Scrollable constructor parameters to enable scrolling. 
 */

import com.badlogic.gdx.math.Vector2;
import static com.project.utilities.Global.*;

public class Scrollable {

	protected Vector2 position;
	protected Vector2 velocity; 
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;
	public static int i = 10;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}

	/*
	 * This method updates objects scrolling left.
	 */
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		
		//If the moving object is no longer visable then reset the location.
		if (position.x + width < -W/8) {
			isScrolledLeft = true;
		}		
	}

	/*
	 * This method stops the objects from scrolling once called upon.
	 */
	public void stop() {
		velocity.x = 0;
	}

	/*
	 * This method gets overridden in the subclasses to perform their operations. 
	 */
	public void reset(float newX, float newY) {
		position.x = newX;
		position.y = newY;
		isScrolledLeft = false;
	}

	//Getter methods
	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
