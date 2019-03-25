package com.longbro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtil {
	static String url=null;
	static String driver=null;
    static String user=null;
	static String password=null;
	static{
		ResourceBundle bundle=ResourceBundle.getBundle("db");
		url=bundle.getString("url");
		driver=bundle.getString("driver");
		user=bundle.getString("user");
		password=bundle.getString("password");
	}
	
	public static Connection getConnection(){
		try {
			loadDriver();
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static void loadDriver(){
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
