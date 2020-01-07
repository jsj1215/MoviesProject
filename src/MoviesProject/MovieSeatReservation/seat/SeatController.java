package MoviesProject.MovieSeatReservation.seat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import MoviesProject.MovieInfo.DataReceive;
import MoviesProject.Pay.PayController;
import MoviesProject.mainPage.mainCtrl;
import MoviesProject.mainPage.DBService.dbService;
import MoviesProject.mainPage.DBService.dbServiceImpl;
import MoviesProject.mypage.MypageCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SeatController implements Initializable {
	private Parent root;
	private static Stage stage;
	private Scene mainScene, prevPage, reserveScene;
	private AgeGroup ag;
	private SeatReserve reservationInfo;
	private Buttons buttons;
	private Label label, idinfo;
	private Parent preroot;
	private String id;
	private Label LB_email;
	private List<String> selectedItemList;
	private DataReceive dr;
	private static dbService DBcon;
	private static String Serial;
	
	
	@FXML private ImageView imgView;
	//수진씨
	public void setRoot(Parent root,Parent preroot ,String Serial) {
		this.root = root;
		ag = new AgeGroup(root, this);
		reservationInfo = new SeatReserve(root, this);		// 이게 지금 우리 좌석선택 페이지 띄우는거고
		buttons = new Buttons(root, ag, reservationInfo, this);
		this.preroot = preroot;
		this.stage = stage;
		System.out.println("SeatController > setRoot > Serial : "+Serial);
		this.Serial =  Serial; //이전페이지에서 넘어온 Serial(잔여좌석 파악하기 위함)
		System.out.println("Serial : " + Serial);
		
		//영화 정보(라벨)불러오기
		movieinfo();
		checkSeat();			// checkSeat 이 선택된건지 확인

		//이미지 불러오기
		dr = new DataReceive();
		dr.setSerial(Serial);
		String url = dr.getMinfo_posterUrl();
		Image img = new Image(url);
		imgView.setFitHeight(250);
		imgView.setFitWidth(200);
		imgView.setImage(img);
	}
	
	public void setStage(Stage stage , Scene mainScene , Scene prevPage, Scene reserveScene) {
		this.stage = stage;
		this.mainScene = mainScene;
		this.prevPage = prevPage;
		this.reserveScene = reserveScene;
	}
	
	public void setMyinfo(String id) {
		this.id = id;
		idinfo = (Label) root.lookup("#myinfo") ;
		idinfo.setText(id);	
	}
	public void mypage() {
		FXMLLoader myPage = new FXMLLoader(getClass().getResource("../../mypage/mypage.fxml"));
		Parent myroot = null;
		try {myroot = myPage.load();}
		catch (IOException e) {e.printStackTrace();}
		MypageCon mpc = myPage.getController();
		mpc.setRoot(myroot, this.root);
		Scene mypageScene = new Scene(myroot);
		mpc.setStage(stage, mainScene, mypageScene, reserveScene);
		mpc.setMyinfo(idinfo.getText());
		stage.setScene(mypageScene);
		stage.show();
	}
	
	public void logoutbtn() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("정말 로그아웃 하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			stage.setScene(mainScene);
			stage.show();
		}
		System.out.println(this.stage);
		System.out.println("로그아웃이 실행됩니다.");
		System.out.println(mainScene);
		
	}
	public void prevbtn() {
		stage.setScene(prevPage);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBcon = new dbServiceImpl();
		DBcon.SeatSelect(Serial); //DB의 SeatSelect와 연결

	}

	
	public void checkSeat() {
		//수진씨
		Map<String, List> seatData = mainCtrl.DBcon.SeatSelect(Serial); //DB에서 넘어온 Serial넘버
		System.out.println("가져온 Data!!!!!!! : "+seatData.size());
		List<String> serialNumberList = seatData.get("serialNumber");
		List<String> seatNumberList	= seatData.get("seatNumber");
		
		List<String> serial = new ArrayList<String>();
		List<String> seat = new ArrayList<String>();
		
		System.out.println("가져온 serialNumber : "+serialNumberList.size());
		System.out.println("가져온 seatNumber : "+seatNumberList.size());
		
		//---------------------------------------------
		// 일련번호 꺼내기!
		//---------------------------------------------
		for(int i=0; i<serialNumberList.size(); i++) {
			serial.add(serialNumberList.get(i));
		}
		
		//-----------------------------------------------------
		// 좌석번호 Data 가공하기 (현재 index 하나에 좌석번호 여러개 들어가있음)
		//-----------------------------------------------------
		for(int i=0; i<seatNumberList.size(); i++) {
			String seatNumber = seatNumberList.get(i);
			String[] seatNum = seatNumber.split(", ");
			for(int j=0; j<seatNum.length; j++) {
				String sn = seatNum[j];

				seat.add(sn);
			}
		}
		
		
		List<Button> buttonList = reservationInfo.getButtonList();	
		boolean[] flagArr = reservationInfo.getClickFlag();				
		
		for(int i=0; i<buttonList.size(); i++) {							
			Button button = buttonList.get(i);								
			String id = button.getId();										
			for(int a=0; a<seat.size(); a++) {								
				String seatNum = seat.get(a);								
				//System.out.println("비교할 버튼 ID 와 선택 좌석 정보 / id : "+id+"  seatNum : "+seatNum);
				//System.out.println("같은가? "+id.equals(seatNum));
				if(id.equals(seatNum)) {									
					//System.out.println("\nSeatController > checkSeat > 선택된 좌석이네요~~~ 좌석번호 : "+seatNum);
					button.setBackground(new Background(new BackgroundFill(Color.web("red"), new CornerRadii(5.0), Insets.EMPTY)));
					button.setDisable(true);
				}
			}
		}
		
		//------------------------------------------
		// 잔여좌석 정보 Setting
		//------------------------------------------
		int totalButtonAmount = buttonList.size();
		int selectedSeatAmount = seat.size();
		int remainSeatAmount = totalButtonAmount-selectedSeatAmount;
		MoviesProject.MovieSeatReservation.seat.Label label = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label.seatLabel("#remainSeat", Integer.toString(remainSeatAmount)+" / "+totalButtonAmount);
	
	}
	
	// -----------------------------------------------------------------------
	// 연령, 인원, 금액 관련 Event 적용
	// -----------------------------------------------------------------------
	public void runAgeGroup() {
		ag.ageGroup();
	}

	// -----------------------------------------------------------------------
	// 좌석 관련 Event 동작
	// -----------------------------------------------------------------------
	public void runReservationInfo() {
		reservationInfo.runReservationInfo();

	}
	
	// -----------------------------------------------------------------------
	// 버튼 관련 설정
	// -----------------------------------------------------------------------
	public void runButtons() {
		//buttons.reserveBtn(root);
		buttons.resetBtn(root);
	}
	
	public void reserve() {
		System.out.println("▶예매하기 버튼을 눌렀습니다.");
		if(reservationInfo.check == null) {
			buttons.alert05();
			return;
		}
		else if(reservationInfo.check.size() != reservationInfo.getPeopleCount()) {
			buttons.alert04();
			return;
		}
		
		if(reservationInfo.selectedSeatNumber == null) {
			System.out.println("[error]인원,좌석 선택 안했음!!");
			buttons.alert03();
			return; 
		}else {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Pay/paywindow.fxml"));
		Parent root = null;
		try {root = loader.load();}
		catch (IOException e) {e.printStackTrace();}
		PayController paycon = loader.getController();
		paycon.setRoot(root);
		Scene seatScene = new Scene(root);
		System.out.println(seatScene);
		paycon.setMyinfo(id);
		paycon.setStage(stage, mainScene, seatScene, reserveScene);
		
		stage.setScene(seatScene);
		stage.show();
		}
	}
	
	public void movieinfo() {
		System.out.println(selectedItemList.size());
		String placeTable = selectedItemList.get(2);
		String movieTime = selectedItemList.get(3);
		String movieDate2 = selectedItemList.get(4);
		String movieName = selectedItemList.get(5);
		
		//---------------------------------------------------------------------------
		// 상영관 가져오기
		//---------------------------------------------------------------------------
		MoviesProject.MovieSeatReservation.seat.Label label1 = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label1.seatLabel("#placeTable", placeTable.substring(0,2));
		
		//---------------------------------------------------------------------------
		// 상영 날짜 가져오기
		//---------------------------------------------------------------------------
		MoviesProject.MovieSeatReservation.seat.Label label2 = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label2.seatLabel("#movieDate", movieDate2.substring(0,2)+"년"+movieDate2.substring(2,4)+"월"+movieDate2.substring(4)+"일");	
		
		//---------------------------------------------------------------------------
		// 상영 시간 가져오기
		//---------------------------------------------------------------------------
		MoviesProject.MovieSeatReservation.seat.Label label3 = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label3.seatLabel("#movieTime", movieTime);	
		
		//---------------------------------------------------------------------------
		// 영화 이름 가져오기
		//---------------------------------------------------------------------------
		MoviesProject.MovieSeatReservation.seat.Label label4 = new MoviesProject.MovieSeatReservation.seat.Label(root);
		label3.seatLabel("#movieName", movieName);
	}

	
	//==========================================================================================================================================
	// Getter Setter
	//==========================================================================================================================================
	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getMainScene() {
		return mainScene;
	}

	public void setMainScene(Scene mainScene) {
		this.mainScene = mainScene;
	}

	public Scene getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Scene prevPage) {
		this.prevPage = prevPage;
	}

	public AgeGroup getAg() {
		return ag;
	}

	public void setAg(AgeGroup ag) {
		this.ag = ag;
	}

	public SeatReserve getReservationInfo() {
		return reservationInfo;
	}

	public void setReservationInfo(SeatReserve reservationInfo) {
		this.reservationInfo = reservationInfo;
	}

	public Buttons getButtons() {
		return buttons;
	}

	public void setButtons(Buttons buttons) {
		this.buttons = buttons;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Parent getPreroot() {
		return preroot;
	}

	public void setPreroot(Parent preroot) {
		this.preroot = preroot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Label getLB_email() {
		return LB_email;
	}

	public void setLB_email(Label lB_email) {
		LB_email = lB_email;
	}

	public List<String> getSelectedItemList() {
		return selectedItemList;
	}

	public void setSelectedItemList(List<String> selectedItemList) {
		this.selectedItemList = selectedItemList;
	}

	public ImageView getImgView() {
		return imgView;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}

}
