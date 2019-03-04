<%@page import="beans.Category"%>
<%@page import="dao.CategoryDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>所有分类</title>
    
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
          CategoryDao dao=new CategoryDao();
          ArrayList<Category> c=dao.queryCategories();
          out.write("<ul>");
          for(int i=0;i<c.size();i++){
              Category cate=c.get(i);
              int id=cate.getCat_Id();
              String name=cate.getCat_Name();
              int num=cate.getCat_Num();
              out.write("<li><p>"+id+"&nbsp"+name+"("+num+")"+"<a>修改</a>&nbsp<a>删除</a></p></li>");
          }
          out.write("</ul>");
       %>
  </body>
</html>
