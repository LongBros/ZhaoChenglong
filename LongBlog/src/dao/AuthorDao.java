package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.JdbcUtil;
import beans.Author;

/**
 * UPDATE `longblog`.`author` SET `a_Name`='王勃', `a_QQ`='2970164416', `a_Phone`='17520431816', `a_Pass`='0120' WHERE `a_Id`='2';
 * @date 2018-5-28
 * @author Administrator
 *
 */
public class AuthorDao {
	Connection con;
	Statement st;
	ResultSet rs;
	/**
	 * 查询所有作者
	 * @return 返回包含作者信息的集合
	 */
	public ArrayList<Author> queryAuthors(){
		ArrayList<Author> as=new ArrayList<Author>();
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select * from author";
			rs=st.executeQuery(sql);
			while(rs.next()){
				Author author=new Author();
				author.setA_Id(rs.getInt("a_Id"));
				author.setA_Name(rs.getString("a_Name"));
				System.out.println(rs.getString("a_Name"));
				author.setA_QQ(rs.getString("a_QQ"));
				author.setA_Phone(rs.getString("a_Phone"));
				author.setA_Pass(rs.getString("a_Pass"));
				author.setA_Sex(rs.getString("a_Sex"));
				
				as.add(author);
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
}
