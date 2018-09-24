package assignment_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Hans Bjerkevoll
 *
 */

/**
 * This is the main class for the program, and the start application
 */


public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("TDT4136 Assignment 2");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}