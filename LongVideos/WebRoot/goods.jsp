<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>LongBro优惠商品</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="淘宝,优惠券,LongBro">
	<meta http-equiv="description" content="淘宝商品优惠券，优惠购物，就来LongBro优惠商品。">
    <link rel="shortcut icon" href="/LongVideos/images/logo2.png" type="image/x-icon"/>
	
	<link rel="stylesheet" type="text/css" href="css/goods.css">

  </head>
  
  <body>
      <%
         try{
              Class.forName(SqlUtil.driver);
              Connection con=DriverManager.getConnection(SqlUtil.url, SqlUtil.user, SqlUtil.pass);
              Statement st=con.createStatement();
              ResultSet rs= st.executeQuery("select * from women limit 0,30;");
              while(rs.next()){
                  String name=rs.getString("商品名称");
                  String img=rs.getString("商品主图");
                  Double price=rs.getDouble("商品价格(单位：元)");
                  int s_num=rs.getInt("商品月销量");
                  Double rate=rs.getDouble("收入比率(%)");
                  Double earn=rs.getDouble("佣金");
                  String s_link=rs.getString("淘宝客短链接(300天内有效)");
                  String link=rs.getString("淘宝客链接");
                  
                  String quan=rs.getString("优惠券面额");
                  String q_link=rs.getString("优惠券链接");
                  //System.out.println(img);
                  %>
                  <div class="good">
                       <a href="<%=s_link%>" target="_blank"><img alt="<%=img %>" src="<%=img%>"></a>
                       <p><a href="<%=s_link%>" target="_blank"><%=name %></a></p>
                       <span>￥<%=price %></span>&nbsp;&nbsp;&nbsp;<span><a href="<%=q_link%>"><%=quan %></a></span>
                  </div>
                  <%
              }
         }catch(Exception e){
         }
         
       %>
       <hr width="80%">
       <%@include file="footer.jsp" %>
  </body>
</html>
