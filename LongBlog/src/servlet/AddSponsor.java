package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longbro.util.JdbcUtil;

public class AddSponsor extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String source=request.getParameter("source");
		String remark=request.getParameter("remark");
		double amount=Double.parseDouble(request.getParameter("amount"));
		String time=request.getParameter("time");
		Connection con=JdbcUtil.getConnection();
		try {
			Statement st=con.createStatement();
			st.executeUpdate("insert into sponsor (s_Time,s_Source,s_Remark,s_Amount) values('"+time+"','"+source+"','"+remark+"','"+amount+"');");
		    
			st.close();
		    con.close();
		    response.sendRedirect("/sponsor/showSponsor.jsp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
