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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>为所有ip添加地址</title>
    
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
      		try{
      			Class.forName(SqlUtil.driver);
                Connection con=DriverManager.getConnection(SqlUtil.url, SqlUtil.user, SqlUtil.pass);
                Statement st=con.createStatement();
                String sql="select * from loginlog where l_Id=1";
                System.out.println(sql);
                ResultSet rs= st.executeQuery(sql);
                while(rs.next()){    
                    String ip=rs.getString(3);//访问ip 
                    String ip1="";
            		if(ip.contains(",")){
            			ip1=ip.substring(0, ip.indexOf(","));
            		}else{
            			ip1=ip;
            		}
            		System.out.println(ip1);
            		String add=AddressUtils.getAddByIp(ip1);//得到该ip的地址
            		
                    String adds="update loginlog set l_Address='"+add+"' where l_Ip='"+ip+"'";
                    out.println(adds+"<br>");
                    Statement st1=con.createStatement();

                    st1.executeUpdate(adds);//修改对应ip的地址
					out.println("---------------------------------------------------------------------------------------------"+"<br>");
                	//Thread.sleep(10000);
                }
      		}catch(Exception e){
      			
      		}
      %>
  </body>
</html>
