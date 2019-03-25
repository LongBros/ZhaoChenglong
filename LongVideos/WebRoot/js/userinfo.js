/**
 * 用户信息页面所需js内容，包括
 * 删除播放记录，评论，收藏记录，编辑评论等等
 */
//定义ajax技术所需对象
var xmlHttp=false;
if(window.XMLHttpRequest){
	xmlHttp=new XMLHttpRequest();
}else if(window.ActiveXObject){
	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
}
/**
 * 移除播放记录，收藏的视频
 * @param name 
 * @param url
 * @param i 指定要删除的数据所在的表，i=0为评论表，i=1播放记录表，i=2收藏记录表，
 */
function remove(name,url,i){
	var t="";//弹出确认框内容需要
	if(i==1){
		t="我的足迹";
	}else if(i==2){
		t="我的看单";
	}
	var r=confirm("确定从\""+t+"\"移除\""+name+"\"？")
	if(r==true){
		//i表示移除的表，id表示移除的是评论表时的评论id，不为评论表时置为-1，因delete.jsp需要该参数，故需设置值
		var url="delete.jsp?&url="+url+"&i="+i+"&id=-1";
		xmlHttp.open("get", url, true);
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				window.alert(xmlHttp.responseText);
			}
		}
		xmlHttp.send();
	}else{
		
	}
	
}
/**
 * 删除评论函数
 * @param id
 */
function deleteC(id) {
	var r=confirm("确定删除该条评论吗？")
	if(r==true){
		//删除评论只需要评论id，但由于delete.jsp执行的其他删除操作需链接,所以这里也要带上
		var url="delete.jsp?url=&i=0&id="+id;
 		alert("即将为你删除选定的评论！");
 		xmlHttp.open("get",url,true);
 		xmlHttp.onreadystatechange=function(){
 			if(xmlHttp.readyState==4){
 				window.alert(xmlHttp.responseText);
 			}
 		}
 		xmlHttp.send();
	}else{
 		//alert("Cancel");
	}
}

function editCom(id){
	//alert("即将编辑id为"+id+"的评论？");
	var com=document.getElementById("com"+id).innerText;
	//alert(com);
	document.getElementById("input").value=com;
}
//点击完成按钮，将输入框的最终内容更新至指定id的评论
function complete(){
	var com=document.getElementById("input").value;
	alert(com+"---》》》编辑评论功能尚未开启！");
}


/**
 * 弹出框-编辑评论
 * @param title	弹出框的title内容
 * @param msg  弹出框中input中的默认值即原评论
 * @param w
 * @param h
 */
function alertWin(title, msg, w, h) { 

    var titleheight = "22px"; // 窗口标题高度 
    var bordercolor = "#666699"; //窗口的边框颜色 
    var titlecolor = "#FFFFFF"; // 窗口的标题颜色 
    var titlebgcolor = "#666699"; // 窗口的标题背景色 
    var bgcolor = "#FFFFFF"; // 内容背景色 
    var iWidth = document.documentElement.clientWidth; //这个窗口的宽度 
    var iHeight = document.documentElement.clientHeight; //这个窗口的高度 

    //背景层的格式 
    var bgObj = document.createElement("div"); 

    bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:" + iWidth + "px;height:" + Math.max(document.body.clientHeight, iHeight) + "px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;z-index:1000;"; 

    document.body.appendChild(bgObj); 

    var iframe2 = document.createElement("iframe"); 
    iframe2.style.cssText = "position:absolute; top:0px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000; z-index:1001; border-style:none; border-width:0px; border:0px;width:" + iWidth + "px;height:" + iHeight + "px"; 
    bgObj.appendChild(iframe2); 

    //创建一个弹出层 
    var msgObj = document.createElement("div"); 
    //设置弹出的层的样式 
    msgObj.style.cssText = "position:absolute;font:11px '宋体';top:" + (iHeight - h) / 2 + "px;left:" + (iWidth - w) / 2 + "px;width:" + w + "px;height:" + h + "px;text-align:center;border:1px solid " + bordercolor + ";background-color:" + bgcolor + ";padding:1px;line-height:22px;z-index:1001;"; 
    document.body.appendChild(msgObj); 
    //创建一个table用于容纳层上的内容 
    var table = document.createElement("table"); 
    //将Table放到弹出层上 
    msgObj.appendChild(table); 
    //设置table的格式 
    table.style.cssText = "margin:0px;border:0px;padding:0px;"; 
    table.cellSpacing = 0; 
    //插入一行用于显示标题 
    var tr = table.insertRow(-1); 
    //插入一个单元格用于容纳标题 
    var titleBar = tr.insertCell(-1); 
    titleBar.style.cssText = "width:100%;height:" + titleheight + "px;text-align:left;padding:3px;margin:0px;font:bold 13px '宋体';color:" + titlecolor + ";border:1px solid " + bordercolor + ";cursor:move;background-color:" + titlebgcolor; 
    titleBar.style.paddingLeft = "10px"; 
    //设置标题 
    titleBar.innerHTML = title; 
    //设置曾德拖动事件 
    var moveX = 0; 
    var moveY = 0; 
    var moveTop = 0; 
    var moveLeft = 0; 
    var moveable = false; 
    var docMouseMoveEvent = document.onmousemove; 
    var docMouseUpEvent = document.onmouseup; 
    //鼠标点击标题 
    titleBar.onmousedown = function() { 
        var evt = getEvent(); 
        moveable = true; 
        moveX = evt.clientX; 
        moveY = evt.clientY; 
        moveTop = parseInt(msgObj.style.top); 
        moveLeft = parseInt(msgObj.style.left); 
        //鼠标拖动 
        document.onmousemove = function() { 
            if (moveable) { 
                var evt = getEvent(); 
                var x = moveLeft + evt.clientX - moveX; 
                var y = moveTop + evt.clientY - moveY; 
                if (x > 0 && (x + w < iWidth) && y > 0 && (y + h < iHeight)) { 
                    msgObj.style.left = x + "px"; 
                    msgObj.style.top = y + "px"; 
                } 
            } 
        }; 
        document.onmouseup = function() { 
            if (moveable) { 
                document.onmousemove = docMouseMoveEvent; 
                document.onmouseup = docMouseUpEvent; 
                moveable = false; 
                moveX = 0; 
                moveY = 0; 
                moveTop = 0; 
                moveLeft = 0; 
            } 
        }; 
    } 

    //关闭按钮事件 
    var closeBtn = tr.insertCell(-1); 
    closeBtn.style.cssText = "cursor:pointer; text-align:right;padding:2px;background-color:" + titlebgcolor; 
    closeBtn.innerHTML = "<span style='font-size:15pt;color:" + titlecolor + ";'>×</span>"; 
    //closeBtn.onclick = function() { 
     //   document.body.removeChild(bgObj); 
    //    document.body.removeChild(msgObj); 
    //} 
	closeBtn.onclick=close;
	function close(){
		document.body.removeChild(bgObj);
		document.body.removeChild(msgObj); 
	}

    //弹出的消息窗口内容 
    //var msgBox = table.insertRow(-1).insertCell(-1); 
    //msgBox.style.cssText = "font:10pt '宋体';"; 
    //msgBox.colSpan = 2; 
    //msgBox.innerHTML = msg; 
    //层上模板名称的内容 
    var nameBox = table.insertRow(-1); 
    var nameLable = nameBox.insertCell(-1); 
    nameLable.style.cssText = "font:12pt '宋体';text-align:center;"; 
    nameLable.innerHTML = "<br/>评论：<br/>"; 
    var nametext = nameBox.insertCell(-1); 
    nametext.style.cssText = "font:12pt '宋体';text-align:Left; margin-left:0px"; 
    //nametext.innerHTML = "<br/><textarea type='text' cols='30' rows='3' placeholder='"+msg+"' id='modalName'/> "; 
	nametext.innerHTML = "<br/><input type='text' value='"+msg+"' id='modalName'/> "; 
	//alert(nametext.innerHTML);
    //层上动作按钮的内容 
    var submitBox = table.insertRow(-1); 
    var submitBtn = submitBox.insertCell(-1); 
    submitBtn.style.cssText = "text-align:center;"; 
    submitBtn.colSpan = 2; 
    submitBtn.innerHTML = "<br/><input type='Button' value='确 定' id='saveHeader' onclick='alert(document.getElementById(\"modalName\").value)' /><input type='Button' value='取 消' id='cancel' onclick='close()' /><br/>"; 
    function getEvent() { 
        return window.event || arguments.callee.caller.arguments[0]; 
    } 
} 

//<input type="button" value="编辑" onclick="alertWin('编辑评论','111',300,150);" />