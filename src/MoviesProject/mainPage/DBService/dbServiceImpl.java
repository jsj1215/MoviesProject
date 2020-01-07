package MoviesProject.mainPage.DBService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MoviesProject.ViewList.viewController;
import MoviesProject.mainPage.mainCtrl;
import MoviesProject.mainPage.commonService.commonService;
import MoviesProject.mainPage.commonService.commonServiceimpl;
import javafx.scene.control.TextField;


public class dbServiceImpl implements dbService {
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	//final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 

	final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
	final String INSERTIDSQL = "INSERT INTO member VALUES(?,?,?,0)"; 
	final String SELECTIDSQL = " SELECT * FROM member WHERE email = ?"; 
	final String LOGINSQL = " SELECT * FROM member WHERE email = ? AND password = ?";
	///////////////////////////////////////////////////////////////////////////////////////
	//-----------SeatReservation에서 좌석 등록된 것 있는지 확인 용!!!!!
	
	final String MYPAGE = "select m.email, s.num, s.seatnumber, s.price, t.serialnum from member m, seatreserve s, ticket t "
			+ "where t.m_email = m.email and t.s_num = s.num and m.email = ?";
	final String SEATSELECTSQL = "select m.email, s.num, s.seatnumber, s.price, t.serialnum from member m, seatreserve s, ticket t "
			+ "where t.m_email = m.email and t.s_num = s.num and t.serialnum = ?"; 
	final String PAYSELECTSQL_EMAIL = "select m.point from member m, seatreserve s, ticket t " + 
			"where t.m_email = m.email and t.s_num = s.num and m.email = ?";
	final String PAYINSERTSQL_SEAT = "insert into seatreserve values(seat_num.nextval, ?, ?)"; // 좌석, 가격 받기
	final String PAYINSERTSQL_TICKET = "insert into ticket values(tick_num.nextval, ?, seat_num.currval, ?)";
	final String PAYUPDATESQL_POINT = "update member set point = point + ? where email = ?";// 이메일, 시리얼 넘버 받기
	final String MYPAGE_REFUND = "delete from seatreserve s where exists( select * from ticket t  where t.num = s.num and t.serialnum = ? and s.seatnumber = ?)";
	final String MYPAGE_POINT = "update member set point = ? where email = ?";
	final String MYPAGE_POINTREFRESH = "select point from member where email = ?";
	///////////////////////////////////////////////////////////////////////////////////////
	//final String 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	String user,pw;
	private commonService comSer;
	private mainCtrl mc;
	
	
	static {
		try {Class.forName(DRIVER);}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public dbServiceImpl() {
		user = "movie"; pw ="1234";
	}
	
	@Override
	public boolean Insert(TextField a , TextField b ,TextField c) {
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(INSERTIDSQL);
			pstmt.setString(1,a.getText());
			pstmt.setString(2,b.getText());
			pstmt.setString(3,c.getText());
			pstmt.executeUpdate();
			conn.close(); pstmt.close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean Select(TextField a) {
		int count = 0;
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(SELECTIDSQL);
			pstmt.setString(1,a.getText());
            rs = pstmt.executeQuery();
            
            while(rs.next()){
            	   String email = rs.getString("email");
                   String name = rs.getString("name");
                   count += 1;
               System.out.println("해당 아이디 : " +name + "//" + email);
            }
            conn.close(); pstmt.close();
            if (count == 0) return true;
            else return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean Login(TextField a , TextField b) {
		comSer = new commonServiceimpl();
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(LOGINSQL);
			pstmt.setString(1,a.getText());
			pstmt.setString(2,b.getText());
            rs = pstmt.executeQuery();
            int count = 0;
            int point = 0;
            while(rs.next()){
            	   String email = rs.getString("email");
                   String name = rs.getString("name");
                   point = rs.getInt("point");
                   count = 1;
               System.out.println("정보: " + name + "//" + email + "//" + point);
            }
            if (count == 1) {comSer.SetUserinfo(a); comSer.setUserPoint(point); return true;}
            else return false;
		} catch (SQLException e) { e.printStackTrace(); return false;}
	}
	
	public HashMap MyPage(String email) {
		HashMap<String, ArrayList> MyPage = new HashMap<String, ArrayList>();
		
		ArrayList<String> Serialarr = new ArrayList<String>();
		ArrayList<String> Seatarr = new ArrayList<String>();
		ArrayList<Integer> SeatPrice = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(URL,user,pw);		// Connection 연결
			pstmt = conn.prepareStatement(MYPAGE);					// Query 문 실행 준비
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();								// query 실행
		  
			while(rs.next()){ 										//m.email, s.num, s.seatnumber, s.price, t.serialnum
			  	System.out.println(rs.getString("serialnum") +"//"+ rs.getString("email") +"//"+ rs.getString("price") +"//"+ rs.getString("seatnumber"));
			  	Serialarr.add(rs.getString("serialnum"));
			  	Seatarr.add(rs.getString("seatnumber"));
			  	SeatPrice.add(rs.getInt("price"));
		  	}
			MyPage.put("Serial", Serialarr);
			MyPage.put("Seat", Seatarr);
			MyPage.put("price", SeatPrice);
			conn.close(); pstmt.close();
		}
		catch (SQLException e) {e.printStackTrace();}
		
		return MyPage;
	}
	

	public int getRestSeat (String Serial) { 						// myPage 마이페이지
		int RestSeat = 0;
		//System.out.println("getRestSeat 실행 , " +Serial);
		try {
			conn = DriverManager.getConnection(URL,user,pw);		// Connection 연결
			pstmt = conn.prepareStatement(SEATSELECTSQL);			// Query 문 실행 준비
			pstmt.setString(1, Serial);
	        rs = pstmt.executeQuery();								// query 실행
	        
	        while(rs.next()){										// 실행 결과 조회
	        	String serialnum = rs.getString("serialnum");
	        	String seatnumber = rs.getString("seatnumber");
	        	//System.out.println("SelectCtrl의 좌석 정보 :" + serialnum + "//" + seatnumber);
	        	String [] a = seatnumber.split(",");
	        	//System.out.println(RestSeat+=a.length);
	        }
	        conn.close(); pstmt.close();
		} catch (Exception e) {
			System.out.println("에러가 나옵니다.");
			e.printStackTrace();
		}
		return RestSeat;
	}
	
	
	// 좌석 확인
	@Override
	public Map<String, List> SeatSelect(String Serial) {
		System.out.println("데이터베이스에서 조회해야할 시리얼 : "+ Serial);
		List<String> serialNumberList = new ArrayList<String>();
		List<String> seatNumberList = new ArrayList<String>();
		Map<String, List> getData = new HashMap<String, List>();
		try {
			conn = DriverManager.getConnection(URL,user,pw);		// Connection 연결
			pstmt = conn.prepareStatement(SEATSELECTSQL);			// Query 문 실행 준비
			pstmt.setString(1, Serial);
            rs = pstmt.executeQuery();								// query 실행
            
            while(rs.next()){										// 실행 결과 조회
            	String serialnumber = rs.getString("serialnum");
            	String seatnumber = rs.getString("seatnumber");
            	serialNumberList.add(serialnumber);
            	seatNumberList.add(seatnumber);
            }
            getData.put("serialNumber", serialNumberList);
        	getData.put("seatNumber", seatNumberList);
            
            if (getData.size() == 0) { System.out.println("조회된 Data 없음");}
		}
		catch (SQLException e) {e.printStackTrace();
		}
		return getData;
	}
	public boolean PaySaleEqual(String email, int selectPoint) { // 포인트 사용 체크
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(PAYSELECTSQL_EMAIL);
			pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while(rs.next()){
            	int point = rs.getInt("point");
            	if(selectPoint > point) {return false;}
            }
            pstmt.close(); conn.close();
		}
		catch (SQLException e) {e.printStackTrace();}
		return true;
	}
	public void PayInsert(String seat, int price, String email, String serial) { // 결제 후 변경
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(PAYINSERTSQL_SEAT);
			pstmt.setString(1, seat);
			pstmt.setInt(2, price);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(PAYINSERTSQL_TICKET);
            pstmt.setString(1, email);
			pstmt.setString(2, serial);
            pstmt.executeUpdate();
            pstmt.close(); conn.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	public void PayUpdate(int point, String email) {
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(PAYUPDATESQL_POINT);
			pstmt.setInt(1, point);
			pstmt.setString(2, email);
	        pstmt.executeUpdate();
	        pstmt.close(); conn.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public void MypageRefund(String Serial, String seats) {
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(MYPAGE_REFUND);
			pstmt.setString(1, Serial);
			pstmt.setString(2, seats);
	        pstmt.executeUpdate();
	        pstmt.close(); conn.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	public void pointUpdate(int point, String email) {
		try {
			conn = DriverManager.getConnection(URL,user,pw);
			pstmt = conn.prepareStatement(MYPAGE_POINT);
			pstmt.setInt(1, point);
			pstmt.setString(2, email);
	        pstmt.executeUpdate();
	        pstmt.close(); conn.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	public int PointRefresh(String email) {
        int point = 0;
        try {
        	conn = DriverManager.getConnection(URL,user,pw);
    		pstmt = conn.prepareStatement(MYPAGE_POINTREFRESH);
    		pstmt.setString(1, email);
            rs = pstmt.executeQuery();
        	while(rs.next()){point = rs.getInt("point");}
        	pstmt.close(); conn.close();
        }
        catch (SQLException e) {e.printStackTrace();}
        return point;
	}
}
