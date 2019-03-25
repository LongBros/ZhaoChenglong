<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>留言板-Long Bro博客留言板</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="留言板,Long Bro博客,keyword3">
	<meta http-equiv="description" content="欢迎各位的神留言">
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
        .changyan{
            margin-left: 100px;
            margin-right: 100px;
            margin-top: 20px;
        }
    </style>
  </head>
  
  <body>
      <%@include file="header.jsp" %>
      
      <!--PC和WAP自适应版-->
<div id="SOHUCS" class="changyan"></div> 
<script type="text/javascript"> 
(function(){ 
var appid = 'cytGbs8d7'; 
var conf = 'prod_5a8242468d403fc3b1f445bfcc3b362b'; 
var width = window.innerWidth || document.documentElement.clientWidth; 
if (width < 960) { 
    window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>'); 
}else { 
   var loadJs=function(d,a)
   {
      var c=document.getElementsByTagName("head")[0]||document.head||document.documentElement;
      var b=document.createElement("script");
      b.setAttribute("type","text/javascript");
      b.setAttribute("charset","UTF-8");
      b.setAttribute("src",d);
      if(
         typeof a==="function"){
         if(window.attachEvent){
             b.onreadystatechange=function(){
               var e=b.readyState;if(e==="loaded"||e==="complete")
               {
                 b.onreadystatechange=null;a()
               }
            }
         }
         else{
           b.onload=a
         }
       }
       c.appendChild(b)
    };
    loadJs("http://changyan.sohu.com/upload/changyan.js",function(){
    window.changyan.api.config({appid:appid,conf:conf})}); } })(); 
    </script>
      
      <%@include file="footer.jsp" %>
      
  </body>
</html>
