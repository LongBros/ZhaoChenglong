<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>撰写您的博客，让更多的人看到</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
   <link rel="shortcut icon" href="/LongBlog/images/useful/logo2.png" type="image/x-icon"/>
	
	<link href="css/base.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/modernizr.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>

  </head>
  
  <body>
  <div class="topnav">
      <a href="http://www.longqcloud.cn/" target="_blank">Long Bro博客</a>——查看其它<a href="http://tv.zy52113.com" target="_blank">Long Bro影院</a>
  </div>
    
    <%@include file="header.jsp" %>
    <%
       Cookie[] cookie=request.getCookies();
       String acc="";
       for(int i=0;cookie!=null&&i<cookie.length;i++){
           if(cookie[i].getName().equals("account")){//cookie中有登录过得信息，直接进入管理界面
                 acc=cookie[i].getValue().toString();
                if(!acc.equals("")){//cookie中账号不为空，说明已登录过
                   %>
                   <div class="writeBlog">
    <form action="/LongBlog/ReleaseBlog" method="post">
       <!--   选择作者：<br><br>
       <input name="author" type="radio" value="赵盈" checked>赵盈 &nbsp;
        <input name="author" type="radio" value="赵成龙">赵成龙<br><br><br> -->
        选择类别：</br><br>
        <select name="category" style="width: 80px; ">
        <%
        CategoryDao c=new CategoryDao();
        ArrayList<Category> ca=c.queryCategories();
            for(int j=0;j<ca.size();j++){
                  Category category=ca.get(j);
                  String cat_Name=category.getCat_Name();
                  out.write("<ul>");
                  out.write("<option value='"+cat_Name+"'>"+cat_Name+"</option>");
                  out.write("<ul>");
              }
         %>
        </select>
        <br><input type="text" width="100%" name="title" placeholder="标题" style="width: 100%;height: 28px; ">
        <textarea id="content" name="content" style="width:0%; height:0px;"></textarea>
        <div id="editor">
            <p>写一篇<b>漂亮的</b>博文.</p>
        </div>
        <input type="submit" value="发布" id="btn" style="width: 77px; height: 45px">
    </form>
    
    <script type="text/javascript" src="editor/wangEditor.min.js"></script>
    <script type="text/javascript">
        var E = window.wangEditor;
        var editor = new E('#editor');
        editor.customConfig.uploadImgShowBase64 = true;//使用 base64 保存图片
        // 隐藏“网络图片”tab------editor.customConfig.showLinkImg = false
        // 或者 var editor = new E( document.getElementById('#editor') )
        var $content=$('#content');
        editor.customConfig.onchange=function(html){
        //监控变化，同步更新到textarea
           $content.val(html);
        };
        
        // 配置服务器端地址
        //editor.customConfig.uploadImgServer = '/LongBlog/images/upload'
        // 将图片大小限制为 3M
        //editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024
        // 限制一次最多上传 5 张图片
        //editor.customConfig.uploadImgMaxLength = 5
        
        editor.create();
        //初始化textarea的值
        $text1.val(editor.txt.html());
    </script>
                   <%
                  break;
                }
            }else if(i==cookie.length-1){//遍历到最后一个cookie仍没有，说明未登录
              System.out.println("...............");
                    //for(int j=3;j>0;j--){
                        //Thread.sleep(1000);
                        //out.write("抱歉，你还未登录，"+j+"秒后为你跳转<br>"); 
                    //}
                    //out.write("正在为你跳转至首页");
                    //Thread.sleep(3000);
                    //response.sendRedirect("/LongBlog");
                    %>
                     <center><h1>抱歉，你还未登录，</h1>
                     <a href="/LongBlog/login.jsp?option=write">点此登录&gt;&gt;</a></center>                                                   
                    <%
                    
             }
       }
       %>
      
  
 </div>
   
   <%@include file="footer.jsp" %>
   
  </body>
</html>
