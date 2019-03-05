package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtilDao;
import utils.SqlUtil;

public class SendCom extends HttpServlet {

	/**
	 * 
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String com=request.getParameter("comment");
		com=URLDecoder.decode(com,"utf-8");
		//注意：评论之中的图片地址带'，要换为"
		com=com.replace("'", "\"");
		String url=request.getParameter("url");
		//采取从cookie中获取用户名方式避免ajax从url传来导致乱码
		Cookie[] cookies=request.getCookies();
		String nick="";
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("user")){
				nick=cookies[i].getValue().toString();
				nick=URLDecoder.decode(nick, "utf-8");
			}
		}
		String time=SqlUtil.time();
		//在player.jsp中已判断用户是否已登录，以及评论为空或过长的问题，故此处可直接发表评论
		String sql="insert into comment(c_Video,c_User,c_Content,c_Znum,c_Cnum,c_Time) values('"
				+url+"','"+nick+"','"+com+"',"+"0,"+"0,'"+time+"')";
		UtilDao.insertData(sql);//向评论表插入评论
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
