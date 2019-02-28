import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TestTencent {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="http://v.qq.com/x/list/movie";
		Document doc=Jsoup.connect(url).get();
		Elements movies=doc.getElementsByClass("list_item");
		for(Element movie:movies){
			String sMov=movie.toString();
//			System.out.println(sMov);
			
			String href=sMov.substring(sMov.indexOf("<a href=\"")+9,sMov.indexOf("\" class=\"figure\""));
//			System.out.println(href);
			String src="http:"+sMov.substring(sMov.indexOf("r-lazyload=\"//")+12,sMov.indexOf("\" alt=\""));
//			System.out.println(src);
			String title=sMov.substring(sMov.indexOf("videos:title\">")+14,sMov.indexOf("</a></strong>"));
//			System.out.println(title);
			//评分整数位
			String score=sMov.substring(sMov.indexOf("<em class=\"score_l\">")+20,sMov.indexOf("</em>"));
			System.out.println(score);
			String b="";
			for(int i=0;i<751;i++){
				b=b+" ";
			}
//			System.out.println("1"+b+"1");

			//评分小数位
//			String sorce2=sMov.substring(sMov.indexOf("<em class=\"score_s\">")+20,sMov.indexOf("</em>"+b+"</div>"));
//			System.out.println("1"+b+"1");

//			String viewNum=sMov.substring(sMov.indexOf("<span class=\"num\">")+18,sMov.indexOf("</span>     </div> </li>"));
//			System.out.println(viewNum);
			System.out.println("-----------------");

		}
//		System.out.print(movies.size());
	}

}
