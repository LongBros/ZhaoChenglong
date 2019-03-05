<%@page import="java.net.URLDecoder"%>
<%@page import="dao.UtilDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url=request.getParameter("url");//要删除的播放或收藏的视频的链接
String id=request.getParameter("id");//要删除的评论的id
String table=request.getParameter("i");//要删除的表的类型，0评论表，1播放记录表，2收藏记录表
//从cookie中获取用户名
Cookie[] cookies=request.getCookies();
String nick=null;
for(int i=0;cookies!=null&&i<cookies.length;i++){
	if(cookies[i].getName().equals("user")){
		nick=cookies[i].getValue().toString();
		nick=URLDecoder.decode(nick, "utf-8");
	}
}
String sql="";
String what="";
if(table.equals("0")){//评论表，刷新页面后生效
	what="该条评论已删除";
	sql="delete from comment where c_Id="+id;//执行删除操作的SQL语句	
}else if(table.equals("1")){//播放记录表
	what="已删除该播放记录";
	sql="delete from precord where p_Link='"+url+"' and p_Player='"+nick+"'";
}else if(table.equals("2")){//收藏记录表
	what="已移除该收藏";
	sql="delete from store where s_Url='"+url+"' and s_User='"+nick+"'";
}
UtilDao.delete(sql);
out.write(what);

%>
