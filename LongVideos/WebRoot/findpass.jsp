<%@page import="dao.UtilDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>找回密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/login.css">	

  </head>
  <style type="text/css">
	   font{
	      margin-left:40px;
	      font-family: 'Arizonia', cursive;text-shadow:#fff 1px 1px 1px;;
	   }
	   font  a{
	       color:white;
	       -webkit-transition: all 0.5s; 
	       -moz-transition: all 0.5s; 
	       -o-transition: all 0.5s;
	       text-decoration: none;
	   }
       font  a:hover{ padding-left:20px}
	</style>
  <body>
  <div class="wrap">
      <font size="+5"><a href="http://www.zy52113.com">553影院</a></font>
        <div class="container">
	  	<script type="text/javascript">
		  	var xmlHttp=false;
			if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
		   		xmlHttp=new XMLHttpRequest();
		   	}else if(window.ActiveXObject){//QQ浏览器（IE)
		       	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		   	}
		  	function getQues(){//用户输入用户名后自动加载出注册时设置的问题
		  		var user=document.form.user.value;
			  	var url="getques.jsp?user="+user;	
			  	xmlHttp.open("get", url, true);
	  			xmlHttp.onreadystatechange= function(){
	  				if(xmlHttp.readyState==4){
	  					//将异步获取的问题赋值至问题文本框
	  					/* document.form.ques.value=xmlHttp.responseText; */
	  					document.form.qq.placeholder=xmlHttp.responseText; 
	  				}
	  			}
	  			if(user==""){
	  				document.form.qq.placeholder="请先输入用户名";
	  			}else{
	  				xmlHttp.send();
	  			}
	  			
		  	}	
		  	function findpass(){
	  			var user=document.form.user.value;
	  			var qq=document.form.qq.value;
	  			qq=encodeURI(encodeURI(qq));
	  			//若考虑中文问题，此处应编码
	  			//1.借用Servlet，传值找回
	  			var url="GetPass?user="+user+"&qq="+qq;
	  			xmlHttp.open("get", url, true);
	  			xmlHttp.onreadystatechange= function(){
	  				if(xmlHttp.readyState==4){
	  					window.alert(xmlHttp.responseText);//弹出得到的内容，即密码或错误信息
	  				}
	  			}
	  			xmlHttp.send();
	  			//2.在js中调用Java方法，传入用户名和QQ参数，返回密码或提示信息
	  			//如何向Java方法中传入js的值参数？可通过cookie，先将js中变量值存入cookie
	  			//再用Java代码取出cookie传入参数，但略显繁琐
	  			//<%
	  			//String pass=UtilDao.getPass(user, qq);
	  			//%>
	  			//window.alert();
		  	}
	  	</script>
	  	<center>
	  	  <form name="form">
	  	  	  <h1>找回密码</h1><h3>Find Your Password!</h3>
	  	  		<br><br>
		  	  <input type="text" class="other" onmouseout="getQues()" name="user" placeholder="用户名">
<!-- 		  	  <input type="text" class="other" name="ques" readonly="readonly">
 -->		  	  
		      <input type="text" class="other" name="qq" placeholder="请先输入用户名">
		      
		      <input type="button" class="other" onclick="findpass()" value="找回">
		      	*若注册时QQ填写错误，你也可联系QQ1459892910找回密码
	  	  </form>
	    </center> 
   		</div>
    
    	<ul>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul> 
    </div>
  </body>
</html>
