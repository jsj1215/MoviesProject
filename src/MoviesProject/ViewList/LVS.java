package MoviesProject.ViewList;

import javafx.beans.property.SimpleStringProperty;

public class LVS {

	private SimpleStringProperty movielist;
	private SimpleStringProperty imagelist;
	
	public void ListViewSetting(String moviename, String image) {
		this.movielist = new SimpleStringProperty(moviename);
		this.imagelist = new SimpleStringProperty(image);
	}
	
	public void setMovielist(String movie) {
		this.movielist.set(movie);
	}
	public String getMovielist() {
		return movielist.get();
	}
	
	public String getImage() {
		return imagelist.get();
	}
	public void setImage(String image) {
		this.imagelist.set(image);
	}
}
