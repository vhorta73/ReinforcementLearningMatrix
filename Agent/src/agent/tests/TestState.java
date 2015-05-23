package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.impl.VisionImpl;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * State unit tests.
 * 
 * @author Vasco
 *
 */
public class TestState {
	/**
	 * The State handler.
	 */
	private State state;
	
	/**
	 * The action list for this state.
	 */
	private List<Action> actionList;

	/**
	 * The State attributes.
	 */
	private StateAttributes stateAttributes;

	/**
	 * The Vision state attribute.
	 */
	private Vision vision;
	
	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		vision = new VisionImpl(new Integer[10][10][3]);
		stateAttributes = new StateAttributesImpl(1, vision);
		actionList = new LinkedList<Action>();
		actionList.add(Action.UPPER_LEFT);
		actionList.add(Action.UP);
		actionList.add(Action.UPPER_RIGHT);
		actionList.add(Action.LEFT);
		actionList.add(Action.IDLE);
		actionList.add(Action.RIGHT);
		actionList.add(Action.LOWER_LEFT);
		actionList.add(Action.DOWN);
		actionList.add(Action.LOWER_RIGHT);
		state = new StateImpl(actionList, stateAttributes);
	}
	
	/**
	 * Test actions list.
	 */
	@Test
	public void testActionsAvailable() {
		List<Action> foundList = state.getActions();
		verifyActionListContents(actionList, foundList);
	}
	
	/**
	 * Test expected exception for null action list.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullActionListException() {
		new StateImpl(null, stateAttributes);
	}

	/**
	 * Test expected exception for null state attributes.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullStateAttributesException() {
		new StateImpl(actionList, null);
	}

	/**
	 * Test default stateId not null.
	 */
	@Test
	public void testStateIdNotNull() {
		Integer stateId = state.getStateId();
		assertNotNull(stateId);
	}

	/**
	 * Test same stateId for same attributes and actions list.
	 */
	@Test
	public void testSameStateIdForSameActionsAndAttributes() {
		// The same list.
		List<Action> newActionList = new LinkedList<Action>();
		newActionList.add(Action.UPPER_LEFT);
		newActionList.add(Action.UP);
		newActionList.add(Action.UPPER_RIGHT);
		newActionList.add(Action.LEFT);
		newActionList.add(Action.IDLE);
		newActionList.add(Action.RIGHT);
		newActionList.add(Action.LOWER_LEFT);
		newActionList.add(Action.DOWN);
		newActionList.add(Action.LOWER_RIGHT);

		// Same state attributes.
		StateAttributes newStateAttributes = new StateAttributesImpl(1, vision);

		// Should generate the same state ID.
		State newState = new StateImpl(actionList, newStateAttributes);

		// Get the old and the new state ID and compare if equal.
		Integer stateId = state.getStateId();
		Integer newStateId = newState.getStateId();
		assertNotNull(stateId);
		assertNotNull(newStateId);
		assertEquals(stateId, newStateId);
	}

	/**
	 * Test duplicated actions in list exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDuplicatedActionsException() {
		actionList.add(Action.LEFT);
		state = new StateImpl(actionList, stateAttributes);
	}

	/**
	 * Checks if elements of the expected action list,
	 * exist all in the given found action list.
	 * 
	 * @param expected action list
	 * @param found action list
	 */
	private void verifyActionListContents(List<Action> expected, List<Action> found) {
		assertNotNull(found);
		assertEquals(expected.size(), found.size());
		for( Action actionExpected : expected ) {
			assertTrue(found.contains(actionExpected));
		}
	}
}
