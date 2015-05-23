package environment.interfaces;

import agent.interfaces.Agent;

/**
 * Envirionment Agent Handler interface.
 * 
 * @author Vasco
 *
 */
public interface EnvironmentAgentHandler {
	/**
	 * The Agent id.
	 * 
	 * @return Integer ID
	 */
	public int getAgentId();
	
	/**
	 * The Agent radius.
	 * 
	 * @return Integer radius
	 */
	public int getVisionRadius();
	
	/**
	 * The Agent Position
	 * 
	 * @return Position
	 */
	public Position getAgentPosition();
	
	/**
	 * The Agent.
	 * 
	 * @return Agent
	 */
	public Agent getAgent();

}