<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String href=request.getParameter("href");//视频信息链接，从这个链接里获取到播放链接
System.out.println(href);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>正在播放，，，</title>
    
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
       //http://v.youku.com/v_show/id_XMzIxMDMyMTM2OA==.html?s=5e04338e529e11e4b2ad
       //http://v.youku.com/v_show/id_XMTU4MTkwODkxMg==.html?spm=a2h1n.8251845.0.0
       //http://v.youku.com/v_show/id_XMTU4MTkwODkxMg==.html?spm=a2h0j.11185381.listitem_page1.5~A&s=5e04338e529e11e4b2ad
           Document doc=Jsoup.connect(href).get();
           Elements es=doc.getElementsByClass("drama-content");
           System.out.println(es);
        %>
       <iframe src="http://beaacc.com/api.php?url=<%=href%>" align="middle" width="800px" height="500px"></iframe>
  </body>
</html>
