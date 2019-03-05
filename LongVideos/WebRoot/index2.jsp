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
<html class="no-js">
  <head>
    <base href="<%=basePath%>">
    <title>首页-553影院|最新，最全，最受欢迎的影视免费免广告在线观看</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/> 
	<meta http-equiv="pragma" content="no-cache">
	<meta name="mobile-web-app-capable" content="yes"> 
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<meta name="apple-mobile-web-app-capable" content="yes"> 
    <meta name="apple-mobile-web-app-status-bar-style" content="black"> 
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/> 
    <link rel="apple-touch-icon-precomposed" href="/images/util/logo2.png"> 
    <meta name="msapplication-TileImage" content="/images/util/logo2.png"> 
    <meta name="msapplication-TileColor" content="#0e90d2"> 
	<link rel="stylesheet" href="http://cdn.amazeui.org/amazeui/2.7.2/css/amazeui.css">
    <link rel="stylesheet" href="http://www.anytv.vip/assets/css/app.css?v=2.1">
    <link rel="stylesheet" href="css/mainpage.css"> 
    <!--[if (gte IE 9)|!(IE)]><!--> 
	<script src="http://www.anytv.vip/assets/js/jquery.min.js"></script> 
	<!--<![endif]--> <!--[if lte IE 8 ]> 
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script> 
	<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script> 
	<script src="http://www.anytv.vip/assets/js/amazeui.ie8polyfill.min.js"></script> 
	<![endif]--> <!--<script src="http://www.anytv.vip/assets/js/app.js"></script>--> 
	<script>  
			var mkSiteInfo = { 
				siteUrl: "http://www.anytv.vip", debug: false, admin: false 
			}; 
	</script>  
  </head>
  <body ondblclick="Close()">
  	<header class="am-topbar">
  	<div class="am-container">
  		<h1 class="am-topbar-brand hover-bounce"> 
              <a href="/MainPage" class="web-name"> 
                  <span class="am-icon-film am-icon-md"></span>553影院
              </a> 
        </h1> 
        <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#doc-topbar-collapse'}"> 
            <span class="am-sr-only">导航切换</span>
             <span class="am-icon-bars"></span> 
        </button> 
	  	<div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse"> 
			<ul class="am-nav am-nav-pills am-topbar-nav">
			  <li><a href="/index.jsp">首页</a></li>
			  <li><a href="/series.jsp">电视剧</a></li>
			  <li><a href="/movie.jsp">电影</a></li>
			  <li><a href="/variety.jsp">综艺</a></li>
			  <li><a href="/cartoon.jsp">动漫</a></li>
			  <li  class="am-dropdown" data-am-dropdown>
			  	  <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">其他
			  	  <span class="am-icon-caret-down"></span></a>
			      <ul class="am-dropdown-content"> 
			      	  <li><a href="http://www.zy52113.com/Minimusic/">Mini音乐</a></li>
			      	  <li><a href="http://www.zy52113.com/Minimusic/">Mini音乐</a></li>
			      	  <li><a href="http://www.zy52113.com/Minimusic/">Mini音乐</a></li>
			      </ul>
			  </li>
			  <li><a href="toast.jsp">吐槽墙<img alt="" src="/images/util/hot2.gif"></a></li>
			  </ul>
		      	<form  class="am-topbar-form am-topbar-left am-form-inline"  name="form" action="/search.jsp" method="post" target="_blank">
			      <div class="am-input-group am-input-group-primary am-input-group-sm"> 
				      <input  type="text" onfocus="toast('<%=nick%>')" onkeyup="toast('<%=nick%>')" class="input"  placeholder="尽情搜吧" name="v_name">
				      <input type="submit" class="search" value='搜索'>
			      </div>
			  	</form>
			  	<div class="am-topbar-right">
				      <%
						if(!nick.equals("")){//显示个人信息，隐藏登录按钮
							%>
							<li id="info" class="am-dropdown" data-am-dropdown style="display: inline-block;">
				        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
				         		 <img src="/images/tx/tx5.jpg">
				        		 <%=nick %>
				        		</a>
				        		<ul class="am-dropdown-content"> 
				        			  <li><a href="myInfo.jsp">个人信息</a></li>
				         			  <li><a onmousedown="exit()">退出登录</a></li>
				        		</ul>
				     		 </li>
				     		 <li id="login" style="display: none;" class="layui-nav-item"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
							<%
						}else{//隐藏个人信息，显示登录按钮
							%>
							<ul>	
							<li id="info" style="display: none;">
				        		<a href="javascript:;">
				         		 <img src="/images/tx/tx5.jpg">
				        		 <%=nick %>
				        		</a>
				        		<dl class="layui-nav-child">
				        			  <dd><a href="myInfo.jsp">个人信息</a></dd>
				         			  <dd><a onmousedown="exit()">退出登录</a></dd>
				        		</dl>
				     		 </li>
				     		 <li id="login" style="display: inline-block;"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
							</ul>
							<% 
						}
					%>
					</div><!-- <div class="am-topbar-right">结束 -->
			</div>
      </div>
	</header>
	<style> 
        .am-slider-d2 .am-slider-more { padding: 4px 10px; } 
        .friend-links>div>a { margin-right: 10px; } 
    </style> 	   		
		<div class="banner">
			<img src="/images/util/trumpet.png">&nbsp;
			<span id="word">
			截止目前，553影院已注册用户数为<%=usenum%>，其中今日注册<%=usertoday%>；
			访问ip共计<%=visnum%>，其中今日访问<%=vistoday%>
			</span>
		</div>
		
		<!-- 退出登录，搜索提示框，小喇叭切换函数，计时器函数及设置添加cookie函数 -->
		<script type="text/javascript" src="/js/index.js"></script>
		<div class="am-alert am-alert-warning am-margin-bottom" data-am-alert> 
			<button type="button" class="am-close">&times;</button> 
			<p> 
				<span class="am-icon-bullhorn"></span> 
				<a href="" target="_blank"> 尊重版权，请勿通过本站点播、下载、查阅盗版及非法资源！否则产生的一切法律纠纷均由您自行承担！</a> 
			</p> 
        </div> 
		<div data-am-widget="slider" class="am-slider am-slider-d2" data-am-slider='{"directionNav":false}' > 
             <ul class="am-slides"> 
                <li> <img src="https://ws1.sinaimg.cn/large/a15b4afegy1fp8ya82r99j20rs0bwn0a.jpg"> <div class="am-slider-desc"></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2018/07/24/141116.72322717_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">延禧攻略</h2> <p>又一出宫斗大戏</p> </div><a href="http://www.anytv.vip/player.php?oid=17090" target="_blank" class="am-slider-more">立即观看</a></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2018/06/15/163107.78496368_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">死侍2</h2> <p>更加限制级，但同样具备娱乐性。这部聚焦X战警宇宙的调皮老小孩的影片也没有留给观众太多解构空间，成功超越了前作</p> </div><a href="http://www.anytv.vip/player.php?oid=17378" target="_blank" class="am-slider-more">立即观看</a></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2017/12/14/115604.99772757_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">湮灭</h2> <p>原生态的荒野，隐藏着神秘的黑暗力量..</p> </div><a href="http://www.anytv.vip/player.php?mid=hKTmYxH4S0r1SB" target="_blank" class="am-slider-more">立即观看</a></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2018/02/12/141551.46802281_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">黑豹</h2> <p>漫威宇宙中独树一帜</p> </div><a href="http://www.anytv.vip/player.php?mid=hKbrYxH4RnT2UB" target="_blank" class="am-slider-more">立即观看</a></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2017/12/12/092424.19068833_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">红海行动</h2> <p>蛟龙突击队、北约武器、坦克大战！《红海行动》展现真实残酷的战争</p> </div><a href="http://www.anytv.vip/player.php?mid=hKvjYhH4RHX3Sh" target="_blank" class="am-slider-more">立即观看</a></div></li>
				<li> <img src="http://img5.mtime.cn/pi/2018/01/22/100420.62238576_864X370.jpg"> <div class="am-slider-desc"> <div class="am-slider-content"> <h2 class="am-slider-title">唐人街探案2</h2> <p>“也许中国就要拥有一个新的侦探电影品牌了”</p> </div><a href="http://www.anytv.vip/player.php?mid=gqLmZRH4R0j0Sx" target="_blank" class="am-slider-more">立即观看</a></div></li> 
			</ul> 
		</div> 
		<div class="am-panel am-panel-default" style="margin-top: 40px"> 
			<div class="am-panel-bd"> 院线热映： 
				<span class="colorful"> 
					<a href="http://www.anytv.vip/player.php?oid=17492" target="_blank"> 
					<span class="am-icon-play-circle"></span> 碟中谍6</a>
					<a href="http://www.anytv.vip/player.php?oid=16396" target="_blank"> 
					<span class="am-icon-play-circle"></span> 蚁人2 </a>
					<a href="http://www.anytv.vip/player.php?oid=18146" target="_blank"> 
					<span class="am-icon-play-circle"></span> 一出好戏 </a>
					<a href="http://www.anytv.vip/player.php?oid=18148" target="_blank"> 
					<span class="am-icon-play-circle"></span> 爱情公寓 </a>
					<a href="http://www.anytv.vip/player.php?oid=17626" target="_blank"> 
					<span class="am-icon-play-circle"></span> 狄仁杰之四大天王 </a>
					<a href="http://www.anytv.vip/player.php?oid=16127" target="_blank"> 
					<span class="am-icon-play-circle"></span> 我不是药神 </a>
					<a href="http://www.anytv.vip/player.php?oid=17526" target="_blank"> 
					<span class="am-icon-play-circle"></span> 西虹市首富 </a>
					<a href="http://www.anytv.vip/player.php?oid=16732" target="_blank"> 
					<span class="am-icon-play-circle"></span> 摩天营救 </a> 
				</span> 
			</div> 
		</div>  
								
		<div class="title">
			&nbsp;<font color='white' style="background: red">&nbsp;hot&nbsp;</font>
			<img alt="" src="/images/util/hot.gif">
			&nbsp;热门电影
			<span id="hour">00</span>:<span id="minute">00</span>:<span id="second">00</span>
		</div>
		<div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" > 
			<h2 class="am-titlebar-title ">热门电影 </h2> 
			<nav class="am-titlebar-nav"> 
				<a href="http://www.zy52113.com/movie.jsp" class="" target="_blank">全部电影 &raquo;</a> 
			</nav> 
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
		<ul class="am-avg-sm-3 am-avg-md-4 am-avg-lg-5 am-thumbnails movie-lists"> 
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
	    	<li>
		    	<a class="movie-item" href="/player.jsp?type=dianying&url=<%=href %>" target="_blank"> 
					<div class="movie-cover"> 
						<img src="http://www.anytv.vip/assets/i/lazy.gif" data-original="<%=img%>" class="lazyload"> 
						<span class="movie-description"><i class="description-bg"></i> <p>评分：<%=score%></p> <p>年代：2018</p> <p>&gt; 在线观看</p> </span> 
					</div> 
					<div class="movie-title"> 
						<p class="movie-name"><%=name%></p> <p class="movie-tags">评分：<%=score%></p> 
					</div> 
				</a>
	    	</li>
	    	<%   
	     }
	    %>
	    </ul>
	    <div class="newest">&nbsp;
		    <font color="white" style="background: red">&nbsp;new&nbsp;</font>
		    <img alt="" src="/images/util/icon_new.gif">
		    &nbsp;最新电视剧
	    </div>
	   <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" > 
			<h2 class="am-titlebar-title ">最新电视剧 </h2> 
			<nav class="am-titlebar-nav"> 
				<a href="http://www.zy52113.com/series.jsp" class="" target="_blank">全部电视剧 &raquo;</a> 
			</nav> 
		</div>
		<ul class="am-avg-sm-3 am-avg-md-4 am-avg-lg-5 am-thumbnails movie-lists"> 
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
        	
        	<li>
		    	<a class="movie-item" href="/player.jsp?type=dianshi&url=<%=href %>" target="_blank"> 
					<div class="movie-cover"> 
						<img src="http://www.anytv.vip/assets/i/lazy.gif" data-original="<%=img%>" class="lazyload"> 
						<span class="movie-description"><i class="description-bg"></i> 
							<p>评分：<%=score%></p> <p><%= update%></p> <p>&gt; 在线观看</p> 
						</span> 
					</div> 
					<div class="movie-title"> 
						<p class="movie-name"><%=title%></p> <p class="movie-tags"><%= update%></p> 
					</div> 
				</a>
	    	</li>
	    	<%  

        }
        %>
       </ul>
       
       <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" style="margin-top: 0"> 
			<h2 class="am-titlebar-title "> 热播电影榜 </h2> 
		</div> 
		<ul class="right-list"> 
			<li> <a href="player.php?mid=faXmZBH5QXP0SR" target="_blank" class="am-text-truncate"> <span class="r-l-right">42.1万</span> <span class="am-badge am-round">1</span> 瞒天过海:美人计 </a> </li>
			<li> <a href="player.php?mid=g6fnZhH4SHT0UB" target="_blank" class="am-text-truncate"> <span class="r-l-right">29.3万</span> <span class="am-badge am-round">2</span> 我不是药神 </a> </li> 
			<li> <a href="player.php?mid=fabiaBH4RnX8UR" target="_blank" class="am-text-truncate"> <span class="r-l-right">24.7万</span> <span class="am-badge am-round">3</span> 蚁人2：黄蜂女现身 </a> </li> 
			<li> <a href="player.php?mid=gaXlaRH4SHn0Sx" target="_blank" class="am-text-truncate"> <span class="r-l-right">18.4万</span> <span class="am-badge am-round">4</span> 超人总动员2 </a> </li> 
			<li> <a href="player.php?mid=faPjYhH4RHb2UB" target="_blank" class="am-text-truncate"> <span class="r-l-right">18.3万</span> <span class="am-badge am-round">5</span> 卧底巨星 </a> </li> 
			<li> <a href="player.php?mid=faPoZBH5Q0j6Sh" target="_blank" class="am-text-truncate"> <span class="r-l-right">15.8万</span> <span class="am-badge am-round">6</span> 悲伤逆流成河 </a> </li> 
			<li> <a href="player.php?mid=hqfmZkD5Qnn1TB" target="_blank" class="am-text-truncate"> <span class="r-l-right">14.1万</span> <span class="am-badge am-round">7</span> 英雄本色 </a> </li> 
			<li> <a href="player.php?mid=hKLmZhH4RXn1TR" target="_blank" class="am-text-truncate"> <span class="r-l-right">12.9万</span> <span class="am-badge am-round">8</span> 西虹市首富 </a> </li> 
			<li> <a href="player.php?mid=gKLjZhH4SHXATR" target="_blank" class="am-text-truncate"> <span class="r-l-right">12.8万</span> <span class="am-badge am-round">9</span> 双重约会 </a> </li> 
			<li> <a href="player.php?mid=gKLrYUL8SHjAUB" target="_blank" class="am-text-truncate"> <span class="r-l-right">12.3万</span> <span class="am-badge am-round">10</span> 夜关门：欲望之花 </a> </li> 
		</ul> 
       
       <div data-am-widget="gotop" class="am-gotop am-gotop-fixed" title="返回顶部"> 
			<a href="#top" title=""><i class="am-gotop-icon am-icon-arrow-up"></i></a> 
	   </div> 
       <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default am-show-sm-only" id=""> 
			<ul class="am-navbar-nav am-cf am-avg-sm-4"> 
				<li> 
					<a href="index.jsp" class="">  
						<img src="https://ws1.sinaimg.cn/large/a15b4afegy1fic1fki6s1j203k03ka9t.jpg" alt="首页"/> 
						<span class="am-navbar-label">首页</span> 
					</a> 
				</li> 
				<li> 
					<a href="movie.jsp" class=""> 
						<img src="https://ws1.sinaimg.cn/large/a15b4afely1fic14zf5pqj203k03kjr9.jpg" alt="电影"/> 
						<span class="am-navbar-label">电影</span> 
					</a> 
				</li> 
				<li> 
					<a href="series.jsp" class=""> 
						<img src="https://ws1.sinaimg.cn/large/a15b4afegy1fic1iuanydj203k03kjrd.jpg" alt="电视剧"/> 
						<span class="am-navbar-label">电视剧</span> 
					</a> 
				</li> 
				<li> 
					<a href="cartoon.jsp" class=""> 
						<img src="https://ws1.sinaimg.cn/large/a15b4afely1fic14imn45j203k03kglh.jpg" alt="动漫"/> 
						<span class="am-navbar-label">动漫</span> 
					</a> 
				</li> 
				<li> 
					<a href="variety.jsp" class=""> 
						<img src="https://ws1.sinaimg.cn/large/a15b4afegy1fic1eik5w4j203k03kq2t.jpg" alt="VIP解析"/> 
						<span class="am-navbar-label">综艺</span> 
					</a> 
				</li> 
			</ul> 
		</div>
		
		<script src="http://www.anytv.vip/assets/plugns/layer/layer.js"></script> 
		<script src="http://www.anytv.vip/assets/js/jquery.lazyload.min.js?v1.0"></script> 
		<script type="text/javascript">
			var store;
			$(function() {  
				$("img.lazyload").lazyload({ effect: "fadeIn",  failurelimit: 20,  threshold: 180,  load: function() { $(this).removeClass('lazyload'); $(this).addClass('img-loaded'); } });  $("#show-history").click(function() { store = $.AMUI.store; if (store.enabled) { var histemp = store.get('history')? store.get('history'): []; if(histemp.length == 0) { $("#history-list").html('<li><a href="javascript:;">暂无播放记录</a></li>'); } else { $("#history-list").html(''); for(var i=0; i<histemp.length; i++) { switch(histemp[i].types) { case 'movie': $("#history-list").append('<li><a href="' + mkSiteInfo.siteUrl + '/player.php?mid='+histemp[i].id+'">'+histemp[i].name+'</a></li>'); break; case 'tv': $("#history-list").append('<li><a href="' + mkSiteInfo.siteUrl + '/player.php?tvid='+histemp[i].id+'">'+histemp[i].name+' [第'+histemp[i].episode+'集]</a></li>'); break; case 'ct': $("#history-list").append('<li><a href="' + mkSiteInfo.siteUrl + '/player.php?ctid='+histemp[i].id+'">'+histemp[i].name+' [第'+histemp[i].episode+'集]</a></li>'); break; case 'va': $("#history-list").append('<li><a href="' + mkSiteInfo.siteUrl + '/player.php?vaid='+histemp[i].id+'">'+histemp[i].name+' ['+histemp[i].episode+']</a></li>'); break; case 'okzy': $("#history-list").append('<li><a href="' + mkSiteInfo.siteUrl + '/player.php?oid='+histemp[i].id+'">'+histemp[i].name+' ['+histemp[i].episode+']</a></li>'); break; } } $("#history-list").append('<li><a href="javascript:;" onclick="clearHistory();"><span class="am-text-warning am-text-xs">清空播放记录</span></a></li>'); } } }); for(var jj=50; jj >0; jj--) { console.log('盗代码的人木有小 JJ ......................................' + jj); }}); 
				function clearHistory() {
					$("#show-history-dropdown").dropdown("close");  
					store.remove('history'); 
					layer.msg("播放记录已清空");
				}
				function urlEncode(String) { 
					return encodeURIComponent(String).replace(/'/g,"%27").replace(/"/g,"%22"); 
				}
				if(mkSiteInfo.debug == false) {  
					(function(){ 
						var bp = document.createElement('script'); 
						var curProtocol = window.location.protocol.split(':')[0]; 
						if (curProtocol === 'https') { 
							bp.src = 'https://zz.bdstatic.com/linksubmit/push.js'; 
						} else { bp.src = 'http://push.zhanzhang.baidu.com/push.js'; 
						} 
						var s = document.getElementsByTagName("script")[0]; 
						s.parentNode.insertBefore(bp, s); 
					})();  
				(function(){ 
				var src = (document.location.protocol == "http:") ? "http://js.passport.qihucdn.com/11.0.1.js?__":"https://jspassport.ssl.qhimg.com/11.0.1.js?__"; 
					document.write('<script src="' + src + '" id="sozz"><\/script>'); })(); 
				} else { 
					console.info("当前站点处于调试模式！"); 
				} 
			</script> 
			<style> 
				#feedAv, .section-service-w { width: 0!important; height: 0!important; overflow: hidden!important; position: fixed!important; left: -9999999px!important; top: -9999999px!important; } 
				#MZAD_POP_PLACEHOLDER { margin-top: -250px!important; transform: scale(0); } 
			</style> 
            <script src="http://www.anytv.vip/assets/js/amazeui.min.js"></script>   

 </body>
</html>
