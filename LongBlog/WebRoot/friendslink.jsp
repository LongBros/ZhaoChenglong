<%@page import="dao.ViewConDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//增加一次受访记录，并查询页面受访次数
//增加一次受访记录
ViewConDao vDao=new ViewConDao();
vDao.addVisit(request,0);
//查询页面受访次数
int count=vDao.getVisitNum(0);
 %>
<html>
  <head>    
  	<title>友情链接-LongBro博客</title>
  	<link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	<link href="css/link.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery.js"></script>
  </head>
  
  <body>
      <%@include file="header.jsp"%>
      <div class="container">
	    <div class="con_content">
	      <div class="about_box">
	        <h2 class="nh1"><span>您现在的位置是：<a href="/" target="_blank">干货街</a>>><a href="about.jsp" target="_blank">友情链接</a></span><b>友情链接</b></h2>
	        <div class="f_box">
	          <p class="a_title">友情链接</p>
	          <p class="p_title"></p>
	            <p class="box_p"><span>发布时间：2018-12-12 15:12:42</span><span>作者：赵成龙</span><span>来源：LongBro博客</span><span>点击：<%=count%></span></p>
	          <!-- 可用于内容模板 --> 
	        </div>
	        <div class="links">
	        	<div class="title">本站其它站点：</div>
	        	<div class="link">
	                  <a href="http://www.zy52113.com" target="_blank" title="553影院">看影视</a>
					  <a href="http://www.zy52113.com/Minimusic" target="_blank" title="553Music">听音乐</a>
			 		  <a href="http://eng.zy52113.com" target="_blank" title="LongBro英语">背单词</a>
					  <a href="http://tutor.zy52113.com" target="_blank" title="鹰城家教邦">做家教</a>
					  <a href="http://erya.longqcloud.cn" target="_blank" title="超星尔雅">搜答案</a>
	        	</div>
	        	<div class="title">各大友链：</div>
	        	<div class="link">
	        		<a href="/">LongBro博客</a>
	        		<a href="http://www.gold404.cn/" target="_blank" title="K先生的博客">K先生的博客</a>
	        		<a href="http://www.yangqq.com/" target="_blank" title="杨青个人博客">杨青个人博客</a>
	        		<a href="http://www.sanshi30.cn/" target="_blank" title="Sanshi-Blog">Sanshi-Blog</a>
	        	</div>
	        	<div class="link">
	        	</div>
	        </div>
	        
	      </div>
	   </div>
 	</div>
	<!-- container代码 结束 -->
	<%@include file="footer.jsp"%>
  </body>
</html>
