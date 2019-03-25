package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BlogsDao;

public class DeleteBlog extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("id");//要删除的博客的id
		PrintWriter out=response.getWriter();
		//将该博客加入回收站(d_blogs表)并删除博客
		BlogsDao dao=new BlogsDao();
		dao.deleteBlog(id);
		response.sendRedirect("/management/viewBlogs.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
