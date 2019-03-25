<%@page import="java.net.URLDecoder"%>
<%@page import="dao.UtilDao"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//将cookie中toast加1
String toast="";
Cookie[] cs=request.getCookies();
for(int i=0;i<cs.length;i++){
	if(cs[i].getName().toString().equals("toast")){
		toast=cs[i].getValue().toString();
	}
}
//System.out.println(toast);
//第一次加载从第六个开始
int start=Integer.parseInt(toast)*5-1;
//System.out.println(start);
 Connection con=null;
 Statement st=null;
 ResultSet rs=null;
//加载更多吐槽
try {
	Class.forName(SqlUtil.driver);
	con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
	st=con.createStatement();
	//查询该视频下的所有评论
	String sql="select * from wall order by w_Id desc limit "+start+",6";
	rs=st.executeQuery(sql);
	if(rs.next()){//如果库中还有记录，继续让toast加1
		toast=Integer.parseInt(toast)+1+"";//将toast转化为整型并加1，再转为字符型
		Cookie cookie=new Cookie("toast",toast);
		cookie.setMaxAge(1*60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	while(rs.next()){
		int w_Id=rs.getInt(1);
		String w_Nick=rs.getString(2);
		String w_Tx=rs.getString(3);
		String w_Content=rs.getString(4);
		w_Content=w_Content.replaceAll("【", "<img alt='' src='/images/aodamiao/");
		w_Content=w_Content.replaceAll("】", ".gif'>");			
		System.out.println(w_Id);
					
		String w_Ip=rs.getString(5);//原始ip
		if(w_Ip.length()>8){
			w_Ip=w_Ip.substring(0,8)+"...";
		}
		String w_Add=rs.getString(6);//地址
		System.out.println("地址为"+w_Add);
		if("".equals(w_Add)){//如果地址为空，则将ip放上
			w_Add=w_Ip;
		} 
		String w_Time=rs.getString(7);
		%>
		<div class="comment">
			<ul>
				<img alt="" src="<%=w_Tx%>">
				<span class="info"><%=w_Nick%>（来自<font color="gray"><%=w_Add%></font>的网友）</span>
				<span class="time"><%=w_Time %></span>		
				<%if(w_Id==1){ %>
					<span style="width:25px;height:25px;color:white; background:red;">置顶</span>
				<%} %>
				<p><%= w_Content%></p>
			</ul>
		</div>
		<%
	}
	st.close();
	con.close();
} catch (Exception e) {
	e.printStackTrace();
}
%>
