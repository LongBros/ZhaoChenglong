package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpideAiqiYi extends HttpServlet {

	//http://pic3.qiyipic.com/image/20180217/01/7b/v_110007751_m_601_m4_180_236.jpg
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url="http://list.iqiyi.com/www/1/----------------iqiyi--.html";
		//http://list.iqiyi.com/www/1/----------------iqiyi--.html//1电影
		//http://list.iqiyi.com/www/2/----------------iqiyi--.html//2电视剧
		//http://list.iqiyi.com/www/3/----------------iqiyi--.html//3纪录片
		//http://list.iqiyi.com/www/4/----------------iqiyi--.html动漫
		//http://list.iqiyi.com/www/5/----------------iqiyi--.html
		//http://list.iqiyi.com/www/6/----------------iqiyi--.html//综艺
		//25咨询，24财经，16网络电影，8游戏，7娱乐，6综艺，5音乐，4动漫，3纪录片，2电视剧，1电影
		Document doc=Jsoup.connect(url).get();
		//需要取出来pics里面的链接和图片片长以及infos里边的
		Elements pics=doc.getElementsByClass("site-piclist_pic");
		Elements infos=doc.getElementsByClass("site-piclist_info");

		PrintWriter out=response.getWriter();
		out.println("<head>");
		out.println("<meta title='全部影视'></meta>");
		out.println("<style type=\"text/css\">");
		out.print("body{margin-left:60px;}");
		out.println("  .whole{border:solid red;width:200px;height:250px;margin:10px;float: left;}");
		out.println("</style>");
		out.println("</head>");
		for(Element pic:pics){
			String sPic=pic.toString();
			//取链接url
			String href=sPic.substring(sPic.indexOf("href=\"")+6, sPic.indexOf("\" class"));
//			System.out.println(href);
//			System.out.println("--------------");
			//取图片img
			String img;
			if(sPic.contains("> <p class=\"video_dj ")){
				img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("> <p class=\"video_dj ")-1);
			}
			else if(sPic.contains("> <p class=\"viedo_lt")){
			    img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("> <p class=\"viedo_lt")-1);

			} else{
			    img=sPic.substring(sPic.indexOf("src=\"//")+7, sPic.indexOf("<div class=\"wrapper-listTitle\">")-6);
			}
			img="http://"+img;
			System.out.println(img);
			//取片长duration
//			String duration=sPic.substring(sPic.indexOf("<span class=\"icon-vInfo\">"),sPic.indexOf("</span>"));
			out.write("<a href='"+href+"'> <div class='whole'><img style='width:200px;height:250px;' src='"+img+"' title='"+img+"'></div></a>");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
