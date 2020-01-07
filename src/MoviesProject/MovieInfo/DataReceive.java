package MoviesProject.MovieInfo;

import javafx.scene.Scene;

public class DataReceive {
	// 데이터(혹은 DB) 주고받기용 클래스
	private static String minfo_posterUrl; // movieinfo 포스터
	private static String minfo_moviename; // movieinfo 영화명
	private static String minfo_movierating; // movieinfo 관람등급
	private static String minfo_moviegenre; // movieinfo 장르
	private static String minfo_movieruntime; // movieinfo 상영시간
	private static String mainStory; // story 줄거리
	private static String imageUrl; // image 스틸컷
	private static String videoUrl; // video 동영상
	private static int setMovieNum; // 일련번호를 위한 영화넘버
	private static String Location; // 지역 + 관
	private static String Day; // 상영 날짜
	private static String Time; // 상영 시간
	private static String People; // 인원
	private static String Seat; // 좌석
	private static int price; //가격
	private static int sale; //포인트(할인)
	private static String serial; //시리얼
	private static String seatLB; // 좌석 유저 표시용
	private static Scene mypage;
	private static Scene listScene;
	
	
	public String getMinfo_posterUrl() {return minfo_posterUrl;}
	public void setMinfo_posterUrl(String minfo_posterUrl) {
		this.minfo_posterUrl = minfo_posterUrl;
	}
	public String getMinfo_moviename() {return minfo_moviename;}
	public void setMinfo_moviename(String minfo_moviename) {
		this.minfo_moviename = minfo_moviename;
	}
	public String getMinfo_movierating() {return minfo_movierating;}
	public void setMinfo_movierating(String minfo_movierating) {
		this.minfo_movierating = minfo_movierating;
	}
	public String getMinfo_moviegenre() {return minfo_moviegenre;}
	public void setMinfo_moviegenre(String minfo_moviegenre) {
		this.minfo_moviegenre = minfo_moviegenre;
	}
	public String getMinfo_movieruntime() {return minfo_movieruntime;}
	public void setMinfo_movieruntime(String minfo_movieruntime) {
		this.minfo_movieruntime = minfo_movieruntime;
	}
	public String getMainStory() {return mainStory;}
	public void setMainStory(String mainStory) {
		this.mainStory = mainStory;
	}
	public String getImageUrl() {return imageUrl;}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {return videoUrl;}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public int getMovieNum() {return setMovieNum;}
	public void setMovieNum(int a) {
		this.setMovieNum = a;
		
	}
	public String getLocation() {return Location;}
	public void setLocation(String location) {Location = location;}
	public String getDay() {return Day;}
	public void setDay(String day) {Day = day;}
	public String getTime() {return Time;}
	public void setTime(String time) {Time = time;}
	public String getSeat() {return Seat;}
	public void setSeat(String Seat) {this.Seat = Seat;}
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	public int getSale() {return sale;}
	public void setSale(int sale) {this.sale = sale;}
	public String getSerial() {return serial;}
	public void setSerial(String serial) {this.serial = serial;}
	public String getLBseat() {return seatLB;}
	public void setLBseat(String seat) {this.seatLB = seat;}
	
	public Scene getMypage() {return mypage;}
	public void setMypage(Scene mypage) {this.mypage = mypage;}
	public Scene getListScene() {return listScene;}
	public void setListScene(Scene listScene) {this.listScene = listScene;}
}
