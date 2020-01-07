package MoviesProject.MovieInfo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main_notused extends Application {
	private FXMLLoader loader;
	private Parent root;
	@Override
	public void start(Stage primaryStage) throws IOException {
		loader = new FXMLLoader(getClass().getResource("mainFrame.fxml"));
		root = loader.load();
		Parent d = null;
		TabController tabcon = loader.getController();
		tabcon.setRoot(root, d);
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("영화 상세보기");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {launch(args);}
}
