package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longbro.util.AddressUtils;
import com.longbro.util.JdbcUtil;

import beans.Blogs;
import beans.Comment;
import beans.ViewCon;
import dao.BlogsDao;
import dao.CommentDao;
import dao.ViewConDao;
/**
 * 备份博文，评论，登录日志，阅读情况等数据库信息
 * 注意，服务器上一定要加上getPost方法
 * @author Administrator
 * @time 2018-5-20
 *
 */
public class BackupServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		out.write("<head><title>数据备份防丢失|向数据库添加地址字段</title></head>");
//		backupBlog(out);
//		backupCom(out);
//		backupView(out);
		//因表中添加地址字段，需为库中已有的评论和登录日志添加地址
		addAddress(out);
		addAddress1(out);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	//为库中已有的评论添加对应的地址
	public static void addAddress(PrintWriter out){
		Connection con=JdbcUtil.getConnection();
		out.write("开始为评论表插入地址字段...<br><br>");
		try {
			Statement st=con.createStatement();
			String s1="select * from comment";
			ResultSet rs=st.executeQuery(s1);
			while(rs.next()){
				int  id=rs.getInt("c_Id");
				String ip=rs.getString("c_Ip");
				String a=rs.getString("c_Address");
				if(a.equals("")){
					String add=AddressUtils.getAddByIp(ip);
					String s2="update comment set c_Address='"+add+"' where c_Id='"+id+"';";
					out.println(s2+"<br>");
					Statement st1=con.createStatement();
					st1.executeUpdate(s2);			
				}else{
					out.println("该条记录库中已有地址<br>");
				}
			}
			out.write("已为所有评论插入地址字段...<br><br>");
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//为库中已有的登录日志添加对应的地址
	public static void addAddress1(PrintWriter out){
		Connection con=JdbcUtil.getConnection();
		out.write("开始为登录日志表插入地址字段...<br><br>");
		try {
			Statement st=con.createStatement();
			String s1="select * from log_vis";
			ResultSet rs=st.executeQuery(s1);
			while(rs.next()){
				int id=rs.getInt("l_Id");
				String ip=rs.getString("l_Ip");
				String a=rs.getString("l_Address");
				if(a.equals("")){
					String add=AddressUtils.getAddByIp(ip);
					String s2="update log_vis set l_Address='"+add+"' where l_Id='"+id+"';";
					out.println(s2+"<br>");
					Statement st1=con.createStatement();
					st1.executeUpdate(s2);			
				}
			}
			out.write("已为所有登录日志插入地址字段。..<br><br>");
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//备份博文
	public static void backupBlog(PrintWriter out){
		String bs="";//所有博文
		String b="";//当条博文
		BlogsDao dao=new BlogsDao();
		ArrayList<Blogs> blogs=dao.queryBlogs("select * from blogs");
		for(Blogs blog :blogs){
			int id=blog.getId();//博客id
			String title=blog.getTitle();//标题
			String cont=blog.getContent();//内容
			String time=blog.getTime();//发布时间
			String author=blog.getAuthor();//作者
			String path=blog.getImgPath();//图片路径
			int vnum=blog.getViewNum();//阅读量
			int cnum=blog.getComment();//评论数量
			int cat=blog.getCat_Id();//分类id
			
			b=id+"&"+title+"&"+cont+"&"+time+"&"+author+"&"+path+"&"+vnum+"&"+cnum+"&"+cat+"】";//当条博文
			bs=bs+b;//所有博文
		}
//		System.out.println(bs);
//		File file=new File("D:/backup/longblog/blogs.txt");
		File file=new File("/home/ubuntu/backup/longblog/blogs.txt");//
		writeDbtoFile(bs,file);
		out.write("已读取数据至"+file.getAbsolutePath()+"<br>");
	}
	//备份阅读情况
	public void backupCom(PrintWriter out){
		String cs="";//所有评论
		String c="";//当条评论
		CommentDao dao=new CommentDao();
		ArrayList<Comment> comments=dao.queryCom("select * from comment");
		for(Comment comment :comments){
			int id=comment.getId();//评论id
			int bid=comment.getB_Id();//评论所属博客id
			String cont=comment.getContent();//内容
			String time=comment.getTime();//评论时间
			String ip=comment.getIp();//评论者的ip
			String path=comment.getPath();//评论者头像图片路径
			
			c=id+"&"+bid+"&"+cont+"&"+time+"&"+ip+"&"+path+"】";//当条博文
			cs=cs+c;//所有博文
		}
//		System.out.println(bs);
//		File file=new File("D:/backup/longblog/comments.txt");
		File file=new File("/home/ubuntu/backup/longblog/comments.txt");
		writeDbtoFile(cs,file);
		out.write("已读取数据至"+file.getAbsolutePath()+"<br>");

	}
	
	//备份阅读情况
	public static void backupView(PrintWriter out){
		String vs="";//所有评论
		String v="";//当条评论
		ViewConDao dao=new ViewConDao();
		ArrayList<ViewCon> views=dao.queryView("select * from viewcon");
		for(ViewCon view :views){
			int id=view.getV_Id();//评论id
			int bid=view.getB_Id();//评论所属博客id
			String time=view.getV_Time();//评论时间
			String ip=view.getV_Ip();//评论者的ip
			
			v=id+"&"+bid+"&"+time+"&"+ip+"&"+"】";//当条博文
			vs=vs+v;//所有博文
		}
//		File file=new File("D:/backup/longblog/viewcons.txt");
		File file=new File("/home/ubuntu/backup/longblog/viewcons.txt");
		writeDbtoFile(vs,file);
		out.write("已读取数据至"+file.getAbsolutePath()+"<br>");

	}   		
	public static void backLoginLog(PrintWriter out){
		String l="";//单条日志
		String ls="";//所有访问日志
		String sql="select * from log_vis";
		Connection conn=JdbcUtil.getConnection();
		try {
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				int id=rs.getInt("l_Id");
				String ip=rs.getString("l_Ip");
				String time=rs.getString("l_Time");
				l=id+"&"+ip+"&"+time+"&"+"】";
				
				ls=ls+l;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file=new File("/home/ubuntu/backup/longblog/loginlog.txt");
		writeDbtoFile(ls,file);
		out.write("已读取数据至"+file.getAbsolutePath()+"<br>");
	}
	public static void writeDbtoFile(String bs,File file){
		//循环执行完毕后，bs中已存入所有博文
		try {
			if(file.exists()){
				file.delete();
				file.createNewFile();
			}else{
				file.createNewFile();
			}
			FileOutputStream out=new FileOutputStream(file);
			out.write(bs.getBytes());
			System.out.println("已读取数据至"+file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
