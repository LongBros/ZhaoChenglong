package com.longbro.util;

import java.io.File;
import java.io.IOException;

public class HtmlGen {
	public static void main(String[] args) throws IOException {
		for(int i=1;i<60;i++){
			File file=new File("D:/apache-tomcat-7.0.68/webapps/LongBlog/blogs/"+i+".htm");
			file.createNewFile();
		}
		
	}

}
