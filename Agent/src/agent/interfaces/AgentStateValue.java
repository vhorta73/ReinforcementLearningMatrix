package agent.interfaces;
/**
 * The AgentState interface.
 * 
 * Extending State for calculation of the state value
 * in the way the Agent sees fit.
 * 
 * @author Vasco
 *
 */
public interface AgentStateValue extends State {
	/**
	 * The unique state value id.
	 * 
	 * This State id is generated depending on specific
	 * deciding factors. By default, it would be just a
	 * hashcode() generated integer
	 * 
	 * @return Integer state value
	 */
	public Integer getStateId();
}
