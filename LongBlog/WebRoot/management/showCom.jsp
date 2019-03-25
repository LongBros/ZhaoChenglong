<%@page import="java.sql.ResultSet"%>
<%@page import="com.longbro.util.OtherUtil"%>
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
    
    <title>评论管理</title>
    
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
      最新评论
      <table border="1px">
         <tr>
            <td>编号</td>
            <td>评论id</td>
            <td>博客id</td>
            <td>内容</td>
            <td>时间</td>
            <td>ip</td>
            <td>图像路径</td>
         </tr>
         
         <%
          try{
             Connection con=JdbcUtil.getConnection();
          Statement st=con.createStatement();
          String t=OtherUtil.time().substring(0,10);//今天的日期
          System.out.println(t);
          
          //今日的访问日志---注意where子句和order by子句的先后顺序
          String sql="select * from comment order by c_Id desc ;";
          ResultSet rs=st.executeQuery(sql);
          int i=1;
          while(rs.next()){
             int id=rs.getInt("c_Id"); 
             int bid=rs.getInt("b_Id");
             String content=rs.getString("c_Content");
             String cont;
             if(content.length()>10){
                  cont=content.substring(0,10)+"..."; 
             }else{
                cont=content;
             }
             String ip=rs.getString("c_Ip");
             String time=rs.getString("c_Time");
             String ipath=rs.getString("c_ImgPath");
             //System.out.println(ip+add);
             %>
             <tr>
                <td><%=i %></td>
                <td><%=id %></td>
                <td><%=bid %></td>
                <td title="<%=content%>"><%=cont %></td>
                <td><%=time %></td>
                <td><%=ip %></td>
                <td><%=ipath %></td>
             </tr>
             <%
             i++;
            }
            rs.close();
            st.close();
            con.close();
          }catch(Exception e){
             
          }
          
         
       %>
         
      </table>
  </body>
</html>
