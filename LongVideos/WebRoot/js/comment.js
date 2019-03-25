/******************************************
 * 吐槽墙界面js代码
 * 2018-11-04吐槽墙模块初次上线
 * 2018-12-08添加函数以实时自动计算吐槽内容的字符数
 * 2018-12-14将ajax异步加载的文件移动至“ajax”文件夹
 * 2019-03-17实现了键盘监听事件函数，将代码放至顶部可实现，尾部却不行
 ******************************************/

/**
 * 上键切换头像，左键加载吐槽，右键切换昵称
 * @param event
 */
function keydown(event) {
	//alert(event.keyCode);
	if(event.keyCode=="37"){//左键
		loadTo();
	}else if(event.keyCode=="38"){//上键
		var i=ran(6);//i的取值为1,2,3,4,5,6
		changeTx(i);
	}else if(event.keyCode=="39"){//右键
		changeNick();
	}else if(event.keyCode=="40"){//下键
		imgAo();
	}
}

document.addEventListener("keydown", keydown);
/**
 * 返回一个小于i的正整数
 * @param i
 */
function ran(i){
	var num=Math.round(Math.random()*i);
	if(num==0){
	   return ran(i);
	}
	return num;
}

//点击切换改变昵称
function changeNick(){
	var nicks=new Array("游客","稚嫩小子","沧桑大叔","花季少女","缤纷少年","花季少女","沧桑大叔","Legendary","回忆淡漠年华"
			,"风中的那封情书","酒阑人散","没那么简单","是例外是意外","施妄者","小样儿","干掉老班、劳资就是王。",
			"我要当学霸@","软萌","薄荷微光少年时","蔷薇纪年静谧时","铁蹄踏破流年","流年戳碎谎言","倦了世俗","怠了青春",
			"把孤独喂饱","把回忆锁好","不离不走","迷人hero");
	var i=Math.round(Math.random()*(nicks.length-1-0)+0);
	document.forms.name.value=nicks[i];
}
//点击头像切换
function changeTx(i){
	//alert(i);
	//隐藏的头像路径置为选择的
	document.forms.tx.value="images/tx/tx"+i+".jpg";
	//将选择的头像放大特殊显示
	for(j=1;j<7;j++){
		if(j==i){//当前选择头像
			//alert(document.getElementById("current"+j).style.width);
			document.getElementById("current"+j).style.width="40px";
			document.getElementById("current"+j).style.height="40px";
		}else{
			//alert(j);
			document.getElementById("current"+j).style.width="30px";
			document.getElementById("current"+j).style.height="30px";
		}
	}
}
/**
 * 控制嗷大喵的出现与隐藏
 */
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
	var value=document.forms.toast;
	//存入数据库的不是路径，需对评论内容拆分后加路径再显示
	value.value=value.value+"【"+v+"】";
}
//刚进入随机分配昵称和头像
function random(){
	var nicks=new Array("游客","稚嫩小子","花季少女","星坛歌神","花季少女","缤纷少年","花季少女","沧桑大叔","Legendary","回忆淡漠年华"
			,"风中的那封情书","酒阑人散","没那么简单","是例外是意外","施妄者","小样儿","干掉老班、劳资就是王。",
			"我要当学霸@","软萌","薄荷微光少年时","蔷薇纪年静谧时","铁蹄踏破流年","流年戳碎谎言","倦了世俗","怠了青春",
			"把孤独喂饱","把回忆锁好","不离不走","迷人hero");
	var i=Math.round(Math.random()*(nicks.length-1-0)+0);
	//随机分配昵称
	document.forms.name.value=nicks[i];
	if(i>6||i<1){//因头像只有6个，故，，，
		i=ran(6);//随机生成一个小于等于6的正整数
	}
	//随机分配头像
	document.getElementById("current"+i).style.width="40px";
	document.getElementById("current"+i).style.height="40px";
	document.forms.tx.value="images/tx/tx"+i+".jpg";
}
window.onload=random;
/**
 * 设置输入时已输入内容字数
 */
function writting(){
	var co=document.forms.toast.value;//吐槽内容
	co=co+"";
	//alert(co.length);
	document.getElementById("wordnum").innerHTML="<font color=\"red\">"+co.length+"</font>";
}
/**
 * 发布吐槽
 */
var xmlHttp=false;
if(window.XMLHttpRequest){//搜狗浏览器(Mozilla)
	xmlHttp=new XMLHttpRequest();
}else if(window.ActiveXObject){//QQ浏览器（IE)
	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
}
function sendCom(){
	var nick=document.forms.name.value;//昵称
	
	var ni=encodeURI(encodeURI(nick));//编码后的昵称
	var tx=document.forms.tx.value;//头像
	
	var co=document.forms.toast.value;//吐槽内容
	var com=encodeURI(encodeURI(co));//编码后的评论，为评论（必须）二次编码
	
	var u="/SendToast?nick="+ni+"&tx="+tx+"&comment="+com;
	xmlHttp.open("post",u,true);
	if(nick==""){
		alert("昵称不能为空哟！");
	}else if(co.length==0||co.length>245){//评论内容为空或过长
		alert("您输入的评论为空（或过长需小于245字符）");
	}else{
		xmlHttp.send();
		alert("发表成功");
		window.location="/toast.jsp";
	}
}
/**
 * Ajax异步加载更多吐槽
 * 刚进入加载五个，刷新一次加载接下来的5个
 * 参数为加载更多的开始的序号，即原本已显示十个，则从第11个开始
 * 取消参数，从cookie中取出
 */
function loadTo(){
	var load=document.getElementById("load");
	var u="ajax/loadTo.jsp";
	xmlHttp.open("post",u,true);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			//alert(xmlHttp.responseText.length);
			var before=document.getElementById("comments");
			before.innerHTML=before.innerHTML+xmlHttp.responseText;
			load.innerText="点击加载更多∨";
			//alert(xmlHttp.responseText);
			if(xmlHttp.responseText.length==18){//返回为空，表示已加载完，改变load
				load.innerText="没有更多了哟！";
			}
		}else{
			load.innerText="加载中，请稍后...";
		}
	}
	xmlHttp.send();
}
//刚进入时加载几个吐槽
//window.onload=loadTo();
/**
 * 根据类型异步加载其下的影视榜，并显示到指定位置
 * @param type
 */
function qiehuan(type){
	var u="ajax/spiderank.jsp?type="+type;
	xmlHttp.open("post",u,true);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			document.getElementById("video").innerHTML=xmlHttp.responseText;
		}
	};
	xmlHttp.send();
	//当前绿色显示，其他黑色显示
	var ts=new Array("dianshi","dianying","zongyi","dongman");
	for(i=0;i<4;i++){
		//alert("\""+ts[i]+"");
		if(type.equal(ts[i])){
			alert(ts[i]);
			//document.getElementById(ts[i]).style.color="green";
		}else{
			//document.getElementById(ts[i]).style.color="black";
		}
	}

}
window.onload=qiehuan('dianshi');