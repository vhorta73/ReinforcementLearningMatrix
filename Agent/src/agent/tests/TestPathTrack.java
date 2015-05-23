package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.impl.ActionValueImpl;
import agent.impl.PathTrackImpl;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.impl.VisionImpl;
import agent.interfaces.ActionValue;
import agent.interfaces.PathTrack;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * Testing PathTrack implementation.
 * 
 * @author Vasco
 *
 */
public class TestPathTrack {
	/**
	 * PathTrack handler.
	 */
	private PathTrack pathTrack;
	
	/**
	 * Generic previous State.
	 */
	private State previousState;
	
	/**
	 * Generic current State.
	 */
	private State currentState;
	
	/**
	 * All Actions list.
	 */
	private List<Action> allActions;
	
	/**
	 * The State attributes.
	 */
	private StateAttributes stateAttributes;

	/**
	 * The state attribute value.
	 */
	private final Integer STATE_ATTRIBUTE_VALUE = 10;
	
	/**
	 * Vision state attribute.
	 */
	private Vision vision;

	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		vision = new VisionImpl(new Integer[10][20][3]);
		stateAttributes = new StateAttributesImpl(STATE_ATTRIBUTE_VALUE, vision);
		// Initialising all actions list.
		allActions = new LinkedList<Action>();
		for ( Action action : Action.values() ) {
			allActions.add(action);
		}
		
		previousState = new StateImpl(allActions, stateAttributes);
		currentState = new StateImpl(allActions, stateAttributes);
		pathTrack = new PathTrackImpl(0.8, 10);
	}

	/**
	 * Testing exception on invalid state list actions stored.
	 */
	@Test(expected=IllegalStateException.class)
	public void testExceptionOnWrongActionFoundInState() {
		// Initialise a new previous state with only one action possible.
		List<Action> newActions = new LinkedList<Action>();
		newActions.add(Action.DOWN);
		previousState = new StateImpl(newActions, stateAttributes);

		// Add event from previous state, applying an action that is not listed.
		pathTrack.addEvent(previousState, Action.LOWER_RIGHT, currentState, 1.0);
	}
	
	/**
	 * Testing exception on invalid gamma value.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionOnInvalidGamma() {
		new PathTrackImpl(0.0, 100);
	}
	
	/**
	 * Testing exception on invalid negative gamma value.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionOnInvalidNegativeGamma() {
		new PathTrackImpl(-10.0, 100);
	}

	/**
	 * Testing exception on bigger than 1 gamma value.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionOnInvalidTooBigGamma() {
		new PathTrackImpl(1.00000000001, 100);
	}

	/**
	 * Test on null previous state.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddEventOnNullPState() {
		pathTrack.addEvent(null, Action.DOWN, currentState, 0.0);
	}

	/**
	 * Test on null current state.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddEventOnNullCState() {
		pathTrack.addEvent(previousState, Action.DOWN, null, 0.0);
	}

	/**
	 * Test on null action.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddEventOnNullAction() {
		pathTrack.addEvent(previousState, null, currentState, 0.0);
	}

	/**
	 * Test not found action on previous state exception.
	 */
	@Test(expected=IllegalStateException.class)
	public void testAddEventOnActionNotInPState() {
		List<Action> actions = new LinkedList<Action>();
		actions.add(Action.IDLE);
		previousState = new StateImpl(actions, stateAttributes);
		pathTrack.addEvent(previousState, Action.DOWN, currentState, 0.0);
	}

	/**
	 * Test previous State with no actions available.
	 */
	@Test(expected=IllegalStateException.class)
	public void testAddEventOnPStateWithNoActions() {
		List<Action> actions = new LinkedList<Action>();
		previousState = new StateImpl(actions, stateAttributes);
		pathTrack.addEvent(previousState, Action.DOWN, currentState, 0.0);
	}

	/**
	 * Test current State with no actions available.
	 */
	@Test(expected=IllegalStateException.class)
	public void testAddEventOnCStateWithNoActions() {
		List<Action> actions = new LinkedList<Action>();
		currentState = new StateImpl(actions, stateAttributes);
		pathTrack.addEvent(previousState, Action.DOWN, currentState, 0.0);
	}

	/**
	 * Integration testing the best Action.
	 */
	@Test
	public void testGetBestAction() {
		List<Action> previousActions = new LinkedList<Action>();
		previousActions.add(Action.DOWN);
		previousActions.add(Action.UP);
		previousActions.add(Action.LEFT);
		previousActions.add(Action.LOWER_RIGHT);
		State previousState = new StateImpl(previousActions, stateAttributes);
		State currentState = new StateImpl(previousActions, stateAttributes);

		pathTrack.addEvent(previousState, Action.DOWN, currentState, 0.0);
		pathTrack.addEvent(previousState, Action.UP, currentState, 0.0);
		pathTrack.addEvent(previousState, Action.LEFT, currentState, 0.0);
		pathTrack.addEvent(previousState, Action.LOWER_RIGHT, currentState, 1.0);

		Action bestAction = pathTrack.getBestAction(previousState);
		assertNotNull(bestAction);
		assertEquals(Action.LOWER_RIGHT, bestAction);

		// Actions returned sorted by value bigger to smaller.
		List<ActionValue> actions = pathTrack.getActions(previousState);
		assertNotNull(actions);
		assertTrue(actions.size() > 0);

		// Actions added in the order that it is expected to be returned
		// The value is not important to be tested as long as values decrease.
		List<ActionValue> expected = new LinkedList<ActionValue>();
		expected.add(new ActionValueImpl(Action.LOWER_RIGHT, 0.0));
		expected.add(new ActionValueImpl(Action.LEFT, 0.0));
		expected.add(new ActionValueImpl(Action.UP, 0.0));
		expected.add(new ActionValueImpl(Action.DOWN, 0.0));
		verifyActionListSorted(expected, actions);
	}
	
	/**
	 * Check between two action value lists to see if they are sorted by value.
	 * 
	 * @param expected ActionValue list
	 * @param found ActionValue list
	 */
	private void verifyActionListSorted(List<ActionValue> expected, List<ActionValue> found) {
		// Initial checks
		assertNotNull(found);
		assertEquals(expected.size(), found.size());
		
		// Check if Actions are sorted in the same way as expected and next value is smaller.
		int index = 0;
		Double previousActionValue = null;

		// Go over the found list to check on values.
		for( ActionValue foundActionValue : found ) {
			// Action must match on same index.
			assertEquals(expected.get(index).getAction(), foundActionValue.getAction());
			
			// First time, set previous value as it should be the biggest value on all list.
			if ( previousActionValue == null ) previousActionValue = foundActionValue.getValue();
			// Subsequent values are expected to be smaller all the way to the end.
			else {
				assertTrue(previousActionValue >= foundActionValue.getValue());
				previousActionValue = foundActionValue.getValue();
			}
			// Increase the index for the expected list.
			index++;
		}
	}
}
