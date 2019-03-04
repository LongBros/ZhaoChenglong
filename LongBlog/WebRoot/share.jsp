<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>干货街</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/main.css">

  </head>
  
  <body>
  <%@include file="header.jsp" %>
    <div class="container">
     <div class="con_content">
       <div class="about_box">
         <h2 class="nh1"><span>您现在的位置是：<a href="/LongBlog/share.jsp">干货街</a>>><a href="/LongBlog/share.jsp" target="_blank">干货街</a></span><b>欢迎来到干货街</b></h2>
           <br>  
          <div>
             <a href="https://pan.baidu.com/s/1fh6qdeya1qz-kumyX-YuuQ">Android超级终端</a>&nbsp;&nbsp;&nbsp;密码:9b3n
          </div>
      </div>
     </div>
   </div>
   <%@include file="footer.jsp" %>   
  </body>
</html>
