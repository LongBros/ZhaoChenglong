<%@page import="dao.UtilDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cpage=request.getParameter("page");//当前页码
String times=request.getParameter("time");//想要查看的时间段
int pageI=1;
//将所有的页码添加至数组链表
ArrayList<String> arr=new ArrayList<String>();
for(int i=1;i<66;i++){
	String t=i+"";
	arr.add(t);
}
//实现直接进入为第一页，点击页码进入，为页码的
for(int i=0;i<arr.size();i++){
	if((arr.get(i)).equals(cpage)){//如果有参数，且与数组中参数向同，则将数组中该元素定为页码，转为整型
		pageI=Integer.parseInt(arr.get(i));
		break;//
	}
	if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
		pageI=1;
	}
}
//总访问日志数量
int num=UtilDao.getNum("select count(*) from loginlog");
//每页50个，页码数
int pages=0;
if(num%50==0){
	  pages=num/50;
}else{
	  pages=num/50+1;
}
//实现在初次进入未选择时间段的情况下，加载所有的；选择后加载选择的时间段的
if("today".equals(times)){
	times="today";
}else if("week".equals(times)){
	times="week";
}else if("month".equals(times)){
	times="month";
}else{
	times="all";
}
long c=System.currentTimeMillis();
long oneday=1*24*60*60*1000;//一天的毫秒数
long d=c-oneday;//一天前的微秒时间
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>553 cinema visit log</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="553影院，LongBro影院,LongBro">
	<meta http-equiv="description" content="553影院，最新最全影视，免费免广告在线观看">
    <link rel="shortcut icon" href="/LongVideos/images/logo2.png" type="image/x-icon"/>
	<link rel="stylesheet" href="css/table.css"  media="all">
  </head>
  <body>
  	<select onchange="document.location=options[selectedIndex].value">
			<option value="log/visitlog.jsp?time=today&page=1">今日</option>
			<option value="log/visitlog.jsp?time=week&page=1">一周</option>
			<option value="log/visitlog.jsp?time=month&page=1">一月</option>
			<option value="log/visitlog.jsp?time=all&page=1">所有</option>
		</select>
	<div  class="table_content">
		
        <table cellspacing="0px" cellpadding="0px">
            <thead>
               <tr>
                  <th width="20%">访问id</th>
                  <th width="20%">访问者</th>
                  <th width="20%">访问者ip</th>
                  <th width="20%">访问者地址</th>
                  <th width="20%">访问时间</th>
               </tr>
           </thead>
           <tbody>
           
      <%
         try{
        	  
              Class.forName(SqlUtil.driver);
              Connection con=DriverManager.getConnection(SqlUtil.url, SqlUtil.user, SqlUtil.pass);
              Statement st=con.createStatement();
              int startindex=((pageI-1)*50);//当页首个序号，第一页为1，第二页为51
              String sql="select * from loginlog limit "+startindex+",50";
              System.out.println(sql);
              ResultSet rs= st.executeQuery(sql);
              while(rs.next()){    
            	  int id=rs.getInt(1);
                  String user=rs.getString(2);
                  String ip=rs.getString(3);
                  String add=rs.getString(4);
                  String time=rs.getString(5);
                  %>
                  <tr>
	                  <td width="20%"><%=id%></td>
	                  <td width="20%"><%=user%></td>
	                  <td width="20%"><%=ip%></td>
	                  <td width="20%"><%=add%></td>
	                  <td width="20%"><%=time%></td>
	               </tr>
                  <%
              }
         }catch(Exception e){
         }
         
       %>
       
       </tbody>
     </table>
	</div>
	<center>
		共<%=num%>条，每页50条，当前<%=pageI %>/<%=pages%><br>
		<%
			for(int i=1;i<=pages;i++){
				%>
				<span><a href="log/visitlog.jsp?page=<%=i%>"><%=i%></a></span>&nbsp;&nbsp; 
				<%-- <span onclick="loadCom(<%=i%>)"><%=i%></span>&nbsp;&nbsp; --%>
				<%
			}
		%>
	</center>
	<script>
		function loadPage(){
			
		}
		function loadCom(p){
			var xmlHttp=false;
    	   	if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
    	   		xmlHttp=new XMLHttpRequest();
    	   	}else if(window.ActiveXObject){//QQ浏览器（IE)
    	       	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
    	   	}
    	   	var url="/?pages="+i;
    	   	xmlHttp.open("get", url,true);
    	   	
    	   	xmlHttp.send();
		}
		window.onload=loadPage();
	</script>
       
       <hr width="80%">
       <%@include file="../footer.jsp" %>
  </body>
</html>
