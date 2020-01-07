package MoviesProject.ViewList;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class 시간초계산 extends Thread {

	private  int i =0;
	@Override
	public void run() {
		while(true)
		{
			try {
				System.out.println((++i)+"초");
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

		
	public static void main(String[] args) throws IOException {
		
		시간초계산 x = new 시간초계산();
		x.start();
		
		x.shortsnet();
		
		x.stop();
		System.out.println("끝");
		
	
		
		
		
	}
	
	void longSent() throws IOException {
		Document doc;
		
		doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();
		Element movieName = doc.select(".list_ranking tbody a").get(0);
		String detailMovie = "https://movie.naver.com" + movieName.attr("href");
		
		
		doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();
		Element movieName1 = doc.select(".list_ranking tbody a").get(0);
		String detailMovie1 = "https://movie.naver.com" + movieName1.attr("href");
		doc = Jsoup.connect(detailMovie1).get();
		Element movieImg = doc.select("#content div.article div.mv_info_area div.poster a img").get(0);
		
		
		doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();
		Element movieName11 = doc.select(".list_ranking tbody a").get(0);
		String detailMovie11 = "https://movie.naver.com" + movieName11.attr("href");
		doc = Jsoup.connect(detailMovie11).get();
		Element movieImg1 = doc.select("#content div.article div.mv_info_area div.poster a img").get(0);
		
		doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();
		Element movieName111 = doc.select(".list_ranking tbody a").get(0);
		String detailMovie111 = "https://movie.naver.com" + movieName111.attr("href");
		doc = Jsoup.connect(detailMovie).get();
		Element movieImg111 = doc.select("#content div.article div.section_group.section_group_frst div:nth-child(1) div div.story_area").get(0);
		
		System.out.println("여기");
	}
	
	
	void shortsnet() throws IOException {
		Document doc;
		
		doc = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%ED%98%84%EC%9E%AC%EC%83%81%EC%98%81%EC%98%81%ED%99%94").get();
		Element movieName = doc.select("#main_pack div.movie_run.section ul").get(1);
		String A = movieName.text();
		movieName = doc.select("#main_pack div.movie_run.section ul").get(2);
		A+= movieName.text();
		
		

		System.out.println(A );

	}
	
	
	
}
