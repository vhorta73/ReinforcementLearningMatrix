package environment.impl;

import agent.interfaces.Action;
import environment.interfaces.ActionProcess;
import environment.interfaces.Position;

/**
 * The Action Process implementation.
 * 
 * @author Vasco
 *
 */
public class ActionMatrixProcess implements ActionProcess<Position> {
    /**
     * Return the transformed Position according to the Action's definition.
     */
    @Override
    public Position execute(Action action, Position position) {
        if ( position == null ) throw new IllegalArgumentException("Cannot process a null Position.");
        if ( action == null ) throw new IllegalArgumentException("Cannot process a null Action.");
        
        Position finalPosition = null;
        switch (action) {
            case UPPER_LEFT : {
                finalPosition = new PositionImpl(position.getX()-1, position.getY()-1); break;
            }
            case UP : {
                finalPosition = new PositionImpl(position.getX(), position.getY()-1); break;
            }
            case UPPER_RIGHT : {
                finalPosition = new PositionImpl(position.getX()+1, position.getY()-1); break;
            }
            case LEFT : {
                finalPosition = new PositionImpl(position.getX()-1, position.getY()); break;
            }
            case IDLE : {
                finalPosition = new PositionImpl(position.getX(), position.getY()); break;
            }
            case RIGHT : {
                finalPosition = new PositionImpl(position.getX()+1, position.getY()); break;
            }
            case LOWER_LEFT : {
                finalPosition = new PositionImpl(position.getX()-1, position.getY()+1); break;
            }
            case DOWN : {
                finalPosition = new PositionImpl(position.getX(), position.getY()+1); break;
            }
            case LOWER_RIGHT : {
                finalPosition = new PositionImpl(position.getX()+1, position.getY()+1); break;
            }
            default : { break; }
        }
        
        if ( finalPosition == null ) throw new IllegalStateException("Action '"+action+"' is not known and cannot be processed.");
        
        return finalPosition;
    }
}
