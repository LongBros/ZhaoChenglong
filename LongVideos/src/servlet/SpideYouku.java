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

public class SpideYouku extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageId=request.getParameter("pageId");
		
		String url="http://list.youku.com/category/show/"
				+ "c_96_u_1_pt_1_s_1_d_4_p_"+pageId+".html";
		//http://list.youku.com/category/show/c_96_u_1_pt_1_s_1_d_4_p_1.html   -----电影
		//http://list.youku.com/category/show/c_97_s_1_d_1_p_2.html            -----电视剧
		//http://list.youku.com/category/show/c_85_s_1_d_1_p_2.html            -----综艺
		//http://list.youku.com/category/show/c_100_s_1_d_1_p_4.html           -----动漫
		Document doc=Jsoup.connect(url).get();
        Elements  movs=doc.getElementsByClass("mr1");//p-thumb，用外层div不行
        PrintWriter out=response.getWriter();
		 
        out.println("<head>");
		out.println("<title>Long Bro影院_小酷欢迎你</title>");

		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\"> ");

		out.println("</head>");
		out.write("<body>");
        for(Element mov:movs){
        	String sMov=mov.toString();
        	//获取播放链接
        	String href=sMov.substring(sMov.indexOf("<a href=\"")+9,sMov.indexOf(".html\"")+5);
        	if(sMov.contains("正片")){//正片中无http://
        		href="http:"+href;
        	}
        	//获取视频背景图
        	String img=sMov.substring(sMov.indexOf("_src=\"")+6,sMov.indexOf("\" src=\""));
        	//获取视频名
        	String title=sMov.substring(sMov.indexOf("title=\"")+7,sMov.indexOf("\" target="));        	
        	//获取主演，播放量
        	if(sMov.contains("<li class=\"actor\">")){//是电影或电视剧，有主演
        		String mix=sMov.substring(sMov.indexOf("<li class=\"actor\">"));            	      	
            	//获取播放量
            	String playNum=mix.substring(mix.indexOf("<li>")+4,mix.indexOf("次播放 </li>")+3);
            	out.write(" <div class='whole'><a href='/LongVideos/playerYouku.jsp?href="+href+"'  target=\"_blank\">"
				  		+ "<img src='"+img+"' title='"+img+"' alt='"+img+"'><em>"+title+"</em>"
				  				+ "</a><br><em>主演:</em>");
            	
            	//获取主演
            	String [] actors=mix.split("<a href=");
            	
            	for(int i=1;i<actors.length;i++){
            		//获取演员详细信息链接
            		String actorL="http://"+actors[i].substring(3,actors[i].indexOf("\" target"));
            		//获取演员名
            		String actorN=actors[i].substring(actors[i].indexOf(">")+1, actors[i].indexOf("<"));
            		out.write("<a href='"+actorL+"' target='_blank' title='"+actorN+"'><em>"+actorN+","+"<em></a>");
            		
            	}
            	out.write("<br><em>"+playNum+"</em></div>");
      	    }else{//<ul class="info-list">  -------无主演
        		String mix=sMov.substring(sMov.indexOf("<ul class=\"info-list\">"));            
            	//获取播放量
            	String playNum=mix.substring(mix.indexOf("<li>")+4,mix.indexOf("次播放 </li>")+3);
            	
            	out.write("<a href=\"/LongVideos/playerYouku.jsp?href="+href+"\" target=\"_blank\"> <div class='whole'>"
				  		+ "<img src='"+img+"' title='"+img+"' alt='"+img+"'><em>"+title+"</em>"
				  				+ "<br><em>"+playNum+"</em></div></a>");
        	}
        }
		out.write("</body>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

}
