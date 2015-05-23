package agent.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import agent.interfaces.Action;
import agent.interfaces.ActionValue;
import agent.interfaces.PathTrack;
import agent.interfaces.State;
import agent.interfaces.StateActionState;
import agent.utilities.ComparatorActionValue;

/**
 * PathTrack implementation.
 * 
 * Path followed to a reward gets populated only when a non-zero reward is given.
 * 
 * @author Vasco
 *
 */
public class PathTrackImpl extends AgentEventsImpl implements PathTrack {
	/**
	 * The path sequence from start to a reward.
	 */
	private List<StateActionState> pathList;

	/**
	 * The gamma value.
	 */
	private final Double GAMMA;

	/**
	 * Constructor.
	 * 
	 * @param gamma
	 */
	public PathTrackImpl(Double gamma, int averagingSamples) {
		super(averagingSamples);
		if ( gamma <= 0.0 ) throw new IllegalArgumentException("Gamma cannot be zero or negative: " + gamma);
		if ( gamma > 1.0 ) throw new IllegalArgumentException("Gamma cannot be bigger than zero: " + gamma);
		pathList = new LinkedList<StateActionState>();
		this.GAMMA = gamma;
	}

	/**
	 * Adding Events to the Agent's memory.
	 */
	@Override
	public void addEvent(State previousState, Action action, State currentState, Double reward) {
		// Check input
		if ( previousState == null ) throw new IllegalArgumentException("Previous State cannot be null.");
		if ( action == null ) throw new IllegalArgumentException("Action cannot be null.");
		if ( currentState == null ) throw new IllegalArgumentException("Current State cannot be null.");
		if ( previousState.getActions().size() == 0 ) throw new IllegalStateException("Previous State has no actions available.");
		if ( ! previousState.getActions().contains(action) ) 
			throw new IllegalStateException("Supplied action is not available in previous state.");
		if ( currentState.getActions().size() == 0 ) 
			throw new IllegalStateException("Supplied current State has no actions available.");

		// Create a StateActionState and add it to the pathList regardless.
		StateActionState newStateActionState = new StateActionStateImpl(previousState, action, currentState);
		pathList.add(newStateActionState);

		// If a reward not zero, path track to initial state with rewards of weight values 
		// gamma powered to the distance from the goal state to Nth state from the goal.
		if ( reward != 0 ) {
			int distanceFromGoalState = pathList.size();

			// AddEvent all back-propagated values to origin from goal state, and clear the pathList.
			for ( StateActionState stateActionState : pathList ) {

				// Decrease value first to ensure we reach zero.
				distanceFromGoalState--;

				// Calculate gamma value at the origin
				Double gamma = Math.pow(GAMMA, distanceFromGoalState);
                super.addEvent(stateActionState.getPreviousState(), stateActionState.getAction(), 
							stateActionState.getCurrentState(), reward * gamma );
			}

			// Clear pathList to start new.
			pathList = new LinkedList<StateActionState>();
		}
	}
	
	/**
	 * Return the list of actions available at a previously visited state,
	 * sorted by the highest valued action to the smallest.
	 */
	@Override
	public List<ActionValue> getActions(State state) {
		// The list of ActionValue actions to be returned
		List<ActionValue> actions = super.getActions(state);
		
		// Sort the list and return using the ComparatorActionValue() Comparator
		return actions.stream().sorted(new ComparatorActionValue()).collect(Collectors.toList());
	}
}

