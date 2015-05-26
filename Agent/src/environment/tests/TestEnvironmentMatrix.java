package environment.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.interfaces.Vision;
import environment.impl.EnvironmentMatrixImpl;
import environment.impl.PixelImpl;
import environment.impl.PositionImpl;
import environment.interfaces.EnvironmentMatrix;
import environment.interfaces.Pixel;
import environment.interfaces.Position;

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
     * Matrix width.
     */
    private final Integer WIDTH = 60;
    
    /**
     * Matrix height
     */
    private final Integer HEIGHT = 40;

    /**
     * Default position
     */
    private Position position;
    
    /**
     * Generate a new set of values for the matrix every time.
     */
    @Before
    public void before() {
        position = new PositionImpl(0,0);
        matrix = new Pixel[WIDTH][HEIGHT];
        for(int x = 0; x < WIDTH; x++) {
            for( int y = 0; y < HEIGHT; y++ ) {
                int r = (int) ( Math.random() * 255 );
                int g = (int) ( Math.random() * 255 );
                int b = (int) ( Math.random() * 255 );
                boolean blocking = (boolean) ( Math.random() > 0.1 );
                Double reward = (double) ( Math.random() > 0.1 ? 100 : 0 );
                matrix[x][y] = new PixelImpl(r, g, b, blocking, reward);
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
        int xCenterCoordinate = WIDTH / 2;
        int yCenterCoordinate = HEIGHT / 2;
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
        Pixel foundPixel = environmentMatrix.getPixel(3, 4);
        assertNotNull(foundPixel);
        assertEquals(foundPixel.getR(), matrix[3][4].getR());
        assertEquals(foundPixel.getG(), matrix[3][4].getG());
        assertEquals(foundPixel.getB(), matrix[3][4].getB());
    }

    /**
     * Test out of bounds.
     */
    @Test
    public void testOutOfBoundsXNeg() {
        // Bounded matrix expects RGB to be zero and all blocked
        Pixel pixelFound = environmentMatrix.getPixel(-1, 0);
        Pixel pixelExpected = new PixelImpl(0, 0, 0, true, 0.0);
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds.
     */
    @Test
    public void testOutOfBoundsXPos() {
        // Bounded matrix expects RGB to be zero and all blocked
        Pixel pixelFound = environmentMatrix.getPixel(WIDTH, 0);
        Pixel pixelExpected = new PixelImpl(0, 0, 0, true, 0.0);
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds.
     */
    @Test
    public void testOutOfBoundsYNeg() {
        // Bounded matrix expects RGB to be zero and all blocked
        Pixel pixelFound = environmentMatrix.getPixel(0, -1);
        Pixel pixelExpected = new PixelImpl(0, 0, 0, true, 0.0);
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds.
     */
    @Test
    public void testOutOfBoundsYPos() {
        // Bounded matrix expects RGB to be zero and all blocked
        Pixel pixelFound = environmentMatrix.getPixel(0, HEIGHT);
        Pixel pixelExpected = new PixelImpl(0, 0, 0, true, 0.0);
        verify(pixelExpected, pixelFound);
    }
    
    /**
     * Test out of bounds with unbounded.
     */
    @Test
    public void testOutOfBoundsUnboundedXNeg() {
        // Unbounded matrix
        environmentMatrix = new EnvironmentMatrixImpl(matrix, true);
        Pixel pixelFound = environmentMatrix.getPixel(-1, 0);
        Pixel pixelExpected = matrix[WIDTH-1][0];
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds unbounded.
     */
    @Test
    public void testOutOfBoundsUnboundedXPos() {
        // Unbounded matrix
        environmentMatrix = new EnvironmentMatrixImpl(matrix, true);
        Pixel pixelFound = environmentMatrix.getPixel(WIDTH, 0);
        Pixel pixelExpected = matrix[0][0];
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds unbounded.
     */
    @Test
    public void testOutOfBoundsUnboudedYNeg() {
        // Unbounded matrix
        environmentMatrix = new EnvironmentMatrixImpl(matrix, true);
        Pixel pixelFound = environmentMatrix.getPixel(0, -1);
        Pixel pixelExpected = matrix[0][HEIGHT-1];
        verify(pixelExpected, pixelFound);
    }

    /**
     * Test out of bounds unbounded.
     */
    @Test
    public void testOutOfBoundsUnboundedYPos() {
        // Unbounded matrix
        environmentMatrix = new EnvironmentMatrixImpl(matrix, true);
        Pixel pixelFound = environmentMatrix.getPixel(0, HEIGHT);
        Pixel pixelExpected = matrix[0][0];
        verify(pixelExpected, pixelFound);
    }
    
    /**
     * Test null action exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testApplyActionNullAction() {
        environmentMatrix.applyAction(null, position);
    }

    /**
     * Test null position exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testApplyActionNullPosition() {
        environmentMatrix.applyAction(Action.DOWN, null);
    }

    /**
     * Test new Position.
     */
    @Test
    public void testApplyAction() {
        Position newPositionFound = environmentMatrix.applyAction(Action.DOWN, position);
        assertNotNull(newPositionFound);
        assertEquals(newPositionFound.getX(),position.getX());
        assertEquals(newPositionFound.getY(), position.getY()+1);
    }
    
    /**
     * Verify if expected and found pixel are equal.
     * 
     * @param expected Pixel
     * @param found Pixel
     */
    private void verify(Pixel expected, Pixel found) {
        assertNotNull(expected);
        assertNotNull(found);
        assertEquals(expected.getR(),found.getR());
        assertEquals(expected.getG(),found.getG());
        assertEquals(expected.getB(),found.getB());
        assertEquals(expected.isBlocked(),found.isBlocked());
        assertEquals(expected.getReward(),found.getReward());
    }
}