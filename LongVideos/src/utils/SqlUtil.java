package utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class SqlUtil {
	public static void main(String[] args) {
		System.out.print(getWeek());
	}
	
	public static String driver="com.mysql.jdbc.Driver";
	public static String url="jdbc:mysql://localhost:3306/longvideo?useUnicode=true&characterEncoding=utf-8";
	public static String user="root";
	public static String pass="ZCLZY";
	public static String time(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l=System.currentTimeMillis();
		Date date=new Date(l);
		String time=sf.format(date);//记录发表时间
		return time;
	}
	/**
	 * 得到汉语星期几
	 * @return
	 */
	public static String getCWeek(){
		Calendar calendar=Calendar.getInstance();
		String cweekNum="";//中文，如：星期一
		switch(calendar.DAY_OF_WEEK){
			case 1:cweekNum="日";break;	
			case 2:cweekNum="一";break;
			case 3:cweekNum="二";break;
			case 4:cweekNum="三";break;
			case 5:cweekNum="四";break;
			case 6:cweekNum="五";break;
			case 7:cweekNum="六";break;
			
		}
		return "星期"+cweekNum;
	}
	/**
	 * 得到英语星期？,如Sunday
	 * @return
	 */
	public static String getEWeek(){
		Calendar calendar=Calendar.getInstance();
		String eweek="";//英文，如：Monday
		switch(calendar.DAY_OF_WEEK){
			case 1:eweek="Sunday";break;	
			case 2:eweek="Monday";break;
			case 3:eweek="Tuesday";break;
			case 4:eweek="Wednesday";break;
			case 5:eweek="Thursday";break;
			case 6:eweek="Friday";break;
			case 7:eweek="Saturday";break;
		}
		return eweek;
	}
	/**
	 * 利用数组得到汉语星期几
	 * @return
	 */
	public static String getWeek(){
		Calendar calendar=Calendar.getInstance();
		String week[]={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		String cweekNum="";//中文，如：星期一
		for(int i=1;i<=7;i++){
			if(i==calendar.DAY_OF_WEEK){
				cweekNum=week[i-1];
				break;
			}
			System.out.println(i);
		}
		return cweekNum;
	}
	/**
	 * 	获取客户端的IP地址
	 * @param request
	 */
	public static String getIp(HttpServletRequest request) {
		String l_Ip=request.getHeader("x-forwarded-for");
		if(l_Ip==null||l_Ip.length()==0){
		   l_Ip=request.getHeader("Proxy-Client-IP");
		}if(l_Ip==null||l_Ip.length()==0||"unknown".equalsIgnoreCase(l_Ip)){
		    l_Ip=request.getHeader("WL-Proxy-Client-IP");
		}if(l_Ip==null||l_Ip.length()==0||"unknown".equalsIgnoreCase(l_Ip)){
		    l_Ip=request.getRemoteAddr();
		    if(l_Ip.equals("127.0.0.1") ||l_Ip.equals("0:0:0:0:0:0:0:1")){
			    //根据网卡取本机配置的IP
			    InetAddress in=null;
			    try {
					in=InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    l_Ip=in.getHostAddress();//输出的为本机的IP为172.24.21.129，
			    //但是宿舍无线分配的IP为219.157.79.134手机热点分配的是223.104.105.248，而需要记录的正是这，而非本机 
		   }
		}
		return l_Ip;
	} 
}
