package agent.impl;

import agent.constants.Action;
import agent.interfaces.AgentDecider;
import agent.interfaces.State;

/**
 * The AgentDecider implementation extending the PathTrack implementation.
 * 
 * @author Vasco
 *
 */
public class AgentDeciderImpl extends PathTrackImpl implements AgentDecider {
    /**
     * The e-greedy deciding how often Agent goes exploring.
     */
    private final Double E_GREEDY = 0.2;

    /**
     * Constructor.
     */
    public AgentDeciderImpl(Double gamma, Integer averagingSampleRate) {
        super(gamma, averagingSampleRate);
    }

    /**
     * Using the e-greedy to decide when to go exploring.
     */
    @Override
    public Action getNextAction(State currentState) {
        if ( Math.random() > E_GREEDY ) {
            int random = (int) (Math.random()*currentState.getActions().size());
            Action action = currentState.getActions().get(random);
            return action;
        }
        else {
            Action action = getBestAction(currentState);
            if ( action == null ) {
                int random = (int) (Math.random()*currentState.getActions().size());
                action = currentState.getActions().get(random);
                return action;
            }
            return action;       
        }
    }
}