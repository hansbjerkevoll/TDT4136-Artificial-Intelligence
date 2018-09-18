package assignment_2;

import assignment_2.Node.NodeType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

public class MazeSolver {
	
	private ArrayList<ArrayList<Node>> nodes;
	private Node start_node, goal_node;
	
	private String root = "./src/assignment_2/";
	
	/**
	 * Runs the MazeSolving algorithms on all the mazes in the folder "boards"
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		
		File board_folder = new File(root + "boards");
		File[] board_list = board_folder.listFiles();
		
		// Solve all the boards in the boards folder
		for(File file : board_list) {
			this.nodes = readFile(file);
			estimatedCosts();
			
			ArrayList<Node> solution = new SearchAlgorithms().DepthFirstSearch(start_node, nodes);
			Image image = ImageProcessing.createSolutionImage(nodes, solution);
			File img_file = new File(root + "solutions/Depth-First Search/" + file.getName().replaceAll(".txt", "") + ".png");
			file.createNewFile();
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", img_file);
		}
			
	}
	
	/**
	 * Update the estimated cost for all the nodes
	 */
	private void estimatedCosts() {
		for(ArrayList<Node> node_row : nodes) {
			for(Node node : node_row) {
				//manhattanHeuristic(node, goal_node);
				euclidianHeuristic(node, goal_node);
			}
		}
	}
	
	/**
	 *  Update estimated cost for single node, using Manhattan Distance
	 *  
	 *  @param node
	 *  	the node to update the cost
	 *  @param goal_node
	 *  	the node to calculate the distance to
	 *  
	 */
	private void manhattanHeuristic(Node node, Node goal_node) {
		int x_cost = Math.abs(node.getXCord() - goal_node.getXCord());
		int y_cost = Math.abs(node.getYCord() - goal_node.getYCord());
		node.setEstCost(x_cost + y_cost);
	}
	
	/**
	 *  Update estimated cost for single node, using Euclidian Distance
	 *  
	 *  @param node
	 *  	the node to update the cost
	 *  @param goal_node
	 *  	the node to calculate the distance to
	 *  
	 */
	private void euclidianHeuristic(Node node, Node goal_node) {
		int x_cost = Math.abs(node.getXCord() - goal_node.getXCord());
		int y_cost = Math.abs(node.getYCord() - goal_node.getYCord());
		double euc_cost = round(Math.sqrt(Math.pow(x_cost, 2) + Math.pow(y_cost, 2)), 1);		
		node.setEstCost(euc_cost);
	}
	
	
	/**
	 * Reads the file from the given filepath, 
	 * returns a two-dimensional list with the nodes read from the file
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	private ArrayList<ArrayList<Node>> readFile(File file) throws IOException {
		
		ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String board_row;
		int x_cord = 0;
		int y_cord = 0;
		while((board_row = br.readLine()) != null) {
			char[] br_list = board_row.toCharArray();
			ArrayList<Node> node_list = new ArrayList<>();
			for(char c : br_list) {
				Node node = new Node(x_cord, y_cord, c);
				node_list.add(node);
				if(node.getNodeType() == NodeType.START) {
					node.setDistance(0);
					start_node = node;
				}
				if(node.getNodeType() == NodeType.GOAL) {
					goal_node = node;
				}
				x_cord++;
			}
			nodes.add(node_list);
			x_cord = 0;
			y_cord++;
		}
		
		br.close();
		
		return nodes;
		
	}
	
	/**
	 * 
	 * Rounds a given value to the chosen precision
	 * 
	 * @param value
	 * @param precision
	 * @return a runded double value to the given precision
	 * @throws IllegalArgumentException
	 */
	private static double round (double value, int precision) throws IllegalArgumentException {
		if(precision < 0) {
			throw new IllegalArgumentException("Precision cannot be a negative number");
		}
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

}
