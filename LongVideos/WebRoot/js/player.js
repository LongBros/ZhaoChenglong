/**************************************
 * 网站播放界面js代码
 * 1.关闭与显示弹幕
 * 2.Ajax异步收藏视频
 * 3.嗷大喵表情的隐藏与显示
 * 4.点击嗷大喵表情的同时赋值评论框
 * 5.大黄脸表情的隐藏与显示，及赋值评论框
 * 6.Ajax异步加载已有评论
 * 7.Ajax异步发送评论
 ***************************************/
//关闭与显示弹幕
function marquee(){
	var m=document.getElementById("marquee").style.display;
	if(m=="block"){
		document.getElementById("marquee").style.display="none";
		document.getElementById("btn").innerText="开启弹幕";
	}else{
		document.getElementById("marquee").style.display="block";
		document.getElementById("btn").innerText="关闭弹幕";
	}
}

//Ajax异步收藏视频
function sc(nick,title,url,type,img){//用户名，视频名，链接，类型，图片
   	var etitle=encodeURI(encodeURI(title));//需将中文视频名二次编码方可异步通信
	var xmlHttp=false;
   	if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
   		xmlHttp=new XMLHttpRequest();
   	}else if(window.ActiveXObject){//QQ浏览器（IE)
       	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
   	}
   	var url="/Store?url="+url+"&title="+etitle+"&type="+type+"&img="+img;
	xmlHttp.open("post",url,true);
	if(nick==""){
		alert("请先登录！");
		window.open("login.jsp");
	}else{
    	xmlHttp.send();
		alert("收藏成功-"+title);
	}
}

//onmousedown--鼠标按下。onmouseover--鼠标靠近
//嗷大喵表情不在的时候点击让其出现且让“huang”消失，在的时候点击让其消失
function imgAo(){
	var d=document.getElementById("ao").style.display;
	if(d=="none")//必须用==
	{//不在的时候点击让其出现且让“huang”消失
		document.getElementById("ao").style.display="block";
		document.getElementById("huang").style.display="none";
	}else{//在的时候点击让其消失
		document.getElementById("ao").style.display="none";
	}
}
//点击表情，改变textarea中内容
function appendValueAo(v){
	var value=document.getElementById("comme");
	//存入数据库的不是路径，需对评论内容拆分后加路径再显示
	value.value=value.value+"【"+v+"】";
}
//不在的时候点击让其出现且让“嗷大喵”消失
function openImgHu(){
	document.getElementById("huang").style.display="block";
	document.getElementById("ao").style.display="none";
}
//在的时候点击让其消失
function closeImgHu(){
	document.getElementById("huang").style.display="none";
}
//将选中的大黄脸表情对应名添加至评论框
function appendValueHu(v){
	var value=document.getElementById("comme");
	value.value=value.value+"《"+v+"》";
}

//通过Ajax异步加载已有评论
function loadComs(url) {
	var xmlHttp=false;
   	if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
   		xmlHttp=new XMLHttpRequest();
   	}else if(window.ActiveXObject){//QQ浏览器（IE)
       	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
   	}
	var u="ajax/loadCom.jsp?url="+url;
    xmlHttp.open("post", u,true);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			allComs.innerHTML=xmlHttp.responseText;
		}else{
			allComs.innerHTML="正在加载评论请稍后...";
		}
	};
	xmlHttp.send();
}
//通过Ajax异步发送评论
function sendCom(url,nick) {
	var co=document.sendcomment.comment.value;//评论内容
	var com=encodeURI(encodeURI(co));//编码后的评论，为评论（必须）二次编码

	var xmlHttp=false;
   	if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
   		xmlHttp=new XMLHttpRequest();
   	}else if(window.ActiveXObject){//QQ浏览器（IE)
       	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
   	}

	var u="/SendCom?comment="+com+"&url="+url;
	xmlHttp.open("post",u,true);
	if(nick==""){
		alert("您还未登录，登录后方可发表评论！");
		window.location="login.jsp";
	}else if(co.length==0||co.length>145){//评论内容为空或过长
		alert("您输入的评论为空（或过长需小于145字符）");
	}else{
		xmlHttp.send();
		alert("发表成功");
    	loadComs(url);//发表后异步重新加载评论
    	document.sendcomment.comment.value="";//将评论框置为空
	}
}