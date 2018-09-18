package assignment_2;

import java.io.IOException;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

/**
 * This is the main class for the program, and the start application
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		MazeSolver a_star = new MazeSolver();
		a_star.run();
	}
	
}
