package MoviesProject.Pay;

import java.net.URL;
import java.util.ResourceBundle;

import MoviesProject.Service.main_service;
import MoviesProject.Service.main_serviceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;

public class controller implements Initializable {
	@FXML ToggleButton cardtg;
	@FXML ToggleButton sendtg;
	@FXML ToggleButton phonetg;
	@FXML Label dcpayment;
	@FXML Label howtopayment;
	@FXML TextField usept;
	private Parent root;
	private main_service Service;
	
	public void setRoot(Parent root) {
		this.root = root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Service = (main_service) new main_serviceImpl();
		// 포인트 사용 텍스트필드에 사용할 텍스트 포맷터
		// 숫자만 입력하게 하고, 나머지 문자들은 입력처리를 안해줌.
		TextFormatter<Integer> usept_num = new TextFormatter<Integer>(change -> {
			change.setText(change.getText().replaceAll("[^0-9.,]", ""));
			return change;
		});
		usept.setTextFormatter(usept_num);
		// 텍스트 필드가 바뀔 때마다 할인금액 라벨의 값이 변경됨 (속성 감시)
		usept.textProperty().addListener(listener -> {dcpayment.setText(usept.getText() + " 원");});
	}
	public void ChoiceSeat() {
		Service.ChoiceSeat(root);
	}	
	public void FinishPay() {
		Service.FinishPay(root);
	}
	public void PayType_card() {
		sendtg.setSelected(false); phonetg.setSelected(false);
		PayTypeCheck(cardtg);
	}
	public void PayType_send() {
		cardtg.setSelected(false); phonetg.setSelected(false);
		PayTypeCheck(sendtg);
	}
	public void PayType_phone() {
		cardtg.setSelected(false); sendtg.setSelected(false);
		PayTypeCheck(phonetg);
	}
	private void PayTypeCheck(ToggleButton tb) {
		if(tb.isSelected()) {howtopayment.setText(tb.getText());}
		else{howtopayment.setText("없음");}
	}
}