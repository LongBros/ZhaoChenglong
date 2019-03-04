<%@page import="java.net.InetAddress"%>
<%@page import="beans.TimeNum"%>
<%@page import="dao.BlogsDao"%>
<%@page import="util.OtherUtil"%>
<%@page import="beans.Blogs"%>
<%@page import="util.JdbcUtil"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
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
    <title>Long Bro的博客</title>
	<meta name="author" content="Long Bro">
	<meta name="keywords" content="Long Bro的博客,Long Bro,博客,Java学习聚集地，情感博客">
	<meta name="description" content="Long Bro个人博客，一个分享Java技术的博客，个人情感博客，">
   
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet"><!-- 内应该有pageList的css -->
    <link href="css/my.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
    
    <script>
    var _hmt = _hmt || [];
   (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?2062e45c460e154dde1d90601acc3314";
        var s = document.getElementsByTagName("script")[0]; 
       s.parentNode.insertBefore(hm, s);
   })();
   
  
</script>
  </head>
<body onload="initCont()">
<div class="topnav">
    <a href="http://www.longqcloud.cn" target="_blank">Long Bro博客</a>——查看其它<a href="http://www.longqcloud.cn/EryaExam" target="_blank">尔雅考试</a>
</div>
<div id="wrapper">
    <%@include file="header.jsp" %>
   <div class="jztop"></div>
  <div class="container">
    <div class="bloglist f_l">
      
      <%
   
        String pageId=request.getParameter("pageId");
        int pageI=1;
        //将所有的页码添加至数组链表
		ArrayList<String> arr=new ArrayList<String>();
		for(int i=1;i<60;i++){
			String t=i+"";
			arr.add(t);
		}
		System.out.println(arr.get(0)+arr.get(1));
		//实现直接进入为第一页，点击页码进入，为页码的
		for(int i=0;i<arr.size();i++){
			if((arr.get(i)).equals(pageId)){//如果有参数，且与数组中某元素相同，则将数组中该元素定为页码，转为整型
				pageI=Integer.parseInt(arr.get(i));
				break;//
			}
			if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的，不是点击页码进去的
				pageI=1;
			}
		}
        //首页展示IT技术以及学习笔记类型的博客
        BlogsDao inDao=new BlogsDao();
        String cata=" where cat_Id=1 or cat_Id=3;";
        int num=inDao.queryBlogsNum(cata);//库中博客的数量
        int pages=0;//博客页数
        //每页五篇，计算出页数
        if(num%5==0){
            pages=num/5;
        }else{
            pages=num/5+1;
        } 
            
          //加入一条访问日志信息
          OtherUtil.genLog(request);
			
          //加载指定类型的博客
             String is="select * from blogs where cat_Id=1 or cat_Id=3 order by b_Id desc limit "+((pageI-1)*5)+",5";
             ArrayList<Blogs> ar= inDao.queryBlogs(is);
             CategoryDao c=new CategoryDao();
             for(int i=0;i<ar.size();i++){
                 Blogs blog=ar.get(i);
                 int id=blog.getId(); //根据id来查看某条博客的全部内容
                 String title=blog.getTitle();
                 String content=blog.getContent();
                 String time=blog.getTime();
                 String author=blog.getAuthor();
                 String imgPath=blog.getImgPath();
                 int readNum=blog.getViewNum();
                 int comment=blog.getComment();
                 int c_Id=blog.getCat_Id();
                 
                 String cat_Name=c.queryCatNameById(c_Id);
                
                 content=content.replaceAll("<[.[^<]]*>", "");
                
                 //注意，先去掉标签，在截取
                 if(content.length()>88){//博客较长，只显示66个字符，后面跟......
                     content=content.substring(0,88)+"......";
                 }
                  %>
                 <h3><a title="<%=title%>" href="/LongBlog/detailblog.jsp?id=<%=id%>" target="_blank"><%=title%></a></h3>
                 <figure>
                     <img  style="width:140px;height:120px;border-radius:50%;" src="<%=imgPath %>" alt='<%=imgPath%>' title='发布博文后，系统随机分配的图片'>
                 </figure>
                 <p><%=content%></p>
                 <a title="<%=title%>" href="/LongBlog/detailblog.jsp?id=<%=id%>" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
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
            ArrayList<Category> ca=c.queryCategories();
            for(int i=0;i<ca.size();i++){
                  Category category=ca.get(i);
                  int cat_Id=category.getCat_Id();
                  String cat_Name=category.getCat_Name();
                  int cat_Num=category.getCat_Num();
                  out.write("<ul>");
                  out.write("<li><p><a href='cat_search.jsp?id="+cat_Id+"' target='_blank'>"+cat_Name+"</a></p></li>");//&nbsp;共("+cat_Num+")篇
                  out.write("<ul>");
              }
           
         %>
        
      </div>
     
     <div class="tuwen">
        <h3 class="tit">日期归类</h3>
        
        <%
             //left(b_Time,10)      left(b_Time,7)
             String what="distinct left(b_Time,7) as y_m";
             
             ArrayList<TimeNum> tnar=inDao.queryBlogs(what,null);
             for(int i=0;i<tnar.size();i++){
                TimeNum tn=tnar.get(i);
                String y_m=tn.getTime();
                int amo=tn.getNum();
                out.write("<ul>");
               out.write("<li><p><a>"+y_m+"</a>&nbsp;&nbsp共("+amo+")篇</p></li>");
               out.write("<ul>");
             }
           String select="select distinct left(b_Time,10) as y_m  from blogs;";//年月日
           String select1="select distinct left(b_Time,7) as y_m  from blogs;";//年月
          
         %>
        
       
      </div>
     
      <div class="tuwen">
        <h3 class="tit">阅读量排行</h3>
        <%
            String re="select * from blogs order by b_ViewNum desc limit 0,10";
            ArrayList<Blogs> b=inDao.queryBlogs(re);
            out.write("<ul>");
            for(int i=0;i<b.size();i++){
                Blogs bl=b.get(i);
                int b_Id=bl.getId();
                String title=bl.getTitle();
                int viewNum=bl.getViewNum();
                if(i==0){//阅读量排第一
                   out.write("<li><p><pai class='first'>"+(i+1)+"</pai>.<a href=\"detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==1){//阅读量排第二
                   out.write("<li><p><pai class='second'>"+(i+1)+"</pai>.<a href=\"detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==2){
                   out.write("<li><p><pai class='third'>"+(i+1)+"</pai>.<a href=\"detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else{
                   out.write("<li><p><pai class='other'>"+(i+1)+"</pai>.<a href=\"detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }
            }
           out.write("</ul>");
            
         %> 
      </div>
     </div>
  
  </div>
  <!-- container代码 结束 -->
  <div class="jzend"></div>
  
  <div class="pagelist">页次：<%=pageI %>/<%=pages %> 每页5篇 共<%=num %>篇
  <%
    if(pageI>1){
       %>
         <a href="/LongBlog/index.jsp?pageId=<%=pageI-1%>">上</a>
       <%
    }
   %>
  <%
     if(pages<=6){//页数小于等于6，直接输出6个页数
        for(int j=1;j<=pages;j++){
         String pageIndex="<a href=\"/LongBlog/index.jsp?pageId="+j+"\">"+j+"</a>";
         if(j==pageI){
              out.write("<current>"+pageIndex+"</current>");     
         }else{
              out.write(pageIndex); 
         }
       } 
     }else{//页数大于6，算法设计只显示6个页数
        if(pageI>pages-6){//当前页码大于总页码-6，输出后六页
           for(int j=pages-5;j<=pages;j++){
              String pageIndex="<a href=\"/LongBlog/index.jsp?pageId="+j+"\">"+j+"</a>";
              if(j==pageI){
                out.write("<current>"+pageIndex+"</current>");     
              }else{
                  out.write(pageIndex); 
              }
           }
        }else{//当前页码小于总页码-6，输出当前页码后的六页
            for(int j=pageI;j<pageI+6;j++){
               String pageIndex="<a href=\"/LongBlog/index.jsp?pageId="+j+"\">"+j+"</a>";
               if(j==pageI){
                   out.write("<current>"+pageIndex+"</current>");     
               }else{
                   out.write(pageIndex); 
                }
           }
        }
     }
  %>
   <%
   if(pageI<pages){
      %>
          <a href="/LongBlog/index.jsp?pageId=<%=pageI+1%>">下</a>
      <%
   }
    %>
  
  </div>
  
  <%@include file="footer.jsp" %>
</div>
</body>
</html>
