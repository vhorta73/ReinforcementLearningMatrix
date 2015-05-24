package agent.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import agent.impl.AgentImpl;
import agent.impl.StateAttributesImpl;
import agent.impl.StateImpl;
import agent.impl.VisionImpl;
import agent.interfaces.Agent;
import agent.interfaces.State;
import agent.interfaces.StateAttributes;
import agent.interfaces.Vision;

/**
 * Unit testing Agent implementation.
 * 
 * @author Vasco
 *
 */
public class TestAgent {
    /**
     * The averaging sample rate.
     */
    private final Integer AVERAGING_SAMPLE_RATE = 100;

    /**
     * The gamma value for the reward back-propagation.
     */
    private final Double GAMMA = 0.8;

    /**
     * The Agent handler.
     */
    private Agent agent;

    /**
     * A state to test with.
     */
    private State state;

    /**
     * The State Attributes.
     */
    private StateAttributes stateAttributes;

    /**
     * The State Vision.
     */
    private Vision vision;

    /**
     * The state attribute value.
     */
    private final Integer STATE_ATTRIBUTE_VALUE = 12;

    /**
     * The required initial initialisation 
     * for each test.
     */
    @Before
    public void before() {
        vision = new VisionImpl(new Integer[50][5][3]);
        stateAttributes = new StateAttributesImpl(STATE_ATTRIBUTE_VALUE, vision);
        List<Action> actionList = new LinkedList<Action>();
        actionList.add(Action.UP);
        state = new StateImpl(actionList, stateAttributes);
        agent = new AgentImpl(GAMMA, AVERAGING_SAMPLE_RATE);
    }

    /**
     * Check that at initialisation isActionReady is false
     */
    @Test
    public void testAgentInitialAction() {
        assertFalse(agent.isReadyForAction());
    }

    /**
     * Check action is not ready.
     */
    @Test
    public void testIsActionReadyNot() {
        assertFalse(agent.isReadyForAction());
    }

    /**
     * Check action is not ready before first state is given.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetActionException() {
        agent.getAction();
    }

    /**
     * Check action is ready before after first state is given.
     */
    @Test
    public void testIsActionReady() {
        agent.set(state, 1.2);
        agent.isReadyForAction();
    }

    /**
     * Check state is ready after state given and action received.
     */
    @Test
    public void testIsStateReady() {
        // Tell the agent which new state its in and which actions available
        List<Action> actionList = new LinkedList<Action>();
        actionList.add(Action.UP);
        state = new StateImpl(actionList, stateAttributes);
        agent.set(state, 0.0);

        // Give the Agent some time to think
        while(!agent.isReadyForAction());

        // Collect the ready action
        Action action = agent.getAction();
        // Check it is not null
        assertNotNull(action);
        // Check that we have got the one and only.
        assertEquals(Action.UP,action);

        // Tell the agent which action its in now and which actions available
        actionList = new LinkedList<Action>();
        actionList.add(Action.LOWER_LEFT);
        state = new StateImpl(actionList, stateAttributes);
        agent.set(state, 0.0);

        // Give the Agent some time to think
        while(!agent.isReadyForAction());

        // Collect the ready action
        action = agent.getAction();
        // Check it is not null
        assertNotNull(action);
        // Check that we have got the one and only.
        assertEquals(Action.LOWER_LEFT,action);

        // Set the new state
        agent.set(state, 0.0);
    }
}