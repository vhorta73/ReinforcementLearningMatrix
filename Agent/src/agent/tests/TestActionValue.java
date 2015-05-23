package agent.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.impl.ActionValueImpl;
import agent.interfaces.ActionValue;

/**
 * Tests for the ActionValue implementation.
 * 
 * @author Vasco
 *
 */
public class TestActionValue {
	/**
	 * The ActionValue handler.
	 */
	private ActionValue actionValue;

	/**
	 * The Action to test with.
	 */
	private final Action ACTION = Action.IDLE;
	
	/**
	 * The value to test with.
	 */
	private final Double VALUE = 2.34;
			
	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		actionValue = new ActionValueImpl(ACTION, VALUE);
	}
	
	@Test
	public void testAction() {
		assertEquals(ACTION, actionValue.getAction());
	}
	
	@Test
	public void testValue() {
		assertEquals(VALUE, actionValue.getValue());
	}
}
