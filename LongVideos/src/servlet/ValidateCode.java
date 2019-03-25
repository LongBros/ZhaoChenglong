package servlet;
import javax.imageio.ImageIO;
import javax.servlet.http.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.*;
//输出随机认证码图片。JavaScript编写切换图片验证码
public class ValidateCode extends HttpServlet{
	public static final int WIDTH=120;
	public static final int HEIGHT=25;
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g=image.getGraphics();
		//1.设置背景色
		setBackground(g);
		//2.生成边界
		setBorder(g);
		//3.画几条随机干扰线
		drawRandomLine(g);
		//4.生成几个随机汉字
//		String num=drawRandomCh((Graphics2D) g);
//		System.out.println(num);
//		num=URLEncoder.encode(num,"gbk");
		//随机生成几个数字
		int num=drawNum((Graphics2D) g);
		//随机生成几个数字或字母
//		String num=drawNums((Graphics2D) g);
		//方法1，利用cookie记住验证码
		Cookie cookie=new Cookie("code",num+"");
		cookie.setMaxAge(60*60);
		cookie.setPath("/");
		res.addCookie(cookie);
//		System.out.println("Response中的code为"+num);
		//5.图形写给浏览器
		res.setContentType("image/jpeg");
		//控制浏览器不要缓存
		res.setDateHeader("expries", -1);
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Pragma","no-cache");
		ImageIO.write(image, "jpg", res.getOutputStream());
	}
	private void setBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	private void setBorder(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.WHITE);
		g.fillRect(1,1,WIDTH-2,HEIGHT-2);
	}
	private void drawRandomLine(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		for(int i=0;i<5;i++){
			int x1=new Random().nextInt(WIDTH);
			int y1=new Random().nextInt(HEIGHT);
			int x2=new Random().nextInt(WIDTH);
			int y2=new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	//画汉字
	private String drawRandomCh(Graphics2D g) {
		String s="";//随机生成的四个字的组合
		g.setColor(Color.RED);
		g.setFont(new Font("宋体",Font.BOLD,20));
		String base="\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6";
		//\u4e00-\u9fa5
		for(int i=0;i<4;i++){
			int degree=new Random().nextInt()%30;
			String ch=base.charAt(new Random().nextInt(base.length()))+"";
			g.rotate(degree*Math.PI/180, 5+30*i, 20);
			g.drawString(ch, 5+30*i, 20);
			g.rotate(-degree*Math.PI/180, 5+30*i, 20);
			s=s+ch;
		}
		return s;
		
	}
	//画0-9数字
	private int drawNum(Graphics2D g) {
		int num=0;
		String nu="";
		g.setColor(Color.RED);
		g.setFont(new Font("宋体",Font.BOLD,20));
		for(int i=0;i<4;i++){
			int degree=new Random().nextInt()%30;
			int n=new Random().nextInt(10);
			g.rotate(degree*Math.PI/180, 5+30*i, 20);
			g.drawString(n+"", 5+30*i, 20);
			g.rotate(-degree*Math.PI/180, 5+30*i, 20);
			nu=nu+""+n;
		}
//		System.out.print(nu);
		return Integer.parseInt(nu);
	}
	//画0-9数字和所有大小写字母
	private String drawNums(Graphics2D g) {
		String num="";//要返回的字符串
		char[] c={'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
				'o','p','q','r','s','t','u','v','w','x','y','z',
				'A','B','C','D','E','F','F','G','H','I','J','K','L','M','N',
				'O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		g.setColor(Color.RED);
		g.setFont(new Font("宋体",Font.BOLD,20));
		for(int i=0;i<4;i++){
			System.out.println(c.length);
			int degree=new Random().nextInt()%30;
			int n=new Random().nextInt(c.length);
			g.rotate(degree*Math.PI/180, 5+30*i, 20);
			g.drawString(c[n]+"", 5+30*i, 20);
			g.rotate(-degree*Math.PI/180, 5+30*i, 20);
			num=num+c[n];
		}
		return num;
		
	}
	
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		doGet(req,res);
	}

}
