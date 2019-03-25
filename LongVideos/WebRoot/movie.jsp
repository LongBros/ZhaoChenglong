<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="utils.Movie"%>
<%@page import="com.longbro.bean.Category"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String cate=request.getParameter("cate");//类型
String year1=request.getParameter("year");
String cpage=request.getParameter("page");
int pageI=1;
String cateI="";
String yearI="";
String cateName="";//分类名，主要用于title中，方便百度蜘蛛爬取时带上分类名
//类型
if("other".equals(cate)){
	cateI="other";
	cateI="其他";
}else if("all".equals(cate)||StringUtils.isEmpty(cate)){
	cateI="all";
	cateName="全部";
}else{
	cateI=cate;
	cateName=Movie.getCatgoryNameById("movie", cateI);
	/* ArrayList<String> arr1=new ArrayList<String>();
	for(int i=100;i<135;i++){
		String t=i+"";
		arr1.add(t);
	}
	//实现直接进入为全部，点击页类型进入，为类型的
	for(int i=0;i<arr1.size();i++){
		if((arr1.get(i)).equals(cate)){
			cateI=arr1.get(i);
			cateName=Movie.getCatgoryNameById("movie", cateI);
			break;//
		}
		if(i==(arr1.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
			cateI="all";
			cateName="全部";
		}
	}		 */
}
//年份
if("other".equals(year1)){
	yearI="other";
}else if("all".equals(year1)||StringUtils.isEmpty(year1)){
	yearI="all";
}else{
	yearI=year1;
	/* ArrayList<String> arr1=new ArrayList<String>();
	for(int i=2000;i<2019;i++){
		String t=i+"";
		arr1.add(t);
	}
	//实现直接进入为全部，点击页
	for(int i=0;i<arr1.size();i++){
		if((arr1.get(i)).equals(year1)){
			yearI=arr1.get(i);
			break;//
		}
		if(i==(arr1.size()-1)){//到循环的最后一个，还没有与之相 匹配的年份，说明是直接进去的
			yearI="all";
		}
	}	 */	
}
//页码
if(StringUtils.isEmpty(cpage)){//默认为首页
	pageI=1;
}else{
	pageI=Integer.parseInt(cpage);
}
/* //将所有的页码添加至数组链表
ArrayList<String> arr=new ArrayList<String>();
for(int i=1;i<26;i++){
	String t=i+"";
	arr.add(t);
}
//实现直接进入为第一页，点击页码进入，为页码的
for(int i=0;i<arr.size();i++){
	if((arr.get(i)).equals(cpage)){//如果有参数，且与数组中参数向同，则将数组中该元素定位页码，转为整型
		pageI=Integer.parseInt(arr.get(i));
		break;//
	}
	if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的
		pageI=1;
	}
} */
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>电影-<%=cateName%>(分类)-LongBro影院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="/images/util/logo2.png" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="css/layui.css">
	<link rel="stylesheet" type="text/css" href="css/mainpage.css">
  </head>
  <body>
		<ul class="layui-nav layui-bg-green">
		  <li class="layui-nav-item"><a href="/MainPage">旧版-LongBro影院</a></li>
		  <li class="layui-nav-item"><a href="/index.jsp">首页</a></li>
		  <li class="layui-nav-item"><a href="/series.jsp">电视剧</a></li>
		  <li class="layui-nav-item layui-this"><a href="/movie.jsp">电影</a></li>
		  <li class="layui-nav-item"><a href="/variety.jsp">综艺</a></li>
		  <li class="layui-nav-item"><a href="/cartoon.jsp">动漫</a></li>
		  <li class="layui-nav-item"><a href="toast.jsp">吐槽墙</a></li><img alt="" src="/images/util/hot2.gif">
		</ul>
		<ul class="layui-nav layui-layout-right layui-bg-green" >
	      <li class="layui-nav-item">
	      	<form action="/search.jsp" method="post" target="_blank">
		      <input type="text" class="input"  placeholder="尽情搜吧" name="v_name">
		      <input type="submit" class="search" value='搜索'>
		  	</form>
	      </li>
	    </ul>
	<%
		out.write("<div class='cate'>");
		out.write("类型:&nbsp;");
		ArrayList<Category> cs=Movie.getCate("dianying");//根据分类获取分类下的类型
		out.write("<a href='/movie.jsp?cate=all&year="+yearI+"&page=1'>全部</a>&nbsp;");

		for(Category c:cs){
			String cat=c.getId();
			String name=c.getName();
			String url="";
			if(name.contains(cateName)){//因name中可能含有空格，故不用equal
				url="<a href='/movie.jsp?cate="+cat+"&year="+yearI+"&page=1'><font color='orange'>"+name+"</font></a>&nbsp;";
			}else{
				url="<a href='/movie.jsp?cate="+cat+"&year="+yearI+"&page=1'>"+name+"</a>&nbsp;";
			}
			out.write(url);
		}
		out.write("<br>年份:&nbsp;");
		ArrayList<String> years=Movie.getYear("dianying");//根据分类获取分类下的类型
		out.write("<a href='/movie.jsp?cate="+cateI+"&year=all&page=1'>全部</a>&nbsp;");

		String y="";
		for(String year:years){
			if(year.equals("other")){
				y="更早";
			}else{
				y=year;
			}
			if(yearI.equals(year)){//当前页特殊显示
				 out.write("<a href='/movie.jsp?cate="+cateI+"&year="+year+"&page=1'><font color='orange'>"+y+"</font></a>&nbsp;");
				
			}else{
				 out.write("<a href='/movie.jsp?cate="+cateI+"&year="+year+"&page=1'>"+y+"</a>&nbsp;");
			}
		}
		out.write("</div>");	
		String main="https://www.360kan.com";
        String url="https://www.360kan.com/dianying/list.php?rank=rankhot&cat="+cateI+"&area=all&year="+yearI+"&pageno="+pageI;
        Document doc=Jsoup.connect(url).get();
		Elements plays=doc.getElementsByClass("js-tongjic"); 
		for(Element play: plays){
			String p=play.toString();
			
			String[] s=p.split(">");
		    String hr=s[0]+">";
		    //视频详细信息界面链接
		    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
			String sr=s[2]+">";
			//视频图片链接
			String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
			String year,name,score="",actor;
		    if(p.contains("付费")){
		    	//有评分的和无评分的，不一样
				if(p.contains("point")){//有评分
					year=s[7].substring(0,4);					  
					name=s[15].split("<")[0];					  
					score=s[16].split("<")[0];					  
					actor=s[18].split("<")[0];
				}else{
					year=s[7].substring(0,4);					  
					name=s[13].split("<")[0];					  
					actor=s[16].split("<")[0];
				}
			}else{
				if(p.contains("point")){//有评分
					year=s[5].substring(0, 4);					  
					name=s[13].split("<")[0];					  
					score=s[7].split("<")[0];
					actor=s[16].split("<")[0];
				}else{
					year=s[5].substring(0, 4);					  
					name=s[11].split("<")[0];					  
					actor=s[14].split("<")[0];
				}
			}
			if(name.length()>11){
				name=name.substring(0,11)+"...";
			}
			if(actor.length()>16){
				actor=actor.substring(0, 16)+"...";
			}
			if(score.equals("")){
				score="";
			}
			if(year.length()!=4){
				year="";
			}
			out.write("<div class='whole'><a href=\"/player.jsp?type=dianying&url="+hre+"\" target='_blank'>"
			  		+ "<img src='"+src+"' title='"+name+"' alt='"+src+"'>"
			  				+ "<span>"+name+"&emsp;<font color='red' size='+1'>"+score+"</font></span><br><p>"+actor+"</p></a></div>");
		   }
		out.write("<center><div class='page'>");
		if(pageI>1){//当前页码大于1，显示上一页按钮
			out.write("<a  href=\"/movie.jsp?cate="+cateI+"&year="+yearI+"&page="+(pageI-1)+"\">上</a>");
		}
		if(pageI>25-6){//当前页码大于总页码-6，输出后六页
		    for(int j=25-5;j<=25;j++){
		        if(j==pageI){
		             out.write("<current><a>"+j+"</a></current>");     
		        }else{
		             out.write("<a href=\"/movie.jsp?cate="+cateI+"&year="+yearI+"&page="+j+"\">"+j+"</a>"); 
		        }
		    }
		}else{//当前页码小于总页码-6，输出当前页码后的六页
		    for(int j=pageI;j<pageI+6;j++){
		    	if(j==pageI){
		             out.write("<current><a>"+j+"</a></current>");     
		        }else{
		             out.write("<a href=\"/movie.jsp?cate="+cateI+"&year="+yearI+"&page="+j+"\">"+j+"</a>"); 
		        }
		    }
		}
		if(pageI<25){//当前页码小于24，显示下一页按钮
			out.write("<a  href=\"/movie.jsp?cate="+cateI+"&year="+yearI+"&page="+(pageI+1)+"\">下</a>");
		}
		out.write("</div></center>");
       %>
	<center><hr width='80%' height='5px'></center><br>
	<%@include file="footer.jsp" %>
		<script src="layui.js" charset="utf-8"></script>
		<script>
		layui.use('element', function(){
		  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
		  
		  //监听导航点击
		  element.on('nav(demo)', function(elem){
		    //console.log(elem)
		    layer.msg(elem.text());
		  });
		});
		</script> 
 </body>
</html>
