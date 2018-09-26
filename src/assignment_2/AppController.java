package assignment_2;

import assignment_2.Node.NodeType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

/**
 * 
 * Controller class for the JavaFX application
 *
 */
public class AppController {
	
	/**
	 * FXML Variables
	 */
	@FXML VBox root;
	@FXML ImageView imageview;
	@FXML Button solve_button, load_button;
	@FXML Label board_label, est_label;
	@FXML ChoiceBox<String> alg_choicebox, est_choicebox;
	@FXML CheckBox solution_check, open_check, closed_check;
	
	
	private ArrayList<ArrayList<Node>> nodes;
	private Node start_node, goal_node;
	
	private String root_folder = "./src/assignment_2/";
	private File loaded_board;
	
	/**
	 * Initilize.
	 * Runs on App startup
	 */
	public void initialize() {
		
		// Set default values
		alg_choicebox.setValue("A* (A-Star)");
		est_choicebox.setValue("Euclidian");
		// Add items to choicebox
		alg_choicebox.setItems(FXCollections.observableArrayList("A* (A-Star)", "Dijkstra", "BFS", "DFS"));
		est_choicebox.setItems(FXCollections.observableArrayList("Euclidian", "Manhattan"));
		
		// Show or hide estimate choice
		alg_choicebox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> {
			est_label.setVisible("A* (A-Star)".equals(newv));
			est_choicebox.setVisible("A* (A-Star)".equals(newv));			
		});
		
		// Load board on load click
		load_button.setOnAction(e -> {
			
			// Show window to choose board
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("Load Board");
			filechooser.getExtensionFilters().addAll(new ExtensionFilter("Text files", "*.txt"));
			filechooser.setInitialDirectory(new File(root_folder));

			File selected_file = filechooser.showOpenDialog((Stage) root.getScene().getWindow());
			if (selected_file != null) {
				loaded_board = selected_file;
				board_label.setText(selected_file.getName());
				
				// Read and show unsolved board
				try {
					this.nodes = readFile(loaded_board);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image image = ImageProcessing.createSolutionImage(nodes, null, null, null);
				imageview.setImage(image);
			}
		});
		
		// Solve loaded board on solve button click
		solve_button.setOnAction(e -> {
			
			// If board not loaded => do nothing
			if(loaded_board == null) {
				return;
			}
			
			solveBoard(loaded_board);		
			
		});
		
	}
	
	/**
	 * Update the estimated cost for all the nodes
	 */
	private void estimatedCosts() {
		for(ArrayList<Node> node_row : nodes) {
			for(Node node : node_row) {
				if(est_choicebox.getValue().equals("Euclidian")) {
					euclidianHeuristic(node, goal_node);
				} else if(est_choicebox.getValue().equals("Euclidian")) {
					manhattanHeuristic(node, goal_node);
				}
				
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
	 * Solves and display a given board file
	 * 
	 * @param loaded_board
	 */
	public void solveBoard(File loaded_board) {
		estimatedCosts();
		
		SearchAlgorithms search = new SearchAlgorithms(start_node, nodes);
					
		ArrayList<Node> solution = null;
		
		// Get selected algorithm
		if(alg_choicebox.getValue().equals("A* (A-Star)")) {
			solution = search.AStarSearch();
		} else if (alg_choicebox.getValue().equals("Dijkstra")) {
			solution = search.DijkstraSearch();
		} else if (alg_choicebox.getValue().equals("BFS")) {
			solution = search.BreadthFirstSearch();
		} else if (alg_choicebox.getValue().equals("DFS")) {
			solution = search.DepthFirstSearch();
		}
		
		solution = solution_check.isSelected() ? solution : null;
		ArrayList<Node> open = open_check.isSelected() ? search.getOpenNodes() : null;
		ArrayList<Node> closed = closed_check.isSelected() ? search.getClosedNodes() : null;
		
		Image image = ImageProcessing.createSolutionImage(nodes, solution, open, closed);
		imageview.setImage(image);
		
		
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
