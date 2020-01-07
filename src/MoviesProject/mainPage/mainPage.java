package MoviesProject.mainPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainPage extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPageEvent.fxml"));
		
		Parent root = loader.load();
		mainCtrl mainCtrl = loader.getController();
		Scene mainScene = new Scene(root);
		mainCtrl.setRoot(root);
		mainCtrl.setStage(primaryStage, mainScene);
		
		mainScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Anton&display=swap");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
