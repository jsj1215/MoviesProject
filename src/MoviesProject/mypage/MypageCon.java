package MoviesProject.mypage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import MoviesProject.ViewList.MovieInfo;
import MoviesProject.mainPage.DBService.dbService;
import MoviesProject.mainPage.DBService.dbServiceImpl;
import MoviesProject.mainPage.commonService.commonService;
import MoviesProject.mainPage.commonService.commonServiceimpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class MypageCon implements Initializable{
//   바꿀거임.
//	@FXML private TextArea fxTextview;
	
	MovieInfo movieinfoFromview = new MovieInfo();
	@FXML private ListView<String> fxseatlist;
	@FXML private Label idLabel;
	
	//리스트뷰에서 목록을 클릭하면 정보를 보여줄 텍스트들
	@FXML private Text fxmovieTitle;
	@FXML private Text fxRegion;
	@FXML private Text fxTheater;
	@FXML private Text fxDay;
	@FXML private Text fxStartTime;
	@FXML private Text fxSeatNum;

	@FXML private Text fxPoint;
	
	// 리스트뷰에 나오는 일련번호임.
	ObservableList<String> seatSerial = FXCollections.observableArrayList();
	// 일련번호에 맞춰서 넣어줄 것들.
	
	private int ind;
	private Parent root;
	private Parent preroot;
	private Stage stage;
	private Scene mainScene;
	private Scene mypageScene;
	private Scene listScene;
	private String id;

	/* serialArr은 DB에서 해당 이메일에 들어있는 모든 영화일련번호를 등록하는 리스트임. 
	 즉 p@p.p가 분노의질주와 변신 두개를 예약하면 두개의 일련번호가 리스트에 올라옴. 
	 나머지는 디비에서 가져온 정보를 저장 할 리스트들임.                       	 */
	private HashMap<String, ArrayList> dbserialArr =new HashMap<String, ArrayList>();
	private ArrayList<String> Serialarr = new ArrayList<String>();
	private ArrayList<String> Seatarr = new ArrayList<String>();
	private ArrayList<Integer> SeatPrice = new ArrayList<>();
	
	private int Spoint;
	private String Sserial;
	private int Stitle;
	private String Sregion;
	private String Stheater;
	private String Sday;
	private String Sstarttime;
	private String Sseatnum;
	private dbServiceImpl DBcon;
	private commonService cs;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			cs = new commonServiceimpl();
			DBcon = new dbServiceImpl();
//			DBcon.(이메일을 조건으로 셀렉해서 영화예매관련 정보를 가져오는 함수) *써야됨.*
			fxseatlist.getSelectionModel().selectedIndexProperty().addListener(
					(observable,oldValue,newValue)->{
						//System.out.println("인덱스 :" + fxseatlist.getSelectionModel().getSelectedIndex());
						try {
							ListSeatSet(fxseatlist.getSelectionModel().getSelectedItem());
							fxmovieTitle.setText(movieinfoFromview.movie.get(Stitle)); //텍스트에 영화제목이 들어옴.
							fxRegion.setText(Sregion);     //텍스트에 지역이 들어옴.  
							fxTheater.setText(Stheater);    //텍스트에 상영관이 들어옴.
							fxDay.setText(Sday);        //텍스트에 상영날짜가 들어옴.
							fxStartTime.setText(Sstarttime);  //텍스트에 상영시간이 들어옴.
							fxSeatNum.setText(Seatarr.get(fxseatlist.getSelectionModel().getSelectedIndex()));    //텍스트에 좌석이 들어옴.
							ind = fxseatlist.getSelectionModel().getSelectedIndex();
							//System.out.println(SeatPrice.get(ind));
						}  catch (ArrayIndexOutOfBoundsException e) {}

					});
			
	}

	public void setSeatList() {
		Serialarr = dbserialArr.get("Serial");
		Seatarr = dbserialArr.get("Seat");
		SeatPrice = dbserialArr.get("price");
		for (int i = 0; i < Serialarr.size(); i++) {
			// 리스트뷰에 나올 일련번호에 디비에서 가져온 일련번호들을 넣어줌.
			//System.out.println(Serialarr.size());
			//System.out.println(Serialarr.get(i));
			seatSerial.add(Serialarr.get(i));			
		}
		// 리스트뷰에 셋팅함.
		fxseatlist.setItems(seatSerial);
	}

	//                        현재 루트              리스트뷰루트	
	public void setRoot(Parent root, Parent preroot) {
		this.preroot = preroot;
		this.preroot = root;
	}

	public void setStage(Stage stage , Scene mainScene, Scene mypageScene, Scene listScene) {
		this.stage = stage;
		this.mainScene = mainScene;
		this.mypageScene = mypageScene;
		this.listScene = listScene;
	}

	// 아이디 라벨에 나옴.
	public void setMyinfo(String id) {
		this.id = id;
		idLabel.setText(id);
		dbserialArr = DBcon.MyPage(id);
		Spoint = DBcon.PointRefresh(id);
		fxPoint.setText(Spoint + "");
		setSeatList();
		
	}

	// 뒤로가기 버튼
	public void backproc() {
		stage.setScene(listScene);
		stage.show();
	}
	// 로그아웃버튼
	public void logoutproc() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("정말 로그아웃 하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			stage.setScene(mainScene);
			stage.show();
		}
	}
	
	public void ListSeatSet(String serial) {
		if(serial == null) {}
		else {
			String a = serial.substring(1,2);
			String b = serial.substring(2,4);
			String c = "20"+serial.substring(4,6)+"년 "+serial.substring(6,8)+"월 "+serial.substring(8,10)+"일";
			if (a.equals("S")) {a = "서울";}
			else if(a.equals("G")) {a = "경기";}
			else if(a.equals("I")) {a = "인천";}
			else if(a.equals("B")) {a = "부산";}
			else if(a.equals("W")) {a = "강원";}
			else if(a.equals("D")) {a = "대구";}
			else if(a.equals("C")) {a = "충청";}
			else{a = "제주";}
			
			if (a.equals("P1")) {
				b = "1관";
			}else if(a.equals("P2")) {
				b = "2관";
			}else if(a.equals("P3")) {
				b = "3관";
			}else if(a.equals("P4")) {
				b = "4관";
			}else if(a.equals("P5")) {
				b = "5관";
			}else if(a.equals("P6")) {
				b = "6관";
			}else if(a.equals("P7")) {
				b = "7관";
			}else if(a.equals("P8")) {
				b = "8관";
			}else{b = "9관";}
			this.Sserial = serial;
			this.Stitle = Integer.parseInt((serial.substring(0,1)));
			this.Sregion = a;
			this.Stheater = b;
			this.Sday = c;
			this.Sstarttime = serial.substring(10,12)+"시 " +serial.substring(12) +"분";
			//System.out.println(serial.substring(10));
		}
		
	}
	
	public void refundTicket () {
		System.out.println("환불창을 띄웁니다.");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("예매취소");
		alert.setHeaderText("정말 예매취소 하시겠습니까? ");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get()==ButtonType.OK) {
			DBcon.MypageRefund(Sserial, Seatarr.get(fxseatlist.getSelectionModel().getSelectedIndex())); //  디비에서삭제
			fxseatlist.getItems().remove(fxseatlist.getSelectionModel().getSelectedIndex()); // 리스트에서 지워짐.
			int p = SeatPrice.get(ind)/10;  // 환불 포인트 
			fxPoint.setText(((Integer.parseInt(fxPoint.getText()))-p)+"");
			DBcon.pointUpdate(Integer.parseInt(fxPoint.getText()), id);
			try {
				Serialarr.remove(fxseatlist.getSelectionModel().getSelectedIndex());
				Seatarr.remove(fxseatlist.getSelectionModel().getSelectedIndex());
				SeatPrice.remove(fxseatlist.getSelectionModel().getSelectedIndex());
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("빈칸입니다.");
			}

			
		}
		
		//System.out.println(Sserial +" -->>"+Seatarr.get(fxseatlist.getSelectionModel().getSelectedIndex()));
	}
	
}
