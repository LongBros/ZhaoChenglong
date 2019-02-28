import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TestAiqiyi {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="http://list.iqiyi.com/www/1/----------------iqiyi--.html";

		Document doc=Jsoup.connect(url).get();
		Elements pics=doc.getElementsByClass("site-piclist_pic");
		for(Element pic:pics){
			String sPic=pic.toString();
			String source=sPic.substring(sPic.indexOf("href=\"")+6, sPic.indexOf("\" class"));
//			System.out.println(source);
//			System.out.println("--------------");

			if(sPic.contains("> <p class=\"video_dj ")){
				String img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("> <p class=\"video_dj ")-1);
//			    System.out.println(img);

			}
			else if(sPic.contains("> <p class=\"viedo_lt")){
				String img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("> <p class=\"viedo_lt")-1);
//				System.out.println(sPic);

//				System.out.println(img);

//> <p class="video_dj 
//> <p class="video_dj 			    
			} else{
			    String img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("<div class=\"wrapper-listTitle\">")-6);
//			    System.out.println(img);
			}
			String duration=null;
			if(sPic.contains("<p class=\"viedo_rb\"> <span class=\"icon-vInfo\">")){
				int i=sPic.indexOf("<p class=\"viedo_rb\"> <span class=\"icon-vInfo\">");
				int j= sPic.indexOf("00 </span> </p>");
//				duration=sPic.substring(sPic.indexOf("<p class=\"viedo_rb\"> <span class=\"icon-vInfo\">"),sPic.indexOf("00 </span> </p>"));
				 System.out.println(j);
//		         System.out.println("--------------");
			}else{
//				System.out.println(duration);
			}
            
			
			/*if(sPic.contains("<span class=\"icon-vInfo\">")){
				 duration=sPic.substring(sPic.indexOf("<span class=\"icon-vInfo\">")+26,sPic.indexOf("00 </span> </p>")+2);
				 System.out.println(duration);
		         System.out.println("--------------");
			}*/
		}
	}

}
