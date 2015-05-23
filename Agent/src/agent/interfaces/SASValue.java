package agent.interfaces;
/**
 * The State-Action-State value.
 * 
 * @author Vasco
 *
 */
public interface SASValue {
	/**
	 * The value for the state-action-state.
	 * 
	 * @return double value
	 */
	public Double getValue();
		
	/**
	 * Add a new sample.
	 * 
	 * @param value double
	 */
	public void add(Double value);
	
	/**
	 * The total amount of samples added so far.
	 * 
	 * @return amount of samples.
	 */
	public int getTotalSampleCount();
	
	/**
	 * The averaging sample to be used on all calculations.
	 * 
	 * @param averagingSample
	 */
	public void setAveragingSample(int averagingSample);
	
	/**
	 * The averaging sample currently being used.
	 * 
	 * @return the averaging sample.
	 */
	public int getAveragingSample();
}
