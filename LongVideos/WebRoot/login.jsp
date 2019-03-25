<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>会员登录-LongBro影院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro影院,好看的都在这里,免广告看视频就来Long Bro影院">
	<meta http-equiv="description" content="This is my page">
    <link rel="shortcut icon" href="/LongVideos/images/logo2.png" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="css/login.css">	
	<link rel="stylesheet" type="text/css" href="css/layui.css">
	
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
	<script type="text/javascript">
	   function change(img) {
		    img.src=img.src+"?"+new Date().getTime();
	   }
	   
	</script>
  <body>
      <div class="wrap">
      <font size="+5"><a href="http://www.zy52113.com">553影院</a></font>
        <div class="container">
            <h1>Welcome</h1>
            <script type="text/javascript">
	  		function login(){
	 		   var name=document.logins.name.value;//用户名
	 		   name=encodeURI(encodeURI(name));
	 		   var pass=document.logins.pass.value;//密码
	 		   pass=encodeURI(encodeURI(pass));
	 	  	   var code=document.logins.code.value;//验证码
	 	       code=encodeURI(encodeURI(code));
	 	  	   if(name.length<4||name.length>10){
	 	  		   alert("英文用户名，且长度大于3小于10");
	 	  	   }else if(pass.length<4||pass.length>15){
	 	  		   alert("密码长度应大于4且小于15");
	 	  	   }else if(code.length!=4){
	 	  		   alert("验证码错误");
	 	  	   }else{
	 	  		   window.location="Login?name="+name+"&pass="+pass+"&code="+code;
	 	  	   }
	 	    } 
  			</script>
            <form name="logins">
                <input type="text" class="other"  name="name" placeholder="用户名"/>
                <input type="password" class="other" name="pass" placeholder="密码"/>
                <center><input type="text" class="yan" name="code" placeholder="验证码"/>
                <img alt="" style="vertical-align:middle" src="/Response" onclick="change(this)">
                </center>
                <input type="button" class="other" onclick="login()" value="登录"/>
            </form>
        </div>
        <center><font>未注册账号？去</font><a href="/register.jsp">注册</a></center>
        <center><font>忘记密码？去</font><a href="/findpass.jsp">找回</a>  
        <div id="layerDemo" style="margin-bottom: 0;">
        	<button data-method="notice" class="layui-btn"></button>
        </div>
        </center>
        <script src="layui.js" charset="utf-8"></script>
        <script>
        	  layui.use('layer', function(){
        	  var $ = layui.jquery, layer = layui.layer;
        	  //触发事件
	          var active = {
				notice: function(){
	            //示范一个公告层
	            layer.open({
	              type: 1
	              ,title: false //不显示标题栏
	              ,closeBtn: false
	              ,area: '300px;'
	              ,shade: 0.8
	              ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	              ,btn: ['火速围观', '残忍拒绝']
	              ,btnAlign: 'c'
	              ,moveType: 1 //拖拽模式，0或者1
	              ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你知道吗？亲！<br>layer ≠ layui<br><br>layer只是作为Layui的一个弹层模块，由于其用户基数较大，所以常常会有人以为layui是layerui<br><br>layer虽然已被 Layui 收编为内置的弹层模块，但仍然会作为一个独立组件全力维护、升级。<br><br>我们此后的征途是星辰大海 ^_^</div>'
	              ,success: function(layero){
	                var btn = layero.find('.layui-layer-btn');
	                btn.find('.layui-layer-btn0').attr({
	                  href: 'http://www.layui.com/'
	                  ,target: '_blank'
	                });
	              }
	            });
	          }
	        }
	        $('#layerDemo .layui-btn').on('click', function(){
	            var othis = $(this), method = othis.data('method');
	            active[method] ? active[method].call(this, othis) : '';
	          });
        });
        </script>
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
