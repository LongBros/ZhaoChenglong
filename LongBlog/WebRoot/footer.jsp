<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="util.JdbcUtil"%>
<%@page import="dao.BlogsDao"%>
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
    <footer>
    <div class="footer">
     <p> 友情链接<select  onchange="document.location=options[selectedIndex].value">
          <option></option>
          <option value="http://www.gold404.cn/">王勃博客</option>
          <option value="http://www.duanliang920.com/">段亮博客</option>
          <option value="https://mkblog.cn/">孟坤博客</option>
          <option value="http://www.yangqq.com/">杨青个人博客</option>
      </select></p>
      <div class="f_l">
        <p>All Rights Reserved 版权所有：<a href="http://www.longqcloud.cn/EryaExam" target="_blank">尔雅助手</a> 备案号：豫ICP备16023798号-1</p>
      </div>
      <div class="f_r textr">
        <p>Design by Long Bro</p>
        <%
         BlogsDao d=new BlogsDao();
         int n=d.visitNum();
         %>
         <br><h6 align="center">网站访问量共<%=n %>人次</h6>
      </div>
    </div>
  </footer>
  </body>
</html>
