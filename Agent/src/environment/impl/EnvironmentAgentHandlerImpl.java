package environment.impl;

import agent.interfaces.Agent;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.Position;

/**
 * Environment Agent Handler implementation.
 * 
 * @author Vasco
 *
 */
public class EnvironmentAgentHandlerImpl implements EnvironmentAgentHandler {
	/**
	 * The Agent.
	 */
	private final Agent agent;
	
	/**
	 * The Agent's position.
	 */
	private final Position position;
	
	/**
	 * The Agent's vision radius.
	 */
	private final Integer radius;

	/**
	 * The agent ID.
	 */
	private final Integer agentId;
	
	/**
	 * Constructor.
	 * 
	 * @param agent Agent
	 * @param visionRadius Integer
	 * @param position Position
	 * @param agentId Integer
	 */
    public EnvironmentAgentHandlerImpl(Agent agent, Integer visionRadius, Position position, Integer agentId) {
    	// Validate arguments.
    	if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");
    	if ( position == null ) throw new IllegalArgumentException("Position cannot be null.");
    	if ( visionRadius == null ) throw new IllegalArgumentException("Vision radius cannot be null.");
    	if ( agentId == null ) throw new IllegalArgumentException("AgentId cannot be null.");

    	this.agent = agent;
    	this.radius = visionRadius;
    	this.position = position;
    	this.agentId = agentId;
    }
    
    /**
     * The Agent id.
     */
	@Override
	public int getAgentId() {
		return agentId;
	}

	/**
	 * The Vision radius.
	 */
	@Override
	public int getVisionRadius() {
		return radius;
	}

	/**
	 * The Agent's position.
	 */
	@Override
	public Position getAgentPosition() {
		return position;
	}

	/**
	 * The Agent.
	 */
	@Override
	public Agent getAgent() {
		return agent;
	}

}
