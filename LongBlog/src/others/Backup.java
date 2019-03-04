package others;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import beans.Blogs;
import beans.Comment;
import beans.ViewCon;
import dao.BlogsDao;
import dao.CommentDao;
import dao.ViewConDao;

public class Backup {
	public static void main(String[] args) {
		backupBlog();
		backupCom();
		backupView();
	}
	
	 //备份评论
	public static void backupCom(){
		String cs="";//所有评论
		String c="";//当条评论
		CommentDao dao=new CommentDao();
		ArrayList<Comment> comments=dao.queryCom("select * from comment");
		for(Comment comment :comments){
			int id=comment.getId();//评论id
			int bid=comment.getB_Id();//评论所属博客id
			String cont=comment.getContent();//内容
			String time=comment.getTime();//评论时间
			String ip=comment.getIp();//评论者的ip
			String path=comment.getPath();//评论者头像图片路径
			
			c=id+"&"+bid+"&"+cont+"&"+time+"&"+ip+"&"+path+"】";//当条博文
			cs=cs+c;//所有博文
		}
		String path="D:/backup/longblog/comments.txt";

		writeDbtoFile(cs,path);
	}   
	//备份博文
	public static void backupBlog(){
		String bs="";//所有博文
		String b="";//当条博文
		BlogsDao dao=new BlogsDao();
		ArrayList<Blogs> blogs=dao.queryBlogs("select * from blogs");
		for(Blogs blog :blogs){
			int id=blog.getId();//博客id
			String title=blog.getTitle();//标题
			String cont=blog.getContent();//内容
			String time=blog.getTime();//发布时间
			String author=blog.getAuthor();//作者
			String path=blog.getImgPath();//图片路径
			int vnum=blog.getViewNum();//阅读量
			int cnum=blog.getComment();//评论数量
			int cat=blog.getCat_Id();//分类id
			
			b=id+"&"+title+"&"+cont+"&"+time+"&"+author+"&"+path+"&"+vnum+"&"+cnum+"&"+cat+"】";//当条博文
			bs=bs+b;//所有博文
		}
		String path="D:/backup/longblog/blogs.txt";

		writeDbtoFile(bs,path);
	}
	
	//备份阅读情况
	public static void backupView(){
		String vs="";//所有评论
		String v="";//当条评论
		ViewConDao dao=new ViewConDao();
		ArrayList<ViewCon> views=dao.queryView("select * from viewcon");
		for(ViewCon view :views){
			int id=view.getV_Id();//评论id
			int bid=view.getB_Id();//评论所属博客id
			String time=view.getV_Time();//评论时间
			String ip=view.getV_Ip();//评论者的ip
			
			v=id+"&"+bid+"&"+time+"&"+ip+"&"+"】";//当条博文
			vs=vs+v;//所有博文
		}
		String path="D:/backup/longblog/viewcons.txt";

		writeDbtoFile(vs,path);
	}   	
	public static void writeDbtoFile(String bs,String path){
		//循环执行完毕后，bs中已存入所有博文
		try {
			File file=new File(path);
			if(file.exists()){
				file.delete();
				file.createNewFile();
			}else{
				file.createNewFile();
			}
			FileOutputStream out=new FileOutputStream(file);
			out.write(bs.getBytes());
			
//			FileWriter out=new FileWriter(file);
//			BufferedWriter writer=new BufferedWriter(out);
//			writer.write(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
