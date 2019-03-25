<%@page import="dao.UtilDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 	<%
 		String user=request.getParameter("user");
 		String sql="select * from user where nickname='"+user+"'";
 		String ques=UtilDao.getQues(sql);//根据用户名得到注册前设置的问题
 	%>
 	<%=ques%>
  
