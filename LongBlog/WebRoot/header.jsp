<%@page import="beans.Sentence"%>
<%@page import="util.OtherUtil"%>
<%@page import="beans.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page import="util.JdbcUtil"%>
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
      <div class="logo f_l">Long Bro的博客</div>
      <div class="search f_r">
        <form action="searchblog.jsp" method="post" name="searchform" id="searchform">
          <input name="keyboard" id="keyboard" class="input_text" value="请输入关键字" style="color: rgb(153, 153, 153);" onfocus="if(value=='请输入关键字'){this.style.color='#000';value=''}" onblur="if(value==''){this.style.color='#999';value='请输入关键字'}" type="text">
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
                  <li><a href="login.jsp?option=write">写写写</a></li>
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
                int num=category.getCat_Num();
                out.write("<li><a href='cat_search.jsp?id="+id+"'>"+name+"</a></li> ");//("+num+")
              }
            
         %>
              
              </ul>
            </li>
            <li><a href="login.jsp?option=manage">后台管理</a> 
                
            </li>
            <li><a href="msgboard.jsp">每日一句</a>
                <ul>
                   <li><a href="msgboard.jsp">每日一句</a></li>
                   <li><a href="leaveword.jsp">留言板</a></li>
                </ul> 
            </li>
            <li><a href="#">关于我们</a>
              <ul>
                <li><a href="about.jsp">简介</a></li>
                <li><a href="login.jsp?option=gallery">相册</a></li>
                <li><a href="history.jsp">建博史</a></li>
              </ul>
            </li>
            <li><a href="share.jsp">干货街</a>
               
            </li>
          </ul>
        </div>
        <%OtherUtil util=new OtherUtil();
          Sentence sen=util.genSen();
        %>
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
