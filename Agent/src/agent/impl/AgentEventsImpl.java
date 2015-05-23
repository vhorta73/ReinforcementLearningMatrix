package agent.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import agent.constants.Action;
import agent.interfaces.ActionValue;
import agent.interfaces.AgentEvents;
import agent.interfaces.AgentStateValue;
import agent.interfaces.SASValue;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;

/**
 * The AgentEvents implementation.
 * 
 * @author Vasco
 *
 */
public class AgentEventsImpl implements AgentEvents {
    /**
     * All the agent events known so far.
     */
    private Map<Integer,Map<Action,Map<Integer, SASValue>>> agentEvents;
    
    /**
     * The sample amount values will be averaged for.
     * Default will be 100.
     */
    private final int averagingSamples;

    /**
     * Constructor.
     * 
     * @param averagingSamples
     */
    public AgentEventsImpl(int averagingSamples) {
        if ( averagingSamples <= 0 ) throw new IllegalArgumentException("Cannot set " + averagingSamples + " as an averaging sample.");
        agentEvents = new HashMap<Integer,Map<Action,Map<Integer, SASValue>>>();
        this.averagingSamples = averagingSamples;
    }

    /**
     * Adding previous state, action taken and current state and the reward
     * found at current state, the addEvent(SASR) will store this reward into
     * the SASValue class.
     */
    @Override
    public void addEvent(State previousState, Action action, State currentState, Double reward) {
		// Check input.
		if ( previousState == null ) throw new IllegalArgumentException("Null previous State supplied.");
		if ( action == null ) throw new IllegalArgumentException("Null action supplied.");
		if ( currentState == null ) throw new IllegalArgumentException("Null current State supplied.");
		if ( previousState.getActions().size() == 0 ) throw new IllegalStateException("No actions available from previouState.");
		if ( !previousState.getActions().contains(action) ) throw new IllegalStateException("Action " + action + " is not listed in previousState.");

        // Initialisations.
        Map<Action,Map<Integer, SASValue>> actionStateValue = new HashMap<Action, Map<Integer, SASValue>>();
        Map<Integer, SASValue> stateValue = new HashMap<Integer, SASValue>();
        SASValue value = new SASValueImpl();
        value.setAveragingSample(averagingSamples);

        // Get State Values through the AgentStateValue implementation. By default will use the StateImpl getStateValue().
        Integer previousStateValue = new AgentStateValueImpl(previousState.getActions(), previousState.getStateAttributes()).getStateId();
        Integer currentStateValue  = new AgentStateValueImpl(currentState.getActions(), currentState.getStateAttributes()).getStateId();

        // Some records found
        if ( agentEvents.size() != 0 ) {

        	// We have visited the previous State before
            if ( agentEvents.get(previousStateValue) != null ) {
                actionStateValue = agentEvents.get(previousStateValue);

                // We have visited this Action before
                if ( actionStateValue.get(action) != null ) {
                    stateValue = actionStateValue.get(action);

                    // We have visited this current state before
                    if ( stateValue.get(currentStateValue) != null ) {
                        value = stateValue.get(currentStateValue);
                    }
                }
            }
        }

        // Add given reward to value and store back values to Map
        value.add(reward);
        stateValue.put(currentStateValue, value);
        actionStateValue.put(action, stateValue);
        agentEvents.put(previousStateValue, actionStateValue);
    }

    /**
     * The best action found at the given state.
     */
    @Override
    public Action getBestAction(State state) {
    	// Check input
    	if ( state == null ) throw new IllegalArgumentException("Null state received.");
    	if ( state.getActions().size() == 0 ) throw new IllegalStateException("No actiona available for state.");

    	// To calculate the state value from the attributes. This defaults to the state getStateValue()
    	List<Action> actionList = state.getActions();
    	StateAttributes stateAttributes = state.getStateAttributes();
    	AgentStateValue agentStateValue = new AgentStateValueImpl(actionList, stateAttributes);
    	Integer thisStateValue = agentStateValue.getStateId();

        // Initialisations.
        Map<Action,Map<Integer, SASValue>> actionStateValue = new HashMap<Action, Map<Integer, SASValue>>();
        Map<Integer, SASValue> stateValue = new HashMap<Integer, SASValue>();
        Map<Action,Double> actionValues = new HashMap<Action, Double>();
        
        if ( agentEvents.size() != 0 ) {
            actionStateValue = agentEvents.get(thisStateValue);
            for( Action action : actionStateValue.keySet() ) {
                Double actionValue = 0.0;
                int count = 0;
                stateValue = actionStateValue.get(action);
                if ( stateValue != null ) {
                    for( Integer valueState : stateValue.keySet() ) {
                        actionValue += stateValue.get(valueState).getValue();
                        count++;
                    }
                }
                actionValues.put(action, (actionValue/count));
            }
        }
        
        // Initialise with null
        Action bestAction = null;
        Double bestValue = 0.0;
        for( Action action : actionValues.keySet() ) {
            if ( bestValue < actionValues.get(action) ) {
                bestAction = action;
                bestValue = actionValues.get(action);
            }
        }

        return bestAction;
    }

    /**
     * The list of visited actions at the given state, 
     * with their respective current values.
     */
    @Override
    public List<ActionValue> getActions(State state) {
    	// Check input
    	if ( state == null ) throw new IllegalArgumentException("Null state received.");
    	if ( state.getActions().size() == 0 ) throw new IllegalStateException("No actions available for received state.");
    	
        // Initialisations.
        Map<Action,Map<Integer, SASValue>> actionStateValue = new HashMap<Action, Map<Integer, SASValue>>();
        Map<Integer, SASValue> stateValue = new HashMap<Integer, SASValue>();
        Map<Action,Double> actionValues = new HashMap<Action, Double>();

        // Get State Values through the AgentStateValue implementation. By default will use the StateImpl getStateValue().
        Integer thisStateValue = new AgentStateValueImpl(state.getActions(), state.getStateAttributes()).getStateId();

        if ( agentEvents.size() != 0 ) {
            actionStateValue = agentEvents.get(thisStateValue);
            List<Action> stateActions = state.getActions();
            for( Action action : actionStateValue.keySet() ) {
            	if ( !stateActions.contains(action) ) 
            		throw new IllegalStateException("Action '" + action + "' is not found on the given state.");
                Double actionValue = 0.0;
                int count = 0;
                stateValue = actionStateValue.get(action);
                if ( stateValue != null ) {
                    for( Integer valueState : stateValue.keySet() ) {
                        actionValue += stateValue.get(valueState).getValue();
                        count++;
                    }
                }
                actionValues.put(action, (actionValue/count));
            }
        }
        
        List<ActionValue> actionValueList = new LinkedList<ActionValue>();
        for( Action action : actionValues.keySet() ) {
            ActionValue aValue = new ActionValueImpl(action, actionValues.get(action));
            actionValueList.add(aValue);
        }

        return actionValueList;
    }
}
