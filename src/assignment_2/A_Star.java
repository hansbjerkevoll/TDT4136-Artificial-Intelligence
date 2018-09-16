package assignment_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import assignment_2.Node.NodeType;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

public class A_Star {
	
	private ArrayList<ArrayList<Node>> nodes;
	private Node start_node, goal_node;
	
	public void run() throws IOException {
		this.nodes = readFile("./boards/board-1-1.txt");
		estimatedCosts();
		printNodes();
		
	}
	
	/**
	 * Update the estimated cost for all the nodes
	 */
	private void estimatedCosts() {
		for(ArrayList<Node> node_row : nodes) {
			for(Node node : node_row) {
				manhattanHeuristic(node, goal_node);
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
	private ArrayList<ArrayList<Node>> readFile(String filepath) throws IOException {
		
		ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
		
		File file = new File(filepath);
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
	
	private void printNodes() {
		for(ArrayList<Node> node_list : nodes) {
			String line = "";
			for(Node node : node_list) {
				line += node.getEstCost() + "\t";
			}
			System.out.println(line);
			line = "";
		}
	}

}
