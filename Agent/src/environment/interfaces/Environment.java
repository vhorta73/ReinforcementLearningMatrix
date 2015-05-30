package environment.interfaces;

import agent.interfaces.Agent;

/**
 * The Environment interface.
 * 
 * @author Vasco
 *
 */
public interface Environment extends Runnable {
    /**
     * Add a new Agent to the environment.
     * 
     * @param agent
     */
    public void addAgent(Agent agent, Integer visionRadius);

    /**
     * Remove the Agent from the environment.
     * 
     * @param agent
     */
    public void removeAgent(Agent agent);

    /**
     * Closing all actions and exiting.
     */
    public void exit();

    /**
     * Displays the environment matrix.
     */
    public void showEnvironment();

    /**
     * Hides the environment matrix
     */
    public void hideEnvironment();

    /**
     * Shows Agent's vision as the Agent sees it.
     * 
     * @param agent the Agent
     */
    public void showAgent(Agent agent);

    /**
     * Hides the Agent's vision.
     * 
     * @param agent the Agent
     */
    public void hideAgent(Agent agent);
}