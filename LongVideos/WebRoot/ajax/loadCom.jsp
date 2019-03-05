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
String url=request.getParameter("url");
//采取从cookie中获取用户名方式避免ajax从url传来导致乱码
Cookie[] cookies=request.getCookies();
String nick="";
for(int i=0;cookies!=null&&i<cookies.length;i++){
	if(cookies[i].getName().equals("user")){
		nick=cookies[i].getValue().toString();
		nick=URLDecoder.decode(nick, "utf-8");
	}
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>ajax异步加载评论</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <!-- 实现加载库中已有评论功能-开始 -->
       <%
       	String sql="select count(*) from comment where c_Video='"+url+"'";
        int num=UtilDao.getNum(sql);//查询该视频的评论的数量
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
       %>
       <br>	<br>评论列表：(共有<font color="red" size="-2"><%=num%></font>条该影视的评论)
       <%
       //加载该视频下的所有评论-开始
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			//查询该视频下的所有评论
			String sql1="select * from comment where c_Video='"+url+"'";
			rs=st.executeQuery(sql1);
			int i=0;
			while(rs.next()){//有该用户对该影视的播放记录，修改时间为最新
				i++;
				int c_Id=rs.getInt("c_Id");
				String c_User=rs.getString("c_User");
				String c_Time=rs.getString("c_Time");
				String c_Content=rs.getString("c_Content");
				//因库中表情存入的是【】，而不是路径，故在此对其做处理.
				//具体处理方法，【】中的内容不变，只将两边【和】分别变为<img alt='' src='/images/aodamiao/和.gif'>
				//两行代码即可实现，下面注释部分算是走的弯路吧！
				c_Content=c_Content.replaceAll("【", "<img alt='' src='/images/aodamiao/");
				c_Content=c_Content.replaceAll("】", ".gif'>");
				c_Content=c_Content.replaceAll("《", "<img alt='' src='/images/huang/");
				c_Content=c_Content.replaceAll("》", ".gif'>");
				
				int znum=rs.getInt("c_Znum");//赞的数量
				int cnum=rs.getInt("c_Cnum");//踩的数量
				//得到该评论已存在的回复的数量
				String sql2="select count(*) from reply where c_Id="+c_Id;
  				int num2=UtilDao.getNum(sql2);
  				Random ran=new Random();
  				int tx=ran.nextInt(9);
  				//System.out.println(tx);
				%>
				<div class="coms">
		       		<img alt="" src="/images/tx/tx<%=tx%>.jpg">
					<nick><a href="myInfo.jsp?user=<%=c_User %>"><%=c_User%></a></nick><time><%=c_Time %></time>
		      		<div class="comment"><%=c_Content %></div>
		      		<div class="zan">
			      		<span id="r<%=i%>"  onclick="reply<%=i%>()"><font size="-2">回复(<font color="red"><%=num2 %></font>)</font></span>
			      		<span id="cr<%=i%>" style="display: none" onclick="creply<%=i%>()"><font size="-2">取消回复</font></span>
			      		<img id="ding<%=i%>" src="/images/util/ding-2x.png" onclick="changeDing<%=i %>()">
			      		<span id="znum<%=i%>"><%=znum%></span>
			      		<img id="cai<%=i%>" src="/images/util/cai-2x.png" onclick="changeCai<%=i %>()">
			      		<span id="cnum<%=i%>"><%=cnum%></span>
			      		<br>
			      		<form action="/SendReply" method="post">
			      			<input type="text" style="display: none" name="r_User" value="<%=nick%>">
			      			<input type="text" style="display: none" name="c_Id" value="<%=c_Id%>">
			      			<textarea rows="5" style="display: none" id="reply<%=i %>"
			      			 cols="140" name="reply" placeholder="期待你的神回复"></textarea>
			      			 <input id="btn<%=i %>" type="submit" style="display: none" value="回复">
			      		</form>
			      		<div class="reply" id="allReply<%=i %>" style="display: none;">
			      			<%
			      				//System.out.println("评论下的回复数量"+num2);
			      				if(num2>0){//有回复，则加载并显示
			      					try{
			      						Class.forName(SqlUtil.driver);
			      						Connection con2=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			      						Statement st2=con2.createStatement();
			      						ResultSet rs2=st2.executeQuery("select * from reply where c_Id="+c_Id);
			      						int j=0;
			      						while(rs2.next()){
			      							j++;
			      							String ru=rs2.getString(3);//回复者
			      							String co=rs2.getString(4);//回复内容
			      							String t=rs2.getString(7);//回复时间
			      							%>
			      								<%=j%>楼：<%=co %>&nbsp;<%=t %><br>
			      							<%
			      						}
			      						rs2.close();
			      						st2.close();
			      						con2.close();
			      					}catch(Exception e){
			      						
			      					}
			      				}
			      				//加载该评论下的回复
			      			%>
			      		</div>
			      	</div>
			      	<script type="text/javascript">
			      		function changeDing<%=i%>(){
			      			var d=document.getElementById("ding<%=i%>");
			      			var s=d.src;
			      			//点击赞，赞对应的数量加1
			      			var znum=document.getElementById("znum<%=i%>").innerHTML;
			      			var z=parseInt(znum);
			      			//window.alert(s);
			      			//弹出的是一个绝对路径，即http://longqcloud/images/util/ding-2x.png
			      			//此处必须用==，不能只用一个=，否则无法正确判断是否相等
			      			if(s=="http://longqcloud/images/util/ding-2x.png"){//未点赞状态点击后为点赞状态且点赞数加1
			      				//window.alert("1111111");
			      				d.src="/images/util/ding-active-2x.png";
			      				z=z+1;
			      			} else{//点赞状态点击后为未点赞状态且点赞数减一
			      				// window.alert("1111111");
			      				d.src="/images/util/ding-2x.png";
			      				z=z-1;
			      			}
			      			//window.alert(z);
			      			document.getElementById("znum<%=i %>").innerHTML=z;
			      		}
			      		function changeCai<%=i %>(){
			      			var d=document.getElementById("cai<%=i%>");//根据id获得的img
			      			//点击踩，踩对应的数量变化
			      			var cnum=document.getElementById("cnum<%=i %>").innerHTML;
			      			var c=parseInt(cnum);//踩的数量，转化为整型
			      			if(d.src=="http://longqcloud/images/util/cai-2x.png"){
			      				d.src="/images/util/cai-active-2x.png";
			      				c=c+1;
			      			}else{
			      				d.src="/images/util/cai-2x.png";
			      				c=c-1;
			      			}
			      			//window.alert(c);
			      			document.getElementById("cnum<%=i %>").innerHTML=c;
			      		}
			      		function reply<%=i %>(){
			      			//点击回复按钮，显示取消回复和textarea以及回复submit和所有该评论的回复，隐藏回复
			      			//window.alert("<%=i %>");
			      			document.getElementById("r<%=i %>").style.display="none";
			      			document.getElementById("cr<%=i %>").style.display="inline-block";
			      			document.getElementById("reply<%=i %>").style.display="block";
			      			document.getElementById("btn<%=i %>").style.display="block";
			      			document.getElementById("allReply<%=i %>").style.display="block";
			      		}
			      		function creply<%=i%>(){
			      			//点击取消回复按钮，显示回复，隐藏取消回复和textarea以及回复submit和所有该评论的回复
			      			//window.alert("<%=i%>");
			      			document.getElementById("r<%=i%>").style.display="inline-block";
			      			document.getElementById("cr<%=i%>").style.display="none";
			      			document.getElementById("reply<%=i%>").style.display="none";
			      			document.getElementById("btn<%=i%>").style.display="none";
			      			document.getElementById("allReply<%=i%>").style.display="none";
			      		}
			      	</script>
		      		<div class="line"></div>
      			 </div>
				<%
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
       %>
       <!-- 实现加载库中已有评论功能-开始 -->
  </body>
</html>
