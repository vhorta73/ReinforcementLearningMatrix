package environment.impl;

import java.util.LinkedList;
import java.util.List;

import agent.constants.Action;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.interfaces.Agent;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;
import environment.interfaces.ActionProcess;
import environment.interfaces.Environment;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.Pixel;
import environment.interfaces.Position;

/**
 * Environment implementation.
 * 
 * @author Vasco
 *
 */
public class EnvironmentImpl extends EnvironmentMatrixImpl implements Environment {
    /**
     * The list of active agents on the environment.
     */
    private List<EnvironmentAgentHandler> environmentAgentHanlderList;

    /**
     * The exiting flag.
     */
    private boolean exit = false;

    /**
     * Initial coordinate X for new agents.
     */
    private final int INITIAL_X = 20;

    /**
     * Initial coordinate Y for new agents.
     */
    private final int INITIAL_Y = 20;

    /**
     * Constructor.
     */
    public EnvironmentImpl(Pixel[][] matrix) {
        // Load given matrix.
        super(matrix);
        environmentAgentHanlderList = new LinkedList<EnvironmentAgentHandler>();
    }

    /**
     * Add a new Agent to the environment.
     */
    @Override
    public void addAgent(Agent agent, Integer visionRadius) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Cannot add a null Agent.");

// For now, 0,0 becomes the initial position for all agents.
// TODO: Decide what should be the best starting position.
// TODO: create new function to return a random valid position?
        Position position = new PositionImpl(INITIAL_X, INITIAL_Y);

        // We don't have an agent id, so we check for the highest agentId and
        // assign to this one unless... we already have this agent in list.
        Integer highestId = 0;
        Integer previousId = 0;
        for (EnvironmentAgentHandler environmentAgentHandler : environmentAgentHanlderList ) {
            Agent alreadyAdded = environmentAgentHandler.getAgent();
            if ( alreadyAdded.equals(agent) ) {
                throw new IllegalStateException("Cannot add same agent twice.");
            }
            previousId = environmentAgentHandler.getAgentId();
            if ( highestId < previousId ) highestId = environmentAgentHandler.getAgentId();
        }

        // Get the reward if any at current position.
        Double reward = get(position.getX(),position.getY()).getReward();
        // Gather all possible actions.
        List<Action> actionList = getPossibleActions(position);
        // Get the vision.
        Vision vision = super.getVision(INITIAL_X, INITIAL_Y, visionRadius);
        // Setup the state attributes.
        StateAttributes stateAttributes = new StateAttributesImpl(0, vision);
        // Setup the current state for the Agent.
        State currentState = new StateImpl(actionList, stateAttributes);

        // Set the Agent with its first initial state.
        agent.set(currentState, reward);

        // Create the new EnvironmentAgentHandler and add it to the list.
        EnvironmentAgentHandler newAgent = new EnvironmentAgentHandlerImpl(agent, visionRadius, position, highestId + 1);
        // ..and off you go..
        environmentAgentHanlderList.add(newAgent);
    }

    /**
     * Remove an Agent from the environment.
     */
    @Override
    public void removeAgent(Agent agent) {
        // Validate parameters.
        if ( agent == null ) throw new IllegalArgumentException("Cannot remove a null Agent.");

        // Loop through all agents in list and remove the one with the same agent address.
        for( int i = 0; i < environmentAgentHanlderList.size(); i++ ) {
            // Take the change to ensure the sanity of the list with no mercy for mistakes elsewhere.
            EnvironmentAgentHandler foundHanlder = environmentAgentHanlderList.get(i);
            if ( foundHanlder == null ) throw new IllegalStateException("Null handler found in list.");
            Agent foundAgent = foundHanlder.getAgent();
            if ( foundAgent == null ) throw new IllegalStateException("Null agent found in list.");

            // Check now if this is the same agent to be removed.
            if ( agent.equals(foundAgent) ) {
                environmentAgentHanlderList.remove(i);
                // Assume list is correct and that there will be no two agents 
                // with the same address.
                return;
            }
        }
        // Opted to not create an exception if the agent to be removed was not in list
        // since that is not relevant for the purpose of removing a specific agent.
    }

    /**
     * The Runnable.
     */
    @Override
    public void run() {
        // Run forever until requested to exit
        while(!exit) {

            // Loop through each agent to collect ready actions and reply with new state.
            for( int i = 0 ; i < environmentAgentHanlderList.size(); i++ ) {

                // Get the respective environment Agent Handler
                EnvironmentAgentHandler oldEnvironmentAgentHandler = environmentAgentHanlderList.get(i);
                if ( oldEnvironmentAgentHandler == null ) throw new IllegalStateException("Handler cannot be null.");

                // Collect the respective agent
                Agent agent = oldEnvironmentAgentHandler.getAgent();
                if ( agent == null ) throw new IllegalStateException("Agent cannot be null.");

                if ( agent.isReadyForAction() ) {
                    EnvironmentAgentHandler newEnvironmentAgentHandler = process(oldEnvironmentAgentHandler);
                    if ( newEnvironmentAgentHandler != null ) {
                        environmentAgentHanlderList.remove(oldEnvironmentAgentHandler);
                        environmentAgentHanlderList.add(newEnvironmentAgentHandler);
                    }
                }
            }
        }
    }

    /**
     * Process current Environment Agent handler into the next 
     * after processing the requested action made by the agent.
     * 
     * @param environmentAgentHandler
     * @return EnvironmentAgentHandler
     */
    private EnvironmentAgentHandler process(EnvironmentAgentHandler environmentAgentHandler) {
        // Validate parameters.
        if ( environmentAgentHandler == null ) throw new IllegalArgumentException("EnvironmentAgentHanlder cannot be null.");

        // Gather all environment agent handler information.
        Agent agent = environmentAgentHandler.getAgent();
        Integer agentId = environmentAgentHandler.getAgentId();
        Position currentPosition = environmentAgentHandler.getAgentPosition();
        Integer visionRadius = environmentAgentHandler.getVisionRadius();

        if ( agent == null ) throw new IllegalStateException("Agent cannot be null.");
        if ( agentId == null ) throw new IllegalStateException("Agent ID cannot be null.");
        if ( currentPosition == null ) throw new IllegalStateException("Agent's position cannot be null.");        
        if ( visionRadius == null ) throw new IllegalStateException("Vision radius cannot be null.");

        Action action = agent.getAction();
        if ( action == null ) throw new IllegalStateException("Action cannot be null.");

    // ======================== Action being executed here ======================= //

        // Convert the requested action from current position to the new position
        ActionMatrixProcess actionConverter = new ActionMatrixProcess();
        Position newPosition = actionConverter.execute(action, currentPosition);

    // =========================================================================== //

        // Get all possible actions at this new position.
        List<Action> actionList = getPossibleActions(newPosition);
        // Get the vision seen from this new position.
        Vision vision = super.getVision(newPosition.getX(), newPosition.getY(), visionRadius);
        // Add the vision to the new State Attributes to be passed on the State
        StateAttributes stateAttributes = new StateAttributesImpl(0, vision);
        // Create the new state object to be given to the Agent.
        State currentState = new StateImpl(actionList, stateAttributes);
        // The reward found at this state by the environment.
        Double reward = get(newPosition.getX(),newPosition.getY()).getReward();

        // Update the agent with the current new state and the reward found at this state.
        agent.set(currentState, reward);

        // Calculate the final EnvironmentAgentHanlder object and return it.
        return new EnvironmentAgentHandlerImpl(agent, visionRadius, newPosition, agentId);
    }

    /**
     * Gathering all possible actions set by the environment at given position.
     * 
     * @param position
     * @return Action List
     */
    private List<Action> getPossibleActions(Position position) {
        List<Action> actionList = new LinkedList<Action>();
        ActionProcess<Position> actionProcess = new ActionMatrixProcess();

        // Go over all possible actions in the book and check which ones
        // are possible to be done from this given position
        for ( Action action : Action.values() ) {
            Position newPosition = actionProcess.execute(action, position);
            Pixel pixel = get(newPosition.getX(),newPosition.getY());
            // Don't add this action if resulting position is blocked
            if ( pixel.isBlocked() ) continue;

            // Add current action as valid.
            actionList.add(action);
        }
        return actionList;
    }

    /**
     * The turn-off switch.
     */
    @Override
    public void exit() {
        this.exit = true;
    }
}