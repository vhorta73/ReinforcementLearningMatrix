package environment.interfaces;
/**
 * Pixel interface.
 * 
 * @author Vasco
 *
 */
public interface Pixel {
	/**
	 * The Red value.
	 * 
	 * @return Integer
	 */
	public Integer getR();
	
	/**
	 * The Green value.
	 * 
	 * @return Integer
	 */
	public Integer getG();
	
	/**
	 * The Blue value.
	 * 
	 * @return Integer 
	 */
	public Integer getB();

	/**
	 * If this is a blocking position.
	 * 
	 * @return True if blocking
	 */
	public Boolean isBlocked();

	/**
	 * The reward at this position.
	 * 
	 * @return Double
	 */
	public Double reward();
}
