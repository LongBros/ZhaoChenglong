<%@page import="bean.SearchBean"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="utils.AddressUtils"%>
<%@page import="utils.SqlUtil"%>
<%@page import="dao.UtilDao"%>
<%@page import="bean.VideoObj"%>
<%@page import="utils.Movie"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//从cookie中获取用户名
Cookie[] cookies=request.getCookies();
String nick="";
for(int i=0;cookies!=null&&i<cookies.length;i++){
	if(cookies[i].getName().equals("user")){
		nick=cookies[i].getValue().toString();
		nick=URLDecoder.decode(nick, "utf-8");
	}
}
//加载已注册用户数，今日注册用户数。已访问ip，今日访问ip
String time=SqlUtil.time();//当前时间
String date=time.substring(0,10);//当前日期
int usenum=UtilDao.getNum("select count(*) from user");
int visnum=UtilDao.getNum("select count(*) from loginlog");
int usertoday=UtilDao.getNum("select count(*) from user where r_Time like'%"+date+"%'");
int vistoday=UtilDao.getNum("select count(*) from loginlog where l_Time like'%"+date+"%'");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>首页-553影院|最新，最全，最受欢迎的影视免费免广告在线观看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/mainpage.css">	
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">	
	<link rel="stylesheet" type="text/css" href="css/index.css"> 
  </head>
  	<script type="text/javascript">
	   function change(img) {
		    img.src=img.src+"?"+new Date().getTime();
	   }
	</script>
  <body ondblclick="Close()">
  	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  		<a class="navbar-brand" href="/MainPage" target="_blank">旧版-LongBro影院</a>
  		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    		<span class="navbar-toggler-icon"></span>
  		</button>
  	<div class="collapse navbar-collapse" id="collapsibleNavbar">
		<ul class="navbar-nav">
		  <li class="nav-item"><a class="nav-link"  href="/index.jsp">首页</a></li>
		  <li class="nav-item"><a class="nav-link"  href="/series.jsp">电视剧</a></li>
		  <li class="nav-item"><a class="nav-link"  href="/movie.jsp">电影</a></li>
		  <li class="nav-item"><a class="nav-link"  href="/variety.jsp">综艺</a></li>
		  <li class="nav-item"><a class="nav-link"  href="/cartoon.jsp">动漫</a></li>
		  <li class="nav-item">
		    <a href="http://www.zy52113.com/Minimusic/"  class="nav-link" >Mini音乐</a>
		  </li>
		  <li class="nav-item"><a class="nav-link" href="toast.jsp">吐槽墙</a></li><img alt="" src="/images/util/hot2.gif">
		  <li class="nav-item">
	      	<form class="form-inline" name="form" action="/search.jsp" method="post" target="_blank">
		      <input  type="text" onfocus="toast('<%=nick%>')" onkeyup="toast('<%=nick%>')" 
		      class="input"  placeholder="尽情搜吧" name="v_name">
		      <input type="submit" class="search" value='搜索'>
		  	</form>
	      </li>
	      <%
			if(!nick.equals("")){//显示个人信息，隐藏登录按钮
				%>
				<li id="info" style="display: inline-block;" class="nav-item">
	        		<a href="javascript:;">
	         		 <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
	        		 <%=nick %>
	        		</a>
	        		<dl class="layui-nav-child">
	        			  <dd><a href="myInfo.jsp">个人信息</a></dd>
	         			  <dd><a  onmousedown="exit()">退出登录</a></dd>
	        		</dl>
	     		 </li>
	     		 <li id="login" style="display: none;" class="layui-nav-item"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
				<%
			}else{//隐藏个人信息，显示登录按钮
				%>	
				<li id="info" style="display: none;" class="nav-item">
	        		<a href="javascript:;">
	         		 <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
	        		 <%=nick %>
	        		</a>
	        		<dl class="layui-nav-child">
	        			  <dd><a href="myInfo.jsp">个人信息</a></dd>
	         			  <dd><a onmousedown="exit()">退出登录</a></dd>
	        		</dl>
	     		 </li>
 					 <li id="login" style="display: inline-block;"  class="layui-nav-item"><a class="login" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
				<% 
			}
		%>
		</div>
		
	      
	      <div id="info" style="display:none;color: white">	      		
	      		
      	 </div>
	    </nav>
	   
		<div class="modal fade" id="loginModal" style="display:none;">
			<div class="modal-dialog modal-sm" style="width:540px;">
				<div class="modal-content" style="border:none;">
					<div class="col-left"></div>
					<div class="col-right">
						<div class="modal-header">
							<button type="button" id="login_close" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title" id="loginModalLabel" style="font-size: 18px;">登录</h4>
						</div>
						<div class="modal-body">
							<section class="box-login v5-input-txt" id="box-login">
								<form id="login_form" action="/Login" method="post" autocomplete="off">
									<ul>
										<li class="form-group"><input class="form-control" id="id_account_l" maxlength="50" name="name" placeholder="请输入用户名" type="text"></li>
										<li class="form-group"><input class="form-control" id="id_password_l" name="pass" placeholder="请输入密码" type="password"></li>
										<li class="form-group"><input class="form-code" id="id_password_l" name="code" placeholder="请输入验证码" type="text"><img alt="验证code" src="/Response"  onclick="change(this)"></li>
									</ul>
								</form>
								<p class="good-tips marginB10"><a id="btnForgetpsw" href="/findpass.jsp" class="fr">忘记密码？</a>还没有账号？<a href="/register.jsp" target="_blank" id="btnRegister">立即注册</a></p>
								<div class="login-box marginB10">
									<button id="login_btn" type="button" class="btn btn-micv5 btn-block globalLogin">登录</button>
									<div id="login-form-tips" class="tips-error bg-danger" style="display: none;">错误提示</div>
								</div>
							</section>
						</div>
					</div>
				</div>
			</div>
 		</div>		
	    <script type="text/javascript" src="js/jquery2.2.2.min.js"></script>
		<script type="text/javascript" src="js/modal.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
	    
		
		<div class="banner">
			<img src="/images/util/trumpet.png">&nbsp;
			<span id="word">
			截止目前，553影院已注册用户数为<%=usenum%>，其中今日注册<%=usertoday%>；
			访问ip共计<%=visnum%>，其中今日访问<%=vistoday%>
			</span>
		</div>
		
		<!-- 退出登录，搜索提示框，小喇叭切换函数，计时器函数及设置添加cookie函数 -->
		<script type="text/javascript" src="/js/index.js"></script>
		
		<div class="title">
			&nbsp;<font color='white' style="background: red">&nbsp;hot&nbsp;</font>
			<img alt="" src="/images/util/hot.gif">
			&nbsp;热门电影
			<span id="hour">00</span>:<span id="minute">00</span>:<span id="second">00</span>
		</div>
		<!-- 以下js位置必须在计时器下面，不然会导致计时失败，无法计时 -->
		<script type="text/javascript">
			//每隔五秒执行改变小喇叭函数，切换一句话
			window.setInterval("change('<%=usenum%>','<%=usertoday%>','<%=visnum%>','<%=vistoday%>');", 5000);
		
			//得到cookie中的值
			var co;
			if(document.cookie!=null){
				//alert(document.cookie);
				//alert(document.cookie.indexOf("timecount")+9);
				co=document.cookie.substring(document.cookie.indexOf("timecount")+10);
				//alert(co);
				//alert(co.substring(0, 2));
				//alert(co.substring(2, 4));
				//alert(co.substring(4, 6));
			}
			//alert(co);

			var h=document.getElementById("hour");
			var m=document.getElementById("minute");
			var s=document.getElementById("second");
			h.innerText=co.substring(0, 2);
			m.innerText=co.substring(2, 4);
			s.innerText=co.substring(4, 6);
			window.setInterval("ti()", 1000);
		</script>
		<div class="all">
			<div class="row">
	<%
		//添加一条访问记录，若是登录用户，则保存登录名，反之登录名为空
		String ip=SqlUtil.getIp(request);
		/* String ip1="";
		if(ip.contains(",")){
			ip1=ip.substring(0, ip.indexOf(","));
		}else{
			ip1=ip;
		}System.out.println(ip1);
		String add=AddressUtils.getAddByIp(ip1); */
		if(UtilDao.exist(ip)==false){//无一天内的记录，则添加
			String sql="insert into loginlog (l_User,l_Ip,l_Time) values"
			+"('"+nick+"','"+ip+"','"+time+"')";
			//System.out.println(sql);	
			UtilDao.insertData(sql);
		}
		//System.out.println(nick);
		String main="https://www.360kan.com";
		//爬取热门电影
		Document doc=Jsoup.connect(main).get();
	    Elements s=doc.getElementsByClass("rmcontent"); 
	    String ss=s.toString();
	    String hot[]=ss.split("</li>");
	    for(int i=0;i<hot.length-1;i++){
	    	String href=hot[i].substring(hot[i].indexOf("href")+6,hot[i].indexOf("\" class=\"js-link\">"));
	//    	String year=hot[i].substring(hot[i].indexOf("class=\"w-newfigure-hint\">")+25,hot[i].indexOf("</span></div>"));
	    	String img=hot[i].substring(hot[i].indexOf("data-src")+10,hot[i].indexOf("\" alt="));
	    	String name="";
	    	String score="";
	    	if(hot[i].contains("class=\"s2")){//有评分
	        	name=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span><span"));
	            score=hot[i].substring(hot[i].indexOf("class=\"s2")+11,hot[i].indexOf("</span></p"));
	    	}else{//无评分
	        	name=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span></p"));
	    	}
	    	%>
	    	<div class="col-md-2 col-sm-3 col-4" >
	    	<div class='whole'>
		    	<a href="/player.jsp?type=dianying&url=<%=href %>" class="g-playicon s-cover-img" target="_blank">
		    	<img id="image" onmouseover="mouseover()" src="<%=img%>" title="<%=name%>" alt="<%=img%>">
		    	<span><%=name%></span><br><span><%=score%></span>
		    	</a>
	    	</div>
	    	</div>
	    	<%   
	     }
	    %>
	    </div><!-- <div class="row">结束 -->
	    </div>
	    <div class="newest">&nbsp;
	    <font color="white" style="background: red">&nbsp;new&nbsp;</font>
	    <img alt="" src="/images/util/icon_new.gif">
	    &nbsp;最新电视剧</div>
	    <div class="all">
	    <div class="row">
	    <%
	  	//爬取最新电视剧
	    Elements es=doc.getElementsByClass("w-newfigure-list"); 
        Element e=es.get(0);
        String se=e.toString();
        String ne[]=se.split("</li>");
        for(int i=0;i<ne.length-1;i++){
        	String href=ne[i].substring(ne[i].indexOf("href")+6,ne[i].indexOf("\" class=\"js-link\">"));
        	String img=ne[i].substring(ne[i].indexOf("data-src")+10,ne[i].indexOf("\" alt="));
        	String update=ne[i].substring(ne[i].indexOf("w-newfigure-hint")+18,ne[i].indexOf("</span>"));
        	String title="";
        	String score="";
        	if(ne[i].contains("class=\"s2")){//有评分
            	title=ne[i].substring(ne[i].indexOf("class=\"s1")+11,ne[i].indexOf("</span><span"));
                score=ne[i].substring(ne[i].indexOf("class=\"s2")+11,ne[i].indexOf("</span></p"));
        	}else{//无评分
            	title=ne[i].substring(ne[i].indexOf("class=\"s1")+11,ne[i].indexOf("</span></p"));
        	}
        	%>
        	<div class="col-md-2 col-sm-3 col-4" >
	    	<div class='whole serial'>
		    	<a href="/player.jsp?type=dianshi&url=<%=href %>" class="g-playicon s-cover-img" target="_blank">
		    	<img id="image" style="width: 180px;height: 100px;" onmouseover="mouseover()" src="<%=img%>" title="<%=title%>" alt="<%=img%>">
		    	<span><%=title%></span><span><%=score%></span>
		    	<p><%= update%></p>
		    	</a>
	    	</div>
	    	</div>
	    	<%  

        }
        %>
        </div>
       </div>
 </body>
</html>
