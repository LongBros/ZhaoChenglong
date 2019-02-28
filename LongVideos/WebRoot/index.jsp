<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro影院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro影院,好看的都在这里,免广告看视频就来Long Bro影院">
	<meta http-equiv="description" content="This is my page">
   <link rel="shortcut icon" href="/LongVideos/images/logo2.png" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="css/index.css">
	<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?7967a6213a2050176eff0e952c6348c1";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
	
  </head>
  
  <body>
  <br><br>
  <div class="form">
     <form action="/LongVideos/Search" method="post" target="ifr">
         <input type="text" placeholder="尽情搜吧" name="v_name">
         <input type="submit" value="搜搜">
     </form>
  </div>
       <%
          String p=request.getParameter("pageId");
          if("1".equals(p)){
             p="1";
          }else if("2".equals(p)){
             p="2";
          }else if("3".equals(p)){
             p="3";
          }else if("4".equals(p)){
             p="4";
          }else if("5".equals(p)){
             p="5";
          }else if("6".equals(p)){
             p="6";
          }else if("7".equals(p)){
             p="7";
          }else if("8".equals(p)){
             p="8";
          }else if("9".equals(p)){
             p="9";
          }else if("10".equals(p)){
             p="10";
          }else if("11".equals(p)){
             p="11";
          }else if("12".equals(p)){
             p="12";
          }else if("13".equals(p)){
             p="13";
          }else if("14".equals(p)){
             p="14";
          }else if("15".equals(p)){
             p="15";
          }else if("16".equals(p)){
             p="16";
          }else if("17".equals(p)){
             p="17";
          }else if("18".equals(p)){
             p="18";
          }else if("19".equals(p)){
             p="19";
          }else if("20".equals(p)){
             p="20";
          }else if("21".equals(p)){
             p="21";
          }else if("22".equals(p)){
             p="22";
          }else if("23".equals(p)){
             p="23";
          }else if("24".equals(p)){
             p="24";
          }else{
             p="1";
          }
        int cp=Integer.parseInt(p);
         %>
       <div class="tab" align="center">
            <a href="/LongVideos/SpideThree?cate=all&type=tvplay&pageId=<%=p %>" target="ifr">电视剧</a>
            <a href="/LongVideos/SpideThree?cate=all&type=movie&pageId=<%=p %>" target="ifr">电影</a>
            <a href="/LongVideos/SpideThree?cate=all&type=variety&pageId=<%=p %>" target="ifr">综艺</a>
            <a href="/LongVideos/SpideThree?cate=all&type=cartoon&pageId=<%=p %>" target="ifr">动漫</a>
            <a >轻松一刻</a>
            <a >排行榜</a>
       </div>
          <iframe src="/LongVideos/SpideThree?cate=all&type=movie" name="ifr" width="100%" height="100%;"></iframe>
       
       <div class="page" align="center">
       
       <%
          if(cp>1){
             %>
             <a href="/LongVideos/index.jsp?pageId=<%=cp-1%>">上</a>
             <%
          }
        %>
       
          <%
          if(cp>18){
              for(int j=19;j<=24;j++){
                String pageIndex="<a href=\"/LongVideos/index.jsp?pageId="+j+"\">"+j+"</a>";
                if(j==Integer.parseInt(p)){//当前页特殊显示
                    out.write("<current>"+pageIndex+"</current>");
                }else{
                    out.write(pageIndex);
                }
              }
          }else{
              for(int j=cp;j<=cp+5;j++){
                String pageIndex="<a href=\"/LongVideos/index.jsp?pageId="+j+"\">"+j+"</a>";
                if(j==Integer.parseInt(p)){//当前页特殊显示
                    out.write("<current>"+pageIndex+"</current>");
                }else{
                    out.write(pageIndex);
                }
              }
          }
          %>
         
         
         <%
          if(cp<24){
             %>
             <a href="/LongVideos/index.jsp?pageId=<%=cp+1%>">下</a>
             <%
          }
        %>
         
       </div>
       
   <%@include file="footer.jsp" %>
  </body>
</html>
