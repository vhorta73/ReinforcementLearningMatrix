package agent.impl;

import agent.interfaces.Action;
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
	private final Double E_GREEDY = 0.8;
	
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
    		return currentState.getActions().get((int)Math.random()*currentState.getActions().size());
	    }
    	else {
	    	return getBestAction(currentState);       
    	}
	}
}
