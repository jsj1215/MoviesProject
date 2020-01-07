package MoviesProject.mainPage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import MoviesProject.MovieInfo.DataReceive;
import MoviesProject.ViewList.viewController;
import MoviesProject.mainPage.DBService.dbService;
import MoviesProject.mainPage.DBService.dbServiceImpl;
import MoviesProject.mainPage.commonService.commonService;
import MoviesProject.mainPage.commonService.commonServiceimpl;
import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutLeft;

public class mainCtrl implements Initializable {
	// newUser id pw ����� fx 
	private Stage stage;
	private TextField id;
	private Parent root;
	private Scene mainScene;
	public static dbService DBcon;
	public commonService comSer;

	public void setRoot( Parent root) {
		this.root = root;
	}
	public void setStage(Stage stage , Scene mainScene) {
		this.stage = stage;
		this.mainScene = mainScene;
	}
	
	@FXML private Label newUser;
	@FXML private Pane imgPane;
	
	
	public void LoginProc() {
		System.out.println("로그인 버튼이 실행됩니다.");
		id = (TextField) root.lookup("#id");
		PasswordField  pw = (PasswordField) root.lookup("#pw");
		Parent root = null;
		boolean bool = mainCtrl.DBcon.Login(id , pw);
		if(bool) {
			System.out.println("회원 정보가 있네요 곧 로그인됩니다.");
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../ViewList/viewevent.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewController con = loader.getController();

			Scene listScene = new Scene(root);
			con.setStage(stage , mainScene, listScene);
			con.setRoot(root ,this.root );
			con.setMyinfo(id.getText());// 아이디 정보 셋팅
			pw.setText("");
			listScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto:700&display=swap");
			DataReceive dr = new DataReceive();
			dr.setListScene(listScene);
			stage.setScene(listScene);
			stage.show();
			
		
		}else {
			System.out.println("회원정보가 없습니다 로그인을 거절합니다.");
			comSer.alertMessage("아이디나 비밀번호가 잘못되었습니다.");
		}
	}
	
	public void Signin() {
		System.out.println("가입버튼을 눌렀습니다.");
		signCtrl signCon = new signCtrl();
		signCon.setRoot(root);
		Node LogPage = root.lookup("#logPage");
		Node SignPage = root.lookup("#signPage");
		SignPage.setVisible(true);
		SignPage.toFront();
		new FadeOutLeft(LogPage).play();
		new FadeInRight(SignPage).play();

		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBcon = new dbServiceImpl();
		comSer = new commonServiceimpl();
		newUser.setOnMouseEntered((e)->{
			newUser.setCursor(Cursor.HAND);
		});
		    	 
		    
		  

	}

}
