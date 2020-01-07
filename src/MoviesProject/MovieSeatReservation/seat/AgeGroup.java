package MoviesProject.MovieSeatReservation.seat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class AgeGroup {

	private Parent root;
	private SeatController controller;

	private ToggleButton[] adultButtonArr = new ToggleButton[5];
	private ToggleButton[] teenButtonArr = new ToggleButton[5];
	
	private ToggleButton adult01;
	private ToggleButton adult02;
	private ToggleButton adult03;
	private ToggleButton adult04;
	private ToggleButton adult05;
	private ToggleButton teen01;
	private ToggleButton teen02;
	private ToggleButton teen03;
	private ToggleButton teen04;
	private ToggleButton teen05;

	private Map<String, List> selectedItems = new HashMap<String, List>(); // 예매하기 동작시 필요 Data
//	List<Integer> selectedInfo;	

//	List<Integer> selectedInfoad;
//	List<Integer> selectedInfote; 

	private int adultCount;
	private int teenCount;

	// 어른&청소년 가격
	private int adultprice = 12000;
	private int teenprice = 8000;

	// 생성자
	public AgeGroup(Parent root, SeatController controller) {
		this.root = root;
		this.controller = controller;
		
		adult01 = (ToggleButton) root.lookup("#adult01");
		adult02 = (ToggleButton) root.lookup("#adult02");
		adult03 = (ToggleButton) root.lookup("#adult03");
		adult04 = (ToggleButton) root.lookup("#adult04");
		adult05 = (ToggleButton) root.lookup("#adult05");
		
		teen01 = (ToggleButton) root.lookup("#teen01");
		teen02 = (ToggleButton) root.lookup("#teen02");
		teen03 = (ToggleButton) root.lookup("#teen03");
		teen04 = (ToggleButton) root.lookup("#teen04");
		teen05 = (ToggleButton) root.lookup("#teen05");
		
		ageGroup();
	}

	// 어른&청소년 인원 수 설정
	public void ageGroup() {
		// ===================================================================================================================================
		// ■어른 인원수 선택
		// ===================================================================================================================================

		adultButtonArr[0] = adult01;
		adultButtonArr[1] = adult02;
		adultButtonArr[2] = adult03;
		adultButtonArr[3] = adult04;
		adultButtonArr[4] = adult05;

		for (int i = 0; i < adultButtonArr.length; i++) {
			ToggleButton bt = adultButtonArr[i];

			bt.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					//selectedItems.clear();
					int adultCount = Integer.parseInt(bt.getText());
					int adultPrice = adultCount * adultprice;

					List<Integer> selectedInfo = new ArrayList<Integer>();
					
					selectedInfo.add(adultCount);
					selectedInfo.add(adultPrice);

					selectedItems.put("adult", selectedInfo);
					System.out.println(
							"================================================================================");
					System.out.println("▶ ageGroup >> ageGroup >> ag 에서 선택된 성인  인원수, 가격 정보");
					System.out.println(
							"================================================================================");
					System.out.println("adult 선택 인원 : " + selectedItems.get("adult").get(0));
					System.out.println("adult 총 가격 : " + selectedItems.get("adult").get(1));

					controller.getReservationInfo().setAg(AgeGroup.this);
					
					
					// 아래에서 부터는 디버깅코드
					System.out.println("ageGroup 에서의 Instance : " + AgeGroup.this);
					System.out.println("controller 의 reservationInfo 의 agGroup Insatance : "
							+ controller.getReservationInfo().getAg());
					System.out.println();
					System.out.println("reservationInfo 의 ag 의 Map(adult) 의 인원수 : "
							+ controller.getReservationInfo().getAg().getSelectedItems().get("adult").get(0));
					System.out.println("reservationInfo 의 ag 의 Map(adult) 의 가격 : "
							+ controller.getReservationInfo().getAg().getSelectedItems().get("adult").get(1));
					System.out.println();
				};
			});
		}

		
		
		// ===================================================================================================================================
		// ■청소년 인원수 선택
		// ===================================================================================================================================
		
		teenButtonArr[0] = teen01;
		teenButtonArr[1] = teen02;
		teenButtonArr[2] = teen03;
		teenButtonArr[3] = teen04;
		teenButtonArr[4] = teen05;

		for (int i = 0; i < teenButtonArr.length; i++) {
			ToggleButton bt2 = teenButtonArr[i];

			bt2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					//selectedItems.clear();
					int teenCount = Integer.parseInt(bt2.getText());
					int teenPrice = teenCount * teenprice;

					List<Integer> selectedInfo = new ArrayList<Integer>();

					selectedInfo.add(teenCount);
					selectedInfo.add(teenPrice);
					selectedItems.put("teen", selectedInfo);
					System.out.println(
							"================================================================================");
					System.out.println("▶ ageGroup >> ageGroup >> ag 에서 선택된 청소년  인원수, 가격 정보");
					System.out.println(
							"================================================================================");
					System.out.println("teen 선택 인원 : " + selectedItems.get("teen").get(0));
					System.out.println("teen 총 가격 : " + selectedItems.get("teen").get(1));

					controller.getReservationInfo().setController(AgeGroup.this.controller);
					controller.getReservationInfo().setAg(AgeGroup.this);
					
					
					// 아래부터는 디버깅코드
					System.out.println("ageGroup 에서의 Instance : " + AgeGroup.this);
					System.out.println("controller 의 reservationInfo 의 agGroup Insatance : "
							+ controller.getReservationInfo().getAg());
					System.out.println();
					System.out.println("reservationInfo 의 ag 의 Map(teen) 의 인원수 : "
							+ controller.getReservationInfo().getAg().getSelectedItems().get("teen").get(0));
					System.out.println("reservationInfo 의 ag 의 Map(teen) 의 가격 : "
							+ controller.getReservationInfo().getAg().getSelectedItems().get("teen").get(1));
					System.out.println();
					System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+selectedInfo);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+selectedItems);
				};
			});
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// 초기화 클릭시 ageGroup button re활성화!
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void resetAgeGroup() {
		selectedItems.clear();
		System.out.println("[reset] 어른/청소년 버튼이 리셋되었습니다.");
		System.out.println(selectedItems.size());

		for (int i = 0; i < adultButtonArr.length; i++) {
			ToggleButton tbt = adultButtonArr[i];
			tbt.setSelected(false);
			//System.out.println("[reset] 어른 버튼이 다시 활성화 되었습니다.");
		}
		for (int i = 0; i < teenButtonArr.length; i++) {
			ToggleButton tbt2 = teenButtonArr[i];
			tbt2.setSelected(false);
			//System.out.println("[reset] 청소년 버튼이 다시 활성화 되었습니다.");
		}
	}
	
	//adult, teen 버튼 disable
	public void disableBtn() {
		adult01.setDisable(true);
		adult02.setDisable(true);
		adult03.setDisable(true);
		adult04.setDisable(true);
		adult05.setDisable(true);
		
		teen01.setDisable(true);
		teen02.setDisable(true);
		teen03.setDisable(true);
		teen04.setDisable(true);
		teen05.setDisable(true);
	}
	
	//adult, teen 버튼 살리기
	public void ableBtn() {
		adult01.setDisable(false);
		adult02.setDisable(false);
		adult03.setDisable(false);
		adult04.setDisable(false);
		adult05.setDisable(false);
		
		teen01.setDisable(false);
		teen02.setDisable(false);
		teen03.setDisable(false);
		teen04.setDisable(false);
		teen05.setDisable(false);
	}
	
	

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Getter & Setter
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}

	public SeatController getController() {
		return controller;
	}

	public void setController(SeatController controller) {
		this.controller = controller;
	}

	public ToggleButton[] getAdultButtonArr() {
		return adultButtonArr;
	}

	public void setAdultButtonArr(ToggleButton[] adultButtonArr) {
		this.adultButtonArr = adultButtonArr;
	}

	public ToggleButton[] getTeenButtonArr() {
		return teenButtonArr;
	}

	public void setTeenButtonArr(ToggleButton[] teenButtonArr) {
		this.teenButtonArr = teenButtonArr;
	}

	public Map<String, List> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(Map<String, List> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getTeenCount() {
		return teenCount;
	}

	public void setTeenCount(int teenCount) {
		this.teenCount = teenCount;
	}

	public int getAdultprice() {
		return adultprice;
	}

	public void setAdultprice(int adultprice) {
		this.adultprice = adultprice;
	}

	public int getTeenprice() {
		return teenprice;
	}

	public void setTeenprice(int teenprice) {
		this.teenprice = teenprice;
	}
}
