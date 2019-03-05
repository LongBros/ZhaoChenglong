<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
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
Cookie cookie=new Cookie("toast","1");
cookie.setMaxAge(1*60*60*24);//时间不能太小，因为太小的话，若用户在当前页停留时间过长，则容易不能加载更多，至抛异常
response.addCookie(cookie);

//加载已注册用户数，今日注册用户数。已访问ip，今日访问ip，累计吐槽数，今日吐槽数
String time=SqlUtil.time();//当前时间
String date=time.substring(0,10);//当前日期
int usenum=UtilDao.getNum("select count(*) from user");
int visnum=UtilDao.getNum("select count(*) from loginlog");
int usertoday=UtilDao.getNum("select count(*) from user where r_Time like'%"+date+"%'");
int vistoday=UtilDao.getNum("select count(*) from loginlog where l_Time like'%"+date+"%'");
int tonum=UtilDao.getNum("select count(*) from wall");
int totoday=UtilDao.getNum("select count(*) from wall where W_Time like'%"+date+"%'");
Connection con;
Statement st;
ResultSet rs;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>吐槽社区-553影院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="css/layui.css">
	<link rel="stylesheet" type="text/css" href="css/mainpage.css">
	<link rel="stylesheet" type="text/css" href="css/toast.css">
  </head>
  <body>
		<ul class="layui-nav layui-bg-green">
		  <li class="layui-nav-item"><a href="/MainPage" target="_blank">旧版-LongBro影院</a></li>
		  <li class="layui-nav-item"><a href="/index.jsp">首页</a></li>
		  <li class="layui-nav-item"><a href="/series.jsp">电视剧</a></li>
		  <li class="layui-nav-item"><a href="/movie.jsp">电影</a></li>
		  <li class="layui-nav-item"><a href="/variety.jsp">综艺</a></li>
		  <li class="layui-nav-item"><a href="/cartoon.jsp">动漫</a></li>
		  <li class="layui-nav-item layui-this"><a href="http://www.longqcloud.cn" target="_blank">LongBro博客</a></li>
		</ul>
		<ul class="layui-nav layui-layout-right layui-bg-green" >
	      <%
			if(!nick.equals("")){//显示个人信息，隐藏登录按钮
				%>
				<li id="info" style="display: inline-block;" class="layui-nav-item">
	        		<a href="javascript:;">
	         		 <img src="/images/tx/tx5.jpg" class="layui-nav-img">
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
				<li id="info" style="display: none;" class="layui-nav-item">
	        		<a href="javascript:;">
	         		 <img src="/images/tx/tx5.jpg" class="layui-nav-img">
	        		 <%=nick %>
	        		</a>
	        		<dl class="layui-nav-child">
	        			  <dd><a href="myInfo.jsp">个人信息</a></dd>
	         			  <dd><a onmousedown="exit()">退出登录</a></dd>
	        		</dl>
	     		 </li>
	     		 <li id="login" style="display: inline-block;"  class="layui-nav-item"><a href="login.jsp" title="登录是为了方便你评论视频，以及记录你的播放记录等功能。">登录</a></li>
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
		<script type="text/javascript">
			window.setInterval("change('<%=usenum%>','<%=usertoday%>','<%=visnum%>','<%=vistoday%>');", 5000);
		</script>
		<!-- 退出登录，搜索提示框，小喇叭切换函数，计时器函数及设置添加cookie函数 -->
		<script type="text/javascript" src="/js/index.js"></script>
		<script type="text/javascript" src="/js/comment.js"></script>
		<div class="ranks">
			<div class='rank'>
				&emsp;<span style="color: black;" id="dianshi" onmouseover="qiehuan('dianshi')">电视剧</span>&emsp;
					  <span style="color: black" id="zongyi" onmouseover="qiehuan('zongyi')">综艺</span>&emsp;
					  <span style="color: black" id="dianying" onmouseover="qiehuan('dianying')">电影</span>&emsp;
					  <span style="color: black" id="dongman" onmouseover="qiehuan('dongman')">动漫</span>
				<div id="video"></div>
			</div>
			<%-- <%
			String main="https://www.360kan.com";
		ArrayList<VideoObj> vos=Movie.getdata(main);
		//System.out.println(vos.size());
		out.write("<div class='rank'>");
		out.write("<div class='type'>电视剧排行榜</div>");
		for(int i=0;i<10;i++){
			VideoObj vo=vos.get(i);
			int pai=vo.getPai();
			String sp="";
			String name=vo.getName();
			String href=vo.getHref();
			String pn=vo.getPn();
			if(name.length()>6){
				name=name.substring(0,5)+"...";
			}
			//获取播放链接
			String purl=Movie.getPUrl(href);
			if(i==0){
				sp="<first>"+(i+1)+"</first>";
			}else if(i==1){
				sp="<second>"+(i+1)+"</second>";
			}else if(i==2){
				sp="<third>"+(i+1)+"</third>";
			}else{
				sp="<other>"+(i+1)+"</other>";
			}
			
			out.write(sp+"&nbsp;<a href=\"/player.jsp?type=dianshi&url="+href+"\" target='_blank'>"+name+"</a>&nbsp;<font>"+vo.getPn()+"</font><br>");
		
		}
		out.write("</div>");
		
		out.write("<div class='rank'>");
		out.write("<div class='type'>综艺排行榜</div>");
		for(int i=10;i<20;i++){
			VideoObj vo=vos.get(i);
			int pai=vo.getPai();
			String name=vo.getName();
			String href=vo.getHref();
			String pn=vo.getPn();
			if(name.length()>6){
				name=name.substring(0,5)+"...";
			}
			//获取播放链接
			String purl=Movie.getPUrl(href);
			out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=zongyi&url="+vo.getHref()+"\" target='_blank'>"+name+"</a>&nbsp;<font>"+vo.getPn()+"</font><br>");
		
		}
		out.write("</div>");
		
		out.write("<div class='rank'>");
		out.write("<div class='type'>电影排行榜</div>");
		for(int i=20;i<30;i++){
			VideoObj vo=vos.get(i);
			int pai=vo.getPai();
			String name=vo.getName();
			String href=vo.getHref();
			String pn=vo.getPn();
			if(name.length()>6){
				name=name.substring(0,5)+"...";
			}
			//获取播放链接
			String purl=Movie.getPUrl(href);
			out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=dianying&url="+vo.getHref()+"\" target='_blank'>"+name+"</a>&nbsp;<font>"+vo.getPn()+"</font><br>");
		}
		out.write("</div>");
		
		out.write("<div class='rank'>");
		out.write("<div class='type'>动漫排行榜</div>");
		for(int i=30;i<40;i++){
			VideoObj vo=vos.get(i);
			int pai=vo.getPai();
			String name=vo.getName();
			String href=vo.getHref();
			String pn=vo.getPn();
			if(name.length()>6){
				name=name.substring(0,5)+"...";
			}
			//获取播放链接
			String purl=Movie.getPUrl(href);
			out.write("<pai>"+pai+"</pai>&nbsp;<a href=\"/player.jsp?type=dongman&url="+vo.getHref()+"\" target='_blank'>"+name+"</a>&nbsp;<font>"+vo.getPn()+"</font><br>");
		
		}
		out.write("</div>"); 
       %> --%>
		</div>	
		<div class="send">
			<form name="forms" action="">
				<p> <strong>来说点儿什么吧...</strong></p>
				<p><span> 您的昵称:</span>
		            <input name="name" type="text" value="" id="name">
		            *&nbsp;<em style="color: red;" onclick="changeNick()">切换</em>
		             &nbsp;&nbsp;(您也可以自己设置昵称哟~)</p>
	            <p><span> 您的头像:</span>
	            <input type="text" name="tx" style="display: none;">
	            <img id="current1" style="width: 30px;height: 30px;" onclick="changeTx(1)" src="images/tx/tx1.jpg">&nbsp;
	            <img id="current2" style="width: 30px;height: 30px;" onclick="changeTx(2)" src="images/tx/tx2.jpg">&nbsp;
	            <img id="current3" style="width: 30px;height: 30px;" onclick="changeTx(3)" src="images/tx/tx3.jpg">&nbsp;
	            <img id="current4" style="width: 30px;height: 30px;" onclick="changeTx(4)" src="images/tx/tx4.jpg">&nbsp;
	            <img id="current5" style="width: 30px;height: 30px;" onclick="changeTx(5)" src="images/tx/tx5.jpg">&nbsp;
	            <img id="current6" style="width: 30px;height: 30px;" onclick="changeTx(6)" src="images/tx/tx6.jpg">&nbsp;
	            *</p>
	            <p><span class="tnr">吐槽内容:</span>*已输入<span id="wordnum"><font color="red">0</font></span>字符，需小于<font color="red">245</font>字符且不为空
		            <textarea name="toast" onkeyup="writting()" cols="60" rows="8" placeholder="请您文明吐槽，谢谢！^-^" id="lytext"></textarea>
		          </p>
	            <p>
	            	<img alt="嗷大喵表情" title="嗷大喵表情" style="width: 18px;height: 18px" src="/images/util/face-2x.png" onmousedown="imgAo()">
		            <input type="button" onclick="sendCom()" value="提交">
		        </p>
			</form>
			<div class="ao" id="ao" style="display: none;">
       		<%
       		String ao[]={"baibai","bishi","caidao","cangsang","chanle","chijing","dengyan"
       				,"dese","deyi","guzhang","haixiu","haode","jingdaile","jingjingkan","keai"
       				,"kun","lianhong","nidongde","qidai","qinqin","shangxin","shengqi"
       				,"shuai","sikao","tongxin","touxiao","wabikong","weixiao","wulian"
       				,"wuyu","xiaoku","xiaozheku","xihuan","yaobai","yihuo","zan"
       				,"zhayan","zhenjing","zhenjingku","zhuakuang"};
       		for(int i=0;i<ao.length;i++){
       			if(i%10==0){
       				out.write("<br>");
       			}
       			%>
       			<img alt="" src="/images/aodamiao/<%=ao[i]%>.gif" onclick="appendValueAo('<%=ao[i]%>')">
       			<%
       		}
       		%>
      		 </div>
       		<img alt="" src="/images/util/hot.gif">
			&nbsp;累计<font color='red'><%=tonum%></font>句吐槽，其中今日<font color='red'><%=totoday%></font>句吐槽
		</div>
			
		<div class="comments">
		   <div id="comments">
			<%
				//加载数据库中的吐槽
				try{
		       		Class.forName(SqlUtil.driver);
					con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
					st=con.createStatement();
					String sql="select * from wall order by w_Id desc limit 0,5";
					rs=st.executeQuery(sql);
					while(rs.next()){
						int w_Id=rs.getInt(1);
						String w_Nick=rs.getString(2);
						String w_Tx=rs.getString(3);
						String w_Content=rs.getString(4);
						
						w_Content=w_Content.replaceAll("【", "<img alt='' src='/images/aodamiao/");
						w_Content=w_Content.replaceAll("】", ".gif'>");						
						String w_Ip=rs.getString(5);//原始ip
						//w_Ip=w_Ip.substring(0,w_Ip.indexOf("."));
						//System.out.println(w_Ip);
						//System.out.println(w_Ip.split(".").length);
						//w_Ip=ips[0]+"."+ips[1]+".*.*";
						if(w_Ip.length()>8){
							w_Ip=w_Ip.substring(0,8)+"...";
						}
						String w_Add=rs.getString(6);//地址
						System.out.println("地址为"+w_Add);
						if("".equals(w_Add)){//如果地址为空，则将ip放上
							w_Add=w_Ip;
						} 
						String w_Time=rs.getString(7);
						%>
						<div class="comment">
							<ul>
								<img alt="" src="<%=w_Tx%>">
								<span class="info"><%=w_Nick%>（来自<font color="gray"><%=w_Add%></font>的网友）</span>
								<span class="time"><%=w_Time %></span>		
								<%if(w_Id==1){ %>
									<span style="width:25px;height:25px;color:white; background:red;">置顶</span>
								<%} %>
								<p><%=w_Content%></p>
							</ul>
						</div>
						<%
					}
		       	 }catch(Exception e){
		       		 
		       	 }
			 %>
			 </div>
			 <center><span id="load" onclick="loadTo()">点击加载更多∨</span></center>
		</div>
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
