package MoviesProject.MovieSeatReservation.seat;

import java.util.List;

import MoviesProject.MovieInfo.DataReceive;
import javafx.scene.Parent;

public class Label {
	Parent root;
	private List<String> selectedItemList;
	private DataReceive dr;
	public Label(Parent root) {
		this.root = root;
	}

	// -----------------------------------------------------------------------------------
	// Label Component 속성 Ssetting
	// -----------------------------------------------------------------------------------
	public void seatLabel(String label_id, String text_value) {
		dr = new DataReceive();
		javafx.scene.control.Label lb_some = (javafx.scene.control.Label) root.lookup(label_id);

		try {
			int price = Integer.parseInt(text_value);
			dr.setPrice(price);
			lb_some.setText("￦" + SeatCommons.setComma(text_value));
		} catch (NumberFormatException e) {
			lb_some.setText(text_value);
		}
	}

}
