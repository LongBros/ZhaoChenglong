package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtilDao;
import utils.SqlUtil;

public class SendReply extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String r_User=request.getParameter("r_User");//回复者用户名
		String c_Id=request.getParameter("c_Id");//被回复的评论的id
		String reply=request.getParameter("reply");//回复的内容
		String time=SqlUtil.time();//回复的时间
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		if(r_User.equals("")){
			out.print("<a href='/login.jsp'>点击登录</a>，登陆后方可回复");
			out.print("<script>");
			out.print("window.alert('请先登录，登陆后方可回复！');");
			out.println("</script>");
		}else{
			if(reply.length()==0||reply.length()>150){
				out.print("<script>");
				out.print("window.alert('你输入的回复过长（大于150字符）或为空，请修改后再发布！');");
				out.println("</script>");
			}else{
				String sql="insert into reply (c_Id,r_User,r_Content,r_Time) values("+c_Id+",'"+r_User+"','"+reply+"','"+time+"')";
				System.out.println(sql);
				UtilDao.insertData(sql);
				out.print("<script>");
				out.println("window.alert('回复成功，请手动返回至上一页，并刷新方可看到你发布的回复。功能初成，给你带来的不便还请见谅。')");
				out.println("</script>");
			}
		}		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
