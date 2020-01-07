package MoviesProject.ViewList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import MoviesProject.MovieInfo.DataReceive;
import MoviesProject.MovieInfo.TabController;
import MoviesProject.mypage.MypageCon;
import animatefx.animation.Bounce;
import animatefx.animation.BounceOut;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.Flip;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import animatefx.animation.ZoomInLeft;
import animatefx.animation.ZoomInRight;
import animatefx.animation.ZoomOutLeft;
import animatefx.animation.ZoomOutRight;
import javafx.animation.Animation;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class viewController implements Initializable{

	@FXML private ListView<String> fxListView;
	@FXML private ImageView fxImageView;
	@FXML private Label idLabel;
	ObservableList<String> movieString;
	ObservableList<String> movieURL;
	private Parent root;
	private Parent preroot;
	private String id;
	private int a = -1;
	private Stage stage;
	private Scene mainScene;
	private Scene listScene;
	private ArrayList<String> seatarr = new ArrayList<String>();
	private DataReceive dr;
	MovieInfo test = new MovieInfo();
	LVS t = new LVS();

	////////////////////////////////////////////////////////////
	//	이미지리스트에서 가져오는 번호 (0~9)사이에 담김.
	public int title(int num) {
		this.a = num;
		return a;
	}
	public void setListView() throws Exception { // 씬에 올라와 있는 리스트뷰 설정
		movieString = FXCollections.observableArrayList();
		movieURL = FXCollections.observableArrayList();
		ArrayList movies = test.movie;
		ArrayList img = test.imgmv;
		for (int i = 0; i < 10; i++) {
			movieString.add(movies.get(i).toString());
			t.ListViewSetting(movies.get(i).toString(), img.get(i).toString());
			movieURL.add(t.getImage());
		}
		fxListView.setItems(movieString);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dr = new DataReceive();
		try {
			test.sum();
			this.setListView();
			fxListView.getSelectionModel().selectedIndexProperty().addListener(
				(observable,oldValue,newValue)->{
					fxImageView.setImage(new Image(movieURL.get((int)newValue).toString()));
					title((int)newValue);
				}); // 리스트뷰와 이미지뷰 셋팅완료.	
		} catch (Exception e) {e.printStackTrace();}		
	}


	// 현재 viewevent.fxml 경로의 root를 담을 메소드
	public void setRoot(Parent root,Parent preroot) {
		this.root = root; // viewevent 루트
		this.preroot = preroot; // 혁순형님의 메인루트
		
	}
	public void setMyinfo(String id) {
		this.id = id;
		Label idinfo = (Label) root.lookup("#myinfo") ;
		idinfo.setText(id);
		
		
	}
	
	public void setStage(Stage stage , Scene mainScene, Scene listScene) {
		this.stage = stage;
		this.mainScene = mainScene;
		this.listScene = listScene;
	}
	// 현재 viewevent.fxml 경로를 보낼것. (쌉고수님한테 보낼 경로)
	public Parent getRoot() {
		return root;
	}

	// 혁순형님의 씬빌더 루트값을 내보내줌.(메인페이지로 갈 경로임)
	public Parent getpreRoot() {
		return preroot;
	}

	public void postproc(){
		try {
			String title  = test.movie.get(a).toString(); //영화 제목
			String cutimg = test.cutimg.get(a).toString(); // 스틸컷
			String story = test.story.get(a).toString().substring(4); // 줄거리
			String poster = test.imgmv.get(a).toString(); // 포스터사진
			String time = test.mtime.get(a).toString(); // 상영시간
			String genre = test.genre.get(a).toString(); // 장르
			String grade = test.grade.get(a).toString(); // 관람등급
			Parent viewEventRoot = getRoot(); // viewevent.fxml 값.
			
			// 영화 정보 불러오기
			dr.setMainStory(story);
			dr.setImageUrl(cutimg);
			dr.setMinfo_posterUrl(poster);
			dr.setMinfo_movieruntime(time);
			dr.setMinfo_movierating(grade);
			dr.setMinfo_moviename(title);
			dr.setMinfo_moviegenre(genre);
			dr.setMovieNum(a);
			
			// 스테이지 불러오기
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../MovieInfo/mainFrame.fxml"));
			try {root = loader.load();}
			catch (IOException e) {e.printStackTrace();}
			TabController tabcon = loader.getController();
			tabcon.setRoot(root, this.root );
			Scene InfoScene = new Scene(root);
			tabcon.setStage(stage, mainScene, listScene, InfoScene);
			tabcon.setMyinfo(id);// 아이디 정보 셋팅
			listScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto:700&display=swap");
			stage.setScene(InfoScene);
			stage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("선택된 포스터가 없습니다.");
			alert.setContentText("영화제목을 누르고 포스터를 눌러주세요!");
			alert.showAndWait();
		}
		
		
	}

	// 로그아웃 버튼관련
	public void logoutproc() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("정말 로그아웃 하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			// 혁순형님 씬빌더가 나오도록 하면 됨. 혁순형님의 루트값을 불러옴.
			stage.setScene(mainScene);
			stage.show();
			
		}// else는 따로 없음. 어차피 취소버튼이면 그대로 화면 유지되면 됨.
		

	}

	// 마이페이지 버튼
	public void mypage() {
		FXMLLoader myPage = new FXMLLoader(getClass().getResource("../mypage/mypage.fxml"));
		Parent myroot = null;
		try {myroot = myPage.load();}
		catch (IOException e) {e.printStackTrace();}
		MypageCon mpc = myPage.getController();
		mpc.setRoot(myroot, this.root);
		Scene mypageScene = new Scene(myroot);
		mpc.setStage(stage, mainScene, mypageScene, listScene);
		mpc.setMyinfo(id);
		stage.setScene(mypageScene);
		stage.show();
	}
	
	public void setSeat(ArrayList<String> a){
		this.seatarr = a;
	}
}
