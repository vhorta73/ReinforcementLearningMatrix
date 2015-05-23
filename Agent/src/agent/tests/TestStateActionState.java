package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.impl.StateActionStateImpl;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.impl.VisionImpl;
import agent.interfaces.Action;
import agent.interfaces.State;
import agent.interfaces.StateActionState;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * Tests for the StateActionState implementation.
 * 
 * @author Vasco
 *
 */
public class TestStateActionState {
	/**
	 * The StateActionState handler.
	 */
	private StateActionState stateActionState;
	
	/**
	 * Previous State.
	 */
	private State previousState;
	
	/**
	 * Action to test with.
	 */
	private Action action;
	
	/**
	 * The current State.
	 */
	private State currentState;
	
	/**
	 * The state attributes.
	 */
	private StateAttributes stateAttributes;

	/**
	 * The Vision state attribute.
	 */
	private Vision vision;
	
	/**
	 * Initialisation.
	 */
	@Before
	public void before() {
		vision = new VisionImpl(new Integer[10][10][3]);
		stateAttributes = new StateAttributesImpl(34, vision);
		List<Action> actionList = new LinkedList<Action>();
		actionList.add(Action.DOWN);
		previousState = new StateImpl(actionList, stateAttributes);
		currentState = new StateImpl(actionList, stateAttributes);
		action = Action.DOWN;
		stateActionState = new StateActionStateImpl(previousState, action, currentState);
	}
	
	/**
	 * Testing previous State.
	 */
	@Test
	public void testPreviousState() {
		State foundPreviousState = stateActionState.getPreviousState();
		assertNotNull(foundPreviousState);
		assertEquals(previousState, foundPreviousState);
	}

	/**
	 * Testing current State.
	 */
	@Test
	public void testCurrentState() {
		State foundCurrentState = stateActionState.getCurrentState();
		assertNotNull(foundCurrentState);
		assertEquals(currentState, foundCurrentState);
	}

	/**
	 * Testing Action.
	 */
	@Test
	public void testAction() {
		Action foundAction = stateActionState.getAction();
		assertNotNull(foundAction);
		assertEquals(action, foundAction);
	}
}
