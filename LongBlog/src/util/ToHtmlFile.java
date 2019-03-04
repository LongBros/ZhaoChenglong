package util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ToHtmlFile extends HttpServlet {
	String url="";
    String name = ""; 
    ServletContext sc = getServletContext(); 
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String file_name=request.getParameter("filename");//要访问的jsp文件名
		String realName = request.getParameter("realName");
		String path = request.getParameter("path");
		String realPath = request.getParameter("realPath");
		if (realName == null || realName == "") { 
            int a = 0; 
            a = file_name.indexOf("=") + 1; 
            realName = file_name.substring(a); 
            if (realName.indexOf(".")>0) { 
             realName = file_name.substring(0, file_name.indexOf(".")); 
            } 
           }
		if (path == null || path == "") { 
            url = "/" + file_name;// 这是你要生成HTML的jsp文件,如 
           } else { 
            url = "/" + path + "/" + file_name;// 这是你要生成HTML的jsp文件,如 
           } 
		// 下面构造要保存的文件名，及路径。 
        // 1、如果有realPath，则保存在realPath下。 
        // 2、如果有path则保存在path下。 
        // 3、否则，保存在根目录下。 
       /* if (realPath == null || realPath == "") { 
         if (path == null || path == "") { 
          name = ConfConstants.CONTEXT_PATH + "\\" + realName + ".htm";// 这是生成的html文件名,如index.htm.说明： ConfConstants.CONTEXT_PATH为你的上下文路径。 
         } else { 
          name = ConfConstants.CONTEXT_PATH + "\\" + path + "\\" 
            + realName + ".htm";// 这是生成的html文件名,如index.htm. 
         } 
        } else { 
         name = ConfConstants.CONTEXT_PATH + "\\" + realPath + "\\" 
           + realName + ".htm";// 这是生成的html文件名,如index.htm. 
        }*/
        
        // 访问请求的页面，并生成指定的文件。 
        RequestDispatcher rd = sc.getRequestDispatcher(url); 

        final ByteArrayOutputStream os = new ByteArrayOutputStream(); 

        final ServletOutputStream stream = new ServletOutputStream() { 
         public void write(byte[] data, int offset, int length) { 
          os.write(data, offset, length); 
         } 

         public void write(int b) throws IOException { 
          os.write(b); 
         } 
        }; 

        final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os)); 

        HttpServletResponse rep = new HttpServletResponseWrapper(response) { 
         public ServletOutputStream getOutputStream() { 
             return stream; 
         } 

         public PrintWriter getWriter() { 
             return pw; 
         } 
        }; 
        
        pw.flush(); 
        FileOutputStream fos = new FileOutputStream(name); // 把jsp输出的内容写到xxx.htm 
        os.writeTo(fos); 
        fos.close(); 
        PrintWriter out = response.getWriter(); 
        out.print("success！"); 
      } 

}
