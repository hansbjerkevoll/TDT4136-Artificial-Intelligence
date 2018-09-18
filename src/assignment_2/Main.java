package assignment_2;

import java.io.IOException;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */
public class Main {
	
	/**
	 * This is the main class for the program, and the start application
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MazeSolver a_star = new MazeSolver();
		a_star.run();
	}
	
}
