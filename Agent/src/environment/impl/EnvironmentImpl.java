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
	 * Thread sleeping time.
	 */
	private final Long SLEEP = 1000L;
	
	/**
	 * The list of active agents on the environment.
	 */
    private List<EnvironmentAgentHandler> agents;
    
    /**
     * The exiting flag.
     */
    private boolean exit = false;

    /**
     * Constructor.
     */
    public EnvironmentImpl(Pixel[][] matrix) {
    	// Load given matrix.
    	super(matrix);
    	agents = new LinkedList<EnvironmentAgentHandler>();
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
		Position position = new PositionImpl(5, 5);

		// Check if this agent already exists, and get the highest agentId.
		Integer highestId = 0;
		Integer previousId = 0;
		for (EnvironmentAgentHandler environmentAgentHandler : agents ) {
			Agent alreadyAdded = environmentAgentHandler.getAgent();
			if ( alreadyAdded.equals(agent) ) {
				throw new IllegalStateException("Cannot add same agent twice.");
			}
			previousId = environmentAgentHandler.getAgentId();
			if ( highestId < previousId ) highestId = environmentAgentHandler.getAgentId();
		}
		
// TODO: Setup what would be the initial State through a method.
// TODO: Auto-generation of the State at xy coordinates.
		// Create the new EnvironmentAgentHandler and add it to the list.
		EnvironmentAgentHandler newAgent = new EnvironmentAgentHandlerImpl(agent, visionRadius, position, highestId + 1);
		agents.add(newAgent);
		Double reward = 0.0;
		List<Action> actionList = new LinkedList<Action>();
		actionList.add(Action.IDLE);
		Vision vision = super.getVision(5, 5, visionRadius);
		StateAttributes stateAttributes = new StateAttributesImpl(0, vision);
		State currentState = new StateImpl(actionList, stateAttributes);
		newAgent.getAgent().set(currentState, reward);
	}

	/**
	 * Remove an Agent from the environment.
	 */
	@Override
	public void removeAgent(Agent agent) {
		if ( agent == null ) throw new IllegalArgumentException("Cannot remove a null Agent.");

		for( int i = 0; i < agents.size(); i++ ) {
			Agent foundAgent = agents.get(i).getAgent();
			if ( agent.equals(foundAgent) ) agents.remove(i);
		}
	}

	/**
	 * The Runnable.
	 */
	@Override
	public void run() {
		// Run forever until requested to exit
		while(!exit) {
			// Loop through each agent to collect ready actions and reply with new state.
			for(EnvironmentAgentHandler environmentAgentHandler : agents ) {
				Agent agent = environmentAgentHandler.getAgent();
				if ( agent.isReadyForAction() ) {
					Position position = environmentAgentHandler.getAgentPosition();
					Integer agentId = environmentAgentHandler.getAgentId();
					Integer radius = environmentAgentHandler.getVisionRadius();
					System.out.println("["+agentId+"] ("+position.getX()+","+position.getY()+") <"+radius+"> = "+agent);
				}
			}
			try {
				Thread.sleep(SLEEP);
				System.out.println("Sleepting 1s");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The turn-off switch.
	 */
	@Override
	public void exit() {
		this.exit = true;
	}
}