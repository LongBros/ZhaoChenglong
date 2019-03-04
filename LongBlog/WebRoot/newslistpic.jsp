<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro的博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro的博客">
	<meta http-equiv="description" content="Long Bro的博客">
	<link href="css/base.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
</head>
<body>
<div id="wrapper">


     <%@include file="header.jsp" %>


<div class="container">
  <div class="con_content">
    <div class="about_box">
      <h2 class="nh1"><span>您现在的位置是：<a href="/" target="_blank">网站首页</a>>><a href="#" target="_blank">个人日记</a></span><b>个人日记</b></h2>
      <div class="dtxw box">
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷原汁原味重现经典....</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/02.jpg"></a></div>
        </li>
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷广安领先....</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/01.jpg"></a></div>
        </li>
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷原汁原味重现经典....</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/03.jpg"></a></div>
        </li>
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷原汁原味重现经典</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/04.jpg"></a></div>
        </li>
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷原汁原味重现经典</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/02.jpg"></a></div>
        </li>
        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/">个人博客模板古典系列之——江南墨卷</a></h2>
              <p>个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷个人博客模板古典系列之——江南墨卷原汁原味重现经典</p>
              <span>2017-3-10</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/06.jpg"></a></div>
        </li>
      </div>
      <div class="pagelist">页次：1/1 每页25 总数10<a href="/">首页</a><a href="/">上一页</a><a href="/">下一页</a><a href="/">尾页</a></div>
    </div>
  </div>
  <div class="blank"></div>
  <!-- container代码 结束 -->
  
  <%@include file="footer.jsp" %>
  
  
</div>
</body>
</html>