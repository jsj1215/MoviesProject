package MoviesProject.MovieSeatReservation.seat;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Buttons {
	Parent root;
	AgeGroup ag; //나이 구분하는 클래스
	SeatReserve sr; //좌석예약 클래스
	SeatController sc; //컨트롤러

	// -----------------------------------------------------------------------
	// 생성자 Overloading
	// -----------------------------------------------------------------------
	public Buttons(Parent root, AgeGroup ag, SeatReserve sr, SeatController sc) {
		this.root = root;
		this.ag = ag;
		this.sr = sr;
		this.sc = sc;

		//reserveBtn(this.root); //예매하기 버튼
		resetBtn(this.root); //초기화버튼
	}

	
	// -----------------------------------------------------------------------
	// 버튼1. 예매하기 버튼
	// -----------------------------------------------------------------------
		
//	public void reserveBtn(Parent root) {
//		Button reserveBtn = (Button) root.lookup("#reserveBtn");
//		reserveBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				
//				System.out.println("▶예매하기 버튼을 눌렀습니다.");
//				System.out.println("선택된 좌석 수 : " +sr.check.size());
//				if (sr.check.size() != ag.getSelectedItems().size()) {
//					System.out.println("selectedSeatNumber : " + sr.selectedSeatNumber.length());
//					System.out.println("SelectedItems : " + ag.getSelectedItems().size());
//					alert04();
//					sr.selectedSeatNumber.isEmpty();
//					System.out.println("selectedSeatNumber 비워졌는지 확인용 : "+ sr.selectedSeatNumber.length());
//					return;
//				}
//
//				if (sr.selectedSeatNumber == null) {
//					System.out.println("[error]인원,좌석 선택 안했음!!");
//					alert03();
//					sr.selectedSeatNumber.isEmpty();
//					System.out.println("selectedSeatNumber 비워졌는지 확인용222 : "+ sr.selectedSeatNumber.length());
//
//					return;
//				}
//			}
//		});
//	}
	
	// -------------------------------------------------------------------------------------------
	// 버튼2. 다시하기 버튼
	// -------------------------------------------------------------------------------------------
	
	public void resetBtn(Parent root) {
		Button resetBtn = (Button) root.lookup("#resetBtn");
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetFunc();
			}
		});
	}
	
		// -----------------------------------------------------------------------
		// Alert!!
		// -----------------------------------------------------------------------
		// 3. 최종 예매하기 전, 인원&좌석 선택 없으면  alert!
		public void alert03() {
			Alert alert03 = new Alert(AlertType.INFORMATION);
			alert03.setTitle("인원 / 좌석 미선택");
			alert03.setHeaderText("선택된 정보가 없음");
			alert03.setContentText("인원 및 좌석을 선택해주세요.");
			alert03.show();
		}
		// 4. 최종 예매하기 전, 인원&좌석 선택 없으면 alert!
		public void alert04() {
			Alert alert04 = new Alert(AlertType.INFORMATION);
			alert04.setTitle("인원 / 좌석 선택오류");
			alert04.setHeaderText("선택한인원과 좌석수가 맞지 않음");
			alert04.setContentText("인원 및 좌석을 다시 확인해주세요.");
			alert04.show();
		}
		
		public void alert05() {
			Alert alert04 = new Alert(AlertType.INFORMATION);
			alert04.setTitle("인원 / 좌석 선택오류");
			alert04.setHeaderText("인원과 좌석이 선택되지 않음");
			alert04.setContentText("인원 및 좌석을 다시 확인해주세요.");
			alert04.show();
		}
		
		public void resetFunc() {
			ag.ableBtn();
			System.out.println(sr.getSelectedItems());
			if(sr.getSelectedItems() == null) {
				System.out.println("[초기화] 선택한 데이터가 없습니다.");
				return;
			}
			sr.getSelectedItems().clear(); //선택된 좌석수 초기화
			sr.selectedSeatNumber.isEmpty();
			
			boolean[] flagArr = sr.getClickFlag();
			for(int i=0; i<flagArr.length; i++) {
				flagArr[i]=false;
			}
			
			Label label = new Label(root);
			label.seatLabel("#totalPrice", "-");
			label.seatLabel("#selectNum", "-");
			
			List<Button> buttonList = sr.getButtonList();
			for(int i=0; i<buttonList.size(); i++) {
				Button button = buttonList.get(i);
				button.setBackground(new Background(new BackgroundFill(Color.web("GAINSBORO"), new CornerRadii(5.0), Insets.EMPTY)));
				
			}
			ag.resetAgeGroup();
			sc.checkSeat();
			sr.runReservationInfo();
		}
}
