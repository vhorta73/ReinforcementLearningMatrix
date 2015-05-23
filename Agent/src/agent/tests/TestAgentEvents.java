package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.impl.AgentEventsImpl;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.impl.VisionImpl;
import agent.interfaces.ActionValue;
import agent.interfaces.AgentEvents;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * Testing AgentEvents implementation.
 * 
 * @author Vasco
 *
 */
public class TestAgentEvents {
	/**
	 * The AgentEvents handler.
	 */
	private AgentEvents agentEvents;
	
	/**
	 * The generic complete State action list.
	 */
	private List<Action> actionList;

	/**
	 * The previous State action list.
	 */
	private List<Action> previousActionList;

	/**
	 * The current State action list.
	 */
	private List<Action> currentActionList;

	/**
	 * The previous State.
	 */
	private State previousState;
	
	/**
	 * Action taken from previous state.
	 */
	private Action ACTION = Action.LOWER_LEFT;
	
	/**
	 * The current State.
	 */
	private State currentState;
	
	/**
	 * Reward gained at the current State.
	 */
	private final Double REWARD = 1.0;

	/**
	 * The default averaging samples.
	 */
	private final int AVERAGING_SAMPLES = 100;

	/**
	 * The Current State Attributes.
	 */
	private StateAttributes currentStateAttributes;
	
	/**
	 * The Previous State Attributes.
	 */
	private StateAttributes previousStateAttributes;
	
	/**
	 * The Vision state attribute.
	 */
	private Vision vision;
	
	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		vision = new VisionImpl(new Integer[2][3][3]);
		currentStateAttributes = new StateAttributesImpl(1, vision);
		previousStateAttributes = new StateAttributesImpl(5, vision);
		previousActionList = new LinkedList<Action>();
		currentActionList  = new LinkedList<Action>();
		actionList         = new LinkedList<Action>();

		actionList.add(Action.UPPER_LEFT);
		previousActionList.add(Action.UPPER_LEFT);
		currentActionList.add(Action.UPPER_LEFT);

		actionList.add(Action.UP);
		previousActionList.add(Action.UP);
		currentActionList.add(Action.UP);
		
		actionList.add(Action.UPPER_RIGHT);
		previousActionList.add(Action.UPPER_RIGHT);
		currentActionList.add(Action.UPPER_RIGHT);

		previousState = new StateImpl(previousActionList, previousStateAttributes);

		actionList.add(Action.LEFT);
		currentActionList.add(Action.LEFT);

		actionList.add(Action.IDLE);
		currentActionList.add(Action.IDLE);

		actionList.add(Action.RIGHT);
		currentActionList.add(Action.RIGHT);

		actionList.add(Action.LOWER_LEFT);
		currentActionList.add(Action.LOWER_LEFT);

		currentState = new StateImpl(currentActionList, currentStateAttributes);

		actionList.add(Action.DOWN);
		actionList.add(Action.LOWER_RIGHT);

		agentEvents = new AgentEventsImpl(AVERAGING_SAMPLES);
		setAgentEvents();
	}

	/**
	 * Testing exception at constructor time with invalid averaging samples.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionAtConstructoZero() {
	    new AgentEventsImpl(0);	
	}

	/**
	 * Testing exception at constructor time with invalid averaging samples.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionAtConstructoNegative() {
	    new AgentEventsImpl(-10);	
	}

	/**
	 * Testing exception on null previous state.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionPreviousState() {
	    agentEvents.addEvent(null, ACTION, currentState, REWARD);	
	}

	/**
	 * Testing exception on null action.
	 */
    @Test(expected=IllegalArgumentException.class)
	public void testExceptionAction() {
	    agentEvents.addEvent(previousState, null, currentState, REWARD);	
	}

	/**
	 * Testing exception on null current state.
	 */
    @Test(expected=IllegalArgumentException.class)
	public void testExceptionCurrentState() {
	    agentEvents.addEvent(previousState, ACTION, null, REWARD);	
	}
	
	/**
	 * Testing exception on invalid action for previous state.
	 */
    @Test(expected=IllegalStateException.class)
	public void testExceptionPreviousStateAction() {
	    agentEvents.addEvent(previousState, Action.IDLE, currentState, REWARD);	
	}

	/**
	 * Testing exception on empty action current state list.
	 */
    @Test(expected=IllegalStateException.class)
	public void testExceptionEmptyListCurrentState() {
    	List<Action> emtpyList = new LinkedList<Action>();
    	previousState = new StateImpl(emtpyList, previousStateAttributes);
	    agentEvents.addEvent(previousState, ACTION, currentState, REWARD);	
	}

	/**
	 * Testing best action at State.
	 */
	@Test
	public void testBestActionAtState() {
		Action actionExpected = previousActionList.get(1);
		assertNotNull(actionExpected);
		agentEvents.addEvent(previousState, actionExpected, currentState, REWARD*2);
	    Action bestActionFound = agentEvents.getBestAction(previousState);	
	    assertNotNull(bestActionFound);
	    assertEquals(actionExpected, bestActionFound);
	}

	/**
	 * Testing best action at State null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBestActionAtStateNull() {
		Action actionExpected = previousActionList.get(1);
		assertNotNull(actionExpected);
		agentEvents.addEvent(previousState, actionExpected, currentState, REWARD*2);
	    agentEvents.getBestAction(null);	
	}

	/**
	 * Testing best action at State with no actions available.
	 */
	@Test(expected=IllegalStateException.class)
	public void testBestActionAtStateNoActions() {
		agentEvents.addEvent(previousState, previousState.getActions().get(0), currentState, REWARD*2);
		List<Action> noActions = new LinkedList<Action>();
		previousState = new StateImpl(noActions, previousStateAttributes);
	    agentEvents.getBestAction(previousState);	
	}

	/**
	 * Test if the list of Actions returned match the expected visited.
	 */
	@Test
	public void testGetActions() {
		List<ActionValue> foundActions = agentEvents.getActions(previousState);
		assertNotNull(foundActions);
		verifyActionList(previousActionList, foundActions);

		foundActions = agentEvents.getActions(currentState);
		assertNotNull(foundActions);
		verifyActionList(currentActionList, foundActions);
	}

	/**
	 * Test exception on null state
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetActionsNullState() {
		agentEvents.getActions(null);
	}
	
	/**
	 * Test exception on state with no actions.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetActionsFromStateWithNoActions() {
		previousState = new StateImpl(new LinkedList<Action>(), previousStateAttributes);
		agentEvents.getActions(previousState);
	}
	
	/**
	 * Check if the added event was really added.
	 */
	@Test
	public void testAddEvent() {
		Action actionExpected = previousActionList.get(0);
		assertNotNull(actionExpected);
		agentEvents.addEvent(previousState, actionExpected, currentState, REWARD*2);
		Action foundAction = agentEvents.getBestAction(previousState);
		assertNotNull(foundAction);
		assertEquals(actionExpected, foundAction);
	}

	/**
	 * Check if the added event from initial state was really added 
	 * when creating a new state with same attributes.
	 */
	@Test
	public void testAddEventInitialState() {
		List<Action> actions = new LinkedList<Action>();
		actions.add(Action.DOWN);
		previousState = new StateImpl(actions, new StateAttributesImpl(5, vision));
		agentEvents.addEvent(previousState, Action.DOWN, currentState, REWARD*2);
		
		actions = new LinkedList<Action>();
		actions.add(Action.DOWN);
		previousState = new StateImpl(actions, new StateAttributesImpl(5, vision));
		
		Action foundAction = agentEvents.getBestAction(previousState);
		assertNotNull(foundAction);
		assertEquals(Action.DOWN, foundAction);
	}

	/**
	 * The expected best action is UP
	 */
	private void setAgentEvents() {
		for( Action action : previousActionList ) {
			agentEvents.addEvent(previousState, action, currentState, REWARD);
		}

		for( Action action : currentActionList ) {
			agentEvents.addEvent(currentState, action, previousState, REWARD);
		}
	}

	/**
	 * Verify that both given expected and found Action list are equal.
	 * 
	 * @param expected Action list
	 * @param found Action list
	 */
	private void verifyActionList(List<Action> expected, List<ActionValue> found) {
		assertNotNull(found);
		assertEquals(expected.size(), found.size());
		for ( ActionValue actionValueFound : found ) {
			Action action = actionValueFound.getAction();
			assertTrue(expected.contains(action));
		}
	}
}
