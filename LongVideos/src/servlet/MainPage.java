package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Movie;
import bean.Category;
import bean.VideoObj;

public class MainPage extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String type=request.getParameter("type");//分类
		String cate=request.getParameter("cate");//类型
		String page=request.getParameter("page");
		int pageI=1;
		String cateI="";
		
		if(("dianshi").equals(type)){
			type="dianshi";
		}else if(("dianying").equals(type)){
			type="dianying";
		}else if(("zongyi").equals(type)){
			type="zongyi";
		}else if(("dongman").equals(type)){
			type="dongman";
		}else if(("all").equals(type)){
			type="all";
		}else{
			type="dianshi";
		}
		
		if("other".equals(cate)){
			cateI="other";
		}else if("all".equals(cate)){
			cateI="all";
		}
		else{
			//类型
			ArrayList<String> arr1=new ArrayList<String>();
			for(int i=100;i<135;i++){
				String t=i+"";
				arr1.add(t);
			}
			//实现直接进入为全部，点击页类型进入，为类型的
			for(int i=0;i<arr1.size();i++){
				if((arr1.get(i)).equals(cate)){
					cateI=arr1.get(i);
					break;//
				}
				if(i==(arr1.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
					cateI="all";
				}
			}		
		}
		
		
		//将所有的页码添加至数组链表
		ArrayList<String> arr=new ArrayList<String>();
		for(int i=1;i<24;i++){
			String t=i+"";
			arr.add(t);
		}
		//实现直接进入为第一页，点击页码进入，为页码的
		for(int i=0;i<arr.size();i++){
			if((arr.get(i)).equals(page)){//如果有参数，且与数组中参数向同，则将数组中该元素定位页码，转为整型
				pageI=Integer.parseInt(arr.get(i));
				break;//
			}
			if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
				pageI=1;
			}
		}
//		System.out.println(type+cateI+pageI);
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Long Bro影院欢迎你</TITLE>");
	    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/mainpage.css\"> ");
	    
//	    out.println("<link rel=\"stylesheet\" href=\"https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css\">");
//	    out.println("<script src=\"https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js\"></script>");
//	    out.println("<script src=\"https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js\"></script>");
//	    out.println("<script src=\"https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js\"></script>");
//	    
	    out.println("</HEAD>");
		out.println("  <BODY>");
		out.println("<div class='bar'>");
		out.println("<form action=\"/LongVideos/Search\" method=\"post\" target=\"_blank\"><ul>");
		if(type.equals("all")){
			out.println("<li><current><a href='/MainPage?type=all&cate=all&page=1'>首页排行榜</a></current></li>");
			out.println("<li><a href='/MainPage?type=dianshi&cate=all&page=1'>电视剧</li></a>");
			out.println("<li><a href='/MainPage?type=dianying&cate=all&page=1'>电影</li></a>");
			out.println("<li><a href='/MainPage?type=zongyi&cate=all&page=1'>综艺</li></a>");
			out.println("<li><a href='/MainPage?type=dongman&cate=all&page=1'>动漫</li></a>");
		}else if(type.equals("dianshi")){
			out.println("<li><a href='/MainPage?type=all&cate=all&page=1'>首页排行榜</a></li>");
			out.println("<li><current><a href='/MainPage?type=dianshi&cate=all&page=1'>电视剧</current></a></li>");
			out.println("<li><a href='/MainPage?type=dianying&cate=all&page=1'>电影</li></a>");
			out.println("<li><a href='/MainPage?type=zongyi&cate=all&page=1'>综艺</li></a>");
			out.println("<li><a href='/MainPage?type=dongman&cate=all&page=1'>动漫</li></a>");
		} else if(type.equals("dianying")){
			out.println("<li><a href='/MainPage?type=all&cate=all&page=1'>首页排行榜</a></li>");
			out.println("<li><a href='/MainPage?type=dianshi&cate=all&page=1'>电视剧</a></li>");
			out.println("<li><current><a href='/MainPage?type=dianying&cate=all&page=1'>电影</a></current></li>");
			out.println("<li><a href='/MainPage?type=zongyi&cate=all&page=1'>综艺</li></a>");
			out.println("<li><a href='/MainPage?type=dongman&cate=all&page=1'>动漫</li></a>");
		}else if(type.equals("zongyi")){
			out.println("<li><a href='/MainPage?type=all&cate=all&page=1'>首页排行榜</a></li>");
			out.println("<li><a href='/MainPage?type=dianshi&cate=all&page=1'>电视剧</a></li>");
			out.println("<li><a href='/MainPage?type=dianying&cate=all&page=1'>电影</a></li>");
			out.println("<li><current><a href='/MainPage?type=zongyi&cate=all&page=1'>综艺</a></current></li>");
			out.println("<li><a href='/MainPage?type=dongman&cate=all&page=1'>动漫</li></a>");
		}else if(type.equals("dongman")){
			out.println("<li><a href='/MainPage?type=all&cate=all&page=1'>首页排行榜</a></li>");
			out.println("<li><a href='/MainPage?type=dianshi&cate=all&page=1'>电视剧</a></li>");
			out.println("<li><a href='/MainPage?type=dianying&cate=all&page=1'>电影</a></li>");
			out.println("<li><a href='/MainPage?type=zongyi&cate=all&page=1'>综艺</a></li>");
			out.println("<li><current><a href='/MainPage?type=dongman&cate=all&page=1'>动漫</a></current></li>");
		}
		
		
		out.println("<li><input type=\"text\" placeholder=\"尽情搜吧\" name=\"v_name\"><input type=\"submit\" value=\"搜搜\"></li>");
		out.println("</ul></form>");
		out.write("</div>");
		
		out.write("<div class='cate'>");
		out.write("类型:&nbsp;");
		if(!type.equals("all")){
			ArrayList<Category> cs=Movie.getCate(type);//根据分类获取分类额下的类型
			for(Category c:cs){
				String cat=c.getId();
				String name=c.getName();
				String hre=c.getUrl();
				out.write("<a href='/MainPage?type="+type+"&cate="+cat+"&page=1'>"+name+"</a>&nbsp;");
			}
		}
		out.write("</div>");	
		String main="https://www.360kan.com";
		if(type.equals("all")){
			
//			out.write("<div id=\"demo\" class=\"carousel slide\" data-ride=\"carousel\">");
//			
//			out.write("<ul class=\"carousel-indicators\">");
//			out.write("<li data-target=\"#demo\" data-slide-to=\"0\" class=\"active\"></li>");
//			out.write("<li data-target=\"#demo\" data-slide-to=\"1\"></li>");
//			out.write("<li data-target=\"#demo\" data-slide-to=\"2\"></li>");
//			out.write("</ul>");
//			
//			out.write("<div class=\"carousel-inner\">");
//			out.write("<div class=\"carousel-item active\"><img src=\"http://static.runoob.com/images/mix/img_fjords_wide.jpg\"></div>");
//			out.write("<div class=\"carousel-item\"><img src=\"http://static.runoob.com/images/mix/img_mountains_wide.jpg\"></div>");
//			out.write("<div class=\"carousel-item\"><img src=\"http://static.runoob.com/images/mix/img_nature_wide.jpg\"></div>");
//			out.write("</div>");
//			out.write("<a class=\"carousel-control-prev\" href=\"#demo\" data-slide=\"prev\"><span class=\"carousel-control-prev-icon\"></span></a>");
//			out.write("<a class=\"carousel-control-next\" href=\"#demo\" data-slide=\"next\"><span class=\"carousel-control-next-icon\"></span></a>");
//			out.write("</div>");
			
			ArrayList<VideoObj> vos=Movie.getdata("https://www.360kan.com/");
			System.out.println(vos.size());
			out.write("<div class='rank'>");
			out.write("<br>影视排行榜<br>");
			for(int i=0;i<10;i++){
				VideoObj vo=vos.get(i);
				int pai=vo.getPai();
				String name=vo.getName();
				String href=vo.getHref();
				String pn=vo.getPn();
				if(name.length()>6){
					name=name.substring(0,5)+"...";
				}
				//获取播放链接
				String purl=Movie.getPUrl(href);
				out.write("<pai>"+pai+"</pai>&nbsp;<a href='"+href+"'>"+name+"</a>&nbsp;"+pn+"<br>");
			}
			out.write("</div>");
			out.write("<div class='rank'>");
			out.write("<br>电视剧排行榜<br>");
			for(int i=10;i<20;i++){
				VideoObj vo=vos.get(i);
				int pai=vo.getPai();
				String name=vo.getName();
				String href=vo.getHref();
				String pn=vo.getPn();
				if(name.length()>6){
					name=name.substring(0,5)+"...";
				}
				//获取播放链接
				String purl=Movie.getPUrl(href);
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/LongVideos/player.jsp?type=dianshi&url="+href+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
			}
			out.write("</div>");
			
			out.write("<div class='rank'>");
			out.write("<br>综艺排行榜<br>");
			for(int i=20;i<30;i++){
				VideoObj vo=vos.get(i);
				int pai=vo.getPai();
				String name=vo.getName();
				String href=vo.getHref();
				String pn=vo.getPn();
				if(name.length()>6){
					name=name.substring(0,5)+"...";
				}
				//获取播放链接
				String purl=Movie.getPUrl(href);
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/LongVideos/player.jsp?type=zongyi&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
			}
			out.write("</div>");
			
			out.write("<div class='rank'>");
			out.write("<br>电影排行榜<br>");
			for(int i=30;i<40;i++){
				VideoObj vo=vos.get(i);
				int pai=vo.getPai();
				String name=vo.getName();
				String href=vo.getHref();
				String pn=vo.getPn();
				if(name.length()>6){
					name=name.substring(0,5)+"...";
				}
				//获取播放链接
				String purl=Movie.getPUrl(href);
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/LongVideos/player.jsp?type=dianying&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
			}
			out.write("</div>");
			
			out.write("<div class='rank'>");
			out.write("<br>动漫排行榜<br>");
			for(int i=40;i<50;i++){
				VideoObj vo=vos.get(i);
				int pai=vo.getPai();
				String name=vo.getName();
				String href=vo.getHref();
				String pn=vo.getPn();
				if(name.length()>6){
					name=name.substring(0,5)+"...";
				}
				//获取播放链接
				String purl=Movie.getPUrl(href);
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/LongVideos/player.jsp?type=dongman&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
			}
			out.write("</div>");
		}else{
			String url="https://www.360kan.com/"+type+"/list.php?rank=rankhot&cat="+cateI+"&area=all&year=all&pageno="+pageI;//?rank=rankhot&cat=all&area=all&year=all&pageno=2
//		    System.out.print(url);
			Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4

			Elements plays=doc.getElementsByClass("js-tongjic"); 
			  
			for(Element play: plays){
					  //  Attribute
//					  String href=play.attr("href");
				String p=play.toString();
					 
					  //写一个方法，取出href里面的内容，src里面的内容，以及span里的年份，视频名和评分，还有演员
				String[] s=p.split(">");
			    String hr=s[0]+">";
			    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
					  
				String sr=s[2]+">";
				String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));

				String year,name,score,actor;
			    if(p.contains("付费")){
					year=s[6].substring(0,4);					  
					name=s[11].split("<")[0];					  
					score=s[13].split("<")[0];					  
					actor=s[16].split("<")[0];
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
				String purl=Movie.getPUrl(hre);	  
				//从href网页源码中获取其他信息，如视频的详情，剧集，等等，然后传入播放界面并显示
				//或者只将hre传入player.jsp，在其里面进行这些信息的爬取操作
				out.write("<div class='whole'><a href=\"/LongVideos/player.jsp?type="+type+"&url="+hre+"&href="+purl+"\" target='_blank'>"
					  		+ "<img src='"+src+"' title='"+name+"' alt='"+src+"'>"
					  				+ "<em>"+name+"</em><br><em>"+actor+"</em><br><em>年份:"+year+"</em><br>"
					  						+ "<em>评分:"+score+"</em></a></div>");
					  
			   }
			
			out.write("<center><div class='page'>");
			if(pageI>1){//当前页码大于1，显示上一页按钮
				out.write("<a  href=\"/MainPage?type="+type+"&cate="+cate+"&page="+(pageI-1)+"\">上</a>");
			}
			if(pageI>24-6){//当前页码大于总页码-6，输出后六页
			    for(int j=24-5;j<=24;j++){
			        String pageIndex="<a href=\"/MainPage?type="+type+"&cate="+cate+"&page="+j+"\">"+j+"</a>";
			        if(j==pageI){
			             out.write("<current>"+pageIndex+"</current>");     
			        }else{
			             out.write(pageIndex); 
			        }
			    }
			}else{//当前页码小于总页码-6，输出当前页码后的六页
			    for(int j=pageI;j<pageI+6;j++){
			          String pageIndex="<a href=\"/MainPage?type="+type+"&cate="+cate+"&page="+j+"\">"+j+"</a>";
			          if(j==pageI){
			             out.write("<current>"+pageIndex+"</current>");     
			          }else{
			             out.write(pageIndex); 
			          }
			    }
			}
			if(pageI<24){//当前页码小于24，显示下一页按钮
				out.write("<a  href=\"/MainPage?type="+type+"&cate="+cate+"&page="+(pageI+1)+"\">下</a>");
			}
			out.write("</div></center>");
		}
	    out.write("<hr width='80%' height='5px'>");
		out.write("<br>");
        out.write("<footer><div class=\"footer\"><div class=\"f_l\">");
        out.write("<center><p>All Rights Reserved 版权所有：<a href=\"http://www.longqcloud.cn/\" target=\"_blank\">Long Blog</a> 备案号：豫ICP备16023798号-1</p>");
        out.write("<p>本站所有视频均采集自互联网，如有侵犯贵公司合法权益，请联系QQ1459892910删除，谢谢</p>");
        out.write("</div><div class=\"f_r textr\"><br><p>Design by Long Bro</p></center>");
        out.write("</div></div></footer>");
        out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	    
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
