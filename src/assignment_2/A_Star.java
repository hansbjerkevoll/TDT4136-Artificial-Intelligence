package assignment_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import assignment_2.Node.NodeType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

public class A_Star {
	
	private ArrayList<ArrayList<Node>> nodes;
	private Node start_node, goal_node;
	
	public void run() throws IOException {
		String boardname = "board-2-1";
		this.nodes = readFile("./boards/" + boardname + ".txt");
		estimatedCosts();
		Image image = printImage(nodes);
		image = resample(image);
		try {
			File file = new File("./solutions/" + boardname + ".png");
			file.createNewFile();
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
	
	/**
	 * Prints image based on node list. One pixel per node.
	 * 
	 * @param nodes
	 * @return
	 */
	private Image printImage(ArrayList<ArrayList<Node>> nodes) {
		WritableImage wi = new WritableImage(nodes.get(0).size(), nodes.size());
		PixelWriter pw = wi.getPixelWriter();
		
		// Colors Part 1
		Color open_color = new Color(255d/255, 255d/255, 255d/255, 1.0);
		Color block_color = new Color(0d/255, 0d/255, 0d/255, 1.0);
		Color start_color = new Color(225d/255, 0d/255, 0d/255, 1.0);
		Color goal_color = new Color(127d/255, 255d/255, 0d/255, 1.0);
		
		// Colors Part 2
		Color water_color = new Color(0d/255, 0d/255, 200d/255, 1.0);
		Color mountain_color = new Color(100d/255, 100d/255, 100d/255, 1.0);
		Color forest_color = new Color(34d/255, 139d/255, 34d/255, 1.0);
		Color grass_color = new Color(144d/255, 238d/255, 144d/255, 1.0);
		Color road_color = new Color(205d/255, 133d/255, 63d/255, 1.0);
		
		for(ArrayList<Node> node_row : nodes) {
			for(Node node : node_row) {
				if(node.getNodeType() == NodeType.OPEN) {
					pw.setColor(node.getXCord(), node.getYCord(), open_color);
				} else if(node.getNodeType() == NodeType.BLOCK) {
					pw.setColor(node.getXCord(), node.getYCord(), block_color);
				} else if(node.getNodeType() == NodeType.START) {
					pw.setColor(node.getXCord(), node.getYCord(), start_color);
				} else if(node.getNodeType() == NodeType.GOAL) {
					pw.setColor(node.getXCord(), node.getYCord(), goal_color);
				} else if(node.getNodeType() == NodeType.WATER) {
					pw.setColor(node.getXCord(), node.getYCord(), water_color);
				} else if(node.getNodeType() == NodeType.MOUNTAIN) {
					pw.setColor(node.getXCord(), node.getYCord(), mountain_color);
				} else if(node.getNodeType() == NodeType.FOREST) {
					pw.setColor(node.getXCord(), node.getYCord(), forest_color);
				} else if(node.getNodeType() == NodeType.GRASSLAND) {
					pw.setColor(node.getXCord(), node.getYCord(), grass_color);
				} else if(node.getNodeType() == NodeType.ROAD) {
					pw.setColor(node.getXCord(), node.getYCord(), road_color);
				} 
			}
		}
		
		return wi;
		
	}
	
	/**
	 * Resamples a given image
	 * 
	 * @param input
	 * @return
	 */
	private Image resample(Image input) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final double S = Math.min(750d / (double) W, 750d / (double) H);

		WritableImage output = new WritableImage((int) (W * S) + 1, (int) (H * S) + 1);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb((int) (x * S + dx), (int) (y * S + dy), argb);
					}
				}
			}
		}

		return output;
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
