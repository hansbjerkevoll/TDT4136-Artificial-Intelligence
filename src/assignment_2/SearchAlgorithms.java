package assignment_2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import assignment_2.Node.NodeType;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */
public class SearchAlgorithms {
	
	/** 
	 * Searches the list of nodes, using A* search
	 * 
	 * @param start
	 * @param nodes
	 * @return a list of nodes containing the solution
	 */
	public ArrayList<Node> AStarSearch(Node start, ArrayList<ArrayList<Node>> nodes) {
		
		// Nodes already evaluated
		ArrayList<Node> closed = new ArrayList<>();
		// Discovered nodes not evaluated
		ArrayList<Node> open = new ArrayList<>();
		
		open.add(start);
		
		Node goal_node = null;
		
		// Agenda loop
		while(goal_node == null) {
			
			// Failed search -> return null
			if(open.size() == 0) return null;
			
			// Finds the next node to evaluate, based on f(s) = g(s) + h(s)
			Node current_node = null;
			for(Node node : open) {
				if(current_node == null) current_node = node;
				else if(node.getTotalExpectedCost() < current_node.getTotalExpectedCost()) {
					current_node = node;
				}
			}
			
			// Found goal -> return node
			if(current_node.getNodeType() == NodeType.GOAL) {
				goal_node = current_node;
				break;
			}
			
			open.remove(current_node);
			closed.add(current_node);
			
			ArrayList<Node> successors = getSuccessors(current_node, nodes);
			for(Node succ_node : successors) {
				// Skip nodes already evalutated
				if(closed.contains(succ_node)) {
					continue;
				}
				
				// Skip block nodes
				if(succ_node.getNodeType() == NodeType.BLOCK) {
					continue;
				}
				
				// Tentative distance from 
				double tentative_score = current_node.getDistance() + succ_node.getNodeCost();
				
				// Discover a new node
				if(!open.contains(succ_node)) {
					open.add(succ_node);
				}
				// Not a better path
				else if(tentative_score >= succ_node.getDistance()) {
					continue;
				}
				
				// Best path until now
				succ_node.setParent(current_node);
				succ_node.setDistance(tentative_score);
				
			}
		}
		

		ArrayList<Node> solution = reconstruct_solution(goal_node);
			
		return solution;
		
	}
	
	/** 
	 * Searches the list of nodes, using Dijkstra search
	 * 
	 * @param start
	 * @param nodes
	 * @return a list of nodes containing the solution
	 */
	public ArrayList<Node> DijkstraSearch(Node start, ArrayList<ArrayList<Node>> nodes){
		// Nodes already evaluated
		ArrayList<Node> closed = new ArrayList<>();
		// Discovered nodes not evaluated
		ArrayList<Node> open = new ArrayList<>();
		
		open.add(start);
		
		Node goal_node = null;
		
		// Agenda loop
		while(goal_node == null) {
			
			// Failed search -> return null
			if(open.size() == 0) return null;
			
			Node current_node = null;
			for(Node node : open) {
				if(current_node == null) current_node = node;
				else if(node.getDistance() < current_node.getDistance()) {
					current_node = node;
				}
			}
			
			// Found goal -> return node
			if(current_node.getNodeType() == NodeType.GOAL) {
				goal_node = current_node;
				break;
			}
			
			open.remove(current_node);
			closed.add(current_node);
			
			ArrayList<Node> successors = getSuccessors(current_node, nodes);
			for(Node succ_node : successors) {
				// Skip nodes already evalutated
				if(closed.contains(succ_node)) {
					continue;
				}
				
				// Skip block nodes
				if(succ_node.getNodeType() == NodeType.BLOCK) {
					continue;
				}
				
				// Tentative distance from 
				double tentative_score = current_node.getDistance() + succ_node.getNodeCost();
				
				// Discover a new node
				if(!open.contains(succ_node)) {
					open.add(succ_node);
				}
				// Not a better path
				else if(tentative_score >= succ_node.getDistance()) {
					continue;
				}
				
				// Best path until now
				succ_node.setParent(current_node);
				succ_node.setDistance(tentative_score);
				
			}
		}
		

		ArrayList<Node> solution = reconstruct_solution(goal_node);
		
		
		return solution;
	}
	
	/** 
	 * Searches the list of nodes, using Breadth First Search
	 * 
	 * @param start
	 * @param nodes
	 * @return a list of nodes containing the solution
	 */
	public ArrayList<Node> BreadthFirstSearch(Node start, ArrayList<ArrayList<Node>> nodes){
		// Nodes already evaluated
		ArrayList<Node> closed = new ArrayList<>();
		// Discovered nodes not evaluated
		Stack<Node> open = new Stack<>();
		
		open.add(start);
		
		Node goal_node = null;
		
		// Agenda loop
		while(goal_node == null) {
			
			// Failed search -> return null
			if(open.size() == 0) return null;
			
			Node current_node = open.pop();
			
			// Found goal -> return node
			if(current_node.getNodeType() == NodeType.GOAL) {
				goal_node = current_node;
				break;
			}
			
			open.remove(current_node);
			closed.add(current_node);
			
			ArrayList<Node> successors = getSuccessors(current_node, nodes);
			for(Node succ_node : successors) {
				// Skip nodes already evalutated
				if(closed.contains(succ_node)) {
					continue;
				}
				
				// Skip block nodes
				if(succ_node.getNodeType() == NodeType.BLOCK) {
					continue;
				}
				
				// Tentative distance from 
				double tentative_score = current_node.getDistance() + succ_node.getNodeCost();
				
				// Discover a new node
				if(!open.contains(succ_node)) {
					open.add(succ_node);
				}
				// Not a better path
				else if(tentative_score >= succ_node.getDistance()) {
					continue;
				}
				
				// Best path until now
				succ_node.setParent(current_node);
				succ_node.setDistance(tentative_score);
				
			}
		}
		

		ArrayList<Node> solution = reconstruct_solution(goal_node);
		
		
		return solution;
	}
	
	
	/** 
	 * Searches the list of nodes, using Depth First Search
	 * (implemented dfs to demonstrate the differences between bfs and dfs)
	 * (solutions to dfs can be found in separate folder)
	 * 
	 * @param start
	 * @param nodes
	 * @return a list of nodes containing the solution
	 */
	public ArrayList<Node> DepthFirstSearch(Node start, ArrayList<ArrayList<Node>> nodes){
		// Nodes already evaluated
		ArrayList<Node> closed = new ArrayList<>();
		// Discovered nodes not evaluated
		Queue<Node> open = new LinkedList<>();
		
		open.add(start);
		
		Node goal_node = null;
		
		// Agenda loop
		while(goal_node == null) {
			
			// Failed search -> return null
			if(open.size() == 0) return null;
			
			Node current_node = open.poll();
			
			// Found goal -> return node
			if(current_node.getNodeType() == NodeType.GOAL) {
				goal_node = current_node;
				break;
			}
			
			open.remove(current_node);
			closed.add(current_node);
			
			ArrayList<Node> successors = getSuccessors(current_node, nodes);
			for(Node succ_node : successors) {
				// Skip nodes already evalutated
				if(closed.contains(succ_node)) {
					continue;
				}
				
				// Skip block nodes
				if(succ_node.getNodeType() == NodeType.BLOCK) {
					continue;
				}
				
				// Tentative distance from 
				double tentative_score = current_node.getDistance() + succ_node.getNodeCost();
				
				// Discover a new node
				if(!open.contains(succ_node)) {
					open.add(succ_node);
				}
				// Not a better path
				else if(tentative_score >= succ_node.getDistance()) {
					continue;
				}
				
				// Best path until now
				succ_node.setParent(current_node);
				succ_node.setDistance(tentative_score);
				
			}
		}
		

		ArrayList<Node> solution = reconstruct_solution(goal_node);
		
		
		return solution;
	}
	
	/**
	 * Traverses from the goal node back to the start node.
	 * 
	 * @param goal_node
	 * @return a list of nodes, containing the solution
	 */
	private ArrayList<Node> reconstruct_solution(Node goal_node) {
		
		ArrayList<Node> solution = new ArrayList<Node>();
		solution.add(goal_node);
		
		Node parent = goal_node.getParent();
		
		while(parent != null) {
			solution.add(parent);
			parent = parent.getParent();
		}
		
		return solution;
		
	}
	
	/**
	 * Checks and finds the 4 possible neighbour for a given node
	 * 
	 * @param node
	 * @return a list of neighbours
	 */
	private ArrayList<Node> getSuccessors(Node node, ArrayList<ArrayList<Node>> nodes){
		
		ArrayList<Node> successors = new ArrayList<>();
		
		// Check left
		if(node.getXCord() > 0) {
			successors.add(nodes.get(node.getYCord()).get(node.getXCord() - 1));
		}
		
		// Check up
		if(node.getYCord() > 0) {
			successors.add(nodes.get(node.getYCord() - 1).get(node.getXCord()));
		}
		
		// Check right
		if(node.getXCord() < nodes.get(0).size() - 1) {
			successors.add(nodes.get(node.getYCord()).get(node.getXCord() + 1));
		}
		
		// Check down
		if(node.getYCord() < nodes.size() - 1) {
			successors.add(nodes.get(node.getYCord() + 1).get(node.getXCord()));
		}
		
		return successors;
		
	}

}
