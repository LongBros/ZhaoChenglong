package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longbro.util.JdbcUtil;


public class Login extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>登录状态</TITLE></HEAD>");
		out.println("  <BODY>");
		
		response.setContentType("text/html");
		
		String acc=request.getParameter("account");
		String pass=request.getParameter("password");
		String option=request.getParameter("option");
		System.out.println(option);
		Connection con=JdbcUtil.getConnection();
		try {
			Statement st=con.createStatement();
			String sql="select * from author where a_QQ='"+acc+"' and a_Pass='"+pass+"';";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){//
				//登录成功，向cookie中添加数据
				Cookie cookie=new Cookie("account", acc);
				cookie.setPath("/");
				cookie.setMaxAge(30*24*60*60);
				response.addCookie(cookie);
				if(option.equals("write")){
					response.sendRedirect("/writeBlog.jsp");
				}else if(option.equals("manage")){
					response.sendRedirect("/management/manage.jsp");
				}
			}else{
				out.write("<script type=\"text/javascript\">window.alert(\"账号或密码错误\");</script>");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//未连接数据库的登录操作
		/*if(acc.equals("145989")&&pass.equals("0120")){
			//登录成功，向cookie中添加数据
			Cookie cookie=new Cookie("account", acc);
			cookie.setPath("/");
			cookie.setMaxAge(30*24*60*60);
			response.addCookie(cookie);
			if(option.equals("write")){
				response.sendRedirect("/LongBlog/writeBlog.jsp");
			}else if(option.equals("manage")){
				response.sendRedirect("/LongBlog/management/manage.html");
			}
		}else{
			out.write("<script type=\"text/javascript\">window.alert(\"账号或密码错误\");</script>");
			return;
		}*/
		
		
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
