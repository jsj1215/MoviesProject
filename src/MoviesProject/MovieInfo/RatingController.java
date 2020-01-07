package MoviesProject.MovieInfo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RatingController implements Initializable{
	@FXML ComboBox<String> ComB_Rating;
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	public void BTN_serrating() {
		if(ComB_Rating.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("오류");
			alert.setHeaderText("평점 입력 오류.");
			alert.setContentText("점수를 선택하셔야 합니다.");
			alert.showAndWait();
			alert.close();
		}
		else{System.out.println(ComB_Rating.getValue() + " 주기");}
	}
}