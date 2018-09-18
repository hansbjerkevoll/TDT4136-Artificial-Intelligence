package assignment_2;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

/**
 * 
 * This class represents a single node in the maze
 *
 */
public class Node {
	
	/**
	 * Enumerative Datatype to keep track of all the nodetypes
	 */
	enum NodeType {
		// Part 1
		START,
		GOAL,
		OPEN,
		BLOCK,
		
		// Part 2
		WATER,
		MOUNTAIN,
		FOREST,
		GRASSLAND,
		ROAD
		
	}
	
	private NodeType nodetype;
	
	private Node parent;
	
	private double distance = Double.POSITIVE_INFINITY;
	private double est_cost;
	private Integer node_cost;
	
	private int x_cord, y_cord;
	
	public Node (int x_cord, int y_cord, char nodetype) throws IllegalArgumentException{
		
		this.x_cord = x_cord;
		this.y_cord = y_cord;
		
		switch(nodetype) {
			case 'A':
				this.nodetype = NodeType.START;
				this.node_cost = 0;
				break;
			case 'B':
				this.nodetype = NodeType.GOAL;
				this.node_cost = 0;
				break;
			
			// Part 1
			case '.':
				this.nodetype = NodeType.OPEN;
				this.node_cost = 1;
				break;
			case '#':
				this.nodetype = NodeType.BLOCK;
				this.node_cost = null;
				break;
			
			// Part 2
			case 'w':
				this.nodetype = NodeType.WATER;
				this.node_cost = 100;
				break;
			case 'm':
				this.nodetype = NodeType.MOUNTAIN;
				this.node_cost = 50;
				break;
			case 'f':
				this.nodetype = NodeType.FOREST;
				this.node_cost = 10;
				break;
			case 'g':
				this.nodetype = NodeType.GRASSLAND;
				this.node_cost = 5;
				break;
			case 'r':
				this.nodetype = NodeType.ROAD;
				this.node_cost = 1;
				break;
			default:
				throw new IllegalArgumentException("Incorrect nodetype");
		}

	}
	
	/**
	 * 
	 * Setters and getters for the Node objects
	 * 
	 */
	
	public void setParent(Node node) {
		this.parent = node;
	}
	
	public Node getParent() {
		return this.parent;
	}
	
	public int getXCord() {
		return this.x_cord;
	}
	
	public int getYCord() {
		return this.y_cord;
	}
	
	public NodeType getNodeType() {
		return this.nodetype;
	}
	
	public double getNodeCost(){
		return this.node_cost;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public void setEstCost(double est_cost) {
		this.est_cost = est_cost;
	}
	
	public double getEstCost() {
		return this.est_cost;
	}
	
	public Double getTotalExpectedCost() {
		return est_cost + distance;
	}

}
