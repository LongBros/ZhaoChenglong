package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.longbro.util.JdbcUtil;

import beans.Comment;

public class CommentDao {
	Connection con;
	Statement st;
	ResultSet rs;
	public ArrayList<Comment> queryCom(String require){
		ArrayList<Comment> comments=new ArrayList<Comment>();
		String sel=require;
		try {		
			con=JdbcUtil.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sel);
			while(rs.next()){
		    	int c_Id=rs.getInt("c_Id");
				int b_Id=rs.getInt("b_Id");
			    String cont=rs.getString("c_Content");			    
			    String time=rs.getString("c_Time");
				String ip=rs.getString("c_Ip");
				String add=rs.getString("c_Address");
				String path=rs.getString("c_ImgPath");
			    Comment comment=new Comment();
			    comment.setId(c_Id);
			    comment.setB_Id(b_Id);
			    comment.setContent(cont);
			    comment.setTime(time);
			    comment.setIp(ip);
			    comment.setAdd(add);
			    comment.setPath(path);
			    comments.add(comment);
			}
				
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	
		return comments;
	}

}
