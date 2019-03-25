<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="dao.UtilDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@page import="utils.Movie"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
String type=request.getParameter("type");
String url=request.getParameter("url");//视频详细信息链接
String time=SqlUtil.time();
//从cookie中获取用户名
Cookie[] cookies=request.getCookies();
String nick="";
for(int i=0;cookies!=null&&i<cookies.length;i++){
	if(cookies[i].getName().equals("user")){
		nick=cookies[i].getValue().toString();
		nick=URLDecoder.decode(nick, "utf-8");
	}
}
Connection con;
Statement st;
ResultSet rs;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>正在播放</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="来Long Bro影院，免费免广告观看最新，最全，最受欢迎的影视。">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="css/player.css">
	<script src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
  </head>
  <body>
         <div class="top">
         	<span onclick="marquee()" id="btn">开启弹幕</span>
         	 <div>
	         	<div class="marquee" id="marquee" style="display:none;background-color: green"><!-- 弹幕 -->
			       <%//加载该视频下的所有评论弹幕-开始
			       try {
						Class.forName(SqlUtil.driver);
						con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
						st=con.createStatement();
						//查询该视频下的所有评论
						String sql1="select * from comment where c_Video='"+url+"'";
						rs=st.executeQuery(sql1);
						int i=1;
						out.write("<marquee width='68%' scrolldelay='500' scrollamount='50'>");
						while(rs.next()){
							int c_Id=rs.getInt("c_Id");
							String c_User=rs.getString("c_User");
							String c_Time=rs.getString("c_Time");
							String c_Content=rs.getString("c_Content");
							c_Content=c_Content.replaceAll("【", "<img alt='' style='width:30px;height:30px'  src='/images/aodamiao/");
							c_Content=c_Content.replaceAll("】", ".gif'>");
							c_Content=c_Content.replaceAll("《", "<img alt='' src='/images/huang/");
							c_Content=c_Content.replaceAll("》", ".gif'>");
							%>
			       		 	<font color="white" size="+2"><%=c_Content%></font>&emsp;
							<%
							if(i%3==0){//每三条评论为一个marquee
								out.write("</marquee><marquee  width='68%'>");
							}
							i++;
						}
						out.write("</marquee>");
					}catch(Exception e){
						
					}
	       		%>
	      	</div>
         	<iframe id="frame" src='' class="player" width=66% height="550px" align="left"></iframe><br>
         </div>
         <!-- http://beaacc.com/api.php?url=-->
         <!-- http://player.jidiaose.com/supapi/iframe.php?v= -->
         <!-- http://y.mt2t.com/lines?url= -->
         <!-- http://www.82190555.com/index/qqvod.php?url= -->
         <!-- http://app.baiyug.cn:2019/vip/?url= -->
         <!-- 关闭与显示弹幕，电视剧和动漫切换剧集，电影点击播放，综艺切换期别，异步收藏视频，异步加载及发送评论，显示和隐藏小表情及赋值 -->
         <script type="text/javascript" src="/js/player.js"></script>
         <script type="text/javascript">
         	var ji="";//此处不定义也不出错
         	//电视剧和动漫切换剧集
			function tab(url,i,len) {//当前的播放链接，当前播放剧集，总剧集
				document.getElementById("frame").src="http://app.baiyug.cn:2019/vip/?url="+url;
				for(j=1;j<len;j++){
					if(j==i){
						document.getElementById("serial"+j).style.background="gray";
					}else{
						document.getElementById("serial"+j).style.background="white";
					}
				}
				ji="第"+i+"集";
				changeTitle();//改变网页title
			}
			//电影点击播放
			function tabMovie(url) {//电影的播放链接
				document.getElementById("frame").src="http://app.baiyug.cn:2019/vip/?url="+url;
				document.getElementById("movie").style.background="gray";
				changeTitle();//改变网页title
			}
			//综艺切换期别
			function tabZongyi(url,i,stage,len) {//播放链接，序号，期别名，总期数
				document.getElementById("frame").src="http://app.baiyug.cn:2019/vip/?url="+url;
				for(j=1;j<len;j++){
					if(j==i){
						document.getElementById("serial"+j).style.background="gray";
					}else{
						document.getElementById("serial"+j).style.background="white";
					}
				}
				ji=stage;
				changeTitle();//改变网页title
			}
       		//进入该页面后自动执行加载评论函数（在body里面执行一直不行<body onload="loadComs()">）
       	    window.onload=loadComs("<%=url%>");//根据视频链接加载其对应评论，故传入链接参数 
         </script>
      <%
           String title=null;
      	   String imgP="";//保存至播放记录表和收藏表的图片的路径
      	   String des="";
           //电视和动漫的区别在于:前者有评分，后者无;前者为演员，后者为人物
		   if(type.equals("dianshi")||type.equals("dongman")){
			     Document doc=Jsoup.connect(url).get();
			     //爬取影视图片路径，需注意，部分图片后缀非.jpg，故不用jpg作为截取标识字段
			     Elements s1=doc.getElementsByClass("s-cover-img");
			     imgP=s1.toString();
			     if(!imgP.contains("ico-pay")){
			        imgP=imgP.substring(imgP.indexOf("src")+5,imgP.indexOf("</a>")-3);
			     }else{
				    imgP=imgP.substring(imgP.indexOf("src")+5,imgP.indexOf("\"> <i class"));
			     }
			     Elements s;//剧集所在HTML元素
			     if(type.equals("dianshi")){
				     s=doc.getElementsByClass("s-top-list-ji");
			     }else{
				     s=doc.getElementsByClass("site-wrap");
			     }
			     String ss=s.toString();
			     if(ss.contains("展开更多")){
				     ss=ss.substring(ss.indexOf("展开更多"));
			     }
			     String se="";

		    	 if(ss.contains("series-slice-view")){//集数较多，折叠的
			        //可能有很多页个50集，需将其分离再只将剧集的链接合并一起
			        String m[]=ss.split("div class=\"series-slice-view g-clear js-series");
		    	 	String e="";
				    for(int i=1;i<m.length;i++){
						if(i==1){//第一个50集
					        e=m[i].substring(4,m[i].indexOf("收起</a>")-66);
						}else if(i==m.length-1){
					        e=m[i].substring(m[i].indexOf("display:none")+15);
						}else{//最后一个50集
					        e=m[i].substring(m[i].indexOf("display:none")+15,m[i].indexOf("收起</a>")-66);
						}
				        se=se+e;
				    }
			     }else if(ss.contains("<i class=\"ico-yugao\">")){//有预告片的
				     se=ss.substring(ss.indexOf("js-tab")+14,ss.indexOf("<i class=\"ico-yugao\">"));
			     }else{//无预告片且未折叠，有预告片且折叠
				    se=ss.substring(ss.indexOf("js-tab")+14);
			     }

			     Elements info;//视频详细信息所在HTML元素
			     if(type.equals("dianshi")){
				     info=doc.getElementsByClass("s-top-info"); 
			     }else{
				     info=doc.getElementsByClass("m-top-info");
			     }
			     String sinfo=info.toString();	
			     //视频名	     
			     title=sinfo.substring(sinfo.indexOf("<h1>")+4,sinfo.indexOf("</h1>"));
			    
			     %>
			     <div class='episode'>
			     <br><h2 class='title'><%=title%></h2>
			     <button onclick="sc('<%=nick%>','<%=title%>','<%=url%>','<%=type%>','<%=imgP%>')">收藏</button>
			     <%
			     //更新或已完结情况
			     String update=sinfo.substring(sinfo.indexOf("<p class=\"tag\">")+15,sinfo.indexOf("<div id=\"js-desc-switch\""));;//更新情况
		         update=update.substring(0,update.indexOf("</p>"));
		         out.println(update+"<br>");
	 			 Element s8=doc.getElementById("js-desc-switch");			   
		       	 String[] r=s8.toString().split("<span>");
				 //类型
				 String style=r[1].substring(11, r[1].indexOf("</p>"));
				 //地区
				 String area=r[3].substring(11,r[3].indexOf("</p>"));
				 //导演
				 String lD=r[4].substring(r[4].indexOf("href")+6, r[4].indexOf("\">"));
				 String director=r[4].substring(r[4].indexOf("\">")+2, r[4].indexOf("</a>"));
				 //演员
			     String as[]=r[5].split("&nbsp;");
			     if(type.equals("dianshi")){
					 String year=r[2].substring(11,r[2].indexOf("</p>"));
			         String score=sinfo.substring(sinfo.indexOf("class=\"s\">")+10,sinfo.indexOf("class=\"s\">")+13);
			       	 out.println("<br>评分:<des>"+score+"</des><br>类型:<des>"+style+"</des><br>年份:<des>"+year+"</des><br>地区:<des>"+area+"</des><br>导演:<des>"+director+"</des><br>演员:");
			     }else{
					 String year=r[2].substring(10,r[2].indexOf("</p>"));
			         out.println("<br>类型:<des>"+style+"</des><br>年份:<des>"+year+"</des><br>地区:<des>"+area+"</des><br>导演:<des>"+director+"</des><br>人物:");
			     }
			     for(int i=0;i<as.length-1;i++){
			         String href=as[i].substring(as[i].indexOf("href")+6,as[i].indexOf("\">"));
			         String name=as[i].substring(as[i].indexOf("\">")+2,as[i].indexOf("</a>"));
			         out.println("<other><a href='"+href+"' target='_blank'><des>"+name+"</des></a></other>");
			     }
		         out.write("<br><hr width='100%' height='5px'>");		     
			     //剧集
			     String[] hs=se.split("href=\"");
			     String u="";
			     int end=0;
			     if(type.equals("dianshi")){
			    	 end=hs.length-1;
			     }else{
			    	 end=hs.length;
			     }
	      		for(int i=1;i<end;i++){
				   System.out.println("------------");
				   u=hs[i].substring(0,hs[i].indexOf("\">"));
			       %>
			       <noc><span style="background: white;" id="serial<%=i%>" onclick="tab('<%=u%>','<%=i%>','<%=end%>')"><%=i%></span></noc>&emsp;
			       <%
				   //每行3集
				   if(i%3==0){
				      out.write("<br>");
				   }
	   			}
			    //简介
			    des=sinfo.substring(sinfo.indexOf("wrap\" style=\"display:none;\"")+74,sinfo.indexOf("<a href=\"#\" class=\"js-close btn\">"));
		 	}
         	else if(type.equals("dianying")||type.equals("zongyi")){
				   Document doc=Jsoup.connect(url).get();
				   //爬取影视图片路径，需注意，部分图片后缀非.jpg
				   Elements s1=doc.getElementsByClass("s-cover-img");
				   imgP=s1.toString();
				   if(!imgP.contains("ico-pay")){
				       imgP=imgP.substring(imgP.indexOf("src")+5,imgP.indexOf("</a>")-3);
				   }else{//收费电影
					   imgP=imgP.substring(imgP.indexOf("src")+5,imgP.indexOf("\"> <i class"));
				   }
				   Elements s=doc.getElementsByClass("top-info");//电影
				   String ss=s.toString();
				   out.write("<div class='zongyi'>");
				   //片名
				   title=ss.substring(ss.indexOf("<h1>")+4,ss.indexOf("</h1>"));
				   if(type.equals("dianying")){
				      //评分
				      String score=ss.substring(ss.indexOf("<span class=\"s\">")+16,ss.indexOf("</span>"));
				      if(score.length()>6){
				         	%>
				         	<br><h2 class='title'><%=title%></h2>
				         	<button onclick="sc('<%=nick%>','<%=title%>','<%=url%>','<%=type%>','<%=imgP%>')">收藏</button><br>
				         	<%
				      }else{
				    	  %>
				         	<br><h2 class='title'><%=title%></h2>
				         	<button onclick="sc('<%=nick%>','<%=title%>','<%=url%>','<%=type%>','<%=imgP%>')">收藏</button><br>
				         <%				   
				      }
				     Element s8=doc.getElementById("js-desc-switch");
				     String[] r=s8.toString().split("<span>");		
				     //年份
				     String year=r[1].substring(11, r[1].indexOf("</p>"));
				     //地区
				     String area=r[2].substring(11,r[2].indexOf("</p>"));
				     //原网站中导演和演员被换位置
				     //演员
				     String actor=r[4].substring(10,r[4].indexOf("</p>"));
				     String as[]=actor.split("&nbsp;");
				     //导演
				     String lD=r[3].substring(r[3].indexOf("href")+6, r[3].indexOf("\">"));
				     String director=r[3].substring(r[3].indexOf("\">")+2, r[3].indexOf("</a>"));
				     //System.out.println(lD+director);
				     out.println("年份:<des>"+year+"</des><br>地区:<des>"+area+"</des><br>导演:<des>"+director+"</des><br>演员:");
				     for(int i=0;i<as.length-1;i++){
				         String href=as[i].substring(as[i].indexOf("href")+6,as[i].indexOf("\">"));
				         String name=as[i].substring(as[i].indexOf("\">")+2,as[i].indexOf("</a>"));
				         out.println("<des>"+name+"</des>");
				     }
				     String pu=Movie.getPUrl(url);//根据视频的链接获取播放链接
				     %>
				     <br><br><noc><span id="movie" onclick="tabMovie('<%=pu%>')">点此播放</span>
				     <%
				 }else if(type.equals("zongyi")){
				   		%>
			         	<br><h2 class='title'><%=title%></h2>
			         	<button onclick="sc('<%=nick%>','<%=title%>','<%=url%>','<%=type%>','<%=imgP%>')">收藏</button><br>
			         	<%
				   		//爬取综艺节目的详细信息
				        Element s8=doc.getElementById("js-desc-switch");
				        String[] r=s8.toString().split("<span>");
				        //类型
				        //String st=r[0].substring(r[0].indexOf("_blank")+8, r[0].indexOf("</p>")-5);
				        //年份
				       String year=r[1].substring(11, r[1].indexOf("</p>"));
				       //地区
				       String area=r[2].substring(11,r[2].indexOf("</p>"));
				       //播出电台频道channel
				       String channel=r[3].substring(11, r[3].indexOf("</p>"));
				        out.println("<br>类型：<des></des><br>年份:<des>"+year+"</des><br>地区:<des>"+area+"</des><br>频道：<br>主持:");
				       //主持
				       String host=r[6].substring(10, r[6].indexOf("</p>"));
				       String []hs=host.split("</a>");
				       for(int i=0;i<hs.length-1;i++){
				           String hh=hs[i].substring(hs[i].indexOf("href")+6,hs[i].indexOf("\">"));
				           String hn=hs[i].substring(hs[i].indexOf("\">")+2);//主持人链接
				           out.println("<des>"+hn+"</des>");
				       }
				       out.write("<br><hr width='100%' height='5px'>");
				        //爬取剧集
				        Elements es=doc.getElementsByClass("juji-main-wrap");
				        String ses=es.toString();
				        String[] s9=ses.split("<li title");
				        int len=s9.length;
				        for(int i=1;i<len;i++){
					         //期别
					         String stage=s9[i].substring(2,s9[i].indexOf("\" class"));
					         //链接
					         String pu=s9[i].substring(s9[i].indexOf("<a href=\"")+9,s9[i].indexOf("\" data-daochu"));
					         //该期的img
					         //System.out.println(s9[i].substring(s9[i].indexOf("data-src=\"")+10,s9[i].indexOf("\" alt=")));
					         //期别日期
					         String date=s9[i].substring(s9[i].indexOf("<span class=\"w-newfigure-hint\">")+31,s9[i].indexOf("</span>"));
					         %>
						     <noc><span id="serial<%=i%>" onclick="tabZongyi('<%=pu%>','<%=i%>','<%=stage%>','<%=len%>')"><%=date%></span></noc><br>
						     <%
				        }
				  }
				   //简介
				   des=ss.substring(ss.indexOf("wrap\" style=\"display:none;\"")+74,ss.indexOf("<a href=\"#\" class=\"js-close btn\">"));
        	 }		 
           %>
      	   </div><!-- episode或zongyi -->
      	   
           </div><!-- top -->
           <br>简介:<span><%=des %></span>
           <%
			//添加播放记录
			if(!nick.equals("")){
				String quSql="select * from precord where p_Player='"+nick+"' and p_Link='"+url+"'";
				String upSql="update precord set p_Time='"+time+"' where p_Player='"+nick+"' and p_Link='"+url+"'";
				String addSql="insert into precord(p_Player,p_Time,p_Name,p_Path,p_Type,p_Link) values('"+nick+"','"+time+"','"+title+"','"+imgP+"','"+type+"','"+url+"')";
 				UtilDao.addPRecord(quSql, upSql,addSql);
			}
			//加载播放过该视频的用户以及评论
       %>
       <br><br><br>
                看过该视频的人：<br>
       <% 
         int n=UtilDao.getNum("select count(*) from precord where p_Link='"+url+"'");
       	 out.write("<font color='red'>"+n+"</font>位小伙伴看过该视频<br>");	 
       try{
       		Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			//查询看过该视频的其他用户
			String sql="select * from precord where p_Link='"+url+"'";
			rs=st.executeQuery(sql);
			while(rs.next()){
				String user=rs.getString(2);
				String time1=rs.getString(3);
				out.write("<font color='red'>"+user+"</font>在<font color='red'>"+time1+"</font>看过<br>");
			}
       	 }catch(Exception e){
       		 
       	 }
       %>
       <span id="hour">00</span>:<span id="minute">00</span>:<span id="second">00</span>
       <!-- 用到以下js中的计时器函数 -->
       <script type="text/javascript" src="/js/index.js"></script>
       <script type="text/javascript">
	        //得到cookie中的值
	   		var co;
			if(document.cookie!=null){
				//alert(document.cookie);
				//alert(document.cookie.indexOf("timecount")+9);
				co=document.cookie.substring(document.cookie.indexOf("timecount")+10);
				
				//alert(co.substring(0, 2));
				//alert(co.substring(2, 4));
				//alert(co.substring(4, 6));
			}
			var h=document.getElementById("hour");
	        var m=document.getElementById("minute");
	        var s=document.getElementById("second");
      	
	   		h.innerText=co.substring(0, 2);
	   		m.innerText=co.substring(2, 4);
	   		s.innerText=co.substring(4, 6);
	   		window.setInterval("ti()", 1000);
	   		
	   		//var ji="";//此处不定义也不出错
       		//进入该页面后自动执行加载评论函数（在body里面执行一直不行<body onload="loadComs()">）
       	    window.onload=loadComs("<%=url%>");//根据视频链接加载其对应评论，故传入链接参数 
     </script>
       
       <!-- 实现评论功能-开始 -->
       <form name="sendcomment">
       		<textarea rows="5" id="comme" cols="170" name="comment" placeholder="一起评论吧"></textarea>
	       <br><img alt="嗷大喵表情" title="嗷大喵表情" style="width: 18px;height: 18px" src="/images/util/face-2x.png" onmousedown="imgAo()">
	       <img alt="小黄脸表情" title="小黄脸表情" style="width: 18px;height: 18px" src="/images/huang/wx.gif" onmouseover="closeImgHu()" onmousedown="openImgHu()">
	       <img alt="" style="width: 18px;height: 18px" src="/images/util/image-2x.png" onclick="openFile()">
	       	<font size="-2" color="gray">鼠标放上显示表情包，点击隐藏表情包</font>
	       <!-- 将视频主页链接以及发表评论者一并通过表单传递以存入评论表中，style的display设为none -->
	       <input type="button" value="发表" onclick="sendCom('<%=url%>','<%=nick%>')" class="fabiao">
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
       <div id="huang" class="ao" style="display: none;">
	       <%
	       		String hu[]={"wx","pz","se","fd","dy","ll","hx","bz","shui","dk","gg","fn"
	    		   ,"tp","cy","jy","ng","ku","lh","zk","tu","tx","ka","by","am"
	    		   ,"jie","kun","jk","liuh","hanx","db","fend","zm","yw","xu","yun","zm"
	    		   ,"shuai","kl","qiao","zj","ch","kb","gz","qd","huaix","zhh","yhh","hq"
	    		   };
	       		for(int i=0;i<hu.length;i++){
	       			if(i%12==0){
	       				out.write("<br>");
	       			}
	       			%>
	       			<img alt="" src="/images/huang/<%=hu[i]%>.gif" onclick="appendValueHu('<%=hu[i]%>')">
	       			<%
	       		}
	       %>
       </div>
        <!-- 实现评论功能-结束 -->
        <div id="allComs"></div>
       <hr width='95%' height='5px'>
       <%@include file="footer.jsp" %>
       <script>
           function changeTitle(){
              document.title="<%=title%>-"+ji+"-LongBro影院";
           }   
           window.onload=changeTitle;   
       </script>
  </body>
</html>
