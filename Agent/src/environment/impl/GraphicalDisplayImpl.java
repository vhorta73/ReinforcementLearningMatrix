package environment.impl;

import environment.interfaces.GraphicalDisplay;
import environment.interfaces.Pixel;

/**
 * The EnvironmentGraphics implementation.
 * 
 * @author Vasco
 *
 */
public class GraphicalDisplayImpl implements GraphicalDisplay {
	private boolean show = false;
	private Pixel[][] matrix;

	public GraphicalDisplayImpl(Pixel[][] environmentMatrix) {
		if ( environmentMatrix == null ) throw new IllegalArgumentException("Matrix cannot be null.");
		this.matrix = environmentMatrix;
	}

	@Override
	public void show() {
		this.show = true;
	}

	@Override
	public void hide() {
		this.show = false;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}
}
