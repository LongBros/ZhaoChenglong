package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BlogsDao;
import beans.Blogs;
import util.JdbcUtil;
import util.OtherUtil;

public class ReleaseBlog extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con;
		Statement st=null;
		ResultSet rs;
//		String author=request.getParameter("author");
		String category=request.getParameter("category");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String time=OtherUtil.time();
		
		Cookie[] cookie=request.getCookies();
		String author="";
		String qq="";
		for(int i=0;i<cookie.length&&cookie!=null;i++){
			if(cookie[i].getName().equals("account")){
				 qq=cookie[i].getValue();//从cookie中得到当前登录账号
			}
		}
		//根据账号查询作者名
	    con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select * from author where a_QQ="+qq;
		    rs=st.executeQuery(sql);
			if(rs.next()){
				author=rs.getString("a_Name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		PrintWriter out=response.getWriter();
		if(title.equals("")){
			out.write("没有标题的博文是一篇不完整的博文，so，请无论如何为你的博文添加一个标题呢！！！");
		}else if(content.equals("")){
			out.write("博文怎么可以没有内容，写点什么呢吧！！！");
		}else{
			Blogs blog=new Blogs();
			blog.setTitle(title);
			blog.setContent(content);
			blog.setTime(time);
			blog.setAuthor(author);
			BlogsDao dao=new BlogsDao();
			dao.writeBlog(category,blog);
			
            //博客发送成功，重定向至首页			
			response.sendRedirect("/LongBlog");
		}
		
	}

}
