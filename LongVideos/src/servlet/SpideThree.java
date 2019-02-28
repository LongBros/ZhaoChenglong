package servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpideThree extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type=request.getParameter("type");//视频类型
		String cate=request.getParameter("cate");//类型下的分类号
		String page=request.getParameter("pageId");
		if(type.equals("tvplay")){
			type="dianshi";
		}else if(type.equals("movie")){
			type="dianying";
		}else if(type.equals("variety")){
			type="zongyi";
		}else if(type.equals("cartoon")){
			type="dongman";
		}
		HttpSession session=request.getSession();
		session.setAttribute("type", type);
		session.setAttribute("cate", cate);
		session.setAttribute("pageId", page);
		PrintWriter out=response.getWriter();
		 
		   out.println("<head>");
		   out.println("<title>Long Bro影院欢迎你</title>");
		   out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\"> ");

		   out.println("</head>");
		//根据分类爬取分类下的类型
		   String url1="https://www.360kan.com/"+type+"/list";
			Document doc1=Jsoup.connect(url1).get();
			Elements cats=doc1.getElementsByClass("js-filter-content");
//			System.out.println(cats);//cats包含总，类型，年代，地区，明星四大分类
			Element cat=cats.get(1);//类型分类
//			System.out.println(cat);
			String scat=cat.toString();
			String[] ca=scat.split("href=\"");
			out.write("<div class=\"category\">");
			out.println("<a href='"+url1+"'>全部</a>&emsp;");
			for(int i=1;i<ca.length;i++){
//				System.out.println(ca[i]);
				//分类的链接
				String lcat=ca[i].substring(0,ca[i].indexOf("\" target"));
				//截取类型的id
	            String id=lcat.substring(lcat.indexOf("cat=")+4);
	           
	            lcat="/LongVideos/SpideThree?cate="+id+"&type="+type+"&pageId=1";
	            //分类名
				String ncat=ca[i].substring(ca[i].indexOf("\">")+2, ca[i].indexOf("</a>"));
				out.println("<a href='"+lcat+"'>"+ncat+"</a>&emsp;");
			}
			out.write("</div>");
		
		   String main="https://www.360kan.com";
	       String url="https://www.360kan.com/"+type+"/list.php?rank=rankhot&cat="+cate+"&area=all&year=all&pageno="+page;//?rank=rankhot&cat=all&area=all&year=all&pageno=2
		   Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4

		   Elements plays=doc.getElementsByClass("js-tongjic"); 
		   
		   for(Element play: plays){
				  //  Attribute

//				  String href=play.attr("href");
				  String p=play.toString();
				 
				  //写一个方法，取出href里面的内容，src里面的内容，以及span里的年份，视频名和评分，还有演员
				  String[] s=p.split(">");
				  String hr=s[0]+">";
				  String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				  
				  String sr=s[2]+">";
				  String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));

				  String year,name,score,actor;
				  if(p.contains("付费")){
//					  System.out.println("360付费影视");
					  year=s[6].substring(0,4);
//					  System.out.println("年份+year);
					  
					  name=s[11].split("<")[0];
//					  System.out.println("片名�?+name);
					  
					  score=s[13].split("<")[0];
//					  System.out.println("评分�?+score);
					  
					  actor=s[16].split("<")[0];
//					  System.out.println("主演�?+actor);
				  }else{
					  year=s[4].substring(0, 4);					  
					  name=s[9].split("<")[0];					  
					  score=s[11].split("<")[0];
					  actor=s[14].split("<")[0];
				  }
				  if(name.length()>11){
					  name=name.substring(0,11)+"...";
				  }
				  if(actor.length()>11){
					  actor=actor.substring(0, 11)+"...";
				  }
				  if(score.equals("")){
					  score="暂无";
				  }
				  if(year.length()!=4){
					  year="暂无";
				  }
				  if(actor.equals("")){
					  actor="暂无";
				  }
				  ///LongVideos/player.jsp?href=
				  //得到hre中的网页源码，以从中筛选出想要的信息
				  Document docu=Jsoup.connect(hre).get();
				  Elements btns=docu.getElementsByClass("s-cover");//播放链接在这个id中
				  String pu=null;

				  String sb=btns.toString();
//				  System.out.println(sb);
//					  PlayerUrl获取该视频的播放链接
				  pu=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
	 
//				  System.out.println(name+"\n"+pu);
				  
				  //从href网页源码中获取其他信息，如视频的详情，剧集，等等，然后传入播放界面并显示
				  //或者只将hre传入player.jsp，在其里面进行这些信息的爬取操作
				  
//				  System.out.println("--------------------");
				  out.write(" <div class='whole'><a href=\"/LongVideos/player.jsp?type="+type+"&url="+hre+"&href="+pu+"\" target='_blank'>"
				  		+ "<img src='"+src+"' title='"+name+"' alt='"+src+"'>"
				  				+ "<em>"+name+"</em><br><em>"+actor+"</em><br><em>年份:"+year+"</em><br>"
				  						+ "<em>评分:"+score+"</em></a></div>");
				  
				 
		  }
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

}
