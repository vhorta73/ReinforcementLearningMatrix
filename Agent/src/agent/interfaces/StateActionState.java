package agent.interfaces;

import agent.constants.Action;


/**
 * StateActionState interface.
 * 
 * @author Vasco
 *
 */
public interface StateActionState {
	/**
	 * The previous State.
	 * 
	 * @return State
	 */
	public State getPreviousState();
	
	/**
	 * The taken action.
	 * 
	 * @return Action
	 */
	public Action getAction();
	
	/**
	 * The current State
	 * 
	 * @return State
	 */
	public State getCurrentState();
}
