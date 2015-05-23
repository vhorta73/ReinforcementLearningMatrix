package agent.interfaces;
/**
 * The Vision interface.
 * 
 * @author Vasco
 *
 */
public interface Vision {
	/**
	 * Check if this Vision is equal to another vision supplied.
	 * 
	 * @param vision
	 * @return true if equal
	 */
	public boolean equals(Vision vision);
	
	/**
	 * The Vision 3D integer array matrix of RGB values.
	 * 
	 * @return Integer[][][]
	 */
	public Integer[][][] getVision();
}
