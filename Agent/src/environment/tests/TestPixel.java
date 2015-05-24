package environment.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import environment.impl.PixelImpl;
import environment.interfaces.Pixel;

/**
 * Testing Pixel implementation.
 * 
 * @author Vasco
 *
 */
public class TestPixel {
    private Pixel pixel;
    private Integer R = 12;
    private Integer B = 21;
    private Integer G = 231;
    private boolean BLOCKED = true;
    private Double REWARD = 100.0;

    @Before
    public void before() {
        pixel = new PixelImpl(R, G, B, BLOCKED, REWARD);
    }

    /**
     * Testing red out of bounds exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRedOutOfBounds() {
        pixel = new PixelImpl(256, G, B, BLOCKED, REWARD);
    }

    /**
     * Testing green out of bounds exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testGreedOutOfBounds() {
        pixel = new PixelImpl(R, 256, B, BLOCKED, REWARD);
    }

    /**
     * Testing blue out of bounds exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBlueOutOfBounds() {
        pixel = new PixelImpl(R, G, 256, BLOCKED, REWARD);
    }

    /**
     * Testing null Blocked exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullBlockedException() {
        pixel = new PixelImpl(R, G, B, null, REWARD);
    }

    /**
     * Testing null reward exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullRewardException() {
        pixel = new PixelImpl(R, G, B, BLOCKED, null);
    }

    /**
     * Testing red.
     */
    @Test
    public void testRed() {
        assertEquals(R, pixel.getR());
    }

    /**
     * Testing green.
     */
    @Test
    public void testGreen() {
        assertEquals(G, pixel.getG());
    }

    /**
     * Testing blue.
     */
    @Test
    public void testBlue() {
        assertEquals(B, pixel.getB());
    }

    /**
     * Testing blocked.
     */
    @Test
    public void testBlocked() {
        assertEquals(true, pixel.isBlocked());
    }

    /**
     * Testing not blocked.
     */
    @Test
    public void testNotBlocked() {
        pixel = new PixelImpl(R, G, B, false, REWARD);
        assertEquals(false, pixel.isBlocked());
    }

    /**
     * Testing Reward.
     */
    @Test
    public void testReward() {
        assertEquals(REWARD, pixel.getReward());
    }

    /**
     * Testing no reward.
     */
    @Test
    public void testNoReward() {
        pixel = new PixelImpl(R, G, B, true, 0.0);
    }
}