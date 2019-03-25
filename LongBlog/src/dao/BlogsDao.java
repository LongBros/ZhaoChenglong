package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import others.Dytosta;

import com.longbro.util.JdbcUtil;
import com.longbro.util.OtherUtil;

import beans.Blogs;
import beans.TimeNum;
/**
 * 1. 查询指定类型博客的条数
 * 2.查询指定时间对应的博客的数量
 * 3.按照指定条件,指定页码查询博客 
 * 4.新建博客
 * 5.生成最新发布博文的静态html
 * 6.删除指定ID的博客
 * 7.修改博客
 * 8.博文阅读量加1
 * 9.提供将删除的博客恢复功能
 * 10.生成访问日志
 * 11.网站的访问数量
 * 12.根据博客id获取博客名
 * @author Administrator
 *
 */
public class BlogsDao {
	Connection con;
	Statement st;
	ResultSet rs;
	PreparedStatement ps;

	/**
	 *1. 查询指定类型博客的条数
	 * @param type 查询语句--要查询的博客类型
	 * @return  int 查询到的数量--该类型下的博客数量
	 */
	public int queryBlogsNum(String type) {
		int num=0;
		try{
			con=JdbcUtil.getConnection();
			st=con.createStatement();
			 String blognum="select count(*) from blogs"+type;
             rs=st.executeQuery(blognum);
             if(rs.next()){
                 num=rs.getInt("count(*)");
             }
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	/**
	 * 2.查询指定时间对应的博客的数量
	 * @param what
	 * @param limit
	 * @return
	 */
	public ArrayList queryBlogs(String what,String limit){
        String select="select "+what+" from blogs;";//年月日
        ArrayList<TimeNum> arr=new ArrayList<TimeNum>();
        try {
        	con=JdbcUtil.getConnection();
			st=con.createStatement();
            rs=st.executeQuery(select);

			while(rs.next()){
			   String y_m=rs.getString("y_m");//月份
			   String se="select count(*) as amo from blogs where b_Time like '%"+y_m+"%'";
			   Statement st1=con.createStatement();
			   ResultSet rs1=st1.executeQuery(se);
			   int amo=0;//月份下写的博客数量
			   if(rs1.next()){
			       amo=rs1.getInt("amo");
			   }
			   //存入bean
			   TimeNum tn=new TimeNum();
			   tn.setTime(y_m);
			   tn.setNum(amo);
			   //将bean添加至数组列表
			   arr.add(tn);
			   rs1.close();
			   st1.close();
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	/**
	 * 3.按照指定条件,指定页码查询博客      ----已在cat_search中使用
	 * @param sql
	 * @return
	 */
	public ArrayList<Blogs> queryBlogs(String sql){
		ArrayList<Blogs> blogs=new ArrayList<Blogs>();
		try{
			con=JdbcUtil.getConnection();
			st=con.createStatement();
		     System.out.println();

             rs=st.executeQuery(sql);
             while(rs.next()){
            	 int id=rs.getInt("b_Id"); //根据id来查看某条博客的全部内容
                 String title=rs.getString("b_Title");
                 String content=rs.getString("b_Content");
                 String time=rs.getString("b_Time");
                 String author=rs.getString("b_Author");
                 String imgPath="http://img.zy52113.com/blog/"+rs.getString("b_ImagePath")+".jpg";
                 int readNum=rs.getInt("b_ViewNum");
                 int comment=rs.getInt("b_ComNum");
                 int c_Id=rs.getInt("cat_Id");
                 
                 Blogs blog=new Blogs();
                 blog.setId(id);
                 blog.setTitle(title);
                 blog.setContent(content);
                 blog.setTime(time);
                 blog.setAuthor(author);
                 blog.setImgPath(imgPath);
                 blog.setViewNum(readNum);
                 blog.setComment(comment);
                 blog.setCat_Id(c_Id);
                 blogs.add(blog);
                 
             }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return blogs;
	}
	/**
	 * 4.新建博客
	 * @param category
	 * @param blog
	 */
	public void writeBlog(String category,Blogs blog){
		con=JdbcUtil.getConnection();

		try {
			//查询该分类在分类表中的类别id
			String sel="select cat_Id from category where cat_Name='"+category+"';";
//			System.out.println(sel);
			st=con.createStatement();
			rs=st.executeQuery(sel);
			int c_Id=1;
			if(rs.next()){
			    c_Id=rs.getInt("cat_Id");
			}
			//该类别下博客数量加1
			String update="update category set cat_Num=cat_Num+1 where cat_Id="+c_Id+";";
//			System.out.println(update);
			st.executeUpdate(update);
			//将博客插入博客表
			String sql="insert into blogs(b_Title,b_Content,b_Time,b_Author,b_ImagePath,b_ViewNum,b_ComNum,cat_Id) values (?,?,?,?,?,?,?,?)";
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, blog.getTitle());
			ps.setString(2, blog.getContent());
			ps.setString(3, blog.getTime());
			ps.setString(4, blog.getAuthor());
			Random random=new Random();
			int i=random.nextInt(18);
			ps.setString(5, "love"+i);
			ps.setInt(6, 1);//访问量
			ps.setInt(7, 0);//评论
			ps.setInt(8, c_Id);
			ps.executeUpdate();
			
			ps.close();
			st.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 5.生成最新发布博文的静态html
	 * @param url
	 */
	public void genHtml(String url){
		con=JdbcUtil.getConnection();
		try {
			//以下字符串均能查到最新博客的id
			//String s="select b_Id from blogs order by b_Id desc limit 0,1";
			String s1="select max(b_Id) from blogs;";
			st=con.createStatement();
			
			rs=st.executeQuery(s1);
			int id=0;
            if(rs.next()){
    			 id=rs.getInt("max(b_Id)");
			}
			Document doc=Jsoup.connect(url+id).get();
			String con=doc.toString();
			//新建html文件，内容为该篇博客的doc，名为id+html
			 File file=new File("/home/ubuntu/tomcat/webapps/LongBlog/blogs/"+id+".html");
//			 File file=new File("D:/apache-tomcat-7.0.68/webapps/LongBlog/blogs/"+id+".html");
			 if (!file.exists()) {
				file.createNewFile();
				//向文件中写入文本
				Dytosta.writeStrtoFile(con, file);
			}else{//向文件中写入文本
				Dytosta.writeStrtoFile(con, file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 6.删除指定ID的博客-----------注意，博客删除后类别表也会发生变化
	 * @param id
	 */
	public void deleteBlog(String id) {
		con=JdbcUtil.getConnection();
		int iid=Integer.parseInt(id);
		try{
			st=con.createStatement();
			//查询该条博客信息
			String sql1="select * from blogs where b_Id="+iid;
			rs=st.executeQuery(sql1);//
            if(rs.next()){
            	String title=rs.getString("b_Title");//博客标题
    			String content=rs.getString("b_Content");//内容
    			String time=rs.getString("b_Time");//发布时间
    			String author=rs.getString("b_Author");//作者
    			String ipath=rs.getString("b_ImagePath");//图片路径
    			int vNum=rs.getInt("b_ViewNum");//阅读量
    			int cNum=rs.getInt("b_ComNum");//评论数量
    			int cat=rs.getInt("cat_Id");//分类id
    			String dtime=OtherUtil.time();//记录博客的删除时间
    			//将该条博客加入回收站中
    			String sql2="insert into d_blogs(b_Id,b_Title,b_Content,b_Time,b_Author,"
    					+ "b_ImagePath,b_ViewNum,b_ComNum,cat_Id,d_Time) values(?,?,?,?,?,?,?,?,?,?);";
    			ps=con.prepareStatement(sql2);
    			ps.setString(1, id);ps.setString(2, title);ps.setString(3, content);
    			ps.setString(4, time);ps.setString(5, author);ps.setString(6, ipath);
    			ps.setInt(7, vNum);ps.setInt(8, cNum);ps.setInt(9, cat);ps.setString(10, dtime);
    			ps.executeUpdate();
                System.out.println(sql2);	
            }					
			//删除博客表中记录
			String sql="delete from blogs where b_Id="+iid;
			st.executeUpdate(sql);
			st.close();
			con.close();
		}catch(Exception e){
			
		}
	}
	/**
	 * 7.修改博客
	 * 注意，如果修改了博客的类别，那么类别表中也会发生变化，这样的话，就需要记住原本博客所属类别，原本的减1，修改的加1
	 * @param b_Id-------要修改的博客id
	 * @param category---------该条博客所属类别
	 * @param blog-------------博客实体
	 */
	public boolean updateBlog(String b_Id,String category,Blogs blog){
		con=JdbcUtil.getConnection();

		try {
			//查询该分类在分类表中的类别id
			String sel="select cat_Id from category where cat_Name='"+category+"';";
			st=con.createStatement();
			rs=st.executeQuery(sel);
			int c_Id=1;
			if(rs.next()){
			    c_Id=rs.getInt("cat_Id");
			}
			String author=blog.getAuthor();
			String title=blog.getTitle();
			String content=blog.getContent();
			String image=blog.getImgPath();
			//将修改后的博客更新至表中
			String sql="update blogs set b_Title='"+title+"',b_Content='"+content+"'"
					+ ",b_Author='"+author+"',cat_Id="+c_Id+",b_ImagePath='"+image+"' where b_Id="+b_Id;
			System.out.println(sql);
			st.executeUpdate(sql);
			
			st.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 8.博文阅读量加1
	 */
	public void addNum(int id){
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="update blogs set b_ViewNum=b_ViewNum+1 where b_Id="+id+";";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 9.提供将删除的博客恢复功能
	 * 2018-05-9
	 * did为在回收站中的id
	 * @param did
	 */
	public void recoverBlog(String did){
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			int idid=Integer.parseInt(did);
			String sql="select * from d_blogs where d_Id="+idid;
			rs=st.executeQuery(sql);
			if(rs.next()){//根据指定id只查询到一条数据
				int bid=rs.getInt("b_Id");//博客删除前的id
	             String title=rs.getString("b_Title"); 
	             String content=rs.getString("b_Content");
	             String time=rs.getString("b_Time");
	             String author=rs.getString("b_Author");
	             String ipath=rs.getString("b_ImagePath");
	             int vnum=rs.getInt("b_ViewNum");
	             int cnum=rs.getInt("b_ComNum");
	             int cat=rs.getInt("cat_Id");
	             
	             //插入blogs表
	             String sql1="insert into blogs values("+bid+",'"+title+"','"+content+"','"
	             +time+"','"+author+"','"+ipath+"',"+vnum+","+cnum+","+cat+");";
	             st.executeUpdate(sql1);
	             //删除回收站中这条博客
	             String sql2="delete from d_blogs where d_Id="+idid;
	             st.executeUpdate(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 10.生成访问日志
	 * 1判断五分钟内同IP是否已访问过，若是，则不添加
	 * 2第一种需判断数据库，过于麻烦，可设置同10分钟内该代码不执行
	 * 该例中使用的是第一种，还行，没有很麻烦
	 * 网站每被访问一次，生成一条记录
	 */
	public void genLog(String ip) {
		String time=OtherUtil.time();//
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			//获取该IP最近一次访问的时间
			String sql1="select l_Time from log_vis where l_Ip='"+ip+"' order by l_Id desc limit 0,1;";
			rs=st.executeQuery(sql1);
			if(rs.next()){
			   String tim=rs.getString("l_Time");
			   //如果该记录十分钟内没有添加过，则新加一条访问记录
			   if(!tim.substring(0, 15).equals(time.substring(0, 15))){
				   String sql="insert into log_vis (l_Ip,l_Time) values('"+ip+"','"+time+"')";
				   st.executeUpdate(sql);
			   }
			   System.out.println(tim.substring(0, 15));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 11.网站的访问数量
	 */
	public int visitNum(){
		int num=0;
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select count(*) from log_vis;";
			rs=st.executeQuery(sql);
			if(rs.next()){
				 num=rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	/**
	 * 12.根据博客id获取博客名
	 * @param id
	 * @return
	 */
	public String getBlogNameById(int id) {
		String title="";
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select b_Title from blogs where b_Id="+id;
			rs=st.executeQuery(sql);
			if(rs.next()){
				title=rs.getString("b_Title");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}
}
