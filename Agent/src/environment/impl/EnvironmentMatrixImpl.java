package environment.impl;

import java.util.LinkedList;
import java.util.List;

import agent.constants.Action;
import agent.impl.VisionImpl;
import agent.interfaces.Vision;
import environment.interfaces.ActionProcess;
import environment.interfaces.EnvironmentMatrix;
import environment.interfaces.Pixel;
import environment.interfaces.Position;

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
     * Flag to tell if the matrix edges touch.
     */
    private final boolean roundMatrix;
    
    /**
     * The default out of range pixel.
     */
    private final Pixel OUT_OF_RANGE_PIXEL = new PixelImpl(0, 0, 0, true, 0.0);
    
    /**
     * The Action Matrix Process to convert any Position to the expected
     * after given action is applied.
     */
    private final ActionProcess<Position> actionProcess = new ActionMatrixProcess();

    /**
     * Constructor.
     * 
     * @param matrix
     */
    public EnvironmentMatrixImpl(Pixel[][] matrix, boolean roundMatrix) {
        // Validate parameters.
        if ( matrix == null ) throw new IllegalArgumentException("Matrix cannot be null.");
        this.matrix = matrix;
        this.roundMatrix = roundMatrix;
    }

    /**
     * The pixel at given coordinates.
     * If the coordinates are not possible, it will return null. 
     */
    @Override
    public Pixel getPixel(Integer x, Integer y) {
        // If matrix is not bounded
        if ( roundMatrix ) {
            Position unboundedPosition = getUnbounded(x, y);
            int uX = unboundedPosition.getX();
            int uY = unboundedPosition.getY();
            return matrix[uX][uY];
        }
        // If matrix is bounded
        else {
            if ( x < 0 || x >= matrix.length ) return OUT_OF_RANGE_PIXEL;
            if ( y < 0 || y >= matrix[0].length ) return OUT_OF_RANGE_PIXEL;
            return matrix[x][y];
        }
    }

    /**
     * Get the vision from x,y with given radius.
     */
    @Override
    public Vision getVision(Integer x, Integer y, Integer radius) {
        // Calculate the diameter for this vision
        Integer diameter = radius + radius + 1;
        // Allocate the vision matrix size
        Integer[][][] vis = new Integer[diameter][diameter][3];

        int visionX = 0;
        for(int xx = x-radius; xx < x+radius+1; xx++) {
            int visionY = 0;
            for(int yy = y-radius; yy < y+radius+1; yy++) {
                Pixel pixel = getPixel(xx,yy);
                int r = pixel.getR();
                int g = pixel.getG();
                int b = pixel.getB();
                vis[visionX][visionY] = new Integer[]{r, g, b};
                visionY++;
            }
            visionX++;    
        }
        return new VisionImpl(vis);
    }
    
    /**
     * Gathering all possible actions set by the environment at given position.
     * 
     * @param position
     * @return Action List
     */
    @Override
    public List<Action> getPossibleActions(Position position) {
        List<Action> actionList = new LinkedList<Action>();

        // Go over all possible actions in the book and check which ones
        // are possible to be done from this given position
        for ( Action action : Action.values() ) {

            // Do all the checks if this action can be performed on this position.
            if ( !allowedAction(action, position) ) continue;

            // Add current action as valid.
            actionList.add(action);
        }

        if ( actionList.size() == 0 ) 
            // TODO: get a parser to throw this error then, not at run time :)
            throw new IllegalStateException("Ops.. Environment found a position with no actions: ["+position.getX()+","+position.getY()+"].");
        return actionList;
    }

    /**
     * Checks if the new converted position is valid and thus ok to apply given action.
     * 
     * @param action given
     * @param position initial
     * @return true if action is ok
     */
    private boolean allowedAction(Action action, Position position) {
        // Validate arguments
        if ( action == null ) throw new IllegalArgumentException("Action cannot be null.");
        if ( position == null ) throw new IllegalArgumentException("Position cannot be null.");

        // Apply requested action
        Position newPosition = actionProcess.execute(action, position);
        
        // Get x and y coordinates.
        int x = newPosition.getX();
        int y = newPosition.getY();

        // Unbounded matrices, have no boundary limitations.
        if ( ! roundMatrix ) {
            if ( x < 0 || x >= matrix.length ) return false;
            if ( y < 0 || y >= matrix[0].length ) return false;
        }

        // Check if the final pixel is blocked.
        if ( getPixel(x,y).isBlocked() ) return false;

        // If no more excuses...
        return true;
    }

    /**
     * Converting initial given Position to a new one via the Action supplied.
     * 
     * @param action to apply
     * @param position to convert
     * @return Position converted
     */
    @Override
    public Position applyAction(Action action, Position position) {
        // Validate arguments
        if ( action == null ) throw new IllegalArgumentException("Action cannot be null.");
        if ( position == null ) throw new IllegalArgumentException("Position cannot be null.");

        // Apply requested action
        Position newPosition = actionProcess.execute(action, position);

        // Get x and y coordinates.
        int x = newPosition.getX();
        int y = newPosition.getY();

        // Unbounded matrices, always convert and we are done.
        if ( roundMatrix ) {
            return getUnbounded(x, y);
        }
        // Bounded matrices, check boundaries and make exception. We should never ever be here..
        else {
            if ( x < 0 || x >= matrix.length ) throw new IllegalArgumentException("X out of bounds ("+x+")");
            if ( y < 0 || y >= matrix[0].length ) throw new IllegalArgumentException("Y out of bounds ("+y+")");
        }
        return newPosition;
    }

    /**
     * Convert the given x,y to the unbounded version,
     * as if the matrix would be unbounded.
     * 
     * @param x coordinate
     * @param y coordinate
     * @return Position
     */
    private Position getUnbounded(Integer x, Integer y) {
        // Validate arguments.
        if ( x == null ) throw new IllegalArgumentException("X cannot be null.");
        if ( y == null ) throw new IllegalArgumentException("Y cannot be null.");

        // Normalise x and y in case they are bigger than twice the length.
        x = x % matrix.length;
        y = y % matrix[0].length;

        // If x,y are outside of the matrix, convert them to the new unbounded x,y pair. 
        if ( x < 0 ) x = ( matrix.length + x );
        if ( y < 0 ) y = ( matrix[0].length + y );
        if ( x >= matrix.length ) x = x % matrix.length;
        if ( y >= matrix[0].length ) y = y % matrix[0].length;

        return new PositionImpl(x,y);
    }
}