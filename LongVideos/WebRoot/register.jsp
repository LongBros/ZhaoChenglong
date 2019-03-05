<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>会员注册-LongBro影院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/login.css">
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
  </head>
  <script type="text/javascript">
     function change(img) {
		img.src=img.src+"?"+new Date().getTime();
	 }
     //此注册功能之所以不用ajax是因为传的值中如用户名，问题，答案可能含有密码，传的时候需编码再解码
     //故不用ajax或js实现，直接用form的action向注册Servlet传值
     //若使用js，好处是可直接在js中判断输入内容的准确性，注册Servlet不用判断，但究其利弊，不用js了
     //登录界面使用js就可以了
     function register(){
    	 //表格名可能不能定义为和jsp文件名一样，比如使用register无效果，因其被document调用
    	 var name=document.register1.name.value;
    	 name=encodeURI(encodeURI(name));
    	 var pass1=document.register1.pass1.value;
    	 var pass2=document.register1.pass2.value;
    	 var question=document.register1.question.value;
    	 question=encodeURI(encodeURI(question));
    	 var answer=document.register1.answer.value;
    	 answer=encodeURI(encodeURI(answer));
    	 var code=document.register1.code.value;
		 //alert(code);
		 if(pass2==""){
			 alert("...");
		 }
     }
  </script>
  <body>
      <div class="wrap">
      <font size="+5"><a href="http://www.zy52113.com">LongBro影院</a></font>
        <div class="container">
            <!-- <h1>Welcome</h1> -->
            <form action="/Register" method="post">
                <input type="text" class="other" name="name" placeholder="用户名"/>
                <input type="password" class="other" name="pass1" placeholder="密码"/>
                <input type="password" class="other" name="pass2" placeholder="确认密码"/>
                <select class="other" name="question">
               		<option value="">选择找回密码的问题</option>
                	<option>1.你的手机号是？</option>
                	<option>2.你的QQ号是？</option>
                	<option>3.你的弟弟（妹妹）的名字是？</option>
                	<option>4.你最喜欢的球类运动是？</option>
                </select>
                <input type="text" class="other" name="answer" placeholder="答案-找回密码使用">
                <center><input type="text" class="yan" name="code" placeholder="验证码"/>
                <img alt="Image Matching Code" style="vertical-align: middle;" src="/Response" onclick="change(this)">
                </center>
                <input type="submit" class="other" value="注册"/>
            </form>
        </div>
        <center><font>已有账号？去</font><a href="/login.jsp">登录</a></center>
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
