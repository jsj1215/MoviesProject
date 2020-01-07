package MoviesProject.Pay;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import MoviesProject.MovieInfo.DataReceive;
import MoviesProject.MovieSeatReservation.seat.SeatCommons;
import MoviesProject.ViewList.viewController;
import MoviesProject.mainPage.DBService.dbServiceImpl;
import MoviesProject.mypage.MypageCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PayController implements Initializable {
	@FXML private ImageView IV_Poaster;
	@FXML private Label LB_Location;
	@FXML private Label LB_Day;
	@FXML private Label LB_Time;
	@FXML private Label LB_PeopleNSeat;
	@FXML private Label LB_PayType;
	@FXML private Label LB_PayPrice;
	@FXML private Label LB_SalePrice;
	@FXML private Label LB_FinalPayPrice;
	@FXML private AnchorPane ap1;
	@FXML private AnchorPane ap2;
	@FXML private AnchorPane ap3;
	@FXML private AnchorPane ap4;
	@FXML private AnchorPane ap5;
	@FXML private Button BTN_Card;
	@FXML private Button BTN_Send;
	@FXML private Button BTN_Phone;
	@FXML private TextField TF_UsePoint;
	private Parent root;
	private TextFormatter<Integer> TFormat1, TFormat2, TFormat3;
	private ObservableList<String> CardCompany, CardDay, Bank, Phone;
	private ComboBox<String> CB_CardCompany, CB_CardDay, CB_Bank, CB_Phone;
	private TextField TF_BankNum, TF_PhoneNum;
	private Stage stage;
	private Scene mainScene;
	private Scene seatScene;
	private Scene selectScene;
	private dbServiceImpl DBCon;
	private DataReceive dr;
	private String email;
	private Label myinfo;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBCon = new dbServiceImpl();
		dr = new DataReceive();
		TFormat1 = new TextFormatter<Integer>(change -> {
			change.setText(change.getText().replaceAll("[^0-9.,]", ""));
			return change;
		});
		TF_UsePoint.setTextFormatter(TFormat1);
		// 포스터 이미지
		IV_Poaster.setImage(new Image(dr.getMinfo_posterUrl()));
		// 위치, 날짜, 시간, 인원과 좌석을 라벨에 출력
		LB_Location.setText(dr.getLocation());
		LB_Day.setText(dr.getDay());
		LB_Time.setText(dr.getTime());
		LB_PeopleNSeat.setText(dr.getLBseat());
		// 결제 금액
		System.out.println("가격(결제): " + dr.getPrice());
		LB_PayPrice.setText(dr.getPrice()+" 원");
		// 할인 금액과 최종 금액
		LB_FinalPayPrice.setText(((dr.getPrice() - dr.getSale()) + "") + " 원");
		TF_UsePoint.textProperty().addListener(listener -> {
				LB_SalePrice.setText(TF_UsePoint.getText() + " 원");
				dr.setSale(Integer.parseInt(TF_UsePoint.getText()));
				LB_FinalPayPrice.setText(((dr.getPrice() - dr.getSale()) + "") + " 원");
			}
		);
	}
	public void Paying() {
		int curpoint = DBCon.PointRefresh(email);
		int inputpoint = Integer.parseInt(TF_UsePoint.getText());
		if(curpoint < inputpoint) {
			showAlert("포인트 입력", "가지고 있는 포인트보다 많이 입력하셨습니다", AlertType.ERROR);
			TF_UsePoint.setText("0");
		}else if(ap1.isVisible()) {
			showAlert("결제 수단 미 선택", "결제 방법을 먼저 선택해주세요.", AlertType.ERROR);
		}else if(ap2.isVisible() && (CB_CardCompany.getValue() == null || CB_CardDay.getValue() == null)) {
			showAlert("신용카드 결제 필수 사항 미 선택", "카드종류와 할부기간을 선택해주세요.", AlertType.ERROR);
		}else if(ap3.isVisible() && (CB_Bank.getValue() == null || TF_BankNum.getLength() != 14 || TF_BankNum.getText() == null)) {
			showAlert("계좌이체 결제 필수 사항 미 선택", "은행과 계좌번호(14자리)를 입력해주세요.", AlertType.ERROR);
		}else if(ap4.isVisible() && (CB_Phone.getValue() == null || TF_PhoneNum.getLength() != 11 || TF_PhoneNum.getText() == null)) {
			showAlert("휴대폰 결제 필수 사항 미 선택", "통신사와 전화번호(11자리)를 입력해주세요.", AlertType.ERROR);
		}else {
			int finalprice = (dr.getPrice() - dr.getSale());
			int point = finalprice / 10;
			DBCon.PayInsert(dr.getSeat(), finalprice, email, dr.getSerial());
			System.out.println(point + " 점 적립."); // 포인트 적립 추가
			DBCon.PayUpdate(point, email);
			if (dr.getSale() !=0) DBCon.pointUpdate(DBCon.PointRefresh(email)-dr.getSale(), email);
			showAlert("결제 완료", "결제가 완료되었습니다.", AlertType.INFORMATION);
			
			
			stage.setScene(dr.getListScene());
			stage.show();
			
		}
	}
	
	public void Logout() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("정말 로그아웃 하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			stage.setScene(mainScene);
			stage.show();
		}
	}
	
	public void MyPage() {
		FXMLLoader myPage = new FXMLLoader(getClass().getResource("../mypage/mypage.fxml"));
		Parent myroot = null;
		try {myroot = myPage.load();}
		catch (IOException e) {e.printStackTrace();}
		MypageCon mpc = myPage.getController();
		mpc.setRoot(myroot, root);
		Scene mypageScene = new Scene(myroot);
		mpc.setStage(stage, mainScene, mypageScene, selectScene);
		mpc.setMyinfo(myinfo.getText());
		stage.setScene(mypageScene);
		stage.show();
	}
	public void PrevPage() {
		stage.setScene(seatScene);
		stage.show();
	}
	
	public void setRoot(Parent root) {
		this.root = root; initControl();
	}
	public void setStage(Stage stage , Scene mainScene , Scene selectScene, Scene seatScene) {
		this.stage = stage;
		this.mainScene = mainScene;
		this.selectScene = selectScene;
		this.seatScene = seatScene;
	}
	public void setMyinfo(String email) {
		this.email = email;
		myinfo = (Label)root.lookup("#myinfo");
		myinfo.setText(email);
	}
	
	public void PayType_card() {AnchorShow(false, true, false, false, true, "신용카드");}
	public void PayType_send() {AnchorShow(false, false, true, false, true, "계좌이체");}
	public void PayType_phone() {AnchorShow(false, false, false, true, true, "휴대폰");}
	public void LBsp_Settext(String str) {LB_SalePrice.setText(str);}
	
	private void showAlert(String header, String content, AlertType e) {
		Alert al = new Alert(e);
		al.setTitle("오류");
		al.setHeaderText(header);
		al.setContentText(content);
		al.show();
	}
	private void AnchorShow(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, String str) {
		ap1.setVisible(b1); ap2.setVisible(b2); ap3.setVisible(b3);
		ap4.setVisible(b4); ap5.setVisible(b5); LB_PayType.setText(str);
	}
	private void initControl() {
		CB_CardCompany = (ComboBox<String>)root.lookup("#CB_CardCompany");
		CB_CardDay = (ComboBox<String>)root.lookup("#CB_CardDay");
		CB_Bank = (ComboBox<String>)root.lookup("#CB_Bank");
		CB_Phone = (ComboBox<String>)root.lookup("#CB_Phone");
		TF_BankNum = (TextField)root.lookup("#TF_BankNum");
		TF_PhoneNum = (TextField)root.lookup("#TF_PhoneNum");
		
		TFormat2 = new TextFormatter<Integer>(change -> { // 
			change.setText(change.getText().replaceAll("[^0-9.,]", ""));
			return change;
		});
		TF_BankNum.setTextFormatter(TFormat2);
		TF_BankNum.textProperty().addListener(lis -> {
			if(TF_BankNum.getLength() > 14) {
				TF_BankNum.setText(TF_BankNum.getText().substring(0, 14));
			}
		});
		TFormat3 = new TextFormatter<Integer>(change -> {
			change.setText(change.getText().replaceAll("[^0-9.,]", ""));
			return change;
		});
		TF_PhoneNum.textProperty().addListener(lis -> {
			if(TF_PhoneNum.getLength() > 11) {
				TF_PhoneNum.setText(TF_PhoneNum.getText().substring(0, 11));
			}
		});
		TF_PhoneNum.setTextFormatter(TFormat3);
		
		ComboBoxValues();
		ComboBoxSetting(CB_CardCompany, CardCompany);
		ComboBoxSetting(CB_CardDay, CardDay);
		ComboBoxSetting(CB_Bank, Bank);
		ComboBoxSetting(CB_Phone, Phone);		
	}
	private void ComboBoxValues() {
		CardCompany = FXCollections.observableArrayList();
		CardCompany.addAll("신한카드", "국민카드", "기업카드");
		CardDay = FXCollections.observableArrayList();
		CardDay.addAll("무이자", "1개월", "2개월", "3개월");
		Bank = FXCollections.observableArrayList();
		Bank.addAll("신한은행", "국민은행", "기업은행");
		Phone = FXCollections.observableArrayList();
		Phone.addAll("SKT", "KT", "LG", "알뜰폰");
	}
	private void ComboBoxSetting(ComboBox<String> combo, ObservableList<String> obser) {
		if(combo.getItems().isEmpty()) {combo.getItems().addAll(obser);}
	}
}