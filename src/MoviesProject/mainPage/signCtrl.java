package MoviesProject.mainPage;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import MoviesProject.mainPage.commonService.commonService;
import MoviesProject.mainPage.commonService.commonServiceimpl;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutRight;

public class signCtrl implements Initializable {
	private static Parent root;
	private commonService comSer;
	
	public void setRoot(Parent root) {
		this.root = root;
	}
	public void signConfirm() {
		System.out.println("가입창에 확인 버튼이 실행됩니다.");
		comSer.alertMessage(root);
		if (comSer.getST() == true) {
			Node LogPage = root.lookup("#logPage");
			Node SignPage = root.lookup("#signPage");
			TextField a = (TextField) root.lookup("#userName");
			TextField b = (TextField) root.lookup("#userEmail");
			TextField c = (TextField) root.lookup("#pw1");
			TextField d = (TextField) root.lookup("#pw2");
			a.setText("");b.setText("");c.setText("");d.setText("");
			new FadeInLeft(LogPage).play();
			new FadeOutRight(SignPage).play();
		}
	}
	
	public void signCancel() {
		System.out.println("가입창에 취소 버튼이 실행됩니다.");
		Node LogPage = root.lookup("#logPage");
		Node SignPage = root.lookup("#signPage");
		new FadeInLeft(LogPage).play();
		new FadeOutRight(SignPage).play();
	}
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		comSer = new commonServiceimpl();
	}

}
