<%@page import="dao.UtilDao"%>
<%@page import="utils.SqlUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Best Wishes to You!</title>
</head>
<style type="text/css">
    body{
		background:url(../ying/cat.png);
	}
	.content{
		margin-left:50px;
		margin-top:88px;
	}
	.content img{
		width:50px;
		height:50px;
	}
	.content .from{
		float: right;
		margin-right: 88px;
		margin-bottom:88px;
		font-size: 40px;
	}
	.bottom{
		margin-top: 255px;
	}
</style>

<body>
	<%
		String ip=SqlUtil.getIp(request);
		String time=SqlUtil.time();//当前时间
			String sql="insert into loginlog (l_User,l_Ip,l_Time) values"
			+"('Best Wishes Page','"+ip+"','"+time+"')";
			UtilDao.insertData(sql);
		
	 %>
	<div class="content">
	<div>赵盈:&emsp;<img src="http://img.zy52113.com/video/bbt.gif"></div><br><br>	
	&emsp;&emsp;犹豫了很久，还是决定把这封信发给你，如果又打扰到你了，真的很抱歉。我真的是犹豫了很久<img src="http://img.zy52113.com/video/bbt.gif"><br><br>
	&emsp;&emsp;最近发工资了，挺开心的，这是我工作以来第一个月发的工资。然后就想把我的这份喜悦分享给你，或许你也会替我开心吧<img src="http://img.zy52113.com/video/mg.gif"><br><br>
	&emsp;&emsp;原本是想给你寄点东西去的，但现在已经不太合适了，或许也会引起不必要的误会，所以就以转账(红包)的形式来表达吧<br><br>
	&emsp;&emsp;我现在挺好的，虽然每天都过着朝九晚六的上班生活，不过庆幸的是做的是自己喜欢的工作，还是双休<br><br>
	&emsp;&emsp;只是偶尔...<br><br>
	&emsp;&emsp;虽然不知道现在的你怎么样，过得好不好，但是真心的希望也祝愿你能够过得幸福快乐<br><br>
	&emsp;&emsp;或许以后再也不会相见了吧，<br><br>
	&emsp;&emsp;但倘若有缘再相见，希望我们还能真心的笑着对彼此说一句：好久不见 <br><br>
	&emsp;&emsp;爱情里没有谁对谁错，走不到最后，也许是命里注定的。<br><br>
	&emsp;&emsp;我们都曾失望过，也曾彷徨过，伤心过，虽然我们的这段故事以悲伤结局，但我们曾真心相爱过，真心付出过，这一点就足以让我永远也不会恨你，我想你也不会恨我吧。<br><br>
	&emsp;&emsp;如今的我只想感谢你曾经来过，感谢你曾经的真心付出，还有就是如果需要的话，我会致以我最真诚的祝愿，祝你们幸福<br><br>
	<div class="from">
		赵成龙<br><br>
		2019-03-16
	</div>
	</div>
	<div class="bottom">
		<center><p>All Rights Reserved 版权所有：<a href="http://www.longqcloud.cn/" target="_blank">Long Blog</a> 备案号：<a href="http://www.miitbeian.gov.cn" target="_blank">豫ICP备16023798号-1 </a></p></center> 
		<center><p>Designed by Long Bro</p></center>
	</div>
	
</body>
</html>