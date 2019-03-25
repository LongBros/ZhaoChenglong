<%@page import="utils.AddressUtils"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="utils.SqlUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
      		try{
      			Class.forName(SqlUtil.driver);
                Connection con=DriverManager.getConnection(SqlUtil.url, SqlUtil.user, SqlUtil.pass);
                Statement st=con.createStatement();
                String sql="select * from data";
                ResultSet rs= st.executeQuery(sql);
		while(rs.next()){
			String temp=rs.getString("temp");
			String humi=rs.getString("humi");
			String light=rs.getString("light");
               		out.println("temp is "+temp+",humi is"+humi+",light is"+light);

		}

                }catch(Exception e){
      			
      		}
      %>
