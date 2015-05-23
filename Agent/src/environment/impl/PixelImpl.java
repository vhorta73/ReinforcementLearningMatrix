package environment.impl;

import environment.interfaces.Pixel;

/**
 * Pixel implementation.
 * 
 * @author Vasco
 *
 */
public class PixelImpl implements Pixel {
	/**
	 * The red.
	 */
	private final Integer red;
	
	/**
	 * The green.
	 */
	private final Integer green;
	
	/**
	 * The blue.
	 */
	private final Integer blue;
	
	/**
	 * The blocked flag.
	 */
	private final Boolean blocked;
	
	/**
	 * The reward value.
	 */
	private final Double reward;

	/**
	 * Constructor.
	 */
	public PixelImpl(Integer red, Integer green, Integer blue, Boolean blocked, Double reward) {
		if ( red == null ) throw new IllegalArgumentException("Red cannot be null.");
		if ( green == null ) throw new IllegalArgumentException("Green cannot be null.");
		if ( blue == null ) throw new IllegalArgumentException("Blue cannot be null.");
		if ( blocked == null ) throw new IllegalArgumentException("Blocked cannot be null.");
		if ( reward == null ) throw new IllegalArgumentException("Reward cannot be null.");

		if ( red < 0 | red > 255 ) throw new IllegalArgumentException("Red colour out of bounds.");
		if ( green < 0 | green > 255 ) throw new IllegalArgumentException("Green colour out of bounds.");
		if ( blue < 0 | blue > 255 ) throw new IllegalArgumentException("Blue colour out of bounds.");
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.blocked = blocked;
		this.reward = reward;
    }

	/**
	 * The red colour.
	 */
	@Override
	public Integer getR() {
		return red;
	}

	/**
	 * The green colour.
	 */
	@Override
	public Integer getG() {
		return green;
	}

	/**
	 * The blue colour.
	 */
	@Override
	public Integer getB() {
		return blue;
	}

	/**
	 * Position state if blocked.
	 */
	@Override
	public Boolean isBlocked() {
		return blocked;
	}

	/**
	 * The reward.
	 */
	@Override
	public Double reward() {
		return reward;
	}
}