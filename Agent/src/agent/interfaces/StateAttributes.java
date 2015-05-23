package agent.interfaces;
/**
 * The State attributes given by the environment.
 * 
 * A State can contain multiple attributes in type and value,
 * like a state with a matrix for an image seen or a sound
 * played, or something tasted our touched and returned as a
 * value.
 * 
 * These attributes are a property of a State, to be used by
 * the Agent to then calculate which value these should reflect
 * and then correlate with previous states.
 * 
 * A similar image may receive similar state value, but the state
 * may dramatically change if the sound is too loud or any at all.
 * e.g.: An image of a house may reflect the state "at home", but
 * sound that goes with it, may reflect the state "party" at home.
 * 
 * 
 * @author Vasco
 *
 */
public interface StateAttributes {
	/**
	 * The default attribute value.
	 * 
	 * @return Integer value.
	 */
	public Integer getValue();
	
	/**
	 * The Vision State attribute.
	 * 
	 * @return Vision
	 */
	public Vision getVision();
}
