import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchTest {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//https://so.360kan.com/index.php?kw=%E8%BF%9C%E5%A4%A7%E5%89%8D%E7%A8%8B&from=
		String url="https://so.360kan.com/index.php?kw=下一站";
		Document doc=Jsoup.connect(url).get();
//		System.out.println(doc);
		Elements es=doc.getElementsByClass("js-longitem");
//		System.out.println(es);
	
		for(Element s:es){
//			System.out.println(s);
			System.out.println("----------------------------------------------------");
			String ss=s.toString();
			//视频详情网址
			String u=ss.substring(ss.indexOf("<a href=\"")+9,ss.indexOf("\" class=\"g-playicon") );
			//得到播放链接
			Document docu=Jsoup.connect(u).get();
			  Elements btns=docu.getElementsByClass("s-cover");//播放链接在这个id中
			  String pu=null;

			  String sb=btns.toString();
//			  System.out.println(sb);
//				  PlayerUrl获取该视频的播放链接
			  pu=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
//			  System.out.println(pu);
			
			//视频图片
			String img=ss.substring(ss.indexOf("<img src=\"")+10,ss.indexOf("\" alt=\""));
			//视频名
			String name=ss.substring(ss.indexOf("title=\"")+7,ss.indexOf("\" data-logger=\"ctype"));
//			System.out.println(name);
			String type=ss.substring(ss.indexOf("<span class=\"playtype\">")+24,ss.indexOf("]</span>"));
			System.out.println(type);
			
		}
		
	}
	

}
