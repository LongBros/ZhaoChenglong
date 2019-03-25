<%@page import="dao.ViewConDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//增加一次受访记录，并查询页面受访次数
//增加一次受访记录
ViewConDao vDao=new ViewConDao();
vDao.addVisit(request,2);
//查询页面受访次数
int count=vDao.getVisitNum(2);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>干货街-Long Bro的博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro的博客">
	<meta http-equiv="description" content="Long Bro的博客">
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
	<link href="css/base.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
</head>
<body>
<div id="wrapper">
     <%@include file="header.jsp" %>

<div class="container">
  <div class="con_content">
    <div class="about_box">
      <h2 class="nh1"><span>您现在的位置是：<a href="/" target="_blank">网站首页</a>>><a href="#" target="_blank">干货街</a></span><b>无偿分享</b></h2>
      
      <div class="f_box">
	          <p class="a_title">无偿分享</p>
	          <p class="p_title"></p>
	            <p class="box_p"><span>发布时间：2018-12-12 15:12:42</span><span>作者：赵成龙</span><span>来源：LongBro博客</span><span>点击：<%=count%></span></p>
	          <!-- 可用于内容模板 --> 
	        </div>
      
      <div class="dtxw box">
	<li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/Minimusic">迷你音乐</a></h2>              
              <p>个人做的一个音乐网页，支持音乐播放的基本所有功能，与键盘快捷键相连</p>
	     	  <p>上一曲（左键），下一曲（右键），音量加（上键），音量减（下键），播放/暂停（P键），模式切换（C键）</p>
              <span>2018-9-26</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/share/music.png"></a></div>
        </li>

        <li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="/share/poi-3.11-20141221.jar">Java输出表格所用jar包</a></h2>              
              <p>如果你在做Java项目时，有输出xls表格的需求，那么Apache公司的这个jar包会对你很有帮助，分享在此，欢迎下载！</p>
              <p>使用示例详见<a href="http://www.longqcloud.cn/detailblog.jsp?id=53">java将数据导出至表格文件</a>！</p>
              <span>2018-9-26</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/upload/00531.jpg"></a></div>
        </li>

	<li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="http://z.weiwee.com/Register.aspx?inviter=LongBro">号外微信托管赚钱</a></h2>              
              <p>一个让你可以利用微信闲着赚钱的平台，平台使用你的微信小号干活获取收益，您将获得分成，托管之后您不需要做任何操作，一切都由平台自动完成，您只需静静等待，坐等赚钱即可，不影响你的使用！</p>
              <p>您的小号仅用于网赚，如：刷阅读，刷公众号关注等，绝不从事任何非法活动。请放心托管！</p>
              <span>2018-9-21</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/share/haowai.png"></a></div>
        </li>
	<li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="http://www.zy52113.com">单词大师网页版</a></h2>              
              <p>还在为记不住英语单词而发愁吗？还在因单词的原因而频频在四级考试中失利而苦恼吗？不妨来这里看看，在游戏中记单词，就它了！</p>
              <p>仿微信小程序“单词大师”，开发自扇贝。仿的像不像，一看便知！</p>
              <span>2018-9-14</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/upload/00500.jpg"></a></div>
        </li>
	<li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="http://www.longqcloud.cn/LongNovel/SpiderPro">中国-省市县镇村及其代码</a></h2>
              <p>想知道中国的哪个省有哪些市，哪个市有哪些县，哪些县有哪些镇，哪些镇有哪些村？不妨来这里看看。算一算你的身份证号前六位。</p>
              <span>2018-7-11</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/share/china.png"></a></div>
        </li>        
	<li>
          <div class="dttext f_l">
            <ul>
              <h2><a href="https://pan.baidu.com/s/1fh6qdeya1qz-kumyX-YuuQ">Android超级终端——远程连接必备神器</a></h2>              
              <p>Android超级终端，是一款手机连接远程服务器的优秀软件，通过它，你可以很方便的操作远程文件以及数据库--------密码:9b3n</p>
              <span>2018-7-9</span>
            </ul>
          </div>
          <div class="xwpic f_r"><a href="/"><img src="images/02.jpg"></a></div>
        </li>
        
      </div>
      <div class="pagelist">页次：1/1 每页25 总数10<a href="/">首页</a><a href="/">上一页</a><a href="/">下一页</a><a href="/">尾页</a></div>
    </div>
  </div>
  <div class="blank"></div>
  <!-- container代码 结束 -->
</div>
  <%@include file="footer.jsp" %>

</body>
</html>