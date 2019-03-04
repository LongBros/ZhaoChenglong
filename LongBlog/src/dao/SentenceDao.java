package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.JdbcUtil;
import beans.Author;
import beans.Sentence;

/**
 * 每日一句
 * @author Administrator
 *
 */
public class SentenceDao {
	Connection con;
	Statement st;
	ResultSet rs;
	/**
	 * 查询数据库中所有已显示的每日一句
	 */
	public ArrayList<Sentence> queryAll(String require) {
		ArrayList<Sentence> as=new ArrayList<Sentence>();
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select * from sentence order by s_Id desc"+require;
			rs=st.executeQuery(sql);
			while(rs.next()){
				//实例化Sentence不能放在循环外部，不然存入数组的会是同一个
				Sentence sentence=new Sentence();
				sentence.setS_Id(rs.getInt("s_Id"));
				sentence.setS_Date(rs.getString("s_Date"));
				sentence.setS_Chinese(rs.getString("s_Chinese"));
				sentence.setS_English(rs.getString("s_English"));
				sentence.setS_Time(rs.getString("s_Time"));
				sentence.setS_Ciba(rs.getString("s_Ciba"));
				
				as.add(sentence);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return as;
	}

	/**
	 * 查询每日一句的数量
	 * @return
	 */
	public int queryNum() {
		int num=0;
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select count(*) from sentence";
			rs=st.executeQuery(sql);
			if(rs.next()){
				num=rs.getInt("count(*)");
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
}
