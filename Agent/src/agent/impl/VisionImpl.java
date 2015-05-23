package agent.impl;

import agent.interfaces.Vision;

/**
 * The Vision implementation.
 * 
 * @author Vasco
 *
 */
public class VisionImpl implements Vision {
	/**
	 * The vision matrix.
	 */
	private final Integer[][][] vision;
	
	/**
	 * Constructor.
	 * 
	 * @param vision 2D matrix with RGB values.
	 */
	public VisionImpl(Integer[][][] vision) {
		if (vision == null) throw new IllegalArgumentException("Vision cannot be null.");
		this.vision = vision;
	}

	/**
	 * Check for equality between this and another vision.
	 */
	@Override
	public boolean equals(Vision visionMatrix) {
		// Null is not an option and is best to throw and exception about it.
		if ( visionMatrix == null ) throw new IllegalArgumentException("Vision cannot be null.");

		// Get the actual vision to check against this vision.
		Integer[][][] vision1 = visionMatrix.getVision();

		// It is expected for vision to be in the form of a matrix.
		if ( vision1.length != this.vision.length ) return false;
		if ( vision1[0].length != this.vision[0].length ) return false;
		if ( vision1[0][0].length != this.vision[0][0].length ) return false;

		// Check and compare each value of the matrices.
		for( int x = 0; x < this.vision.length; x++ ) {
			for( int y = 0; y < this.vision[x].length; y++ ) {
				for( int z = 0; z < this.vision[x][y].length; z++ ) {
					if ( vision1[x][y][z] != this.vision[x][y][z] ) return false;
				}
			}
		}
		
		// If nothing failed, these are equal.
		return true;
	}

	/**
	 * Return the vision 2D matrix.
	 */
	@Override
	public Integer[][][] getVision() {
		return vision;
	}
}
