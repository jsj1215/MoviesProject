package MoviesProject.Service;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main_serviceImpl implements main_service{
	
	@Override
	public void ChoiceSeat(Parent root) {
		Stage ChoiceSeatform = new Stage();
		
		FXMLLoader seat_loader =
				new FXMLLoader(getClass().getResource(""));
		try {	root = seat_loader.load();	}
		catch(IOException e) {	e.printStackTrace();	}
		
		ChoiceSeatform.setScene(new Scene(root));
		ChoiceSeatform.show();
		
		}
		

	
	@Override
	public void FinishPay(Parent root) {
		Stage FinishPayform = new Stage();
		
		FXMLLoader finish_loader =
				new FXMLLoader(getClass().getResource ("../FinPay/EndPay.fxml"));
		try {	root = finish_loader.load();	}
		catch(IOException e) {	e.printStackTrace();	}
		
		FinishPayform.setScene(new Scene(root));
		FinishPayform.show();
		
	}
	
	
}
