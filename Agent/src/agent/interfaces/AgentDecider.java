package agent.interfaces;
/**
 * The AgentDecider interface.
 * 
 * The decision on which action to take next is to be made on this implementation.
 * Basis for the next action would be upon the AgentEvents past experiences
 * and on any other algorithms built to aid on this decision.
 * 
 * @author Vasco
 *
 */
public abstract interface AgentDecider extends AgentEvents {
	/**
	 * The next action to be had.
	 * 
	 * This is to offer a default method in the absence of and override.
	 * 
	 * @param currentState the current Agent's state
	 * @return Action
	 */
	public default Action getNextAction(State currentState) {
		// Half of the time, goes exploring by taking any of the available
		// actions on the current state at random.
		if ( Math.random() > 0.5 ) {
    		return currentState.getActions().get((int)Math.random()*currentState.getActions().size());
	    }
		// The other half of the time, goes for the best know action know at 
		// current state. This comes from the past events experienced.
    	else {
	    	return getBestAction(currentState);       
    	}
	}
}
