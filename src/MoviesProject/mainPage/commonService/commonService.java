package MoviesProject.mainPage.commonService;

import javafx.scene.Parent;
import javafx.scene.control.TextField;

public interface commonService {
	public String getUserinfo() ;
	public void SetUserinfo(TextField a) ;
	public void setUserPoint(int a);
	public boolean getST();
	public void winExit(Parent root);
	public void alertMessage(Parent root);
	public void alertMessage(String err);
	public int getUserPoint();
}
