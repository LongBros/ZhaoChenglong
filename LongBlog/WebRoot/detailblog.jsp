<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.longbro.util.AddressUtils"%>
<%@page import="beans.Blogs"%>
<%@page import="beans.Comment"%>
<%@page import="dao.CommentDao"%>
<%@page import="com.longbro.util.OtherUtil"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id1=request.getParameter("id");//获取要查看的博客的id号
int iid=Integer.parseInt(id1);//将字符串参数转化为整型博客id
Connection con=JdbcUtil.getConnection();
String title="";//博客标题
String content="";//博客内容
try{
	String sql="select * from blogs  where b_Id="+id1;
	BlogsDao bDao=new BlogsDao();
	ArrayList<Blogs> blogs=bDao.queryBlogs(sql);//只有一条博客
	Blogs blog=blogs.get(0);
	title=blog.getTitle();
	System.out.println(title);
	content=blog.getContent();
	content=content.replaceAll("<[.[^<]]*>", "");

	if(content.length()>66){
	   content=content.substring(0, 66);//截取作为文档描述
	}
}catch(Exception e){

}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=title%>-LongBro博客</title>
	<meta name="keywords" content="<%=title%>">
	<meta name="description" content="<%=content%>">
    <link rel="shortcut icon" href="/images/useful/logo2.png" type="image/x-icon"/>
	<link rel="stylesheet" href="css/editor.css">
	<link rel="stylesheet" href="css/my.css">
  </head>
  
  <body>
    <div class="topnav">
        <a href="http://www.longqcloud.cn" target="_blank">Long Bro博客</a>——查看其它<a href="http://tv.zy52113.com" target="_blank">Long Bro影院</a>
    </div>
    <%@include file="header.jsp" %> 
   
    <div class="detailblog">
    <%
		try {
			Statement st=con.createStatement();
			//查看博客全文执行三次SQL事件1.将该博客的阅读量加1；2在阅读情况表中增加一条记录；3.查询该条博客的信息并显示
			//1.将博客表中该博客的阅读量加1
			String update="update blogs set b_ViewNum=b_ViewNum+1 where b_Id="+id1+";";
			st.executeUpdate(update);

			//System.out.println(update);
			//2在阅读情况表中增加一条记录
			String insert="insert into viewcon(b_Id,v_Time,v_Ip) values(?,?,?)";
			
			String l_Ip=OtherUtil.getIp(request);
						
			PreparedStatement ps=con.prepareStatement(insert);
			ps.setInt(1, Integer.parseInt(id1));
			ps.setString(2, OtherUtil.time());
			ps.setString(3, l_Ip);
			ps.executeUpdate();

			BlogsDao bDao=new BlogsDao();
			//库中博客数量
			int bnum=bDao.queryBlogsNum("");
			int ib=0;
			int il=0;
			if(iid==1){
			    ib=bnum;//第一篇博客，上一篇设为最后一篇
			    il=iid+1;//下一篇的博客号
			}else if(iid==bnum){//最后一篇博客，下一篇设为第一篇
			    ib=iid-1;//上一篇序号
			    il=1;
			}else{
			   	ib=iid-1;//上一篇序号
			    il=iid+1;//下一篇的博客号
			}

			String sql="select * from blogs where b_Id="+id1;
			ArrayList<Blogs> blogs=bDao.queryBlogs(sql);//只有一条博客

			Blogs blog=blogs.get(0);
			title=blog.getTitle();//博客标题
		    content=blog.getContent();//内容
		    //专为图片添加的代码，使显示图片而非本身HTML的img标签
		    content=content.replace("&lt;img", "<img");
		    content=content.replace("&gt;", ">");
			
		    String tim=blog.getTime();//发布时间
			String author=blog.getAuthor();//作者
			int viewNum=blog.getViewNum();//阅读量
			int cat_Id=blog.getCat_Id();   //分类id
			//上一篇
			String bsql="select * from blogs where b_Id="+ib;
			ArrayList<Blogs> bblogs=bDao.queryBlogs(bsql);//只有一条博客
			//某些id抛IndexOutOfBoundsException,问题是有的id为空，上一篇为空的话，就查不到上一篇的博客，即为0
			//为避免此问题的出现，要么在程序中修改，要么需保证每个id都有博客存在着，先采用第二种吧
			Blogs bblog=bblogs.get(0);
			String bTitle=bblog.getTitle();//博客标题
			//下一篇
			String lsql="select * from blogs where b_Id="+il;
			ArrayList<Blogs> lblogs=bDao.queryBlogs(lsql);//只有一条博客
			Blogs lblog=lblogs.get(0);
			String lTitle=lblog.getTitle();//博客标题
			//根据类别id查询类别名
			CategoryDao dao=new CategoryDao();
			String cat_Name=dao.queryCatNameById(cat_Id);//分类
			%>
			<font face="Arial" size="+3" color="#008000"><center><%=title%></center></font></br>
			<p  align="center">
			<font size="-1">&emsp;
			作者:<%=author%>&nbsp;
			发布时间:<%=tim %>&nbsp;
			所属类别:<%=cat_Name%>&nbsp;
			浏览量:<%=viewNum%><%-- <script type="text/javascript" src="/lib/addRnum.jsp?id=<%=id1%>"></script> --%>
			</font></p></br></br>
			<font size="+1"  color="#000000">&nbsp;<%=content %></font>
			<br><hr width="99%"><br>
			<p class="otherbox""><br>本文出自LongBro博客，如需转载请注明出处。<br>
			本文链接:<a href="/blogs/<%=id1%>.html">http://www.longqcloud.cn/blogs/<%=id1%>.html</a><br>
			上一篇:<a href="/blogs/<%=ib%>.html"><%=bTitle %></a><br>
			下一篇:<a href="/blogs/<%=il%>.html"><%=lTitle %></a>
			</p>
			<br><hr width="99%"><br>
			<!-- 含显示与隐藏二维码图片 -->
   			<script type="text/javascript" src="js/detailblog.js"></script>
		   <center>如果你觉得这篇文章对你有用，欢迎
			 <font size="+1" title="Long Bro这小子向你表示感谢" onclick="showHide()" color="red">打赏</font><font color="blue"><a href="/sponsor/showSponsor.jsp">（打赏记录）</a></font><br>
			 <br>打赏多少，你高兴就行，谢谢你对Long Bro这小子的支持 &nbsp;^-^<br><br>
			  <div class="pic"><img id="paypal"  src=""></div>
			  <div class="pic"><img id="wechat"  src=""></div>
		   </center>
			
			<%
			out.write("<form action='/Comment?b_Id="+id1+"&c_Ip="+l_Ip+"' method='post'>");
			out.write("<textarea rows='5' cols='90' name='content' placeholder='一言不合就吐槽' ></textarea>");
			out.write("<input type='submit' value='评论' align='right'>");
			out.write("</form>");
			
			String sel="select * from comment where b_Id="+id1;
			CommentDao comDao=new CommentDao();
			ArrayList<Comment> coms= comDao.queryCom(sel);
			for(Comment com:coms){
			    String time=com.getTime();
			    String ip=com.getIp();
			    String add=com.getAdd();
			    String cont=com.getContent();			    
			    String img="images/aodamiao/"+com.getPath()+".gif";
				%>
				<p><img src='<%=img %>' width='25px' height='25px'>
				<nobr> 来自:<add title='<%=ip%>'><%=ip %></add>的评论 <br><br>
				<%=cont %><br><br><%=time %></p>
<!-- 				<b onclick='showResponse()'>回复</b><textarea rows="-1" cols="-1" id="response"></textarea>
 -->				<HR  align='LEFT' size='0.01' width='100%' color='#ffffFF' noshade>
				<%				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  %>
  </div>

   <%@include file="footer.jsp" %>
   </body>
   
</html>
