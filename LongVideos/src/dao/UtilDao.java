package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.taobao.api.internal.toplink.remoting.NettyRemotingDecoder.State;

import bean.SearchBean;
import bean.TimeNum;
import utils.SqlUtil;
/**
 * 一。返回查询结果数量
 * 二。根据用户名查询出库中存在其看视频的日子，返回链表
 * 三。向数据表中插入数据，
 * 四。判断库中一天内是否有该条ip访问，若有则不必添加返回true，反之返回false
 * 5.添加播放记录，如果库中有，则修改为最新的，若无，则添加
 * 6.添加收藏，如果库中已有该人对该视频的收藏，则返回，反之添加
 * 7.查询指定搜索记录，存入ArrayList
 * 8.根据用户名得到注册时设置的问题
 * 9.得到根据输入内容匹配到的视频名的集合
 * 10查询库中每日的访问量数量，返回含有日期和对应访问数量的链表
 * @author Administrator
 *
 */
public class UtilDao {
	static Connection con=null;
	static Statement st=null;
	static ResultSet rs=null;
	/**
	 * 一。返回查询结果数量
	 * 1.查询指定视频下的评论数量
	 * 2.查询指定天指定用户看的视频的数量
	 * 3.查询指定评论下的回复的数量
	 * @param sql
	 * @return
	 */
	public static int getNum(String sql) {
		int num=0;
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()){
				num=rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}
	/**
	 * 二。根据用户名查询出库中存在其看视频的日子，返回链表
	 * @param nick
	 * @return
	 */
	public static ArrayList<String> getDate(String sql) {
		ArrayList<String> as=new ArrayList<String>();
		try {
			Class.forName(SqlUtil.driver);
			Connection con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user, SqlUtil.pass);
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				as.add(rs.getString(1));
//				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return as;
	}
	/**
	 * 三。向数据表中插入数据，
	 * 比如1.发布评论
	 * 2发表回复
	 * 3添加一条访问记录
	 */
	public static void insertData(String sql) {
		try{
			Class.forName(SqlUtil.driver);
			Connection con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user, SqlUtil.pass);
			Statement st=con.createStatement();
			st.execute(sql);
		}catch(Exception e){
			
		}
	}
	/**
	 * 四。判断库中一天内是否有该条ip访问，若有则不必添加返回true，反之返回false
	 */
	@SuppressWarnings("deprecation")
	public static boolean exist(String ip){
		try{
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			long c=System.currentTimeMillis();
			long oneday=1*24*60*60*1000;//一天的毫秒数
			long d=c-oneday;//一天前的微秒时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(new Date(d));//一天前的时间
			//判断是否有该ip一天内的访问记录
			String sql="select l_Time from loginlog where l_Ip='"+ip+"' and l_Time>'"+time+"'";
//			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()){//存在一天内的访问记录，不必添加				
				return true;
				//因时间字符串无法比较，还是算出微秒吧
//				long t=Date.parse(time);//数据库中存储的记录的微秒时间
//				System.out.println(t);
//				if(t<d){//库中的记录是一天前的，需再记录一条
//					return false;
//				}else{
//					return true;
//				}
			}else{
				return false;
			}
		}catch(Exception e){
			return true;
		}
	}
	public static void loadLog() {
		try{
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery("select * from loginlog limit");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 5.添加播放记录，如果库中有，则修改为最新的，若无，则添加
	 * @param query
	 * @param update
	 * @param add
	 */
	public static void addPRecord(String query,String update,String add){
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()){//有该用户对该影视的播放记录，修改时间为最新
				st.execute(update);
			}else{//没有该条播放记录方添加
				st.execute(add);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 6.添加收藏，如果库中已有该人对该视频的收藏，则返回，反之添加
	 * @param query
	 * @param add
	 */
	public static void addStore(String query,String add){
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()){//有该用户对该影视的播放记录，
				return;
			}else{//没有该条播放记录方添加
				st.execute(add);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getPass(String user,String qq){
		String pass="";
		if(!user.equals("")&&!qq.equals("")){
			String sql="select pass from user where nickname='"+user+"' and qq='"+qq+"'";
			try {
				Class.forName(SqlUtil.driver);
				con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
				st=con.createStatement();
				rs=st.executeQuery(sql);
				if(rs.next()){
					pass=rs.getString(1);
				}else{
					pass="未匹配到对应数据，请确认你已注册且输入正确";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			pass="部分信息未输入";
		}
		return pass;
	}
	
	public static ArrayList<SearchBean> getSearchRecord(String sql) {
		ArrayList<SearchBean> sbs=new ArrayList<SearchBean>();
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				SearchBean sb=new SearchBean();
				sb.setId(rs.getInt(1));//搜索记录id
				sb.setSearcher(rs.getString(2));//搜索者
				sb.setContent(rs.getString(3));//搜索内容
				sb.setIp(rs.getString(4));//搜索者ip
				sb.setAddress(rs.getString(5));//搜索者地址
				sb.setTime(rs.getString(6));//搜索时间
				sbs.add(sb);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sbs;
	}
	
	/**
	 * 8.根据用户名得到注册时设置的问题
	 * @param sql
	 * @return
	 */
	public static String getQues(String sql){
		String ques="";
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()){//有该用户，则得到问题并返回
				String qa=rs.getString(4);
				if(qa.contains(":")){
					ques=qa.substring(0,qa.indexOf(":"));
				}else{
					ques="你的QQ号是？";
				}
			}else{
				ques="用户名不存在";
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ques;
	}
	public static boolean delete(String sql){
		boolean isdelete=true;
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			isdelete=st.execute(sql);
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isdelete;
	}
	/**
	 * 9.得到根据输入内容匹配到的视频名的集合
	 */
	public static ArrayList<String> getNames(String sql){
		ArrayList<String> names=new ArrayList<String>();
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				names.add(rs.getString(3));
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return names;
		
	}
	/**
	 * 10查询库中每日的访问量数量，返回含有日期和对应访问数量的链表
	 * @param sql
	 * @return
	 */
	public static ArrayList<TimeNum> getDay(String sql) {
		ArrayList<TimeNum> days=new ArrayList<TimeNum>();
		try {
			Class.forName(SqlUtil.driver);
			con=DriverManager.getConnection(SqlUtil.url,SqlUtil.user,SqlUtil.pass);
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				String day=rs.getString(1);
				Statement st1=con.createStatement();
				String s="select count(*) from loginlog where l_Time like'"+day+"%'";
				ResultSet rs1=st1.executeQuery(s);
				if(rs1.next()){
					int num=rs1.getInt(1);
					TimeNum tn=new TimeNum();
					tn.setTime(day);
					tn.setNum(num);
					days.add(tn);
				}
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return days;
	}
}
