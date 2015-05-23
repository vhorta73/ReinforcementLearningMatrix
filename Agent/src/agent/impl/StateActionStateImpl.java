package agent.impl;

import agent.constants.Action;
import agent.interfaces.State;
import agent.interfaces.StateActionState;

public class StateActionStateImpl implements StateActionState {
	/**
	 * Previous State.
	 */
	private final State previousState;
	
	/**
	 * Action taken.
	 */
	private final Action action;
	
	/**
	 * The current State
	 */
	private final State currentState;
	
	/**
	 * Constructor.
	 * 
	 * @param currentState
	 * @param action
	 * @param nextState
	 */
	public StateActionStateImpl(State previousState, Action action, State currentState) {
		this.previousState = previousState;
		this.action = action;
		this.currentState = currentState;
	}
	
	/**
	 * The Action taken.
	 */
	@Override
	public Action getAction() {
		return action;
	}

	/**
	 * The current State.
	 */
	@Override
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * The previous State.
	 */
	@Override
	public State getPreviousState() {
		return previousState;
	}
}
