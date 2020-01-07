package MoviesProject.SelectPage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


import MoviesProject.MovieInfo.DataReceive;
import MoviesProject.MovieInfo.MovieinfoController;
import MoviesProject.MovieSeatReservation.seat.SeatController;
import MoviesProject.mainPage.DBService.dbService;
import MoviesProject.mainPage.DBService.dbServiceImpl;
import MoviesProject.mainPage.commonService.commonService;
import MoviesProject.mainPage.commonService.commonServiceimpl;
import MoviesProject.mypage.MypageCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oracle.jdbc.driver.DBConversion;

public class SelectCtrl implements Initializable {
	private ToggleButton prevbtn = null;
	public static dbService DBcon;
	private String Serial;
	private String CalFormat;
	private Stage stage;
	private Scene mainScene, infoScene, selectScene;
	private Parent preroot;
	private static Parent root;
	private String id;
	private boolean daybool = false;
	private boolean totaltimeCheck;
	private static Label LB_email; // 라벨_이메일
	private Label idinfo;
	private Label movieName;
	DataReceive data = new DataReceive();
	ToggleGroup timeGroup = new ToggleGroup();
	commonService comSer;
	ToggleButton selectedDay;
	@FXML private ListView<String> placeTable;
	ObservableList<String> placeSet = FXCollections.observableArrayList();
	@FXML private ListView<String> movieTable;
	ObservableList<String> movieSet = FXCollections.observableArrayList();
	@FXML private ListView<String> timeTable;
	ObservableList<String> timeSet = FXCollections.observableArrayList();
	private ArrayList<Integer>RestSeat = new ArrayList<Integer>();
	@FXML private VBox daysTable;
	public void setRoot(Parent preroot, Parent root) {
		this.root = root;
		this.preroot = preroot;

//		Label idinfo = (Label) root.lookup("#myinfo") ;
//		System.out.println(id +" "+ idinfo);
//		idinfo.setText(id);

	}
	public void setStage(Stage stage , Scene mainScene , Scene infoScene, Scene selectScene) {
		
		System.out.println( "여기" +stage);
		this.stage = stage;
		this.mainScene = mainScene;
		this.infoScene = infoScene;
		this.selectScene = selectScene;
	}
	
	public void setMyinfo(String id) {
		this.id = id;
		idinfo = (Label) root.lookup("#myinfo");
		System.out.println(id +" "+ idinfo);
		idinfo.setText(id);	
		movieName= (Label) root.lookup("#movieName"); //영화이름
		
		movieName.setText(data.getMinfo_moviename());
		System.out.println("영화이름 : "+movieName);
	
	}
	public void mypage() {
		FXMLLoader myPage = new FXMLLoader(getClass().getResource("../mypage/mypage.fxml"));
		Parent myroot = null;
		try {myroot = myPage.load();}
		catch (IOException e) {e.printStackTrace();}
		MypageCon mpc = myPage.getController();
		mpc.setRoot(myroot, this.root);
		Scene mypageScene = new Scene(myroot);
		mpc.setStage(stage, mainScene, mypageScene, selectScene);
		mpc.setMyinfo(idinfo.getText());
		stage.setScene(mypageScene);
		stage.show();
	}
	public void logoutbtn() {
		System.out.println(this.stage);
		System.out.println("로그아웃이 실행됩니다.");
		System.out.println(mainScene);
		
		stage.setScene(mainScene);
		stage.show();
		
	}
	public void prevbtn() {
		stage.setScene(infoScene);
		stage.show();
		
	}
	//------------------------------------------------------------------------------ㄴ
	//좌석선택 페이지 연결
	//------------------------------------------------------------------------------
	
	public void SerialMaker(int place) { // String 줘서 int가 뭐이면 뭐실행되게 스위치
		String a; String b; String c;
		switch (place) {
		
			case 1:
				System.out.println("선택하기가 실행됩니다.");
				selectedDay =(ToggleButton) timeGroup.getSelectedToggle();
				System.out.println();
				System.out.println("상영 날짜 : "+selectedDay.getText() +"여기는 id정보  >>>>" +selectedDay.getId());
				System.out.println("상영 장소 : "+placeTable.getSelectionModel().getSelectedItem());
				System.out.println("상영 관 : "+movieTable.getSelectionModel().getSelectedItem());
				System.out.println("상영 시간 : "+timeTable.getSelectionModel().getSelectedItem());
				System.out.println("상영날짜 : "+CalFormat);
				data.setLocation(placeTable.getSelectionModel().getSelectedItem() + " " + movieTable.getSelectionModel().getSelectedItem());
				data.setTime(timeTable.getSelectionModel().getSelectedItem());
				data.setDay(CalFormat);
				//일련번호 생성--------------------------------------------------------------------------------
				//순서대로 앞에철자 땀 (틀려도 무시하셈)
				if (placeTable.getSelectionModel().getSelectedItem().equals("서울")) {
					a = "S";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("경기")) {
					a = "G";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("인천")) {
					a = "I";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("부산")) {
					a = "B";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("강원")) {
					a = "W";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("대구")) {
					a = "D";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("충청")) {
					a = "C";
				}else{ // 제주
					a = "J";
				}
				
				if ((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("1관")) {
					b = "P1";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("2관")) {
					b = "P2";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("3관")) {
					b = "P3";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("4관")) {
					b = "P4";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("5관")) {
					b = "P5";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("6관")) {
					b = "P6";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("7관")) {
					b = "P7";
				}else if((movieTable.getSelectionModel().getSelectedItem()).substring(0,2).equals("8관")) {
					b = "P8";
				}else{ // 9관
					b = "P9";
				}
				
				c = (timeTable.getSelectionModel().getSelectedItem()).replace(":", "");
				
				Serial=+data.getMovieNum()+a+b+selectedDay.getId()+c;
				System.out.println("영화번호 : "+data.getMovieNum()+"지역: "+a+"상영관 : "+b+" 예약 날짜 : "+ selectedDay.getId() +" 예약 시간 : "+c +"\n"
						+ "일련번호 :"+Serial);
				
				break;
	
			case 2:
				System.out.println("선택하기가 실행됩니다.");
				selectedDay =(ToggleButton) timeGroup.getSelectedToggle();
				System.out.println();
				System.out.println("상영 날짜 : "+selectedDay.getText() +"여기는 id정보  >>>>" +selectedDay.getId());
				System.out.println("상영 장소 : "+placeTable.getSelectionModel().getSelectedItem());
				System.out.println("상영 관 : "+movieTable.getSelectionModel().getSelectedItem());
				System.out.println("상영 시간 : "+timeTable.getSelectionModel().getSelectedItem());
				System.out.println("상영날짜 : "+CalFormat);
				//일련번호 생성--------------------------------------------------------------------------------
				//순서대로 앞에철자 땀 (틀려도 무시하셈)
				if (placeTable.getSelectionModel().getSelectedItem().equals("서울")) {
					a = "S";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("경기")) {
					a = "G";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("인천")) {
					a = "I";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("부산")) {
					a = "B";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("강원")) {
					a = "W";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("대구")) {
					a = "D";
				}else if(placeTable.getSelectionModel().getSelectedItem().equals("충청")) {
					a = "C";
				}else{ // 제주
					a = "J";
				}
				
				
				c = (timeTable.getSelectionModel().getSelectedItem()).replace(":", "");
				RestSeat.clear();
				for (int i = 1 ;i<=9; i++) { 
					Serial = data.getMovieNum()+a+"P"+i+selectedDay.getId()+c;
					//System.out.println(Serial);
					RestSeat.add(DBcon.getRestSeat(Serial));
				}
				
				break;

		}
	}
	public void Selectbtn() {
		
		try {
			SerialMaker(1);
			List<String> selectItemList = new ArrayList<String>();
			selectItemList.add(selectedDay.getText()); //0
			selectItemList.add(placeTable.getSelectionModel().getSelectedItem());//1
			selectItemList.add(movieTable.getSelectionModel().getSelectedItem());//2
			selectItemList.add(timeTable.getSelectionModel().getSelectedItem());//3
			selectItemList.add(selectedDay.getId());//4
			selectItemList.add(movieName.getText());//5(영화이름)
			
			
			//좌석선택 페이지 연결
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../MovieSeatReservation/seat/seat.fxml"));
			Parent root = null;
			try {root = loader.load();}
			catch (IOException e) {e.printStackTrace();}
			SeatController seatcont = loader.getController();
			Scene reserveScene = new Scene(root);
			seatcont.setSelectedItemList(selectItemList);
			seatcont.setRoot(root,SelectCtrl.root, Serial);
			seatcont.setMyinfo(idinfo.getText());
			seatcont.setStage(stage, mainScene, selectScene, reserveScene);
			
			stage.setScene(reserveScene);
			stage.show();
			
		} catch (Exception NullPointerException) {
			System.out.println("선택지 비어있음 오류");
			comSer.alertMessage("선택지가 비어있습니다 모두 선택해주세요.");
		}
		
		
		
		
		
	}
	
	public void placeTable() {
		placeSet.addAll("서울","경기","인천","부산","강원","대구","충청","제주");
		placeTable.setItems(placeSet);
	}
	
	public void movieTable() {
		movieSet.addAll("","","","","","","나머지 목록을 선택하신후에 ","상영관을 선택가능합니다.","저희  1조영화를 이용해주셔서","감사합니다.");
		movieTable.setItems(movieSet);
		movieTable.setDisable(true);
	}
	
	public void daysTable() {
		Calendar time = Calendar.getInstance();
		int month = time.get(Calendar.MONTH);
		for (int i=0; i<35 ; i++) {
			time.add(Calendar.DATE, i);
			//System.out.println("이번달: "+(time.get(Calendar.MONTH) + 1) +" 일:"+time.get(Calendar.DATE));
			if (month != (time.get(Calendar.MONTH) + 1)) {
				month =time.get(Calendar.MONTH) + 1;
				System.out.println("달 이름을 추가합니다.");
				Label y = new Label(""+time.get(Calendar.YEAR));
				Label t = new Label(""+month);

				y.setStyle("-fx-font-size:15.0;  -fx-text-fill: yellow; ");
				t.setStyle("-fx-font-size:20.0;  -fx-text-fill: yellow; ");
				daysTable.getChildren().addAll(y,t);
			}
			ToggleButton btn = new ToggleButton(""+time.get(Calendar.DATE));
			btn.setStyle("-fx-font-size:15.0;  -fx-text-fill: white; -fx-background-color:  #777777; -fx-padding : 2 70 2 70;");
			String yearFormat = ""+time.get(Calendar.YEAR);
			String monthFormat = (month < 10) ? "0"+month : ""+month;
			String dayFormat = (time.get(Calendar.DATE) < 10) ? "0"+time.get(Calendar.DATE) : ""+time.get(Calendar.DATE);
			//System.out.println("Set id 이름 :"+yearFormat.substring(2)+monthFormat+dayFormat);
			CalFormat = yearFormat.substring(2)+monthFormat+dayFormat;
			btn.setId(CalFormat);
			btn.setToggleGroup(timeGroup);
			daysTable.getChildren().add(btn);
			
			btn.setOnMouseClicked((e)->{
				if (prevbtn != btn) {
					if (prevbtn != null) prevbtn.setStyle("-fx-font-size:15.0;  -fx-text-fill: white;  -fx-padding : 2 70 2 70; -fx-background-color:  #777777;");
					prevbtn = btn;
				}
				if (btn.isSelected() == true) {
					
					btn.setStyle("-fx-font-size:15.0;  -fx-text-fill: yellow; -fx-background-color:  black;  -fx-padding : 2 70 2 70;");
					daybool = true;
					System.out.println("if daybool : " + daybool);
					Checker();
				}else {
					btn.setStyle("-fx-font-size:15.0;  -fx-text-fill: white;  -fx-padding : 2 70 2 70; -fx-background-color:  #777777;");
					daybool = false;
					System.out.println("else daybool : " +daybool);
					Checker();
				}
			});
			time = Calendar.getInstance();
		} //for_end
		
		
		//System.out.println(time.get(Calendar.DAY_OF_WEEK)); // ����
		//System.out.println(time.get(Calendar.MONTH) + 1); // ��
		//System.out.println(time.get(Calendar.DAY_OF_MONTH)); //��
		
		
	}
	
	public void timeTable() {
		// 이부분에서 상호씨한테 상영시간을 가져와서 시간을 계산하게 할 예정입니다.*******************************************************************
		timeSet.addAll("","","","","","","나머지 목록을 선택하신후에 ","상영시간을 확인 가능합니다.","저희  1조영화를 이용해주셔서","감사합니다.");
		timeTable.setItems(timeSet);
		timeTable.setDisable(true);
	}
	
	
	
	public void Checker() {
		
		//System.out.println("체크실행됨");
		if (placeTable.getSelectionModel().getSelectedItem() == null ||
						daybool == false) {
			System.out.println("시간창 비활성화");
			timeTable.setDisable(true);
			movieTable.setDisable(true);
		}
		else {
			totaltimeCheck = true;
			timeSet.clear();
			timeTable.getItems().clear();
			int runTime = Integer.parseInt(data.getMinfo_movieruntime()) +20;
			System.out.println("시간창 활성화 런타임 시간 : " +runTime);
			Calendar timer = Calendar.getInstance();
			timer.set(Calendar.HOUR_OF_DAY , 9);
			timer.set(Calendar.MINUTE , 30);
			timer.set(Calendar.SECOND , 0);
			
			String value = timer.get(Calendar.HOUR_OF_DAY) +":"+ timer.get(Calendar.MINUTE);
			int value2 = timer.get(Calendar.DATE);
			while(totaltimeCheck == true){
				System.out.println("시간 :" + value +"날짜 :" +value2);
				timer.add(Calendar.MINUTE, runTime);
				
				
				String hourformat = (timer.get(Calendar.HOUR_OF_DAY) < 10) ? "0"+timer.get(Calendar.HOUR_OF_DAY) : ""+timer.get(Calendar.HOUR_OF_DAY);
				String minuteFormat = (timer.get(Calendar.MINUTE) < 10) ? "0"+timer.get(Calendar.MINUTE) : ""+timer.get(Calendar.MINUTE);
				value = hourformat +":"+ minuteFormat;
				timeSet.add(value);
				if (timer.get(Calendar.DATE) != value2 && timer.get(Calendar.HOUR_OF_DAY) >= 2 ) {
					totaltimeCheck = false;
				}
			}
			timeTable.setItems(timeSet);
			timeTable.setDisable(false);
		}

	}
	public void Checker2() { // 시간까지 설정을 하면 비로소 최종적으로 디비에들어가서 남은 좌석을 걸러낸다.
		if (placeTable.getSelectionModel().getSelectedItem() != null &&
				timeTable.getSelectionModel().getSelectedItem() != null &&
				daybool == true) {
			SerialMaker(2);
			movieSet.clear();
			movieTable.getItems().clear();
			movieSet.addAll("1관 [ "+(49-RestSeat.get(0)) +" ]","2관 [ "+(49-RestSeat.get(1)) +" ]","3관 [ "+(49-RestSeat.get(2)) +" ]",
					"4관 [ "+(49-RestSeat.get(3)) +" ]","5관 [ "+(49-RestSeat.get(4)) +" ]","6관 [ "+(49-RestSeat.get(5)) +" ]",
					"7관 [ "+(49-RestSeat.get(6)) +" ]","8관 [ "+(49-RestSeat.get(7)) +" ]","9관 [ "+(49-RestSeat.get(8)) +" ]");
			movieTable.setItems(movieSet);
			movieTable.setDisable(false);

		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBcon = new dbServiceImpl();
		placeTable(); movieTable(); timeTable(); daysTable();  
		comSer = new commonServiceimpl();
		placeTable.setOnMouseClicked((e)->{
			Checker();
		});
		timeTable.setOnMouseClicked((e)->{
			Checker2();
		});
		

		
	}
	
}
