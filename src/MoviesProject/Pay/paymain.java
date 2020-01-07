package MoviesProject.Pay;

import java.io.IOException;

//import ex02.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class paymain extends Application {
@Override
public void start(Stage primaryStage) throws IOException {
	FXMLLoader loader = 
			new FXMLLoader(getClass().getResource("mainfx.fxml"));
	Parent root = loader.load();
	
	controller con = loader.getController();
	con.setRoot(root);
	
	Scene scene = new Scene(root);
	primaryStage.setTitle("결제하기");
	primaryStage.setScene(scene);
	primaryStage.show();
}

	public static void main(String[] args) {
		launch(args);
	}
}


