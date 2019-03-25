<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>"> 
    <title>个人简介-LongBro的博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro的博客,Long Bro,个人简介,关于我">
	<meta http-equiv="description" content="Long Bro,一个将要步入大四的学生。">
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
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
        <h2 class="nh1"><span>您现在的位置是：<a href="/" target="_blank">网站首页</a>>><a href="about.jsp" target="_blank">信息浏览</a></span><b>个人简介</b></h2>
        <div class="f_box">
          <p class="a_title">个人简介</p>
          <p class="p_title"></p>
          <!--  <p class="box_p"><span>发布时间：2017-07-07 15:12:42</span><span>作者：唐孝文</span><span>来源：稽查支队</span><span>点击：111056</span></p>--> 
          <!-- 可用于内容模板 --> 
        </div>
        <ul class="about_content">
          <p> 赵成龙，1995年10月16日出生于河南省南阳市，2015年9月份步入平顶山学院的学堂，就学于计算机科学与技术学院物联网工程专业，后学院名改为信息工程学院。机缘巧合下学习了Java这门神奇的语言，并对Java开发这有着炙热的爱，曾学习过Android APP开发，后转入Java web开发的学习。随着时间的流逝，对Java语言的热爱不减反增。没错，我是一名热爱搬砖的码农，我很菜，但我渴望优秀，时间会证明一切。</p>
          <p><img src="images/01.jpg"></p>
          <p>“冥冥中该来则来，无处可逃”。 </p>
        </ul>
        <!--    <div class="nextinfos">
      <p>上一篇：<a href="/">区中医医院开展志愿服务活动</a></p>
      <p>下一篇：<a href="/">广安区批准“单独两孩”500例</a></p>
    </div>--> 
        <!-- 可用于内容模板 --> 
        
        <!-- container代码 结束 --> 
      </div>
    </div>
    <div class="blank"></div>
  </div>
  <!-- container代码 结束 -->
  

  <%@include file="footer.jsp" %>
 
</div>
</body>
</html>
