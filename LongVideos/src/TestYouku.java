import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TestYouku {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		spideInfo();
//		String href="http://v.youku.com/v_show/id_XNjgyMDYyNTgw.html?spm=a2h1n.8251845.0.0";
//		Document doc=Jsoup.connect(href).get();
//        Elements es=doc.getElementsByClass("drama-content");
////        System.out.println(es);
//        String ss=es.toString();
//        String url="http"+ss.substring(ss.indexOf("<a href=")+9, ss.indexOf("\" data"));
//        System.out.println(url);
        
        
        searchVideo();
	}
	private static void searchVideo() throws IOException {
		String url="http://www.soku.com/search_video/q_%E5%A4%A7%E8%AF%9D%E8%A5%BF%E6%B8%B8?f=1&kb=020200000000000_%E4%BD%99%E7%BD%AA_%E5%A4%A7%E8%AF%9D%E8%A5%BF%E6%B8%B8";
		Document doc=Jsoup.connect(url).get();
		Elements infs=doc.getElementsByClass("s_dir");//s_dir(所有)  s_poster(图片评分)   s_info(详细信息)
//		System.out.println(infs);
		for(Element e:infs){
			String se=e.toString();
			System.out.println(se);
			//图片url
			String img="http:"+se.substring(se.indexOf("<img src=\"")+10, se.indexOf("<img src=\"")+58);
//			System.out.println(img);
			//片名
			String name=se.substring(se.indexOf("_log_title=")+12, se.indexOf("_iku_showid")-2);
//			System.out.println(name);
			//类型
			String type=se.substring(se.indexOf("<span class=\"base_type\">")+24, se.indexOf("<span class=\"base_type\">")+38);
			type=type.substring(0, type.indexOf("</span>"));
//			System.out.println(type);
			//地区
			String area=se.substring(se.indexOf("<span class=\"s_area\">")+24, se.indexOf("<span class=\"s_area\">")+48);
			area=area.substring(0, area.indexOf("</span>"));
//			System.out.println(area);
			//剧情简介
			String desc=se.substring(se.indexOf("<span data-text=")+17, se.indexOf("</span> <a _log_type="));
			desc=desc.substring(0, desc.indexOf("\">"));
//			System.out.println(desc);			
			int l=e.toString().length();
			System.out.println(l);//9615   10085   10130   3898
			System.out.println("--------------------");

		}
		
	}
	//爬取影片的外层信息，包括视频url，图片，主演，播放量
	private static void spideInfo() throws IOException {
		String url="http://list.youku.com/category/show/c_96_u_1_pt_1_s_1_d_4.html?spm=a2h1n.8251845.filterPanel.5!5~1~3!2~A";
		Document doc=Jsoup.connect(url).get();
        Elements  movs=doc.getElementsByClass("mr1");//p-thumb，用外层div不行
        for(Element mov:movs){
        	String sMov=mov.toString();
//        	System.out.println(sMov);
        	//视频链接
        	String href="http://"+sMov.substring(sMov.indexOf("<a href=\"//")+11,sMov.indexOf(".html\"")+5);
//        	//视频展示图片
        	String img=sMov.substring(sMov.indexOf("_src=\"")+6,sMov.indexOf("\" src=\""));
        	//片名
        	String title=sMov.substring(sMov.indexOf("title=\"")+7,sMov.indexOf("\" target="));        	
        	//主演，播放量
        	if(sMov.contains("<li class=\"actor\">")){//是电影或电视剧，有主演
        		//主演和播放量的混合字符串
        		String mix=sMov.substring(sMov.indexOf("<li class=\"actor\">"));            	
            	//获取主演
            	String [] actors=mix.split("<a href=");
            	for(int i=1;i<actors.length;i++){
            		String actorL=actors[i].substring(3,actors[i].indexOf("\" target"));
            		System.out.println(actorL);
            		String actorN=actors[i].substring(actors[i].indexOf(">")+1, actors[i].indexOf("<"));
            		System.out.println(actorN);
            	}
            	
            	//获取播放量
            	String playNum=mix.substring(mix.indexOf("<li>")+4,mix.indexOf("次播放 </li>")+3);
            	System.out.println(playNum);

        	}else{//<ul class="info-list">  -------无主演
        		String mix=sMov.substring(sMov.indexOf("<ul class=\"info-list\">"));
            	System.out.println(mix);
            
            	//获取播放量
            	String playNum=mix.substring(mix.indexOf("<li>")+4,mix.indexOf("次播放 </li>")+3);
            	System.out.println(playNum);
        	}
        	
        	System.out.println("-----------------------------");

        }
	}

}
