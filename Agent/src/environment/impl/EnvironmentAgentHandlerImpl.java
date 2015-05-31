package environment.impl;

import agent.interfaces.Agent;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.GraphicalDisplay;
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
     * The GraphicalDisplat handler.
     */
    private final GraphicalDisplay graphicalDisplayHandler;

    /**
     * The GraphicalDisplay Thread handler.
     */
    private final Thread graphicalDisplayThreadHandler;
    
    /**
     * Constructor.
     * 
     * @param agent Agent
     * @param visionRadius Integer
     * @param position Position
     * @param agentId Integer
     */
    public EnvironmentAgentHandlerImpl(Agent agent, Integer visionRadius, 
            Position position, Integer agentId, Boolean showVision,
            GraphicalDisplay graphicalDisplayHandler, Thread graphicalDisplayThread) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");
        if ( position == null ) throw new IllegalArgumentException("Position cannot be null.");
        if ( visionRadius == null ) throw new IllegalArgumentException("Vision radius cannot be null.");
        if ( agentId == null ) throw new IllegalArgumentException("AgentId cannot be null.");
        if ( showVision == null ) throw new IllegalArgumentException("Show vision flag cannot be null.");
        if ( graphicalDisplayHandler == null ) 
            throw new IllegalArgumentException("GraphicalDisplay handler cannot be null.");
        if ( graphicalDisplayThread == null ) 
            throw new IllegalArgumentException("GraphicalDisplay Thread handler cannot be null.");

        this.agent = agent;
        this.radius = visionRadius;
        this.position = position;
        this.agentId = agentId;
        this.showVision = showVision;
        this.graphicalDisplayHandler = graphicalDisplayHandler;
        this.graphicalDisplayThreadHandler = graphicalDisplayThread;
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

    /**
     * The GraphicalDisplay handler.
     */
    @Override
    public GraphicalDisplay getDisplayHandler() {
        return graphicalDisplayHandler;
    }

    /**
     * The GraphicalDisplay Thread handler.
     */
    @Override
    public Thread getDisplayThreadHandler() {
        return graphicalDisplayThreadHandler;
    }
}
