package environment.impl;

import java.util.LinkedList;
import java.util.List;

import agent.interfaces.Agent;
import environment.interfaces.Environment;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.Position;

/**
 * Environment implementation.
 * 
 * @author Vasco
 *
 */
public class EnvironmentImpl implements Environment {
	/**
	 * Thread sleeping time.
	 */
	private final Long SLEEP = 1000L;
	
	/**
	 * The list of active agents on the environment.
	 */
    private List<EnvironmentAgentHandler> agents;
    
    /**
     * The exitting flag.
     */
    private boolean exit = false;

    /**
     * Constructor.
     */
    public EnvironmentImpl() {
    	agents = new LinkedList<EnvironmentAgentHandler>();
	}

    /**
     * Add a new Agent to the environment.
     */
	@Override
	public void addAgent(Agent agent, Integer visionRadius) {
		// Validate arguments.
		if ( agent == null ) throw new IllegalArgumentException("Cannot add a null Agent.");

		Position position = new PositionImpl(0, 0);

		// Check if this agent already exists, and for the highest agentId.
		Integer highestId = 0;
		Integer previousId = 0;
		for (EnvironmentAgentHandler environmentAgentHandler : agents ) {
			Agent alreadyAdded = environmentAgentHandler.getAgent();
			if ( alreadyAdded.equals(agent) ) return;
			previousId = environmentAgentHandler.getAgentId();
			if ( highestId < previousId ) highestId = environmentAgentHandler.getAgentId();
		}
		
		// Create the new EnvironmentAgentHandler and add it to the list.
		EnvironmentAgentHandler newAgent = new EnvironmentAgentHandlerImpl(agent, visionRadius, position, highestId + 1);
		agents.add(newAgent);
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
		while(!exit) {
			for(EnvironmentAgentHandler environmentAgentHandler : agents ) {
				Agent agent = environmentAgentHandler.getAgent();
				Position position = environmentAgentHandler.getAgentPosition();
				Integer agentId = environmentAgentHandler.getAgentId();
				Integer radius = environmentAgentHandler.getVisionRadius();
				System.out.println("["+agentId+"] ("+position.getX()+","+position.getY()+") <"+radius+"> = "+agent);
			}
			try {
				Thread.sleep(SLEEP);
				System.out.println("Sleepting 1s");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void exit() {
		System.out.println("Exiting");
		this.exit = true;
	}
}