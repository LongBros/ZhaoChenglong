<%@page import="dao.ViewConDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//增加一次受访记录，并查询页面受访次数
//增加一次受访记录
ViewConDao vDao=new ViewConDao();
vDao.addVisit(request,1);
//查询页面受访次数
int count=vDao.getVisitNum(1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>干货街-有偿干货-LongBro的博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="LongBro的博客,干货街，有偿干货">
	<meta http-equiv="description" content="LongBro博客的一大模块-干货街，为大家分享干货">
    <link rel="shortcut icon" href="../images/useful/logo2.png" type="image/x-icon"/>
	<link href="../css/base.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery.js"></script>
</head>
<body>
<div id="wrapper">
     <%@include file="../header.jsp" %>
	<div class="container">
	  <div class="con_content">
	    <div class="about_box">
	      <h2 class="nh1"><span>您现在的位置是：<a href="/" target="_blank">干货街</a>>><a href="#" target="_blank">有偿分享</a></span><b>有偿干货</b></h2>
	      
	      <div class="f_box">
	          <p class="a_title">有偿干货</p>
	          <p class="p_title"></p>
	            <p class="box_p"><span>发布时间：2018-12-12 15:12:42</span><span>作者：赵成龙</span><span>来源：LongBro博客</span><span>点击：<%=count%></span></p>
	          <!-- 可用于内容模板 --> 
	        </div>
	        
	      <div class="dtxw box">
	      	&emsp;&emsp;郑重声明：LongBro博客有偿分享的各种资料或源码，均为站长经努力编写或爬取互联网上的劳动成品，
	      	因此属于有偿分享，赞助本站方可下载（尊重劳动成果，下载后请勿分享或二次出售）。感谢大家的理解和支持，赞助的金额
	      	全部会被用来进行服务器及域名的购买，续费以及维护完善。如有侵权行为请联系QQ1459892910删除，谢谢！
	      	温馨提示：由于本站暂不支持支付功能，各位赞助后可联系QQ1459892910，说明情况后会收到文件的下载链接，
	      	带来的不便还请见谅！如有问题也可前往http://www.zy52113.com/toast.jsp吐槽社区
	      	吐槽说明!
			
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">553影院源代码系统</a></h2>              
	              <p>站长历经数月时间倾心编制的一个基于JavaWeb的个人视频网站，支持各大型视频网站的大部分功能，包括但不限于：
	              	注册，登录，发表评论，网站吐槽，搜索视频，记录播放记录，用户足迹，及搜索记录...，赞助后可以
	              	学习使用本源代码，但不可出售此资源！</p>
		     	  <p>初步阶段，下载此<font color="red">资源</font>文件只需赞助本站15元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://www.zy52113.com" target="_blank"><img src="../images/share/cinema.png"></a></div>
	        </li>
			
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">553Music源代码系统</a></h2>              
	              <p>站长倾心编制的一个基于JavaWeb后端的个人音乐网站，音乐资源为站长手动录入的个人比较喜欢的数百首歌曲，
	              	里面包括歌词的ajax异步加载以及支持单句歌词在指定时间显示的算法。支持键盘快捷键切换或快进歌曲。具体效果
	              	可单击右图体验网页版效果。赞助后可以学习使用本源代码，但不可出售此资源！</p>
		     	  <p>初步阶段，下载此<font color="red">资源</font>文件只需赞助本站10元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://www.zy52113.com/Minimusic" target="_blank"><img src="../images/share/music.png"></a></div>
	        </li>
			
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">近百本词汇电子表格书</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的近百本词汇书，包括初中牛津版六本，人教版五本，仁爱版六本；
	              	高中牛津版六本，人教版七本；大学英语六册，以及四级六级考研等等词汇单词书。其中内容包括英语汉语音标
	              	及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站15元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com" target="_blank"><img src="../images/share/every.png"></a></div>
	        </li>
			
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">五本词汇电子表格书</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的五本词汇书，包括下面五本书(共<font color="red">18237</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站3元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/all.png"></a></div>
	        </li>
	        
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">四级必备词汇</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的四级词汇(共<font color="red">4509</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站一元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/CET-4.png"></a></div>
	        </li>
	        
	        <li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">六级必备词汇</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的六级词汇(共<font color="red">2087</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站一元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/CET-6.png"></a></div>
	        </li>
	
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">考研必备词汇</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的考研词汇(共<font color="red">5475</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站一元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/graduate.png"></a></div>
	        </li>
	        
			<li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">计算机常用词汇</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的计算机常用词汇(共<font color="red">3820</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站一元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/computer.png"></a></div>
	        </li>
	        
	        <li>
	          <div class="dttext f_l">
	            <ul>
	              <h2><a href="/sponsor/showSponsor.jsp">金融常用英语词汇</a></h2>              
	              <p>爬虫算法爬取的互联网上的知名网站的金融常用词汇(共<font color="red">2346</font>个词汇)，包括英语汉语音标及发音资源路径，下载下来可以作为开发软件网站的数据支持，也可以作为背单词的文档。但不可出售此资源！</p>
		     	  <p>下载此<font color="red">资源</font>文件只需赞助本站一元，感谢大家的理解和支持！</p>
	              <span>2018-12-12</span>
	            </ul>
	          </div>
	          <div class="xwpic f_r"><a href="http://eng.zy52113.com/" target="_blank"><img src="../images/share/finance.png"></a></div>
	        </li> 
	        
	      </div>
	      <div class="pagelist"></div>
	    </div>
	  </div>
	  <div class="blank"></div>
	  <!-- container代码 结束 -->
	</div>
  <%@include file="../footer.jsp" %>

</body>
</html>