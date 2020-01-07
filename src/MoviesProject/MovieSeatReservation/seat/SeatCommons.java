package MoviesProject.MovieSeatReservation.seat;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;

public class SeatCommons {

	// --------------------------------------------------------------------
	// 가격 천단위 표시
	// --------------------------------------------------------------------
	public static String setComma(String price) {
		char[] priceCharArr = price.toCharArray();
		int priceCharArrLength = priceCharArr.length;
		List<String> newPriceArr = new ArrayList<String>();
		String newPrice = "";
		int count = 0;
		for (int i = priceCharArrLength - 1; i >= 0; i--) {
			count++;
			if (count % 4 == 0) {
				newPriceArr.add(",");
			}
			newPriceArr.add(Character.toString(priceCharArr[i]));
		}
		for (int i = newPriceArr.size() - 1; i >= 0; i--) {
			newPrice += newPriceArr.get(i);
		}
		return newPrice;
	}
}
