package agent.impl;

import java.util.List;

import agent.constants.Action;
import agent.interfaces.Agent;
import agent.interfaces.State;

/**
 * Agent implementation.
 * 
 * @author Vasco
 *
 */
public class AgentImpl extends AgentDeciderImpl implements Agent {
	/**
	 * Previous State.
	 */
	private State previousState;
	
	/**
	 * The Action taken / to take.
	 */
	private Action action;
	
	/**
	 * Flag to tell if Action is ready to be pulled.
	 */
	private boolean actionReady;
	
	/**
	 * Flag to tell if new State is ready to be received.
	 * This is turned on initially, and off when in the
	 * process of calculating the next action, and the action
	 * calculated has been pulled.
	 */
	private boolean stateReady;

	/**
	 * Constructor.
	 */
	public AgentImpl(Double gamma, Integer averagingSampleRate ) {
		super(gamma, averagingSampleRate);

		// State is not yet received
		stateReady = true;

		// Action is not yet ready to be pulled
		actionReady = false;
	}

	/**
	 * Receive the new state and any reward it carries.
	 */
	@Override
	public void set(State currentState, Double reward) {
		// Validate arguments.
		if ( currentState == null ) throw new IllegalArgumentException("Current state cannot be null.");
		if ( currentState.getActions() == null ) throw new IllegalArgumentException("Current state must have actions.");
		if ( currentState.getActions().size() == 0 ) throw new IllegalArgumentException("Current state must have actions.");
		if ( ! stateReady ) throw new IllegalStateException("Not ready for new state yet.");
		
		// If this is the first time ever, then there is no extra processing to be done.
		if ( action == null ) {

			// We are now on a new state.
			previousState = currentState;

			// Get a random next action for this state
			List<Action> actions = currentState.getActions();
			int randomIndex = (int) Math.random() * actions.size();
			action = currentState.getActions().get(randomIndex);

			// Now we are ready to process the next action
			actionReady = true;

			// State is only ready when we see the outcome of the above action.
			stateReady = false;
		}
		// This is not the first ever state.
		else {
			// Starting a thread to calculate the next action.
			Runnable calculateNextAction = new Runnable() {
				@Override
				public void run() {
					// Stop receiving any further state updates until we process this one out.
					stateReady = false;

					// Record this event before any further action
					addEvent(previousState, action, currentState, reward);
					
					// Move on to the current state and get ready for next action.
					previousState = currentState;
					
					// Do all calculations necessary to know which action to take next.
					setNextAction();

					// Get the next action
					action = getAction();
					// Set the action as ready first.
					actionReady = true;
					// Now set ready for next state.
					stateReady = true;
				}
			};
			Thread processNextAction = new Thread(calculateNextAction);
			processNextAction.start();
		}
	}

	/**
	 * The next action chosen to be.
	 * 
	 * Environment must call this at least once before sending any new state.
	 */
	@Override
	public Action getAction() {
		if ( actionReady ) {
			stateReady = true;
			return action;
		} 
		else throw new IllegalStateException("Agent is not ready for the new action.");
	}

	/**
	 * Flag to tell when the action is ready to be pulled.
	 */
	@Override
	public boolean isReadyForAction() {
		return actionReady;
	}

	/**
	 * This used by a Thread and is where the action will be calculated.
	 * The process to calculate which action should be taken next, will 
	 * depend on the used algorithm.
	 * 
	 * The next action is set here.
	 */
	private void setNextAction() {
		action = super.getNextAction(previousState);
	}
}
