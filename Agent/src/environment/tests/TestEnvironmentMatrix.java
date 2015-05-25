package environment.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import agent.interfaces.Vision;
import environment.impl.EnvironmentMatrixImpl;
import environment.impl.PixelImpl;
import environment.interfaces.EnvironmentMatrix;
import environment.interfaces.Pixel;

/**
 * EnvironmentMatrx tests.
 * 
 * @author Vasco
 *
 */
public class TestEnvironmentMatrix {
	/**
	 * The EnvironmentMatrix handler.
	 */
	private EnvironmentMatrix environmentMatrix;

	/**
	 * The actual matrix.
	 */
	private Pixel[][] matrix;
	
	/**
	 * Round matrix flag.
	 */
	private final Boolean ROUND_MATRIX = false;

	/**
	 * Generate a new set of values for the matrix every time.
	 */
	@Before
	public void before() {
		matrix = new Pixel[60][40];
		for(int x = 0; x < 60; x++) {
			for( int y = 0; y < 40; y++ ) {
				int r = (int) ( Math.random() * 255 );
				int g = (int) ( Math.random() * 255 );
				int b = (int) ( Math.random() * 255 );
				boolean blocking = (boolean) ( Math.random() > 0.1 );
				Double reward = (double) ( Math.random() > 0.1 ? 100 : 0 );
				Pixel pixel = new PixelImpl(r, g, b, blocking, reward);
				matrix[x][y] = pixel;
			}
		}
		environmentMatrix = new EnvironmentMatrixImpl(matrix, ROUND_MATRIX);
	}

	/**
	 * Test vision if returned vision by the environmentMatrix matches
	 * the initial matrix values, on same positioned coordinates.
	 */
	@Test
	public void testVision() {
		int xCenterCoordinate = 50;
		int yCenterCoordinate = 30;
		int radius            = 3;
		int diameter          = radius + radius + 1;

		Vision foundVision = environmentMatrix.getVision(xCenterCoordinate, yCenterCoordinate, radius);
		assertNotNull(foundVision);

		Integer[][][] integerMatrix = foundVision.getVision();
		assertNotNull(integerMatrix);
		assertEquals(diameter, integerMatrix.length);
		assertEquals(diameter, integerMatrix[0].length);

		for(int visionX = 0; visionX < integerMatrix.length; visionX++) {
			for(int visionY = 0; visionY < integerMatrix[0].length; visionY++) {
				assertEquals(integerMatrix[visionX][visionY][0], 
						matrix[xCenterCoordinate-radius+visionX][yCenterCoordinate-radius+visionY].getR());
				assertEquals(integerMatrix[visionX][visionY][1], 
						matrix[xCenterCoordinate-radius+visionX][yCenterCoordinate-radius+visionY].getG());
				assertEquals(integerMatrix[visionX][visionY][2], 
						matrix[xCenterCoordinate-radius+visionX][yCenterCoordinate-radius+visionY].getB());
			}
		}
	}
	
	/**
	 * Test pixel match.
	 */
	@Test
	public void testPixelMatch() {
		Pixel foundPixel = environmentMatrix.get(3, 4);
		assertNotNull(foundPixel);
		assertEquals(foundPixel.getR(), matrix[3][4].getR());
		assertEquals(foundPixel.getG(), matrix[3][4].getG());
		assertEquals(foundPixel.getB(), matrix[3][4].getB());
	}

	/**
	 * Test out of bounds.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testOutOfBoundsXNeg() {
		environmentMatrix.get(-1, 0);
	}

	/**
	 * Test out of bounds.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testOutOfBoundsXPos() {
		environmentMatrix.get(61, 0);
	}

	/**
	 * Test out of bounds.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testOutOfBoundsYNeg() {
		environmentMatrix.get(0, -1);
	}

	/**
	 * Test out of bounds.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testOutOfBoundsYPos() {
		environmentMatrix.get(0, 41);
	}
}