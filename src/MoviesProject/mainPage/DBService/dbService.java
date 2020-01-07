package MoviesProject.mainPage.DBService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TextField;

public interface dbService {
	public boolean Insert(TextField a , TextField b ,TextField c); // ����
	public boolean Select(TextField a); // �α����Ҷ�
	public boolean Login(TextField a , TextField b);
	public int getRestSeat (String a);
	
	public Map<String, List> SeatSelect(String a); //이미 예약되어있는 좌석 여부 확인
	public HashMap MyPage(String email);
	public void MypageRefund(String Serial, String seats);
}
