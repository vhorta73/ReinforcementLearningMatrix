package environment.tests;

import org.junit.Before;
import org.junit.Test;

import environment.impl.GraphicalDisplayImpl;
import environment.interfaces.GraphicalDisplay;
import environment.interfaces.Pixel;

/**
 * Unit tests for the EnvironmentGraphics implementation.
 * 
 * @author Vasco
 *
 */
public class TestGraphicalDisplay {
	/**
	 * The EnvironmentGraphics object handler.
	 */
    private GraphicalDisplay environmentGraphics;
    
    @Before
    public void before() {
    	environmentGraphics = new GraphicalDisplayImpl(new Pixel[10][]);
    }
    
    @Test
    public void testShow() {
    	environmentGraphics.show();
    }
    
    @Test
    public void testHide() {
    	environmentGraphics.hide();
    }
    
    @Test
    public void testRender() {
    	environmentGraphics.render();
    }
    
}
