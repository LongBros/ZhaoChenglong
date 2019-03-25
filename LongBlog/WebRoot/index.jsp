<%@page import="java.net.InetAddress"%>
<%@page import="beans.TimeNum"%>
<%@page import="dao.BlogsDao"%>
<%@page import="com.longbro.util.OtherUtil"%>
<%@page import="beans.Blogs"%>
<%@page import="com.longbro.util.JdbcUtil"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<!--
//                          _ooOoo_
//                         o8888888o
//                         88" . "88
//                         (| ^_^ |)
//                         O\  =  /O
//                      ____/`---'\____
//                    .'  \\|     |//  `.
//                   /  \\|||  :  |||//  \
//                  /  _||||| -:- |||||-  \
//                  |   | \\\  -  /// |   |
//                  | \_|  ''\---/''  |   |
//                  \  .-\__  `-`  ___/-. /
//                ___`. .'  /--.--\  `. . ___
//              ."" '<  `.___\_<|>_/___.'  >'"".
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//            \  \ `-.   \_ __\ /__ _/   .-` /  /
//      ========`-.____`-.___\_____/___.-`____.-'========
//                           `=---='//
//
//            拦截插件累计拦截逗比攻击"3838438"次！
//        .............................................
//                  佛祖保佑         永无BUG
//                                                               
//                     江城子 . 程序员之歌
// 
//               十年生死两茫茫，写程序，到天亮。
//                     千行代码，Bug何处藏。
//               纵使上线又怎样，朝令改，夕断肠。           
// 
//               领导每天新想法，天天改，日日忙。
//                    相顾无言，惟有泪千行。
//               每晚灯火阑珊处，夜难寐，加班狂。
//
-->
  <head>
    <base href="<%=basePath%>">
    <title>Long Bro博客|一个分享Java编程和web技术的博客</title>
	<meta name="author" content="Long Bro">
	<meta name="keywords" content="个人博客,Long Bro的博客,Long Bro,博客,Java学习聚集地，情感博客">
	<meta name="description" content="Long Bro个人博客，一个分享Java技术的博客，个人情感博客，">
   <link rel="shortcut icon" href="/images/useful/logo2.png" type="image/x-icon"/>
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet"><!-- 内应该有pageList的css -->
    <link href="css/my.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
    
    <!-- <script>
	    var _hmt = _hmt || [];
	   (function() {
	        var hm = document.createElement("script");
	        hm.src = "https://hm.baidu.com/hm.js?2062e45c460e154dde1d90601acc3314";
	        var s = document.getElementsByTagName("script")[0]; 
	       s.parentNode.insertBefore(hm, s);
	   })();
	</script> -->
  </head>
  <body onload="initCont()">
	<div class="topnav">
	    <a href="http://www.zy52113.com" target="_blank">Long Bro影院</a>——查看其它<a href="http://erya.longqcloud.cn/" target="_blank">尔雅考试</a>
	</div>
	<div id="wrapper">
   		 <%@include file="header.jsp" %>
  		 <div class="jztop"></div><!-- 卷轴顶部 -->
  		 <div class="container">
    		<div class="bloglist f_l">
		      <%
		        String pageId=request.getParameter("pageId");
		        int pageI=1;
		        //将所有的页码添加至数组链表
				ArrayList<String> arr=new ArrayList<String>();
				for(int i=1;i<60;i++){
					String t=i+"";
					arr.add(t);
				}
				//实现直接进入为第一页，点击页码进入，为页码的
				for(int i=0;i<arr.size();i++){
					if((arr.get(i)).equals(pageId)){//如果有参数，且与数组中某元素相同，则将数组中该元素定为页码，转为整型
						pageI=Integer.parseInt(arr.get(i));
						break;//
					}
					if(i==(arr.size()-1)){//到循环的最后一个，还没有与之相 匹配的页码，说明是直接进去的，不是点击页码进去的
						pageI=1;
					}
				}
		        //首页展示IT技术以及学习笔记类型的博客
		        BlogsDao inDao=new BlogsDao();
		        String cata=" where cat_Id=1 or cat_Id=3;";
		        int num=inDao.queryBlogsNum(cata);//库中博客的数量
		        int pages=0;//博客页数
		        //每页五篇，计算出页数
		        if(num%5==0){
		            pages=num/5;
		        }else{
		            pages=num/5+1;
		        } 
		          //加入一条访问日志信息
		          OtherUtil.genLog(request);
					
		          //加载指定类型的博客
	             String is="select * from blogs where cat_Id=1 or cat_Id=3 order by b_Id desc limit "+((pageI-1)*5)+",5";
	             ArrayList<Blogs> ar= inDao.queryBlogs(is);
	             CategoryDao c=new CategoryDao();
	             for(int i=0;i<ar.size();i++){
	                 Blogs blog=ar.get(i);
	                 int id=blog.getId(); //根据id来查看某条博客的全部内容
	                 String title=blog.getTitle();
	                 String content=blog.getContent();
	                 String time=blog.getTime();
	                 String author=blog.getAuthor();
	                 String imgPath=blog.getImgPath();
	                 int readNum=blog.getViewNum();
	                 int comment=blog.getComment();
	                 int c_Id=blog.getCat_Id();
	                 String cat_Name=c.queryCatNameById(c_Id);
	                 content=content.replaceAll("<[.[^<]]*>", "");
	                 //注意，先去掉标签，在截取
	                 if(content.length()>88){//博客较长，只显示66个字符，后面跟......
	                     content=content.substring(0,88)+"......";
	                 }
	                  %>
	                 <%-- <h3><a title="<%=title%>" href="/blogs/<%=id%>.html" target="_blank"><%=title%></a></h3> --%>
	                 <h3><a title="<%=title%>" href="detailblog.jsp?id=<%=id%>" target="_blank"><%=title%></a></h3>
	                 
	                 <figure>
	                     <img  style="width:140px;height:120px;border-radius:50%;" src="<%=imgPath %>" alt='<%=imgPath%>' title='欢迎您访问LongBro博客'>
	                 </figure>
	                 <p><%=content%></p>
	                 <%-- <a title="<%=title%>" href="/blogs/<%=id%>.html" target="_blank" class="readmore">阅读全文&gt;&gt;</a> --%>
	                 <a title="<%=title%>" href="detailblog.jsp?id=<%=id%>" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
	                  <p   class="dateview">
	                     <span class="spanm"><%=time%></span>
	                     <span  class="spanm">作者：<%=author %></span>
	                     <span  class="spanm">个人博客：[<a href='cat_search.jsp?id=<%=c_Id%>' target='_blank'><%=cat_Name%></a>]
	                     </span><span class="viewNum"><%=readNum%></span>
	                     <span class='commentNum'><%=comment%></span>
	                  </p>
	                 <%
	             }  
		       %>          
		    </div>
			
			<div class="r_box f_r">
			      <%-- <%
			        //每次进入，随机放置一张图片
			        Random ran=new Random();
			        int p=ran.nextInt(18);
			        String lovesen=OtherUtil.loveSen(p);//爱的句子
			        out.write("<h3 class=\"tit\" id='tit'>"+lovesen+"</h3>"); 
			      %> --%>
			      <h3 class="tit" id='tit'>分享-553影院源代码</h3>
      			 <div class="ad"> 
      			 	<a href="sponsor/sshare.jsp">
      			 		<%-- <img id="imag" title="干货街-有偿干货" style="width:250px;height:120px;" src="images/yinger/love<%=p%>.jpg"> --%>
      			 		<img id="imag" title="干货街-有偿干货" style="width:250px;height:120px;" src="images/share/cinema.png">
      			 	</a>
      			 </div>
      			 <script type="text/javascript">
					function change(){
						//var sens=["和你在一起好开心的呢","平西湖畔的的回忆","平院的操场,你,和我","宝贝好可爱的呢","要一直笑下去哟亲爱的","盈儿萌萌哒"
						//,"老婆，我爱你","小可爱去郑航的路上","这个就是我了，嘻嘻","宝宝，要开心呢","是不是挺帅的，嘿嘿","刚起床头发有点乱呢"
						//,"看看百年前的Long Bro颜值如何","真可爱，么么","嘻嘻，我好喜欢你","等你下课一起走好吗","嘿嘿，很爱很爱你","欧耶，又看见小可爱了呢",
						//"多喜欢你"];
						//var i=Math.round(Math.random()*(sens.length-1-0)+0);
						//document.getElementById("tit").innerHTML=sens[i];
						//document.getElementById("imag").src="images/yinger/love"+i+".jpg";
						
						//2018-12-15将图片改为资源文件
						var sens=["553影院源代码","553Music源代码","近百本英语电子词汇书",
						          "五本电子词汇书","CET4电子词汇书","CET6电子词汇书"
						          ,"考研电子词汇书","计算机专业电子词汇书","金融方面电子词汇书"];
						var imgPath=["cinema","music","every","all","CET-4","CET-6","graduate","computer","finance"];
						var i=Math.round(Math.random()*(sens.length-1-0)+0);
						document.getElementById("tit").innerHTML="分享-"+sens[i];
						document.getElementById("imag").src="images/share/"+imgPath[i]+".png";
					}
					window.setInterval("change();", 5000);
			   </script>
		      <div class="tuwen">
		        <h3 class="tit">博客分类</h3>
		        <%  ArrayList<Category> ca=c.queryCategories();
		            out.write("<ul>");
		            for(int i=0;i<ca.size();i++){
		                  Category category=ca.get(i);
		                  int cat_Id=category.getCat_Id();
		                  String cat_Name=category.getCat_Name();
		                  String cs=" where cat_Id="+cat_Id;//查询语句的条件
		                  int cat_Num=inDao.queryBlogsNum(cs);//库中指定类型博客的数量
		                  out.write("<li><p><a href='cat_search.jsp?id="+cat_Id+"' target='_blank'>"+cat_Name+"</a>&nbsp;共("+cat_Num+")篇</p></li>");
		              }
		            out.write("</ul>");
		         %>
		      </div>
     		 <div class="tuwen">
		        <h3 class="tit">日期归类</h3>
		        <%
		             //left(b_Time,10)      left(b_Time,7)
		             String what="distinct left(b_Time,7) as y_m";
		             ArrayList<TimeNum> tnar=inDao.queryBlogs(what,null);
		             out.write("<ul>");
		             for(int i=0;i<tnar.size();i++){
		                TimeNum tn=tnar.get(i);
		                String y_m=tn.getTime();
		                int amo=tn.getNum();
			            out.write("<li><p><a href='date_search.jsp?time="+y_m+"'>"+y_m+"</a>&nbsp;&nbsp共("+amo+")篇</p></li>");		             	
		             }
		             out.write("</ul>");
		           String select="select distinct left(b_Time,10) as y_m  from blogs;";//年月日
		           String select1="select distinct left(b_Time,7) as y_m  from blogs;";//年月
		         %>
		      </div>
		      <div class="tuwen">
		        <h3 class="tit">阅读量排行</h3>
		        <ul>
		        <%
		            String re="select * from blogs order by b_ViewNum desc limit 0,10";
		            ArrayList<Blogs> b=inDao.queryBlogs(re);
		            for(int i=0;i<b.size();i++){
		                Blogs bl=b.get(i);
		                int b_Id=bl.getId();
		                String title=bl.getTitle();
		                int viewNum=bl.getViewNum();
		                if(i==0){//阅读量排第一
		                   out.write("<li><p><pai class='first'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
		                }else if(i==1){//阅读量排第二
		                   out.write("<li><p><pai class='second'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
		                }else if(i==2){
		                   out.write("<li><p><pai class='third'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
		                }else{
		                   out.write("<li><p><pai class='other'>"+(i+1)+"</pai>.<a href=\"/detailblog.jsp?id="+b_Id+"\" target='_blank'>"+title+"</a>("+viewNum+")</p></li>");
		                }
		            }		            
		         %> 
		         </ul>
		  	 </div>
     </div>
  </div><!-- class="container"的结束 -->
  <!-- container代码 结束 -->
  <div class="jzend"></div><!--卷轴尾部 -->
  
  <div class="pagelist">页次：<%=pageI %>/<%=pages %> 每页5篇 共<%=num %>篇
  <% if(pageI>1){
       %>
         <a href="/index.jsp?pageId=<%=pageI-1%>">上</a>
       <%
    } %>
  <% if(pages<=6){//页数小于等于6，直接输出6个页数
        for(int j=1;j<=pages;j++){
         if(j==pageI){
              out.write("<current><a>"+j+"</a></current>");     
         }else{
              out.write("<a href=\"/index.jsp?pageId="+j+"\">"+j+"</a>"); 
         }
       } 
     }else{//页数大于6，算法设计只显示6个页数
        if(pageI>pages-6){//当前页码大于总页码-6，输出后六页
           for(int j=pages-5;j<=pages;j++){
              if(j==pageI){
              out.write("<current><a>"+j+"</a></current>");     
         }else{
              out.write("<a href=\"/index.jsp?pageId="+j+"\">"+j+"</a>"); 
         }
         }
        }else{//当前页码小于总页码-6，输出当前页码后的六页
            for(int j=pageI;j<pageI+6;j++){
               if(j==pageI){
                   out.write("<current><a>"+j+"</a></current>");     
               }else{
                    out.write("<a href=\"/index.jsp?pageId="+j+"\">"+j+"</a>"); 
               }
           }
        }
     } %>
   <% if(pageI<pages){
      %>
          <a href="/index.jsp?pageId=<%=pageI+1%>">下</a>
      <%
   }
    %>
  
  </div> 
  <%@include file="footer.jsp" %>
</div>
</body>
</html>
