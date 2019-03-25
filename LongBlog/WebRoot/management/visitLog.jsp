<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.longbro.util.OtherUtil"%>
<%@page import="com.longbro.util.AddressUtils"%>
<%@page import="com.longbro.util.AddressUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String timestamp=request.getParameter("ts");//获取请求的时间段
if("today".equals(timestamp)){
     timestamp="today";
}else if("week".equals(timestamp)){
     timestamp="week";
}
else if("month".equals(timestamp)){
     timestamp="month";
}
else if("all".equals(timestamp)){
     timestamp="all";
}else{
    timestamp="today";
}
System.out.println(timestamp);
int pages=0;//总页数
int pre=0;
String num="";
int n=0;
//获取页码
String scp=request.getParameter("page");
int cp=1;
//将所有的页码添加至数组链表
ArrayList<String> arr=new ArrayList<String>();
for(int i=1;i<1000;i++){
	String t=i+"";
	arr.add(t);
}
System.out.println(arr.get(0)+arr.get(1));
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
    <title>网站访问量分析</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/main.css">
  </head>
  
  <body>
      <select onchange="document.location=options[selectedIndex].value" size="1">
         <option value="/LongBlog/management/visitLog.jsp?ts=today&page=1">今日</option>
         <option value="/LongBlog/management/visitLog.jsp?ts=week&page=1">七日内</option>
         <option value="/LongBlog/management/visitLog.jsp?ts=month&page=1">一月内</option>
         <option value="/LongBlog/management/visitLog.jsp?ts=all&page=1">所有</option>
      </select>
      <table border="2px">
          <tr>
             <td>id</td>
             <td>访问者ip</td>
             <td>地区</td>
             <td>访问时间</td>
          </tr>
      <%
          try{
              Connection con=JdbcUtil.getConnection();
	          Statement st=con.createStatement();
	          ResultSet rs;
	          long curr=System.currentTimeMillis();//当前时间
	          System.out.println(curr);
	          System.out.println(new Date(System.currentTimeMillis()).toLocaleString());
	          String t=OtherUtil.time().substring(0,10);//今天的日期
	          long oneday=1*24*60*60*1000;//一天的毫秒数         
         
	          SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	          String w=sdf.format(new Date(curr-oneday*7));//一周前的时间
	          String m=sdf.format(new Date(curr-oneday*30));//一月前的时间
         
	          //得到数量
	          if(timestamp.equals("today")){//当天
	              num="select count(*) from log_vis where left(l_Time,10)='"+t+"';";
	          }else if(timestamp.equals("week")){//一周前
	              num="select count(*) from log_vis where l_Time>'"+w+"';";
	          }else if(timestamp.equals("month")){//一月前
	              num="select count(*) from log_vis where l_Time>'"+m+"';";
	          }else{
	              num="select count(*) from log_vis";
	          }
	          rs=st.executeQuery(num);
	          if(rs.next()){
	              n=rs.getInt("count(*)");
	          }
           //每页60个，计算出页数  3篇有一页，63篇有两页，故需加1
           if(n%30==0){
               pages=n/30;//总页数
           }else{
               pages=n/30+1;//总页数  
           }
           pre=30*(cp-1)+1;//显示的第cp页的第一个记录
          
          String sql="";
          //今日的访问日志---注意where子句和order by子句的先后顺序
          if(timestamp.equals("today")){//当天
              sql="select * from log_vis where left(l_Time,10)='"+t+"' order by l_Id desc  limit "+pre+",30;";
          }else if(timestamp.equals("week")){//一周前
              sql="select * from log_vis where l_Time>'"+w+"' order by l_Id desc limit "+pre+",30;";
          }else if(timestamp.equals("month")){//一月前
              sql="select * from log_vis where l_Time>'"+m+"' order by l_Id desc limit "+pre+",30;";
          }else if(timestamp.equals("all")){//所有
              sql="select * from log_vis order by l_Id desc limit "+pre+",30;";
          } 
          System.out.println(sql);
          out.println("<br>"+timestamp+"→"+n+"条记录,分为"+pages+"页,第"+cp+"页"+pre+"→"+(pre+29)+",共30条");

          
          rs=st.executeQuery(sql);
          int i=30*(cp-1)+1;
          while(rs.next()){
             int id=rs.getInt("l_Id"); 
             String ip=rs.getString("l_Ip");
             String add=rs.getString("l_Address");
             String time=rs.getString("l_Time");//可以用getString方法得到DateTime的值
             //System.out.println(ip+add);
             %>
             <tr>
                <td><%=i %></td>
                <td><%=ip %></td>
                <td><%=add %></td>
                <td><%=time %></td>
             </tr>
             <%
             i++;
            }
            rs.close();
            st.close();
            con.close();
          }catch(Exception e){
             
          }         
       %>
     </table>  
     
  <form action="/management/visitLog.jsp?ts=<%=timestamp%>" method="post">
  <div class="pagelist">页次：<%=cp %>/<%=pages %> 每页5条 共<%=n %>条记录
  <%
    if(cp>1){
       %>
         <a href="/management/visitLog.jsp?ts=<%=timestamp %>&page=<%=cp-1%>">←</a>
       <%
    }
   %>
  <%
     if(pages<=6){//页数小于等于6，直接输出6个页数
        for(int j=1;j<=pages;j++){
         if(j==cp){
              out.write("<current><a>"+j+"</a></current>");     
         }else{
              out.write("<a href=\"/management/visitLog.jsp?ts="+timestamp+"&page="+j+"\">"+j+"</a>"); 
         }
       } 
     }else{//页数大于6，算法设计只显示6个页数
        if(cp>pages-6){//当前页码大于总页码-6，输出后六页
           for(int j=pages-5;j<=pages;j++){
              if(j==cp){
                 out.write("<current><a>"+j+"</a></current>");     
              }else{
                  out.write("<a href=\"/management/visitLog.jsp?ts="+timestamp+"&page="+j+"\">"+j+"</a>"); 
              }
           }
        }else{//当前页码小于总页码-6，输出当前页码后的六页
            for(int j=cp;j<cp+6;j++){
               if(j==cp){
                 out.write("<current><a>"+j+"</a></current>");     
              }else{
                  out.write("<a href=\"/management/visitLog.jsp?ts="+timestamp+"&page="+j+"\">"+j+"</a>"); 
              }
           }
        }
     }
  %>
  
  
      <input type="text" name="page" style="width: 42px; ">
      <input type="submit" value="检索">
      
      
       <%
   if(cp<pages){
      %>
          <a href="/management/visitLog.jsp?ts=<%=timestamp%>&page=<%=cp+1%>">→</a>
      <%
   }
    %>
      
  </form>
  </div>
  
  </body>
</html>
