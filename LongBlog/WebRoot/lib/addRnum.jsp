<%@page import="beans.Blogs"%>
<%@page import="dao.BlogsDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>恭喜主人，又来访问量了-Long Bro博客</title>
    
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
          BlogsDao dao=new BlogsDao();
          dao.addNum(Integer.parseInt(id));//将博客的阅读量加1
          ArrayList<Blogs> bs= dao.queryBlogs("select * from blogs where b_Id="+id);
          Blogs b=bs.get(0);
          int num=b.getViewNum();//查询出博文的阅读量
          out.write("document.write('"+num+"');");
          
         %>         
          
  </body>
</html>
