package environment.impl;

import environment.interfaces.Position;

/**
 * The Position implementation.
 * 
 * @author Vasco
 *
 */
public class PositionImpl implements Position {
	/**
	 * The X coordinate.
	 */
	private final int x;
	
	/**
	 * The y coordinate.
	 */
	private final int y;
	
	/**
	 * Constructor.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 */
	public PositionImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * The x coordinate.
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * The y coordinate.
	 */
	@Override
	public int getY() {
		return y;
	}

}
