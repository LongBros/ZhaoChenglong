<%@page import="dao.BlogsDao"%>
<%@page import="beans.ViewCon"%>
<%@page import="dao.ViewConDao"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文章阅读日志记录</title>
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
  	<script type="text/javascript">
  		
  		function tab(){//页码
  			
  		}
  	</script>
  	<table border="2px">
          <tr>
             <td>阅读id</td>
             <td>被阅读博客的id</td>
             <td>被阅读博客名</td>
             <td>阅读者ip</td>
             <td>阅读者地址</td>
             <td>阅读时间</td>
          </tr>
      <%
          String sql="";
          sql="select * from viewcon order by v_Id desc limit 0,300";
          ViewConDao vDao=new ViewConDao();
          BlogsDao bDao=new BlogsDao();
          ArrayList<ViewCon> views=vDao.queryView(sql);
          for(ViewCon view:views){
             int id=view.getV_Id();//阅读id
             int bid=view.getB_Id();//被阅读博客的id
             String name=bDao.getBlogNameById(bid);//被阅读博客名
             String ip=view.getV_Ip();
             String add="";
             String time=view.getV_Time();//可以用getString方法得到DateTime的值
             %>
             <tr>
                <td><%=id %></td>
                <td><%=bid %></td>
                <td><%=name %></td>
                <td><%=ip %></td>
                <td><%=add %></td>
                <td><%=time %></td>
             </tr>
             <%
            }
      %>
      </table>
  </body>
</html>
