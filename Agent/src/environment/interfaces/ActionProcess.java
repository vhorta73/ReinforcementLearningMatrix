package environment.interfaces;

import agent.constants.Action;

/**
 * The ActionProcess interface.
 * 
 * Definitions on how Actions should transform the given data
 * are to be executed at the implementation.
 * 
 * @author Vasco
 *
 */
public interface ActionProcess<T> {
	/**
	 * The given action will transform the given object into
	 * another object of the same type.
	 * 
	 * @param action requested
	 * @param objectToTransform 
	 * @return T
	 */
	public T execute(Action action, T objectToTransform);
}
