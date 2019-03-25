package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtilDao;
import utils.SqlUtil;

public class Store extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		String url=request.getParameter("url");//要收藏的视频的链接
		String title=request.getParameter("title");//要收藏的视频名
		title=URLDecoder.decode(title,"utf-8");
		String type=request.getParameter("type");//要收藏的视频的类型
		String img=request.getParameter("img");//要收藏的视频的图片
		Cookie[] cookies=request.getCookies();
		String nick="";//收藏者
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("user")){
				nick=cookies[i].getValue().toString();
				nick=URLDecoder.decode(nick, "utf-8");
			}
		}
		String time=SqlUtil.time();
		String query="select * from store where s_User='"+nick+"' and s_Url='"+url+"'";
		String insert="insert into store(s_User,s_Url,s_Vname,s_Path,s_Type,s_Time) "
				+ "values('"+nick+"','"+url+"','"+title+"','"+img+"','"+type+"','"+time+"')";
		System.out.println(insert);
		UtilDao.addStore(query, insert);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
