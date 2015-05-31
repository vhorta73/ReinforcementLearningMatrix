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
        // The amount of time in milliseconds a frame should takes.
        Long millisPerFrame = ( 1000L / FRAMES_PER_SECOND );

        // The amount of sleep needed to compensate.
        Long sleepingTime = millisPerFrame;

        while(!shutdown) {
            // The loop starting time.
            Long start = System.currentTimeMillis();

            // The compensating sleep.
            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // TODO clean up this code and build the display here.
            System.out.print(".");

            // The end of the loop.
            Long end = System.currentTimeMillis();

            // The time taken on this loop.
            Long diffMillis = end - start;

            // Calculate the next sleeping time to compensate any elapsed or delay taken.
            sleepingTime = 
                      ( diffMillis > millisPerFrame ) 
                    ? ( diffMillis - millisPerFrame ) 
                    : ( millisPerFrame - diffMillis );
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
