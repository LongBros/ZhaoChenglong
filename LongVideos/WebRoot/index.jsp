<%@page import="com.longbro.bean.SearchBean"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="utils.AddressUtils"%>
<%@page import="utils.SqlUtil"%>
<%@page import="dao.UtilDao"%>
<%@page import="com.longbro.bean.VideoObj"%>
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
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="个人视频网站,免费免广告观看VIP视频影视,553影院">
	<meta http-equiv="description" content="553影院，原名LongBro影院，提供各种类型视频资源的免费免广告在线观看。">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="css/layui.css">
	<link rel="stylesheet" type="text/css" href="css/mainpage.css">
	<!-- <script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?875d53ca341e74921df390bce7e8fc10";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script> -->
  </head>
  <body ondblclick="Close()">
		<ul class="layui-nav layui-bg-green">
		  <li class="layui-nav-item"><a href="/MainPage" target="_blank">旧版-LongBro影院</a></li>
		  <li class="layui-nav-item layui-this"><a href="/index.jsp">首页</a></li>
		  <li class="layui-nav-item"><a href="/series.jsp">电视剧</a></li>
		  <li class="layui-nav-item"><a href="/movie.jsp">电影</a></li>
		  <li class="layui-nav-item"><a href="/variety.jsp">综艺</a></li>
		  <li class="layui-nav-item"><a href="/cartoon.jsp">动漫</a></li>
		  <li class="layui-nav-item">
			    <a href="javascript:;">其他</a>
			    <dl class="layui-nav-child">
			      <dd><a href="http://www.longqcloud.cn/msgboard.jsp" target="_blank">每日一句</a></dd>
			      <dd><a href="http://www.longqcloud.cn/sponsor/showSponsor.jsp" target="_blank">赞助记录</a></dd>
			      <dd><a href="log/visitlog.jsp" target="_blank">553访问日志</a></dd>
			      <dd><a href="log/barchart.jsp" target="_blank">553访问日志-条形图</a></dd>
			      <dd><a href="log/linechart.jsp" target="_blank">553访问日志-线形图</a></dd>
			      <dd><a href="http://www.longqcloud.cn" target="_blank">LongBro博客</a></dd>
			      <dd><a href="http://www.zy52113.com/Minimusic/" target="_blank">Mini音乐</a></dd>
			    </dl>
		  </li>
		  <li class="layui-nav-item"><a href="toast.jsp">吐槽墙</a></li><img alt="" src="/images/util/hot2.gif">
		</ul>
		<ul class="layui-nav layui-layout-right layui-bg-green" >
	      <li class="layui-nav-item">
	      	<form name="form" action="/search.jsp" method="post" target="_blank">
		      <input type="text" onfocus="toast('<%=nick%>')" onkeyup="toast('<%=nick%>')" 
		      class="input"  placeholder="尽情搜吧" name="v_name">
		      <input type="submit" class="search" value='搜索'>
		  	</form>
	      </li>
	      <div id="info" style="display:none;color: white"></div>
	      <%
			if(!nick.equals("")){//显示个人信息，隐藏登录按钮
				%>
				<li id="info" style="display: inline-block;" class="layui-nav-item">
	        		<a href="javascript:;">
	         		 <img src="/images/tx/tx5.jpg" class="layui-nav-img">
	        		 <%=nick%>
	        		</a>
	        		<dl class="layui-nav-child">
	        			  <dd><a target="_blank" href="myInfo.jsp?user=cookie">个人信息</a></dd>
	         			  <dd><a onmousedown="exit()">退出登录</a></dd>
	        		</dl>
	     		 </li>
	     		 <li id="login" style="display:none;" class="layui-nav-item"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
				<%
			}else{//隐藏个人信息，显示登录按钮
				%>	
				<li id="info" style="display: none;" class="layui-nav-item">
	        		<a href="javascript:;">
	         		 <img src="/images/tx/tx5.jpg" class="layui-nav-img">
	        		 <%=nick%>
	        		</a>
	        		<dl class="layui-nav-child">
	        			  <dd><a href="myInfo.jsp?user=">个人信息</a></dd>
	         			  <dd><a onmousedown="exit()">退出登录</a></dd>
	        		</dl>
	     		 </li>
 	     		 <li id="login" style="display:inline-block;" class="layui-nav-item"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
 				<% 
			}
		%>
	    </ul>	    
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
	    	<div class='whole'>
		    	<a href="/player.jsp?type=dianying&url=<%=href %>" class="g-playicon s-cover-img" target="_blank">
		    	<img id="image" src="<%=img%>" title="<%=name%>" alt="<%=img%>">
		    	<span><%=name%></span><br><span><%=score%></span>
		    	</a>
	    	</div>
	    	<%   
	     }
	    %>
	    </div>
	    <div class="newest">&nbsp;
	    <font color="white" style="background: red">&nbsp;new&nbsp;</font>
	    <img alt="" src="/images/util/icon_new.gif">
	    &nbsp;最新电视剧</div>
	    <div class="all">
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
	    	<div class='whole serial'>
		    	<a href="/player.jsp?type=dianshi&url=<%=href %>" class="g-playicon s-cover-img" target="_blank">
		    	<img id="image" style="width: 180px;height: 100px;" src="<%=img%>" title="<%=title%>" alt="<%=img%>">
		    	<span><%=title%></span><span><%=score%></span>
		    	<p><%=update%></p>
		    	</a>
	    	</div>
	    	<%  
        }
        %>
        </div>
        <center><hr width='80%' height='5px'></center><br>
		<%@include file="footer.jsp" %>	
		<script src="layui.js" charset="utf-8"></script>
		<script>
		layui.use('element', function(){
		  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
		  //监听导航点击
		  element.on('nav(demo)', function(elem){
		    //console.log(elem)
		    layer.msg(elem.text());
		  });
		});
		</script> 
 </body>
</html>
