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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BlogsDao;
import beans.Blogs;
import util.JdbcUtil;
import util.OtherUtil;

public class UpdateBlog extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con;
		Statement st=null;
		ResultSet rs;
		String b_Id=request.getParameter("id");//需修改的博客id
		String author=request.getParameter("author");//作者
		String category=request.getParameter("category");//类别
		String title=request.getParameter("title");//标题
		String content=request.getParameter("content");//内容
		System.out.println(b_Id+"\n"+author+"\n"+category+"\n"+title+"\n"+content);
		PrintWriter out=response.getWriter();
		out.write("<html><body>");
		if(title.equals("")){
			out.write("没有标题的博文是一篇不完整的博文，so，请无论如何为你的博文添加一个标题呢！！！");
		}else if(content.equals("")){
			out.write("博文怎么可以没有内容，写点什么呢吧！！！");
		}else{
			//修改博客仅可修改博客分类，作者，标题，内容
			Blogs blog=new Blogs();
			blog.setTitle(title);
			blog.setContent(content);
			blog.setAuthor(author);
			
			BlogsDao dao=new BlogsDao();
			dao.updateBlog(b_Id,category,blog);
			out.write("<script type=\"text/javascript\">window.alert(\"修改成功\");</script>");
            //博客修改成功，重定向至首页			
			response.sendRedirect("/LongBlog/management/viewBlogs.jsp");
		}
		out.write("</body></html>");
		
	}

}
