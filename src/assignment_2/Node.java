package assignment_2;

/**
 * 
 * @author Hans Bjerkevoll
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
	
	private int distance;
	private double est_cost;
	private int node_cost;
	
	private int x_cord, y_cord;
	
	public Node (int x_cord, int y_cord, char nodetype) throws IllegalArgumentException{
		
		this.x_cord = x_cord;
		this.y_cord = y_cord;
		
		if(nodetype == 'A') {
			this.nodetype = NodeType.START;
			this.node_cost = 1;
		} else if (nodetype == 'B') {
			this.nodetype = NodeType.GOAL;
			this.node_cost = 1;
		} else if (nodetype == '.') {
			this.nodetype = NodeType.OPEN;
			this.node_cost = 1;
		} else if (nodetype == '#') {
			this.nodetype = NodeType.BLOCK;
			this.node_cost = Integer.MAX_VALUE;
		} else if(nodetype == 'w') {
			this.nodetype = NodeType.WATER;
			this.node_cost = 100;
		} else if(nodetype == 'm') {
			this.nodetype = NodeType.MOUNTAIN;
			this.node_cost = 50;
		} else if(nodetype == 'f') {
			this.nodetype = NodeType.FOREST;
			this.node_cost = 10;
		} else if(nodetype == 'g') {
			this.nodetype = NodeType.GRASSLAND;
			this.node_cost = 5;
		} else if(nodetype == 'r') {
			this.nodetype = NodeType.ROAD;
			this.node_cost = 1;
		} else {
			throw new IllegalArgumentException("Incorrect nodetype");
		}
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
	
	public int getPathCost(){
		return this.node_cost;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public void setEstCost(double est_cost) {
		this.est_cost = est_cost;
	}
	
	public double getEstCost() {
		return this.est_cost;
	}

}
