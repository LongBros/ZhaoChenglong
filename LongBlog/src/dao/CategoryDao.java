package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.JdbcUtil;
import beans.Category;

public class CategoryDao {
	Connection con;
	Statement st;
	ResultSet rs;
	//查询库中所有分类信息----------已在header.jsp中使用
	public ArrayList queryCategories() throws SQLException{
		ArrayList categories=new ArrayList();
		con=JdbcUtil.getConnection();
		st=con.createStatement();
		//加载博客类别
        String sel="select * from category";
        rs=st.executeQuery(sel);
        String cat_Name;
        int cat_Num;
        int cat_Id;
        while(rs.next()){
           cat_Name=rs.getString("cat_Name");
           cat_Num=rs.getInt("cat_Num");
           cat_Id=rs.getInt("cat_Id");
           
           Category category=new Category();
           category.setCat_Id(cat_Id);
           category.setCat_Name(cat_Name);
           category.setCat_Num(cat_Num);
           
           categories.add(category);
	   }
        rs.close();
        st.close();
        con.close();
		return categories;

	}
	
	//根据类别id查询类别名--------已在cat_search.jsp中使用
		public String queryCatNameById(int c_Id) throws SQLException {
			con=JdbcUtil.getConnection();
			st=con.createStatement();
			//查询该博客类别id的类别名
	        String sel="select cat_Name from category where cat_Id="+c_Id+";";
	        st=con.createStatement();
	        rs=st.executeQuery(sel);//此处需定义一新的结果集
	        String cat_Name="";
	        if(rs.next()){
	           cat_Name=rs.getString("cat_Name");
	        }
	        rs.close();
	        st.close();
	        con.close();
			return cat_Name;
		}

}
