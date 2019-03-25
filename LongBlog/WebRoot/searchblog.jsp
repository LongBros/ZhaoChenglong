<%@page import="beans.TimeNum"%>
<%@page import="beans.Blogs"%>
<%@page import="dao.BlogsDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>搜索结果|Long Bro博客</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="author" content="Long Bro">
	<meta name="keywords" content="Long Bro的博客,Long Bro,博客,Java学习聚集地，情感博客">
	<meta name="description" content="Long Bro个人博客，一个分享Java技术的博客，个人情感博客，">
   <link rel="shortcut icon" href="/images/useful/logo2.png" type="image/x-icon"/>
	
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet"><!-- 内应该有pageList的css -->
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
  </head>
  
  <body>
  <div class="topnav">
        <a href="http://www.longqcloud.cn/" target="_blank">Long Bro博客</a>——查看其它<a href="http://tv.zy52113.com" target="_blank">Long Bro影院</a>
    </div>
    <%@include file="header.jsp" %>
       <div class="jztop"></div>
    
    <div class="container">
    
       <div class="bloglist f_l">
    <%
       String input=request.getParameter("keyword");
       String require="select * from blogs where b_Content like '%"+input+"%'";
       BlogsDao dao=new BlogsDao();
       ArrayList<Blogs> arr=dao.queryBlogs(require);
             CategoryDao dao1=new CategoryDao();
             
             for(int i=0;i<arr.size();i++){
                 Blogs blog=arr.get(i);
                 int id=blog.getId(); //根据id来查看某条博客的全部内容
                 String title=blog.getTitle();
                 String content=blog.getContent();
                 String time=blog.getTime();
                 String author=blog.getAuthor();
                 String imgPath=blog.getImgPath();
                 int readNum=blog.getViewNum();
                 int comment=blog.getComment();
                 int c_Id=blog.getCat_Id();
                 
                 String cat_Name=dao1.queryCatNameById(c_Id);
                 content=content.replaceAll("<[.[^<]]*>", "");//去掉标签                
                 if(content.length()>88){//博客较长，只显示88个字符，后面跟......
                     content=content.substring(0,88)+"......";
                 }
                 //如果截取显示的语句中有关键字，则将其特殊红色显示
                 if(content.contains(input)){
                     //两种方法实现将关键字特殊显示
                     //1.在关键字头部加上<font color="red">，尾部加上</font>
                     int start=content.indexOf(input);//关键字所在位置
                     int end=start+input.length();
                     content=content.substring(0, start)+"<font color=\"red\">"+input+"</font>"+content.substring(end);                     
                     
                     //2.直接使用replace方法
                     content=content.replace(input,"<font color=\"red\">"+input+"</font>");
                 }
                 
                  %>
                 
                 <h3><a title="<%=title%>" href="/blogs/<%=id%>.html" target="_blank"><%=title%></a></h3>
                 
                 <figure>
                     <img  style="width:140px;height:120px;border-radius:50%;" src="<%=imgPath %>" alt='<%=imgPath%>' title='欢迎您访问LongBro博客'>
                 </figure>
                 <p><%=content%></p>
                 <a title="<%=title%>" href="/blogs/<%=id%>.html" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
                  <p   class="dateview">
                     <span class="spanm"><%=time%></span>
                     <span  class="spanm">作者：<%=author %></span>
                     <span  class="spanm">个人博客：[<a href='cat_search.jsp?id=<%=c_Id%>' target='_blank'><%=cat_Name%></a>]
                     </span><span class="viewNum"><%=readNum%></span>
                     <span class='commentNum'><%=comment%></span>
                  </p>
                 <%
             }    
             
     %>
    </div>
    <div class="r_box f_r">
      <%
        //控制图片每5秒换一张
        //int p=OtherUtil.generateRan();//还是不行，不过意料之中
        //每次进入，随机放置一张图片
       Random ran=new Random();
        int p=ran.nextInt(18);
        String lovesen=OtherUtil.loveSen(p);//爱的句子
        out.write("<h3 class=\"tit\">"+lovesen+"</h3>"); 
      %>
      <div class="ad" > <a href=""><img style="width:150px;height:120px;" src="images/yinger/love<%=p%>.jpg" ></a> </div>
     <div class="tuwen">
        <h3 class="tit">博客分类</h3>
        
        <%
            ArrayList<Category> ca=dao1.queryCategories();
            out.write("<ul>");
            for(int i=0;i<ca.size();i++){
                  Category category=ca.get(i);
                  int cat_Id=category.getCat_Id();
                  String cat_Name=category.getCat_Name();
                  String cs=" where cat_Id="+cat_Id;//查询语句的条件
                  int cat_Num=dao.queryBlogsNum(cs);//库中指定类型博客的数量
                  out.write("<li><p><a href='cat_search.jsp?id="+cat_Id+"' target='_blank'>"+cat_Name+"</a>&nbsp;共("+cat_Num+")篇</p></li>");
              }
            out.write("</ul>");
              
           
         %>
        
      </div>
     
     <div class="tuwen">
        <h3 class="tit">日期归类</h3>
        
        <%
             //left(b_Time,10)      left(b_Time,7)
             String what="distinct left(b_Time,7) as y_m";
             
             ArrayList<TimeNum> tnar=dao.queryBlogs(what,null);
             out.write("<ul>");
             for(int i=0;i<tnar.size();i++){
                TimeNum tn=tnar.get(i);
                String y_m=tn.getTime();
                int amo=tn.getNum();
               out.write("<li><p><a href='date_search.jsp?time="+y_m+"'>"+y_m+"</a>&nbsp;&nbsp共("+amo+")篇</p></li>");
             }
             out.write("</ul>");
             
           String select="select distinct left(b_Time,10) as y_m  from blogs;";//年月日
           String select1="select distinct left(b_Time,7) as y_m  from blogs;";//年月
          
         %>
        
       
      </div>
     
      <div class="tuwen">
        <h3 class="tit">阅读量排行</h3>
        <%
            String re="select * from blogs order by b_ViewNum desc limit 0,10";
            ArrayList<Blogs> b=dao.queryBlogs(re);
            out.write("<ul>");
            for(int i=0;i<b.size();i++){
                Blogs bl=b.get(i);
                int b_Id=bl.getId();
                String title=bl.getTitle();
                int viewNum=bl.getViewNum();
                if(i==0){//阅读量排第一
                   out.write("<li><p><pai class='first'>"+(i+1)+"</pai>.<a href=\"/blogs/"+b_Id+".html\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==1){//阅读量排第二
                   out.write("<li><p><pai class='second'>"+(i+1)+"</pai>.<a href=\"/blogs/"+b_Id+".html\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==2){
                   out.write("<li><p><pai class='third'>"+(i+1)+"</pai>.<a href=\"/blogs/"+b_Id+".html\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else{
                   out.write("<li><p><pai class='other'>"+(i+1)+"</pai>.<a href=\"/blogs/"+b_Id+".html\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }
            }
           out.write("</ul>");
            
         %> 
      </div>
    </div>
    </div>
    
    
    <%@include file="footer.jsp" %>
  </body>
</html>
