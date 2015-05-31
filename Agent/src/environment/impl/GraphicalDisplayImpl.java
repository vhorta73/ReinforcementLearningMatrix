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
    // TODO
    private boolean show = false;
    // TODO
    private Pixel[][] matrix;

    /**
     * The shutdown switch.
     */
    private Boolean shutdown = false;

    // TODO
    private final Integer FRAMES_PER_SECOND = 60;

    /**
     * Constructor.
     * 
     * @param pixelMatrix 2D
     */
    public GraphicalDisplayImpl(Pixel[][] pixelMatrix) {
        // Validate arguments.
        if ( pixelMatrix == null ) throw new IllegalArgumentException("Matrix cannot be null.");

        this.matrix = pixelMatrix;
    }

    /**
     * Display the matrix.
     */
    @Override
    public void show() {
        this.show = true;
    }

    /**
     * Hide the matrix.
     */
    @Override
    public void hide() {
        this.show = false;
    }

    /**
     * Render new image.
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
    }

    /**
     * The runnable.
     */
    @Override
    public void run() {
        while(!shutdown) {
            try {
                Thread.sleep(FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // TODO clean up this code.
            System.out.print(".");
        }
    }

    /**
     * Set this thread to exit.
     */
    @Override
    public void shutdown() {
        this.shutdown = true;
    }
}
