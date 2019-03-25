package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.longbro.util.JdbcUtil;
import com.longbro.util.OtherUtil;

import beans.Author;
import beans.Sentence;

/**
 * 1.查询数据库中所有的每日一句
 * 2.查询每日一句的数量
 * 3.向视频网站吐槽数据表写入一条吐槽
 * @author Administrator
 *
 */
public class SentenceDao {
	Connection con;
	Statement st;
	ResultSet rs;
	/**
	 * 1.查询数据库中所有的每日一句
	 */
	public ArrayList<Sentence> queryAll(String require) {
		ArrayList<Sentence> as=new ArrayList<Sentence>();
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select * from sentence order by s_Id desc"+require;
			rs=st.executeQuery(sql);
			while(rs.next()){
				//实例化Sentence不能放在循环外部，不然存入数组的会是同一个
				Sentence sentence=new Sentence();
				sentence.setS_Id(rs.getInt("s_Id"));
				sentence.setS_Date(rs.getString("s_Date"));
				sentence.setS_Chinese(rs.getString("s_Chinese"));
				sentence.setS_English(rs.getString("s_English"));
				sentence.setS_Time(rs.getString("s_Time"));
				sentence.setS_Ciba(rs.getString("s_Ciba"));
				
				as.add(sentence);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return as;
	}

	/**
	 * 2.查询每日一句的数量
	 * @return
	 */
	public int queryNum() {
		int num=0;
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			String sql="select count(*) from sentence";
			rs=st.executeQuery(sql);
			if(rs.next()){
				num=rs.getInt("count(*)");
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	/**
	 * 3.向视频网站吐槽数据表写入一条吐槽
	 * 2018-11-24 00:23解决每日一句为null的问题
	 * @param s
	 */
	public void insertToast(String s) {
		try{
			String date=OtherUtil.time().substring(0, 10);
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/longvideo?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "ZCLZY");
			st=con.createStatement();
			//如果数据库有当天的，说明已添加过，需比对日期而不是话的内容，因内容可能会一样
			rs=st.executeQuery("select w_Content from wall where w_Time like '%"+date+"%'");
			String time=OtherUtil.time();
			System.out.println(time);
			if(rs.next()){//如果有，表示当天每日一句已添加过，不再添加
				return;
			}else{//如果没有，则添加
				if(s.length()<5){//说明该每日一句异常，很可能是null，不添加
					return;
				}else{
					String com="来自今日<font color=\"red\"><a href=\"http://www.longqcloud.cn/msgboard.jsp\" "
							+ "target=\"_blank\">每日一句</a></font>:"+s;
					String sql="insert into wall(w_Nick,w_Tx,w_Content,w_Ip,w_Add,w_Time) values"
							+ "('每日一句播报员','images/tx/tx5.jpg','"+com+"','0.0.0.0','今日首位访问LongBro博客','"+time+"')";
					st.execute(sql);
				}
			}
		}catch(Exception e){
			
		}		
	}
}
