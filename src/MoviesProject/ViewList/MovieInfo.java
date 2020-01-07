package MoviesProject.ViewList;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MovieInfo {

	public String theStory;
	public String theName;
	public String time;
	public Document doc;
	public static ArrayList<String> movie = new ArrayList<String>();
	public ArrayList<String> imgmv = new ArrayList<String>();
	public ArrayList<String> cutimg = new ArrayList<String>();
	public ArrayList<String> story = new ArrayList<String>();
	public ArrayList<String> mtime = new ArrayList<String>();
	public ArrayList<String> genre = new ArrayList<String>();
	public ArrayList<String> grade = new ArrayList<String>();
	
	public void sum() throws IOException{
		for (int i = 0; i < 10; i++) {
			doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();//1
			Element movieName = doc.select(".list_ranking tbody a").get(i);//2
			String detailMovie = "https://movie.naver.com" + movieName.attr("href");//3
			theName = movieName.text();
			movie.add(theName); // 영화제목
			
			
			doc = Jsoup.connect(detailMovie).get();
			Element movieImg = doc.select("#content div.article div.mv_info_area div.poster a img").get(0);
			imgmv.add(movieImg.attr("src")); // 포스터이미지

			
			String a = detailMovie.substring( detailMovie.indexOf("code=")+5,detailMovie.length());
			String b = "https://movie.naver.com/movie/bi/mi/photoView.nhn?code="+a.toString();
			doc = Jsoup.connect(b).get();
			Element stile = doc.select(".viewer_img img").get(0);
			String stilc = stile.attr("src");//3
			cutimg.add(stilc); // 스틸컷

			doc = Jsoup.connect(detailMovie).get();
			Element time1 = doc.select("#content div.article div.mv_info_area div.mv_info dl dd:nth-child(2) p span:nth-child(3)").get(0);
			time = time1.text();
			String retime = time.replaceAll("[^0-9]", "");
			mtime.add(retime); // 상영시간 (분단위)
			
			
			Element genres = doc.select("#content div.article div.mv_info_area div.mv_info dl dd:nth-child(2) p span:nth-child(1)").get(0);
			genre.add(genres.text()); // 장르
			
			
			Element grade1 = doc.select("#content div.article div.mv_info_area div.mv_info dl dd:nth-child(8) p a").get(0);
			grade.add(grade1.text()); // 등급
			
			
			Element story1= doc.select("#content div.article div.section_group.section_group_frst div:nth-child(1) div div.story_area").get(0);
			theStory = story1.text();
			String restory = theStory.replace("제작노트 보기", "");
			story.add(restory); // 스토리
			
		}
		
	}
}
