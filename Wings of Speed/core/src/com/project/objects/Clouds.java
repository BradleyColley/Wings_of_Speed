package com.project.objects;

/*
 * This Clouds class extends the Scrollable class to gain access to its protected variables and methods. Purpose of this class
 * is to define the clouds attributes and collisions. 
 */

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.project.levels.LevelOne;
import com.project.utilities.Assets;
import com.project.utilities.ScrollManager;
import com.project.utilities.Scrollable;
import static com.project.utilities.Global.*;

public class Clouds extends Scrollable {
	public static float w = W;
	public static float h = H;
	public static Float[] c, r, b, s;
	public static int [] i;
	public static Circle c1a, c2a, c3a, c4a, c5a, c6a, c7a, c1b, c2b, c3b, c4b, c5b, c6b, c7b, c1c, c2c, c3c, c4c, c5c, c6c, c7c,
	c1d, c2d, c3d, c4d, c5d, c6d, c7d, c1e, c2e, c3e, c4e, c5e, c6e, c7e, c1f, c2f, c3f, c4f, c5f, c6f, c7f, c1g, c2g, c3g, c4g, 
	c5g, c6g, c7g, c1h, c2h, c3h, c4h, c5h, c6h, c7h, c1i, c2i, c3i, c4i, c5i, c6i, c7i, c1j, c2j, c3j, c4j, c5j, c6j, c7j, c1k, c2k, 
	c3k, c4k, c5k, c6k, c7k, c1l, c2l, c3l, c4l, c5l, c6l, c7l, c1m, c2m, c3m, c4m, c5m, c6m, c7m, c1n, c2n, c3n, c4n, c5n, c6n, c7n,
	c1o, c2o, c3o, c4o, c5o, c6o, c7o, c1p, c2p, c3p, c4p, c5p, c6p, c7p;

	public Clouds(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);

		s = new Float[20]; //Scaling clouds
		c = new Float[32]; //Coordinates
		r = new Float[22]; //Ratio of circles
		b = new Float[10]; //Cloud's width and height
		i = new int[16]; //Cloud array values

		newCircles();
		scaling();
		collisionScale();
	}

	/*
	 * Method contains the declared collision circles.
	 */
	private void newCircles() {
		c1a = new Circle(); c2a = new Circle(); c3a = new Circle(); c4a = new Circle(); c5a = new Circle(); c6a = new Circle(); c7a = new Circle();
		c1b = new Circle(); c2b = new Circle(); c3b = new Circle(); c4b = new Circle(); c5b = new Circle(); c6b = new Circle(); c7b = new Circle();
		c1c = new Circle(); c2c = new Circle(); c3c = new Circle(); c4c = new Circle(); c5c = new Circle(); c6c = new Circle(); c7c = new Circle(); 
		c1d = new Circle(); c2d = new Circle(); c3d = new Circle(); c4d = new Circle(); c5d = new Circle(); c6d = new Circle(); c7d = new Circle();
		c1e = new Circle(); c2e = new Circle(); c3e = new Circle(); c4e = new Circle(); c5e = new Circle(); c6e = new Circle(); c7e = new Circle();
		c1f = new Circle(); c2f = new Circle(); c3f = new Circle(); c4f = new Circle(); c5f = new Circle(); c6f = new Circle(); c7f = new Circle();
		c1g = new Circle(); c2g = new Circle(); c3g = new Circle(); c4g = new Circle(); c5g = new Circle(); c6g = new Circle(); c7g = new Circle();
		c1h = new Circle(); c2h = new Circle(); c3h = new Circle(); c4h = new Circle(); c5h = new Circle(); c6h = new Circle(); c7h = new Circle();
		c1i = new Circle(); c2i = new Circle(); c3i = new Circle(); c4i = new Circle(); c5i = new Circle(); c6i = new Circle(); c7i = new Circle();
		c1j = new Circle(); c2j = new Circle(); c3j = new Circle(); c4j = new Circle(); c5j = new Circle(); c6j = new Circle(); c7j = new Circle();
		c1k = new Circle(); c2k = new Circle(); c3k = new Circle(); c4k = new Circle(); c5k = new Circle(); c6k = new Circle(); c7k = new Circle();
		c1l = new Circle(); c2l = new Circle(); c3l = new Circle(); c4l = new Circle(); c5l = new Circle(); c6l = new Circle(); c7l = new Circle();
		c1m = new Circle(); c2m = new Circle(); c3m = new Circle(); c4m = new Circle(); c5m = new Circle(); c6m = new Circle(); c7m = new Circle();
		c1n = new Circle(); c2n = new Circle(); c3n = new Circle(); c4n = new Circle(); c5n = new Circle(); c6n = new Circle(); c7n = new Circle();
		c1o = new Circle(); c2o = new Circle(); c3o = new Circle(); c4o = new Circle(); c5o = new Circle(); c6o = new Circle(); c7o = new Circle();
		c1p = new Circle(); c2p = new Circle(); c3p = new Circle(); c4p = new Circle(); c5p = new Circle(); c6p = new Circle(); c7p = new Circle();
	}

	/*
	 * This method is used to scale the clouds.
	 */
	private void scaling() {
		b[0] = Assets._clouds[0].getWidth();
		b[1] = Assets._clouds[0].getHeight();
		b[2] = Assets._clouds[1].getWidth();
		b[3] = Assets._clouds[1].getHeight();
		b[4] = Assets._clouds[2].getWidth();
		b[5] = Assets._clouds[2].getHeight();
		b[6] = Assets._clouds[3].getWidth();
		b[7] = Assets._clouds[3].getHeight();
		b[8] = Assets._clouds[4].getWidth();
		b[9] = Assets._clouds[4].getHeight();	

		//Small cloud scale
		s[0] = (W/23f) / b[0];
		s[1] = (H/12f) / b[0];

		//Medium01 cloud scale
		s[2] = (W/14f) / b[2];
		s[3] = (H/8f) / b[2];

		//Medium02 cloud scale
		s[4] = (W/10f) / b[4];
		s[5] = (H/6f) / b[4];

		//Medium03 cloud scale
		s[6] = (W/11f) / b[6];
		s[7] = (H/7f) / b[6];

		//Large cloud scale
		s[8] = (W/8f) / b[8];
		s[9] = (H/4f) / b[8];
	}

	/*
	 * This method is used to scale the collision circles.
	 */
	public static void collisionScale() {		
		//scaling circles around cloud 0		
		r[0] = (w/95f) / b[0];
		r[1] = (h/95f) / b[1];	
		r[2] = (w/150f) / b[0];
		r[3] = (h/150f) / b[1];	

		//scaling circles around cloud 1
		r[4] = (w/70f) / b[2];
		r[5] = (h/70f) / b[3];
		r[6] = (w/120f) / b[2];
		r[7] = (h/120f) / b[3];	

		//scaling circles around cloud 2
		r[8] = (w/55f) / b[4];
		r[9] = (h/55f) / b[5];	
		r[10] = (w/90f) / b[4];
		r[11] = (h/90f) / b[5];	

		//scaling circles around cloud 3
		r[12] = (w/55f) / b[6];
		r[13] = (h/55f) / b[7];		
		r[14] = (w/130f) / b[6];
		r[15] = (h/130f) / b[7];	

		//scaling circles around cloud 4
		r[16] = (w/70f) / b[8];
		r[17] = (h/70f) / b[9];	
		r[18] = (w/80f) / b[8];
		r[19] = (h/80f) / b[9];	
		r[20] = (w/50f) / b[8];
		r[21] = (h/50f) / b[9];	
	}

	/*
	 * This method is responsible for cloud one collisions.
	 */
	public static void cloudOneCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[0] = ScrollManager.clouds[i[0]].getX();
		c[1] = ScrollManager.clouds[i[0]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[0] == cloud[0]) {	
			c1a.set(c[0] + (w/40f), c[1] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2a.set(c[0] + (w/80f), c[1] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[0] == cloud[1]) {	
			c1a.set(c[0] + (w/33f), c[1] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2a.set(c[0] + (w/36f), c[1] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3a.set(c[0] + (w/19f), c[1] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[0] == cloud[2]) {	
			c1a.set(c[0] + (w/28f), c[1] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2a.set(c[0] + (w/19f), c[1] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3a.set(c[0] + (w/13f), c[1] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4a.set(c[0] + (w/21f), c[1] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[0] == cloud[3]) {	
			c1a.set(c[0] + (w/23f), c[1] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2a.set(c[0] + (w/15.3f), c[1] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3a.set(c[0] + (w/25f), c[1] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[0] == cloud[4]) {	
			c1a.set(c[0] + (w/10f), c[1] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2a.set(c[0] + (w/11f), c[1] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3a.set(c[0] + (w/14f), c[1] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4a.set(c[0] + (w/18.8f), c[1] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5a.set(c[0] + (w/11f), c[1] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6a.set(c[0] + (w/25.5f), c[1] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7a.set(c[0] + (w/24f), c[1] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud two collisions.
	 */
	public static void cloudTwoCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[2] = ScrollManager.clouds[i[1]].getX();
		c[3] = ScrollManager.clouds[i[1]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[1] == cloud[0]) {	
			c1b.set(c[2] + (w/40f), c[3] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2b.set(c[2] + (w/80f), c[3] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[1] == cloud[1]) {	
			c1b.set(c[2] + (w/33f), c[3] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2b.set(c[2] + (w/36f), c[3] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3b.set(c[2] + (w/19f), c[3] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[1] == cloud[2]) {	
			c1b.set(c[2] + (w/28f), c[3] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2b.set(c[2] + (w/19f), c[3] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3b.set(c[2] + (w/13f), c[3] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4b.set(c[2] + (w/21f), c[3] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[1] == cloud[3]) {	
			c1b.set(c[2] + (w/23f), c[3] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2b.set(c[2] + (w/15.3f), c[3] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3b.set(c[2] + (w/25f), c[3] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[1] == cloud[4]) {	
			c1b.set(c[2] + (w/10f), c[3] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2b.set(c[2] + (w/11f), c[3] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3b.set(c[2] + (w/14f), c[3] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4b.set(c[2] + (w/18.8f), c[3] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5b.set(c[2] + (w/11f), c[3] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6b.set(c[2] + (w/25.5f), c[3] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7b.set(c[2] + (w/24f), c[3] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud three collisions.
	 */
	public static void cloudThreeCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[4] = ScrollManager.clouds[i[2]].getX();
		c[5] = ScrollManager.clouds[i[2]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[2] == cloud[0]) {	
			c1c.set(c[4] + (w/40f), c[5] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2c.set(c[4] + (w/80f), c[5] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[2] == cloud[1]) {	
			c1c.set(c[4] + (w/33f), c[5] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2c.set(c[4] + (w/36f), c[5] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3c.set(c[4] + (w/19f), c[5] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[2] == cloud[2]) {	
			c1c.set(c[4] + (w/28f), c[5] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2c.set(c[4] + (w/19f), c[5] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3c.set(c[4] + (w/13f), c[5] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4c.set(c[4] + (w/21f), c[5] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[2] == cloud[3]) {	
			c1c.set(c[4] + (w/23f), c[5] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2c.set(c[4] + (w/15.3f), c[5] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3c.set(c[4] + (w/25f), c[5] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[2] == cloud[4]) {	
			c1c.set(c[4] + (w/10f), c[5] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2c.set(c[4] + (w/11f), c[5] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3c.set(c[4] + (w/14f), c[5] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4c.set(c[4] + (w/18.8f), c[5] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5c.set(c[4] + (w/11f), c[5] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6c.set(c[4] + (w/25.5f), c[5] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7c.set(c[4] + (w/24f), c[5] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud four collisions.
	 */
	public static void cloudFourCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[6] = ScrollManager.clouds[i[3]].getX();
		c[7] = ScrollManager.clouds[i[3]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[3] == cloud[0]) {	
			c1d.set(c[6] + (w/40f), c[7] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2d.set(c[6] + (w/80f), c[7] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[3] == cloud[1]) {	
			c1d.set(c[6] + (w/33f), c[7] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2d.set(c[6] + (w/36f), c[7] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3d.set(c[6] + (w/19f), c[7] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[3] == cloud[2]) {	
			c1d.set(c[6] + (w/28f), c[7] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2d.set(c[6] + (w/19f), c[7] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3d.set(c[6] + (w/13f), c[7] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4d.set(c[6] + (w/21f), c[7] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[3] == cloud[3]) {	
			c1d.set(c[6] + (w/23f), c[7] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2d.set(c[6] + (w/15.3f), c[7] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3d.set(c[6] + (w/25f), c[7] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[3] == cloud[4]) {	
			c1d.set(c[6] + (w/10f), c[7] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2d.set(c[6] + (w/11f), c[7] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3d.set(c[6] + (w/14f), c[7] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4d.set(c[6] + (w/18.8f), c[7] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5d.set(c[6] + (w/11f), c[7] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6d.set(c[6] + (w/25.5f), c[7] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7d.set(c[6] + (w/24f), c[7] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud five collisions.
	 */
	public static void cloudFiveCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[8] = ScrollManager.clouds[i[4]].getX();
		c[9] = ScrollManager.clouds[i[4]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[4] == cloud[0]) {	
			c1e.set(c[8] + (w/40f), c[9] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2e.set(c[8] + (w/80f), c[9] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[4] == cloud[1]) {	
			c1e.set(c[8] + (w/33f), c[9] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2e.set(c[8] + (w/36f), c[9] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3e.set(c[8] + (w/19f), c[9] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[4] == cloud[2]) {	
			c1e.set(c[8] + (w/28f), c[9] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2e.set(c[8] + (w/19f), c[9] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3e.set(c[8] + (w/13f), c[9] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4e.set(c[8] + (w/21f), c[9] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[4] == cloud[3]) {	
			c1e.set(c[8] + (w/23f), c[9] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2e.set(c[8] + (w/15.3f), c[9] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3e.set(c[8] + (w/25f), c[9] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[4] == cloud[4]) {	
			c1e.set(c[8] + (w/10f), c[9] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2e.set(c[8] + (w/11f), c[9] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3e.set(c[8] + (w/14f), c[9] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4e.set(c[8] + (w/18.8f), c[9] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5e.set(c[8] + (w/11f), c[9] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6e.set(c[8] + (w/25.5f), c[9] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7e.set(c[8] + (w/24f), c[9] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud six collisions.
	 */
	public static void cloudSixCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[10] = ScrollManager.clouds[i[5]].getX();
		c[11] = ScrollManager.clouds[i[5]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[5] == cloud[0]) {	
			c1f.set(c[10] + (w/40f), c[11] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2f.set(c[10] + (w/80f), c[11] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[5] == cloud[1]) {	
			c1f.set(c[10] + (w/33f), c[11] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2f.set(c[10] + (w/36f), c[11] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3f.set(c[10] + (w/19f), c[11] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[5] == cloud[2]) {	
			c1f.set(c[10] + (w/28f), c[11] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2f.set(c[10] + (w/19f), c[11] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3f.set(c[10] + (w/13f), c[11] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4f.set(c[10] + (w/21f), c[11] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[5] == cloud[3]) {	
			c1f.set(c[10] + (w/23f), c[11] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2f.set(c[10] + (w/15.3f), c[11] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3f.set(c[10] + (w/25f), c[11] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[5] == cloud[4]) {	
			c1f.set(c[10] + (w/10f), c[11] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2f.set(c[10] + (w/11f), c[11] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3f.set(c[10] + (w/14f), c[11] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4f.set(c[10] + (w/18.8f), c[11] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5f.set(c[10] + (w/11f), c[11] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6f.set(c[10] + (w/25.5f), c[11] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7f.set(c[10] + (w/24f), c[11] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud seven collisions.
	 */
	public static void cloudSevenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[12] = ScrollManager.clouds[i[6]].getX();
		c[13] = ScrollManager.clouds[i[6]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[6] == cloud[0]) {	
			c1g.set(c[12] + (w/40f), c[13] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2g.set(c[12] + (w/80f), c[13] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[6] == cloud[1]) {	
			c1g.set(c[12] + (w/33f), c[13] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2g.set(c[12] + (w/36f), c[13] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3g.set(c[12] + (w/19f), c[13] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[6] == cloud[2]) {	
			c1g.set(c[12] + (w/28f), c[13] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2g.set(c[12] + (w/19f), c[13] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3g.set(c[12] + (w/13f), c[13] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4g.set(c[12] + (w/21f), c[13] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[6] == cloud[3]) {	
			c1g.set(c[12] + (w/23f), c[13] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2g.set(c[12] + (w/15.3f), c[13] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3g.set(c[12] + (w/25f), c[13] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[6] == cloud[4]) {	
			c1g.set(c[12] + (w/10f), c[13] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2g.set(c[12] + (w/11f), c[13] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3g.set(c[12] + (w/14f), c[13] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4g.set(c[12] + (w/18.8f), c[13] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5g.set(c[12] + (w/11f), c[13] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6g.set(c[12] + (w/25.5f), c[13] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7g.set(c[12] + (w/24f), c[13] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud eight collisions.
	 */
	public static void cloudEightCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[14] = ScrollManager.clouds[i[7]].getX();
		c[15] = ScrollManager.clouds[i[7]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[7] == cloud[0]) {	
			c1h.set(c[14] + (w/40f), c[15] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2h.set(c[14] + (w/80f), c[15] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[7] == cloud[1]) {	
			c1h.set(c[14] + (w/33f), c[15] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2h.set(c[14] + (w/36f), c[15] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3h.set(c[14] + (w/19f), c[15] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[7] == cloud[2]) {	
			c1h.set(c[14] + (w/28f), c[15] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2h.set(c[14] + (w/19f), c[15] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3h.set(c[14] + (w/13f), c[15] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4h.set(c[14] + (w/21f), c[15] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[7] == cloud[3]) {	
			c1h.set(c[14] + (w/23f), c[15] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2h.set(c[14] + (w/15.3f), c[15] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3h.set(c[14] + (w/25f), c[15] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[7] == cloud[4]) {	
			c1h.set(c[14] + (w/10f), c[15] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2h.set(c[14] + (w/11f), c[15] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3h.set(c[14] + (w/14f), c[15] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4h.set(c[14] + (w/18.8f), c[15] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5h.set(c[14] + (w/11f), c[15] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6h.set(c[14] + (w/25.5f), c[15] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7h.set(c[14] + (w/24f), c[15] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud nine collisions.
	 */
	public static void cloudNineCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[16] = ScrollManager.clouds[i[8]].getX();
		c[17] = ScrollManager.clouds[i[8]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[8] == cloud[0]) {	
			c1i.set(c[16] + (w/40f), c[17] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2i.set(c[16] + (w/80f), c[17] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[8] == cloud[1]) {	
			c1i.set(c[16] + (w/33f), c[17] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2i.set(c[16] + (w/36f), c[17] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3i.set(c[16] + (w/19f), c[17] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[8] == cloud[2]) {	
			c1i.set(c[16] + (w/28f), c[17] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2i.set(c[16] + (w/19f), c[17] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3i.set(c[16] + (w/13f), c[17] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4i.set(c[16] + (w/21f), c[17] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[8] == cloud[3]) {	
			c1i.set(c[16] + (w/23f), c[17] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2i.set(c[16] + (w/15.3f), c[17] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3i.set(c[16] + (w/25f), c[17] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[8] == cloud[4]) {	
			c1i.set(c[16] + (w/10f), c[17] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2i.set(c[16] + (w/11f), c[17] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3i.set(c[16] + (w/14f), c[17] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4i.set(c[16] + (w/18.8f), c[17] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5i.set(c[16] + (w/11f), c[17] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6i.set(c[16] + (w/25.5f), c[17] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7i.set(c[16] + (w/24f), c[17] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud ten collisions.
	 */
	public static void cloudTenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[18] = ScrollManager.clouds[i[9]].getX();
		c[19] = ScrollManager.clouds[i[9]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[9] == cloud[0]) {	
			c1j.set(c[18] + (w/40f), c[19] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2j.set(c[18] + (w/80f), c[19] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[9] == cloud[1]) {	
			c1j.set(c[18] + (w/33f), c[19] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2j.set(c[18] + (w/36f), c[19] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3j.set(c[18] + (w/19f), c[19] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[9] == cloud[2]) {	
			c1j.set(c[18] + (w/28f), c[19] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2j.set(c[18] + (w/19f), c[19] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3j.set(c[18] + (w/13f), c[19] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4j.set(c[18] + (w/21f), c[19] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[9] == cloud[3]) {	
			c1j.set(c[18] + (w/23f), c[19] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2j.set(c[18] + (w/15.3f), c[19] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3j.set(c[18] + (w/25f), c[19] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[9] == cloud[4]) {	
			c1j.set(c[18] + (w/10f), c[19] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2j.set(c[18] + (w/11f), c[19] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3j.set(c[18] + (w/14f), c[19] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4j.set(c[18] + (w/18.8f), c[19] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5j.set(c[18] + (w/11f), c[19] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6j.set(c[18] + (w/25.5f), c[19] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7j.set(c[18] + (w/24f), c[19] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud eleven collisions.
	 */
	public static void cloudElevenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[20] = ScrollManager.clouds[i[10]].getX();
		c[21] = ScrollManager.clouds[i[10]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[10] == cloud[0]) {	
			c1k.set(c[20] + (w/40f), c[21] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2k.set(c[20] + (w/80f), c[21] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[10] == cloud[1]) {	
			c1k.set(c[20] + (w/33f), c[21] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2k.set(c[20] + (w/36f), c[21] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3k.set(c[20] + (w/19f), c[21] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[10] == cloud[2]) {	
			c1k.set(c[20] + (w/28f), c[21] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2k.set(c[20] + (w/19f), c[21] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3k.set(c[20] + (w/13f), c[21] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4k.set(c[20] + (w/21f), c[21] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[10] == cloud[3]) {	
			c1k.set(c[20] + (w/23f), c[21] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2k.set(c[20] + (w/15.3f), c[21] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3k.set(c[20] + (w/25f), c[21] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[10] == cloud[4]) {	
			c1k.set(c[20] + (w/10f), c[21] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2k.set(c[20] + (w/11f), c[21] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3k.set(c[20] + (w/14f), c[21] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4k.set(c[20] + (w/18.8f), c[21] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5k.set(c[20] + (w/11f), c[21] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6k.set(c[20] + (w/25.5f), c[21] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7k.set(c[20] + (w/24f), c[21] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud twelve collisions.
	 */
	public static void cloudTwelveCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[22] = ScrollManager.clouds[i[11]].getX();
		c[23] = ScrollManager.clouds[i[11]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[11] == cloud[0]) {	
			c1l.set(c[22] + (w/40f), c[23] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2l.set(c[22] + (w/80f), c[23] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[11] == cloud[1]) {	
			c1l.set(c[22] + (w/33f), c[23] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2l.set(c[22] + (w/36f), c[23] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3l.set(c[22] + (w/19f), c[23] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[11] == cloud[2]) {	
			c1l.set(c[22] + (w/28f), c[23] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2l.set(c[22] + (w/19f), c[23] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3l.set(c[22] + (w/13f), c[23] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4l.set(c[22] + (w/21f), c[23] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[11] == cloud[3]) {	
			c1l.set(c[22] + (w/23f), c[23] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2l.set(c[22] + (w/15.3f), c[23] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3l.set(c[22] + (w/25f), c[23] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[11] == cloud[4]) {	
			c1l.set(c[22] + (w/10f), c[23] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2l.set(c[22] + (w/11f), c[23] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3l.set(c[22] + (w/14f), c[23] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4l.set(c[22] + (w/18.8f), c[23] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5l.set(c[22] + (w/11f), c[23] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6l.set(c[22] + (w/25.5f), c[23] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7l.set(c[22] + (w/24f), c[23] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud thirteen collisions.
	 */
	public static void cloudThirteenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[24] = ScrollManager.clouds[i[12]].getX();
		c[25] = ScrollManager.clouds[i[12]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[12] == cloud[0]) {	
			c1m.set(c[24] + (w/40f), c[25] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2m.set(c[24] + (w/80f), c[25] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[12] == cloud[1]) {	
			c1m.set(c[24] + (w/33f), c[25] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2m.set(c[24] + (w/36f), c[25] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3m.set(c[24] + (w/19f), c[25] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[12] == cloud[2]) {	
			c1m.set(c[24] + (w/28f), c[25] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2m.set(c[24] + (w/19f), c[25] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3m.set(c[24] + (w/13f), c[25] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4m.set(c[24] + (w/21f), c[25] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[12] == cloud[3]) {	
			c1m.set(c[24] + (w/23f), c[25] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2m.set(c[24] + (w/15.3f), c[25] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3m.set(c[24] + (w/25f), c[25] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[12] == cloud[4]) {	
			c1m.set(c[24] + (w/10f), c[25] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2m.set(c[24] + (w/11f), c[25] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3m.set(c[24] + (w/14f), c[25] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4m.set(c[24] + (w/18.8f), c[25] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5m.set(c[24] + (w/11f), c[25] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6m.set(c[24] + (w/25.5f), c[25] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7m.set(c[24] + (w/24f), c[25] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud fourteen collisions.
	 */
	public static void cloudFourteenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[26] = ScrollManager.clouds[i[13]].getX();
		c[27] = ScrollManager.clouds[i[13]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[13] == cloud[0]) {	
			c1n.set(c[26] + (w/40f), c[27] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2n.set(c[26] + (w/80f), c[27] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[13] == cloud[1]) {	
			c1n.set(c[26] + (w/33f), c[27] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2n.set(c[26] + (w/36f), c[27] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3n.set(c[26] + (w/19f), c[27] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[13] == cloud[2]) {	
			c1n.set(c[26] + (w/28f), c[27] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2n.set(c[26] + (w/19f), c[27] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3n.set(c[26] + (w/13f), c[27] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4n.set(c[26] + (w/21f), c[27] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[13] == cloud[3]) {	
			c1n.set(c[26] + (w/23f), c[27] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2n.set(c[26] + (w/15.3f), c[27] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3n.set(c[26] + (w/25f), c[27] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[13] == cloud[4]) {	
			c1n.set(c[26] + (w/10f), c[27] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2n.set(c[26] + (w/11f), c[27] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3n.set(c[26] + (w/14f), c[27] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4n.set(c[26] + (w/18.8f), c[27] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5n.set(c[26] + (w/11f), c[27] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6n.set(c[26] + (w/25.5f), c[27] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7n.set(c[26] + (w/24f), c[27] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud fifteen collisions.
	 */
	public static void cloudFifteenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[28] = ScrollManager.clouds[i[14]].getX();
		c[29] = ScrollManager.clouds[i[14]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[14] == cloud[0]) {	
			c1o.set(c[28] + (w/40f), c[29] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2o.set(c[28] + (w/80f), c[29] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[14] == cloud[1]) {	
			c1o.set(c[28] + (w/33f), c[29] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2o.set(c[28] + (w/36f), c[29] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3o.set(c[28] + (w/19f), c[29] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[14] == cloud[2]) {	
			c1o.set(c[28] + (w/28f), c[29] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2o.set(c[28] + (w/19f), c[29] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3o.set(c[28] + (w/13f), c[29] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4o.set(c[28] + (w/21f), c[29] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[14] == cloud[3]) {	
			c1o.set(c[28] + (w/23f), c[29] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2o.set(c[28] + (w/15.3f), c[29] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3o.set(c[28] + (w/25f), c[29] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[14] == cloud[4]) {	
			c1o.set(c[28] + (w/10f), c[29] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2o.set(c[28] + (w/11f), c[29] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3o.set(c[28] + (w/14f), c[29] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4o.set(c[28] + (w/18.8f), c[29] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5o.set(c[28] + (w/11f), c[29] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6o.set(c[28] + (w/25.5f), c[29] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7o.set(c[28] + (w/24f), c[29] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for cloud sixteen collisions.
	 */
	public static void cloudSixteenCollision() {
		/*
		 * Below retrieves the cloud's X and Y positions based upon what the i float array 
		 * equals too from being defined in the LevelOne class of the scaleCloudsSelector method.
		 */
		c[30] = ScrollManager.clouds[i[15]].getX();
		c[31] = ScrollManager.clouds[i[15]].getY();

		/*
		 * If the levelOne class cloud array equals to element 0 then set the position of the 
		 * collisions otherwise move onto the next element with then arraylist.
		 */
		if(LevelOne.clouds[15] == cloud[0]) {	
			c1p.set(c[30] + (w/40f), c[31] + (h/32f), (r[0]*b[0]) + (r[1]*b[1]));
			c2p.set(c[30] + (w/80f), c[31] + (h/32f), (r[2]*b[0]) + (r[3]*b[1]));
		} else if(LevelOne.clouds[15] == cloud[1]) {	
			c1p.set(c[30] + (w/33f), c[31] + (h/24f), (r[4]*b[2]) + (r[5]*b[3]));
			c2p.set(c[30] + (w/36f), c[31] + (h/14f), (r[6]*b[2]) + (r[7]*b[3]));
			c3p.set(c[30] + (w/19f), c[31] + (h/18.5f), (r[6]*b[2]) + (r[7]*b[3]));
		} else if(LevelOne.clouds[15] == cloud[2]) {	
			c1p.set(c[30] + (w/28f), c[31] + (h/15f), (r[8]*b[4]) + (r[9]*b[5]));
			c2p.set(c[30] + (w/19f), c[31] + (h/18.5f), (r[8]*b[4]) + (r[9]*b[5]));
			c3p.set(c[30] + (w/13f), c[31] + (h/14.5f), (r[10]*b[4]) + (r[11]*b[5]));
			c4p.set(c[30] + (w/21f), c[31] + (h/11.2f), (r[10]*b[4]) + (r[11]*b[5]));
		} else if(LevelOne.clouds[15] == cloud[3]) {	
			c1p.set(c[30] + (w/23f), c[31] + (h/19f), (r[12]*b[6]) + (r[13]*b[7]));
			c2p.set(c[30] + (w/15.3f), c[31] + (h/24.5f), (r[14]*b[6]) + (r[15]*b[7]));
			c3p.set(c[30] + (w/25f), c[31] + (h/12f), (r[14]*b[6]) + (r[15]*b[7]));
		} else if(LevelOne.clouds[15] == cloud[4]) {	
			c1p.set(c[30] + (w/10f), c[31] + (h/9.5f), (r[16]*b[8]) + (r[17]*b[9]));
			c2p.set(c[30] + (w/11f), c[31] + (h/7.3f), (r[18]*b[8]) + (r[19]*b[9]));
			c3p.set(c[30] + (w/14f), c[31] + (h/21f), (r[16]*b[8]) + (r[17]*b[9]));
			c4p.set(c[30] + (w/18.8f), c[31] + (h/6.6f), (r[18]*b[8]) + (r[19]*b[9]));
			c5p.set(c[30] + (w/11f), c[31] + (h/15.5f), (r[18]*b[8]) + (r[19]*b[9]));
			c6p.set(c[30] + (w/25.5f), c[31] + (h/14f), (r[20]*b[8]) + (r[21]*b[9]));
			c7p.set(c[30] + (w/24f), c[31] + (h/9f), (r[18]*b[8]) + (r[19]*b[9]));
		}
	}

	/*
	 * This method is responsible for joining all of the collisions together in one.
	 */
	public static void cloudBoundaries() {
		cloudOneCollision(); cloudTwoCollision(); cloudThreeCollision(); cloudFourCollision();
		cloudFiveCollision(); cloudSixCollision(); cloudSevenCollision(); cloudEightCollision();
		cloudNineCollision(); cloudTenCollision(); cloudElevenCollision(); cloudTwelveCollision();
		cloudThirteenCollision(); cloudFourteenCollision(); cloudFourteenCollision();
		cloudFifteenCollision(); cloudSixteenCollision();		
	}

	/*
	 * This method is responsible for calculating the collisions between the clouds and bird. Once the bird collides
	 * into a cloud it will return a Boolean statement of true. When it returns true it will launch a new method within
	 * the World class to act upon its functionality.
	 */
	public static boolean collision(BirdCharacter bird) {
		Circle b = bird.getbirdCircle();
		return (Intersector.overlaps(b, c1a) || Intersector.overlaps(b, c2a) || Intersector.overlaps(b, c3a) || Intersector.overlaps(b, c4a) || 
				Intersector.overlaps(b, c5a) || Intersector.overlaps(b, c6a) || Intersector.overlaps(b, c7a) ||
				Intersector.overlaps(b, c1b) || Intersector.overlaps(b, c2b) || Intersector.overlaps(b, c3b) || Intersector.overlaps(b, c4b) || 
				Intersector.overlaps(b, c5b) || Intersector.overlaps(b, c6b) || Intersector.overlaps(b, c7b) ||
				Intersector.overlaps(b, c1c) || Intersector.overlaps(b, c2c) || Intersector.overlaps(b, c3c) || Intersector.overlaps(b, c4c) || 
				Intersector.overlaps(b, c5c) || Intersector.overlaps(b, c6c) || Intersector.overlaps(b, c7c) ||
				Intersector.overlaps(b, c1d) || Intersector.overlaps(b, c2d) || Intersector.overlaps(b, c3d) || Intersector.overlaps(b, c4d) || 
				Intersector.overlaps(b, c5d) || Intersector.overlaps(b, c6d) || Intersector.overlaps(b, c7d) ||
				Intersector.overlaps(b, c1e) || Intersector.overlaps(b, c2e) || Intersector.overlaps(b, c3e) || Intersector.overlaps(b, c4e) || 
				Intersector.overlaps(b, c5e) || Intersector.overlaps(b, c6e) || Intersector.overlaps(b, c7e) ||
				Intersector.overlaps(b, c1f) || Intersector.overlaps(b, c2f) || Intersector.overlaps(b, c3f) || Intersector.overlaps(b, c4f) || 
				Intersector.overlaps(b, c5f) || Intersector.overlaps(b, c6f) || Intersector.overlaps(b, c7f) ||
				Intersector.overlaps(b, c1g) || Intersector.overlaps(b, c2g) || Intersector.overlaps(b, c3g) || Intersector.overlaps(b, c4g) || 
				Intersector.overlaps(b, c5g) || Intersector.overlaps(b, c6g) || Intersector.overlaps(b, c7g) ||
				Intersector.overlaps(b, c1h) || Intersector.overlaps(b, c2h) || Intersector.overlaps(b, c3h) || Intersector.overlaps(b, c4h) || 
				Intersector.overlaps(b, c5h) || Intersector.overlaps(b, c6h) || Intersector.overlaps(b, c7h) ||
				Intersector.overlaps(b, c1i) || Intersector.overlaps(b, c2i) || Intersector.overlaps(b, c3i) || Intersector.overlaps(b, c4i) || 
				Intersector.overlaps(b, c5i) || Intersector.overlaps(b, c6i) || Intersector.overlaps(b, c7i) ||
				Intersector.overlaps(b, c1j) || Intersector.overlaps(b, c2j) || Intersector.overlaps(b, c3j) || Intersector.overlaps(b, c4j) || 
				Intersector.overlaps(b, c5j) || Intersector.overlaps(b, c6j) || Intersector.overlaps(b, c7j) ||
				Intersector.overlaps(b, c1k) || Intersector.overlaps(b, c2k) || Intersector.overlaps(b, c3k) || Intersector.overlaps(b, c4k) || 
				Intersector.overlaps(b, c5k) || Intersector.overlaps(b, c6k) || Intersector.overlaps(b, c7k) ||
				Intersector.overlaps(b, c1l) || Intersector.overlaps(b, c2l) || Intersector.overlaps(b, c3l) || Intersector.overlaps(b, c4l) || 
				Intersector.overlaps(b, c5l) || Intersector.overlaps(b, c6l) || Intersector.overlaps(b, c7l) ||
				Intersector.overlaps(b, c1m) || Intersector.overlaps(b, c2m) || Intersector.overlaps(b, c3m) || Intersector.overlaps(b, c4m) || 
				Intersector.overlaps(b, c5m) || Intersector.overlaps(b, c6m) || Intersector.overlaps(b, c7m) ||
				Intersector.overlaps(b, c1n) || Intersector.overlaps(b, c2n) || Intersector.overlaps(b, c3n) || Intersector.overlaps(b, c4n) || 
				Intersector.overlaps(b, c5n) || Intersector.overlaps(b, c6n) || Intersector.overlaps(b, c7n) ||
				Intersector.overlaps(b, c1o) || Intersector.overlaps(b, c2o) || Intersector.overlaps(b, c3o) || Intersector.overlaps(b, c4o) || 
				Intersector.overlaps(b, c5o) || Intersector.overlaps(b, c6o) || Intersector.overlaps(b, c7o) ||
				Intersector.overlaps(b, c1p) || Intersector.overlaps(b, c2p) || Intersector.overlaps(b, c3p) || Intersector.overlaps(b, c4p) || 
				Intersector.overlaps(b, c5p) || Intersector.overlaps(b, c6p) || Intersector.overlaps(b, c7p));
	}

	/*
	 * This method gets called within the World class to reset all of the 
	 * clouds positions and scrollspeed.
	 */
	public void onRestart(float x, float y, float scrollSpeed) {
		position.x = x;
		position.y = y;
		velocity.x = scrollSpeed;
	}		
}
