package agent.impl;

import java.util.List;

import agent.constants.Action;
import agent.interfaces.AgentStateValue;
import agent.interfaces.StateAttributes;

/**
 * The AgentState implementation.
 * 
 * The calculation of the State value is done on this class
 * from the supplied state attributes.
 * By default the State Implementation returns the hashcode() of the
 * state attributes but more calculation could be done if needed 
 * to ascertain what the state value should be.
 * 
 * @author Vasco
 *
 */
public class AgentStateValueImpl extends StateImpl implements AgentStateValue {
	/**
	 * Constructor.
	 * 
	 * @param actionList available actions on this state.
	 * @param stateAttributes State Attributes.
	 */
	public AgentStateValueImpl(List<Action> actionList, StateAttributes stateAttributes) {
		super(actionList, stateAttributes);
	}

//	/**
//	 * The state value
//	 */
//	@Override
//	public Integer getStateId() {
//		// TODO: A clever way to generate a state...
//		StateAttributes stateAttributes = super.getStateAttributes();
//		List<Action> actions = super.getActions();
//		Integer actionsCode = actions.hashCode();
//		Integer stateAttributesCode = stateAttributes.getValue();
//		return actionsCode + stateAttributesCode;
//	}
}
