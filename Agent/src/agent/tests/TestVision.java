package agent.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agent.impl.VisionImpl;
import agent.interfaces.Vision;

/**
 * Testing Vision implementation.
 * 
 * @author Vasco
 *
 */
public class TestVision {
	/**
	 * The vision object handler.
	 */
	private Vision vision;
	
	/**
	 * The testing vision matrix.
	 */
	private Integer[][][] vision3D;

	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		vision3D = new Integer[10][10][3];
		for(int x = 0; x < vision3D.length; x++ ) {
			for( int y = 0; y < vision3D[x].length; y++ ) {
				for( int z = 0; z < vision3D[x][y].length; z++ ) {
					vision3D[x][y][z] = (int) Math.random()*255;
				}
			}
		}

		vision = new VisionImpl(vision3D);
	}

	/**
	 * Testing null vision exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullVision() {
		new VisionImpl(null);
	}

	/**
	 * Testing null vision equals exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullEquals() {
		vision.equals(null);
	}

	/**
	 * Testing equal vision.
	 */
	@Test
	public void testEquals() {
		assertTrue(vision.equals(new VisionImpl(vision3D)));
	}
}
