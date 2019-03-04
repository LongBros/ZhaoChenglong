<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro建博史</title>
    
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
          <h2 class="nh1"><span>您现在的位置是：<a href="/LongBlog">关于我们</a>>><a href="/LongBlog/history.jsp" target="_blank">建站历史</a></span><b>建站历史</b></h2>
          <%
             Connection conn=JdbcUtil.getConnection();
             Statement st=conn.createStatement();
             String sql="select * from blogs where b_Id=24";
             ResultSet rs=st.executeQuery(sql);
             if(rs.next()){
                 String cont=rs.getString("b_Content");
                 out.write("<h1>"+cont+"</h1>");
             }
           %>
          
       </div>
     </div>
    </div>
    <%@include file="footer.jsp" %>   
    
  </body>
</html>
