//onmouseover="showImg()" onmouseout="hideImg()" 
//2018-11-12已去掉
function showImg() {
   element=document.getElementById('paypal');
   element1=document.getElementById('wechat');
   element.src="/images/useful/paypal.jpg";
   element1.src="/images/useful/wechat.jpg";
}
function hideImg() {
   document.getElementById('paypal').src='';
   document.getElementById('wechat').src='';
}

   function showHide() {
	   element=document.getElementById('paypal');
	   element1=document.getElementById('wechat');
	   if(element.src.match("paypal")){//说明正在显示图片
		   document.getElementById('paypal').src='';
		   document.getElementById('wechat').src=''
	   }else{
	       element.src="/images/useful/paypal.jpg";
	       element1.src="/images/useful/wechat.jpg";
	   }
   }
   function showResponse() {
	   document.getElementById('response').rows=5;
	   document.getElementById('response').cols=90;
   }
   /*//可用来设置文档的标题关键字描述
   function setMeta(){
       document.title="<%=title%>-LongBro博客";
	   var metas=document.getElementsByTagName("meta");
	   metas[0].name="keywords";//设置关键字
	   metas[0].content="<%=title%>";
	   metas[1].name="description";//设置介绍
	   metas[1].content="<%=title%>";
   }
   window.onload=setMeta; */
   
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
	   	document.forms.tx.value="images/tx1/boss"+i+".jpg";
	   	//将选择的头像放大特殊显示
	   	for(j=1;j<17;j++){
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
   
 //刚进入随机分配昵称和头像
   function random(){
	   	var nicks=new Array("游客","稚嫩小子","花季少女","星坛歌神","花季少女","缤纷少年","花季少女","沧桑大叔","Legendary","回忆淡漠年华"
	   			,"风中的那封情书","酒阑人散","没那么简单","是例外是意外","施妄者","小样儿","干掉老班、劳资就是王。",
	   			"我要当学霸@","软萌","薄荷微光少年时","蔷薇纪年静谧时","铁蹄踏破流年","流年戳碎谎言","倦了世俗","怠了青春",
	   			"把孤独喂饱","把回忆锁好","不离不走","迷人hero");
	   	var i=Math.round(Math.random()*(nicks.length-1-0)+0);
	   	//随机分配昵称
	   	document.forms.name.value=nicks[i];
	   	if(i>17||i<1){//因头像只有6个，故，，，
	   		i=1;
	   	}
	   	//随机分配头像
	   	document.getElementById("current"+i).style.width="40px";
	   	document.getElementById("current"+i).style.height="40px";
	   	document.forms.tx.value="images/tx/tx"+i+".jpg";
   }
   window.onload=random;
   /**
    * 控制嗷大喵的出现于隐藏
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