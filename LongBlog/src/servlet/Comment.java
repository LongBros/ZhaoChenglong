package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longbro.util.JdbcUtil;
import com.longbro.util.OtherUtil;
/**
 * 发布评论(评论id，被评论的博客的id，评论内容，评论时间，评论来源的ip)
 * 发布评论的同时，该条博客下的评论数要加1
 * @author Administrator
 *
 */
public class Comment extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String content=request.getParameter("content");
		String b_Id=request.getParameter("b_Id");
		String l_Ip=request.getParameter("c_Ip");
		
		Random ran=new Random();
		int i=ran.nextInt(39);
		String image=OtherUtil.imgPath(i);
		Connection con=JdbcUtil.getConnection();
		String sql="insert into comment(b_Id,c_Content,c_Time,c_Ip,c_ImgPath) values(?,?,?,?,?)";
		try {
			String update="update blogs set b_ComNum=b_ComNum+1 where b_Id="+b_Id+";";
			Statement st=con.createStatement();
			st.executeUpdate(update);
			
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(b_Id));
			ps.setString(2, content);
			ps.setString(3, OtherUtil.time());
			ps.setString(4, l_Ip);
			ps.setString(5, image);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("/LongBlog/detailblog.jsp?id="+b_Id);

	}

}
