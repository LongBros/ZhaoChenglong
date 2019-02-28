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

public class SpideTencent extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url="http://v.qq.com/x/list/movie";
		Document doc=Jsoup.connect(url).get();
		Elements movies=doc.getElementsByClass("list_item");
		PrintWriter out=response.getWriter();
		 
		   out.println("<head>");
		   out.println("<meta title='全部影视'></meta>");
		   out.println("<style type=\"text/css\">");
		   out.print("body{margin-left:60px;}");
		   out.println("  .whole{border:solid red;width:200px;height:300px;margin:10px;float: left;}");
		   out.println("</style>");
		   out.println("</head>");
		for(Element movie:movies){
			String sMov=movie.toString();
//			System.out.println(sMov);
			
			String href=sMov.substring(sMov.indexOf("<a href=\"")+9,sMov.indexOf("\" class=\"figure\""));
//			System.out.println(href);
			String src="http:"+sMov.substring(sMov.indexOf("r-lazyload=\"//")+12,sMov.indexOf("\" alt=\""));
//			System.out.println(src);
			String title=sMov.substring(sMov.indexOf("videos:title\">")+14,sMov.indexOf("</a></strong>"));
			System.out.println(title);
			out.write("<a href='"+href+"'> <div class='whole'>"
			  		+ "<img style='width:200px;height:270px;' src='"+src+"' title='"+src+"' alt='"+src+"'><em>"+title+"</em></div></a>");
         }
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

}
