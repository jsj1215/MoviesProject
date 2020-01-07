package MoviesProject.mainPage.commonService;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import MoviesProject.mainPage.mainCtrl;

public class commonServiceimpl implements commonService {

	boolean state = true;
	private String ID = "";
	private static int point = 0;
	
	public boolean getST() {return this.state;}
	public String getUserinfo() {return this.ID;}
	public void SetUserinfo(TextField a) { this.ID = a.getText();}
	public void setUserPoint(int a) { this.point = a;}

	TextField  userName;
	TextField  userEmail;
	TextField  pw1 ;
	TextField  pw2;
	
	//userName userEmail pw1 pw2
	
	@Override
	public void winExit(Parent root) {
		System.out.println("창끄기를 실행합니다.");
		Stage windo=(Stage) root.getScene().getWindow();
		windo.close();

	}
	
	public void alertMessage(String type) {
		System.out.println("에러 창을 띄웁니다.");
		Alert at = new Alert(AlertType.WARNING);
		at.setContentText(type);
		at.show();
		this.state = false;
	}

	@Override
	public void alertMessage(Parent root) {
		String type;
		
		userName  = (TextField) root.lookup("#userName");
		userEmail = (TextField) root.lookup("#userEmail");
		pw1 = (TextField) root.lookup("#pw1");
		pw2 = (TextField) root.lookup("#pw2");
		if (userName.getText().equals("") ) { type = "이름이 빈칸입니다."; alertMessage(type);}
		else if (userEmail.getText().equals("")) { type = "이메일이 빈칸입니다."; alertMessage(type);}
		else if (userEmail.getText().indexOf("@")== -1 || userEmail.getText().indexOf(".") == -1) { type = "이메일 양식이 어긋납니다..."; alertMessage(type);}
		else if (pw1.getText().equals("")) { type = "비밀번호를 입력해주세요"; alertMessage(type);}
		else if (!pw1.getText().equals(pw2.getText())) { type = "비밀번호가 서로 다릅니다."; alertMessage(type);}
		else if (duplicateSign() == false) { type = "해당 이메일은 등록되어있습니다."; alertMessage(type); }
		else {
			this.state = true;
			CompleteSign();
			System.out.println("모든테스트 통과 가입이 승인됩니다.");
			Alert at = new Alert(AlertType.INFORMATION);
			at.setContentText("안녕하세요!  "+userName.getText()+"("+userEmail.getText()+")"+ "님, 환영합니다");
			at.show();
		}
	}
	
	
	
	public boolean CompleteSign() {
		boolean bool = mainCtrl.DBcon.Insert(userEmail, userName, pw1);
		if(bool) {
			System.out.println("DB입력완료,가입완료");
			return true;
		}else {
			System.out.println("잉? DB입력 안됨 오류");
			return false;
		}
	}
	
	
	public boolean duplicateSign() {
		boolean bool = mainCtrl.DBcon.Select(userEmail);
		if(bool) {
			System.out.println("중복가입이 아닙니다 통과!");
			return true;
		}else {
			System.out.println("중복가입 입니다.가입 거부!");
			return false;
		}
	}
	@Override
	public int getUserPoint() {
		return this.point;
	}
	


}
