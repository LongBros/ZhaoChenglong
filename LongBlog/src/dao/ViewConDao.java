package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.longbro.util.JdbcUtil;
import com.longbro.util.OtherUtil;

import beans.ViewCon;
/**
 * 博客受阅读情况，指定页面被访问情况
 * @author 赵成龙
 * @website www.longqcloud.cn & www.zy52113.com
 * @date 2019年3月16日 下午10:50:57
 * @description 被统计的受访页面id：friendslink-0，sshare-1,share-2
 * @version
 */
public class ViewConDao {
	Connection con;
	Statement st;
	ResultSet rs;
	public ArrayList<ViewCon> queryView(String sql){
		ArrayList<ViewCon> views=new ArrayList<ViewCon>();
		try{
			con=JdbcUtil.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				ViewCon view=new ViewCon();
				view.setV_Id(rs.getInt("v_Id"));
				view.setB_Id(rs.getInt("b_Id"));
				view.setV_Time(rs.getString("v_Time"));
				view.setV_Ip(rs.getString("v_Ip"));
				views.add(view);
			}
			
			//关闭数据库连接对象
			rs.close();
			st.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return views;
	}

	/**
	 * 
	 * @desc 添加一次页面受访记录
	 * @author zcl
	 * @date 2019年3月16日
	 * @param request获取ip使用
	 * @param i	被访问的页面被定义的id
	 */
	public void addVisit(HttpServletRequest request,int i){
		String time=OtherUtil.time();//时间
		String ip=OtherUtil.getIp(request);//ip
		String sql="insert into page_visited_con(p_Page,p_Ip,p_Time)"
				+ " values("+i+",'"+ip+"','"+time+"');";
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			st.execute(sql);
			//关闭数据库连接对象
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @desc 得到某页面被访问的次数
	 * @author zcl
	 * @date 2019年3月16日
	 * @param i 页面id
	 * @return 页面被访次数
	 */
	public int getVisitNum(int i){
		int count=0;
		String sql="select count(*) from page_visited_con where p_Page='"+i+"';";
		con=JdbcUtil.getConnection();
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				count=rs.getInt("count(*)");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭数据库连接对象
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
}
