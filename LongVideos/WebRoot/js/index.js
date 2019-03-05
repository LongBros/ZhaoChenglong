/**************************************
 * 网站首页界面js代码
 * 1.关闭搜索提示框
 * 2.搜索提示框所需js代码及异步加载代码
 * 3.将用户选中的视频名赋值到文本框
 * 4.退出登录所需js方法代码
 * 5.小喇叭所需js代码
 * 6.设置cookiejs代码
 * 7.计时器js代码
 ***************************************/

//在body里面添加ondbclick事件，当双击body任意地方关闭搜索榜
function Close() {
	document.getElementById("info").style.display="none";
}
//用户输入提示文本框，弹出匹配到输入内容的视频以供选择，若输入为空，弹出历史搜索和搜索排行榜
function toast(nick) {
	//通过onkeyup方法实时获取用户输入的内容
	var value=document.form.v_name.value;
	value=encodeURI(encodeURI(value));//将输入内容编码
	var xmlHttp=false;
	if(window.XMLHttpRequest){
		xmlHttp=new XMLHttpRequest();
	}else{
		xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	}
	var url="ajax/getToast.jsp?nick="+nick+"&value="+value;
	
	xmlHttp.open("get", url, true);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			document.getElementById("info").innerHTML=xmlHttp.responseText;
			document.getElementById("info").style.display="block";
		}
	}
	xmlHttp.send();
}
//将用户选中的赋值到文本框
function fuzhi(val) {
	document.form.v_name.value=val;
}

//点击退出按钮退出登录
function exit(){
	//1.隐藏个人信息，显示登录按钮
	document.getElementById("info").style.display="none";
	document.getElementById("login").style.display="inline-block";
	
	//2.清除cookie
	document.cookie="user=;code=";
	
	//得到cookie中的值
	if(document.cookie!=null){
		var co=document.cookie.split(";");
		for(var i=0;i<co.length;i++){
			//window.alert(co[i]);
			var arr=co[i].split("=");
			//window.alert(arr[1]);
		}
	}
	//设置cookie
}
function change(un,ut,vn,vt){
	var se=new Array();
	se[0]="2018年10月12日，“LongBro影院”改名“553影院”!";
	se[1]="553，是地瓜（俗称红薯）中最好吃的一种。";
	se[2]="最近一部很火的电影《动物世界》，李易峰主演的哟.";
	se[3]="客官，听说“斗破苍穹”这部新剧很好看。";
	se[4]="553影院新增影视评论及回复功能，快去评论你喜欢的影视剧吧！";
	se[5]="553影院新增登录注册功能，无需手机号邮箱，快去注册一下吧！";
	se[6]="553影院，做个人影院中的佼佼者。";
	se[7]="大家好，我是小喇叭，为您广播本站动态，欢迎您访问本站。";
	se[8]="注册553影院，记录自己的播放记录，拥有评论功能。";
	se[9]="截止目前，553影院已注册用户数为"+un+"其中今日注册"+ut+"人,访问ip共计"+vn+"其中今日访问"+vt;
	se[10]="关闭搜索提示框小窍门：在提示框弹出时，双击屏幕任意地方即关闭提示框！";
	se[11]="2018年4月份，LongBro开始编写LongBro影院！";
	se[12]="2018年11月14日，吐槽墙正式上线，无需注册登录即可吐槽！";
	//随机生成0~sen.length-1内的整数
	var i=Math.round(Math.random()*(se.length-1-0)+0);//使用Math.round来取整
	//alert(se.length-1);
	document.getElementById("word").innerHTML=se[i];
	//如何实现逐个循环显示，而不是随机显示？
	//var i=0;
	//document.getElementById("word").innerHTML=se[i];
	//i++;
}
//设置添加cookie
function setCookie(name,value)  
{  
    var Days = 30;  
    var exp = new Date();  
    exp.setTime(exp.getTime() + Days*24*60*60*1000);  
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();  
    var strsec = getsec(time);  
    var exp = new Date();  
    exp.setTime(exp.getTime() + strsec*1);  
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();  
}  

//该方法实现计时器
function ti() {
	var h=document.getElementById("hour");
    var m=document.getElementById("minute");
    var s=document.getElementById("second");
    if(s.innerText==60){//如果够60秒，则让分加1
    	m.innerText=m.innerText*1+1;
    	s.innerText=0;
    	if(m.innerText==60){//如果够60分，则让时加1
    		h.innerText=h.innerText*1+1;
    		m.innerText=0;
    		s.innerText=0;
    	}
    }
    s.innerText=s.innerText*1+1;
    //小于10，在其前补0
    if((h.innerText*1)<10){
    	h.innerText="0"+h.innerText*1;
    }
    if((m.innerText*1)<10){
    	m.innerText="0"+m.innerText*1;
    }
    if((s.innerText*1)<10){
    	s.innerText="0"+s.innerText*1;
    }
    var tc=h.innerText+""+m.innerText+""+s.innerText;
    tc=encodeURI(encodeURI(tc));
	setCookie("timecount", tc);
}