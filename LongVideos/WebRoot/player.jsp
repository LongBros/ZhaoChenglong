<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
String type=request.getParameter("type");
String hre=request.getParameter("href");//获取该视频的播放链接
String url=request.getParameter("url");
//System.out.println(url);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>正在播放</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/player.css">
    
  </head>
  
  <body>
         <iframe src='http://beaacc.com/api.php?url=<%=hre %>' class="player" width=66% height="550px" align="left"></iframe><br>
         <!-- http://api.baiyug.cn/vip/?url=-->
         <!-- http://player.jidiaose.com/supapi/iframe.php?v= -->
         <!-- http://y.mt2t.com/lines?url= -->
         <!-- http://www.82190555.com/index/qqvod.php?url= -->
      <%
      String title=null;
		   if(type.equals("dianshi")||type.equals("dongman")){
		     //System.out.println(type);
		     Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
		     Elements s=doc.getElementsByClass("s-top-list-ji");
		     String ss=s.toString();
		     //System.out.println(ss);
		     String se="";
		     if(ss.contains("display:none")){//集数较多，折叠的
			    se=s.toString().substring(ss.indexOf("display:none")+15,ss.indexOf("收起</a>")-63);

		     }else if(ss.contains("<i class=\"ico-yugao\">")){//有预告片的
			   
			    se=s.toString().substring(ss.indexOf("js-tab")+14,ss.indexOf("<i class=\"ico-yugao\">"));
		   }else{//无预告片且未折叠，有预告片且折叠
			    se=s.toString().substring(ss.indexOf("js-tab")+14);

		   }
		     String[] hs=se.split("href=\"");
		     String u="";
		     out.write("<div class='episode'>");
		     for(int i=1;i<hs.length;i++){
			   u=hs[i].substring(0,hs[i].indexOf("\">"));

			   out.write("<a href='/LongVideos/player.jsp?type="+type+"&url="+url+"&href="+u+"'>"+i+"</a>&emsp;");
			   //每行八集
			   if(i%8==0){
			      out.write("<br>");
			   }
		   }
		   out.write("</div><br>");
		   
		   Elements info=doc.getElementsByClass("s-top-info");
		   String sinfo=info.toString();
		    title=sinfo.substring(sinfo.indexOf("<h1>")+4,sinfo.indexOf("</h1>"));
			out.println("<br>正在播放:<em class='title'>"+title+"</em><br>");
		
		   if(type.equals("dianshi")){
			   
			       Element s8=doc.getElementById("js-desc-switch");
	       		   String[] r=s8.toString().split("<span>");
			    //类型
			   String style=r[1].substring(10, r[1].indexOf("</p>"));
			   //年份
			   String year=r[2].substring(10,r[2].indexOf("</p>"));
			   //地区
			   String area=r[3].substring(10,r[3].indexOf("</p>"));
			   //导演
			   String lD=r[4].substring(r[4].indexOf("href")+6, r[4].indexOf("\">"));
			   String director=r[4].substring(r[4].indexOf("\">")+2, r[4].indexOf("</a>"));
			   out.println("类型:<des>"+style+"</des>&nbsp;年份:<des>"+year+"</des>&nbsp;地区:<des>"+area+"</des>&nbsp;导演:<des>"+director+"</des><br><br>");
			   }  

		   //简介
		   String desc=sinfo.substring(sinfo.indexOf("<p class=\"item-desc js-close-wrap\" style=\"display:none;\">")+73,sinfo.indexOf("<a href=\"#\" class=\"js-close btn\">"));
		   out.println("简介:"+desc);
		 }
         else if(type.equals("dianying")||type.equals("zongyi")){
		   Document doc=Jsoup.connect(url).get();
		   Elements s=doc.getElementsByClass("top-info");//电影
		   String ss=s.toString();
		   out.write("<div class='zongyi'>");
		   
		   //片名
		    title=ss.substring(ss.indexOf("<h1>")+4,ss.indexOf("</h1>"));
		   
		   if(type.equals("dianying")){
		      //评分
		      String score=ss.substring(ss.indexOf("<span class=\"s\">")+16,ss.indexOf("</span>"));
		      if(score.length()>6){
		         	out.println("<br>正在播放:<em class='title'>"+title+"</em><br><br>");
		         
		      }else{
		         	 out.println("<br>正在播放:<em class='title'>"+title+"</em>&nbsp;"+score+"<br><br>");   
		      }
		      
		       Element s8=doc.getElementById("js-desc-switch");
		   String[] r=s8.toString().split("<span>");		
		  //年份
		   String year=r[1].substring(10, r[1].indexOf("</p>"));
		   //地区
		   String area=r[2].substring(10,r[2].indexOf("</p>"));
		   //演员
		   String actor=r[3].substring(10,r[3].indexOf("</p>"));
		   //导演
		   String lD=r[4].substring(r[4].indexOf("href")+6, r[4].indexOf("\">"));
		   String director=r[4].substring(r[4].indexOf("\">")+2, r[4].indexOf("</a>"));
		   out.println("年份:<des>"+year+"</des>&nbsp;地区:<des>"+area+"</des>&nbsp;导演:<des>"+director+"</des><br><br>");
		   
		   }else if(type.equals("zongyi")){
		   		out.println("<br>正在播放:<em class='title'>"+title+"</em><br><br>");
		       
		        //爬取综艺节目的详细信息
		        Element s8=doc.getElementById("js-desc-switch");
		        String[] r=s8.toString().split("<span>");
		        //类型
		        String st=r[1].substring(10, r[1].indexOf("</p>"));
		        //年份
		       String year=r[2].substring(10,r[2].indexOf("</p>"));
		       //地区
		       String area=r[3].substring(10, r[3].indexOf("</p>"));
		       //播出电台频道channel
		       String channel=r[4].substring(10, r[4].indexOf("</p>"));
		       //主持
		       String host=r[5].substring(10, r[5].indexOf("</p>"));
		       out.println("<br>类型：<des>"+st+"</des>&nbsp;年份:<des>"+year+"</des>&nbsp;地区:<des>"+area+"</des>&nbsp;频道：<des>"+channel+"</des>&nbsp;主持:<des>"+host+"</des><br><br>");
		       
		        //爬取剧集
		        Elements es=doc.getElementsByClass("juji-main-wrap");
		        String ses=es.toString();
		        String[] s9=ses.split("<li title");
		        for(int i=1;i<s9.length;i++){
			         //期别
			         String stage=s9[i].substring(2,s9[i].indexOf("\" class"));
			         //链接
			         String pu=s9[i].substring(s9[i].indexOf("<a href=\"")+9,s9[i].indexOf("\" data-daochu"));
			         //该期的img
			         //System.out.println(s9[i].substring(s9[i].indexOf("data-src=\"")+10,s9[i].indexOf("\" alt=")));
			         //期别日期
			         String date=s9[i].substring(s9[i].indexOf("<span class=\"w-newfigure-hint\">")+31,s9[i].indexOf("</span>"));

			         out.println("<a href='/LongVideos/player.jsp?type="+type+"&url="+url+"&href="+pu+"' title='"+stage+"'>"+date+"</a>");
		             if(i%4==0){
		                out.write("<br>");
		             }
		        }
		   }
		   
		   //简介
		   String desc=ss.substring(ss.indexOf("<p class=\"item-desc js-close-wrap\" style=\"display:none;\">")+73,ss.indexOf("<a href=\"#\" class=\"js-close btn\">"));
         	out.println("<br>简介:<span>"+desc+"</span>");
         	out.write("</div>");
         
         }
       %>
       <%@include file="footer.jsp" %>
  </body>
</html>
