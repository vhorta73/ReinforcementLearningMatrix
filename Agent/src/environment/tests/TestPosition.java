package environment.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import environment.impl.PositionImpl;
import environment.interfaces.Position;

/**
 * Unit tests for the Position implementation.
 * 
 * @author Vasco
 *
 */
public class TestPosition {
	/**
	 * The Position handler.
	 */
	private Position position;
	
	/**
	 * The x coordinate.
	 */
	private final int X = 2;
	
	/**
	 * The y coordinate.
	 */
	private final int Y = 3;
	
	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		position = new PositionImpl(X, Y);
	}
	
	/**
	 * Test if x coordinate was assigned.
	 */
	@Test
	public void testX() {
		int foundX = position.getX();
		assertEquals(X, foundX);
	}

	/**
	 * Test if y coordinate was assigned.
	 */
	@Test
	public void testY() {
		int foundY = position.getY();
		assertEquals(Y, foundY);
	}
}
