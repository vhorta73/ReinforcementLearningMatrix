package agent.interfaces;

import java.util.List;

/**
 * AgentEvents stores events for all state-action-state rewards.
 * Reward values are stored as given into an in-built moving average
 * function which can be tweaked as per SASValue interface specifications.
 * 
 * The Agent will be able to know through the AgentEvents, what would
 * be the best Action value in any previously visited State.
 * 
 * @author Vasco
 *
 */
public interface AgentEvents {
	/**
	 * Setting an event to be recorded, and the reward to be added
	 * into a default set Moving Averaged function.
	 * 
	 * @param previousState before taken action
	 * @param action taken
	 * @param currentState after taken action
	 * @param reward at current state.
	 */
	public void addEvent(State previousState, Action action, State currentState, Double reward);
	
	/**
	 * The best action at a given state, which is calculated by
	 * the average of all state values for each action.
	 * 
	 * @param State to pick best action from
	 * @return Action
	 */
	public Action getBestAction(State state);
	
	/**
	 * The list of visited actions on given state.
	 * 
	 * @param state
	 * @return List of ActionValues
	 */
	public List<ActionValue> getActions(State state);
}
