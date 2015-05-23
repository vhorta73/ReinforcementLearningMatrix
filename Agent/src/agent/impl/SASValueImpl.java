package agent.impl;

import agent.interfaces.SASValue;

/**
 * The SASValue implementation.
 * 
 * @author Vasco
 *
 */
public class SASValueImpl implements SASValue {
	/**
	 * The moving average value.
	 */
	private Double value;
	
	/**
	 * Total samples received so far.
	 */
	private int totalSamples;
	
	/**
	 * The averaging sample to be used.
	 */
	private int averagingSample = 100;

	/**
	 * Constructor.
	 */
	public SASValueImpl() {
		this.totalSamples = 0;
		this.value = 0.0;
	}

	/**
	 * The averaged value.
	 */
	@Override
	public Double getValue() {
		return value;
	}

	/**
	 * Add a new sample value.
	 */
	@Override
	public void add(Double value) {
		this.value = ( this.value * ( averagingSample - 1 ) + value ) / averagingSample;
		this.totalSamples++;
	}

	/**
	 * The total sample count.
	 */
	@Override
	public int getTotalSampleCount() {
		return totalSamples;
	}

	/**
	 * Setting the averaging sample.
	 */
	@Override
	public void setAveragingSample(int averagingSample) {
		if ( averagingSample < 0 ) throw new IllegalArgumentException("Cannot set a negative averaging sample.");
		this.averagingSample = averagingSample;
	}

	/**
	 * The currently used averaging sample.
	 */
	@Override
	public int getAveragingSample() {
		return averagingSample;
	}
}
