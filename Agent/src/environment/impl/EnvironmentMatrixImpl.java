package environment.impl;

import agent.impl.VisionImpl;
import agent.interfaces.Vision;
import environment.interfaces.EnvironmentMatrix;
import environment.interfaces.Pixel;

/**
 * EnvironmentMatrix implementation.
 * 
 * @author Vasco
 *
 */
public class EnvironmentMatrixImpl implements EnvironmentMatrix {
	/**
	 * The matrix.
	 */
	private final Pixel[][] matrix;

	/**
	 * Constructor.
	 * 
	 * @param matrix
	 */
	public EnvironmentMatrixImpl(Pixel[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * The pixel at given coordinates.
	 */
	@Override
	public Pixel get(Integer x, Integer y) {
		if ( x < 0 | x > matrix.length ) 
			throw new IllegalArgumentException("x coordinate out of bounds: " + x);
		if ( y < 0 | y > matrix[0].length ) 
			throw new IllegalArgumentException("y coordinate out of bounds: " + y);
		return matrix[x][y];
	}

	/**
	 * Get the vision from x,y with given radius.
	 */
	@Override
	public Vision getVision(Integer x, Integer y, Integer radius) {
		if ( (x - radius) < 0 | (x + radius) > matrix.length ) 
			throw new IllegalArgumentException("X out of bounds for given radius.");
		if ( (y - radius) < 0 | (y + radius) > matrix[0].length ) 
			throw new IllegalArgumentException("Y out of bounds for given radius.");

		Integer diameter = radius + radius + 1;
		Integer[][][] vis = new Integer[diameter][diameter][3];
		int xxx = 0;
		for(int xx = x-radius; xx < x+radius+1; xx++) {
			int yyy = 0;
			for(int yy = y-radius; yy < y+radius+1; yy++) {
				int r = matrix[xx][yy].getR();
				int g = matrix[xx][yy].getG();
				int b = matrix[xx][yy].getB();
				vis[xxx][yyy] = new Integer[]{r, g, b};
				yyy++;
			}
			xxx++;	
		}
		return new VisionImpl(vis);
	}
}