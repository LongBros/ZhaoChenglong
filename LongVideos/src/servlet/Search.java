package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.SimInfo;
/**
 * 解决搜索内容乱码办法
 * 1.查清编码
 * 2.通过调用其他
 * @author Administrator
 *
 */
public class Search extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//
		
		response.setCharacterEncoding("UTF-8");//设置浏览器响应的编码方式，即控制浏览器的编码
		response.setContentType("text/html;charset=UTF-8");//
		
		String kw=request.getParameter("v_name");
		System.out.println(kw);
		PrintWriter out=response.getWriter();
		out.println("<html><head>");
		out.println("<title>Long Bro-Video搜搜</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\"> ");

	    out.println("<style type=\"text/css\">");
		out.print("body{margin-left:60px;}");
		out.println("</style>");
		out.println("</head>");
//		String kw="意外";
		ArrayList<SimInfo> sis=searchInfo(kw,response);
		out.write("<body>");
		for(int i=0;i<sis.size();i++){
			SimInfo si=sis.get(i);
			String url=si.getUrl();
			//得到播放链接
			Document docu=Jsoup.connect(url).get();
			Elements btns=docu.getElementsByClass("s-cover");//播放链接在这个id中
			String pu=null;

			String sb=btns.toString();
//			System.out.println(sb);
//			PlayerUrl获取该视频的播放链接
			pu=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
			System.out.println(pu);
			
			String img=si.getImg();
			String name=si.getVname();
			String type=si.getType();
			System.out.println(url+"   "+name);
			String typ=null;
			if(type.equals("电视剧")){
				typ="dianshi";
			}else if(type.equals("电影")){
				typ="dianying";
			}else if(type.equals("综艺")){
				typ="zongyi";
			}else if(type.equals("动漫")){
				typ="dongman";
			}
			out.write("<a href='/LongVideos/player.jsp?type="+typ+"&url="+url+"&href="+pu+"' target='_blank'><div class='whole'>"
					+ "<img src='"+img+"' alt='"+name+"' title='"+name+"'><br><em>片名:"+name+"</em><br><br><em>类型:"+type+"</em></div></a>");

		}
		out.write("</body></html");
		
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	public static ArrayList searchInfo(String kw,HttpServletResponse response)throws IOException{
         kw=URLEncoder.encode(kw,"utf-8");//将汉字转为url编码
 		String url="https://so.360kan.com/index.php?kw="+kw;
        System.out.println(url);
		Document doc=Jsoup.connect(url).get();
//		System.out.println("doc"+doc);
		Elements es=doc.getElementsByClass("js-longitem");
//		System.out.println(es);
		ArrayList<SimInfo> sis=new ArrayList<SimInfo>();
		for(Element s:es){
//			System.out.println(s);
			System.out.println("----------------------------------------------------");
			String ss=s.toString();
			//视频详情网址
			String uu=ss.substring(ss.indexOf("<a href=\"")+9,ss.indexOf("\" class=\"g-playicon") );
			//视频图片
			String img=ss.substring(ss.indexOf("<img src=\"")+10,ss.indexOf("\" alt=\""));
			//视频名
			String name=ss.substring(ss.indexOf("title=\"")+7,ss.indexOf("\" data-logger=\"ctype"));
//			System.out.println(name);
			String type=ss.substring(ss.indexOf("<span class=\"playtype\">")+24,ss.indexOf("]</span>"));
//			System.out.println(type);
			SimInfo si=new SimInfo();
			si.setUrl(uu);
			si.setImg(img);
			si.setVname(name);
			si.setType(type);
			sis.add(si);
		}
		return sis;
	}

}
