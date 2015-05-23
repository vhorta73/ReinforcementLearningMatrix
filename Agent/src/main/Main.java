package main;

import agent.impl.AgentImpl;
import agent.interfaces.Agent;
import environment.impl.EnvironmentImpl;
import environment.impl.PixelImpl;
import environment.interfaces.Environment;
import environment.interfaces.Pixel;

/**
 * The Main package to test it all..
 * 
 * @author Vasco
 *
 */
public class Main {
	public static void main(String[] args) {
		Pixel[][] matrix = new Pixel[10][10];
		for( int i = 0; i < matrix.length; i++ ) {
			for ( int j = 0 ; j < matrix[0].length; j++ ) {
				matrix[i][j] = new PixelImpl(23, 11, 56, false, 0.0);
			}
		}
		Environment env = new EnvironmentImpl(matrix);
		Thread a = new Thread(env);
		a.start();
		Agent aa = new AgentImpl(0.8, 100);
		Agent ab = new AgentImpl(0.8, 100);
		try {
			Thread.sleep(2500);
			env.addAgent(aa, 4);
			env.addAgent(ab, 4);
			Thread.sleep(2500);
			System.out.println("Time is up");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		env.exit();
	}
}
