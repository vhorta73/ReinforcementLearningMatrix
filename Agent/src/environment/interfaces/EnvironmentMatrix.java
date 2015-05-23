package environment.interfaces;

import agent.interfaces.Vision;

/**
 * EnvironmentMatrix interface.
 * 
 * The matrix is a fully mapped image of the grid world in RGB.
 * Logic around states and actions will depend on what the grid
 * is showing, thus enabling the setting of obstacles agents cannot
 * go into, limiting actions in some states, etc...
 * 
 * This matrix is meant to be flexible enough to allow the setting
 * of different values for different purposes. 
 * 
 * @author Vasco
 *
 */
public interface EnvironmentMatrix {
	/**
	 * Get a Pixel from the Matrix corresponding to the given x,y coordinates.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return Pixel 
	 */
	public Pixel get(Integer x, Integer y);

	/**
	 * The Vision of the matrix seen from coordinates x,y with given radius.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param radius
	 * @return Vision
	 */
	public Vision getVision(Integer x, Integer y, Integer radius);
}
