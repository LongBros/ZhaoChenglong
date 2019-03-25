<%@page import="beans.TimeNum"%>
<%@page import="beans.Blogs"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String date=request.getParameter("time");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=date%>|Long Bro博客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="author" content="Long Bro">
	<meta name="keywords" content="Long Bro的博客,Long Bro,博客,Java学习聚集地，情感博客">
	<meta name="description" content="Long Bro个人博客，一个分享Java技术和web编程技术的博客，">
    <link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet"><!-- 内应该有pageList的css -->
  </head>
  
  <body>
    <div class="topnav">
        <a href="http://tv.zy52113.com" target="_blank">Long Bro影院</a>——查看其它<a href="http://www.longqcloud.cn/" target="_blank">Long Bro博客</a>
    </div>
    <%@include file="header.jsp" %>  </body>
    <div class="jztop"></div>
     <div class="container">
    <div class="bloglist f_l">
        <%
             String pageId=request.getParameter("pageId");
	        int pageI=1;
	        //将所有的页码添加至数组链表
			ArrayList<String> arr1=new ArrayList<String>();
			for(int i=1;i<60;i++){
				String t=i+"";
				arr1.add(t);
			}
			//实现直接进入为第一页，点击页码进入，为页码的
			for(int i=0;i<arr1.size();i++){
				if((arr1.get(i)).equals(pageId)){//如果有参数，且与数组中某元素相同，则将数组中该元素定为页码，转为整型
					pageI=Integer.parseInt(arr1.get(i));
					break;//
				}
				if(i==(arr1.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的，不是点击页码进去的
					pageI=1;
				}
			}
                    
             BlogsDao dao=new BlogsDao();
             //查询该月份下博客的数量
             int num=dao.queryBlogsNum(" where b_Time like '"+date+"%';");
             int pages=0;//博客页数
             //每页五篇，计算出页数
             if(num%5==0){
                pages=num/5;
             }else{
                pages=num/5+1;
             } 
             
             String sel="select * from blogs where b_Time like '"+date+"%' order by b_Id desc limit "+((pageI-1)*5)+",5";
             ArrayList<Blogs> arr=dao.queryBlogs(sel);
             CategoryDao dao1=new CategoryDao();
             
             for(int i=0;i<arr.size();i++){
                Blogs b=(Blogs)arr.get(i);
                int b_Id=b.getId(); //根据id来查看某条博客的全部内容
                 String title=b.getTitle();
                 String content=b.getContent();
                 String time=b.getTime();
                 String author=b.getAuthor();
                 String imgPath=b.getImgPath();
                 int readNum=b.getViewNum();
                 int comment=b.getComment();
                 int c_Id=b.getCat_Id();
                 
                 String cat_Name=dao1.queryCatNameById(c_Id);
                 
                content=content.replaceAll("<[.[^<]]*>", "");
                
                 //注意，先去掉标签，在截取
                 if(content.length()>88){//博客较长，只显示66个字符，后面跟......
                     content=content.substring(0,88)+"......";
                 }
                 %>
                 <h3><a title="<%=title%>" href="detailblog.jsp?id=<%=b_Id%>" target="_blank"><%=title%></a></h3>
                 
                 <figure>
                     <img  style="width:140px;height:120px;border-radius:50%;" src="<%=imgPath %>" alt='<%=imgPath%>' title='欢迎您访问LongBro博客'>
                 </figure>
                 <p><%=content%></p>
                 <a title="<%=title%>" href="detailblog.jsp?id=<%=b_Id%>" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
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
                   out.write("<li><p><pai class='first'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==1){//阅读量排第二
                   out.write("<li><p><pai class='second'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else if(i==2){
                   out.write("<li><p><pai class='third'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }else{
                   out.write("<li><p><pai class='other'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
                }
            }
           out.write("</ul>");
            
         %> 
      </div>
     </div>
    
    </div>
    
    <div class="pagelist">页次：<%=pageI %>/<%=pages %> 每页5篇 共<%=num %>篇
  <%
    if(pageI>1){
       %>
         <a href="date_search.jsp?time=<%=date%>&pageId=<%=pageI-1%>">上</a>
       <%
    }
   %>
  <%
     if(pages<=6){//页数小于等于6，直接输出6个页数
        for(int j=1;j<=pages;j++){
         if(j==pageI){
              out.write("<current><a>"+j+"</a></current>");     
         }else{
              out.write("<a href=\"date_search.jsp?time="+date+"&pageId="+j+"\">"+j+"</a>"); 
         }
       } 
     }else{//页数大于6，算法设计只显示6个页数
        if(pageI>pages-6){//当前页码大于总页码-6，输出后六页
           for(int j=pages-5;j<=pages;j++){
              if(j==pageI){
                   out.write("<current><a>"+j+"</a></current>");     
               }else{
              out.write("<a href=\"date_search.jsp?time="+date+"&pageId="+j+"\">"+j+"</a>"); 
              }
           }
        }else{//当前页码小于总页码-6，输出当前页码后的六页
            for(int j=pageI;j<pageI+6;j++){
               if(j==pageI){
                   out.write("<current><a>"+j+"</a></current>");     
               }else{
              out.write("<a href=\"date_search.jsp?time="+date+"&pageId="+j+"\">"+j+"</a>"); 
               }
           }
        }
     }
  %>
   <%
   if(pageI<pages){
      %>
          <a href="date_search.jsp?time=<%=date%>&pageId=<%=pageI+1%>">下</a>
      <%
   }
    %>
  </div>
    
    <%@include file="footer.jsp" %>
   
</html>
