package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SqlUtil;

public class GetPass extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		response.setContentType("text/html");
		String user=request.getParameter("user");
		String qq=request.getParameter("qq");
		qq=URLDecoder.decode(qq,"utf-8");
		PrintWriter out = response.getWriter();
		if(!user.equals("")&&!qq.equals("")){
			String sql="select pass from user where nickname='"+user+"' and qq like '%"+qq+"'";
			try {
				Class.forName(SqlUtil.driver);
				con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
				st=con.createStatement();
				rs=st.executeQuery(sql);
				if(rs.next()){
					out.write("你的密码为:"+rs.getString(1)+",请牢记");
				}else{
					out.write("未匹配到对应数据，请确认你已注册且输入正确");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			out.write("部分信息未输入");
		}
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
