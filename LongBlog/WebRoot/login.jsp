<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String option=request.getParameter("option");//登录后的操作入口
System.out.println(option);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro博客-登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
     <style type="text/css">
        .tip{
           margin-top: 150px;
        }
     </style>
  </head>
  
  <body background="/images/yuanshan.jpg">
      <!-- 先判断cookie中有无登录记录，若有，直接登录；反之，加载出表单输入账户密码后登陆 -->
  <center>
    <div class="tip">
    
    <%
        if(option.equals("write")){
           out.write("!登陆后方可写博客");
        }else if(option.equals("manage")){
           out.write("!只有管理员才可管理博客，请先登录");
        }else if(option.equals("gallery")){
           out.write("!只有管理员才可查看图库，请先登录");
        }
     %> 
   </div>
     
  <br>
    <%
       Cookie[] cookie=request.getCookies();
       String acc="";
       for(int i=0;cookie!=null&&i<cookie.length;i++){
           if(cookie[i].getName().equals("account")){//cookie中有登录过得信息，直接进入管理界面
                 acc=cookie[i].getValue().toString();
                if(!acc.equals("")){//cookie中账号不为空，说明已登录过
                     if(option.equals("write")){//点击写博客进入的登录界面，登录后返回写博客界面
                         response.sendRedirect("/LongBlog/writeBlog.jsp");
                     }else if(option.equals("manage")){//点击后台管理进入的登录界面，登陆后进入管理界面
                         response.sendRedirect("/LongBlog/management/manage.jsp");
                     }else if(option.equals("gallery")){//点击后台管理进入的登录界面，登陆后进入管理界面
                         response.sendRedirect("/LongBlog/listpic.jsp");
                     }
                }
           }else if(i==(cookie.length-1)&&acc.equals("")){//cookie中无登录信息，进行登录验证
                %>
                 <form action="/LongBlog/Login?option=<%=option %>" method="post">
                                                                   管&nbsp;&nbsp;理:<input type="text" name="account"><br>
                                                                   密&nbsp;&nbsp;码:<input type="password" name="password"><br>
                   <input type="submit" value="登录">
                 </form>
               <%
               }
          
       }
     %>
    </center>
  </body>
</html>
