<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.longbro.util.JdbcUtil"%>
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
       <center><p>All Rights Reserved 版权所有：<a href="http://www.zy52113.com" target="_blank">LongBro影院</a> 备案号：<a href="http://www.miitbeian.gov.cn" target="_blank">豫ICP备16023798号-1 </a></p> <center>
       <center> <p>Designed by Long Bro
        <%
         BlogsDao d=new BlogsDao();
         int n=d.visitNum();
         %>
                      网站访问量共<font color="black" size="+0"><%=n %></font>人次</center>

    </div>
    <!-- <script type="text/javascript">
(function(){
var p = {
url:location.href,
to:'qqmail',
desc:'来LongBro博客，和我一起看有质量的技术文章。', /*默认分享理由(可选)*/
summary:'这里是LongBro博客',/*摘要(可选)*/
title:'Long Bro博客',/*分享标题(可选)*/
site:'www.longqcloud.cn',/*分享来源 如：腾讯网(可选)*/
pics:'http://www.longqcloud.cn/images/useful/logo2.png' /*分享图片的路径(可选)*/
};
var s = [];
for(var i in p){
s.push(i + '=' + encodeURIComponent(p[i]||''));
}
document.write(["<a target='_blank' ", 'href="http://mail.qq.com/cgi-bin/qm_share?', s.join("&"), '"', ' style="cursor:pointer;text-decoration:none;outline:none"><img src="http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mail_01_02.png"/></a>'].join(""));
})();
</script> -->

<a target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=MgMGBwsKCwALAwJyQ0McUV1f" style="text-decoration:none;"><img src="http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_11.png"/></a>
  </footer>
  </body>
</html>
