<%@page import="utils.AddressUtils"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String data=request.getParameter("data");
if(data.contains("_")){//传的是数据
	String ds[]=data.split("_");
	String temp=ds[0];
	String humi=ds[1];
	String light=ds[2];
	String time=SqlUtil.time();
	
	try{
  		   Class.forName(SqlUtil.driver);
           Connection con=DriverManager.getConnection(SqlUtil.url, SqlUtil.user, SqlUtil.pass);
           Statement st=con.createStatement();
           String sql="insert into data (temp,humi,light,time) values ('"+temp+"','"+humi+"','"+light+"','"+time+"')";
           st.execute(sql);
           out.println(temp+","+humi+","+light);

      }catch(Exception e){
  			
   	  }
}else{//发的是信号
	//a-温度,b-湿度,c-光照强度
	if(data.contains("a")){
		out.print("加温度信号<br>");
	}
	if(data.contains("b")){
		out.print("加湿度信号<br>");
	}
	if(data.contains("c")){
		out.print("加光照强度信号<br>");
	}
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传数据</title>
    
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
      		
      %>
  </body>
</html>
