package assignment_2;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcessing {
	
	/**
	 * Creates an image that respresents the map and visulizes the solution
	 * 
	 * @param nodes
	 * @param solution
	 * @return An full hd image visualizing the map and the solution
	 */
	public static Image createSolutionImage(ArrayList<ArrayList<Node>> nodes, ArrayList<Node> solution) {
		Image node_img = createNodeImage(nodes);
		return resample(node_img, solution);
	}
	
	/**
	 * Creates an image object based on node list. One pixel per node.
	 * 
	 * @param nodes
	 * @return An Image object with one pixel per node in the correct color
	 */
	private static Image createNodeImage(ArrayList<ArrayList<Node>> nodes) {
		WritableImage wi = new WritableImage(nodes.get(0).size(), nodes.size());
		PixelWriter pw = wi.getPixelWriter();
		
		Color start_color = new Color(225d/255, 0d/255, 0d/255, 1.0);
		Color goal_color = new Color(127d/255, 255d/255, 0d/255, 1.0);
		
		// Colors Part 1
		Color open_color = new Color(255d/255, 255d/255, 255d/255, 1.0);
		Color block_color = new Color(0d/255, 0d/255, 0d/255, 1.0);
		
		// Colors Part 2
		Color water_color = new Color(20d/255, 20d/255, 255d/255, 1.0);
		Color mountain_color = new Color(150d/255, 150d/255, 150d/255, 1.0);
		Color forest_color = new Color(34d/255, 139d/255, 34d/255, 1.0);
		Color grass_color = new Color(144d/255, 238d/255, 144d/255, 1.0);
		Color road_color = new Color(205d/255, 133d/255, 63d/255, 1.0);
		
		for(ArrayList<Node> node_row : nodes) {
			for(Node node : node_row) {
				
				// Find the correct color to print, based on the node
				Color node_color = null;
				
				switch (node.getNodeType()) {
				case START:
					node_color = start_color;
					break;
				case GOAL:
					node_color = goal_color;
					break;
				
				// Part 1
				case OPEN:
					node_color = open_color;
					break;
				case BLOCK:
					node_color = block_color;
					break;
				
				// Part 2
				case WATER:
					node_color = water_color;
					break;
				case MOUNTAIN:
					node_color = mountain_color;
					break;
				case FOREST:
					node_color = forest_color;
					break;
				case GRASSLAND:
					node_color = grass_color;
					break;
				case ROAD:
					node_color = road_color;
					break;
					
				default:
					break;
				}
				
				pw.setColor(node.getXCord(), node.getYCord(), node_color);
			}
		}		
		
		return wi;
		
	}
	
	/**
	 * Resamples a given image up to FULL HD with width = 1920px or height = 1080px
	 * Prints the borders on every pixel
	 * Prints a dot in the middle of the node if the pixel is part of the solution
	 * 
	 * @param The image to be resampled
	 * @return The resampled image 
	 */
	private static Image resample(Image input, ArrayList<Node> solution) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final double S = Math.min(1920d / (double) W, 1080d / (double) H);

		WritableImage output = new WritableImage((int) (W * S) + 1, (int) (H * S) + 1);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();
		
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				
				// Test if pixel is part of solution, and dot should be printed
				boolean print_dot = false;
				for(Node node : solution) {
					if(node.getYCord() == y && node.getXCord() == x) {
						print_dot = true;
						break;
					}
				}
						
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						// Print the outline for each node
						if((dy == 0 || dx == 0)) {
							writer.setArgb((int) (x * S + dx), (int) (y * S + dy), -16777216);	
						} 
						// Print a dot in the middle, if node is in solution
						else if(print_dot && (dx < ( S/2 + 3) && dx > ( S/2 - 3) && dy < ( S/2 + 3) && dy > ( S/2 - 3))) {
							writer.setArgb((int) (x * S + dx), (int) (y * S + dy), -16777216);	
						}
						// Print the color of the node
						else {
							writer.setArgb((int) (x * S + dx), (int) (y * S + dy), argb);
						}
						
					}
				}
			}
		}

		return output;
	}

}
