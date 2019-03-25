<%@page import="dao.BlogsDao"%>
<%@page import="dao.CategoryDao"%>
<%@page import="beans.Blogs"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getParameter("utf-8");
String auth=request.getParameter("author");
if(auth.equals("long")){
    auth="赵成龙";
}else if(auth.equals("ying")){
    auth="赵盈";
}
String cate =request.getParameter("cate");//传递过来的是分类的id
String date=request.getParameter("date");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'viewBlog.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <%
       String sql="";
       //如果哪个等于all，就不对它加限制条件
       if(!auth.equals("all")&&!cate.equals("all")&&!date.equals("all")){//都不为全部0,0,0
          sql="select * from blogs where b_Author='"+auth+"' and cat_Id="+cate+" and b_Time like '%"+date+"%'";
       }else if(auth.equals("all")&&cate.equals("all")&&date.equals("all")){//都为全部1,1,1
          sql="select * from blogs "; 
       }else if(auth.equals("all")&&!cate.equals("all")&&!date.equals("all")){//1,0,0
          sql="select * from blogs where  cat_Id="+cate+" and b_Time like '%"+date+"%'"; 
       }else if(auth.equals("all")&&cate.equals("all")&&!date.equals("all")){//1,1,0
          sql="select * from blogs where b_Time like '%"+date+"%'"; 
       }else if(!auth.equals("all")&&!cate.equals("all")&&date.equals("all")){//0,0,1
          sql="select * from blogs where b_Author='"+auth+"' and cat_Id="+cate; 
       }else if(!auth.equals("all")&&cate.equals("all")&&!date.equals("all")){//0,1,0
          sql="select * from blogs where b_Author='"+auth+"' and b_Time like '%"+date+"%'"; 
       }else if(!auth.equals("all")&&cate.equals("all")&&date.equals("all")){//0,1,1
          sql="select * from blogs where b_Author='"+auth+"'"; 
       }else if(auth.equals("all")&&!cate.equals("all")&&date.equals("all")){//1,0,1
          sql="select * from blogs where cat_Id="+cate; 
       }
             BlogsDao inDao=new BlogsDao();
             ArrayList<Blogs> ar= inDao.queryBlogs(sql);
             CategoryDao c=new CategoryDao();
             %>
               <table border="1" width="900px" align="left">
             <tr>
                 <td>编辑
                 <td>内容长度</td> 
                 <td>b_Id</td>
                 <td>标题</td>
                 <td>内容</td>
                 <td>发布时间</td>
                 <td>作者
                 <td>图片路径
                 <td>阅读量
                 <td>评论数
                 <td>分类
                 <td>删除
             </tr>
             <%
             int l=0;
             for(int i=0;i<ar.size();i++){
                 Blogs blog=ar.get(i);
                 int id=blog.getId(); //根据id来查看某条博客的全部内容
                 String title=blog.getTitle();
                 if(title.length()>6){
                    title=title.substring(0,5)+"...";
                 }
                 String content=blog.getContent();
                 l=content.length();
                 if(content.length()>9){
                    content=content.substring(0,8)+"...";
                 }
                 String time=blog.getTime().substring(0,10);
                 String author=blog.getAuthor();
                 String imgPath=blog.getImgPath();
                 int readNum=blog.getViewNum();
                 int comment=blog.getComment();
                 int c_Id=blog.getCat_Id();
                 
                 String cat_Name=c.queryCatNameById(c_Id);
               
                 %>
                   <tr>
                 <td><a href='/management/editBlog.jsp?id=<%=id%>'>编辑</a> 
                 <td><%=l %></td>                 
                 <td><%=id %></td>
                 <td><%=title %></td>
                 <td><%=content %></td>
                 <td><%=time%></td>
                 <td><%=author %>
                 <td><%=imgPath %>
                 <td><%=readNum %>
                 <td><%=comment %>
                 <td><%=cat_Name %>
                 <td><em onclick="delete1(<%=id%>)">删除</em>
             </tr>
                 <%
               
             }
             out.print("</ul>");
             
       %>
  </body>
  <script type="text/javascript">
    //通过js删除博客
      function delete1(id) {
      var r=confirm("确定删除该博客？");
	  if(r==true){
	      //window.location.assign='/DeleteBlog?id='+id;//服务器上不能用 
	      window.open('/DeleteBlog?id='+id,'_self');
	  }else{
	    
	  }
	}
  </script>
</html>
