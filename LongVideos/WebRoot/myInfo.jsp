<%@page import="com.longbro.bean.SearchBean"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="dao.UtilDao"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="utils.SqlUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 需注意，该界面若是查看登陆用户自己的信息，可以看到密码等。如果是查看评论用户的信息，需对密码加密 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String nick=request.getParameter("user");//从参数中取出昵称
//从cookie中获取用户名
Cookie[] cookies=request.getCookies();
String users="";
for(int i=0;cookies!=null&&i<cookies.length;i++){
	if(cookies[i].getName().equals("user")){
		users=cookies[i].getValue().toString();//从cookie中取出当前登陆用户
		users=URLDecoder.decode(users, "utf-8");
	}
}
if("cookie".equals(nick)){//如果参数为""，说明是查看自己的个人信息
	nick=users;//将cookie中自己的登录账号赋值给参数nick，因为下面是通过参数来查找个人的各项信息的。此时nick和users相等，在不等时需对密码进行加密处理
}else{//有参数，说明是查看其他人的信息
	
}
//System.out.println(nick+"...");
Connection con=null;
Statement st=null;
ResultSet rs=null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
       <title>个人详细信息-<%=nick %></title>
       <meta name="renderer" content="webkit"></meta>
  	   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"></meta>
 	   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
  	   <link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon"></link>
  	   <link rel="stylesheet" href="css/layui.css"  media="all"></link>
 	   <link rel="stylesheet" href="css/myinfo.css"  media="all"></link>
  	   <link rel="stylesheet" href="css/table.css"  media="all"></link>
  	   <script type="text/javascript" src="/js/userinfo.js"></script>
</head>
<body>
	
	<div class="layui-tab"><!--最外部div  -->
	  <ul class="layui-tab-title">
	    <li class="layui-this">个人信息</li>
	    <li>播放记录</li>
	    <li>搜索记录</li>
	    <li>登陆日志</li>
	    <li>我的评论</li>
	    <li>我的看单</li>
	  </ul>
		<div class="layui-tab-content">
		    <div class="layui-tab-item layui-show"><!-- 个人信息 -->
		    <%
		  //根据用户名获取用户信息以供显示
		  		String qa="",ques="",ans="",name="",r_Time="";
		  		try {
		  			Class.forName(SqlUtil.driver);
		  			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
		  			st=con.createStatement();
		  			rs=st.executeQuery("select * from user where nickname='"+nick+"'");
		  			if(rs.next()){
		  				qa=rs.getString(4);
		  				
		  				//传入的参数与登陆用户的不一致，则看的是别人的信息，将密码加密
		  				if(!nick.equals(users)){
		  					ques="***该信息您无权限查看***";
		  					ans="***该信息您无权限查看***";
		  				}else{
		  					if(qa.contains(":")){
			  					ques=qa.substring(0,qa.indexOf(":"));
				  				ans=qa.substring(qa.indexOf(":")+1);
			  				}else{//改之前问题统一是QQ号，所以答案里没有问题，只有qq号，不能用:分割
			  					ques="";
			  					ans=qa;
			  				}
		  				}
		  				
		  				name=rs.getString(5);
		  				r_Time=rs.getString(6);
		  			}else{
		  				System.out.print("find user error");
		  			}
		  			rs.close();
		  			st.close();
		  			con.close();
		  		} catch (Exception e) {
		  			e.printStackTrace();
		  		}
		    %>
			    昵称<input type="text" readonly="readonly" value="<%=nick%>"/><br/>
			    找回密码问题<input type="text" readonly="readonly" value="<%=ques%>"/><br/>
			    找回密码答案<input type="text" readonly="readonly" value="<%=ans%>"/><br/>
			    姓名<input type="text" value="<%=name%>"/><br/>
			    注册时间<input type="text" readonly="readonly" value="<%=r_Time%>"/><br/>
		    </div>
		    <div class="layui-tab-item"><!-- 播放记录 -->
				<%
				//根据用户名获取用户的播放记录信息/足迹
				String pname="",img="",type="",url="",time="";
				try {
					Class.forName(SqlUtil.driver);
					con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
					String sql1="select distinct left(p_Time,10) from precord where p_Player='"+nick+"' order by p_Time desc limit 0,4";
					
					ArrayList<String> as=UtilDao.getDate(sql1);//得到该用户每一个有播放记录的天
					for(String a:as){//遍历查询出每一天看的视频
						st=con.createStatement();
						String sql="select count(*) from precord where p_Time like '%"+a+"%' and p_Player='"+nick+"' order by p_Time desc";
						int num=UtilDao.getNum(sql);
						out.write("<div class='day'>"+a+"<共看<span>"+num+"</span>个视频></div><br>");
						rs=st.executeQuery("select * from precord where p_Time like '%"+a+"%' and p_Player='"+nick+"' order by p_Time desc");
						while(rs.next()){
							time=rs.getString(3);
							pname=rs.getString(4);
							img=rs.getString(5);
							type=rs.getString(6);
							url=rs.getString(7);
						%>
						<div class='single'>
							<%=time %>
							<a href="player.jsp?type=<%=type%>&url=<%=url%>" target='_blank'>
							<img src="<%=img%>"></img>
							</a>
							<span><%=pname%></span>
							&nbsp;
							<%
								if(nick.equals(users)){//必须是本人才可移除自己看单中的视频
									%>
								<font color='red' ><span onclick="remove('<%=pname%>','<%=url%>',1)">移除</span></font>
									<%
								}
								 %>
							
						</div>
						<%
						}
						rs.close();
						st.close();
						out.write("<hr width='100%' hreight='10px'>");
					}
					
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				%>
				
			</div>
		    <div class="layui-tab-item"><!-- 搜索记录 -->
				<%
					//加载我的搜索记录
					String sql="select * from srecord where s_Searcher='"+nick+"'";
					ArrayList<SearchBean> sbs= UtilDao.getSearchRecord(sql);
					for(SearchBean sb:sbs){
						String c=sb.getContent();
						String t=sb.getTime();
						out.write("搜索内容："+c+"<br>搜索时间："+t+"<br><br>");
					}
				%>
			</div>
		    <div class="layui-tab-item">
		    
		    	登陆(访问)日志-一日统计一次
		    	<table>
		    		<thead>
		    			<tr>
		    				<td>登录ip</td><td>登录地址</td><td>登录时间</td>
		    			</tr>
		    		</thead>
		    	<%
		    		Class.forName(SqlUtil.driver);
					con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
					st=con.createStatement();
					String sqlL="select * from loginlog where c_User='"+nick+"'";
					rs=st.executeQuery(sqlL);
					int j=0;
					while(rs.next()){
						String user=rs.getString("l_User");
						String ip=rs.getString("l_Ip");
						String add=rs.getString("l_Address");
						String tim=rs.getString("l_Time");
						%>
						<tr>
							<td><%=ip%></td><td><%=add%></td><td><%=tim%></td>
						</tr>
						<%
					}
		    	 %>
		    </table>
		    
		    </div>
		    <div class="layui-tab-item"><!-- 我的评论 -->
			    <div  class="table_content">
					<div style="float:right;width:800px;">
						<font color='red'>请输入要修改成的评论：</font>
						<input type="text" id="input" value="" style="width:600px;" placeholder="请点击要编辑的评论后方的‘编辑’按钮">
						<button onclick="complete()">完成</button>
					</div>
			        <table cellspacing="0px" cellpadding="0px">
			            <thead>
			               <tr>
			                  <th width="30%">视频链接</th>
			                  <th width="50%">评论内容</th>
			                  <th width="10%">评论时间</th>
			                  <th width="4%">操作</th>
			                  <th width="4%">操作</th>
			               </tr>
			            </thead>
			             <tbody>
					<%
						//我的评论
						Class.forName(SqlUtil.driver);
						con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
						st=con.createStatement();
						String sql1="select * from comment where c_User='"+nick+"'";
						rs=st.executeQuery(sql1);
						int i=0;
						while(rs.next()){
							i++;
							int id=rs.getInt(1);
							String v=rs.getString(2);
							String c_Content=rs.getString(4);
							c_Content=c_Content.replaceAll("【", "<img alt='' src='/images/aodamiao/");
							c_Content=c_Content.replaceAll("】", ".gif'>");
							c_Content=c_Content.replaceAll("《", "<img alt='' src='/images/huang/");
							c_Content=c_Content.replaceAll("》", ".gif'>");
							String t=rs.getString(9);
							%>
							<tr>
			                  <td width="10%"><%=v%></td>
			                  <td width="10%" id="com<%=id%>"><%=c_Content%></td>
			                  <td width="10%"><%=t %></td>
			                  
			                  <%
			                  	if(nick.equals(users)){//必须是本人才可以操作自己的评论
			                  		%>
			                  		<td width="4%" onclick="alertWin('编辑评论','<%=c_Content%>',300,150)"><font color='blue'>编辑</font></td>
			                  		<!-- <td width="4%" onclick="deleteC('<%=id%>')"><font color='red'>删除</font></td> -->
			                  		<td width="4%" onclick="deleteC('<%=id%>')"><font color='red'>删除</font></td>
			                  		<%
			                  	}
			                   %>
			                   </tr>
							<%
						}
					%>
					</tbody>
		         </table>
		        
				</div>
				
		 </div>
		 <div class="layui-tab-item"><!-- 我的看单 -->
		 <%		//根据用户名获取用户的看单/收藏/喜欢
				try {
					Class.forName(SqlUtil.driver);
					con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
					String s="select distinct left(s_Time,10) from store where s_User='"+nick+"' order by s_Time desc limit 0,10";
					ArrayList<String> as=UtilDao.getDate(s);//得到该用户每一个有收藏记录的天
					for(String a:as){//遍历查询出每一天加的收藏
						st=con.createStatement();
						String sql2="select count(*) from store where s_Time like '%"+a+"%' and s_User='"+nick+"' order by s_Time desc";
						int num=UtilDao.getNum(sql2);
						out.write("<div class='day'>"+a+"<共收藏<span>"+num+"</span>个视频></div><br>");
						rs=st.executeQuery("select * from store where s_Time like '%"+a+"%' and s_User='"+nick+"' order by s_Time desc");
						while(rs.next()){
							String user=rs.getString(2);//收藏者
							url=rs.getString(3);//收藏的视频链接
							String vname=rs.getString(4);//收藏的视频名
							img=rs.getString(5);//收藏的视频的图片路径
							type=rs.getString(6);//收藏的视频类型
							time=rs.getString(7);//收藏时间
							%>
							<div class='single'>
								<%=time %>
								<a href="player.jsp?type=<%=type%>&url=<%=url%>" target='_blank'>
								<img src="<%=img%>"></img>
								</a>
								<span><%=vname%></span>
								&nbsp;
								<%
								if(nick.equals(users)){//必须是本人才可移除自己看单中的视频
									%>
								 <font color='red'><span onclick="remove('<%=vname%>','<%=url%>',2)">移除</span></font>
									<%
								}
								 %>
							</div>
							<%
						}
						rs.close();
						st.close();
						out.write("<hr width='100%' hreight='10px'>");
					}
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				%>
		 </div>
		</div>
	</div>
	<script src="layui.js" charset="utf-8"></script>
	<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
	<script>
	layui.use('element', function(){
	  var $ = layui.jquery
	  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
	  //触发事件
	  $('.site-demo-active').on('click', function(){
	    var othis = $(this), type = othis.data('type');
	    active[type] ? active[type].call(this, othis) : '';
	  });
	});
	</script>
</body>
</html>
