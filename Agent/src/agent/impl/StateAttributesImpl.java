package agent.impl;

import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * The state attributes implementation.
 * 
 * @author Vasco
 *
 */
public class StateAttributesImpl implements StateAttributes {
	/**
	 * The calculated state value.
	 */
	private final Integer value;

	/**
	 * The vision.
	 */
	private final Vision vision;

	/**
	 * Constructor.
	 * 
	 * @param value state value
	 */
	public StateAttributesImpl(Integer value, Vision vision) {
		this.value = value;
		this.vision = vision;
	}
	
	/**
	 * The state attributes value.
	 */
	@Override
	public Integer getValue() {
		return value + vision.hashCode();
	}

	/**
	 * The Vision.
	 */
	@Override
	public Vision getVision() {
		return vision;
	}
}
