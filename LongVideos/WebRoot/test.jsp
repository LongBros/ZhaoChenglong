<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>平院</title>
    
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
<form name="form1" method="post" id="form1" target="_blank"> 
 <table border="0"> 
  <tbody>
   <tr> 
    <td colspan="2"><h2>数字化平台</h2></td> 
   </tr> 
   <tr> 
    <td class="fl">账号：<input class="k" type="text" name="Tzh" id="Tzh"></td> 
   </tr> 
   <tr> 
    <td class="fl"><a href="http://szhpt.pdsu.edu.cn/stu_get_mm.aspx" target="_blank">密码：</a><input class="k" type="password" name="Tmm" id="Tmm"></td> 
    <td class="fr" rowspan="2" style="margin-top:-30px;"><input class="dlan" type="image" src="images/dl2-1.png" width="64" height="56" onclick="tijiao()"></td> 
   </tr> 
   <input type="hidden" name="Bdl" id="Bdl" value="登录"> 
   <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwULLTExNTc2NTI3OTlkZFizXxmxM2I3lLeYmJvVeuCfnIjc"> 
   <input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWBAKpmZSiCAKeq5+XAgKlueiABQL8z67rChPBkREEBLHd8IJxbYGJ3itgkLlZ"> 
  </tbody>
 </table> 
</form> 

<script type="text/javascript">
 function tijiao(){
alert(document.getElementById("Tzh").value);
alert(document.getElementById("Tmm").value);

document.getElementById("Tzh").innerText="151360247";//设置id为Tzh的输入框的值
document.getElementById("Tmm").innerText="145989";//
document.title="平顶山学院";//相当于head里的title
document.bgColor="#eeffff";
var s=document.getElementById("form1").action="http://szhpt.pdsu.edu.cn/login_net1.aspx";

//document.getElementById("form1").action="http://yyxt.pdsu.edu.cn/cet/welcome.aspx";
//var s=document.getElementById("form1").action="http://yyxt.pdsu.edu.cn/cet/welcome.aspx";
//alert(document.getElementById("form1").action="http://yyxt.pdsu.edu.cn/cet/welcome.aspx")
//document.getElementById("form1").action="http://szhpt.pdsu.edu.cn/jg/default_js.aspx";
//var url=document.location.href;
//alert(url);
document.getElementById("form1").submit();
//alert(document.cookie);
if(document.cookie&&document.cookie!=""){
   alert("不为空");
   var arr=document.cookie.split(';');
   for(var i=0;i<arr.length;i++){
      var arr2=arr[i].spilt("=");
      window.alert(arr2[0]+arr2[1]);
   }
}else{
   //alert("为空");
 }

}
</script>
</body>
</html>
