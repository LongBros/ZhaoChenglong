<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head><title>
	用户登陆
</title></head>
<body background="images\bg.bmp">
    <form name="form1" method="post" action="login.aspx?ReturnUrl=%2fxs%2fdefault_xs.aspx" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJMzM5MzkzMDY3ZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAQUMSW1hZ2VCdXR0b24xDOvIFuu1SSbhitKMkXr171oTQF0=" />
</div>

<div>

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="C2EE9ABB" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWBALM1KzXBQKeq5+XAgKlueiABQLSwpnTCJuZRO5sCVbdAg3tmhvWkN7SA8wq" />
</div>
    <div>
        </div>
        <table width="500" border=0 cellpadding=0 cellspacing=0 align="center">
        <tr><td style="height:56px"></td>
        <td></td>
        </tr>
            <tr>
                <td valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                <td><img alt="" src="images/top.jpg">
                </td>
                </tr>
                <tr>
                <td style="background-image: url(images/center.bmp)" align="center">
        <table width="300" border=0 cellpadding=0 cellspacing=0>
            <tr>
                <td style="height:38px">
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td style="height:30px"  valign="top">
                    <span id="Label1" style="color:Green;">账号：</span></td>
                <td valign="top" align="left">
                    <input name="Tzh" type="text" id="Tzh" style="border-color:YellowGreen;width:150px;" /></td>
                
            </tr>
            <tr>
                <td style="height: 26px">
                    <span id="Label2" style="color:Green;">密码：</span></td>
                <td style="height: 26px"  align="left">
                    <input name="Tmm" type="password" id="Tmm" style="border-color:YellowGreen;width:150px;" /></td>
                
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    &nbsp;</td>
                
            </tr>
            <tr>
                <td style="height:90px">
                </td>
                <td colspan="3" rowspan="3" valign="top" align="left">
                    <input type="image" name="ImageButton1" id="ImageButton1" src="images/dl1.gif" style="border-width:0px;" /></td>
            </tr>
        </table>
                </td>
                </tr>
                <tr>
                <td><img alt="" src="images/bottom.jpg">
                </td>
                </tr>
                </table >
                </td>
               <td valign="top"><img alt="" src="images/right.gif">
               </td>
            </tr>
           
        </table>
    </form>
</body>
</html>
