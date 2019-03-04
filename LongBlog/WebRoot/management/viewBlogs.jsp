<%@page import="beans.TimeNum"%>
<%@page import="beans.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page import="beans.Blogs"%>
<%@page import="dao.BlogsDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>所有Long Bro博客</title>
    
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
  <form action="/LongBlog/management/viewBlog.jsp" method="get" target="blogs">
  按作者<select name="author">
     <option value="all">全部</option>
     <option value="long">赵成龙</option>
     <option value="ying">赵盈</option>
  </select>
  &nbsp;&nbsp;
  按分类<select name="cate">
    <option value="all">全部</option>
  <%
    CategoryDao dao=new CategoryDao();
    ArrayList<Category> cates=dao.queryCategories();
    for(Category cate:cates){
       String name=cate.getCat_Name();
       int cid=cate.getCat_Id();
       out.write("<option value='"+cid+"'>"+name+"</option>");
       
    }
   %>
      
  </select>
  &nbsp;&nbsp;
  按日期
  <select name="date">
    <option value="all">全部</option>
    <%
      //left(b_Time,10)      left(b_Time,7)
             String what="distinct left(b_Time,7) as y_m";
             BlogsDao inDao=new BlogsDao();
             ArrayList<TimeNum> tnar=inDao.queryBlogs(what,null);
             for(int i=0;i<tnar.size();i++){
                TimeNum tn=tnar.get(i);
                String y_m=tn.getTime();
                int amo=tn.getNum();
                out.write("<option value='"+y_m+"'>"+y_m+"</option>");

             }
     %>
  </select>
  <input type="submit" value="检索">
    </form>
    <iframe src="/LongBlog/management/viewBlog.jsp?author=all&cate=all&date=all" name="blogs" width="100%" height="100%"></iframe>
      
  </body>
</html>
