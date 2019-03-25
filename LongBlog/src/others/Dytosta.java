package others;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Dytosta {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		spide();
	}
	private static void spide() throws IOException {
		for(int i=61;i<=61;i++){
			Document doc=Jsoup.connect("http://www.longqcloud.cn/detailblog.jsp?id="+i+"").get();
			 String con=doc.toString();
			 //System.out.println(con);
			 if(con.isEmpty()){
				 System.out.println("无该id对应的博文");
			 }else{
				//新建html文件，内容为该篇博客的doc，名为id+html
				 File file=new File("C:/Users/Administrator/Desktop/1/"+i+".html");
				 if (!file.exists()) {
					file.createNewFile();
					//向文件中写入文本
					writeStrtoFile(con, file);
				}else{//向文件中写入文本
					writeStrtoFile(con, file);
				}
				 System.out.println("第"+i+"篇已静态化");
			 }
			 
		}
	}
	public static void writeStrtoFile(String str,File file) throws IOException{
		try {
			FileOutputStream fos=new FileOutputStream(file);
			fos.write(str.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

}
