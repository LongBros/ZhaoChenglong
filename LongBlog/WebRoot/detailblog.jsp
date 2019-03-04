<%@page import="util.AddressUtils"%>
<%@page import="beans.Blogs"%>
<%@page import="beans.Comment"%>
<%@page import="dao.CommentDao"%>
<%@page import="util.OtherUtil"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="util.JdbcUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Detail Blog</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="css/my.css">
	

  </head>
  
  <body>
    <div class="topnav">
        <a href="http://www.longqcloud.cn" target="_blank">Long Bro博客</a>——查看其它<a href="http://www.longqcloud.cn/LongVideos" target="_blank">Long Bro影院</a>
    </div>
    <%@include file="header.jsp" %> 
   
    <div class="detailblog">
    <%
       String id=request.getParameter("id");//获取要查看的博客的id号
		Connection con=JdbcUtil.getConnection();
		try {
			Statement st=con.createStatement();
			//查看博客全文执行三次SQL事件1.将该博客的阅读量加1；2在阅读情况表中增加一条记录；3.查询该条博客的信息并显示
			//1.将博客表中该博客的阅读量加1
			String update="update blogs set b_ViewNum=b_ViewNum+1 where b_Id="+id+";";
			st.executeUpdate(update);
			//System.out.println(update);
			//2在阅读情况表中增加一条记录
			String insert="insert into viewcon(b_Id,v_Time,v_Ip) values(?,?,?)";
			
			String l_Ip=OtherUtil.getIp(request);
						
			PreparedStatement ps=con.prepareStatement(insert);
			ps.setInt(1, Integer.parseInt(id));
			ps.setString(2, OtherUtil.time());
			ps.setString(3, l_Ip);
			ps.executeUpdate();
			
			//3.查询博客详情并显示
			String sql="select * from blogs  where b_Id="+id;
			BlogsDao bDao=new BlogsDao();
			ArrayList<Blogs> blogs=bDao.queryBlogs(sql);//只有一条博客
			Blogs blog=blogs.get(0);
			String title=blog.getTitle();
		    String content=blog.getContent();
			String tim=blog.getTime();
			String author=blog.getAuthor();
			int viewNum=blog.getViewNum();
			int cat_Id=blog.getCat_Id();   
			//根据类别id查询类别名
			CategoryDao dao=new CategoryDao();
			String cat_Name=dao.queryCatNameById(cat_Id);
			%>
			<font face="Arial" size="+3" color="#008000"><center><%=title%></center></font></br>
			<p  align="center"><font size="-1">&emsp;作者:<%=author%>&nbsp;发布时间:<%=tim %>&nbsp;所属类别:<%=cat_Name %>&nbsp;浏览量:<%=viewNum %></font></p></br></br>
			<font size="+1"  color="#000000">&nbsp;<%=content %></font>
			<br><hr width="95%"><br><br>
			
			<center>如果你觉得这篇文章对你有用，欢迎<b><a title="Long Bro这小子向你表示感谢">打赏</a></b>，<br><br>
			打赏多少，你高兴就行，谢谢你对Long Bro这小子的支持 &nbsp;^-^<br><br>
			  <div class="pic"><img alt="paypal" src="/LongBlog/images/useful/paypal.png"></div>
			  <div class="pic"><img alt="wechat" src="/LongBlog/images/useful/wechat.png"></div>
			</center>
			
			<%
				

			out.write("<form action='/LongBlog/Comment?b_Id="+id+"&c_Ip="+l_Ip+"' method='post'>");
			out.write("<textarea rows='5' cols='90' name='content' placeholder='一言不合就吐槽' ></textarea>");
			out.write("<input type='submit' value='评论' align='right'>");
			out.write("</form>");
			
			String sel="select * from comment where b_Id="+id;
			CommentDao comDao=new CommentDao();
			ArrayList<Comment> coms= comDao.queryCom(sel);
			for(Comment com:coms){
			    String time=com.getTime();
			    String ip=com.getIp();
			    String add=AddressUtils.getAddByIp(ip);
			    
			    String cont=com.getContent();			    
			    String img="images/aodamiao/"+com.getPath()+".gif";
				out.write("<p><img src='"+img+"' width='25px' height='25px'><nobr>来自:<add title='"+ip+"'>"+add+"</add>的评论"+"<br><br>"+cont+"<br><br>"+time+"</p>");
				out.write("<HR align='LEFT' size='0.01' width='100%' color='#ffffFF' noshade>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
     %>
  </div>
    
   <%@include file="footer.jsp" %>
</html>
