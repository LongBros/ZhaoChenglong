<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pageId=request.getParameter("pageId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro影院_小酷欢迎你</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/index.css">

  </head>
  <script type="text/javascript">
      var index=document.searchVideo.name.value;
      var xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
      var url="SearchYouku?name="+index;
      xmlHttp.open("post",url,true);
      xmlHttp.onreadystatechange=function(){
      xmlHttp.send();
      }
  </script>
  <body>
      <div class="form">
         <form name="searchVideo">
            <input type="text" name="name">
            <input type="button" value="搜索" onclick="search()">
         </form>
      </div>
      
  
    <%
       if("2".equals(pageId)){
           pageId="2";
       }else if("3".equals(pageId)){
           pageId="3";
       }else if("4".equals(pageId)){
           pageId="4";
       }else if("5".equals(pageId)){
           pageId="5";
       }else if("6".equals(pageId)){
           pageId="6";
       }else if("7".equals(pageId)){
           pageId="7";
       }else if("8".equals(pageId)){
           pageId="8";
       }else if("9".equals(pageId)){
           pageId="9";
       }else if("10".equals(pageId)){
           pageId="10";
       }else if("11".equals(pageId)){
           pageId="11";
       }else if("12".equals(pageId)){
           pageId="12";
       }else if("13".equals(pageId)){
           pageId="13";
       }else if("14".equals(pageId)){
           pageId="14";
       }else if("15".equals(pageId)){
           pageId="15";
       }else if("16".equals(pageId)){
           pageId="16";
       }else if("17".equals(pageId)){
           pageId="17";
       }else if("18".equals(pageId)){
           pageId="18";
       }else if("19".equals(pageId)){
           pageId="19";
       }else if("20".equals(pageId)){
           pageId="20";
       }else if("21".equals(pageId)){
           pageId="21";
       }else if("22".equals(pageId)){
           pageId="22";
       }else if("23".equals(pageId)){
           pageId="23";
       }else if("24".equals(pageId)){
           pageId="24";
       }else if("25".equals(pageId)){
           pageId="25";
       }else if("26".equals(pageId)){
           pageId="26";
       }else if("27".equals(pageId)){
           pageId="27";
       }else if("28".equals(pageId)){
           pageId="28";
       }else if("29".equals(pageId)){
           pageId="29";
       }else if("30".equals(pageId)){
           pageId="30";
       }else{
           pageId="1";
       }
       session.setAttribute("pageId", pageId);
     %>
     
    <iframe src="/LongVideos/SpideYouku?pageId=<%=pageId %>" width="100%" height="100%" class=""></iframe>
    
    <div class="page">
       <!-- <span class="prev"><button onclick="previous()">上一页</button></span>
       <span class="current"><a>1</a></span> -->
       <%
          for(int i=1;i<=30;i++){
            %><span><a href="/LongVideos/indexYouku.jsp?pageId=<%=i%>"><%=i %></a></span>
            
            <%    
          }
        %>
<!--        <span class="next"><button onclick="next()">下一页</button></span>
 -->    </div>
   <%--  <script type="text/javascript">
        function previous(){
            //获取当前页码
            var curpage='<%=session.getAttribute("pageId")%>';
            if(curpage=="1"){
               
            }else{//将当前页码减一，实现上一页功能
                var page=parseInt(curpage)-1;
                <%=session.setAttribute("pageId", page.toString())%>;
                <%response.sendRedirect("/LongVideos/indexYouku.jsp");%>
                
            }
        }
        function next(){
            //获取当前页码
            var curpage='<%=session.getAttribute("pageId")%>';
            if(curpage=="30"){
               
            }else{//将当前页码加一，实现下一页功能
                var page=parseInt(curpage)+1;
                <%=session.setAttribute("pageId", page.toString())%>;
                <%response.sendRedirect("/LongVideos/indexYouku.jsp");%>
            }        
        }
    </script> --%>
  </body>
</html>
