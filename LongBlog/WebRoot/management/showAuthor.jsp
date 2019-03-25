<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.longbro.util.JdbcUtil"%>
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
    
    <title>所有作者</title>
    
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
      <form action="/LongBlog/AddAuthor" method="post"> 
                                    姓名：<input type="text" name="name">
          QQ号<input type="text" name="qq">
                                   手机号<input type="text" name="phone">
                                   密码<input type="password" name="pass">
          <input type="submit" value="添加">
      </form>
      <table border="3" cellpadding="5px">
          <tr>
             <td>编辑</td>
             <td>姓名</td>
             <td>QQ号</td>
             <td>手机号</td>
             <td>密码</td>
             <td>删除</td>
          </tr>
      <%
          Connection con=JdbcUtil.getConnection();
          Statement st=con.createStatement();
          String sql="select * from author";
          ResultSet rs=st.executeQuery(sql);
          while(rs.next()){
              int id=rs.getInt("a_Id");
              String name=rs.getString("a_Name");
              String qq=rs.getString("a_QQ");
              String phone=rs.getString("a_Phone");
              String pass=rs.getString("a_Pass");
              String sex=rs.getString("a_Sex");
              %>
               <tr>
                   <td>编辑</td>
                   <td><%=name %></td>
                   <td><%=qq %>
                   <td><%=phone %></td>
                   <td><%=pass %></td>
                   <td>删除</td>
               </tr>
              <%
          }
       %>
      </table>
       
  </body>
</html>
