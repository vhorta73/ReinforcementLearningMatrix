package agent.utilities;

import java.util.Comparator;

import agent.interfaces.ActionValue;
/**
 * Comparator for ActionValue lists.
 * 
 * @author Vasco
 *
 */
public class ComparatorActionValue implements Comparator<ActionValue> {
	/**
	 * Comparing values highest to lowest.
	 */
	@Override
	public int compare(ActionValue o1, ActionValue o2) {
		if ( o1.getValue() < o2.getValue() ) return 1;
		if ( o1.getValue() > o2.getValue() ) return -1;
		return 0;
	}
}
