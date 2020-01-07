package MoviesProject.MovieInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TabController implements Initializable{
	private Parent root, preroot;
	private Stage stage;
	private Scene mainScene, listScene, InfoScene;
	private ComboBox<String> comb_rating;
	private Label lb_mainStory;
	private ImageView iv_image;
	private WebView wv_video;
	private DataReceive dr;
	@FXML private AnchorPane a1;
	@FXML private AnchorPane a2;
	@FXML private AnchorPane a3;
	@FXML private AnchorPane a4;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	public void setRoot(Parent root,Parent preroot) {
		this.root = root; // TabController 루트
		this.preroot = preroot; // viewevent 루트
		setting();
	}
	
	public void setStage(Stage stage , Scene mainScene, Scene listScene, Scene InfoScene) {
		this.stage = stage;
		this.mainScene = mainScene;
		this.listScene = listScene;
		this.InfoScene = InfoScene;
	}
	
	public void tabStoryShow() {tabShow(true, false, false, false);}
	public void btnImageShow() {tabShow(false, true, false, false);}
	public void btnVideoShow() {tabShow(false, false, true, false);}
	public void btnRateShow() {tabShow(false, false, false, true);}
	
	public void setMyinfo(String email) {
		new MovieinfoController().SetRoot(root, preroot, stage, mainScene, listScene, InfoScene, email);
	}
	
	private void setting() {
		dr = new DataReceive();
		
		// 평점 주기 용도로 사용하는 콤보박스
		comb_rating = (ComboBox<String>)root.lookup("#ComB_Rating");
		// 줄거리 출력 용도로 사용하는 라벨 
		lb_mainStory = (Label)root.lookup("#lB_mainStory");
		// 스틸컷 출력 용도로 사용하는 이미지뷰
		iv_image = (ImageView)root.lookup("#IV_image");
		// 동영상(유튜브나 네이버) 출력 용도로 사용하는 웹뷰
		wv_video = (WebView)root.lookup("#WV_Video");
		
		lb_mainStory.setText(dr.getMainStory());
		String url = dr.getImageUrl();
		iv_image.setImage(new Image(url));
		// 유튜브 페이지에서 영상 불러오기
		String videoURL = "";
		String moviename = dr.getMinfo_moviename();
		if(moviename.equals("유열의 음악앨범")) {videoURL = "https://www.youtube.com/watch_popup?v=6WQNr2wYDRA";}
		else if(moviename.equals("변신")) {videoURL = "https://www.youtube.com/watch_popup?v=sEnhdvmg7lU";}
		else if(moviename.equals("엑시트")) {videoURL = "https://www.youtube.com/watch_popup?v=li4jOV5j7SI";}
		else if(moviename.equals("47미터 2")) {videoURL = "https://www.youtube.com/watch_popup?v=bKld2z8MUe8";}
		else if(moviename.equals("봉오동 전투")) {videoURL = "https://www.youtube.com/watch_popup?v=G1T4fiq40js";}
		else if(moviename.equals("분노의 질주: 홉스&쇼")) {videoURL = "https://www.youtube.com/watch_popup?v=cKhdU-cPI2g";}
		else if(moviename.equals("광대들: 풍문조작단")) {videoURL = "https://www.youtube.com/watch_popup?v=7W3PzpUFpXk";}
		else if(moviename.equals("사자")) {videoURL = "https://www.youtube.com/watch_popup?v=YZSnQO1BC_o";}
		else if(moviename.equals("안나")) {videoURL = "https://www.youtube.com/watch_popup?v=4puPD1R-AFM";}
		else if(moviename.equals("타짜: 원 아이드 잭")) {videoURL = "https://www.youtube.com/watch_popup?v=YnLtR9HT52U";}
		wv_video.getEngine().load(videoURL);
		// 평점 주는 콤보박스에 내용 넣기
		if(comb_rating.getItems().isEmpty()) {
			comb_rating.getItems().addAll("10점", "9점", "8점", "7점" , "6점"
					, "5점", "4점", "3점", "2점", "1점");
		}
	}
	private void tabShow(Boolean b1, Boolean b2, Boolean b3, Boolean b4) {
		a1.setVisible(b1); a2.setVisible(b2); a3.setVisible(b3); a4.setVisible(b4);
	}
	
	public void MovieList() {
		stage.setScene(listScene);
		stage.show();
	}
}
