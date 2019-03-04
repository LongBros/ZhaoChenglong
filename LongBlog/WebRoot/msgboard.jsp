<%@page import="dao.SentenceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String scp=request.getParameter("page");
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>每日一句</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/my.css">
	
	<style type="text/css">
	/*页码样式，learn.jsp中有*/
.page{ margin:20px 0 ; text-align:center; width:100%;overflow: hidden;}
.page a b {color: #aee1ff;}
.page>b,.page a {margin: 0 2px;height: 26px;line-height: 26px;border-radius: 50%;width: 26px;text-align: center;display: inline-block}
.page a {margin: 0 2px;height: 26px;line-height: 26px;border-radius: 50%;width: 26px;text-align: center;display: inline-block}/* 针对IE6 */
.page>b,.page a:hover{background: #2a455d;color: #FFF;}
.page a {color: #aee1ff;border: #89c9ef 1px solid}
	</style>

  </head>
  
  <body>
      <%@include file="header.jsp" %>
      
          <article>
  <div class="container">
    <h2 class="ctitle"><b>每日一句</b> <span>时光如白驹过隙匆匆而过，每天都要进步一点点。</span></h2>
    <div class="gbook">
      <div class="about">
        <div id="fountainG">
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
        </div>
        <ul class="cbp_tmtimeline">
        
        <% 
           SentenceDao sd=new SentenceDao();
           String re=" limit "+(cp-1)*7+",7";//(cp-1)*7,7
           ArrayList<Sentence> sens= sd.queryAll(re);
           int num=sd.queryNum();
           System.out.println(num);
           int pages=0;
           if(num%7==0){
                pages=num/7;
           }else{
                pages=num/7+1;
           }
           for(Sentence s:sens){
              String chi=s.getS_Chinese();
              String eng=s.getS_English();
              String time=s.getS_Date();
              String year=time.substring(0, 4);
              String monday=time.substring(5, 10);
              //System.out.println(year);
              //System.out.println(monday);
              %>
                   <li>
						<time class="cbp_tmtime"><span><%=monday %></span> <span><%=year %></span></time>
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" data-scroll-reveal="enter right over 1s" >
							<h2><%=chi %></h2>
							 <p><%=eng %></p>
						</div>
				  </li>
			 
              <%
              
           }
        %>
        </ul>
      </div>
    </div>
  </div>
  </aside>
</article>
      
      
   <div class="page">
       <%=cp %>/<%=pages %>&nbsp;共<%=num %>句话
       
  <%
    if(cp>1){
       %>
         <a href="/LongBlog/msgboard.jsp?page=<%=cp-1%>">&lt;&lt;</a>
       <%
    }
   %>
       
       
       <%
     if(cp<=6){//页数小于等于6，直接输出6个页数
        for(int j=1;j<=pages;j++){
         String pageIndex="<a href=\"/LongBlog/msgboard.jsp?page="+j+"\">"+j+"</a>";
         if(j==cp){
              out.write("<b>"+j+"</b>");     
         }else{
              out.write(pageIndex); 
         }
       } 
     }else{//页数大于6，算法设计只显示6个页数
        if(cp>pages-6){//当前页码大于总页码-6，输出后六页
           for(int j=pages-5;j<=pages;j++){
              String pageIndex="<a href=\"/LongBlog/msgboard.jsp?page="+j+"\">"+j+"</a>";
              if(j==cp){
                out.write("<b>"+j+"</b>");     
              }else{
                  out.write(pageIndex); 
              }
           }
        }else{//当前页码小于总页码-6，输出当前页码后的六页
            for(int j=cp;j<cp+6;j++){
               String pageIndex="<a href=\"/LongBlog/msgboard.jsp?page="+j+"\">"+j+"</a>";
               if(j==cp){//当前页码未加链接
                   out.write("<b>"+j+"</b>");     
               }else{
                   out.write(pageIndex); 
                }
           }
        }
     }
  %>
 
   <%
    if(cp<pages){
       %>
         <a href="/LongBlog/msgboard.jsp?page=<%=cp+1%>">&gt;&gt;</a>
       <%
    }
   %>
   </div>
                
      <%@include file="footer.jsp" %>
  </body>
</html>
