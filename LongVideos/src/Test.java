import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 抓取电视剧或动漫的剧集的链接
 * @author Administrator
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 String url="https://www.360kan.com/ct/O0DncMDbLYC2DD.html";//?rank=rankhot&cat=all&area=all&year=all&pageno=2
		   Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
		   Elements s=doc.getElementsByClass("s-top-list-ji");
		   String ss=s.toString();
		   System.out.println(ss);
//		   System.out.println("------------------------");
		   String se="";
		   //1.集数多有折叠，但更新完结的2.集数多有折叠，未更新完3集数少，更新完4.集数少，未更新完
		   if(ss.contains("display:none")){//集数较多，折叠的
			    se=s.toString().substring(ss.indexOf("display:none")+15,ss.indexOf("收起</a>")-63);

		   }else if(ss.contains("<i class=\"ico-yugao\">")){//有预告片的
			   
			    se=s.toString().substring(ss.indexOf("js-tab")+14,ss.indexOf("<i class=\"ico-yugao\">"));
		   }else{
			    se=s.toString().substring(ss.indexOf("js-tab")+14);

		   }
		   System.out.println(se);

		   String[] hs=se.split("href=\"");
		   String u="";
		   for(int i=1;i<hs.length;i++){
			   //<a data-num="40" data-daochu="to=qq" http://v.qq.com/x/cover/uutv6yv0c4jn95h/d002608a679.html?ptag=360kan.tv.free"> 40 <i class="ico-new"></i> </a> 
			   u=hs[i].substring(0,hs[i].indexOf("\">"));
//			   h=h.substring(0,42);
			   System.out.println(u);
			   System.out.println("-------------------------------");

		   }

	}

}
