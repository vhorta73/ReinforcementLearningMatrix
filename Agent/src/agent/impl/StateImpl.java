package agent.impl;

import java.util.List;

import agent.constants.Action;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;

/**
 * State implementation.
 * 
 * @author Vasco
 *
 */
public class StateImpl implements State {
	/**
	 * List of all possible actions at this state.
	 */
	private final List<Action> actionList;
	
	/**
	 * The State Attributes.
	 */
	private final StateAttributes stateAttributes;

	/**
	 * Constructor.
	 * 
	 * @param actionList of all possible actions at this state.
	 */
	public StateImpl(List<Action> actionList, StateAttributes stateAttributes) {
		// Validate parameters.
		if ( actionList == null ) throw new IllegalArgumentException("Action List cannot be null.");
		if ( stateAttributes == null ) throw new IllegalArgumentException("State Attributes cannot be null.");

		// Check for duplicates
		if ( actionList.stream().distinct().count() < actionList.size() ) 
			throw new IllegalArgumentException("Given Action list cannot have duplicated actions.");
		
		this.actionList = actionList;
		this.stateAttributes = stateAttributes;
	}

	/**
	 * Return all possible actions at this state.
	 */
	@Override
	public List<Action> getActions() {
		return actionList;
	}
	
	/**
	 * The attributes of the state.
	 * 
	 * @return StateAttributes
	 */
	public StateAttributes getStateAttributes() {
		return stateAttributes;
	}

	/**
	 * The default state value
	 */
	@Override
	public Integer getStateId() {
		if ( actionList == null ) throw new IllegalStateException("Action list cannot be null.");
		if ( stateAttributes == null ) throw new IllegalStateException("State Attributes cannot be null.");
		return actionList.hashCode() + stateAttributes.getValue();
	}
}
