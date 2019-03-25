<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//获取页码
String scp=request.getParameter("page");
System.out.println(scp);		

int cp=1;
 //将所有的页码添加至数组链表
ArrayList<String> arr=new ArrayList<String>();
for(int i=1;i<10;i++){
	String t=i+"";
	arr.add(t);
}
//实现直接进入为第一页，点击页码进入，为页码的
for(int i=0;i<arr.size();i++){
	if((arr.get(i)).equals(scp)){//如果有参数，且与数组中某元素相同，则将数组中该元素定为页码，转为整型
    	cp=Integer.parseInt(arr.get(i));
			break;//
	}
	if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的，不是点击页码进去的
		cp=1;
	}
}
//System.out.println(cp);		

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Long Bro博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Long Bro博客">
	<meta http-equiv="description" content="Long Bro博客">
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
	<link href="css/base.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
</head>
<body>
<div id="wrapper">

    <%@include file="header.jsp" %>
   
<div class="container"><!-- index.css中定义的 -->
  <div class="con_content"><!--  main.css中定义 -->
    <div class="about_box"><!--  main.css中定义 -->
      <h2 class="nh1"><span>您现在的位置是：<a href="/LongBlog">关于我们</a>>><a href="/LongBlog/listpic.jsp" target="_blank">个人相册</a></span><b>个人相册</b></h2>
      
      <div class="lispic"><!-- main.css中定义 -->
        <ul>
          <%       //(cp-1)*16        16*cp
             for(int i=(cp-1)*16;i<16*cp&&i<OtherUtil.words.length;i++){
                 out.println("<li><a href=\"/LongBlog/images/yinger/love"+i+".jpg\"><img src=\"images/yinger/love"+i+".jpg\"><span>"+OtherUtil.loveSen(i)+"</span></a></li>");
             }
             for(int i=0;i<4;i++){
                 out.println("<li><a href=\"/LongBlog/images/me/me"+i+".jpeg\"><img src=\"images/me/me"+i+".jpeg\"><span>LongBro</span></a></li>");
             }
          %>
        </ul>
      </div><!-- listpic代码 结束 -->
     </div><!-- about_box代码 结束 -->
  </div><!-- con_content代码 结束 -->
  
  <div class="blank"></div>
  <!-- container代码 结束 -->
</div>


<div class="pagelist">页次：<%=cp%>/2 每页20张 共<%=OtherUtil.words.length+4*2 %>张图
  <%
    if(cp>1){
       %>
         <a href="/LongBlog/listpic.jsp?page=<%=cp-1%>"><<<</a>
       <%
    }
   %>


  <%
   if(cp<2){
      %>
          <a href="/LongBlog/listpic.jsp?page=<%=cp+1%>">>>></a>
      <%
   }
    %>
    </div>
  <%@include file="footer.jsp" %>

</body>
</html>