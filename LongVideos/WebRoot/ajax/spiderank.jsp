<%@page import="bean.VideoObj"%>
<%@page import="utils.Movie"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String type=request.getParameter("type");//爬取的类型
String main="https://www.360kan.com";
ArrayList<VideoObj> vos=Movie.getdata(main);
//System.out.println(vos.size());
int begin=0;//开始序号
int end=0;//结束序号
int j=0;//排行榜中序号
if(type.equals("dianshi")){
	begin=0;
	end=10;
}else if(type.equals("zongyi")){
	begin=10;
	end=20;
}else if(type.equals("dianying")){
	begin=20;
	end=30;
}else{
	begin=30;
	end=40;
}
for(int i=begin;i<end;i++){
	VideoObj vo=vos.get(i);
	int pai=vo.getPai();
	String sp="";
	String name=vo.getName();
	String href=vo.getHref();
	String pn=vo.getPn();
	if(name.length()>6){
		name=name.substring(0,5)+"...";
	}
	//获取播放链接
	String purl=Movie.getPUrl(href);
	if(j==0){
		sp="<first>"+(j+1)+"</first>";
	}else if(j==1){
		sp="<second>"+(j+1)+"</second>";
	}else if(j==2){
		sp="<third>"+(j+1)+"</third>";
	}else{
		sp="<other>"+(j+1)+"</other>";
	}
	out.write(sp+"&nbsp;<a href=\"/player.jsp?type="+type+"&url="+href+"\" target='_blank'>"+name+"</a>&nbsp;<font>"+vo.getPn()+"</font><br>");
	j++;
}
%>