<%@page import="dao.UtilDao"%>
<%@page import="com.longbro.bean.SearchBean"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String value=request.getParameter("value");
value=URLDecoder.decode(value, "utf-8");
String nick=request.getParameter("nick");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>搜索提示框</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
     <%
        if(value.isEmpty()){//如果输入框为空，则加载历史搜索和搜索榜
	      	//对已登录用户，从数据库（或cookie）加载其搜索记录
	      	if(!nick.equals("")){//已登录用户则加载历史搜索中最新六个
	      		String sea="select * from srecord where s_Searcher='"+nick+"' order by s_Id desc limit 0,6";
				ArrayList<SearchBean> sbs= UtilDao.getSearchRecord(sea);
				if(sbs.size()>0){
					%>历史搜索：<br><%
					for(int i=0;i<sbs.size();i++){
						String c=sbs.get(i).getContent();
						String t=sbs.get(i).getTime();
						%>
						 <font color="black"><span onclick="fuzhi('<%=c%>')"><%=c%></span>
						<%
						if(i==2||(i/3==1&&i!=3)){//i=2换行，i=3的倍数（i=3除外）换行
							out.write("<br>");
						}
					}
					
				}
	      	}
			
			//加载搜索记录排行榜
			String seaAll="select * from srecord order by s_Id asc limit 0,8";
			ArrayList<SearchBean> sbsa= UtilDao.getSearchRecord(seaAll);
			int xu=1;
			if(sbsa.size()>10){
				for(SearchBean sb:sbsa){
					String c=sb.getContent();
					String t=sb.getTime();
					if(xu<4){
						%>
		      		    <p onclick="fuzhi('<%=c%>')"><font color="red"><%=xu %>&nbsp;</font><%=c%></p><br>
						<%
					}else{
						%>
		      		    <p onclick="fuzhi('<%=c%>')"><font color="black"><%=xu %>&nbsp;</font><%=c%></p><br>
						<%
					}
					
					xu++;
				}
				
			}
			%>
	    	<br><br><p onclick="fuzhi('创业时代')"><font color="red">1</font> 创业时代</p><br>
			<p onclick="fuzhi('斗破苍穹')"><font color="red">2</font> 斗破苍穹</p><br>
			<p onclick="fuzhi('解忧杂货店')"><font color="red">3</font> 解忧杂货店</p><br>
			<p onclick="fuzhi('硬汉2')"><font color="black">4</font> 硬汉2</p><br>
			<p onclick="fuzhi('巨齿鲨')"><font color="black">5</font> 巨齿鲨</p><br>
			<p onclick="fuzhi('如懿传')"><font color="black">6</font> 如懿传</p><br>
			<p onclick="fuzhi('许你浮生若梦')"><font color="black">7</font> 许你浮生若梦</p><br>
			<p onclick="fuzhi('快乐星球')"><font color="black">8</font> 快乐星球</p> 
	     <%
        }/* else{//输入框不为空，则根据输入框加载出匹配到的提示条目，并将输入关键词特殊显示
    	//String seaAll="select *,count(distinct s_Content)  from srecord where s_Content like '%"+value+"%';";
    	String seaAll="select * from srecord where s_Content like '%"+value+"%';";
 		System.out.println(seaAll);
    	ArrayList<SearchBean> sbsa= UtilDao.getSearchRecord(seaAll);
 		int xu=1;
		for(SearchBean sb:sbsa){
 			String c=sb.getContent();
 			//将关键词中的第一个红色显示，比如输入凉，凉凉的第一个凉红色显示
 			String rc=c.replaceFirst(value,"<font color=\"red\">"+value+"</font>");
 			String t=sb.getTime();
 			
 			xu++;
 		}
 		
     } */else{//输入框不为空，则根据输入框加载出匹配到的提示条目，并将输入框中关键词特殊显示
     	String seaAll="select * from video where v_Name like '"+value+"%' limit 0,10;";
  		System.out.println(seaAll);
     	ArrayList<String> names= UtilDao.getNames(seaAll);
  		int xu=1;
 		for(String name:names){
  			//将关键词中的第一个红色显示，比如输入凉，凉凉的第一个凉红色显示
  			String rc=name.replaceFirst(value,"<font color=\"red\">"+value+"</font>");
  			%>
       		    <p onclick="fuzhi('<%=name%>')">
       		    	<font color="black"><%=xu%>&nbsp;</font><%=rc%>
       		    </p><br>
  			<%
  			xu++;
  		}
  		
      }
     %>
  </body>
</html>
