package agent.interfaces;

import java.util.List;

import agent.constants.Action;

/**
 * The State defined by the environment, accepted by the Agent.
 * 
 * @author Vasco
 *
 */
public interface State {
	/**
	 * The list of all possible actions at this state.
	 * 
	 * @return List of all possible Actions
	 */
	public List<Action> getActions();
	
	/**
	 * The state attributes.
	 * 
	 * @return StateAttributes
	 */
	public StateAttributes getStateAttributes();

	/**
	 * The default State id.
	 * 
	 * @return Integer
	 */
	public Integer getStateId();
}
