package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import agent.impl.SASValueImpl;
import agent.interfaces.SASValue;

/**
 * Testing SASValue implementation.
 * 
 * @author Vasco
 *
 */
public class TestSASValue {
	/**
	 * The SASValur handler.
	 */
	private SASValue sasValue;

	/**
	 * The expected default averaging sample amount.
	 */
	private int DEFAULT_AVERAGING_SAMPLE = 100;

	/**
	 * Test initialisations.
	 */
	@Before
	public void before() {
	    sasValue = new SASValueImpl();
	}

	/**
	 * Test if default averaging sample matches expectations.
	 */
	@Test
	public void testDefaultAveragingSample() {
		int foundAveragingSample = sasValue.getAveragingSample();
		assertNotNull(foundAveragingSample);
		assertEquals(DEFAULT_AVERAGING_SAMPLE, foundAveragingSample);
	}
	
	/**
	 * Test if the averaging sample can be set.
	 */
	@Test
	public void testAveragingSampleSetting() {
		sasValue.setAveragingSample(12);
		int foundAveraginSample = sasValue.getAveragingSample();
		assertNotNull(foundAveraginSample);
		assertEquals(12, foundAveraginSample);
	}
	
	/**
	 * Test exception on negative averaging sample.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptioOnNegativeSample() {
		sasValue.setAveragingSample(-1);
	}

	/**
	 * Test the total sample count.
	 */
	@Test
	public void testSampleCount() {
		sasValue.add(1.0);
		assertEquals(1, sasValue.getTotalSampleCount());
		sasValue.add(1.30);
		assertEquals(2, sasValue.getTotalSampleCount());
	}
	
	/**
	 * Test the expected value is returned.
	 */
	@Test
	public void testValueSample() {
		verifyAddingValue(1.0);
		verifyAddingValue(1.0);
	}
	
	/**
	 * Test the expected value returned post averaging samples.
	 */
	@Test
	public void testPostAveragingSamples() {
		int testSample = 3;
		
		// Setting the averaging sample
		sasValue.setAveragingSample(testSample);
		// Ensure it was set
		assertEquals(testSample, sasValue.getAveragingSample());
		
		// Adding values and checking if correct
		verifyAddingValue(1.3);
		verifyAddingValue(1.6);
		verifyAddingValue(5.6);
		verifyAddingValue(-1.6);
	}
	
	/**
	 * Adding and verifying the results of adding a new value.
	 * 
	 * @param value to be added
	 */
	private void verifyAddingValue(Double value) {
		Double previousValue = sasValue.getValue();
		sasValue.add(value);
		Double foundValue = sasValue.getValue();
		assertNotNull(foundValue);
		Double expected = ( previousValue * (sasValue.getAveragingSample() - 1) + value ) / sasValue.getAveragingSample();
		assertEquals(expected, foundValue);
	}
}
