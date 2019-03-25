<%@page import="beans.Author"%>
<%@page import="dao.AuthorDao"%>
<%@page import="beans.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page import="beans.Blogs"%>
<%@page import="dao.BlogsDao"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改博文</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
      <%  
          //根据博客id查询该条博客的信息
          String id=request.getParameter("id");
          String require="select * from blogs where b_Id="+id;
          BlogsDao dao=new BlogsDao();
          ArrayList<Blogs> ar=dao.queryBlogs(require);//需要明确此时链表中只有一条Blogs
          Blogs blog=ar.get(0);
          String title=blog.getTitle();//博客标题
		  String content=blog.getContent();//博客内容
		  System.out.println(content);
		  
		  //将博客内容中的引号换为HTML认识的引号特殊字符
		  content=content.replaceAll("\"", "&quot;");
		  //System.out.println(content);
		  String time=blog.getTime();//博客发布时间
		  String author=blog.getAuthor();//作者
		  String imgPath=blog.getImgPath();//图片路径
		  int viewNum=blog.getViewNum();//
		  int cat_Id=blog.getCat_Id();//类别id
		  //根据类别id查询类别名
		  CategoryDao dao1=new CategoryDao();
		  String cat_Name=dao1.queryCatNameById(cat_Id);
		  %>
	<form action="/LongBlog/UpdateBlog?id=<%=id%>" method="post">
		 分类:<select name="category">
				 <option value="<%=cat_Name%>" checked><%=cat_Name %></option>
		        <% 
		        //获取所有分类以供修改，选中的为该博客修改前的所属分类
		        CategoryDao c=new CategoryDao();
		        ArrayList<Category> ca=c.queryCategories();
		            for(int i=0;i<ca.size();i++){
		                  Category category=ca.get(i);
		                  String n=category.getCat_Name();
		                  if(!n.equals(cat_Name)){
		                       out.write("<option value='"+n+"'>"+n+"</option>");
		                  }
		              }            
		         %>
		         </select>
         <br><br> 作者<input type="radio" name="author" value="<%=author %>" checked><%=author%> 
       <%
           AuthorDao ad=new AuthorDao();
           ArrayList<Author> as= ad.queryAuthors(); 
           for(int i=0;i<as.size(); i++){
              Author a=as.get(i);
              //System.out.print(i);
              String name=a.getA_Name();
              if(!name.equals(author)){
                  out.write("<input type='radio' name='author' value='"+name+"'>"+name+"");
              }
           }   
           // style="height: 0px; width:0px"
        %>
        <br><br> 图片路径<input type="text" name="image" value="<%=imgPath %>">
        <br><br>标题:<input type="text" name="title" value="<%=title%>" style="width: 100%;height:28px;"></input>
        <br><br>内容:<br><textarea id="content" name="content" rows="9" cols="100"><%=content %></textarea>
        <div id="editor"></div>
        <input type="submit" value="修改" style="height: 45px; width: 72px">
   </form>
  	 温馨提示:博客修改的时间不予保存，修改后的发布时间仍为修改前的发布时间！
   <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
   <script type="text/javascript" src="../editor/wangEditor.min.js"></script>
   <script type="text/javascript">
        var E = window.wangEditor;
        var editor = new E('#editor');
        editor.customConfig.uploadImgShowBase64 = true;//使用 base64 保存图片
        var $content=$('#content');
        editor.customConfig.onchange=function(html){
        //监控变化，同步更新到textarea
           $content.val(html);
        };
        editor.create();
        editor.txt.html("<%=content%>");
        //初始化textarea的值
        $content.val(editor.txt.html());
  </script>      
  </body>
</html>
