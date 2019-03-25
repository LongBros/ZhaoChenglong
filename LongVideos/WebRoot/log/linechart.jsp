<%@page import="dao.UtilDao"%>
<%@page import="com.longbro.bean.TimeNum"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>553影院每日访问量实时统计-折线图</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
<script src="manage/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
<!-- <script src="manage/lib/hcharts/Highcharts/5.0.6/js/modules/series-label.js"></script>
<script src="manage/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
<script src="manage/lib/hcharts/Highcharts/5.0.6/js/modules/export-data.js"></script>
 -->
 
 <%
 	String sql="select distinct left(l_Time,10) as y_m_d from loginlog";
 	ArrayList<TimeNum> tns=UtilDao.getDay(sql);
 	ArrayList<String> days=new ArrayList<String>();
 	ArrayList<Integer> nums=new ArrayList<Integer>();
 	for(TimeNum tn:tns){
 		days.add("'"+tn.getTime()+"'");
 		nums.add(tn.getNum());
 		//System.out.println(tn.getNum());
 	}
 	System.out.println(days);
 %>
 
<div id="container"></div>
		<script type="text/javascript">
			Highcharts.chart('container', {
			    title: {
			        text: '2018年553影院日访问量统计(单IP每日仅统计一次)'
			    },
			
			    subtitle: {
			        text: '数据来源: www.zy52113.com'
			    },
			    xAxis: {
		            categories: <%=days%>
		        },

			    yAxis: {
			        title: {
			            text: '日访问量       (ip)'
			        }
			    },
			    legend: {
			        layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'middle'
			    },
			
			    /* plotOptions: {
			        series: {
			            label: {
			                connectorAllowed: false
			            },
			            pointStart: 1
			        }
			    },
			 */
			    series: [{
			        name: '访问IP数量',
			        data: <%=nums%>
			    }],
			
			    responsive: {
			        rules: [{
			            condition: {
			                maxWidth: 500
			            },
			            chartOptions: {
			                legend: {
			                    layout: 'horizontal',
			                    align: 'center',
			                    verticalAlign: 'bottom'
			                }
			            }
			        }]
			    }
			
			});
		</script>
	</body>
</html>