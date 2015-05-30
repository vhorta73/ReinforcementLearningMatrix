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
     * Show vision flag.
     */
    private Boolean showVision;
    
    /**
     * Constructor.
     * 
     * @param agent Agent
     * @param visionRadius Integer
     * @param position Position
     * @param agentId Integer
     */
    public EnvironmentAgentHandlerImpl(Agent agent, Integer visionRadius, Position position, Integer agentId, Boolean showVision) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");
        if ( position == null ) throw new IllegalArgumentException("Position cannot be null.");
        if ( visionRadius == null ) throw new IllegalArgumentException("Vision radius cannot be null.");
        if ( agentId == null ) throw new IllegalArgumentException("AgentId cannot be null.");
        if ( showVision == null ) throw new IllegalArgumentException("Show vision flag cannot be null.");

        this.agent = agent;
        this.radius = visionRadius;
        this.position = position;
        this.agentId = agentId;
        this.showVision = showVision;
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

    /**
     * Check if the vision is to be shown.
     * 
     * @return true if to be shown
     */
    @Override
    public Boolean isShowVision() {
        return this.showVision;
    }

    /**
     * Set the showVision to the requested value.
     * 
     * @param value 
     */
    @Override
    public void setVision(Boolean value) {
        if ( value == null ) throw new IllegalArgumentException("Boolean value cannot be null.");
        this.showVision = value;
    }
}
