<%@page import="dao.SentenceDao"%>
<%@page import="beans.Sentence"%>
<%@page import="com.longbro.util.OtherUtil"%>
<%@page import="beans.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
  </head>
  <body>
     <header>
    <div class="headtop"></div>
    <div class="contenttop">
      <div class="logo f_l">Long Bro博客</div>
     
      <div class="search f_r">
        <form action="searchblog.jsp" method="post" target="_blank" name="searchform" id="searchform">
          <input name="keyword" id="keyboard" class="input_text" value="请输入关键字" style="color: rgb(153, 153, 153);" onfocus="if(value=='请输入关键字'){this.style.color='#000';value=''}" onblur="if(value==''){this.style.color='#999';value='请输入关键字'}" type="text">
          <input name="show" value="title" type="hidden">
          <input name="tempid" value="1" type="hidden">
          <input name="tbname" value="news" type="hidden">
          <input name="Submit" class="input_submit" value="搜索" type="submit">
        </form>
      </div>
      <div class="blank"></div>
      <nav>
        <div  class="navigation">
          <ul class="menu">
            <li><a href="index.jsp">网站首页</a>
               <ul>
                  <li><a href="login.jsp?option=write" target="_blank">写写写</a></li>
                  <li><a href="/sponsor/showSponsor.jsp" target="_blank">赞助记录</a></li>
				  <li><a href="http://blog.zy52113.com/" target="_blank" title="新版博客">新版博客</a></li>
               </ul>
            </li>
             
            <li><a href="#">我的作品</a>
              <ul>
              <%
              CategoryDao dao2=new CategoryDao();
              ArrayList<Category> arr2= dao2.queryCategories();
              for(int i=0;i<arr2.size();i++){
                  Category category=arr2.get(i);
                  int id=category.getCat_Id();
                String name=category.getCat_Name();
                out.write("<li><a href='cat_search.jsp?id="+id+"'>"+name+"</a></li> ");//("+num+")
              }
         %>
              </ul>
            </li>
            <li><a href="login.jsp?option=manage">后台管理</a> 
            </li>
            <li><a href="msgboard.jsp" target="_blank">留言板</a>
                <ul>
                   <li><a href="msgboard.jsp" target="_blank">每日一句</a></li>
                   <li><a href="http://www.zy52113.com/toast.jsp" target="_blank">留言板</a></li>
                </ul> 
            </li>
            <li><a href="https://blog.csdn.net/qq_35750547"  target="_blank">关于我</a>
              <ul>
                <li><a href="about.jsp">简介</a></li>
                <li><a href="login.jsp?option=gallery"  target="_blank" >相册</a></li>
                <li><a href="history.jsp">建博史</a></li>
		<li><a href="https://blog.csdn.net/qq_35750547" target="_blank" title="我的CSDN">我的CSDN</a></li>
              </ul>
            </li>
           <li><a href="share.jsp">干货街</a>
               <ul>
                  <li><a href="friendslink.jsp" target="_blank" title="友情链接">友情链接</a></li>
				  <li><a href="sponsor/sshare.jsp" target="_blank" title="有偿干货">有偿干货</a></li>
		 		  <li><a href="share.jsp" target="_blank" title="无偿干货">无偿分享</a></li>
              </ul>
            </li>
          </ul>
        </div>
        <%
          OtherUtil util=new OtherUtil();
          Sentence sen=util.genSen();
          SentenceDao sdao=new SentenceDao();
          sdao.insertToast(sen.getS_Chinese());
        %>
        <!-- <span>曾经有一份真挚的爱情摆在我面前，我却没有太珍惜。等到失去以后，我才追悔莫及。如果上天能够再给我一次机会，
      	 我一定会好好珍惜，不会再辜负她，我一定会让她重拾对爱情的信心。然而，上天已经收回了这份天赐的爱情，机会不是我
      	 想有就会有的。赵盈，我亏欠你一辈子！</span> -->
        <em style="color: blue;float: right;" title="<%=sen.getS_Chinese()%>"><%=sen.getS_English()%></em>&nbsp;&nbsp;&nbsp;
        <tt style="float: right;color:gray;">每日一句:</tt>
      </nav>
      <SCRIPT type=text/javascript>
	// Navigation Menu
	$(function() {
		$(".menu ul").css({display: "none"}); // Opera Fix
		$(".menu li").hover(function(){
			$(this).find('ul:first').css({visibility: "visible",display: "none"}).slideDown("normal");
		},function(){//光标移走后隐藏
			$(this).find('ul:first').css({visibility: "hidden"});
		});
	});
</SCRIPT> 
    </div>
  </header>
  </body>
</html>
