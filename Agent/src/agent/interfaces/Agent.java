package agent.interfaces;

import agent.constants.Action;

/**
 * The Agent interface to be used by the Environment.
 * Agent prepares an Action to be picked up by the Environment 
 * Environment picks up the action the Agent wants and returns
 * a State and a reward if any. 
 * 
 * For each State reward the environment sends to the Agent, 
 * a new action is chosen by the Agent to its best knowledge.
 * 
 * @author Vasco
 *
 */
public interface Agent {
	/**
	 * Environment supplies the current state and its value.
	 * 
	 * @param currentState the current state
	 * @param reward value at this state
	 * @return 
	 */
	public void set(State currentState, Double reward);
	
	/**
	 * The Action chosed by the Agent.
	 * 
	 * @return Action
	 */
	public Action getAction();
	
	/**
	 * Tells the environment if the next Action has been
	 * chosen by the Agent and is ready to be picked up.
	 * 
	 * @return true if next action ready to be picked up
	 */
	public boolean isReadyForAction();
}
