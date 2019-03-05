package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SqlUtil;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String nickname=request.getParameter("name");
		String pass=request.getParameter("pass");
		String code=request.getParameter("code");//用户输入的验证码
		Cookie[] cookies=request.getCookies();
		//真实的验证码
		String cod=null;
		for(int i=0;i<cookies.length&&cookies.length!=0;i++){
			if(cookies[i].getName().equals("code")){
				cod=cookies[i].getValue().toString();
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		if(!code.equalsIgnoreCase(cod)){//验证码不正确,若为字母则不区分大小写验证
			out.println("code error");
		}else{
			try {
				Class.forName(SqlUtil.driver);
				Connection con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("select * from user where nickname='"+nickname+"' and pass='"+pass+"'");
				if(rs.next()){
					//添加用户名cookie
					nickname=URLEncoder.encode(nickname, "utf-8");
					Cookie cookie=new Cookie("user", nickname);
					cookie.setMaxAge(30*24*60*60);
					cookie.setPath("/");
					response.addCookie(cookie);
					out.write("<script>window.alert('登录成功，将为你跳转至主界面');</script>");
					response.sendRedirect("/");
				}else{
					System.out.print("find user error");
					out.write("<script>window.alert('未找到该注册用户，请确认你已注册并信息输入正确！');</script>");
					response.sendRedirect("login.jsp");
				}
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().write("<script>window.alert('登录成功，将为你跳转至主界面'</script>");

		}
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
