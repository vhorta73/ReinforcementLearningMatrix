package agent.interfaces;

import agent.constants.Action;

/**
 * The Action Value interface.
 * 
 * @author Vasco
 *
 */
public interface ActionValue {
	/**
	 * The Action.
	 * 
	 * @return Action
	 */
	public Action getAction();
	
	/**
	 * The value of the Action
	 * 
	 * @return action value
	 */
	public Double getValue();
}
