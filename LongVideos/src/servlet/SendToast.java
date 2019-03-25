package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtilDao;
import utils.AddressUtils;
import utils.SqlUtil;

public class SendToast extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String nick=request.getParameter("nick");//昵称
		nick=URLDecoder.decode(nick,"utf-8");
		System.out.println(nick);
		String tx=request.getParameter("tx");//头像
		String com=request.getParameter("comment");//吐槽内容
		com=URLDecoder.decode(com,"utf-8");
		//注意：评论之中的图片地址带'，要换为"
		com=com.replace("'", "\"");
		//2019-01-19将吐槽时间加上星期~
		//2019-03-12实现星期~的中英文随机生成
		String week="";
		if(new Random().nextInt(11)>6){//随机数为7,8,9,10，则执行生成中文
			week=SqlUtil.getWeek();
		}else{//随机数为0-6，则执行生成英文星期~
			week=SqlUtil.getEWeek();
		}

		String time=SqlUtil.time()+"&nbsp"+week;//吐槽时间
		String ip=SqlUtil.getIp(request);//得到访问者ip
		String add=AddressUtils.getAddByIp(ip);
		if(add.equals("0")){
			add="";
		}
		String sql="insert into wall(w_Nick,w_Tx,w_Content,w_Ip,w_Add,w_Time) values"
				+ "('"+nick+"','"+tx+"','"+com+"','"+ip+"','"+add+"','"+time+"')";
		UtilDao.insertData(sql);//向评论表插入评论
		System.out.println(sql);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
