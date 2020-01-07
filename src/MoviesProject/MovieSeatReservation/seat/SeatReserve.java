package MoviesProject.MovieSeatReservation.seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MoviesProject.MovieInfo.DataReceive;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SeatReserve {
	private Parent root;
	private SeatController controller;

	private AgeGroup ag;
	private int peopleCount = 0;
	private Label label = new Label();

	private Map<String, List> selectedItems;
	private List<Integer> selectAdultInfo;
	private List<Integer> selectTeenInfo;
	private List<Button> buttonList = new ArrayList<Button>();
	private boolean[] clickFlag;
	public ArrayList<Boolean> check; // 선택된 좌석 확인용
	String selectedSeatNumber;
	String realSeatNumber;
	Button bt;
	private DataReceive dr;
	// 좌석 일련번호 정보
	private String[] alphabet = { "a", "b", "c", "d", "e", "f", "g" };// A열 ~ G열
	private int number = 7; // 1번 ~ 7번
	int totalPrice;

	// -----------------------------------------------------------------------
	// 생성자 Overloading
	// -----------------------------------------------------------------------
	public SeatReserve(Parent root, SeatController controller) {
		this.root = root;
		this.controller = controller;
		dr = new DataReceive();

		for (int a = 0; a < alphabet.length; a++) {
			for (int i = 1; i <= number; i++) {
				bt = (Button) root.lookup("#" + alphabet[a] + "0" + i);
				bt.setBackground(
						new Background(new BackgroundFill(Color.web("GAINSBORO"), new CornerRadii(5.0), Insets.EMPTY)));
				buttonList.add(bt);
			}
		}
		clickFlag = new boolean[buttonList.size()];
		for (int i = 0; i < clickFlag.length; i++) {
			clickFlag[i] = false;
		}

		runReservationInfo(); // 버튼 클릭시 이벤트와 관련된 메소드
	}

	// -----------------------------------------------------------------------
	// 해당 상영관 전체 좌석 Button들 에 Event 를 설정해줌
	// -----------------------------------------------------------------------

	public void runReservationInfo() {
		// -------------------------------------------------
		// 각 버튼에 Event 부여하기 + 선택인원 초과하여 좌석 선택시 alert!
		// -------------------------------------------------
		for (int i = 0; i < buttonList.size(); i++) {
			Button button = buttonList.get(i);
			int flagCount = i;

			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("[Event]좌석 버튼이 눌렸습니다.");
					System.out.println("[Event]여기는 본 Event 메소드입니다.");
					boolean initialDataSettingFlag = initialize();

					if (!initialDataSettingFlag) {
						System.out.println("어른어른어른어른어른어른어른어른 : " + selectAdultInfo);
						System.out.println("청소년청소년청소년청소년청소년 : "+ selectTeenInfo);
						return;
					}

					int count = flagCount;
					boolean clicked = clickFlag[count];

					System.out.println("해당 버튼의 Click Flag : " + clicked);
					if (!clicked) {
						button.setBackground(new Background(
								new BackgroundFill(Color.web("dodgerblue"), new CornerRadii(5.0), Insets.EMPTY)));
						clickFlag[count] = true;
						//System.out.println("해당 버튼은 선택됬으므로 ClickFlag true 로 변경 : " + clickFlag[count]);
						System.out.println("눌린 버튼 : " + button.getId());
						selectNum();
						int trueCount = 0;
						for (int a = 0; a < clickFlag.length; a++) {
							if (clickFlag[a]) {
								trueCount++;
							}
							ag.disableBtn();
						}
						System.out.println("선택할 수 있는 좌석수 : " + peopleCount);

						if (trueCount > peopleCount) {
							System.out.println("trueCount : "+trueCount);
							System.out.println("peopleCount : "+peopleCount);
							alert02();
							button.setBackground(new Background(
									new BackgroundFill(Color.web("GAINSBORO"), new CornerRadii(5.0), Insets.EMPTY)));
							clickFlag[flagCount] = false;
							selectNum();

						}

					} else {
						System.out.println("버튼 선택 취소");
						button.setBackground(new Background(
								new BackgroundFill(Color.web("GAINSBORO"), new CornerRadii(5.0), Insets.EMPTY)));
						clickFlag[flagCount] = false;
						selectNum();
					}
					check = new ArrayList<Boolean>();
					for (int i = 0; i < getClickFlag().length; i++) {
						if (clickFlag[i] == true) {
							check.add(clickFlag[i]);
							System.out.println("클릭된 좌석 수 :" + check.size());
						}
					}

				}
			});
		}
	}

	// --------------------------------------------------------------------------------------------------
	// 좌석 버튼 클릭시 선택 좌석 정보 Label 에 출력
	public void selectNum() {
		selectedSeatNumber = "";
		realSeatNumber = "";
		int count = 0;
		int dotcount = 0;
		for (int i = 0; i < clickFlag.length; i++) {
			boolean flag = clickFlag[i];
			if (flag) {
				Button button = buttonList.get(i);
				System.out.println("button : " + button.getId());
				if (count < 1) {
					selectedSeatNumber += button.getId();
					realSeatNumber += button.getId();
				} else if (dotcount == 4) {
					selectedSeatNumber += ", \n" + button.getId();
					realSeatNumber += ", " + button.getId();
					dotcount++;
				} else {
					selectedSeatNumber += ", " + button.getId();
					realSeatNumber += ", " + button.getId();
					dotcount++;
				}
				count++;
			}
		}
		MoviesProject.MovieSeatReservation.seat.Label label01 = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label01.seatLabel("#selectNum", selectedSeatNumber);
		dr.setSeat(realSeatNumber);
		dr.setLBseat(selectedSeatNumber);
		System.out.println(selectedSeatNumber);
	}
	// --------------------------------------------------------------------------------------------------
	//버튼 눌릴 시 배경 데이터 초기화
	//fx의 Controller에서 연결된 메소드는 최초로 동작 한 번만 수행.
	//그 이후에는 이벤트 안에서 컨트롤해줘야 함.
	public boolean initialize() {
		System.out.println("여기는 initialize 입니다.");
		boolean flag = false;
		this.ag = controller.getAg(); // 인원관련 Button Click 으로 Setting 된 ag 를 Controller 에서 가져옴
		selectedItems = ag.getSelectedItems();
		

		if (selectedItems.size() == 0) {
			alert01(); // 인원 선택 없이 좌석 선택하려고 하면 alert!
			return flag;
		}

		selectAdultInfo = selectedItems.get("adult");
		selectTeenInfo = selectedItems.get("teen");
		System.out.println("어른어른어른어른어른어른어른어른 : " + selectAdultInfo);
		System.out.println("청소년청소년청소년청소년청소년 : "+ selectTeenInfo);  //????????????여기가 왜 널로 뜸

		totalPrice = 0;
		

		// 여기서부터 하세요 안되겟어요.... 위에서 버튼으로 해서 계속 해볼껀데 일단 여기서 그만하고 넘길게요... 아래코드처럼 바꿧는데도
		// 안되네요...
//		if(selectTeenInfo != null && selectAdultInfo != null){	
//			System.out.println(">>> 둘 다 선택되었을 때 입니다.");
//			System.out.println(">>> 둘다 선택되었을 때 어른 수 : "+selectAdultInfo.get(0));
//			System.out.println(">>> 둘다 선택되었을 때 청소년 수 : "+selectTeenInfo.get(0));
//			System.out.println(" ");
//			peopleCount = selectAdultInfo.get(0) + selectTeenInfo.get(0);		
//			totalPrice = selectAdultInfo.get(1) + selectTeenInfo.get(1);		
//			System.out.println("adult : "+ag.getSelectedItems().get("adult").get(0)+" / "+ag.getSelectedItems().get("adult").get(1));
//			System.out.println("teen : "+ag.getSelectedItems().get("teen").get(0)+" / "+ag.getSelectedItems().get("teen").get(1));
//			System.out.println(">>> 둘 다 선택되었을 때 총 인원수 : " + peopleCount);
//		}
//		if(selectAdultInfo == null ) {
//			System.out.println("청소년만 선택했을 때 입니다.");
//			peopleCount = selectTeenInfo.get(0);		
//			totalPrice = selectTeenInfo.get(1);			
//			System.out.println("teen : "+ag.getSelectedItems().get("teen").get(0)+" / "+ag.getSelectedItems().get("teen").get(1));
//		}
//		if(selectTeenInfo == null ){
//			System.out.println("어른만 선택했을 때 입니다.");
//			peopleCount = selectAdultInfo.get(0);		
//			totalPrice = selectAdultInfo.get(1);		
//			System.out.println("adult : "+ag.getSelectedItems().get("adult").get(0)+" / "+ag.getSelectedItems().get("adult").get(1));
//		}	

		// 어른만 선택되었는지 or 청소년만 선택되었는지 or 둘다 선택되었는지 확인!
		if(selectAdultInfo == null) { // 수진씨 이부분 수정해야됨
			// 이렇게 만들면 만약 틴을 먼저 선택하면 아무런 반응도 안일어남...왜나면 만약 어덜트인포가 널이면을 먼저실행하는거라서 두번제 그렇지 않고
			// 만약(else if)은 실행이 안됨
			// 즉 틴을 먼저선택하게 되면 무엇을선택하던 값이 안들어감 어덜트는 어떠한경우에서라도 첫빠다이니깐 실행이됨
			peopleCount = selectTeenInfo.get(0);
			totalPrice = selectTeenInfo.get(1);
			System.out.println("teen : " + ag.getSelectedItems().get("teen").get(0) + " / "
					+ ag.getSelectedItems().get("teen").get(1));
		} 
		
		if(selectTeenInfo == null) {
			peopleCount = selectAdultInfo.get(0);
			totalPrice = selectAdultInfo.get(1);
			System.out.println("adult : " + ag.getSelectedItems().get("adult").get(0) + " / "
					+ ag.getSelectedItems().get("adult").get(1));
		}
		
		if(selectAdultInfo != null && selectTeenInfo !=null){
			peopleCount = selectAdultInfo.get(0) + selectTeenInfo.get(0);
			totalPrice = selectAdultInfo.get(1) + selectTeenInfo.get(1);
			System.out.println("adult : " + ag.getSelectedItems().get("adult").get(0) + " / "
					+ ag.getSelectedItems().get("adult").get(1));
			System.out.println("teen : " + ag.getSelectedItems().get("teen").get(0) + " / "
					+ ag.getSelectedItems().get("teen").get(1));
		}

		// -----------------------------------------------------------------
		// 총 금액 표시(라벨)
		// -----------------------------------------------------------------
		MoviesProject.MovieSeatReservation.seat.Label label = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label.seatLabel("#totalPrice", Integer.toString(totalPrice));
		
		
		flag = true;
		return flag;
	}

	// -----------------------------------------------------------------------
	// Alert!!
	// -----------------------------------------------------------------------
	// 1. 인원 선택 없이 좌석 선택하려고 하면 alert!
	public void alert01() {
		Alert alert01 = new Alert(AlertType.INFORMATION);
		alert01.setTitle("인원 미선택");
		alert01.setHeaderText("선택된 인원 없음");
		alert01.setContentText("인원을 선택해주세요.");
		alert01.show();
	}

	// 2.선택한 인원수보다 초과로 좌석을 선택했을 때
	public void alert02() {
		Alert alert02 = new Alert(AlertType.INFORMATION);
		alert02.setTitle("선택 인원 초과");
		alert02.setHeaderText("좌석 선택 에러");
		alert02.setContentText("선택하신 인원을 초과했습니다.");
		alert02.show();
	}
	// -----------------------------------------------------------------------

	// ==========================================================================================================================================
	// Getter Setter
	// ==========================================================================================================================================
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

	public AgeGroup getAg() {
		return ag;
	}

	public void setAg(AgeGroup ag) {
		this.ag = ag;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Map<String, List> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(Map<String, List> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public List<Integer> getSelectAdultInfo() {
		return selectAdultInfo;
	}

	public void setSelectAdultInfo(List<Integer> selectAdultInfo) {
		this.selectAdultInfo = selectAdultInfo;
	}

	public List<Integer> getSelectTeenInfo() {
		return selectTeenInfo;
	}

	public void setSelectTeenInfo(List<Integer> selectTeenInfo) {
		this.selectTeenInfo = selectTeenInfo;
	}

	public List<Button> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
	}

	public boolean[] getClickFlag() {
		return clickFlag;
	}

	public void setClickFlag(boolean[] clickFlag) {
		this.clickFlag = clickFlag;
	}

	public String[] getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String[] alphabet) {
		this.alphabet = alphabet;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
