package MoviesProject.SelectPage;

import java.util.Calendar;
import java.util.Date;

public class timetest {
public static void main(String[] args) {

	Calendar time = Calendar.getInstance();

	 

	System.out.println(time.getTime());

	System.out.println(time.get(Calendar.DAY_OF_WEEK));

	System.out.println(time.get(Calendar.MONTH) + 1);

	System.out.println(time.get(Calendar.DAY_OF_MONTH));

	System.out.println(time.get(Calendar.HOUR_OF_DAY));

	System.out.println(time.get(Calendar.MINUTE));

	System.out.println(time.get(Calendar.SECOND));

	System.out.println(time.get(Calendar.YEAR));
	
}
}
