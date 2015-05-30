package environment.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agent.impl.AgentImpl;
import agent.interfaces.Agent;
import environment.impl.EnvironmentAgentHandlerImpl;
import environment.impl.PositionImpl;
import environment.interfaces.EnvironmentAgentHandler;
import environment.interfaces.Position;


/**
 * Testing the Environment Agent Handler implementation.
 * 
 * @author Vasco
 *
 */
public class TestEnvironmentAgentHandler {
    /**
     * The handler for the EnvironmentAgentHandler implementation.
     */
    private EnvironmentAgentHandler environmentAgentHandler;

    /**
     * The agent.
     */
    private Agent agent;

    /**
     * The Agent vision radius.
     */
    private Integer VISION_RADIUS = 5;

    /**
     * Agent's position.
     */
    private Position position;

    /**
     * Position X.
     */
    private int X = 23;

    /**
     * Position Y
     */
    private int Y = 45;

    /**
     * The agent ID.
     */
    private Integer AGENT_ID = 34;

    /**
     * The show vision flag.
     */
    private final Boolean SHOW_VISION = true;

    @Before
    public void before() {
        position = new PositionImpl(X, Y);
        agent = new AgentImpl(0.8, 100);
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(agent, VISION_RADIUS, position, AGENT_ID, SHOW_VISION);
    }

    /**
     * Created agent id should be ID.
     */
    @Test
    public void testAgentId() {
        Integer foundAgentId = environmentAgentHandler.getAgentId();
        assertNotNull(foundAgentId);
        assertEquals(AGENT_ID, foundAgentId);
    }

    /**
     * Test vision radius.
     */
    @Test
    public void testVisionRadius() {
        Integer visionRadiusFound = environmentAgentHandler.getVisionRadius();
        assertNotNull(visionRadiusFound);
        assertEquals(VISION_RADIUS, visionRadiusFound);
    }

    /**
     * Test the agent's position.
     */
    @Test
    public void testAgentPosition() {
        Position positionFound = environmentAgentHandler.getAgentPosition();
        assertNotNull(positionFound);
        int foundX = positionFound.getX();
        int foundY = positionFound.getY();
        assertEquals(X,foundX);
        assertEquals(Y,foundY);
    }

    /**
     * The Agent.
     */
    @Test
    public void testGetAgent() {
        Agent foundAgent = environmentAgentHandler.getAgent();
        assertNotNull(foundAgent);
        assertEquals(agent,foundAgent);
    }

    /**
     * The vision flag.
     */
    @Test
    public void testVisionFlag() {
        Boolean foundFlag = environmentAgentHandler.isShowVision();
        assertNotNull(foundFlag);
        assertEquals(SHOW_VISION,foundFlag);
    }

    /**
     * The vision flag set.
     */
    @Test
    public void testSetVisionFlag() {
        environmentAgentHandler.setVision(true);
        Boolean foundFlag = environmentAgentHandler.isShowVision();
        assertNotNull(foundFlag);
        assertTrue(foundFlag);

        environmentAgentHandler.setVision(false);
        foundFlag = environmentAgentHandler.isShowVision();
        assertNotNull(foundFlag);
        assertFalse(foundFlag);
    }

    /**
     * Test exception with Agent is null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullAgent() {
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(null, VISION_RADIUS, position, AGENT_ID, SHOW_VISION);
    }

    /**
     * Test exception with radius is null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullRadius() {
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(agent, null, position, AGENT_ID, SHOW_VISION);
    }

    /**
     * Test exception with position is null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullPosition() {
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(agent, VISION_RADIUS, null, AGENT_ID, SHOW_VISION);
    }

    /**
     * Test exception with Agent Id is null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullAgentId() {
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(agent, VISION_RADIUS, position, null, SHOW_VISION);
    }

    /**
     * Test exception with null Show vision flag.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullVisionFlag() {
        environmentAgentHandler = new EnvironmentAgentHandlerImpl(agent, VISION_RADIUS, position, AGENT_ID, null);
    }

    /**
     * Test exception with null set vision flag.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullSetVisionFlag() {
        environmentAgentHandler.setVision(null);
    }
}
