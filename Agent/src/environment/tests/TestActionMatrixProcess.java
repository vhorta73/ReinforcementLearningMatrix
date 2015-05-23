package environment.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agent.constants.Action;
import environment.impl.ActionMatrixProcess;
import environment.impl.PositionImpl;
import environment.interfaces.Position;

/**
 * Unit tests for the ActionMatixProcess implementation.
 * 
 * @author Vasco
 *
 */
public class TestActionMatrixProcess {
	/**
	 * The ActionMatrixProcess handler.
	 */
	private ActionMatrixProcess actionMatrixProcess;
	
	/**
	 * The initial position to use.
	 */
	private Position position;
	
	/**
	 * X coordinate.
	 */
	private final int X = 34;
	
	/**
	 * Y coordinate.
	 */
	private final int Y = 23;
	
	/**
	 * Initialisations.
	 */
	@Before
	public void before() {
		position = new PositionImpl(X, Y); 
		actionMatrixProcess = new ActionMatrixProcess();
	}

	/**
	 * Testing if all actions have been implemented.
	 */
	@Test
	public void testIfAllActionsAreBuilt() {
		for( Action action : Action.values() ) {
			Position foundPosition = actionMatrixProcess.execute(action, position);
			// If failing as null, it means that an action has not yet been implemented.
			assertNotNull(foundPosition);
		}
	}
	
	/**
	 * Testing action UPPER_LEFT.
	 */
	@Test
	public void testActionUPPERLEFT() {
		Position foundPosition = actionMatrixProcess.execute(Action.UPPER_LEFT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X-1, Y-1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action UP.
	 */
	@Test
	public void testActionUP() {
		Position foundPosition = actionMatrixProcess.execute(Action.UP, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X, Y-1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action UPPER_RIGHT.
	 */
	@Test
	public void testActionUPPERRITGH() {
		Position foundPosition = actionMatrixProcess.execute(Action.UPPER_RIGHT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X+1, Y-1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action LEFT.
	 */
	@Test
	public void testActionLEFT() {
		Position foundPosition = actionMatrixProcess.execute(Action.LEFT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X-1, Y);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action IDLE.
	 */
	@Test
	public void testActionIDLE() {
		Position foundPosition = actionMatrixProcess.execute(Action.IDLE, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X, Y);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action RIGHT.
	 */
	@Test
	public void testActionRIGHT() {
		Position foundPosition = actionMatrixProcess.execute(Action.RIGHT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X+1, Y);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action LOWER_LEFT.
	 */
	@Test
	public void testActionLOWERLEFT() {
		Position foundPosition = actionMatrixProcess.execute(Action.LOWER_LEFT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X-1, Y+1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action DOWN.
	 */
	@Test
	public void testActionDOWN() {
		Position foundPosition = actionMatrixProcess.execute(Action.DOWN, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X, Y+1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Testing action LOWERRIGHT.
	 */
	@Test
	public void testActionLOWERRIGHT() {
		Position foundPosition = actionMatrixProcess.execute(Action.LOWER_RIGHT, position);
		assertNotNull(foundPosition);
		Position expectedPosition = new PositionImpl(X+1, Y+1);
		assertNotNull(expectedPosition);
		verify(expectedPosition, foundPosition);
	}

	/**
	 * Verify if both positions are the same.
	 * 
	 * @param expected Position
	 * @param found Position
	 */
	private void verify(Position expected, Position found) {
		assertNotNull(expected);
		assertNotNull(found);
		assertEquals(expected.getX(),found.getX());
		assertEquals(expected.getY(),found.getY());
	}
}
