package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import util.JdbcUtil;
import beans.ViewCon;

public class ViewConDao {
	Connection con;
	Statement st;
	ResultSet rs;
	public ArrayList<ViewCon> queryView(String sql){
		ArrayList<ViewCon> views=new ArrayList<ViewCon>();
		try{
			con=JdbcUtil.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				ViewCon view=new ViewCon();
				view.setV_Id(rs.getInt("v_Id"));
				view.setB_Id(rs.getInt("b_Id"));
				view.setV_Time(rs.getString("v_Time"));
				view.setV_Ip(rs.getString("v_Ip"));
				views.add(view);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return views;
	}

}
