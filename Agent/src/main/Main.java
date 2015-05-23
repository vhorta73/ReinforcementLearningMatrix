package main;

import agent.impl.AgentImpl;
import environment.impl.EnvironmentImpl;
import environment.interfaces.Environment;

/**
 * The Main package to test it all..
 * 
 * @author Vasco
 *
 */
public class Main {

	public static void main(String[] args) {
		Environment env = new EnvironmentImpl();
		Thread a = new Thread(env);
		a.start();
		try {
			Thread.sleep(2500);
			env.addAgent(new AgentImpl(0.8, 100), 4);
			Thread.sleep(2500);
			System.out.println("Time is up");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		env.exit();
	}
}
