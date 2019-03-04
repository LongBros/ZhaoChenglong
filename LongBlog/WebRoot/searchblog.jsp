<%@page import="beans.Blogs"%>
<%@page import="dao.BlogsDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="util.JdbcUtil"%>
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
    
    <title>search result</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet"><!-- 内应该有pageList的css -->
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
	

  </head>
  
  <body>
  <div class="topnav">
        <a href="http://www.longqcloud.cn/LongBlog" target="_blank">Long Bro博客</a>——查看其它<a href="http://www.longqcloud.cn/LongVideos" target="_blank">Long Bro影院</a>
    </div>
    <%@include file="header.jsp" %>
    
    <div class="container">
    
       <div class="bloglist f_l">
    <%
       String input=request.getParameter("keyboard");//获取要查看的博客的id号
       String require=" where b_Content like '%"+input+"%'";
       BlogsDao dao=new BlogsDao();
       ArrayList<Blogs> arr=dao.queryBlogs(require);
             CategoryDao dao1=new CategoryDao();
             
             for(int i=0;i<arr.size();i++){
                Blogs b=(Blogs)arr.get(i);
                int b_Id=b.getId(); //根据id来查看某条博客的全部内容
                 String title=b.getTitle();
                 String content=b.getContent();
                 String time=b.getTime();
                 String author=b.getAuthor();
                 String imgPath=b.getImgPath();
                 int readNum=b.getViewNum();
                 int comment=b.getComment();
                 int c_Id=b.getCat_Id();
                 
                 String cat_Name=dao1.queryCatNameById(c_Id);
                 
                 if(content.length()>88){//博客较长，只显示66个字符，后面跟......
                     content=content.substring(0,88)+"......";
                 }
                 out.print("<h3><a>"+title+"</a></h3>");
                 out.print("<figure><img  style='width:140px;height:120px;border-radius:50%;' src='"+imgPath+"' alt='"+imgPath+"' title='发布博文后，系统随机分配的图片'></figure>");
                 
                 out.print("<ul>");
                 out.print("<p>"+content+"</p>");
                 out.print("<a title=\""+title+"\" href='detailblog.jsp?id="+c_Id+"&cat_Name="+cat_Name+"' class=\"readmore\">阅读全文&gt;&gt;</a>");
                 out.print("</ul>");
                 
                 out.print(" <p   class=\"dateview\"><span class=\"spanm\">"+
                 time+"</span><span  class=\"spanm\">作者："+author+
                 "</span><span  class=\"spanm\">个人博客：[<a href='cat_search.jsp?id="+c_Id+"&cat_Name="+cat_Name+"'>"+cat_Name+"</a>]</span><span class=\"viewNum\">"
                 +readNum+"</span><span class='commentNum'>"+comment+"</span></p>");
             }          
             
     %>
    </div>
    
    </div>
    
    
    <%@include file="footer.jsp" %>
  </body>
</html>
