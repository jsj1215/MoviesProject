package MoviesProject.MovieInfo;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import MoviesProject.SelectPage.SelectCtrl;
import MoviesProject.ViewList.viewController;
import MoviesProject.mypage.MypageCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MovieinfoController implements Initializable{
	@FXML private ImageView IV_poster; // 이미지뷰_포스터
	@FXML private Label LB_moviename; // 라벨_영화명
	@FXML private Label LB_isshowing; // 라벨_상영중
	@FXML private Label LB_movierating; // 라벨_관람등급
	@FXML private Label LB_moviegenre; // 라벨_장르
	@FXML private Label LB_movieruntime; // 라벨_상영시간
	private static Label LB_email; // 라벨_이메일
	private static Parent root, preroot;
	private static Stage stage;
	private static Scene mainScene, listScene, InfoScene;
	private DataReceive dr;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 컨트롤에 내용 입력 받기
		dr = new DataReceive();
		
		String image_url = dr.getMinfo_posterUrl();
		IV_poster.setImage(new Image(image_url));
		LB_moviename.setText(dr.getMinfo_moviename());
		LB_isshowing.setText("상영중");
		LB_movierating.setText(dr.getMinfo_movierating());
		LB_moviegenre.setText(dr.getMinfo_moviegenre());
		LB_movieruntime.setText(dr.getMinfo_movieruntime()+ "분");
	}
	
	public String GetEmail() {
		return LB_email.getText().toString();
	}
	
	public void SetRoot(Parent root, Parent preroot,Stage stage, Scene mainScene, Scene listScene, Scene InfoScene, String email) {
		this.root = root;
		this.preroot = preroot;
		this.stage = stage;
		this.mainScene = mainScene;
		this.listScene = listScene;
		this.InfoScene = InfoScene;
		LB_email = (Label)root.lookup("#LB_email");
		LB_email.setText(email);
	}
	public void MoveReserve() {
		// 예매 버튼을 누르면 실행
		// 스테이지 불러오기
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../SelectPage/SelectEvent.fxml"));
		Parent root = null;
		try {root = loader.load();}
		catch (IOException e) {e.printStackTrace();}
		SelectCtrl selcon = loader.getController();
		selcon.setRoot(MovieinfoController.root, root);
		Scene selectScene = new Scene(root);
		System.out.println("ddddddd"+LB_email.getText());
		selcon.setMyinfo(LB_email.getText());
		selcon.setStage(stage, mainScene, InfoScene, selectScene);
		
		stage.setScene(selectScene);
		stage.show();
		
	}
	public void Logout() {
		// 로그아웃 버튼을 누르면 실행
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("정말 로그아웃 하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			stage.setScene(mainScene);
			stage.show();
		}
	}
	public void Mypage() {
		FXMLLoader myPage = new FXMLLoader(getClass().getResource("../mypage/mypage.fxml"));
		Parent myroot = null;
		try {myroot = myPage.load();}
		catch (IOException e) {e.printStackTrace();}
		MypageCon mpc = myPage.getController();
		mpc.setRoot(myroot, this.root);
		Scene mypageScene = new Scene(myroot);
		mpc.setStage(stage, mainScene, mypageScene, InfoScene);
		mpc.setMyinfo(LB_email.getText());
		stage.setScene(mypageScene);
		stage.show();
	}

}