<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
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
    
    <title>回收站</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
      <%
         //
         Connection conn=null;
         Statement st=null;
         ResultSet rs=null;
         conn=JdbcUtil.getConnection();
         st=conn.createStatement();
         String sql="select * from d_blogs";
         rs=st.executeQuery(sql);
         %>
         <table border="1" width="1000px" align="left">
             <tr>
                 <td>d_Id</td>
                 <td>b_Id</td>
                 <td>标题</td>
                 <td>内容</td>
                 <td>发布时间</td>
                 <td>作者
                 <td>图片路径
                 <td>阅读量
                 <td>评论数
                 <td>分类id
                 <td>删除时间
                 <td>恢复
             </tr>
         
         <%
         while(rs.next()){
             int dId=rs.getInt("d_Id");//回收站博客id
             int bid=rs.getInt("b_Id");//博客删除前的id
             String title=rs.getString("b_Title");
             if(title.length()>6){
                 title=title.substring(0,6)+"...";
             }
             String content=rs.getString("b_Content");
             if(content.length()>9){
                 content=content.substring(0,8)+"...";
             }
             String time=rs.getString("b_Time").substring(0,10);
             String author=rs.getString("b_Author");
             String ipath=rs.getString("b_ImagePath");
             int vnum=rs.getInt("b_ViewNum");
             int cnum=rs.getInt("b_ComNum");
             int cat=rs.getInt("cat_Id");
             String dtime=rs.getString("d_Time").substring(0,10);//删除时的时间
             %>
             <tr>
                 <td><%=dId %></td>
                 <td><%=bid %></td>
                 <td><%=title %></td>
                 <td><%=content %></td>
                 <td><%=time %></td>
                 <td><%=author %>
                 <td><%=ipath %>
                 <td><%=vnum %>
                 <td><%=cnum %>
                 <td><%=cat %>
                 <td><%=dtime %>
                 <td><a href="/LongBlog/RecoverBlog?did=<%=dId%>">恢复</a>
             </tr>
             <%
             
         }
       out.write("</table>");
         
       %>
  </body>
</html>
