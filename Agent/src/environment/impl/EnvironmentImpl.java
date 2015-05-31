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
import environment.interfaces.Environment;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.GraphicalDisplay;
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
     * Agent counter.
     */
    private Integer agentId = 1;

    /**
     * The exiting flag.
     */
    private boolean exit = false;

    /**
     * Initial coordinate X for new agents.
     * Default to the middle of the matrix.
     */
    private int INITIAL_X;

    /**
     * Initial coordinate Y for new agents.
     * Default to the middle of the matrix.
     */
    private int INITIAL_Y;

    /**
     * Constructor.
     */
    public EnvironmentImpl(Pixel[][] matrix, Boolean roundMatrix) {
        // Load given matrix.
        super(matrix, roundMatrix);

        // TODO: Chose other starting position for when this one is blocking any action.
        INITIAL_X = (int) (matrix.length / 2);
        INITIAL_Y = (int) (matrix[0].length / 2);

        environmentAgentHanlderList = new LinkedList<EnvironmentAgentHandler>();
    }

    /**
     * Add a new Agent to the environment.
     */
    @Override
    public void addAgent(Agent agent, Integer visionRadius) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Cannot add a null Agent.");
        if ( visionRadius == null ) throw new IllegalArgumentException("Vision radius cannot be null.");

        Position position = new PositionImpl(INITIAL_X, INITIAL_Y);

        // Cannot add the same agent twice, thus check if we have it already before adding it.
        for (EnvironmentAgentHandler environmentAgentHandler : environmentAgentHanlderList ) {
            Agent alreadyAdded = environmentAgentHandler.getAgent();
            if ( alreadyAdded.equals(agent) ) {
                throw new IllegalStateException("Cannot add same agent twice.");
            }
        }

        // Get the reward if any at current position.
        Double reward = getPixel(position.getX(),position.getY()).getReward();
        // Gather all possible actions.
        List<Action> actionList = getPossibleActions(position);
        // Get the vision.
        Vision vision = super.getVision(INITIAL_X, INITIAL_Y, visionRadius);
        // Setup the state attributes.
        StateAttributes stateAttributes = new StateAttributesImpl(0, vision);
        // Setup the current state for the Agent.
        State currentState = new StateImpl(actionList, stateAttributes);
        // The initial show vision flag 
        Boolean showVision = false;
        // The initial GraphicalDisplay Handler with Agent's set vision radius
        GraphicalDisplay graphicsDisplay = new GraphicalDisplayImpl(new Pixel[visionRadius][visionRadius]);
        // The initial GraphicalDisplay Thread handler
        Thread graphicsDisplayThreadHandler = new Thread(graphicsDisplay);
        graphicsDisplayThreadHandler.start();

        // Set the Agent with its first initial state.
        agent.set(currentState, reward);

        // Create the new EnvironmentAgentHandler and add it to the list.
        EnvironmentAgentHandler newAgent = new EnvironmentAgentHandlerImpl(
                agent, visionRadius, position, agentId, showVision,
                graphicsDisplay, graphicsDisplayThreadHandler);
        // ..and off you go..
        environmentAgentHanlderList.add(newAgent);

        // Increment for the next agent.
        agentId++;
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
            EnvironmentAgentHandler foundHandler = environmentAgentHanlderList.get(i);
            if ( foundHandler == null ) throw new IllegalStateException("Null handler found in list.");
            Agent foundAgent = foundHandler.getAgent();
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
try {
    Thread.sleep(20);
} catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

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
        if ( environmentAgentHandler == null ) 
            throw new IllegalArgumentException("EnvironmentAgentHanlder cannot be null.");

        // Gather all environment agent handler information.
        Agent agent = environmentAgentHandler.getAgent();
        Integer agentId = environmentAgentHandler.getAgentId();
        Position currentPosition = environmentAgentHandler.getAgentPosition();
        Integer visionRadius = environmentAgentHandler.getVisionRadius();
        Boolean showVision = environmentAgentHandler.isShowVision();

        if ( agent == null ) throw new IllegalStateException("Agent cannot be null.");
        if ( agentId == null ) throw new IllegalStateException("Agent ID cannot be null.");
        if ( currentPosition == null ) throw new IllegalStateException("Agent's position cannot be null.");        
        if ( visionRadius == null ) throw new IllegalStateException("Vision radius cannot be null.");
        if ( showVision == null ) throw new IllegalStateException("Show vision cannot be null.");

        Action action = agent.getAction();
        if ( action == null ) throw new IllegalStateException("Action cannot be null.");

    // ======================== Action being executed here ======================= //

        // Convert the requested action from current position to the new position
        Position newPosition = applyAction(action, currentPosition);
        
    // =========================================================================== //

        // Get all possible actions at this new position.
        List<Action> actionList = super.getPossibleActions(newPosition);
        // Get the vision seen from this new position.
        Vision vision = super.getVision(newPosition.getX(), newPosition.getY(), visionRadius);
        // Add the vision to the new State Attributes to be passed on the State
        StateAttributes stateAttributes = new StateAttributesImpl(0, vision);
        // Create the new state object to be given to the Agent.
        State currentState = new StateImpl(actionList, stateAttributes);
        // The reward found at this state by the environment.
        Double reward = getPixel(newPosition.getX(),newPosition.getY()).getReward();
        // Time to update the display if required.
        Thread graphicalThread = environmentAgentHandler.getDisplayThreadHandler();
        GraphicalDisplay graphicalDisplay = environmentAgentHandler.getDisplayHandler();
        if ( showVision && graphicalThread.isAlive() ) {
            graphicalDisplay.run();
        }
        else {
            graphicalDisplay.hide();
        }

        // Update the agent with the current new state and the reward found at this state.
        agent.set(currentState, reward);
// TODO: Clean up this when done debugging.
System.out.println(environmentAgentHandler.getAgentId()+ " = " +action+"["+currentPosition.getX()+","+currentPosition.getY()+"]->["+newPosition.getX()+","+newPosition.getY()+"]");
        // Calculate the final EnvironmentAgentHanlder object and return it.
        return new EnvironmentAgentHandlerImpl(agent, visionRadius, newPosition, agentId, 
                environmentAgentHandler.isShowVision(), 
                environmentAgentHandler.getDisplayHandler(),
                environmentAgentHandler.getDisplayThreadHandler());
    }

    /**
     * Exit all remaining active threads.
     */
    private void exitAll() {
        // Go over each Environment Agent handler and quit every live thread.
        for(EnvironmentAgentHandler environmentAgentHandler : environmentAgentHanlderList ) {

            // Thread to close.
            Thread closeThread = environmentAgentHandler.getDisplayThreadHandler();

            // Check if it was initialised first.
            if ( closeThread != null ) {

                // Now check if it is alive.
                if ( closeThread.isAlive() ) {

                    // Let's get the graphical display, and issue a shutdown
                    GraphicalDisplay graphicalDisplay = environmentAgentHandler.getDisplayHandler();
                    if ( graphicalDisplay != null ) graphicalDisplay.shutdown();
                }
            }
        }
    }

    /**
     * The turn-off switch.
     */
    @Override
    public void shutdown() {
        // Exit all threads here before quitting this one.
        exitAll();

        // Ready to switch the mains off.
        this.exit = true;

        // Give 1 second for everything to close and avoid surprises..
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the full environment.
     */
    @Override
    public void showEnvironment() {
        super.show();
    }

    /**
     * Hides the full environment.
     */
    @Override
    public void hideEnvironment() {
        super.hide();
    }

    /**
     * Shows the Agent's vision.
     */
    @Override
    public void showAgent(Agent agent) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");

        // Set the display to show the Agent's vision.
        setVision(agent, true);
    }

    /**
     * Hides the Agent's vision.
     */
    @Override
    public void hideAgent(Agent agent) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");

        // Set the display to not show the Agent's vision.
        setVision(agent, false);
    }

    /**
     * Set the vision for the given agent to ON or OFF
     * 
     * @param agent to update
     * @param vision on or off
     */
    private void setVision(Agent agent, Boolean vision) {
        // Validate arguments.
        if ( agent == null ) throw new IllegalArgumentException("Agent cannot be null.");
        if ( vision == null ) throw new IllegalArgumentException("Vision cannot be null.");

        // Find the respective Agent.
        for(EnvironmentAgentHandler environmentAgentHandler : environmentAgentHanlderList ) {

            // Retrieve the agent object address to compare.
            Agent agentFound = environmentAgentHandler.getAgent();

            // Same address.
            if ( agent.equals(agentFound) ) {

                // Set the vision to be displayed
                environmentAgentHandler.setVision(vision);

                // All done, nothing more to be done.
                return;
            }
        }
    }
}