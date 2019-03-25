<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.longbro.util.JdbcUtil"%>
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
    
    <title>赞助记录-LongBro博客</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="/sponsor/sponsor.css">
  </head>
  
  <body>
   <center>
     <font size="+4" color="gray">赞助记录</font>
     <br><br>^-^灰常感谢大家的支持与厚爱，您的每一笔赞助，无论金额大小，我都会做详细记录。<br><br>
     <div  class="table_content">
        <table cellspacing="0px" cellpadding="0px">
            <thead>
               <tr>
                  <th width="10%">时间</th>
                  <th width="10%">来源</th>
                  <th width="10%">备注</th>
                  <th width="11%">金额</th>
               </tr>
            </thead>
            
            <tbody>
              <%
                 Connection con=JdbcUtil.getConnection();
                 Statement st= con.createStatement();
                 ResultSet rs= st.executeQuery("select * from sponsor");
                 while(rs.next()){
                	 %>
              <tr>
                <td width="25%"><%=rs.getString("s_Time") %></th>
                <td width="25%"><%=rs.getString("s_Source") %></td>
                <td width="25%"><%=rs.getString("s_Remark") %></td>
                <td width="25%">¥<%=rs.getDouble("s_Amount") %></td>
			  </tr>
                	 <%
                 }
              %>
              			  
            </tbody>
            
        </table>
     </div>
     <br>温馨提示： 本站被赞助的每一笔都会被用于<font color="red">服务器</font>和<font color="red">域名</font>的购买及续费。 <br><br>
     赞助时请尽量留下备注，如果当时忘记加备注，可在本博客<a href="http://www.longqcloud.cn/leaveword.jsp">留言板</a>中说明<br><br>
     此赞助记录不定期手动更新，可能有延迟。如您曾进行过赞助却未在记录中，可在<a href="http://www.longqcloud.cn/leaveword.jsp">留言板</a>中说明<br><br>
 
  <img alt="ALiPay" title="支付宝支付二维码" src="/images/useful/paypal.jpg">&nbsp;&nbsp; <img alt="wechatPay" title="微信支付二维码" src="/images/useful/wechat.jpg"> 
    &nbsp;&nbsp; <img alt="qqpay" title="qq支付二维码" src="/images/useful/qq.jpg"> 
  <br><br><br><font size="+2" color="green">『感恩的心 · 感谢有你』</font>
  <hr width="90%"><br>
  Copyright © 2018 LongBro.All Rights Reserved
  <br><a href="http://www.longqcloud.cn">LongBro博客</a>&nbsp;版权所有
  </center>  
  
  </body>
</html>
