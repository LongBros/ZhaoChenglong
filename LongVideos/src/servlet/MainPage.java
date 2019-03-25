package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.longbro.bean.Category;
import com.longbro.bean.VideoObj;

import utils.Movie;
/**
 * @author 赵成龙
 * @website www.longqcloud.cn & www.zy52113.com
 * @date 2018年9月26日 上午9:07:47
 * @description 添加两个字符串数组，修改了赘余代码
 * @version
 */
public class MainPage extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取用户名
		Cookie[] cookies=request.getCookies();
		String nick="";
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("user")){
				nick=cookies[i].getValue().toString();
			}
		}
		response.setContentType("text/html");
		String type=request.getParameter("type");//分类
		String cate=request.getParameter("cate");//类型
		String page=request.getParameter("page");
		int pageI=1;
		String cateI="";
		String what[]={"首页","电视剧","电影","综艺","动漫"};
		String we[]={"index","dianshi","dianying","zongyi","dongman"};
		//2019-03-20利用以下方法代替3-11日写的代码
		if(StringUtils.isEmpty(type)){
			type="dianshi";
		}
		/*//2019-03-11利用以下代码代替注释部分
		for(int i=0;i<we.length;i++){
			if(we[i].equals(type)){
				type=we[i];
				break;
			}
			if(i==4){//i=4时循环将结束，还没break是因为初次进入type参数为null
				type="dianshi";
			}
		}*/
		
		/*if(("dianshi").equals(type)){
			type="dianshi";
		}else if(("dianying").equals(type)){
			type="dianying";
		}else if(("zongyi").equals(type)){
			type="zongyi";
		}else if(("dongman").equals(type)){
			type="dongman";
		}else if(("index").equals(type)){
			type="index";
		}else{//实现初次进入无type参数时type置为dianshi
			type="dianshi";
		}*/
		
		if("other".equals(cate)){
			cateI="other";
		}else if("all".equals(cate)||StringUtils.isEmpty(cate)){
			cateI="all";
		}
		else{
			cateI=cate;
			/*//类型
			ArrayList<String> arr1=new ArrayList<String>();
			for(int i=100;i<135;i++){
				String t=i+"";
				arr1.add(t);
			}
			//实现直接进入为全部，点击类型进入，为类型的
			for(int i=0;i<arr1.size();i++){
				if((arr1.get(i)).equals(cate)){
					cateI=arr1.get(i);
					break;//
				}
				if(i==(arr1.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
					cateI="all";
				}
			}		*/
		}
		if(StringUtils.isEmpty(page)){
			pageI=1;
		}else{
			pageI=Integer.parseInt(page);

		}
		/*
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
		*/
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Long Bro影院|最新，最全，最受欢迎的影视免费免广告在线观看</TITLE>");
	    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/mainpage.css\"> ");
	    out.println("</HEAD>");
		out.println("  <BODY>");
		out.println("<div class='bar'>");
		out.println("<form action=\"/search.jsp\" method=\"post\" target=\"_blank\"><ul>");
		out.println("<li><a href='/index.jsp'>LongBro影院</a></li>");
//		2018-9-26使用以下逻辑方法避免代码赘余问题
		for(int i=0;i<5;i++){
			if(type.equals(we[i])){
				out.println("<li><current><a href='/MainPage?type="+we[i]+"&cate=all&page=1'>"+what[i]+"</a></current></li>");
			}else{
				out.println("<li><a href='/MainPage?type="+we[i]+"&cate=all&page=1'>"+what[i]+"</a></li>");
			}
		}
		//2018-9-26使用以上逻辑方法避免代码赘余问题
		out.println("<li><input type=\"text\" placeholder=\"尽情搜吧\" name=\"v_name\"><input type=\"submit\" value='搜索'></li>");

		if(nick.equals("")){
			out.println("<li><a href='/login.jsp'>登录/注册</a></li>");
		}else{
			System.out.println(nick);
			out.println("<li><a href='/myInfo.jsp'>"+nick+"</a></li>");
		}
		
		out.println("</ul></form>");
		out.write("</div>");
		
		out.write("<div class='cate'>");
		if(!type.equals("index")){//不是首页，则爬取分类视频下的类型
			out.write("类型:&nbsp;");
			ArrayList<Category> cs=Movie.getCate(type);//根据分类获取分类下的类型
			out.write("<a href='/MainPage?type="+type+"&cate=all&page=1'>全部</a>&nbsp;");
			for(Category c:cs){
				String cat=c.getId();
				String name=c.getName();
				String hre=c.getUrl();
				out.write("<a href='/MainPage?type="+type+"&cate="+cat+"&page=1'>"+name+"</a>&nbsp;");
			}
		}
		out.write("</div>");	
		String main="https://www.360kan.com";
		if(type.equals("index")){	//首页，则爬取热门电影和排行榜	
			out.write("<p>&nbsp;&nbsp;&nbsp;热门电影</p>");
			//爬取热门电影
			Document doc=Jsoup.connect(main).get();
	        Elements s=doc.getElementsByClass("rmcontent"); 
	        String ss=s.toString();
	        String hot[]=ss.split("</li>");
	        for(int i=0;i<hot.length-1;i++){
	        	String href=hot[i].substring(hot[i].indexOf("href")+6,hot[i].indexOf("\" class=\"js-link\">"));
//	        	String year=hot[i].substring(hot[i].indexOf("class=\"w-newfigure-hint\">")+25,hot[i].indexOf("</span></div>"));
	        	String img=hot[i].substring(hot[i].indexOf("data-src")+10,hot[i].indexOf("\" alt="));
	        	String name="";
	        	String score="";
	        	if(hot[i].contains("class=\"s2")){//有评分
	            	name=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span><span"));
	                score=hot[i].substring(hot[i].indexOf("class=\"s2")+11,hot[i].indexOf("</span></p"));
	        	}else{//无评分
	            	name=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span></p"));
	        	}
	        	out.write("<div class='whole'><a href=\"/player.jsp?type=dianying&url="+href+"\" target='_blank'>"
				  		+ "<img src='"+img+"' title='"+name+"' alt='"+img+"'>"
				  				+ "<span>"+name+"</span><br><span>"+score+"</span></a></div>");
			   }
	        
			
			
			ArrayList<VideoObj> vos=Movie.getdata(main);
//			System.out.println(vos.size());
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
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=dianshi&url="+href+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
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
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=zongyi&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
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
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=dianying&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
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
				out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=dongman&url="+vo.getHref()+"&href="+purl+"\" target='_blank'>"+name+"</a>&nbsp;"+vo.getPn()+"<br>");
			
			}
			out.write("</div>");
		}else{
			String url="https://www.360kan.com/"+type+"/list.php?rank=rankhot&cat="+cateI+"&area=all&year=all&pageno="+pageI;
			Document doc=Jsoup.connect(url).get();
			Elements plays=doc.getElementsByClass("js-tongjic"); 
			for(Element play: plays){
				String p=play.toString();
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
				if(actor.length()>16){
					actor=actor.substring(0, 16)+"...";
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
				//从href网页源码中获取其他信息，如视频的详情，剧集，等等，然后传入播放界面并显示
				//或者只将hre传入player.jsp，在其里面进行这些信息的爬取操作	
				out.write("<div class='whole'><a href=\"/player.jsp?type="+type+"&url="+hre+"\" target='_blank'>"
				  		+ "<img src='"+src+"' title='"+name+"' alt='"+src+"'>"
				  				+ "<span>"+name+"</span><br><p>"+actor+"</p></a></div>");
			   }
			
			out.write("<center><div class='page'>");
			if(pageI>1){//当前页码大于1，显示上一页按钮
				out.write("<a  href=\"/MainPage?type="+type+"&cate="+cate+"&page="+(pageI-1)+"\">上</a>");
			}
			if(pageI>24-6){//当前页码大于总页码-6，输出后六页
			    for(int j=24-5;j<=24;j++){
			        if(j==pageI){
			             out.write("<current><a>"+j+"</a></current>");     
			        }else{
			             out.write("<a href=\"/MainPage?type="+type+"&cate="+cate+"&page="+j+"\">"+j+"</a>"); 
			        }
			    }
			}else{//当前页码小于总页码-6，输出当前页码后的六页
			    for(int j=pageI;j<pageI+6;j++){
			    	if(j==pageI){
			             out.write("<current><a>"+j+"</a></current>");     
			        }else{
			             out.write("<a href=\"/MainPage?type="+type+"&cate="+cate+"&page="+j+"\">"+j+"</a>"); 
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
        out.write("<center><p>All Rights Reserved 版权所有：<a href=\"http://www.longqcloud.cn/\" target=\"_blank\">Long Blog</a> 备案号：<a href=\"http://www.miitbeian.gov.cn\" target=\"_blank\">豫ICP备16023798号-1</a></p></center>");
        out.write("<center><p>本站所有视频均采集自互联网，如有侵犯贵公司合法权益，请联系QQ<a href=\"http://wpa.qq.com/msgrd?v=3&uin=1459892910&site=qq&menu=yes\" target=\"_blank\">1459892910</a>删除，谢谢</p></center>");
        out.write("<center><p>Designed by Long Bro</p></center>");
        out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	    
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
