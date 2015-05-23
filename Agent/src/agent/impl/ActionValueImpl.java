package agent.impl;

import agent.interfaces.Action;
import agent.interfaces.ActionValue;
/**
 * The ActionValue implementation.
 * 
 * @author Vasco
 *
 */
public class ActionValueImpl implements ActionValue {
	/**
	 * The action.
	 */
	private final Action action;

	/**
	 * The value.
	 */
	private final Double value;
	
	/**
	 * Constructor.
	 */
	public ActionValueImpl(Action action, Double value) {
		this.action = action;
		this.value = value;
	}

	/**
	 * The Action
	 */
	@Override
	public Action getAction() {
		return action;
	}

	/**
	 * The value of the action
	 */
	@Override
	public Double getValue() {
		return value;
	}

}
