<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <%
         Cookie[] cookie=request.getCookies();
       String acc="";
       for(int i=0;cookie!=null&&i<cookie.length;i++){
           if(cookie[i].getName().equals("account")){//cookie中有登录过得信息，直接进入管理界面
                 acc=cookie[i].getValue().toString();
                if(!acc.equals("")){//cookie中账号不为空，说明已登录过
                   %>
                   <frameset cols="209,*" border="0">
                     <frame src="management\TaskMenu.html" noresize>
                     <frame src="/LongBlog/management/viewBlogs.jsp" noresize border="0" frameborder="0">
                   </frameset>
                   <%
      
                }
            }else if(i==cookie.length-1){//遍历到最后一个cookie仍没有，说明未登录
                    System.out.println("...............");
                    //for(int j=3;j>0;j--){
                        //Thread.sleep(1000);
                        //out.write("抱歉，你还未登录，"+j+"秒后为你跳转<br>"); 
                    //}
                    //out.write("正在为你跳转至首页");
                    //Thread.sleep(3000);
                    //response.sendRedirect("/LongBlog");
                    %>
                     <center><h1>抱歉，你还未登录，</h1>
                     <a href="/LongBlog/login.jsp?option=write">点此登录&gt;&gt;</a></center>                                                   
                    <%
             }
       }
       %>
      
  <body>
      
  </body>
</html>
