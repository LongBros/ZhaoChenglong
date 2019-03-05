package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SqlUtil;

public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String nickname=request.getParameter("name");
		String pass1=request.getParameter("pass1");
		String pass2=request.getParameter("pass2");
		String question=request.getParameter("question");
		System.out.print(question);
		String answer=request.getParameter("answer");
        String code=request.getParameter("code");//用户输入的验证码
        PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		Cookie[] cookies=request.getCookies();
		//真实的验证码
		String cod=null;
		for(int i=0;i<cookies.length&&cookies.length!=0;i++){
			if(cookies[i].getName().equals("code")){
				cod=cookies[i].getValue().toString();
			}
		}
		if(nickname.equals("")||pass1.equals("")||pass2.equals("")
				||answer.equals("")||code.equals("")){
			out.write("部分信息未输入！");
		}else if(question.equals("")){
			out.write("未选择找回密码的问题！");
		}else if(!pass1.equals(pass2)){
			out.write("两次密码输入不一致！");
		}else if(!code.equals(cod)){
			out.write("验证码错误！");
		}else{
			//可以注册，添加至数据库
			try {
				//将问题和答案拼接后存入qq字段
				String que_ans=question+":"+answer;
		        System.out.println(que_ans);

				Class.forName(SqlUtil.driver);
				Connection con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
				Statement st=con.createStatement();
				String time=SqlUtil.time();
				st.executeUpdate("insert into user (nickname,pass,qq,r_Time) values ('"+nickname+"','"+pass1+"','"+que_ans+"','"+time+"');");
				out.println("<script>window.alert('注册成功，将为你跳转至首页。祝你观影愉快。')</script>");
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//注册成功，添加cookie，跳转至首页
			//防止中文报错
			nickname=URLEncoder.encode(nickname, "utf-8");
			Cookie cookie=new Cookie("user", nickname);
			cookie.setPath("/");
			cookie.setMaxAge(1*24*60*60);
			response.addCookie(cookie);
			response.sendRedirect("/");
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
